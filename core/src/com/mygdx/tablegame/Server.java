package com.mygdx.tablegame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class Server {
    static ArrayList<Card> main_deck = new ArrayList<>();
    static Vector3 main_deck_pos=new Vector3(0,48,19);
    static ArrayList<Card> legend_deck = new ArrayList<>();
    static ArrayList<Card> destroyed_deck = new ArrayList<>();
    static ArrayList<Card> market_deck;
    static Vector3 market_deck_pos=new Vector3(30,30,-30);
    static Player[] players;
    static Player player_now;
    private static int turns_lasts;
    static int players_count;
    static long server_time;

    public static void server_init(int player_count) {
        players = new Player[player_count];
        for (int i = 0; i < player_count; i++) {
            players[i] = new Player(new Vector3(70+MathUtils.random(-20,20), 50+MathUtils.random(-20,20), 0+MathUtils.random(-20,20)), i);
            for (int j=0;j<10;j++){
                Card card=new Card();
                card.setCardPos(players[i].deck_pos);
                players[i].deck.add(card);
            }
        }
        player_now = players[0];
        players_count = player_count;
        turns_lasts = 0;
        for (int i = 0; i < 20; i++) {
            Card card = new Card();
            main_deck.add(card);
        }

    }

    public static void attack(int target_player, int damage) {
        players[target_player].health -= damage;

    }

    public static void get_armor(int armor) {
        player_now.armor += armor;
    }

    public static Card get_card(int player_num, String container_id) {
        Card card = null;
        if (player_num == -1) {
            switch (container_id) {
                case ("main_deck"): {
                    card = main_deck.get(0);
                    main_deck.remove(0);
                    break;
                }
                case ("legend_deck"): {
                    card = legend_deck.get(0);
                    legend_deck.remove(0);
                    break;
                }
            }
        } else {
            switch (container_id) {
                case ("hand"): {
                    int i = MathUtils.random(0, players[player_num].hand_size - 1);
                    card = players[player_num].hand.get(i);
                    players[player_num].hand.remove(i);
                    break;
                }
                case ("deck"): {
                    card = players[player_num].deck.get(0);
                    players[player_num].deck.remove(0);
                    break;
                }
                case ("trash"): {
                    card = players[player_num].trash.get(0);
                    players[player_num].trash.remove(0);
                    break;
                }
            }
        }
        return card;
    }
    public static void refresh_market(){
        for(int i=0;i<5;i++){
            Card card= Server.get_card(-1,"main_deck");
            CanTouch.renderable_3d.add(card);
            card.animations3D.add(card.doAnimation3D(main_deck_pos,new Vector3(market_deck_pos.x+card.box.getWidth()*i,main_deck_pos.y,main_deck_pos.z),30,"to_market_deck"));
        }
    }

    public static void turn_ended() {
        server_time= TimeUtils.millis();
        for (Card card:player_now.hand) {
            card.convertTo3D(player_now.camera.position,player_now.trash_pos);
        }
        for (Card card:player_now.on_table_cards) {
            card.animations3D.add(card.doAnimation3D(card.update_pos(),player_now.trash_pos,40,"to_trash"));
        }
        server_time=TimeUtils.millis();
        turns_lasts++;
        if (players_count != 0) {
            player_now = players[turns_lasts % players_count];
        }
        player_now.player_init();
    }
}
