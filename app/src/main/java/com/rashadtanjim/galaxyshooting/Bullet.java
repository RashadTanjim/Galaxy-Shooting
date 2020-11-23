package com.rashadtanjim.galaxyshooting;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.rashadtanjim.galaxyshooting.GameView.screenRatioX;
import static com.rashadtanjim.galaxyshooting.GameView.screenRatioY;

public class Bullet {

    int x, y, width, height;
    Bitmap bullet;   // using Bitmap to store bullet activities

    Bullet(Resources res) {

        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);  // decoding resources form bitmap

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 4;  // to adjust screen size to all devices
        height /= 4;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
    }

    // to detach collision of bullet and planet
    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

}
