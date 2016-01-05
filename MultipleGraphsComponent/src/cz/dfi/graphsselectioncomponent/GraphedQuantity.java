/*
 */

package cz.dfi.graphsselectioncomponent;

import cz.dfi.datamodel.graphable.GraphableQuantity;
import java.awt.Color;
import java.util.Objects;

/**
 *
 * @author Tomas Prochazka
 * 21.12.2015
 */
public class GraphedQuantity {

    public GraphedQuantity(GraphableQuantity quantity) {
        this.quantity = quantity;
    }

    public double getTranslationX() {
        return translationX;
    }

    public void setTranslationX(double translationX) {
        this.translationX = translationX;
    }

    public double getTranslationY() {
        return translationY;
    }

    public void setTranslationY(double translationY) {
        this.translationY = translationY;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public String getQuantityName() {
        return quantity.getName();
    }
    public String getQuantityUnit() {
        return quantity.getUnit();
    }
    GraphableQuantity quantity;
    double translationX;
    double translationY;
    double scaleX;
    double scaleY;
    Color color;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.quantity);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GraphedQuantity other = (GraphedQuantity) obj;
        return this.quantity==other.quantity;
    }
    
}
