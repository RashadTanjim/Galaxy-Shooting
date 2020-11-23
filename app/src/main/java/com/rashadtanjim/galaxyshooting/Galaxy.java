package com.rashadtanjim.galaxyshooting;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.rashadtanjim.galaxyshooting.GameView.screenRatioX;
import static com.rashadtanjim.galaxyshooting.GameView.screenRatioY;

public class Galaxy {

    public int speed = 7;
    public boolean wasShot = true;
    int x = 0, y, width, height, planetCounter = 1;
    Bitmap planet1, planet2, planet3, planet4;

    Galaxy(Resources res) {

        planet1 = BitmapFactory.decodeResource(res, R.drawable.planet1);
        planet2 = BitmapFactory.decodeResource(res, R.drawable.planet2);
        planet3 = BitmapFactory.decodeResource(res, R.drawable.planet3);
        planet4 = BitmapFactory.decodeResource(res, R.drawable.planet4);

        width = planet1.getWidth();
        height = planet1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        planet1 = Bitmap.createScaledBitmap(planet1, width, height, false);
        planet2 = Bitmap.createScaledBitmap(planet2, width, height, false);
        planet3 = Bitmap.createScaledBitmap(planet3, width, height, false);
        planet4 = Bitmap.createScaledBitmap(planet4, width, height, false);

        y = -height;
    }

    // using bit map to motion the different planet
    Bitmap getPlanet() {

        if (planetCounter == 1) {
            planetCounter++;
            return planet1;
        }

        if (planetCounter == 2) {
            planetCounter++;
            return planet2;
        }

        if (planetCounter == 3) {
            planetCounter++;
            return planet3;
        }

        planetCounter = 1;

        return planet4;
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

}
