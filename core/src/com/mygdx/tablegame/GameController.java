package com.mygdx.tablegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public  class GameController extends Game {
	MainMenuScreen menu;
	GameScreen gameScreen;

	@Override
	public void create () {
		gameScreen=new GameScreen(this);
		menu=new MainMenuScreen(this);
		this.setScreen(menu);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {

	}
}

