/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.valuestreeview;

import cz.dfi.datamodel.values.ValueWrapper;
import cz.dfi.recorddataprovider.FileLookup;
import java.util.Collection;
import java.util.List;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
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

    public TopLevelValueNodesFactory(BeanTreeView beanTreeView) {
        Lookup fileLookup = FileLookup.getDefault();
        availableWrappers = fileLookup.lookupResult(ValueWrapper.class);
        availableWrappers.addLookupListener(listener);
        this.beanTreeView=beanTreeView;

    }

    @Override
    protected boolean createKeys(List<ValueWrapper> toPopulate) {
        availableWrappers.allInstances().stream()
                .filter((x)->x.getParent()==null)
                .forEach(toPopulate::add);
        return true;
    }

    @Override
    protected Node createNodeForKey(ValueWrapper key) {
        if (key.getChildren()!=null) {
            return new ValuesGroupNode(key);
        } else {
            return new SingleValueNode(key);
        }
    }

    private final LookupListener listener = (LookupEvent ev)
            -> {
        this.refresh(true);
        expandTree();
    };
;

    private void expandTree() {
        beanTreeView.expandAll();
    }


}
