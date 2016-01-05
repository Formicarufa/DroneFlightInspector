/*
 */

package cz.dfi.ardronerosbag;

import cz.dfi.datamodel.FlightDataRecord;
import cz.dfi.datamodel.TimeValuesConverter;

/**
 *
 * @author Tomas Prochazka
 * 22.12.2015
 */
public class ArdroneStupidTimeValuesConverter implements TimeValuesConverter{

    double constant;

    public ArdroneStupidTimeValuesConverter(FlightDataRecord firstRecord) {
        //TODO: Implement converter
    }
    
    @Override
    public Double boardTimeToRecordTime(double time, boolean outcoming) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double recordTimeToBoardTime(double time, boolean outcoming) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
