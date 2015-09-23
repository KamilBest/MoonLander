package moon_lander;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
    public static final AudioClip ROCKET_EXPLOSION = Applet.newAudioClip(Sound.class.getResource("/rocket_explosion.wav"));
    public static final AudioClip METEOR_EXPLOSION = Applet.newAudioClip(Sound.class.getResource("/meteor_explosion.wav"));
    public static final AudioClip GAME_OVER = Applet.newAudioClip(Sound.class.getResource("/gameover.wav"));
    public static final AudioClip HISS = Applet.newAudioClip(Sound.class.getResource("/hiss.wav"));



}