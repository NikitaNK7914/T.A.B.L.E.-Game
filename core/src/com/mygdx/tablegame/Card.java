package com.mygdx.tablegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.JsonReader;


import java.util.ArrayList;


public class Card extends Touchable {
    final static Model card_model = new G3dModelLoader(new JsonReader()).loadModel(Gdx.files.internal("card_model1.g3dj"));
    Vector3 card_pos = new Vector3(0, 0, 0);
    ModelInstance instance;
    Integer power_points;
    Integer win_points;
    boolean is3D;
    TextureAttribute simple_3Dtexture = new TextureAttribute(TextureAttribute.Diffuse, new Texture("card_texture1.png"));
    TextureAttribute selected_3Dtexture = new TextureAttribute(TextureAttribute.Diffuse, new Texture(Gdx.files.internal("card_texture_selected.PNG")));
    Texture simple_2Dtexture = new Texture(Gdx.files.internal("card_texture.PNG"));
    Texture selected_2Dtexture = new Texture(Gdx.files.internal("card_texture_selected.PNG"));
    BoundingBox box;
    Sprite sprite;

    ArrayList<Generator> animations2D;
    ArrayList<Generator> animations3D;
    float max_rotate_angel = 8;
    float inHandX;
    float inHandY;
    float inHand_rotation;
    Vector3 temp_camera_pos;
    Vector3 temp_on_table_pos;

    public Card() {
        sprite = new Sprite(simple_2Dtexture, simple_2Dtexture.getWidth(), simple_2Dtexture.getHeight());
        sprite.setCenter(simple_2Dtexture.getWidth() / 2, simple_2Dtexture.getHeight() / 2);
        sprite.setOrigin(sprite.getWidth(), sprite.getHeight());
        sprite.setTexture(simple_2Dtexture);
        is3D = true;
        instance = new ModelInstance(card_model);
        instance.transform.setTranslation(card_pos);
        change_texture(3);
        box = new BoundingBox();
        instance.transform.setToScaling(10, 10, 10);
        instance.transform.rotate(1, 0, 0, 180);
        animations2D = new ArrayList<>();
        animations3D = new ArrayList<>();
        instance.calculateBoundingBox(box).mul(instance.transform);
    }

    public void change_texture(int type) {
        if (type == 1) {
            sprite.setTexture(simple_2Dtexture);
        }
        if (type == 2) {
            sprite.setTexture(selected_2Dtexture);
        }
        if (type == 3) {
            instance.materials.get(0).set(simple_3Dtexture);
        }
        if (type == 4) {
            instance.materials.get(0).set(selected_3Dtexture);
        }
    }

    public void played() {

    }

    public Generator<Vector3> doAnimation3D(final Vector3 startPos, final Vector3 endPos, final int frames, String id) {
        Generator<Vector3> generator = new Generator<Vector3>() {
            @Override
            protected void run() throws InterruptedException {
                Vector3 nowPos = new Vector3(startPos);
                if (endPos.x == startPos.x) startPos.x += 0.00000001f;
                float distanceXpFrame = (float) (Math.abs(endPos.x - startPos.x) / frames);
                if (endPos.y == startPos.y) startPos.y += 0.00000001f;
                float distanceYpFrame = (float) (Math.abs(endPos.y - startPos.y) / frames);
                if (endPos.z == startPos.z) startPos.z += 0.00000001f;
                float distanceZpFrame = (float) (Math.abs(endPos.z - startPos.z) / frames);
                int directionX = 1, directionY = 1, directionZ = 1;
                if (endPos.x < startPos.x) directionX = -1;
                if (endPos.y < startPos.y) directionY = -1;
                if (endPos.z < startPos.z) directionZ = -1;
                for (int i = 1; i <= frames; i++) {
                    nowPos.set(startPos.x + distanceXpFrame * i * directionX, startPos.y + distanceYpFrame * i * directionY, startPos.z + distanceZpFrame * i * directionZ);
                    yield(nowPos);
                }
            }
        };
        generator.id = id;
        return generator;
    }

    public Generator<Pair<Vector2, Float>> doAnimation2D(final Vector2 startPos, final Vector2 endPos, final int frames, final float rotation, String id) {
        Generator<Pair<Vector2, Float>> generator = new Generator<Pair<Vector2, Float>>() {
            @Override
            protected void run() throws InterruptedException {
                Vector2 nowPos = new Vector2(startPos);
                float rotatePframe = (float) (rotation / frames);
                if (endPos.x == startPos.x) startPos.x += 0.000000001f;
                float distanceXpFrame = (float) (Math.abs(endPos.x - startPos.x) / frames);
                if (endPos.y == startPos.y) startPos.y += 0.000000001f;
                float distanceYpFrame = (float) (Math.abs(endPos.y - startPos.y) / frames);
                int directionX = 1, directionY = 1;
                if (endPos.x < startPos.x) directionX = -1;
                if (endPos.y < startPos.y) directionY = -1;
                Pair<Vector2, Float> answ = new Pair<>(nowPos, rotatePframe);
                for (int i = 1; i <= frames; i++) {
                    answ.first.set(startPos.x + distanceXpFrame * i * directionX, startPos.y + distanceYpFrame * i * directionY);
                    yield(answ);
                }
            }
        };
        generator.id = id;
        return generator;
    }

    public void discard() {
    }

    public void setCardPos(Vector3 pos) {
        card_pos = pos;
        instance.transform.setTranslation(card_pos);
    }

    public BoundingBox getHitBox() {
        instance.calculateBoundingBox(box).mul(instance.transform);
        return instance.calculateBoundingBox(box).mul(instance.transform);
    }

    public Rectangle getSpriteHitBox() {
        return sprite.getBoundingRectangle();
    }

    public void touched() {
        selected();
    }

    public void doubleTouched() {
        Server.turn_ended();
    }

    public void sprite_touched() {
        selected();
    }

    public void sprite_doubleTouched() {
        convertTo3D(Server.player_now.camera.position, Server.player_now.played_card_pos);
    }

    public void convertTo3D(Vector3 camera_pos, Vector3 target_pos) {
        temp_camera_pos = camera_pos;
        temp_on_table_pos = target_pos;
        animations2D.add(doAnimation2D(new Vector2(sprite.getX(), sprite.getY()), new Vector2(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2), 2, 0 - sprite.getRotation(), "convert3D"));
    }

    public void convertTo2D(Vector3 camera_pos) {
        Generator<Vector3> animation3D = doAnimation3D(card_pos, camera_pos, 60, "convert3D");
        animations3D.add(animation3D);
    }

    public void calculate_inHand_pos(int hand_size, int index, boolean set) {
        int n;
        float x = sprite.getX(), y = sprite.getY(), rotation = 0;
        if (hand_size % 2 == 1) {
            n = -hand_size / 2 + index;
            x = Gdx.graphics.getWidth() / 2 + sprite.getWidth() * n;
            if (index > 0) x -= sprite.getWidth() / 5 * index;
            y = -sprite.getHeight() / 4 - Math.abs(sprite.getHeight() / 8 * n);
            if (hand_size == 1) hand_size = 2;
            rotation = max_rotate_angel / (hand_size / 2) * -n;
            if (n < 0) sprite.setOrigin(0, 0);
            if (n > 0) sprite.setOrigin(sprite.getWidth(), 0);
        } else {
            if (index < hand_size / 2) n = -hand_size / 2 + index;
            else n = index - hand_size / 2 + 1;
            int n1_crutch_edition = -hand_size / 2 + index;
            x = Gdx.graphics.getWidth() / 2 + sprite.getWidth() * n1_crutch_edition;
            if (index > 0) x -= sprite.getWidth() / 5 * index;
            y = -sprite.getHeight() / 4 - Math.abs(sprite.getHeight() / 8 * n);
            if (hand_size == 1) hand_size = 2;
            rotation = max_rotate_angel / (hand_size / 2) * -n;
            if (n < 0) sprite.setOrigin(0, 0);
            if (n > 0) sprite.setOrigin(sprite.getWidth(), 0);
        }
        inHandX = x;
        inHandY = y;
        inHand_rotation = rotation;
        if (set) {
            sprite.setPosition(x, y);
            sprite.rotate(rotation);
        }
        sprite.setOriginCenter();
    }

    public void animation3Dend(String animation_id) {
        switch (animation_id) {
            case ("convert3D"): {
                CanTouch.renderable_3d.remove(this);
                CanTouch.collisions.remove(this);
                CanTouch.renderable_2d.add(this);
                if (!CanTouch.sprite_collisions.contains(this))
                    CanTouch.sprite_collisions.add(this);
                is3D = false;
                break;
            }
            case ("played_from_hand"): {
                if (!CanTouch.collisions.contains(this)) CanTouch.collisions.add(this);
                played();
                Server.player_now.on_table_cards.add(this);
            }
            case ("to_market_deck"): {
                if (!CanTouch.collisions.contains(this)) CanTouch.collisions.add(this);
            }
        }
    }

    public void animation2Dend(String animation_id) {
        switch (animation_id) {
            case ("convert3D"): {
                CanTouch.renderable_2d.remove(this);
                CanTouch.sprite_collisions.remove(this);
                CanTouch.renderable_3d.add(this);
                animations3D.add(doAnimation3D(temp_camera_pos,temp_on_table_pos, 60, "played_from_hand"));
                Server.player_now.hand.remove(this);
                Server.player_now.refresh_hands_positions();
                is3D = true;
            }

        }
    }

    public void non_selected() {
        if (is3D) {
            change_texture(3);
        } else change_texture(1);
        animations2D.add(doAnimation2D(new Vector2(sprite.getX(), sprite.getY()), new Vector2(inHandX, inHandY), 15, inHand_rotation, "non_selected"));
    }

    public void selected() {
        if (is3D) change_texture(4);
        else change_texture(2);
        if (!(CanTouch.now_selected_card == this)) {
            CanTouch.setNow_selected_card(this);
            animations2D.add(doAnimation2D(new Vector2(sprite.getX(), sprite.getY()), new Vector2(sprite.getX(), 0), 15, 0 - sprite.getRotation(), "selected"));
        }
    }
    public Vector3 update_pos(){
        instance.calculateBoundingBox(box);
        box.getCenter(card_pos);
        return card_pos;
    }
}