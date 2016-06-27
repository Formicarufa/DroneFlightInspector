/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.player;

/**
 *If this class is present in the lookup, the stop button is enabled.
 * @author Tomas Prochazka
 */
public class StopCookie {
    RecordingPlayer player;

    public StopCookie(RecordingPlayer player) {
        this.player = player;
    }
    public void stop() {
        player.stop();
    }

}
