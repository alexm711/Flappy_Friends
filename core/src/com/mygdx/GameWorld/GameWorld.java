package com.mygdx.GameWorld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.GameObjects.Bird;
import com.mygdx.GameObjects.ScrollHandler;
import com.mygdx.ZBHelpers.AssetLoader;

public class GameWorld {

    private Bird bird;
    private ScrollHandler scroller;
    private Rectangle ground;
    private int score = 0;
    private static int defaultLives = 3;
    private int lives = defaultLives;
    private int level = 1;
    private int currentSong = 0;
    private float screenTime = 0;
    private static int startingDifficulty = 20;
    private int countdown = startingDifficulty;
    private int currentBird = AssetLoader.flappyBird;
    private static int levelincrement = 10;
    private int numincrements = 0;
    private float runTime = 0;
    public int midPointY;
    private GameRenderer renderer;
    private GameState currentState;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    public GameWorld(int midPointY) {
        currentState = GameState.MENU;
        this.midPointY = midPointY;
        bird = new Bird(33, midPointY - 5, 17, 12);
        // The grass should start 66 pixels below the midPointY
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 137, 11);
    }

    public void update(float delta) {
        runTime += delta;
        screenTime += delta;
        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }

    }

    private void updateReady(float delta) {
        screenTime = 0;
        bird.updateReady(runTime);
        scroller.updateReady(delta);
    }

    public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        bird.update(delta);
        scroller.update(delta);

        if (scroller.collides(bird) && bird.isAlive()) {
            scroller.stop();
            bird.die();
            AssetLoader.dead.play();
            renderer.prepareTransition(255, 255, 255, .3f);
            AssetLoader.ernesto_scream.play();
        }

        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {

            if (bird.isAlive()) {
                renderer.prepareTransition(255, 255, 255, .3f);
                AssetLoader.dead.play();
                bird.die();
            }
            scroller.stop();
            bird.decelerate();
            currentState = GameState.GAMEOVER;

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }
    }

    public Bird getBird() {
        return bird;

    }

    public int getMidPointY() {
        return midPointY;
    }

    public int getCurrentBird(){return currentBird;}

    public void setCurrentBird(int birdNumber){
        currentBird=birdNumber;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public int getCountdown() {
        return countdown;
    }


    public void addScore(int increment) {
        score += increment;
        if (countdown > 0){
            if (countdown == 1){
                AssetLoader.complete.play();
            }
            countdown -= increment;
        }
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void ready() {
        screenTime = 0;
        currentState = GameState.READY;
        renderer.prepareTransition(0, 0, 0, 1f);
    }

    public void changeSong(boolean returnToMenu, int backgroundNumber){
        AssetLoader.themes[currentSong].stop();
        if (returnToMenu) {
            currentSong = 0;
        }else{
            currentSong = 2*backgroundNumber+renderer.r.nextInt(2);
        }
        AssetLoader.themes[currentSong].play();
    }
    public void restart() {
        screenTime = 0;
        score = 0;
        bird.onRestart(midPointY - 5);
        scroller.onRestart();
        lives-=1;
        if (countdown == 0) {
            currentState = GameState.READY;
            level+= 1;
            renderer.changeBackground();
            changeSong(false, renderer.getBgNum());
            lives += 2;
            numincrements+=1;
            countdown = startingDifficulty+numincrements*levelincrement;
            AssetLoader.warp.play();
        }else if (lives > 0){
            currentState = GameState.READY;
        }
        else {
            currentState = GameState.MENU;
            renderer.changeBackground();
            lives = defaultLives;
            level = 1;
            numincrements = 0;
            countdown = startingDifficulty;
            changeSong(true, renderer.getBgNum());
        }
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public float getScreenTime() {return screenTime;}

    public int getLives() {return lives;}

    public void setLives(int number) {lives=number;}

    public int getLevel(){return level;}

    public int getBgNum(){return renderer.getBgNum();}

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }


}