package com.mygdx.game.tools;

public class CollisionRect {
    float x,y;
    int height ,width;
    public CollisionRect(float x,float y,int width,int height)
    {
        this.height=height;
        this.width=width;
        this.x=x;
        this.y=y;

    }
    public void move(float x, float y)
    {
        this.x=x;
        this.y=y;
    }
    public boolean collidesWith (CollisionRect rect)
    {
        return x<rect.x + rect.width && y<rect.y + rect.height && x+width>rect.x && y+height>rect.y;

    }

}
