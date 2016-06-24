/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.player;

import org.openide.util.*;
import org.openide.util.lookup.InstanceContent;

/**
 * 23.6.2016
 * @author Tomas Prochazka
 */
public class RecordingPlayer  implements Runnable{
    
    Lookup lookup;
    InstanceContent content;

    public RecordingPlayer(Lookup lookup, InstanceContent content) {
        this.lookup = lookup;
        this.content = content;
    }
    
    @Override
    public void run() {
        
    }

}
