/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.datamodel.values;

/**
 * If an instance of this class is present in a file lookup it indicates that
 * the Values Tree is in a consistent state and can be used. 
 * <p>
 * When the time selection component changes the values tree it removes the instance from the 
 * lookup and puts it back after all changes are completed.
 * No other classes should remove the instance of this class from the lookup,
 * nor should they add new.
 * <p>
 * This is only an optimization: the values tree can actually be used anytime
 * (it is never corrupted) but the values are added one by one and therefore it
 * is more efficient to perfomrm update at once after all changes are applied.
 * <p>
 * 23.6.2016
 * @author Tomas Prochazka
 */
public class ValuesTreeConsistent {

}
