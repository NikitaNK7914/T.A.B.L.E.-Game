package com.mygdx.tablegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import javax.swing.plaf.ScrollBarUI;
import javax.swing.text.View;

public class MainMenuScreen implements Screen {
    final GameController game;
    SpriteBatch spriteBatch=new SpriteBatch();
    BitmapFont font=new BitmapFont();
    Stage stage;
   static  ArrayList<String> names=new ArrayList<>();
    Stage players_create_stage;
    Skin skin;
    TextureAtlas atlas;
    PerspectiveCamera camera;
    TextButton start_button;
    TextButton rules_button;
    TextButton settings_button;
    TextButton add_player_button;
    TextButton begin_game_button;
    TextField enter_players_name;
    VerticalGroup verticalGroup;

    public MainMenuScreen(GameController gam) {
        verticalGroup = new VerticalGroup();
        font.getData().setScale(5);
        this.game = gam;
        GameController.state=GameState.START;
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        atlas = new TextureAtlas(Gdx.files.internal("text_button.pack"));
        skin.addRegions(atlas);
        skin.load(Gdx.files.internal("skin.json"));
        start_button = new TextButton("Start Game", skin);
        start_button.setSize(600, 300);
        start_button.setColor(new Color(0.57f, 0.222222f, 0.362785f, 1));
        start_button.getLabel().setFontScale(5);
        start_button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameController.state=GameState.CREATING;
            }
        });
        rules_button = new TextButton("Game Rules", skin);
        rules_button.setSize(600, 300);
        rules_button.setColor(Color.CHARTREUSE);
        rules_button.getLabel().setFontScale(5);
        rules_button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        settings_button = new TextButton("Settings", skin);
        settings_button.setColor(Color.CORAL);
        settings_button.setSize(600, 300);
        settings_button.getLabel().setFontScale(5);
        settings_button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                names.add("biba");
                names.add("boba");
                names.add("hui s goryi");
                Server.players_count+=3;
                Server.server_init(names);
                game.setScreen(new GameScreen());
                GameController.state=GameState.CHANGE_PLAYER;
            }
        });
        verticalGroup.setSize(600, 500);
        verticalGroup.fill(1.5f);
        verticalGroup.wrap(true);
        verticalGroup.setPosition(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 250);
        verticalGroup.addActor(start_button);
        verticalGroup.addActor(rules_button);
        verticalGroup.addActor(settings_button);
        players_create_stage = new Stage();
        add_player_button = new TextButton("add player", skin);
        begin_game_button = new TextButton("start game", skin);
        enter_players_name = new TextField("enter player`s name", skin);
        enter_players_name.setPosition(Gdx.graphics.getWidth() / 2f - enter_players_name.getWidth() * 3f, Gdx.graphics.getHeight() * 0.67f);
        enter_players_name.setSize(800, 150);
        enter_players_name.getStyle().font.getData().setScale(4);
        enter_players_name.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
               enter_players_name.setText("");
            }
        });
        add_player_button.getLabel().setFontScale(5);
        add_player_button.setSize(600, 250);
        add_player_button.setPosition(Gdx.graphics.getWidth() * 0.67f, Gdx.graphics.getHeight() / 5);
        add_player_button.setColor(Color.GOLD);
        add_player_button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(enter_players_name.getText()!="" && Server.players_count<5){
                    Server.players_count++;
                    names.add(enter_players_name.getText());
                    enter_players_name.setText("");
                    if(Server.players_count>1)begin_game_button.setVisible(true);
                }
            }
        });
        begin_game_button.setSize(600, 250);
        begin_game_button.setColor(Color.SCARLET);
        begin_game_button.setPosition(Gdx.graphics.getWidth() * 0.67f, Gdx.graphics.getHeight() / 5 + 250);
        begin_game_button.setVisible(false);
        begin_game_button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Server.server_init(names);
                game.setScreen(new GameScreen());
                GameController.state=GameState.CHANGE_PLAYER;
            }
        });
        stage.addActor(verticalGroup);
        players_create_stage.addActor(enter_players_name);
        players_create_stage.addActor(begin_game_button);
        players_create_stage.addActor(add_player_button);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        switch (GameController.state) {
            case START: {
                Gdx.input.setInputProcessor(stage);
                camera.update();
                ScreenUtils.clear(1,1,1,1);
                stage.act();
                stage.draw();
                break;
            }
            case CREATING: {
                Gdx.input.setInputProcessor(players_create_stage);
                camera.update();
                ScreenUtils.clear(1, 1, 1, 1);
                players_create_stage.act();
                players_create_stage.draw();
                spriteBatch.begin();
                font.draw(spriteBatch,"Players  "+Server.players_count+"/4",add_player_button.getX()+50,add_player_button.getY()-50);
                spriteBatch.end();
                break;
            }
        }
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
