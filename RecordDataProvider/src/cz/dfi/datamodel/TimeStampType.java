/*
 */

package cz.dfi.datamodel;

/**
 *There are two different time stamps that can be provided with the data:
 * either the time value was added to the information on the board of the drone
 * or it was added to the whole message when the message was save to the file/memory
 * on computer.
 * Some messages can even have both these time stamps included.
 * @author Tomas Prochazka
 * 19.12.2015
 */
public enum TimeStampType {
    OnboardTime, TimeOfRecord, Both
}
