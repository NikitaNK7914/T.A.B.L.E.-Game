package com.mygdx.tablegame;

import com.badlogic.gdx.math.Vector3;

public class Animation {
    long start_time;
    Vector3 startPos;
    Vector3 endPos;
    float duration;
    float distanceX;
    float distanceY;
    float distanceZ;
    Vector3 start_rotation_angles;
    Vector3 end_rotation_angles;
    float delta_angleX;
    float delta_angleY;
    float delta_angleZ;
    float prevRotX=0;
    float prevRotY=0;
    float prevRotZ=0;
    boolean is3D;
    String id;

    public Animation(Vector3 start, Vector3 end, float millis_time, String id) {
        is3D = true;
        start_time = -1;
        startPos = start;
        endPos = end;
        duration = millis_time;
        distanceX = endPos.x - startPos.x;
        distanceY = endPos.y - startPos.y;
        distanceZ = endPos.z - startPos.z;
        this.id = id;
    }

    public Animation(Vector3 start, Vector3 end, float millis_time,Vector3 startR_angles,Vector3 endR_angles, String id) {
        is3D = true;
        start_time = -1;
        startPos = start;
        endPos = end;
        duration = millis_time;
        start_rotation_angles=startR_angles;
        end_rotation_angles=endR_angles;
        delta_angleX=end_rotation_angles.x-start_rotation_angles.x;
        delta_angleY=end_rotation_angles.y-start_rotation_angles.y;
        delta_angleZ=end_rotation_angles.z-start_rotation_angles.z;
        distanceX = endPos.x - startPos.x;
        distanceY = endPos.y - startPos.y;
        distanceZ = endPos.z - startPos.z;
        this.id = id;
    }


    public Animation() {
    }
}
