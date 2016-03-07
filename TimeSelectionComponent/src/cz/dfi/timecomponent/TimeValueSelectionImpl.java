/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.timecomponent;

import cz.dfi.datamodel.values.TimeStamp;
import cz.dfi.timecomponent.selection.TimeValueSelection;


public class TimeValueSelectionImpl implements TimeValueSelection {

    private final TimeStamp t;

    public TimeValueSelectionImpl(TimeStamp t) {
        this.t = t;
    }
    
    @Override
    public TimeStamp getSelectedValue() {
        return t;
    }

}
