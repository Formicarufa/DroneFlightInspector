/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.player;

import cz.dfi.recorddataprovider.DataLoadedCallback;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

/**
 * 24.6.2016
 * @author Tomas Prochazka
 */
@ServiceProvider(service = DataLoadedCallback.class)
public class AddPlayActionCallback implements DataLoadedCallback{

    @Override
    public void dataLoaded(Lookup data, InstanceContent content) {
        content.add(new RecordingPlayer(data, content));
        content.add(new RewindCookie(data, content));
    }

}
