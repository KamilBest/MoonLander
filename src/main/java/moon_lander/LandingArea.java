package moon_lander;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Landind area class.
 */
public class LandingArea {

    /**
     * X coordinate of the landing area.
     */
    public int x;
    /**
     * Y coordinate of the landing area.
     */
    public int y;

    /**
     * Image of landing area.
     */
    private BufferedImage landingAreaImg;

    /**
     * Width of landing area.
     */
    public int landingAreaImgWidth;


    public LandingArea() {
        Initialize();
        LoadContent();
    }


    private void Initialize() {
        // X position of landing area (46% of width)
        x = (int) (Framework.frameWidth * 0.46);
        // Y position of landing area (88% of height)
        y = (int) (Framework.frameHeight * 0.88);
    }

    private void LoadContent() {
        try {
            URL landingAreaImgUrl = this.getClass().getResource("/landing_area.png");
            landingAreaImg = ImageIO.read(landingAreaImgUrl);
            landingAreaImgWidth = landingAreaImg.getWidth();
        } catch (IOException ex) {
            Logger.getLogger(LandingArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void Draw(Graphics2D g2d) {
        g2d.drawImage(landingAreaImg, x, y, null);
    }

}
