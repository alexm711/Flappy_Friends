package com.mygdx.ZBHelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.GameObjects.Bird;
import com.mygdx.GameWorld.GameWorld;
import com.mygdx.ui.SimpleButton;

public class InputHandler implements InputProcessor {
    private Bird myBird;
    private GameWorld myWorld;

    private List<SimpleButton> menuButtons;

    private SimpleButton playButton, backButton;
    private SimpleButton[] birdButton = new SimpleButton[AssetLoader.numBirds];

    private float scaleFactorX;
    private float scaleFactorY;
    private int initialX = 10;

    public InputHandler(GameWorld myWorld, float scaleFactorX,
                        float scaleFactorY) {
        this.myWorld = myWorld;
        myBird = myWorld.getBird();

        int midPointY = myWorld.getMidPointY();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(
                136 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2),
                midPointY + 50, 29, 16, AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
        backButton = new SimpleButton(
                136 / 2 - (AssetLoader.playButtonDown.getRegionWidth() / 2),
                midPointY*2 - 16, 40, 12, AssetLoader.back,
                AssetLoader.back);
        for (int i = 0; i < AssetLoader.numBirds; i++) {
            birdButton[i] = new SimpleButton(initialX+(i%4)*30, midPointY-20+(i/4)*30,
                    30, 30, AssetLoader.invisible, AssetLoader.invisible);
            menuButtons.add(birdButton[i]);
        }
        menuButtons.add(playButton);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
            for (int i = 0; i < AssetLoader.numBirds; i++) {
                if(birdButton[i].isTouchDown(screenX, screenY)){
                    myWorld.setCurrentBird(i);
                    AssetLoader.dead.play();
                }
            }
        } else if (myWorld.isReady()) {
            myWorld.start();
            myBird.onClick();
        }else if (myWorld.isRunning()){
            myBird.onClick();
        }

        if (myWorld.isGameOver() || myWorld.isHighScore()) {
            if(backButton.isTouchDown(screenX, screenY)){
                myWorld.setLives(1);
                AssetLoader.dead.play();
            }
            myWorld.restart();
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            if (playButton.isTouchUp(screenX, screenY)) {
                AssetLoader.select.play();
                myWorld.ready();
                myWorld.changeSong(false, myWorld.getBgNum());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {

        // Can now use Space Bar to play the game
        if (keycode == Keys.SPACE) {

            if (myWorld.isMenu()) {
                myWorld.ready();
            } else if (myWorld.isReady()) {
                myWorld.start();
            }

            myBird.onClick();

            if (myWorld.isGameOver() || myWorld.isHighScore()) {
                myWorld.restart();
            }

        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }

    public SimpleButton getBackButton() { return backButton;}
}