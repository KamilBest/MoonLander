package moon_lander;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * The space rocket class.
 */

public class PlayerRocket {

    /**
     * It contains a random number for starting X coordinate of the rocket.
     */
    private Random random;

    /**
     * X coordinate of the rocket.
     */
    public int rocketCoordinateX;
    /**
     * Y coordinate of the rocket.
     */
    public int rocketCoordinateY;

    /**
     * if landed == false and crashed ==false rocket is in space
     * landing state
     */
    public boolean landed;

    /**
     * crashing state
     */
    public boolean crashed;

    /**
     * Accelerating speed of the rocket.
     */
    private int speedAccelerating;
    /**
     * Stopping/Falling speed of the rocket. Speed reduction because of Moon gravity.
     */
    private int speedStopping;

    /**
     * Maximum speed that rocket can have without having a crash when landing.
     */
    public int topLandingSpeed;

    /**
     * How fast and in which direction rocket is moving on X coordinate?
     */
    private int speedX;
    /**
     * How fast and in which direction rocket is moving on Y coordinate?
     */
    public int speedY;

    /**
     * Image of the rocket in air.
     */
    private BufferedImage rocketImg;
    /**
     * Image of the rocket when landed.
     */
    private BufferedImage rocketLandedImg;
    /**
     * Image of the rocket when crashed.
     */
    private BufferedImage rocketCrashedImg;
    /**
     * Image of the rocket fire.
     */
    private BufferedImage rocketFireImg;

    /**
     * Image of the left fire.
     */
    private BufferedImage leftFireImg;

    /**
     * Image of the right fire.
     */
    private BufferedImage rightFireImg;

    /**
     * Width of rocket.
     */
    public int rocketImgWidth;
    /**
     * Height of rocket.
     */
    public int rocketImgHeight;


    public PlayerRocket() {
        Initialize();
        LoadContent();
        rocketCoordinateX = random.nextInt(Framework.frameWidth - rocketImgWidth);
    }


    private void Initialize() {
        random = new Random();

        ResetPlayer();

        speedAccelerating = 2;
        speedStopping = 1;

        topLandingSpeed = 5;
    }

    private void LoadContent() {
        try {
            URL rocketImgUrl = this.getClass().getResource("/rocket.png");
            rocketImg = ImageIO.read(rocketImgUrl);
            rocketImgWidth = rocketImg.getWidth();
            rocketImgHeight = rocketImg.getHeight();

            URL rocketLandedImgUrl = this.getClass().getResource("/rocket_landed.png");
            rocketLandedImg = ImageIO.read(rocketLandedImgUrl);

            URL rocketCrashedImgUrl = this.getClass().getResource("/rocket_crashed.png");
            rocketCrashedImg = ImageIO.read(rocketCrashedImgUrl);

            URL rocketFireImgUrl = this.getClass().getResource("/rocket_fire.png");
            rocketFireImg = ImageIO.read(rocketFireImgUrl);

            URL leftFireImgUrl = this.getClass().getResource("/left_fire.png");
            leftFireImg = ImageIO.read(leftFireImgUrl);

            URL rightFireImgUrl = this.getClass().getResource("/right_fire.png");
            rightFireImg = ImageIO.read(rightFireImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(PlayerRocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Here we set up the rocket when we starting a new game.
     */
    public void ResetPlayer() {
        landed = false;
        crashed = false;

        rocketCoordinateX = random.nextInt(Framework.frameWidth - rocketImgWidth);
        rocketCoordinateY = 10;

        speedX = 0;
        speedY = 0;
    }


    /**
     * Here we move the rocket.
     */
    public void Update() {
        // Calculating speed for moving up or down.
        if (Canvas.keyboardKeyState(KeyEvent.VK_W))
            speedY -= speedAccelerating;
        else
            speedY += speedStopping;

        // Calculating speed for moving or stopping to the left.
        if (Canvas.keyboardKeyState(KeyEvent.VK_A))
            speedX -= speedAccelerating;
        else if (speedX < 0)
            speedX += speedStopping;

        // Calculating speed for moving or stopping to the right.
        if (Canvas.keyboardKeyState(KeyEvent.VK_D))
            speedX += speedAccelerating;
        else if (speedX > 0)
            speedX -= speedStopping;

        // Moves the rocket.
        rocketCoordinateX += speedX;
        rocketCoordinateY += speedY;
    }

    public void Draw(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.drawString("Rocket coordinates: " + rocketCoordinateX + " : " + rocketCoordinateY, 5, 15);

        if (landed) {
            g2d.drawImage(rocketLandedImg, rocketCoordinateX, rocketCoordinateY, null);
        } else if (crashed) {
            g2d.drawImage(rocketCrashedImg, rocketCoordinateX, rocketCoordinateY + rocketImgHeight - rocketCrashedImg.getHeight(), null);
        } else {
            if (Canvas.keyboardKeyState(KeyEvent.VK_W))
                g2d.drawImage(rocketFireImg, rocketCoordinateX + 12, rocketCoordinateY + 66, null);

            if (Canvas.keyboardKeyState(KeyEvent.VK_D))
                g2d.drawImage(leftFireImg, rocketCoordinateX - 5, rocketCoordinateY, null);

            if (Canvas.keyboardKeyState(KeyEvent.VK_A))
                g2d.drawImage(rightFireImg, rocketCoordinateX + 45, rocketCoordinateY, null);
            g2d.drawImage(rocketImg, rocketCoordinateX, rocketCoordinateY, null);
        }
    }

}
