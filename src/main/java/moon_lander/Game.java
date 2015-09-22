package moon_lander;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Game class
 */
public class Game {

    /**
     * Rocket to landing.
     */
    private PlayerRocket playerRocket;

    /**
     * Landing area.
     */
    private LandingArea landingArea;

    /**
     * Game background image.
     */
    private BufferedImage backgroundImg;

    /**
     * Red border image.
     */
    private BufferedImage redBorderImg;


    public Game() {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;

        Thread threadForInitGame = new Thread() {
            @Override
            public void run() {
                Initialize();
                LoadContent();

                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }


    /**
     * initialize variables and objects for the game.
     */
    private void Initialize() {
        playerRocket = new PlayerRocket();
        landingArea = new LandingArea();
    }

    /**
     * Load game files - images.
     */
    private void LoadContent() {
        try {
            URL backgroundImgUrl = this.getClass().getResource("/background.jpg");
            backgroundImg = ImageIO.read(backgroundImgUrl);

            URL redBorderImgUrl = this.getClass().getResource("/red_border.png");
            redBorderImg = ImageIO.read(redBorderImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Restart game - reset some variables.
     */
    public void RestartGame() {
        playerRocket.ResetPlayer();
    }


    /**
     * Update game logic.
     *
     * @param gameTime      gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime, Point mousePosition) {
        // Move the rocket
        playerRocket.Update();

        // Checks where the player rocket state (in space, landed, crashed).
        // Check whether bottom rocketCoordinateY coordinate of the rocket if is it near the landing area.
        if (playerRocket.rocketCoordinateY + playerRocket.rocketImgHeight - 10 > landingArea.y) {
            // Check if the rocket is over landing area.
            if ((playerRocket.rocketCoordinateX > landingArea.x) && (playerRocket.rocketCoordinateX < landingArea.x + landingArea.landingAreaImgWidth - playerRocket.rocketImgWidth)) {
                // Check if the rocket speed isn't too high.
                if (playerRocket.speedY <= playerRocket.topLandingSpeed)
                    playerRocket.landed = true;
                else
                    playerRocket.crashed = true;
            } else
                playerRocket.crashed = true;

            Framework.gameState = Framework.GameState.GAMEOVER;
        }
    }

    /**
     * Draw the game to the screen.
     *
     * @param g2d           Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition) {
        g2d.drawImage(backgroundImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);

        landingArea.Draw(g2d);

        playerRocket.Draw(g2d);
    }


    /**
     * Draw the game over screen.
     *
     * @param g2d           Graphics2D
     * @param mousePosition Current mouse position.
     * @param gameTime      Game time in nanoseconds.
     */
    public void DrawGameOver(Graphics2D g2d, Point mousePosition, long gameTime) {
        Draw(g2d, mousePosition);

        g2d.drawString("Press space or enter to restart.", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3 + 70);

        if (playerRocket.landed) {

            g2d.drawString("You have successfully landed!", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3);
            g2d.drawString("You have landed in " + gameTime / Framework.secInNanosec + " seconds.", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3 + 20);
        } else {
            g2d.setColor(Color.red);
            g2d.drawString("You have crashed the rocket!", Framework.frameWidth / 2 - 95, Framework.frameHeight / 3);
            g2d.drawImage(redBorderImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
        }
    }
}
