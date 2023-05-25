package com.mygdx.tablegame;

public class Pshik extends Card {
    public Pshik() {
        super(1);
        win_points=0;
        power_points=0;
        cost=0;
    }

    @Override
    public void played() {
        /*ArrayList<Player> targets = new ArrayList<>();
        for (int i = 0; i < Server.players_count; i++) {
            if (Server.players[i] != Server.player_now) {
                targets.add(Server.players[i]);
            }
        }
        Server.player_now.power_points+=power_points;
        GameScreen.player_UI_names[Server.player_now.player_number]=Server.player_now.name+"`s power points  : "+Server.player_now.power_points;
        if(targets.size()==1) targets.get(0).refresh_health(-5);
        else GameScreen.attack_target_selection(targets, -5);*/
        //Server.player_now.getCard();
    }
}
