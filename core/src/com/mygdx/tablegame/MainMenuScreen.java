package com.mygdx.tablegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.plaf.ScrollBarUI;

public class MainMenuScreen implements Screen {
    final GameController game;
    BitmapFont font;
    Stage stage;
    Skin skin;
    TextureAtlas atlas;
    PerspectiveCamera camera;
    TextButton button;
    List list;

    public MainMenuScreen(GameController gam) {
        game=gam;
        camera=new PerspectiveCamera(67,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin=new Skin();
        atlas=new TextureAtlas("text_button.pack");
        skin.addRegions(atlas);
        skin.load(Gdx.files.internal("skin.json"));
        TextField[] items=new TextField[]{new TextField("sfdxgcvhbjnklm",skin)};
        //items[0].getStyle().font.getData().setScale(5);
        list=new List<>(skin);
        list.setItems(items);
        list.setPosition(500,300);
        list.setSize(500,250);
        button=new TextButton("Start Game",skin);
        button.setSize(500,300);
        button.setX(Gdx.graphics.getWidth()/2-100);
        button.setY(Gdx.graphics.getHeight()/2-50);
        button.setColor(new Color(0.57f,0.222222f,0.362785f,1));
        button.getLabel().setFontScale(5);
        button.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.gameScreen);
            }
        });
        stage.addActor(button);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(1,1,1,1);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
