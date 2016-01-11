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
public final class GraphedQuantity {


    public GraphedQuantity(GraphableQuantity quantity) {
        this.quantity = quantity;
    }

    public double getTranslationX() {
        return translationX;
    }

    public void setTranslationX(Double translationX) {
        this.translationX = translationX;
    }

    public double getTranslationY() {
        return translationY;
    }

    public void setTranslationY(Double translationY) {
        this.translationY = translationY;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(Double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(Double scaleY) {
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
    public GraphableQuantity quantity;
    double translationX=0;
    double translationY=0;
    double scaleX=1;
    double scaleY=1;
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
