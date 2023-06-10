package com.mygdx.tablegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
// основной класс, его экземпляр создается при запуске приложения
//использует систему экранов, предлагаемую LibGDX
public  class GameController extends Game {
	private MainMenuScreen menu;  //экран главного меню
	public static GameState state;  //энумератор, используемый для смены состояний экранов
	public static final String log_tag="MyApp"; //тег для логов
	@Override
	public void create () {
		TextureStorage tx=new TextureStorage(); //инициализация хранилища текстур
		menu=new MainMenuScreen(this); //создание экрана меню
		this.setScreen(menu);//установка экрана
	}

	@Override
	public void render () {
		super.render(); // вызывает метод рендера установленного на данный момент экрана
	}

	@Override
	public void dispose () {

	}
}

