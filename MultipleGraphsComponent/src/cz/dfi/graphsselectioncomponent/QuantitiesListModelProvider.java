/*
 */
package cz.dfi.graphsselectioncomponent;

import cz.dfi.datamodel.graphable.GraphableQuantity;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author Tomas Prochazka 22.12.2015
 */
public class QuantitiesListModelProvider extends Children.Keys<GraphedQuantityNode> implements LookupListener {

    Lookup l;
    private Lookup.Result<GraphableQuantity> graphablesResult;

    public final void setCurrentFileLookup(Lookup l) {
        if (l == this.l) {
            return;
        }
        if (graphablesResult != null) {
            this.graphablesResult.removeLookupListener(this);
            graphablesResult = null;
        }
        this.l = l;
        if (l == null) {
            return;
        }
        listenToLookupChanges();
    }

    private void listenToLookupChanges() {
        graphablesResult = l.lookupResult(GraphableQuantity.class);
        graphablesResult.addLookupListener(this);
        resultChanged(null);
    }

    public QuantitiesListModelProvider(Lookup l) {
        this.l = l;
    }

    @Override
    protected Node[] createNodes(GraphedQuantityNode key) {
        return new Node[]{key};

    }

    @Override
    protected void addNotify() {
        if (l == null) {
            return;
        }
        listenToLookupChanges();
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Collection<? extends GraphableQuantity> possible = graphablesResult.allInstances();
        Collection<GraphedQuantity> currentlyGraphed = currentlyGraphedQuantities();
        List<GraphedQuantityNode> result = new ArrayList<>();
        for (GraphableQuantity q : possible) {
            GraphedQuantity graphedQuantity = new GraphedQuantity(q);
            boolean checked=false;
            for (GraphedQuantity current : currentlyGraphed) {
                if (current.equals(graphedQuantity)) {
                    graphedQuantity = current;
                    checked=true;
                    break;
                }
            }
            try {
                result.add(new GraphedQuantityNode(graphedQuantity,checked));
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        setKeys(result);
    }

    public Collection<GraphedQuantity> currentlyGraphedQuantities() {
        if (l == null) {
            return new ArrayList<>(0);
        }
        Collection<? extends GraphedQuantity> graphed = l.lookupAll(GraphedQuantity.class);
        ArrayList<GraphedQuantity> res = new ArrayList<>(graphed.size());
        graphed.stream().forEach((g) -> {
            res.add(g);
        });
        return res;
    }

}
