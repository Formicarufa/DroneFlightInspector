/**
* Philosophy: There are 2 basic sub-packages, series and values.
* <p>
* Classes in the series package model the hierarchy of all recorded information 
* - each SeriesWrapper class represents one set of data e.g. a 
* quantity. Each series includes time information for each recorded value.
* <p>
* Conversely, the classes in the values sub-package represent a hierarchy of 
* values taken from all series at a certain time. 
* (Whereas series are vectors, values contain only single / scalar values.)
* <p>
*  The TimeSelection component has access to the hierarchy of series and
*  is capable of creating a hierarchy of values when user selects some point in time.
* The model of values then becomes accessible to all components. Components whose
* content is time-based are then able to change according to the provided values.
* <p>
* Model of series is also prepared for selection of a time interval. If a time
* interval is selected, each series may provide a set of values which summarize
* the content of the series in the interval.   (E.g. an arithmetic mean of the values)
 * Those values are put in the values hierarchy by the TimeSelection component.
 * <p>
 * <b>Content of this package:</b>
 * {@link ImportHelper} class useful when importing new series.
 * {@link TimeSelectionLayer} layer interface used when we want the series 
 * to be displayed on the timeline.
 * {@link TimeValuesConverter} for estimating of a different time value type ({@link TimeStampType}).
**/
package cz.dfi.datamodel;
