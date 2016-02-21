/*
 */

package cz.dfi.datamodel;

import java.awt.image.BufferedImage;

/**
 *
 * @author Tomas Prochazka
 * 8.12.2015
 */
public class ImageDataRecord {
    public long time; //nsec
    public BufferedImage image;

    public ImageDataRecord(long time, BufferedImage image) {
        this.time = time;
        this.image = image;
    }
    
}
