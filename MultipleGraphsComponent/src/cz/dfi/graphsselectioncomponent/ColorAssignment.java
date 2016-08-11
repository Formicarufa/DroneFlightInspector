/*
 */

package cz.dfi.graphsselectioncomponent;

import java.awt.Color;
import java.awt.Paint;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.jfree.chart.ChartColor;

/**
 *Assigns unused color to the quantity that will be drawn on the graph.
 * @author Tomas Prochazka
 * 10.1.2016
 */
public class ColorAssignment {
  public static Paint[] paintArray=ChartColor.createDefaultPaintArray();
  private int i=0;
  private final List<Color> used;

    public ColorAssignment(List<GraphedQuantityNode> nodes) {
      used = nodes.stream().map((x)->x.quantity.getColor()).filter(c->c!=null).collect(Collectors.toList());
    }
    public void assignColor(GraphedQuantity q) {
        while (used.contains((Color)paintArray[i]) && i<paintArray.length) {
            i++;
        } 
        if (i==paintArray.length) {
            Random r= new Random();
            q.setColor(new Color(r.nextFloat(),r.nextFloat(),r.nextFloat()));
        } else {
            q.setColor((Color)paintArray[i++]);
        }
    }
    public void assignToAll(List<GraphedQuantityNode> nodes) {
        for (GraphedQuantityNode node : nodes) {
            final GraphedQuantity quantity = node.quantity;
            if (quantity.getColor()== null) assignColor(quantity);
        }
    }
  
  
  
}
