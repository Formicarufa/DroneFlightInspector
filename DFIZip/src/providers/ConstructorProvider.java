/*
 */

package providers;

import cz.dfi.dfizip.constructors.SingleSeriesConstructor;
import org.w3c.dom.Element;

/**
 * Services implementing this interface provide access 
 * to a set of classes which convert a series of values
 * described by an XML node in the description.xml file to a Series in the application
 * model.
 * <p>
 * The algorithm for data loading finds Constructor Providers in the file lookup,
 * new providers can be therefore implemented and registered as services.
 * </p>
 * <p>
 * -> by this approach it is possible to add support for a new data type
 *    in the CSV file & a new element in the XML file
 * @author Tomas Prochazka
 * 15.5.2016
 */
public interface ConstructorProvider {
    /**
     * Gets the name of the XML element that represents a series which can
     * be constructed using the constructor provided by this provider.
     * @return 
     */
    String getNodeName();
    /**
     * Gets the constructor which converts the data described by the
     * XML node with name {@link #getNodeName() } to the application model Series.
     * @param node an XML node with description of the series.
     * @return 
     */
    SingleSeriesConstructor getConstructor(Element node);
}
