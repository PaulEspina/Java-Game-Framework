package tictactoe.main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import tictactoe.main.display.Display;
import tictactoe.main.input.KeyManager;
import tictactoe.main.input.MouseManager;
import tictactoe.main.states.GameState;
import tictactoe.main.states.MenuState;
import tictactoe.main.states.State;

public class Game implements Runnable {

	private String title;
	private int width, height;
	private Boolean running = false;
	
	private Display display;
	private BufferStrategy bs;
	private Graphics g;
	private Thread thread;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	public State menuState;
	public State gameState;

	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		menuState = new MenuState(this);
		gameState = new GameState(this);
		State.setState(menuState);
	}
	
	private void tick() {
		if(State.getState() != null)
			State.getState().tick();
		
		keyManager.tick();
		mouseManager.tick();
		
	}
	
	

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		
		//Draw here
		
		if(State.getState() != null)
			State.getState().render(g);
		
		//End drawing here
		bs.show();
		g.dispose();
	}

	@Override
	public void run() {
		init();
		
		int fps = 30;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if (delta >= 1) {
				tick();
				render();
				delta--;
			}
			
			if(timer >= 1000000000) {
				timer = 0;
				
			}
			
		}
		
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
}
