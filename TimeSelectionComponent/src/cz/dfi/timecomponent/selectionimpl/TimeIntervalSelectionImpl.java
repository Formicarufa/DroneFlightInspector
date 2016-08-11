/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.timecomponent.selectionimpl;

import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.timeselection.TimeIntervalSelection;


public class TimeIntervalSelectionImpl implements TimeIntervalSelection {

    private final TimeInterval i;
    public TimeIntervalSelectionImpl(TimeInterval i) {
        this.i = i;
    }
    @Override
    public TimeInterval getSelectedInterval() {
        return i;
    }

}
