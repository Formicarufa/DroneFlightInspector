/*
 */

package cz.dfi.datamodel;

import org.netbeans.api.annotations.common.CheckForNull;

/**
 * This is the base interface for classes which wrap a set of
 * loaded values.
 * The interface provides method to get the name of the type of the data,
 * methods to get array of points in time for which the values are present.
 * However, it does not provide access to the data itself.
 * The reason is, the loaded data can be of different types, including
 * primitive types, which can't be represented by generics.
 * (And I do not want to box and unbox them because of performance.)
 * Despite that, this interface provides enough information to be
 * used by, for example,  TimeSelectionComponent.
 * @author Tomas Prochazka
 * 8.12.2015
 */
public interface RecordsWrapper {
    /**
     * Returns a list of time stamps of the recorded values.
     * 
     * Returned time stamps are such that they have been added to the message on board of the drone.
     * therefore the values and the time information shall shall correspond precisely.
     * (No delay should have occurred.)
     * If such data are not present, estimated values may be returned.
     * (Estimated data should be calculated by the TimeValueConvertor which,
     * if it exists, should be present in the Lookup)
     * The user can ensure whether the data are original or estimated by calling
     * the {@link #getOriginalTimeStampSource() }  method.
     * @return list of time values in the ascending order, might also return null
     */
    @CheckForNull double[] getOnBoardTimeValues();
    String getName();
    /**
     * Gets a list of time stamps of the recorded values, 
     * that have been added to the message in the moment when it was saved to the file.
     * If such values are not available, they could be estimated in the same manner
     * as those of {@link #getOnBoardTimeValues() } method.
     * Typically, though, there is no reason these values should not be included.
     * @return list of time values in the ascending order, might also return null
     */
    @CheckForNull double[] getTimeOfRecordValues();
    /**
     * The time information of each record can come from two sources:
     * Either the time stamp is added to the message on the board of the drone
     * or it is added by computer when the message is recorded to file.
     * Some messages may even contain both these time stamps.
     * 
     * @return Type of the time stamp that was present in the original recorded data.
     */
    TimeStampType getOriginalTimeStampSource();
}
