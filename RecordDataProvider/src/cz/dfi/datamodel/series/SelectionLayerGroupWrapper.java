/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.datamodel.series;

import cz.dfi.datamodel.TimeSelectionLayer;

/**
 * Top level group of series which is displayed as a layer on the time selection component.
 * 7.3.2016
 * @author Tomas Prochazka
 */
public class SelectionLayerGroupWrapper extends TopLevelSeriesGroupWrapper implements TimeSelectionLayer{

    public SelectionLayerGroupWrapper(String name, TimeStampArray timeStamps) {
        super(name, timeStamps);
    }

}
