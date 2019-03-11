package tictactoe.main.states;

import java.awt.Graphics;

import tictactoe.main.Game;

public class MenuState extends State {
	
	private Game game;
	
	public MenuState(Game game) {
		this.game = game;
	}
	
	@Override
	public void tick() {
		if(game.getKeyManager().enter)
			State.setState(game.gameState);
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
