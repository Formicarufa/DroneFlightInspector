/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.valuestreeview;

import cz.dfi.datamodel.values.ValueWrapper;
import cz.dfi.datamodel.values.ValuesTreeConsistent;
import cz.dfi.recorddataprovider.FileLookup;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * 6.3.2016
 *
 * @author Tomas Prochazka
 */
public class TopLevelValueNodesFactory extends ChildFactory<ValueWrapper> {

    private final Lookup.Result<ValueWrapper> availableWrappers;
    private final BeanTreeView beanTreeView;
    private final Lookup.Result<ValuesTreeConsistent> dataConsistent = FileLookup.getDefault().lookupResult(ValuesTreeConsistent.class);

    public TopLevelValueNodesFactory(BeanTreeView beanTreeView) {
        Lookup fileLookup = FileLookup.getDefault();
        availableWrappers = fileLookup.lookupResult(ValueWrapper.class);
        dataConsistent.addLookupListener(listener);
        this.beanTreeView = beanTreeView;

    }

    @Override
    protected boolean createKeys(List<ValueWrapper> toPopulate) {
        generatedChildren.clear();
        availableWrappers.allInstances().stream()
                .filter((x) -> x.getParent() == null)
                .forEach(toPopulate::add);
        return true;

    }
    List<AbstractNode> generatedChildren = new ArrayList<>();

    @Override
    protected Node createNodeForKey(ValueWrapper key) {
        AbstractNode node;
        if (key.getChildren() != null) {
            node = new ValuesGroupNode(key);
        } else {
            node = new SingleValueNode(key);
        }
        generatedChildren.add(node);
        return node;
    }

    private final LookupListener listener = (LookupEvent ev)
            -> {
        if (dataConsistent.allInstances().isEmpty()) {
            return;
        }
        if (!tryUpdateCurrentTree()) {
            refresh();
        }
    };

    private void refresh() {
        this.refresh(true);
        expandTree();
    }

    private void expandTree() {
        if (EventQueue.isDispatchThread()) {
            beanTreeView.expandAll();
        } else {
            try {
                EventQueue.invokeAndWait(() -> {
                    expandTree();
                });
            } catch (InterruptedException | InvocationTargetException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    /**
     * Intention: Do not redraw whole tree if the structure of data is the same
     * as last time.
     *
     * @return
     */
    private boolean tryUpdateCurrentTree() {
        List<ValueWrapper> wrappers = new ArrayList<>();
        availableWrappers.allInstances().stream().filter((wrapper) -> (wrapper.getParent()==null)).forEach(wrappers::add);
        if (wrappers.size() != generatedChildren.size()) {
            return false;
        }
        Iterator<AbstractNode> generatedIt = generatedChildren.iterator();
        for (ValueWrapper wrap : wrappers) {
            AbstractNode gen = generatedIt.next();
            if (!tryUpdateSubtree(wrap, gen)) {
                return false;
            }
        }
        return true;

    }

    @SuppressWarnings("StringEquality")
    private boolean tryUpdateSubtree(ValueWrapper wrap, Node gen) {
        //Intentionally comparing strings with == for performance,
        //expecting they come from the same source.
        if (wrap.getName() != gen.getName()) {
            return false;
        }
        final Collection<ValueWrapper> wrapChildren = wrap.getChildren();
        if (wrapChildren == null) {
            if (gen instanceof SingleValueNode) {
                ((SingleValueNode) gen).setWrapper(wrap);
                return true;
            } else {
                return false;
            }
        } else {
            final Node[] nodes = gen.getChildren().getNodes();
            if (wrapChildren.size() != nodes.length) {
                return false;
            }
            return tryUpdateCollections(wrapChildren, nodes);
        }

    }

    private boolean tryUpdateCollections(Collection<ValueWrapper> wrapChildren, Node[] nodes) {
        Iterator<ValueWrapper> iterator = wrapChildren.iterator();
        for (Node node : nodes) {
            ValueWrapper next = iterator.next();
            if (!tryUpdateSubtree(next, node)) {
                return false;
            }
        }
        return true;
    }

}
