package com.rashadtanjim.galaxyshooting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    public static float screenRatioX, screenRatioY;
    int count = 0;
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    private Paint paint;
    private Galaxy[] galaxies;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private List<Bullet> bullets;
    private int sound;
    private FireGun fireGun;
    private GameActivity activity;
    private GameBackground gameBackground1, gameBackground2;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(activity, R.raw.shoot, 1);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        gameBackground1 = new GameBackground(screenX, screenY, getResources());
        gameBackground2 = new GameBackground(screenX, screenY, getResources());

        fireGun = new FireGun(this, screenY, getResources());

        bullets = new ArrayList<>();

        gameBackground2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        galaxies = new Galaxy[4];

        for (int i = 0; i < 4; i++) {

            Galaxy galaxy = new Galaxy(getResources());
            galaxies[i] = galaxy;

        }

        random = new Random();

    }

    @Override
    public void run() {

        while (isPlaying) {

            update();
            draw();
            sleep();

        }

    }

    private void update() {

        gameBackground1.x -= 10 * screenRatioX;
        gameBackground2.x -= 10 * screenRatioX;

        if (gameBackground1.x + gameBackground1.background.getWidth() < 0) {
            gameBackground1.x = screenX;
        }

        if (gameBackground2.x + gameBackground2.background.getWidth() < 0) {
            gameBackground2.x = screenX;
        }

        if (fireGun.isGoingUp)
            fireGun.y -= 30 * screenRatioY;
        else
            fireGun.y += 30 * screenRatioY;

        if (fireGun.y < 0)
            fireGun.y = 0;

        if (fireGun.y >= screenY - fireGun.height)
            fireGun.y = screenY - fireGun.height;

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {

            if (bullet.x > screenX)
                trash.add(bullet);

            bullet.x += 50 * screenRatioX;

            for (Galaxy galaxy : galaxies) {

                if (Rect.intersects(galaxy.getCollisionShape(),
                        bullet.getCollisionShape())) {

                    score++;
                    galaxy.x = -500;
                    bullet.x = screenX + 500;
                    galaxy.wasShot = true;

                }

            }

        }

        for (Bullet bullet : trash)
            bullets.remove(bullet);

        for (Galaxy galaxy : galaxies) {

            galaxy.x -= galaxy.speed;

            if (galaxy.x + galaxy.width < 0) {

                if (!galaxy.wasShot) {
                    count++;
                }
                if (count >= 5) {
                    isGameOver = true;
                    return;
                }

                int bound = (int) (10 * screenRatioX);
                galaxy.speed = random.nextInt(bound);

                if (galaxy.speed < 7 * screenRatioX)
                    galaxy.speed = (int) (7 * screenRatioX);

                galaxy.x = screenX;
                galaxy.y = random.nextInt(screenY - galaxy.height);

                galaxy.wasShot = false;
            }

            if (Rect.intersects(galaxy.getCollisionShape(), fireGun.getCollisionShape())) {

                isGameOver = true;
                return;
            }

        }

    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(gameBackground1.background, gameBackground1.x, gameBackground1.y, paint);
            canvas.drawBitmap(gameBackground2.background, gameBackground2.x, gameBackground2.y, paint);

            for (Galaxy galaxy : galaxies)
                canvas.drawBitmap(galaxy.getPlanet(), galaxy.x, galaxy.y, paint);

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(fireGun.getDead(), fireGun.x, fireGun.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;
            }

            canvas.drawBitmap(fireGun.getFlight(), fireGun.x, fireGun.y, paint);

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void waitBeforeExiting() {

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, GameUI.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause() {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    fireGun.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                fireGun.isGoingUp = false;
                if (event.getX() > screenX / 2)
                    fireGun.toShoot++;
                break;
        }

        return true;
    }

    public void newBullet() {

        if (!prefs.getBoolean("isMute", false))
            soundPool.play(sound, 1, 1, 0, 0, 1);

        Bullet bullet = new Bullet(getResources());
        bullet.x = fireGun.x + fireGun.width;
        bullet.y = fireGun.y + (fireGun.height / 2);
        bullets.add(bullet);

    }
}
