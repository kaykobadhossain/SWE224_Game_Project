package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MainMenuScreen;


public class MyGdxGame extends Game {
	public static final int Width=480;
	public static final int Height=720;
	public SpriteBatch batch;



	@Override
	public void create () {

		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));

	}

	@Override
	public void render () {
		super.render();


	}

}