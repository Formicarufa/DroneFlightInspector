/*
 */
package cz.dfi.graphsselectioncomponent;

import cz.dfi.recorddataprovider.FileLookupProvider;
import java.awt.Color;
import org.openide.explorer.view.CheckableNode;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * GraphQuantityNode is responsible for providing the information about the
 * graph quantity to the components that display the information and let user to
 * update it. (Modify the resulting graph.)
 *
 * @author Tomas Prochazka 22.12.2015
 */
public class GraphedQuantityNode extends AbstractNode implements CheckableNode {

    GraphedQuantity quantity;
    boolean checked = false;
    private InstanceContent content = new InstanceContent();

    private GraphedQuantityNode(GraphedQuantity quantity, InstanceContent ic) {
        this(quantity, false, ic);
    }

    private GraphedQuantityNode(GraphedQuantity quantity, boolean checked, InstanceContent ic) {
        super(Children.LEAF, new AbstractLookup(ic));
        this.content = ic;
        this.quantity = quantity;
        setDisplayName(quantity.getQuantityName());
        this.checked = checked;
    }

    public static GraphedQuantityNode create(GraphedQuantity quantity, boolean checked) {
        GraphedQuantityNode n = new GraphedQuantityNode(quantity, checked, new InstanceContent());
        n.content.add(n);
        return n;
    }

    public static GraphedQuantityNode create(GraphedQuantity quantity) {
        return create(quantity, false);
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
        InstanceContent currentFileLookupContent = FileLookupProvider.getSelectedFileLookupContent();
        if (currentFileLookupContent == null) {
            return;
        }
        if (checked) {
            currentFileLookupContent.add(quantity);
        } else {
            currentFileLookupContent.remove(quantity);
        }
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set informationSet = Sheet.createPropertiesSet();
        informationSet.setDisplayName("Information");

        sheet.put(informationSet);
        Sheet.Set modifications = new Sheet.Set();
        modifications.setDisplayName("Graph modifications");
        sheet.put(modifications);
        try {
            Property<String> nameProp = new PropertySupport.Reflection<>(quantity, String.class, "getQuantityName", null);
            nameProp.setDisplayName("Name");
            nameProp.setShortDescription("Name of the quantity.");
            informationSet.put(nameProp);
            Property<String> unitProp = new PropertySupport.Reflection<>(quantity, String.class, "getQuantityUnit", null);
            unitProp.setDisplayName("Unit");
            unitProp.setShortDescription("Unit of the quantity values.");
            informationSet.put(unitProp);
            Property<Color> colorProp = new PropertySupport.Reflection<>(quantity, Color.class, "color");
            colorProp.setDisplayName("Line color:");
            colorProp.setShortDescription("The color of the line on the chart.");
            modifications.put(colorProp);
            Property<Long> transXProp = new PropertySupport.Reflection<>(quantity, Long.class, "translationX");
            transXProp.setDisplayName("Translation in x:");
            transXProp.setShortDescription("Translation of the graph in the x axis.");
            modifications.put(transXProp);
            Property<Double> transYProp = new PropertySupport.Reflection<>(quantity, Double.class, "translationY");
            transYProp.setDisplayName("Translation in y:");
            transYProp.setShortDescription("Translation of the graph in the y axis.");
            modifications.put(transYProp);
            Property<Double> scaleXProp = new PropertySupport.Reflection<>(quantity, Double.class, "scaleX");
            scaleXProp.setDisplayName("Scale in x:");
            scaleXProp.setShortDescription("Scales the x axis, first value stays untouched.");
            modifications.put(scaleXProp);
            Property<Double> scaleYProp = new PropertySupport.Reflection<>(quantity, Double.class, "scaleY");
            scaleYProp.setDisplayName("Scale in y:");
            scaleYProp.setShortDescription("Number by which the function values are multiplied.");
            modifications.put(scaleYProp);

        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        return sheet;
    }
  

}
