/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "RecordingPlayer",
        id = "cz.dfi.player.StopAction"
)
@ActionRegistration(
        iconBase = "cz/dfi/player/icon_pause.png",
        displayName = "#CTL_StopAction"
)
@ActionReference(path = "Toolbars/File", position = 550)
@Messages("CTL_StopAction=Pause")
/**
 * 
 */
public final class StopAction implements ActionListener {

    private final StopCookie context;

    public StopAction(StopCookie context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.stop();
    }
}
