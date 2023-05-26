package com.mygdx.tablegame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Collections;

public class Server {
    static ArrayList<Card> main_deck = new ArrayList<>();
    static Vector3 main_deck_pos = new Vector3(-2.5f, 30.5f, 0);
    static ArrayList<Card> legend_deck = new ArrayList<>();
    static Vector3 legend_deck_pos = new Vector3(2.5f, 30.5f, 0);
    static ArrayList<Card> destroyed_deck = new ArrayList<>();
    static ArrayList<Card> market_deck = new ArrayList<>();
    static Vector3 market_deck_pos = new Vector3(-9.7f, 30, 8);
    static Boolean turn_end_button_pressed = false;
    static Player[] players;
    static Player player_now;
    static Player prev_player;
    static int turns_lasts = 0;
    static int players_count = 0;
    static long server_time;

    public static void server_init(ArrayList<String> names) {
        players = new Player[players_count];
        if (players_count == 2) {
            Player player = new Player(new Vector3(0, 50, 40), 0);
            player.deck_pos = new Vector3(13, 29.9f, 28);
            player.trash_pos = new Vector3(18, 29.7f, 28);
            player.played_card_pos = new Vector3(0, 30, 25);
            players[0] = player;
            Player player1 = new Player(new Vector3(0, 50, -40), 1);
            player1.deck_pos = new Vector3(-13, 29.9f, -28);
            player1.trash_pos = new Vector3(-18, 29.7f, -28);
            player1.played_card_pos = new Vector3(0, 30, -25);
            players[1] = player1;
        }
        if (players_count == 3) {
            Player player = new Player(new Vector3(0, 50, 40), 0);
            player.deck_pos = new Vector3(13, 29.9f, 28);
            player.trash_pos = new Vector3(18, 29.7f, 28);
            player.played_card_pos = new Vector3(0, 30, 25);
            players[0] = player;
            Player player1 = new Player(new Vector3(30, 50, 0), 1);
            player1.deck_pos = new Vector3(18, 29.9f, -10);
            player1.trash_pos = new Vector3(18, 29.7f, -15);
            player1.played_card_pos = new Vector3(19, 30, -8);
            players[1] = player1;
            Player player2 = new Player(new Vector3(0, 50, -40), 2);
            player2.deck_pos = new Vector3(-13, 29.9f, -28);
            player2.trash_pos = new Vector3(-18, 29.7f, -28);
            player2.played_card_pos = new Vector3(0, 30, -25);
            players[2] = player2;

        }
        if (players_count == 4) {
            Player player = new Player(new Vector3(0, 50, 40), 0);
            player.deck_pos = new Vector3(13, 29.9f, 28);
            player.trash_pos = new Vector3(18, 29.7f, 28);
            player.played_card_pos = new Vector3(0, 30, 25);
            players[0] = player;
            Player player1 = new Player(new Vector3(30, 50, 0), 1);
            player1.deck_pos = new Vector3(18, 29.9f, -10);
            player1.trash_pos = new Vector3(18, 29.7f, -15);
            player1.played_card_pos = new Vector3(19, 30, -8);
            players[1] = player1;
            Player player2 = new Player(new Vector3(0, 50, -40), 2);
            player2.deck_pos = new Vector3(-13, 29.9f, -28);
            player2.trash_pos = new Vector3(-18, 29.7f, -28);
            player2.played_card_pos = new Vector3(0, 30, -25);
            players[2] = player2;
            Player player3 = new Player(new Vector3(-30, 50, 0), 3);
            player3.deck_pos = new Vector3(-18, 29.9f, 10);
            player3.trash_pos = new Vector3(-18, 29.7f, 15);
            player3.played_card_pos = new Vector3(-19, 30, 8);
            players[3] = player3;
        }
        for (int i = 0; i < players_count; i++) {
            players[i].name = names.get(i);
            for (int j = 0; j < 6; j++) {
                Znak card = new Znak();
                card.setCardPos(players[i].deck_pos);
                players[i].deck.add(card);
            }
            for (int j = 0; j < 3; j++) {
                Pshik card = new Pshik();
                card.setCardPos(players[i].deck_pos);
                players[i].deck.add(card);
            }
            for (int j = 0; j < 1; j++) {
                Fire_ball card = new Fire_ball();
                card.setCardPos(players[i].deck_pos);
                players[i].deck.add(card);
            }
            Collections.shuffle(players[i].deck);
        }
        player_now = players[0];
        players_count = players_count;
        turns_lasts = 0;
        for (int i = 0; i < 10; i++) {
            Fire_ball card = new Fire_ball();
            main_deck.add(card);
        }
        for (int i = 0; i < 10; i++) {
            Magic_dog card = new Magic_dog();
            main_deck.add(card);
        }
        for (int i = 0; i < 10; i++) {
            Protection_amulet card = new Protection_amulet();
            main_deck.add(card);
        }
        Collections.shuffle(main_deck);
    }

    public static void attack(Player target_player, int damage) {
        target_player.refresh_health(damage);
    }

    public static Card get_card(int player_num, String container_id) {
        Card card = null;
        if (player_num == -1) {
            switch (container_id) {
                case ("main_deck"): {
                    if (!main_deck.isEmpty()) {
                        card = main_deck.get(0);
                        main_deck.remove(0);
                    }
                    break;
                }
                case ("legend_deck"): {
                    if (!legend_deck.isEmpty()) {
                        card = legend_deck.get(0);
                        legend_deck.remove(0);
                    }
                    break;
                }
            }
        } else {
            switch (container_id) {
                case ("hand"): {
                    if (!players[player_num].hand.isEmpty()) {
                        int i = MathUtils.random(0, players[player_num].hand_size - 1);
                        card = players[player_num].hand.get(i);
                        players[player_num].hand.remove(i);
                    }
                    break;
                }
                case ("deck"): {
                    if (!players[player_num].deck.isEmpty()) {
                        card = players[player_num].deck.get(0);
                        players[player_num].deck.remove(0);
                    }
                    break;
                }
                case ("trash"): {
                    if (!players[player_num].trash.isEmpty()) {
                        card = players[player_num].trash.get(0);
                        players[player_num].trash.remove(0);
                    }
                    break;
                }
            }
        }
        return card;
    }

    public static void refresh_market() {
        int y = market_deck.size();
        for (int i = 0; i < 5 ; i++) {
            Card card = Server.get_card(-1, "main_deck");
            CanTouch.renderable_3d.add(card);
            market_deck.add(card);
        }
        for (int i = 0; i < 5; i++) {
            Card card = market_deck.get(i);
            if (turns_lasts != 0) {
                if (players_count == 2) {
                    card.instance.transform.rotate(0, card.getHitBox().getCenterY(), 0, -180);
                }
                if (players_count == 3 && player_now.player_number == 0)
                    card.instance.transform.rotate(0, card.getHitBox().getCenterY(), 0, -180);
                if (players_count == 3 && player_now.player_number == 1)
                    card.instance.transform.rotate(0, card.getHitBox().getCenterY(), 0, -90);
                if (players_count == 3 && player_now.player_number == 2)
                    card.instance.transform.rotate(0, card.getHitBox().getCenterY(), 0, -90);
                if (players_count == 4 && player_now.player_number==0)
                    card.instance.transform.rotate(0, card.getHitBox().getCenterY(), 0, -90);
                if (players_count == 4 && player_now.player_number==1)
                    card.instance.transform.rotate(0, card.getHitBox().getCenterY(), 0, -90);
                if (players_count == 4 && player_now.player_number==2)
                    card.instance.transform.rotate(0, card.getHitBox().getCenterY(), 0, -90);
                if (players_count == 4 && player_now.player_number==3)
                    card.instance.transform.rotate(0, card.getHitBox().getCenterY(), 0, -90);
            }
            if (market_deck.get(i).in_market) {
                if (players_count == 2) {
                    if (player_now.player_number % 2 == 0) {
                        card.animations3D.add(card.doAnimation3D(market_deck.get(i).update_pos(), new Vector3(market_deck_pos.x + card.box.getWidth() * i, market_deck_pos.y, market_deck_pos.z), 30, "to_market_deck"));
                    } else {
                        card.animations3D.add(card.doAnimation3D(market_deck.get(i).update_pos(), new Vector3(-market_deck_pos.x - card.box.getWidth() * i, market_deck_pos.y, -market_deck_pos.z), 30, "to_market_deck"));
                    }
                }
                if (players_count == 3) {
                    if (player_now.player_number == 0) {
                        card.animations3D.add(card.doAnimation3D(market_deck.get(i).update_pos(), new Vector3(market_deck_pos.x + card.box.getWidth() * i, market_deck_pos.y, market_deck_pos.z), 30, "to_market_deck"));
                    }
                    if (player_now.player_number == 1) {
                        card.animations3D.add(card.doAnimation3D(market_deck.get(i).update_pos(), new Vector3(-market_deck_pos.x, market_deck_pos.y, market_deck_pos.z - card.box.getDepth() * i), 30, "to_market_deck"));
                    }
                    if (player_now.player_number == 2) {
                        card.animations3D.add(card.doAnimation3D(market_deck.get(i).update_pos(), new Vector3(-market_deck_pos.x - card.box.getWidth() * i, market_deck_pos.y, -market_deck_pos.z), 30, "to_market_deck"));
                    }
                }
                if (players_count == 4) {
                    if (player_now.player_number == 0) {
                        card.animations3D.add(card.doAnimation3D(market_deck.get(i).update_pos(), new Vector3(market_deck_pos.x + card.box.getWidth() * i, market_deck_pos.y, market_deck_pos.z), 30, "to_market_deck"));
                    }
                    if (player_now.player_number == 1) {
                        card.animations3D.add(card.doAnimation3D(market_deck.get(i).update_pos(), new Vector3(-market_deck_pos.x, market_deck_pos.y, market_deck_pos.z - card.box.getDepth() * i), 30, "to_market_deck"));
                    }
                    if (player_now.player_number == 2) {
                        card.animations3D.add(card.doAnimation3D(market_deck.get(i).update_pos(), new Vector3(market_deck_pos.x + card.box.getWidth() * i, market_deck_pos.y, -market_deck_pos.z), 30, "to_market_deck"));
                    }
                    if (player_now.player_number == 3) {
                        card.animations3D.add(card.doAnimation3D(market_deck.get(i).update_pos(), new Vector3(market_deck_pos.x, market_deck_pos.y, -market_deck_pos.z + card.box.getDepth() * i), 30, "to_market_deck"));
                    }
                }

            } else {
                if (players_count == 2) {
                    if (player_now.player_number % 2 == 0) {
                        card.animations3D.add(card.doAnimation3D(main_deck_pos, new Vector3(market_deck_pos.x + card.box.getWidth() * i, market_deck_pos.y, market_deck_pos.z), 30, "to_market_deck"));
                    } else {
                        card.animations3D.add(card.doAnimation3D(main_deck_pos, new Vector3(-market_deck_pos.x - card.box.getWidth() * i, market_deck_pos.y, -market_deck_pos.z), 30, "to_market_deck"));
                    }
                }
                if (players_count == 3) {
                    if (player_now.player_number == 0) {
                        card.animations3D.add(card.doAnimation3D(main_deck_pos, new Vector3(market_deck_pos.x + card.box.getWidth() * i, market_deck_pos.y, market_deck_pos.z), 30, "to_market_deck"));
                    }
                    if (player_now.player_number == 1) {
                        card.animations3D.add(card.doAnimation3D(main_deck_pos, new Vector3(-market_deck_pos.x, market_deck_pos.y, market_deck_pos.z - card.box.getDepth() * i), 30, "to_market_deck"));
                    }
                    if (player_now.player_number == 2) {
                        card.animations3D.add(card.doAnimation3D(main_deck_pos, new Vector3(-market_deck_pos.x - card.box.getWidth() * i, market_deck_pos.y, -market_deck_pos.z), 30, "to_market_deck"));
                    }
                }
                if (players_count == 4) {
                    if (player_now.player_number == 0) {
                        card.animations3D.add(card.doAnimation3D(main_deck_pos, new Vector3(market_deck_pos.x + card.box.getWidth() * i, market_deck_pos.y, market_deck_pos.z), 30, "to_market_deck"));
                    }
                    if (player_now.player_number == 1) {
                        card.animations3D.add(card.doAnimation3D(main_deck_pos, new Vector3(-market_deck_pos.x, market_deck_pos.y, market_deck_pos.z - card.box.getDepth() * i), 30, "to_market_deck"));
                    }
                    if (player_now.player_number == 2) {
                        card.animations3D.add(card.doAnimation3D(main_deck_pos, new Vector3(market_deck_pos.x + card.box.getWidth() * i, market_deck_pos.y, -market_deck_pos.z), 30, "to_market_deck"));
                    }
                    if (player_now.player_number == 3) {
                        card.animations3D.add(card.doAnimation3D(main_deck_pos, new Vector3(market_deck_pos.x, market_deck_pos.y, -market_deck_pos.z + card.box.getDepth() * i), 30, "to_market_deck"));
                    }
                }
            }
        }
    }

    public static void turn_ended() {
        turn_end_button_pressed = true;
        for (Card card : player_now.hand) {
            card.convertTo3D(player_now.camera.position, player_now.trash_pos);
        }
        for (Card card : player_now.on_table_cards) {
            card.animations3D.add(card.doAnimation3D(card.update_pos(), player_now.trash_pos, 60, "to_trash_end"));
        }
        turns_lasts++;
        if (main_deck.isEmpty() && market_deck.size() < 5) {
            GameController.state = GameState.END;
        }
    }

    public static void turn_started() {
        turn_end_button_pressed = false;
        if (players_count != 0) {
            prev_player = player_now;
            player_now = players[turns_lasts % players_count];
        }
        player_now.player_init();
        GameController.state = GameState.CHANGE_PLAYER;
        if (player_now.hand.isEmpty()) player_now.getHand();
        else {
            player_now.refresh_hands_positions();
            for (Card card : player_now.hand) {
                if (!CanTouch.renderable_2d.contains(card)) CanTouch.renderable_2d.add(card);
                if (!CanTouch.sprite_collisions.contains(card))
                    CanTouch.sprite_collisions.add(card);
            }
        }
    }
}
