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
    Vector3 tmp_look_at;
    Vector3 camera_pos;
    float XZrotAngle;
    float YrotAngle;
    float prevRotAngleXZ=0;
    float prevRotAngleY=0;
    boolean is3D;
    String id;
    public CameraAnimation(final Vector3 start, final Vector3 end, float millis_time, final Vector3 look_at_pos, String id){
        is3D = true;
        start_time = -1;
        startPos = start;
        endPos = end;
        camera_pos=Server.player_now.camera.position;
        this.look_at_pos=look_at_pos;
        duration = millis_time;
        distanceX = endPos.x - startPos.x;
        distanceY = endPos.y - startPos.y;
        distanceZ = endPos.z - startPos.z;
        Vector3 tmp=new Vector3(Server.player_now.camera.direction);
        tmp.setLength((float) Math.sqrt(Math.pow(look_at_pos.x-Server.player_now.camera.position.x,2)+Math.pow(look_at_pos.y-Server.player_now.camera.position.y,2)+Math.pow(look_at_pos.z-Server.player_now.camera.position.z,2)));
        float b=tmp.len();
        float c=tmp.len();
        Vector3 dotC=new Vector3(Server.player_now.camera.position.x+tmp.x,Server.player_now.camera.position.y+tmp.y,Server.player_now.camera.position.z+tmp.z);
        float a= (float) Math.sqrt((Math.pow(look_at_pos.x-dotC.x,2)+Math.pow(look_at_pos.z-dotC.z,2)));
        XZrotAngle=MathUtils.radiansToDegrees*MathUtils.acos((b*b+c*c-a*a)/(2*b*c));
        tmp.set(look_at_pos.x,tmp.y,look_at_pos.z);
        tmp.setLength(c);
        YrotAngle= MathUtils.radiansToDegrees*MathUtils.acos((float) ((tmp.x*look_at_pos.x+tmp.y*look_at_pos.y+tmp.z*look_at_pos.z)/((Math.sqrt(tmp.x*tmp.x+tmp.y*tmp.y+tmp.z*tmp.z))*(Math.sqrt(look_at_pos.x*look_at_pos.x+look_at_pos.y*look_at_pos.y+look_at_pos.z*look_at_pos.z)))));
        System.out.println(tmp+"  "+look_at_pos);
        float k=(look_at_pos.x-camera_pos.x)/(-1*(look_at_pos.z-camera_pos.z));
        tmp_look_at=new Vector3(1,0,1*k);
        this.id = id;
    }
}
