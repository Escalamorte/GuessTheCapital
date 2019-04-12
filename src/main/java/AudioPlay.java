import jaco.mp3.player.MP3Player;
import java.io.File;

class AudioPlay {
    private String dir = System.getProperty("user.dir") + "\\src\\main\\resources\\audio\\" + File.separator;
    private MP3Player mp3;

    void gameOver(){
        String sound = dir + "gameOver.mp3";
        try{
            mp3 = new MP3Player(new File(sound));
            mp3.play();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    void wrongAnswer(){
        String sound = dir + "wrong.mp3";
        try{
            mp3 = new MP3Player(new File(sound));
            mp3.play();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    void correctAnswer(){
        String sound = dir + "correct.mp3";
        try{
            mp3 = new MP3Player(new File(sound));
            mp3.play();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
