/*
 */
package cz.dfi.graphsselectioncomponent;

import cz.dfi.datamodel.graphable.GraphableQuantity;
import cz.dfi.recorddataprovider.FileLookup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Generates a collection of nodes for available quantities.
 * Updates the collection if available quantities change. 
 * @author Tomas Prochazka 22.12.2015
 */
public class QuantitiesListModelProvider extends Children.Keys<GraphedQuantityNode> implements LookupListener {

    Lookup l;
    private Lookup.Result<GraphableQuantity> graphablesResult;

    private void listenToLookupChanges() {
        graphablesResult = l.lookupResult(GraphableQuantity.class);
        graphablesResult.addLookupListener(this);
        resultChanged(null);
    }

    public QuantitiesListModelProvider() {
        this.l = FileLookup.getDefault();
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
            boolean checked = false;
            for (GraphedQuantity current : currentlyGraphed) {
                if (current.equals(graphedQuantity)) {
                    graphedQuantity = current;
                    checked = true;
                    break;
                }
            }
            result.add(GraphedQuantityNode.create(graphedQuantity, checked));

        }
        ColorAssignment ca = new ColorAssignment(result);
        ca.assignToAll(result);
        setKeys(result);
    }

    public void assignFreeColor(GraphedQuantity q) {

    }

    public Collection<GraphedQuantity> currentlyGraphedQuantities() {

        Collection<? extends GraphedQuantity> graphed = l.lookupAll(GraphedQuantity.class);
        ArrayList<GraphedQuantity> res = new ArrayList<>(graphed.size());
        graphed.stream().forEach((g) -> {
            res.add(g);
        });
        return res;
    }

}
