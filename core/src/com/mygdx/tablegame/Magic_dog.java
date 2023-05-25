package com.mygdx.tablegame;

public class Magic_dog extends Card{
    public Magic_dog() {
        super(3);
        power_points=0;
        win_points=1;
        cost=2;
    }

    @Override
    public void played() {
        Server.attack(Server.player_now,2);
        Server.player_now.getCard();
    }
}
