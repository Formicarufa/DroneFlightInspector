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
        category = "Bugtracking",
        id = "cz.dfi.player.RewindAction"
)
@ActionRegistration(
        iconBase = "cz/dfi/player/icon_rewind.png",
        displayName = "#CTL_RewindAction"
)
@ActionReference(path = "Toolbars/File", position = 600)
@Messages("CTL_RewindAction=Rewind")
public final class RewindAction implements ActionListener {

    private final RewindCookie context;

    public RewindAction(RewindCookie context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.rewind();
    }
}
