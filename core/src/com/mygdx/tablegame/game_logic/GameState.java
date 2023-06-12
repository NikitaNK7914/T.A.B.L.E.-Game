package com.mygdx.tablegame.game_logic;
// класс, содержащий названия игровых состояний, которые используются для удобной смены того, что отрисовывается в данный момент в методе рендера
public enum GameState {
    RUN,
    SELECT,
    CHANGE_PLAYER,
    START,
    CREATING,
    CREATING_ONLINE,
    CARD_LOOKING,
    END;
}
