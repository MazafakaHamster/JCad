package com.rebel.cad.shape;

/**
 * Created by Slava on 27.12.2015.
 */
public class Torus {
    public float GetX(float R1,float R2,float u, float v)
    {
        return ((float)((R1 + R2 * (Math.cos(u))) * Math.abs(Math.cos(v))));

    }
    public float GetY(float R1, float R2,float u, float v)
    {
        return ((float)((R1 + R2 *  (Math.cos(u))) * Math.abs(Math.sin(v))));

    }
    public float GetZ(float R2, float u)
    {
        return ((float)(R2 * Math.sin(u)));
    }
}
