package leapfx;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class LeapListener extends Listener {

    private final Leap leapMain;
    private boolean panelActive = false;
    final Timeline timeline = new Timeline();
    long changeLevelTime;
    private boolean levelChanged = false;
    int currentLevel = 0;
    private boolean gestureDetected = false;

    public LeapListener(Leap leap) {
        this.leapMain = leap;
    }

    @Override
    public void onConnect(Controller controller) {
        System.out.println("Connected!");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
    }

    @Override
    public void onFrame(Controller controller) {
        Frame frame = controller.frame();
        HandList hands = frame.hands();
        GestureList gestures = frame.gestures();
        
        Hand hand = hands.get(0);
        float x = hand.stabilizedPalmPosition().getX();
        
        if (!hands.isEmpty()) {
            changeLevelTime = 0;
            if (panelActive) {
                
                for(Gesture gesture : gestures) {
                    if (gesture.type() == Gesture.Type.TYPE_SWIPE) {
                        SwipeGesture swipe = new SwipeGesture(gesture);

                        if (swipe.state() == Gesture.State.STATE_STOP && swipe.isValid()) {

                            // Up
                            if (swipe.direction().getX() > 0) {
                                if (!gestureDetected) {
                                    gestureDetected = true;

                                    if (currentLevel <= 5) {

                                        if (currentLevel != 5) {
                                            currentLevel = currentLevel + 1;
                                        }

                                        System.out.println(currentLevel);

                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                setNewHeatLevelHeight();
                                            }
                                        });
                                    }

                                    break;
                                }
                            } 

                            // Down
                            else {
                                if (!gestureDetected) {
                                    gestureDetected = true;
                                    if (currentLevel >= 0) {

                                        if (currentLevel != 0) {
                                            currentLevel = currentLevel - 1;
                                        }
                                        System.out.println(currentLevel);

                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                setNewHeatLevelHeight();
                                            }
                                        });
                                    }

                                    break;
                                }
                            }
                        }
                    }
                }
            }
            
            // Panel is inactive but hand is recognized
            else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        leapMain.lockstate.setVisible(false);
                        leapMain.lockNote.setVisible(false);
                        leapMain.vbox.setVisible(true);
                        leapMain.progress.setVisible(true);
                        leapMain.root.setStyle("-fx-border-color: #DB162F ; -fx-border-width: 5px");

                        // Sets the panel to active if the hand is hold for 2 sec
                        if (!panelActive) {
                            if (hand.timeVisible() > 2.0) {
                                panelActive = true;
                            }
                            leapMain.progress.setProgress((int)(hand.timeVisible() * 100) / 2);
                        }

                        if (panelActive) {
                            stateOnActivePanel();

                            if (leapMain.level.getText() == "OFF") {
                                leapMain.level.setText("No heat");
                                leapMain.arrowUp.setVisible(true);
                            } else if (leapMain.level.getText() == "Very high") {
                                leapMain.arrowUp.setVisible(true);
                                leapMain.arrowDown.setVisible(false);
                            } else {
                                leapMain.arrowUp.setVisible(true);
                                leapMain.arrowDown.setVisible(true);
                            }
                        }
                    }
                });
            }
        }
        
        // No hand recognized
        else if (hands.isEmpty()) {
            if (changeLevelTime == 0 && panelActive) {
               changeLevelTime = System.currentTimeMillis();
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (panelActive) {
                            startToLockPanel();
                        } 
                        else {
                            leapMain.vbox.setVisible(false);
                            leapMain.progress.setVisible(false);
                            leapMain.progress.setProgress(0);
                            leapMain.root.setStyle("-fx-border-color: #1a1a1a ; -fx-border-width: 5px");

                            Image lock = new Image("images/lock.png");
                            leapMain.lockstate.setImage(lock);
                            StackPane.setMargin(leapMain.lockstate, new Insets(58,0,0,0));
                            leapMain.lockstate.setFitHeight(45);
                            leapMain.lockstate.setFitWidth(32);
                            leapMain.lockstate.setOpacity(.9);
                            leapMain.lockstate.setVisible(true);
                            leapMain.lockNote.setText("Control panel is locked");
                            leapMain.lockNote.setVisible(true);

                            leapMain.arrowDown.setVisible(false);
                            leapMain.arrowUp.setVisible(false);
                        }
                    }
                });
            }
        }
    }
    
    /**
     * Change the heatLevel 
     */
    private void setNewHeatLevelHeight(){
        double newHeight = 0;
                
        switch (currentLevel) {
            case 0: 
                newHeight = 0;
                leapMain.heatLevel.setStyle("-fx-background-color: linear-gradient(to top, #FFFF00 , #FFCC00)");
                leapMain.level.setText("No heat");
                leapMain.level.setOpacity(.5);
                leapMain.arrowDown.setVisible(false);
                leapMain.arrowUp.setVisible(true);
                            
                break;
            case 1:
                newHeight = 200;
                leapMain.heatLevel.setStyle("-fx-background-color: linear-gradient(to top, #FFFF00 , #FF9900)");
                leapMain.level.setText("Very low");
                leapMain.level.setOpacity(1);
                leapMain.arrowDown.setVisible(true);
                leapMain.arrowUp.setVisible(true);
                
                break;
            case 2:
                newHeight = 400;
                leapMain.heatLevel.setStyle("-fx-background-color: linear-gradient(to top, #FFFF00 , #FF6600)");
                leapMain.level.setText("Low");
                leapMain.level.setOpacity(1);
                leapMain.arrowDown.setVisible(true);
                leapMain.arrowUp.setVisible(true);
                
                break;
            case 3:
                newHeight = 600;
                leapMain.heatLevel.setStyle("-fx-background-color: linear-gradient(to top, #FFFF00 , #FF4400 )");
                leapMain.level.setText("Medium");
                leapMain.level.setOpacity(1);
                leapMain.arrowDown.setVisible(true);
                leapMain.arrowUp.setVisible(true);
                
                break;
            case 4:
                newHeight = 800;
                leapMain.heatLevel.setStyle("-fx-background-color: linear-gradient(to top, #FFFF00 , #FF2200)");
                leapMain.level.setText("High");
                leapMain.level.setOpacity(1);
                leapMain.arrowDown.setVisible(true);
                leapMain.arrowUp.setVisible(true);
                
                break;
            case 5:
                newHeight = 1000;
                leapMain.heatLevel.setStyle("-fx-background-color: linear-gradient(to top, #FFFF00 , #FF0000)");
                leapMain.level.setText("Very high");
                leapMain.level.setOpacity(1);
                leapMain.arrowDown.setVisible(true);
                leapMain.arrowUp.setVisible(false);
                
                break;

            default:
                break;
        }

        ResizeHeightTranslation rht = new ResizeHeightTranslation(Duration.millis(500), leapMain.heatLevel, newHeight);
        rht.play();

        gestureDetected = false;
    }
    
    /**
     * Sets the state of the panel if active
     */
    private void stateOnActivePanel(){
        // Reset
        leapMain.vbox.setVisible(false);
        leapMain.progress.setVisible(false);
        leapMain.progress.setProgress(0);
        
        Image unlock = new Image("images/unlock.png");
        leapMain.lockstate.setImage(unlock);
        leapMain.lockstate.setFitHeight(40);
        leapMain.lockstate.setFitWidth(35);
        StackPane.setMargin(leapMain.lockstate, new Insets(58,0,0,0));
        leapMain.lockstate.setVisible(true);
        leapMain.lockNote.setVisible(true);
        leapMain.lockNote.setText("Control panel is unlocked and active");
    }
    
    /**
     * Lock the panel
     */
    private void startToLockPanel(){
        if (!levelChanged && (System.currentTimeMillis() - changeLevelTime) > 2000) {
            leapMain.progress.setVisible(true);
            leapMain.progress.setProgress((int)((System.currentTimeMillis() - (changeLevelTime + 2000)) / 10) / 2);
            
            leapMain.lockNote.setText("Control panel will be locked");

            if (System.currentTimeMillis() - changeLevelTime > 4000) {
                panelActive = false;
                
                // lock state
                Image lock = new Image("images/lock.png");
                leapMain.lockstate.setImage(lock);
                StackPane.setMargin(leapMain.lockstate, new Insets(58,0,0,0));
                leapMain.lockstate.setFitHeight(50);
                leapMain.lockstate.setFitWidth(37);
                leapMain.lockstate.setOpacity(.9);
                
                leapMain.vbox.setVisible(false);
                leapMain.progress.setVisible(false);
                leapMain.progress.setProgress(0);
                leapMain.root.setStyle("-fx-border-color: #1a1a1a ; -fx-border-width: 5px");
                leapMain.lockNote.setText("Control panel is locked");
                leapMain.lockNote.setVisible(true);

                leapMain.arrowDown.setVisible(false);
                leapMain.arrowUp.setVisible(false);
                
                if (leapMain.level.getText() == "No heat") {
                    leapMain.level.setText("OFF");
                }
            }
        }
    }
}
