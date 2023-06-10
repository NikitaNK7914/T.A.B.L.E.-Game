package com.mygdx.tablegame;

import com.badlogic.gdx.math.Vector3;

public class Animation {
    public long start_time;
    public Vector3 startPos;
    public Vector3 endPos;
    public float duration;
    public float distanceX;
    public float distanceY;
    public float distanceZ;
    public Vector3 start_rotation_angles;
    public Vector3 end_rotation_angles;
    public float delta_angleX;
    public float delta_angleY;
    public float delta_angleZ;
    public float prevRotX = 0;
    public float prevRotY = 0;
    public float prevRotZ = 0;
    public boolean is3D;
    public String id;

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

    public Animation(Vector3 start, Vector3 end, float millis_time, Vector3 startR_angles, Vector3 endR_angles, String id) {
        is3D = true;
        start_time = -1;
        startPos = start;
        endPos = end;
        duration = millis_time;
        start_rotation_angles = startR_angles;
        end_rotation_angles = endR_angles;
        delta_angleX = end_rotation_angles.x - start_rotation_angles.x;
        delta_angleY = end_rotation_angles.y - start_rotation_angles.y;
        delta_angleZ = end_rotation_angles.z - start_rotation_angles.z;
        distanceX = endPos.x - startPos.x;
        distanceY = endPos.y - startPos.y;
        distanceZ = endPos.z - startPos.z;
        this.id = id;
    }

    public Animation() {
    }
}
