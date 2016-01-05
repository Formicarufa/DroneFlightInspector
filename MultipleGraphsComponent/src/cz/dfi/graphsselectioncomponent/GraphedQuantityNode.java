/*
 */

package cz.dfi.graphsselectioncomponent;

import cz.dfi.recorddataprovider.CurrentFileLookupManager;
import java.beans.IntrospectionException;
import org.openide.explorer.view.CheckableNode;
import org.openide.nodes.BeanNode;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Tomas Prochazka
 * 22.12.2015
 */
public class GraphedQuantityNode extends BeanNode<GraphedQuantity> implements CheckableNode {

        GraphedQuantity quantity;
        boolean checked = false;

        public GraphedQuantityNode(GraphedQuantity quantity) throws IntrospectionException {
            super(quantity);
            this.quantity = quantity;
            setDisplayName(quantity.getQuantityName());
        }
        public GraphedQuantityNode(GraphedQuantity quantity, boolean checked) throws IntrospectionException {
            super(quantity);
            this.quantity = quantity;
            setDisplayName(quantity.getQuantityName());
            this.checked=checked;
        }

        @Override
        public boolean isCheckable() {
            return true;
        }

        @Override
        public boolean isCheckEnabled() {
            return true;
        }

        @Override
        public Boolean isSelected() {
            return checked;
        }

        @Override
        public void setSelected(Boolean selected) {
            if (selected == checked) {
                return;
            }
            checked = selected;
            CurrentFileLookupManager fileContentManager = Lookup.getDefault().lookup(CurrentFileLookupManager.class);
            if (fileContentManager== null) return;
            InstanceContent currentFileLookupContent = fileContentManager.getCurrentFileLookupContent();
            if (checked) {
                currentFileLookupContent.add(quantity);
            } else {
                currentFileLookupContent.remove(quantity);
            }
        }

    }
