package com.mygdx.tablegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    Integer player_number = 1;
    PerspectiveCamera camera;
    MyCameraInputController inputController;
    Integer health;
    Integer win_points;
    Integer hand_size;
    Integer armor;
    ArrayList<Card> deck;
    Vector3 deck_pos;
    ArrayList<Card> hand;
    ArrayList<Card> trash;
    ArrayList<Card> on_table_cards;
    Vector3 trash_pos = new Vector3(0, 30, 0);
    Vector3 played_card_pos=new Vector3(8,8,8);

    public Player(Vector3 pos, int num) {
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 1;
        camera.far = 300;
        camera.position.set(pos);
        camera.lookAt(0, 0, 0);
        inputController = new MyCameraInputController(camera);
        health = 20;
        win_points = 0;
        hand_size = 5;
        player_number = num;
        camera.update();
        deck_pos = new Vector3(0, 0, 0);
        deck = new ArrayList<Card>();
        hand = new ArrayList<Card>();
        trash=new ArrayList<>();
        on_table_cards=new ArrayList<>();
    }

    public void player_init() {
        Gdx.input.setInputProcessor(inputController);
    }

    public void getHand() {
        hand.clear();
        deck.trimToSize();
        if (deck.size() < hand_size) {
            for (Card c : trash) {
                deck.add(c);
            }
            trash.clear();
            Collections.shuffle(deck);
        }
        for (int i = 0; i < hand_size; i++) {
            hand.add(deck.get(i));
            if(!CanTouch.renderable_3d.contains(deck.get(i))) CanTouch.renderable_3d.add(deck.get(i));
        }
        for (int i = 0; i < hand_size; i++) {
            deck.remove(0);
        }
        for (int i = 0; i < hand_size; i++) {
            hand.get(i).calculate_inHand_pos(hand_size, i, true);
            hand.get(i).convertTo2D(camera.position);
        }
    }
    public void refresh_hands_positions(){
        for(int i=0;i<hand.size();i++){
            hand.trimToSize();
            Card card=hand.get(i);
            card.calculate_inHand_pos(hand.size(),i,true);
            card.animations2D.add(card.doAnimation2D(new Vector2(card.sprite.getX(),card.sprite.getY()),new Vector2(card.inHandX,card.inHandY),10,card.inHand_rotation-card.sprite.getRotation(),"relocate_after_discard"));
        }
    }
}


