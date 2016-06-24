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
        id = "cz.dfi.player.PlayAction"
)
@ActionRegistration(
        iconBase = "cz/dfi/player/icon_play.png",
        displayName = "#CTL_PlayAction"
)
@ActionReference(path = "Toolbars/File", position = 500)
@Messages("CTL_PlayAction=Play Recording")
public final class PlayAction implements ActionListener {

    private final RecordingPlayer context;

    public PlayAction(RecordingPlayer context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // TODO use context
    }
}
