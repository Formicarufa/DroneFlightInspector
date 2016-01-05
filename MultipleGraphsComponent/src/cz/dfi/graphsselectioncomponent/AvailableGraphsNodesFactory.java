/*
 */

package cz.dfi.graphsselectioncomponent;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.util.Exceptions;

/**
 *
 * @author Tomas Prochazka
 * 22.12.2015
 */
@Deprecated
public class AvailableGraphsNodesFactory extends ChildFactory<GraphedQuantity> {

        private Collection<? extends GraphedQuantity> graphableQuantities;

        public AvailableGraphsNodesFactory(Collection<? extends GraphedQuantity> graphableQuantities) {
            this.graphableQuantities = graphableQuantities;
        }

        @Override
        protected boolean createKeys(List<GraphedQuantity> toPopulate) {
            for (GraphedQuantity graphedQuantity : graphableQuantities) {
                toPopulate.add(graphedQuantity);
            }
            return true;
        }

        @Override
        protected GraphedQuantityNode createNodeForKey(GraphedQuantity key) {
            try {
                return new GraphedQuantityNode(key);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
            return null;
        }

        public void setGraphableQuantities(List<GraphedQuantity> graphedQuantities) {
            this.graphableQuantities = graphedQuantities;
        }
    }

