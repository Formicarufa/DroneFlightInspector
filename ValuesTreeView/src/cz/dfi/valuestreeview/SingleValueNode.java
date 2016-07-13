/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.valuestreeview;

import cz.dfi.datamodel.graphable.DoubleValueWrapper;
import cz.dfi.datamodel.values.ValueWrapper;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 * 6.3.2016
 * @author Tomas Prochazka
 */
public class SingleValueNode extends AbstractNode{
    private ValueWrapper wrapper;

    public SingleValueNode(ValueWrapper wrapper) {
        super(Children.LEAF,Lookups.singleton(wrapper));
        this.wrapper = wrapper;
        setName(wrapper.getName());
        if (wrapper instanceof DoubleValueWrapper) {
            String unit = ((DoubleValueWrapper) wrapper).getUnit();
            if (unit!=null) {
                setShortDescription("Unit: " + unit);
            }
        }
        setIconBaseWithExtension("cz/dfi/valuestreeview/iconmonstr-ruler-8-16.png");
    }

 

    @Override
    public String getHtmlDisplayName() {
        String value = wrapper.getValueAsString();
        if (value==null) return null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>");
        stringBuilder.append(wrapper.getName());
        stringBuilder.append(":</b> ");
        stringBuilder.append(value);
        return stringBuilder.toString();
    }

    public void setWrapper(ValueWrapper wrapper) {
        this.wrapper = wrapper;
        fireDisplayNameChange("a","b");
    }
    
    
}
