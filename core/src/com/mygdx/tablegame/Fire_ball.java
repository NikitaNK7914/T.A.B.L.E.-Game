package com.mygdx.tablegame;

public class Fire_ball extends Card{
    public Fire_ball(){
        super(4);
        power_points=3;
        win_points=2;
        cost=5;
    }

    @Override
    public void played() {
        Server.player_now.power_points+=power_points;
        GameScreen.player_UI_names[Server.player_now.player_number]=Server.player_now.name+"`s power points  : "+Server.player_now.power_points;
        for(int i=0;i<Server.players_count;i++){
            if(Server.players[i]!=Server.player_now){Server.attack(Server.players[i],-3);}
        }
    }
}
