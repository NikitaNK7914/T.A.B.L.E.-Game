package com.mygdx.tablegame;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class CanTouch {
    static ArrayList<Touchable> collisions=new ArrayList<>();
    static ArrayList<Touchable> sprite_collisions=new ArrayList<>();
    static ArrayList<Card> renderable_3d =new ArrayList<>();
    static ArrayList<Card> renderable_2d=new ArrayList<>();
    static ArrayList<Card> need_to_delete3D=new ArrayList<>();
    static ArrayList<Card> need_to_delete2D=new ArrayList<>();
    static ArrayList<ElementUI> UI_elements=new ArrayList<>();
    static Card now_selected_card=null;

    public static void setNow_selected_card(Card card) {
        if(now_selected_card!=null)now_selected_card.non_selected();
        now_selected_card=card;
    }

    public CanTouch() {
    }
}
