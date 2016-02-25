/*
 */
package cz.dfi.graphsselectioncomponent;

import cz.dfi.datamodel.graphable.GraphableQuantity;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Tomas Prochazka 21.12.2015
 */
public final class GraphedQuantity {

    public GraphableQuantity quantity;
    private long translationX = 0;
    private double translationY = 0;
    private double scaleX = 1;
    private double scaleY = 1;
    private Color color;
    private final List<PropertyChangeListener> listeners = Collections.synchronizedList(new LinkedList<PropertyChangeListener>());

    public GraphedQuantity(GraphableQuantity quantity) {
        this.quantity = quantity;
    }

    public long getTranslationX() {
        return translationX;
    }

    public void setTranslationX(Long translationX) {
        Object old = this.translationX;
        this.translationX = translationX;
        fireEvent("translationX", old, translationX);
    }

    public double getTranslationY() {
        return translationY;
    }

    public void setTranslationY(Double translationY) {
        Object old = this.translationY;
        this.translationY = translationY;
        fireEvent("translationY", old, translationY);

    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(Double scaleX) {
        Object old = this.scaleX;
        this.scaleX = scaleX;
        fireEvent("scaleX", old, scaleX);
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(Double scaleY) {
        Object old = this.scaleY;
        this.scaleY = scaleY;
        fireEvent("scaleY", old, scaleY);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        Object old = this.color;
        this.color = color;
        fireEvent("color", old, color);
    }

    public String getQuantityName() {
        return quantity.getName();
    }

    public String getQuantityUnit() {
        return quantity.getUnit();
    }

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
        return this.quantity == other.quantity;
    }

    private void fireEvent(String propName, Object old, Object newObj) {
        PropertyChangeListener[] temp = listeners.toArray(new PropertyChangeListener[0]);
        for (PropertyChangeListener l : temp) {
            l.propertyChange(new PropertyChangeEvent(this, propName, old, newObj));
        }
    }

    public void addPropertyChangedListener(PropertyChangeListener l) {
        listeners.add(l);
    }

    public void removePropertyChangedListener(PropertyChangeListener l) {
        listeners.remove(l);
    }

}
