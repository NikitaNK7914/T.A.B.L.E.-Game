package com.mygdx.tablegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    Integer player_number = 1;
    PerspectiveCamera camera;
    MyCameraInputController inputController;
    Vector3 camera_pos;
    Integer health;
    Integer win_points;
    Integer hand_size;
    ArrayList<Card> deck;
    Vector3 deck_pos;
    ArrayList<Card> hand;
    Vector3 hand_pos;
    BoundingBox box;

    public Player(Vector3 pos,int num) {
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera_pos = pos;
        camera.near = 1;
        camera.far = 300;
        camera.position.set(camera_pos);
        camera.lookAt(0, 0, 0);
        inputController = new MyCameraInputController(camera);
        health = 20;
        win_points = 0;
        hand_size = 5;
        player_number=num;
        camera.update();
        deck_pos=new Vector3(0,0,0);
        deck = new ArrayList<Card>();
        hand = new ArrayList<Card>();

    }

    public void player_init() {
        Gdx.input.setInputProcessor(inputController);
    }

    public void addToDeck(Card card) {
        deck.add(card);
    }

    public void getHand()  {
        Collections.shuffle(deck);
        hand.clear();
        deck.trimToSize();
        for (int i = 0;i<hand_size;i++) {
            hand.add(deck.get(i));
        }
        for (int i=0;i<hand_size;i++) {
            //deck.remove(0);
        }
        for(int i=0;i<hand_size;i++){
            hand.get(i).calculate_inHand_pos(hand_size,i,true);
            hand.get(i).convertTo2D(camera.position);
        }
    }
}


