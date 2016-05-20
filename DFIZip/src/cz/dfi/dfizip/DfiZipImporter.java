/*
 */
package cz.dfi.dfizip;

import cz.dfi.datamodel.ImportHelper;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.dfizip.constructors.GroupConstructor;
import cz.dfi.dfizip.constructors.SeriesConstructor;
import cz.dfi.dfizip.constructors.SingleSeriesConstructor;
import cz.dfi.dfizip.constructors.TimelineLayerGroupConstructor;
import cz.dfi.dfizip.readers.SkipReader;
import cz.dfi.dfizip.readers.TimeReader;
import cz.dfi.dfizip.readers.ValueReader;
import cz.dfi.fileimporertinterface.FileImporter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import providers.ConstructorProvider;

/**
 *
 * @author Tomas Prochazka 4.5.2016
 */
@ServiceProvider(service = cz.dfi.fileimporertinterface.FileImporter.class)
public class DfiZipImporter implements FileImporter {

    private static final  Logger LOGGER=Logger.getLogger(DfiZipImporter.class.getName());

    public DfiZipImporter() {
    }

    Collection<? extends ConstructorProvider> singleSeriesConstructors;
    @Override
    public boolean loadRecords(File data, InstanceContent content) {
        ZipFile zipFile;
        singleSeriesConstructors= Lookup.getDefault().lookupAll(ConstructorProvider.class);
        try {
            zipFile = new ZipFile(data);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return false;
        }
        ZipEntry description = zipFile.getEntry("description.xml");
        if (description == null) {
            String s = "The loaded file " + data.getName() + " does not contain"
                    + " a content description. ";
            NotifyDescriptor nd = new NotifyDescriptor.Message(s, NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notifyLater(nd);
            LOGGER.log(Level.SEVERE, s);
            return false;
        }
        Document descrDoc;
        try (InputStream is = zipFile.getInputStream(description)){
            descrDoc = XMLUtil.parse(new InputSource(is), false, false, null, null);
        } catch (IOException | SAXException ex) {
            Exceptions.printStackTrace(ex);
            return false;
        }
        Element documentElement = descrDoc.getDocumentElement();
        assert documentElement.getNodeName().equals("recording");
        nodeChildren(documentElement).forEach(x->{
            assert x.getNodeName().equals("toplevelgroup");
            try {
                loadGroupFromFile((Element)x,content, zipFile);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        });
        return true;
    }

    public static Stream<Node> nodeChildren(Element element) {
        NodeList children_list = element.getChildNodes();
        Stream<Node> children = IntStream.range(0, children_list.getLength()).mapToObj(children_list::item).filter(n->n.getNodeType()==Node.ELEMENT_NODE);
        return children;
    }

    private void loadGroupFromFile(Element element, InstanceContent content, ZipFile zipFile) throws IOException {
        String groupName = element.getAttribute("name");
        String fileName = element.getAttribute("file");
        String separator = element.getAttribute("separator");
        if ("comma".equals(separator)) separator = ",";
        else if ("tab".equals(separator)) separator="\t";
        else separator=",";
        
        ZipEntry entry = zipFile.getEntry(fileName);
        if (entry==null) {
            LOGGER.log(Level.SEVERE, "File loading: description does not correspond to the archive content.");   
        }
        final List<ValueReader> readers = new ArrayList<>();
        // classes that will create the model after all data are loaded:
        GroupConstructor groupConstructor = new TimelineLayerGroupConstructor(groupName);
        nodeChildren(element).forEach((x)->{
            readGroupContent(x,groupConstructor, readers);
        });
        InputStream inputStream = zipFile.getInputStream(entry);
        readFileContent(inputStream,separator,readers);
        SeriesGroupWrapper series = groupConstructor.createSeries(null);
        ImportHelper.addTreeToLookup(series, content);
        
    }

    private void readGroupContent(Node x, GroupConstructor parentGroup, List<ValueReader> readers) {
        
        String name = x.getNodeName();
        Element el = (Element)x;
        if ("group".equals(name)) {
            //group inside of a group.
            String groupName = el.getAttribute("name");
            GroupConstructor group = new GroupConstructor(groupName);
            nodeChildren(el).forEach(y->{
                readGroupContent(y, group, readers);
            });
            parentGroup.addChild(group);
        } else if ("time".equals(name)) {
            addTimeInformation(parentGroup, el,readers);
        } else {
            //read single series.
            SingleSeriesConstructor constr = findConstructor(name,el);
            if (constr!=null) {
                parentGroup.addChild(constr);
                readers.add(constr.getReader());
            } else {
                LOGGER.log(Level.SEVERE, "File loading: We do not know how to read the series described by XML element with name {0}", name);
                readers.add(new SkipReader());
            }
        }
    }

    private void addTimeInformation(GroupConstructor parentGroup, Element el, List<ValueReader> readers) {
        String source = el.getAttribute("source");
        String unit = el.getAttribute("unit");
        TimeReader timeReader = new TimeReader(unit);
        if ("board".equals(source)) {
            parentGroup.setOnboardTimeReader(timeReader);
        } else if ("recorder".equals(source)) {
            parentGroup.setRecordTimeReader(timeReader);
        } else {
            LOGGER.log(Level.SEVERE, "File loading: Unknown timestamp source. Expected board or recorder.");
        }
        readers.add(timeReader);
    }

    private SingleSeriesConstructor findConstructor(String name, Element node) {
        for (ConstructorProvider c : singleSeriesConstructors) {
            if (c.getNodeName().equals(name)) return c.getConstructor(node);
        }
        return null;
    }

    private void readFileContent(InputStream inputStream, String separator, List<ValueReader> readers) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int size = readers.size();
        int errors=0;
        while ((line = reader.readLine())!=null) {
            String[] elems = line.split(separator);
            if (elems.length!=size) {
                errors++; //Do not clog up the logger if many lines are wrong.
                if (errors<10) LOGGER.log(Level.SEVERE, "File loading: length of the read line does not match to the description.");
                if (elems.length<size) continue; // skip short lines.
            } 
            for (int i = 0; i < readers.size(); i++) {
                readers.get(i).read(elems[i]);
            }
        }
    }

}
