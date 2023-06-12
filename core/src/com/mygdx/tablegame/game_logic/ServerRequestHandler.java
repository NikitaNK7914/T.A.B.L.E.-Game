package com.mygdx.tablegame.game_logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.TreeMap;
import java.util.Vector;

import tech.gusavila92.websocketclient.WebSocketClient;
//класс для сетевой игры, пока вразработки и нигде в игре не используется
public class ServerRequestHandler {
    private WebSocketClient webSocketClient;
    private void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://192.0.0.1:8001/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
               /* webSocketClient.send("{" +
                        "\"session\":" + generate_id_session() + ", " +
                        "\"request\":\"CONNECT\"," +
                        "\"id\": -1" +
                        "}"
                );*/
            }

            @Override
            public void onTextReceived(String s) {
                try {
                    JSONResponse jsonResponse = new JSONResponse(new JSONObject(s));
                    if (jsonResponse.type_request.equals("CONNECT")) {
                        if (jsonResponse.mess == 0) {
                            // not connected message
                        } else {
                            // set global id player - mess value
                        }
                    } else if (jsonResponse.type_request.equals("ATTACK")) {
                        // check armor card and send ARMOR request
                    } else if (jsonResponse.type_request.equals("POST")) {
                        // update decks and stats
                    } else if (jsonResponse.type_request.equals("ARMOR")) {
                        // logic turn
                    } else if (jsonResponse.type_request.equals("START")) {
                        // start session
                    }
                    /*
                    if CONNECT => connect (mess - id player)/not connect
                    if ATTACK => CHECK and SEND ARMOR
                    if POST update
                    if ARMOR => logic
                    if START => start session
                     */
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
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
                /*webSocketClient.send("{" +
                        "\"session\":" + get_id_session() + ", " +
                        "\"request\":\"DISCONNECT\"," +
                        "\"id\": -1" +
                        "}"
                );*/
            }
        };

        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    /*public void sendPostRequest(T deck_1, T deck_2, ...,T state_player_1, T state_player_2, ...) {
        // PSEUDOCODE
        String decks = "";
        for (deck:
             all_decks) {
            decks += "\"NAME_DECK\":[";
            for (card:
                 deck) {
                decks += Integer.to_String(id_card) + ",";
            }
            decks += "],";
        }
        String state = "";
        // state - health and power
        for (state:
             all_states) {
            state += "\"ID_USER\":{";
            state += "\"health\":" + Integer.to_String(state.health) + ",";
            state += "\"power\":" + Integer.to_String(state.power);
            decks += "],";
        }
        webSocketClient.send("{" +
                "\"session\":" + get_id_session() + ", " +
                "\"request\":\"POST\"," +
                "\"id\"" + Integer.toString(get_player_id()) + "\"," +
                "\"data\":" +
                "\"move\":" + whose_turn() + "," +
                "\"decks\":{" + decks + "}," +
                "\"statistic\":{" + state + "}," +
                "}");
    }*/

    public void sendStartRequest() {
        /*webSocketClient.send("{" +
                "\"session\":" + get_id_session() + ", " +
                "\"request\":\"START\"," +
                "\"id\"" + Integer.toString(get_player_id()) + "\"," +
                "}"
        );*/
    }

    public void sendAttackRequest(int card, int player_number) {
        /*webSocketClient.send("{" +
                "\"session\":" + get_id_session() + ", " +
                "\"request\":\"ATTACK\"," +
                "\"id\"" + Integer.toString(get_player_id()) + "\"," +
                "\"card\":\"" + Integer.toString(card) + "\"," +
                "\"id_target\":\"" + Integer.toString(player_number) + "\"," +
                "\"id_player\":\"" + Integer.toString(get_player_id()) + "\"" +
                "}"
        );*/
    }

    public void sendArmorRequest(int card, int player_number) {
        /*webSocketClient.send("{" +
                "\"session\":" + get_id_session() + ", " +
                "\"request\":\"ARMOR\"," +
                "\"id\"" + Integer.toString(get_player_id()) + "\"," +
                "\"card\":\"" + Integer.toString(card) + "\"," +
                "\"id_target\":\"" + Integer.toString(player_number) + "\"," +
                "\"id_player\":\"" + Integer.toString(get_player_id()) + "\"" +
                "}"
        );*/
    }
    private class JSONAttack {
        int card, id_target, id_player_attacker;

        JSONAttack(JSONObject jsonObject){
            try {
                this.card = jsonObject.getInt("card");
                this.id_target = jsonObject.getInt("id_target");
                this.id_player_attacker = jsonObject.getInt("id_player_attacker");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private  class JSONDeck {
        public Vector<Integer> deck;


        JSONDeck(JSONArray jsonArray) {
            deck = new Vector<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); ++i) {
                try {
                    deck.set(i, jsonArray.getInt(i));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private  class JSONPost {
        int move;
        TreeMap<String, JSONDeck> decks = new TreeMap<>();
        TreeMap<String, JSONState> states = new TreeMap<>();


        JSONPost(JSONObject jsonObject) {
            try {
                this.move = jsonObject.getInt("move");
                JSONObject decks = jsonObject.getJSONObject("decks");
                // CHECK COUNT PLAYERS OR SEND ALWAYS 4 deck players
                this.decks.put("deck_player_1", new JSONDeck(decks.getJSONArray("deck_player_1")));
                this.decks.put("deck_player_2", new JSONDeck(decks.getJSONArray("deck_player_2")));
                this.decks.put("deck_player_3", new JSONDeck(decks.getJSONArray("deck_player_3")));
                this.decks.put("deck_player_4", new JSONDeck(decks.getJSONArray("deck_player_4")));
                this.decks.put("deck_all", new JSONDeck(decks.getJSONArray("deck_all")));
                this.decks.put("deck_wand", new JSONDeck(decks.getJSONArray("deck_wand")));
                this.decks.put("deck_dead", new JSONDeck(decks.getJSONArray("deck_dead")));
                JSONObject states = jsonObject.getJSONObject("statistic");
                this.states.put("0", new JSONState(states.getJSONObject("0")));
                this.states.put("1", new JSONState(states.getJSONObject("1")));
                this.states.put("2", new JSONState(states.getJSONObject("2")));
                this.states.put("3", new JSONState(states.getJSONObject("3")));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class JSONResponse {
        long session;
        String type_request;

        int mess;

        JSONPost jsonPost;

        JSONAttack jsonAttack;

        JSONResponse(JSONObject jsonObject) {
            try {
                this.session = jsonObject.getLong("session");
                this.type_request = jsonObject.getString("request");
                switch (this.type_request) {
                    case "POST":
                        jsonPost = new JSONPost(jsonObject.getJSONObject("data"));
                        break;
                    case "ATTACK":

                    case "ARMOR":
                        jsonAttack = new JSONAttack(jsonObject.getJSONObject("data"));
                        break;

                    case "CONNECT":
                    case "CREATE":
                        mess = jsonObject.getInt("data");
                        break;
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private class JSONState {
        public int health, power;


        JSONState(JSONObject jsonObject) {
            try {
                health = jsonObject.getInt("health");
                power = jsonObject.getInt("power");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
