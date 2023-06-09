package com.mygdx.tablegame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class Touchable {
    protected BoundingBox hitBox;
    protected long prevTouchTime=TimeUtils.millis();
    protected Sprite sprite;
    public BoundingBox getHitBox() {return  hitBox;}
    public Rectangle getSpriteHitBox(){return sprite.getBoundingRectangle();}
    public void touched(){};
    public void doubleTouched(){};
    public void sprite_touched(){};
    public void sprite_doubleTouched(){};
    public void updateTime(){
        prevTouchTime= TimeUtils.millis();
    }
}