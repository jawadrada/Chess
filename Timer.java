package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

/**
 * A class representing a timer that counts down from a specified number of
 * seconds.
 */
public class Timer {
    private Timeline timeline;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(0);

    /**
     * constrcutor a Timer object that counts down from the specified number of
     * minutes.
     * 
     * @param minutes the number of minutes to count down from
     */
    public Timer(int minutes) {
        setTime(minutes * 60);
    }

    /**
     * sets the number of seconds remaining in the timer.
     * 
     * @param seconds the number of seconds to set the timer to
     */
    public void setTime(int seconds) {
        timeSeconds.set(seconds);
    }

    /**
     * returnss the IntegerProperty object representing the number of seconds
     * remaining in the timer.
     * 
     * @return the IntegerProperty object representing the number of seconds
     *         remaining in the timer
     */
    public IntegerProperty timeSecondsProperty() {
        return timeSeconds;
    }

    /**
     * Starts the timer.
     * 
     * When the timer is started, it will count down from the specified number of
     * seconds, updating
     * the timeSeconds property every second. If the timer runs out (i.e.
     * timeSeconds reaches 0),
     * the timeline will stop
     */
    public void start() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeSeconds.set(timeSeconds.get() - 1);
            if (timeSeconds.get() == 0) {
                timeline.stop();
                // TODO: add logic for what happens when the timer runs out
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Stops the timer.
     * 
     * When the timer is stopped, the timeline will be stopped and the timer will no
     * longer count down.
     */
    public void stop() {
        timeline.stop();
    }
}