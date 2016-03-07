/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.valuestreeview;

import cz.dfi.datamodel.values.ValueWrapper;
import java.util.Collection;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 * 6.3.2016
 *
 * @author Tomas Prochazka
 */
public class ValuesGroupNodeFactory extends ChildFactory<ValueWrapper> {

    ValueWrapper parent;

    public ValuesGroupNodeFactory(ValueWrapper parent) {
        this.parent = parent;
    }

    @Override
    protected boolean createKeys(List<ValueWrapper> toPopulate) {
        final Collection<ValueWrapper> children = parent.getChildren();
        if (children == null) {
            return true;
        }
        children.stream()
                .forEach(toPopulate::add);
        return true;
    }

    @Override
    protected Node createNodeForKey(ValueWrapper key) {
        if (key.getChildren() != null) {
            return new ValuesGroupNode(key);
        }
        return new SingleValueNode(key);

    }

}
