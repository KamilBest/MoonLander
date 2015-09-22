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
    public Rectangle meteorRectangle;

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

    private int counter=0;

    Meteor()
    {
        Initialize();
        LoadContent();
    }
    private void Initialize()
    {
        boolean sign;
        randomNumber = new Random();
        meteorCoordinateX = randomNumber.nextInt(Framework.frameWidth - metheorImgWidth);

        sign=randomNumber.nextBoolean();
        if(sign) {
            speedX =randomNumber.nextInt(3)+1;
            speedY=randomNumber.nextInt(3)+1;

        }
        else
        {
            speedX=-randomNumber.nextInt(3)-1;
            speedY=randomNumber.nextInt(3)+1;

        }

    }
    private void LoadContent()
    {
        try {
            URL meteorImgUrl = this.getClass().getResource("/meteor.png");
            meteorImg = ImageIO.read(meteorImgUrl);
            metheorImgWidth = meteorImg.getWidth();
            meteorImgHeight = meteorImg.getHeight();

            URL meteorExplosionImgUrl = this.getClass().getResource("/rocket_crashed.png");
            meteorExplosionImg = ImageIO.read(meteorExplosionImgUrl);

            URL holeImgUrl = this.getClass().getResource("/hole.png");
            holeImg = ImageIO.read(holeImgUrl);

        } catch (IOException ex) {
            Logger.getLogger(Meteor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Here we move the rocket.
     */
    public void Update() {
        meteorRectangle = new Rectangle(meteorCoordinateX, meteorCoordinateY, metheorImgWidth, meteorImgHeight);
        if(crashed)
        {
            counter++;
            speedX=0;
            speedY=0;
        }
        meteorCoordinateX += speedX;
        meteorCoordinateY += speedY;

    }
    public void Draw(Graphics2D g2d)
    {
        if(crashed&&counter<15)
            g2d.drawImage(meteorExplosionImg,meteorCoordinateX,meteorCoordinateY,null);
        else if(crashed==false)
        g2d.drawImage(meteorImg, meteorCoordinateX, meteorCoordinateY, null);
        else
            g2d.drawImage(holeImg, meteorCoordinateX, meteorCoordinateY, null);

    }
}

