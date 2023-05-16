package com.mygdx.tablegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;


public class GameScreen implements Screen {
    GameController game;
    PerspectiveCamera camera;
    Model table_model;
    ModelInstance table_instance;
    ModelBatch modelBatch;
    Environment environment;
    Player player;
    SpriteBatch spriteBatch;
    long Time;


    public GameScreen(GameController gam) {
        game = gam;
        modelBatch = new ModelBatch();
        table_model = new G3dModelLoader(new JsonReader()).loadModel(Gdx.files.internal("Table.g3dj"));
        table_instance = new ModelInstance(table_model);
        table_instance.transform.setToScaling(50, 50, 50);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        player = new Player(new Vector3(70, 50, 0),1);
        camera = player.camera;
        for (int i = 0; i < 20; i++) {
            Card card = new Card();
            card.setCardPos(player.deck_pos);
            CanTouch.collisions.add(card);
            CanTouch.sprite_collisions.add(card);
            CanTouch.renderable_3d.add(card);
            player.addToDeck(card);
        }
        camera.update();
        spriteBatch=new SpriteBatch();
    }

    @Override
    public void show() {
        player.player_init();
        Time=TimeUtils.millis();
        player.getHand();
    }

    @Override
    public void render(float delta) {
        player.inputController.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        modelBatch.begin(camera);
        modelBatch.render(table_instance, environment);
        for (Card card : CanTouch.renderable_3d) {
            if (card.animations3D.isEmpty()) {
            } else {
                if (card.animations3D.get(0).iterator().hasNext()) {
                    card.setCardPos((Vector3) card.animations3D.get(0).iterator().next());
                } else {
                    card.animations3Dend(card.animations3D.get(0).id);
                    card.animations3D.remove(0);
                }
            }
            modelBatch.render(card.instance);
        }
        modelBatch.end();
        spriteBatch.begin();
        for (Card card: CanTouch.renderable_2d) {
            Pair<Vector2,Float> animation_data;
            if(card.animations2D.isEmpty()){}
            else {if(card.animations2D.get(0).iterator().hasNext()) {animation_data= (Pair<Vector2, Float>) card.animations2D.get(0).iterator().next();
                card.sprite.setPosition(animation_data.first.x,animation_data.first.y);
                card.sprite.rotate(animation_data.second);}
            else {card.animations2D.remove(0);}}
            card.sprite.draw(spriteBatch);
        }
        spriteBatch.end();
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
