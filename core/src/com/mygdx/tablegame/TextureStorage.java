package com.mygdx.tablegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;

import java.util.ArrayList;

public class TextureStorage {
    TextureAttribute simple3D_texture1;
    TextureAttribute selected3D_texture1;
    Texture simple_2Dtexture1;
    Texture selected_2Dtexture1;
    TextureAttribute simple3D_texture2;
    TextureAttribute selected3D_texture2;
    Texture simple_2Dtexture2;
    Texture selected_2Dtexture2;
    TextureAttribute simple3D_texture3;
    TextureAttribute selected3D_texture3;
    Texture simple_2Dtexture3;
    Texture selected_2Dtexture3;
    TextureAttribute simple3D_texture4;
    TextureAttribute selected3D_texture4;
    Texture simple_2Dtexture4;
    Texture selected_2Dtexture4;
    TextureAttribute simple3D_texture5;
    TextureAttribute selected3D_texture5;
    Texture simple_2Dtexture5;
    Texture selected_2Dtexture5;
    TextureAttribute simple3D_texture6;
    TextureAttribute selected3D_texture6;
    Texture simple_2Dtexture6;
    Texture selected_2Dtexture6;
    Texture heart_UI;
    Texture half_heart_UI;
    static TextureAttribute[][] textures3d = new TextureAttribute[6][2];
    static Texture[][] textures2d = new Texture[6][2];
    static Texture[][] textures_UI=new Texture[2][];

    public TextureStorage() {
        ArrayList<TextureAttribute> temp3d = new ArrayList<>();
        ArrayList<Texture> temp2d = new ArrayList<>();
        simple3D_texture1 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("card_texture1.png"));
        temp3d.add(simple3D_texture1);
        selected3D_texture1 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("card_texture1.png"));
        temp3d.add(selected3D_texture1);
        simple_2Dtexture1 = new Texture(Gdx.files.internal("card_texture.PNG"));
        temp2d.add(simple_2Dtexture1);
        selected_2Dtexture1 = new Texture(Gdx.files.internal("card_texture_selected.PNG"));
        temp2d.add(selected_2Dtexture1);
        simple3D_texture2 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("pshick3d.png"));
        temp3d.add(simple3D_texture2);
        selected3D_texture2 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("pshick3d_selected.png"));
        temp3d.add(selected3D_texture2);
        simple_2Dtexture2 = new Texture(Gdx.files.internal("pshick.png"));
        temp2d.add(simple_2Dtexture2);
        selected_2Dtexture2 = new Texture(Gdx.files.internal("pshick_selected.png"));
        temp2d.add(selected_2Dtexture2);
        simple3D_texture3 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("znak3D.png"));
        temp3d.add(simple3D_texture3);
        selected3D_texture3 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("znak3D_selected.png"));
        temp3d.add(selected3D_texture3);
        simple_2Dtexture3 = new Texture(Gdx.files.internal("znak.png"));
        temp2d.add(simple_2Dtexture3);
        selected_2Dtexture3= new Texture(Gdx.files.internal("znak_selected.png"));
        temp2d.add(selected_2Dtexture3);
        simple3D_texture4 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("abrakadabrador3d.png"));
        temp3d.add(simple3D_texture4);
        selected3D_texture4 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("abrakadabrador3d_selected.png"));
        temp3d.add(selected3D_texture4);
        simple_2Dtexture4 = new Texture(Gdx.files.internal("abrakadabrador.png"));
        temp2d.add(simple_2Dtexture4);
        selected_2Dtexture4 = new Texture(Gdx.files.internal("abrakadabrador_selected.png"));
        temp2d.add(selected_2Dtexture4);
        simple3D_texture5 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("fireball3d.png"));
        temp3d.add(simple3D_texture5);
        selected3D_texture5 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("fireball3d_selected.png"));
        temp3d.add(selected3D_texture5);
        simple_2Dtexture5 = new Texture(Gdx.files.internal("fireball.png"));
        temp2d.add(simple_2Dtexture5);
        selected_2Dtexture5 = new Texture(Gdx.files.internal("fireball_selected.png"));
        temp2d.add(selected_2Dtexture5);
        simple3D_texture6 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("Amulet3d.png"));
        temp3d.add(simple3D_texture6);
        selected3D_texture6 = new TextureAttribute(TextureAttribute.Diffuse, new Texture("Amulet3d_selected.png"));
        temp3d.add(selected3D_texture6);
        simple_2Dtexture6 = new Texture(Gdx.files.internal("Amulet.png"));
        temp2d.add(simple_2Dtexture6);
        selected_2Dtexture6 = new Texture(Gdx.files.internal("Amulet_selected.png"));
        temp2d.add(selected_2Dtexture6);
        heart_UI=new Texture(Gdx.files.internal("heart.png"));
        half_heart_UI=new Texture(Gdx.files.internal("half.png"));
        Texture armor=new Texture(Gdx.files.internal("shield.png"));
        for (int i = 0; i < textures3d.length; i++) {
            for (int j = 0; j < textures3d[i].length; j++) {
                textures3d[i][j] = temp3d.get(0);
                temp3d.remove(0);
            }
        }
        for (int i = 0; i < textures2d.length; i++) {
            for (int j = 0; j < textures2d[i].length; j++) {
                textures2d[i][j] = temp2d.get(0);
                temp2d.remove(0);
            }
        }
        textures_UI[0] = new Texture[2];
        textures_UI[0][0]=heart_UI;
        textures_UI[0][1]=half_heart_UI;
        textures_UI[1]=new Texture[1];
        textures_UI[1][0]=armor;
    }
}
