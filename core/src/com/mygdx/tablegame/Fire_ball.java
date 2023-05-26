package com.mygdx.tablegame;

import java.util.ArrayList;

public class Fire_ball extends Card{
    public Fire_ball(){
        super(4);
        power_points=3;
        win_points=2;
        cost=3;
    }

    @Override
    public void played() {
        Server.player_now.power_points+=power_points;
        GameScreen.player_UI_names[Server.player_now.player_number]=Server.player_now.name+"`s power points  : "+Server.player_now.power_points;
        ArrayList<Player> targets = new ArrayList<>();
        for (int i = 0; i < Server.players_count; i++) {
            if (Server.players[i] != Server.player_now) {
                targets.add(Server.players[i]);
            }
        }
        Server.player_now.power_points+=power_points;
        GameScreen.player_UI_names[Server.player_now.player_number]=Server.player_now.name+"`s power points  : "+Server.player_now.power_points;
        if(targets.size()==1) targets.get(0).refresh_health(-3);
        else {GameScreen.attack_target_selection(targets, -3);GameController.state=GameState.SELECT;}
    }
}
