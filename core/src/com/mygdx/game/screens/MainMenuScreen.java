package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class MainMenuScreen implements Screen {

    public static final int Exit_Button_Width=300;
    public static final int Exit_Button_Height=150;
    public static final int Play_Button_Width=330;
    public static final int Play_Button_Height=150;
    public static final int Exit_Button_Y =125;
    public static final int Play_Button_Y=200;


    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;

    MyGdxGame game;
    public MainMenuScreen (MyGdxGame game){
        this.game=game;
        playButtonActive=new Texture("play_button_active.png");
        playButtonInactive=new Texture("play_button_inactive.png");
        exitButtonActive=new Texture("exit_button_active.png");
        exitButtonInactive=new Texture("exit_button_inactive.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        int x=MyGdxGame.Width/2-Exit_Button_Width/2;
        if(Gdx.input.getX()>x && Gdx.input.getX()<x+Exit_Button_Width && MyGdxGame.Height-Gdx.input.getY()>Exit_Button_Y && MyGdxGame.Height-Gdx.input.getY()<Exit_Button_Y+Exit_Button_Height)
        {
            game.batch.draw(exitButtonActive,x,Exit_Button_Y,Exit_Button_Width,Exit_Button_Height);

        }
        else{
            game.batch.draw(exitButtonInactive,x,Exit_Button_Y,Exit_Button_Width,Exit_Button_Height);

        }
        x=MyGdxGame.Width/2-Play_Button_Width/2;
        if(Gdx.input.getX()>x && Gdx.input.getX()<x+Play_Button_Width && MyGdxGame.Height-Gdx.input.getY()>Play_Button_Y && MyGdxGame.Height-Gdx.input.getY()<Play_Button_Y+Play_Button_Height)
        {
            game.batch.draw(playButtonActive,x,Play_Button_Y,Play_Button_Width,Play_Button_Height);

        }
        else{
            game.batch.draw(playButtonInactive,x,Play_Button_Y,Play_Button_Width,Play_Button_Height);

        }
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
