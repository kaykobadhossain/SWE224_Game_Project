package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Asteroids;
import com.mygdx.game.entities.Bullets;

import java.util.ArrayList;
import java.util.Random;

public class MainGameScreen implements Screen {
    public static final float SPEED = 200;

    public static final float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;
    public static final float ROLL_TIMER_SWITCH_TIME =0.15f;
    public static final float SHOOT_WAIT_TIME=0.3f;
    public static final float MIN_ASTEROIDS_SPAWN_TIME=0.3f;
    public static final float MAX_ASTEROIDS_SPAWN_TIME=0.6f;



    Animation[] rolls;
    float x;
    float y;
    int roll;
    float stateTime;
    float rollTimer;
    float shootTimer;
    float asteroidsSpawnTimer;

    Random random;
    MyGdxGame game;

    ArrayList<Bullets>bullets;
    ArrayList<Asteroids>asteroids;


    public MainGameScreen (MyGdxGame game) {
        this.game = game;
        y = 15;
        x = (float) MyGdxGame.Width / 2 - (float) SHIP_WIDTH / 2;

        bullets=new ArrayList<Bullets>();
        asteroids=new ArrayList<Asteroids>();
        random=new Random();

        asteroidsSpawnTimer = random.nextFloat()* (MAX_ASTEROIDS_SPAWN_TIME- MIN_ASTEROIDS_SPAWN_TIME)+MIN_ASTEROIDS_SPAWN_TIME;

        shootTimer=0;
        roll = 2;
        rollTimer=0;

        rolls = new Animation[5];



        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);

        rolls[0] = new Animation( SHIP_ANIMATION_SPEED, rollSpriteSheet[2]);
        rolls[1] = new Animation( SHIP_ANIMATION_SPEED, rollSpriteSheet[1]);
        rolls[2] = new Animation( SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);
        rolls[3] = new Animation( SHIP_ANIMATION_SPEED, rollSpriteSheet[3]);
        rolls[4] = new Animation( SHIP_ANIMATION_SPEED, rollSpriteSheet[4]);
    }
    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        shootTimer+=delta;

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer>=SHOOT_WAIT_TIME){
            shootTimer=0;

            int offset=4;
            if(roll ==1 || roll ==3)
            {
                offset=8;
            }
            if(roll ==0 || roll ==4)
            {
                offset=16;
            }

            bullets.add(new Bullets(x+offset));
            bullets.add(new Bullets(x+SHIP_WIDTH-offset));

        }
        asteroidsSpawnTimer-=delta;

        if(asteroidsSpawnTimer <= 0)
        {
            asteroidsSpawnTimer=random.nextFloat()* (MAX_ASTEROIDS_SPAWN_TIME- MIN_ASTEROIDS_SPAWN_TIME)+MIN_ASTEROIDS_SPAWN_TIME;
            asteroids.add(new Asteroids(random.nextInt(Gdx.graphics.getWidth()-Asteroids.WIDTH)));

        }

        ArrayList<Asteroids>asteriosToRemove = new ArrayList<Asteroids>();
        for(Asteroids asteroid : asteroids)
        {
            asteroid.update(delta);
            if(asteroid.remove) {
                asteriosToRemove.add(asteroid);
            }

        }


        ArrayList<Bullets>bulletsToRemove = new ArrayList<Bullets>();
        for(Bullets bullet : bullets)
        {
            bullet.update(delta);
            if(bullet.remove)
            {
                bulletsToRemove.add(bullet);
            }
        }





        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            x-=SPEED*Gdx.graphics.getDeltaTime();

            if(x<0){
                x=0;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT) && roll>0 ){
                rollTimer=0;
                roll--;
            }


            rollTimer-=Gdx.graphics.getDeltaTime();
            if(Math.abs(rollTimer)>ROLL_TIMER_SWITCH_TIME && roll>0)
            {
                rollTimer-=ROLL_TIMER_SWITCH_TIME;
                roll--;
            }

        }else {
            if(roll<2){
                rollTimer+=Gdx.graphics.getDeltaTime();
                if(Math.abs(rollTimer)>ROLL_TIMER_SWITCH_TIME && roll<4)
                {
                    rollTimer-=ROLL_TIMER_SWITCH_TIME;
                    roll++;
                }

            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            x+=SPEED*Gdx.graphics.getDeltaTime();
            if(x+SHIP_WIDTH >Gdx.graphics.getWidth())
                x=Gdx.graphics.getWidth()-SHIP_WIDTH;

            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.LEFT) && roll>0 ){
                rollTimer=0;
                roll--;
            }

            rollTimer+=Gdx.graphics.getDeltaTime();
            if(Math.abs(rollTimer)>ROLL_TIMER_SWITCH_TIME && roll<4)
            {
                rollTimer-=ROLL_TIMER_SWITCH_TIME;
                roll++;

            }

        } else{
            if(roll>2){
                rollTimer-=Gdx.graphics.getDeltaTime();
                if(Math.abs(rollTimer)>ROLL_TIMER_SWITCH_TIME && roll >0)
                {
                    rollTimer-=ROLL_TIMER_SWITCH_TIME;
                    roll--;

                }
            }
        }


        for(Bullets bullet :bullets)
        {
            for(Asteroids asteroid :asteroids)
            {
                if(bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect()))
                {
                    bulletsToRemove.add(bullet);
                    asteriosToRemove.add(asteroid);


                }
            }
        }
        asteroids.removeAll(asteriosToRemove);
        bullets.removeAll(bulletsToRemove);



        stateTime +=delta;
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        for(Bullets bullet :bullets)
        {
            bullet.render(game.batch);

        }
        for(Asteroids asteroid :asteroids)
        {
            asteroid.render(game.batch);

        }

        game.batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);
        game.batch.end();

    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
