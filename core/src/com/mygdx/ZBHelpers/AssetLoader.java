package com.mygdx.ZBHelpers;

/**
 * Created by alexandermartinez on 7/17/14.
 */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.geom.Point2D;
import java.util.List;

public class AssetLoader {
    public static int numBirds = 8;
    public static int numbgs = 11;
    public static int numSongs = 22;
    public static int flappyBird = numBirds-1;
    public static Texture texture, logoTexture, invisabletexture, continueSign, backSign;
    public static Texture[] up = new Texture[numBirds], down = new Texture[numBirds], mid = new Texture[numBirds], backgrounds = new Texture[numbgs-1];
//    public static String [] birdnames = {"ernesto/down.png", "ernesto/mid.png", "ernesto/up.png",
//            "ricardo/downric.png", "ricardo/midric.png", "ricardo/upric.png",
//            "steven/down.png","steven/mid.png","steven/up.png",
//            "yongxing/yongdown.png","yongxing/yongmid.png","yongxing/yongup.png",
//            "marja/down.png", "marja/mid.png", "marja/up.png"},
    public static String [] birdnames = {
        "ernesto/down.png", "ernesto/mid.png", "ernesto/up.png",
        "evan/down.png", "evan/mid.png", "evan/up.png",
        "steven/down.png","steven/mid.png","steven/up.png",
        "sassy/down.png","sassy/mid.png","sassy/up.png",
        "louis/down.png","louis/mid.png","louis/up.png",
        "fernando/down.png","fernando/mid.png","fernando/up.png",
        "lisa/down.png","lisa/mid.png","lisa/up.png"},
    themeNames = {"super_z_theme.mp3","halo.mp3","forest_maze.mp3","forest_maze_acappella.mp3",
            "wily.mp3","wily_acappella.mp3", "castlevania_acoustic.mp3", "castlevania_acappella.mp3",
            "dire_acappella.mp3", "dire_dire_docks.mp3","derezzed.mp3", "the_grid.mp3",
            "kh_ddd_dearly_beloved.mp3","zanarkand.mp3", "korobelniki.mp3", "tetris_acappella.mp3",
            "mgs4.mp3", "nier.mp3","skyrim.mp3","sultans_of_swing.mp3",
            "gourmet_race.mp3","gourmet_race_brawl.mp3"},
    backgroundNames = {"fire.png","forest.jpg","factory.jpg","castle.jpg","ocean.jpg",
            "tron.png","ruins.jpg","tetris.jpg","dark.png","skyrim.jpg" };

    public static String soundHeader = "data/sound/", backgroundHeader = "data/backgrounds/";

//    public static int[][] birdSizeXY = {{80,64},{211,219},{169,224},{211,259},{175,187}};
    public static int[][] birdSizeXY = {{80,64},{164,199},{150,228},{216,215},{197,195},{164,205},{149,172}};

    public static TextureRegion[] birdDown = new TextureRegion[numBirds], bird = new TextureRegion[numBirds], birdUp = new TextureRegion[numBirds], bg = new TextureRegion[numbgs];
    public static TextureRegion logo, invisible, zbLogo, grass, skullUp, skullDown, bar, playButtonUp, playButtonDown,
            ready, gameOver, highScore, scoreboard, star, noStar, retry, cont, back;
    public static Animation[] birdAnimation = new Animation[numBirds];
    public static Sound dead, coin, ernesto_scream, flap,  warp, select, complete;
    public static Music[] themes = new Music[numSongs];
    public static BitmapFont font, shadow, whiteFont, smallShadow;
    private static Preferences prefs;

    public static void load() {

        logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        logo = new TextureRegion(logoTexture, 0, 0, 477, 197);

        continueSign  = new Texture(Gdx.files.internal("data/go.png"));
        continueSign.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        backSign  = new Texture(Gdx.files.internal("data/back.png"));
        backSign.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        for (int i = 0; i < numbgs-1; i++) {
            backgrounds[i] = new Texture(Gdx.files.internal(backgroundHeader+backgroundNames[i]));
            backgrounds[i].setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        }

        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        invisabletexture = new Texture(Gdx.files.internal("data/invisiblebutton.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        invisible = new TextureRegion(invisabletexture, 0, 0, 29, 17);

        playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
        playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        ready = new TextureRegion(texture, 59, 83, 34, 7);
        ready.flip(false, true);

        retry = new TextureRegion(texture, 59, 110, 33, 7);
        retry.flip(false, true);

        cont = new TextureRegion(continueSign, 0, 0, continueSign.getWidth(), continueSign.getHeight());
        cont.flip(false, true);

        back = new TextureRegion(backSign, 0, 0, backSign.getWidth(), backSign.getHeight());
        back.flip(false, true);

        gameOver = new TextureRegion(texture, 59, 92, 46, 7);
        gameOver.flip(false, true);

        scoreboard = new TextureRegion(texture, 111, 83, 97, 37);
        scoreboard.flip(false, true);

        star = new TextureRegion(texture, 152, 70, 10, 10);
        noStar = new TextureRegion(texture, 165, 70, 10, 10);

        star.flip(false, true);
        noStar.flip(false, true);

        highScore = new TextureRegion(texture, 59, 101, 48, 7);
        highScore.flip(false, true);

        zbLogo = new TextureRegion(texture, 0, 55, 135, 25);
        zbLogo.flip(false, true);

        for (int i = 0; i < numbgs-1; i++) {
            bg[i] = new TextureRegion(backgrounds[i], 0, 0, backgrounds[i].getWidth(), backgrounds[i].getHeight());
            bg[i].flip(false, true);
        }

        bg[numbgs-1] = new TextureRegion(texture, 0, 0, 136, 43);
        bg[numbgs-1].flip(false, true);

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        birdDown[numBirds-1] = new TextureRegion(texture, 136, 0, 17, 12);
        birdDown[numBirds-1].flip(false, true);

        bird[numBirds-1] = new TextureRegion(texture, 153, 0, 17, 12);
        bird[numBirds-1].flip(false, true);

        birdUp[numBirds-1] = new TextureRegion(texture, 170, 0, 17, 12);
        birdUp[numBirds-1].flip(false, true);

        TextureRegion[] birds = {birdDown[numBirds-1], bird[numBirds-1], birdUp[numBirds-1] };
        birdAnimation[numBirds-1] = new Animation(0.06f, birds);
        birdAnimation[numBirds-1].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        for (int i = 0; i < numBirds-1; i++) {
            down[i] = new Texture(Gdx.files.internal("data/birds/"+ birdnames[i*3]));
            texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            mid[i] = new Texture(Gdx.files.internal("data/birds/"+ birdnames[i*3+1]));
            texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            up[i] = new Texture(Gdx.files.internal("data/birds/"+ birdnames[i*3+2]));
            texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

            birdDown[i] = new TextureRegion(down[i], 0, 0, birdSizeXY[i][0], birdSizeXY[i][1]);
            birdDown[i].flip(false, true);

            bird[i] = new TextureRegion(mid[i], 0, 0, birdSizeXY[i][0], birdSizeXY[i][1]);
            bird[i].flip(false, true);

            birdUp[i] = new TextureRegion(up[i], 0, 0, birdSizeXY[i][0], birdSizeXY[i][1]);
            birdUp[i].flip(false, true);

            TextureRegion[] birdz = {birdDown[i], bird[i], birdUp[i] };
            birdAnimation[i] = new Animation(0.06f, birdz);
            birdAnimation[i].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        }

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
        // Create by flipping existing skullUp
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);

        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);

        complete = Gdx.audio.newSound(Gdx.files.internal(soundHeader+"complete.mp3"));
        select = Gdx.audio.newSound(Gdx.files.internal(soundHeader+"select.mp3"));
        dead = Gdx.audio.newSound(Gdx.files.internal(soundHeader+"dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal(soundHeader+"flap.mp3"));
        coin = Gdx.audio.newSound(Gdx.files.internal(soundHeader+"coin.wav"));
        warp = Gdx.audio.newSound(Gdx.files.internal(soundHeader+"warp.mp3"));
        ernesto_scream = Gdx.audio.newSound(Gdx.files.internal(soundHeader+"ernesto_scream.mp3"));

        for (int i = 0; i < numSongs; i++) {
            themes[i] = Gdx.audio.newMusic(Gdx.files.internal(soundHeader+themeNames[i]));
        }



        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setScale(.25f, -.25f);

        whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
        whiteFont.setScale(.1f, -.1f);

        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(.25f, -.25f);

        smallShadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        smallShadow.setScale(.1f, -.1f);

        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("ZombieBird");

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();

        // Dispose sounds
        dead.dispose();
        flap.dispose();
        coin.dispose();
        ernesto_scream.dispose();
        warp.dispose();

        font.dispose();
        shadow.dispose();
    }

}