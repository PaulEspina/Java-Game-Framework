package tictactoe.main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean[] keys;
	public boolean enter;
	
	public KeyManager() {
		keys = new boolean[256];
	}
	
	public void tick() {
		enter = keys[KeyEvent.VK_ENTER];
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
	}
	
	
	
}
