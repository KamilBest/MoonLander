package moon_lander;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * moon_lander.Meteor class. Rocket have to avoid metheors and land.
 */
public class Meteor {
    /**
     * It contains a random number for starting X coordinate of the meteor.
     */
    private Random randomNumber;

    /**
     * position of meteor
     */
    public int meteorCoordinateX;
    public int meteorCoordinateY;

    public boolean crashed;
    /**
     * How fast and in which direction meteor is moving on X coordinate?
     */
    private int speedX;
    /**
     * How fast and in which direction meteor is moving on Y coordinate?
     */
    public int speedY;

    /**
     * Image of meteor.
     */
    private BufferedImage meteorImg;

    /**
     * Image of meteor explosion.
     */
    private BufferedImage meteorExplosionImg;

    /**
     * Image of hole after explosion.
     */
    private BufferedImage holeImg;

    /**
     * Width of meteor.
     */
    public int metheorImgWidth;
    /**
     * Height of meteor.
     */
    public int meteorImgHeight;

    /**
     * Random direction for meteor (left or right).
     */
    private boolean meteorFlyDirection;

    /**
     * Starting position of meteor.
     */
    private final int STARTING_POSITION = -100;

    /**
     * Max random speed.
     */
    private final int MAX_RANDOM_SPEED = 10;

    /**
     * The smallest X speed.
     */
    private final int SMALLEST_X_SPEED = 3;
    /**
     * The smallest Y speed.
     */
    private final int SMALLEST_Y_SPEED = 4;

    Meteor() {
        Initialize();
        LoadContent();
    }

    private void Initialize() {
        randomNumber = new Random();
        resetMeteors();


    }

    private void LoadContent() {
        try {
            URL meteorImgUrl = this.getClass().getResource("/meteor.png");
            meteorImg = ImageIO.read(meteorImgUrl);
            metheorImgWidth = meteorImg.getWidth();
            meteorImgHeight = meteorImg.getHeight();

            URL meteorExplosionImgUrl = this.getClass().getResource("/explosion.png");
            meteorExplosionImg = ImageIO.read(meteorExplosionImgUrl);

            URL holeImgUrl = this.getClass().getResource("/hole.png");
            holeImg = ImageIO.read(holeImgUrl);

        } catch (IOException ex) {
            Logger.getLogger(Meteor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resetMeteors() {
        crashed = false;
        meteorCoordinateX = randomNumber.nextInt(Framework.frameWidth - metheorImgWidth);
        meteorCoordinateY = STARTING_POSITION;
        meteorFlyDirection = randomNumber.nextBoolean();
        if (meteorFlyDirection) {
            speedX = randomNumber.nextInt(MAX_RANDOM_SPEED) + SMALLEST_X_SPEED;
            speedY = randomNumber.nextInt(MAX_RANDOM_SPEED) + SMALLEST_Y_SPEED;
        } else {
            speedX = -randomNumber.nextInt(MAX_RANDOM_SPEED) - SMALLEST_X_SPEED;
            speedY = randomNumber.nextInt(MAX_RANDOM_SPEED) + SMALLEST_Y_SPEED;
        }
    }

    /**
     * Here we move the rocket.
     */
    public void Update() {
        if (crashed) {
            speedX = 0;
            speedY = 0;
            resetMeteors();
        }
        meteorCoordinateX += speedX;
        meteorCoordinateY += speedY;
    }

    public void Draw(Graphics2D g2d) {
        g2d.drawImage(meteorImg, meteorCoordinateX, meteorCoordinateY, null);
    }

    public void DrawMeteorCrash(Graphics2D g2d) {
        g2d.drawImage(meteorExplosionImg, meteorCoordinateX, meteorCoordinateY - 50, null);
    }
}

