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
import java.util.Comparator;


public class GameScreen implements Screen {
    GameController game;
    Model table_model;
    ModelInstance table_instance;
    ModelBatch modelBatch;
    Environment environment;
    SpriteBatch spriteBatch;
    long Time;


    public GameScreen(GameController gam) {
        game = gam;
        Server.server_init(4);
        modelBatch = new ModelBatch();
        table_model = new G3dModelLoader(new JsonReader()).loadModel(Gdx.files.internal("Table.g3dj"));
        table_instance = new ModelInstance(table_model);
        table_instance.transform.setToScaling(50, 50, 50);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        Server.player_now.camera.update();
        spriteBatch=new SpriteBatch();
    }

    @Override
    public void show() {
        Server.player_now.player_init();
        Server.player_now.getHand();
        //Server.refresh_market();
        Time=TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        Server.player_now.inputController.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        modelBatch.begin(Server.player_now.camera);
        modelBatch.render(table_instance, environment);
        if(!CanTouch.renderable_3d.isEmpty())
        {for (Card card : CanTouch.renderable_3d) {
            if (card.animations3D.isEmpty()) {
            } else {
                if (card.animations3D.get(0).iterator().hasNext()) {
                    card.setCardPos((Vector3) card.animations3D.get(0).iterator().next());
                } else {
                    CanTouch.need_to_delete3D.add(card);
                }
            }
            modelBatch.render(card.instance);
        }
        }
        for(int i=0;i<CanTouch.need_to_delete3D.size();){
            CanTouch.need_to_delete3D.get(0).animation3Dend(CanTouch.need_to_delete3D.get(0).animations3D.get(0).id);
            CanTouch.need_to_delete3D.get(0).animations3D.remove(0);
            CanTouch.need_to_delete3D.remove(0);
        }
        modelBatch.end();
        spriteBatch.begin();
        if(!CanTouch.renderable_2d.isEmpty()){
        for (Card card: CanTouch.renderable_2d) {
            Pair<Vector2,Float> animation_data;
            if(card.animations2D.isEmpty()){}
            else {if(card.animations2D.get(0).iterator().hasNext()) {animation_data= (Pair<Vector2, Float>) card.animations2D.get(0).iterator().next();
                card.sprite.setPosition(animation_data.first.x,animation_data.first.y);
               card.sprite.rotate(animation_data.second);}
            else {CanTouch.need_to_delete2D.add(card);}
            }
            card.sprite.draw(spriteBatch);
        }}
        for(int i=0;i<CanTouch.need_to_delete2D.size();){
            CanTouch.need_to_delete2D.get(0).animation2Dend(CanTouch.need_to_delete2D.get(0).animations2D.get(0).id);
            CanTouch.need_to_delete2D.get(0).animations2D.remove(0);
            CanTouch.need_to_delete2D.remove(0);
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
