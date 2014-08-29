package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.screen.SplashScreen;
import com.mygdx.ZBHelpers.AssetLoader;
public class MyGdxGame extends Game {

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
