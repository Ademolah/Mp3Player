package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class Controller implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, prevButton, nextButton, resetButton, speedButton;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;

    private File directory;
    private File[] files;

    private Media media;
    private MediaPlayer mediaPlayer;

    private ArrayList<File> songs;
    private int songNumber;
    private int[] speed = {25, 50, 75, 100, 125, 150, 175, 200};

    private Timer timer;
    private TimerTask task;
    private boolean running;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songs = new ArrayList<File>();
        directory = new File("Mp3Player/music");
        files = directory.listFiles();

        if(files != null){
            for(File file : files){
                songs.add(file);
                System.out.println(file);
            }
        }

        for(int i = 0; i < speed.length; i++){
            speedBox.getItems().add(Integer.toString(speed[i]) + "%");
        }

        speedBox.setOnAction(this::changeSpeed);

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });

        this.media = new Media(songs.get(songNumber).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());

    }

    private void changeSpeed(ActionEvent e) {
        if(speedBox.getValue() == null){
            mediaPlayer.setRate(1);
        }
        else {
            mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length()-1))* 0.01);
        }
    }

    public void resetMedia() {

        mediaPlayer.seek(Duration.seconds(0.0));
    }

    public void playMedia() {
        changeSpeed(null);
        mediaPlayer.play();
    }

    public void pauseMedia() {
        mediaPlayer.pause();
    }

    public void prevSong() {
        if(songNumber > 0){
            songNumber--;
            mediaPlayer.stop();
            this.media = new Media(songs.get(songNumber).toURI().toString());
            this.mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
        }
        else {
            songNumber = songs.size()-1;
            mediaPlayer.stop();
            this.media = new Media(songs.get(songNumber).toURI().toString());
            this.mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
    }

    public void nextSong() {
        if(songNumber < songs.size() -1){
            songNumber++;
            mediaPlayer.stop();
            this.media = new Media(songs.get(songNumber).toURI().toString());
            this.mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
        }
        else {
            songNumber = 0;
            mediaPlayer.stop();
            this.media = new Media(songs.get(songNumber).toURI().toString());
            this.mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
    }

    public void beginTimer(){

    }
    public void cancelTimer(){

    }
}
