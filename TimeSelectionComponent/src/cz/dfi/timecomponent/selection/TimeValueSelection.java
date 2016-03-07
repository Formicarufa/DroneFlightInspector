/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.timecomponent.selection;

import cz.dfi.datamodel.values.TimeStamp;


public interface TimeValueSelection extends TimeSelection {
    TimeStamp getSelectedValue();
    
}
