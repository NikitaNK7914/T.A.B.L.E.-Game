package com.mygdx.tablegame;

public class Znak extends Card{
    public Znak() {
        super(2);
        win_points=0;
        power_points=1;
        cost=0;
    }

    @Override
    public void played() {
        Server.player_now.power_points+=power_points;
        GameScreen.player_UI_names[Server.player_now.player_number]=Server.player_now.name+"`s power points  : "+Server.player_now.power_points;
    }
}
