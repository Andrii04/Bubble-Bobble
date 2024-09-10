package MODEL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
public class Sound {
    Clip clip;
    URL soundUrl[] = new URL[8];

    public Sound(){
        soundUrl[0] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_bubble.wav");
        soundUrl[1] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_death.wav");
        soundUrl[2] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_hit.wav");
        soundUrl[3] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_jump.wav");
        soundUrl[4] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_lose.wav");
        soundUrl[5] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_pickup.wav");
        soundUrl[6] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_theme.wav");
        soundUrl[7] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_win.wav");
    }
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

    public void playMusic(int i){
        setFile(i);
        play();
        loop();
    }
    public void playSound(int i){
        setFile(i);
        play();
    }
    public void stopSound(){
        stop();
    }
}
