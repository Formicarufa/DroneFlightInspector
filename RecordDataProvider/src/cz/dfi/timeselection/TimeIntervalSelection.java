/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.timeselection;

import cz.dfi.datamodel.values.TimeInterval;


/**
 * An instance of this class is inserted into the lookup if
 * time interval is selected.
 * @author Tomas Prochazka
*/
public interface TimeIntervalSelection extends TimeSelection {
    TimeInterval getSelectedInterval();
}
