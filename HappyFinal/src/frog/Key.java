/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Hoan toan hanh phuc
 */
public class Key implements KeyListener{
    private boolean press = false;
    private boolean release = true;

    public boolean isPress() {
        return press;
    }

    public void setPress(boolean press) {
        this.press = press;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            if(release){
                press = true;
                release = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        release = true;
    }
    
}
