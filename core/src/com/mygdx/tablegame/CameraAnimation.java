package com.mygdx.tablegame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class CameraAnimation {
    long start_time;
    final Vector3 startPos;
    final Vector3 endPos;
    float duration;
    float distanceX;
    float distanceY;
    float distanceZ;
    final Vector3 look_at_pos;
    final Vector3 start_look_pos;
    float delta_lookX;
    float delta_lookY;
    float delta_lookZ;
    boolean is3D;
    String id;
    public CameraAnimation(final Vector3 start, final Vector3 end, float millis_time, final Vector3 look_at_pos, String id){
        is3D = true;
        start_time = -1;
        startPos = start;
        endPos = end;
        this.look_at_pos=look_at_pos;
        duration = millis_time;
        distanceX = endPos.x - startPos.x;
        distanceY = endPos.y - startPos.y;
        distanceZ = endPos.z - startPos.z;
        Vector3 tmp=new Vector3(Server.player_now.camera.direction);
        tmp.setLength((float) Math.sqrt(Math.pow(look_at_pos.x-Server.player_now.camera.position.x,2)+Math.pow(look_at_pos.y-Server.player_now.camera.position.y,2)+Math.pow(look_at_pos.z-Server.player_now.camera.position.z,2)));
        start_look_pos=new Vector3(Server.player_now.camera.position.x+tmp.x,Server.player_now.camera.position.y+tmp.y,Server.player_now.camera.position.z+tmp.z);
        delta_lookX=look_at_pos.x-start_look_pos.x;
        delta_lookY=look_at_pos.y-start_look_pos.y;
        delta_lookZ=look_at_pos.z-start_look_pos.z;
        this.id = id;
    }
}
