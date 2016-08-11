/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.valuestreeview;

import cz.dfi.datamodel.values.ValueWrapper;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 * Node that represents a group and 
 * generate child nodes for members  of the group
 * using the {@link  ValuesGroupNodeFactory}.
 * 6.3.2016
 * @author Tomas Prochazka
 */
public class ValuesGroupNode extends AbstractNode{

    public ValuesGroupNode(ValueWrapper valueWrapper) {
        super(Children.create(new ValuesGroupNodeFactory(valueWrapper),false), Lookup.EMPTY);
        final String name = valueWrapper.getName();
        setName(name);
        setDisplayName(name);
        setIconBaseWithExtension("cz/dfi/valuestreeview/iconmonstr-layer-21-16.png");
    }

}
