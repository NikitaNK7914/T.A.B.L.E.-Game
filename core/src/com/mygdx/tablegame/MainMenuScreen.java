package com.mygdx.tablegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import tech.gusavila92.websocketclient.WebSocketClient;

public class MainMenuScreen implements Screen {
    private final GameController game;
    private SpriteBatch spriteBatch=new SpriteBatch();
    private BitmapFont font=new BitmapFont();
    private Stage stage;
    public static  ArrayList<String> names=new ArrayList<>();
    private Stage players_create_stage;
    private Skin skin;
    private TextureAtlas atlas;
    private PerspectiveCamera camera;
    private TextButton start_button;
    private TextButton rules_button;
    private TextButton settings_button;
    private TextButton add_player_button;
    private TextButton begin_game_button;
    private TextField enter_players_name;
    private VerticalGroup verticalGroup;

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
        createWebSocketClient();
        sendMessage("test conn");

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
    private WebSocketClient webSocketClient;


    private void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://192.168.1.42:8001/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                webSocketClient.send("{\"session\":id_session, \"request\":\"ADD\"}"); // REQUEST ADD {"session":id_session, "request":"ADD"}
            }

            @Override
            public void onTextReceived(String s) {
                // you actions when receive message
            }

            @Override
            public void onBinaryReceived(byte[] data) {
            }

            @Override
            public void onPingReceived(byte[] data) {
            }

            @Override
            public void onPongReceived(byte[] data) {
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                webSocketClient.send("{\"session\":id_session, \"request\":\"DELETE\"}"); // REQUEST DELETE {"session":id_session, "request":"DELETE"}
            }
        };

        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    public void sendMessage(String message) {
        webSocketClient.send(message);
    }
}
