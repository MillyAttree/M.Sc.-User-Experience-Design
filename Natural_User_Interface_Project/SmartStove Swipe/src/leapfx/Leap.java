package leapfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.leapmotion.leap.Controller;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Leap extends Application {
    
    public Controller controller;
    public LeapListener listener;
    
    public AnchorPane heatLevel = new AnchorPane();
    public AnchorPane root = new AnchorPane();
    public Text level = new Text();
    public ImageView imgHand;
    public ImageView lockstate;
    public Text lockNote = new Text();
    public VBox vbox = new VBox();
    public RingProgressIndicator progress = new RingProgressIndicator();
    
    public ImageView arrowUp;
    public ImageView arrowDown;

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane.setBottomAnchor(heatLevel, 0.0);
            AnchorPane.setLeftAnchor(heatLevel, 0.0);
            AnchorPane.setRightAnchor(heatLevel, 0.0);
            
            // heat level
            heatLevel.setPrefHeight(0);
            heatLevel.setPrefWidth(500);
            heatLevel.setStyle("-fx-background-color: red");
            
            // lock state
            Image lock = new Image("images/lock.png");
            lockstate = new ImageView(lock);
            StackPane.setMargin(lockstate, new Insets(58,0,0,0));
            lockstate.setFitHeight(50);
            lockstate.setFitWidth(37);
            lockstate.setOpacity(.9);
            
            lockNote.setText("Control panel is locked");
            StackPane.setMargin(lockNote, new Insets(125,0,0,0));
            lockNote.setFont(new Font(15));
            lockNote.setTextAlignment(TextAlignment.CENTER);
            lockNote.setFill(Color.rgb(255, 255, 255));
            lockNote.setWrappingWidth(250);
            
            // Note to activate
            Image hand = new Image("images/hand.png");
            imgHand = new ImageView(hand);
            StackPane.setMargin(imgHand, new Insets(10,10,50,10));
            imgHand.setFitHeight(65);
            imgHand.setFitWidth(65);
            imgHand.setOpacity(.3);
            
            Text note = new Text("Hold your hand for 2 seconds to activate the control panel");
            note.setFont(new Font(17));
            note.setTextAlignment(TextAlignment.CENTER);
            note.setFill(Color.rgb(255, 255, 255));
            note.setWrappingWidth(250);
            
            vbox.setSpacing(20);
            vbox.setPadding(new Insets(47.5, 0, 0, 0));
            vbox.getChildren().addAll(imgHand, note);
            vbox.setAlignment(Pos.TOP_CENTER);
            vbox.setVisible(false);
            
            // Stackpane
            StackPane stack = new StackPane();
            stack.setPrefWidth(500);
            stack.setPrefHeight(1000);
            
            // Text Level & Arrows
            VBox levelBox = new VBox();
            levelBox.setSpacing(50);
            
            // Arrow up
            Image arrowUpImg = new Image("images/arrowUp.png");
            arrowUp = new ImageView(arrowUpImg);
            arrowUp.setFitHeight(25);
            arrowUp.setFitWidth(50);
            arrowUp.setVisible(false);
            
            // Arrow down
            Image arrowDownImg = new Image("images/arrowDown.png");
            arrowDown = new ImageView(arrowDownImg);
            arrowDown.setFitHeight(25);
            arrowDown.setFitWidth(50);
            arrowDown.setVisible(false);
            
            // Text level
            level.setFont(new Font(60));
            level.setTextAlignment(TextAlignment.CENTER);
            level.setText("OFF");
            level.setFill(Color.rgb(255, 255, 255));
            
            levelBox.setAlignment(Pos.CENTER);
            levelBox.getChildren().addAll(arrowUp, level, arrowDown);
            
            // Progress for activation
            StackPane.setMargin(progress, new Insets(35,0,0,0));
            progress.setVisible(false);
            
            stack.getChildren().addAll(levelBox, vbox, progress, lockstate, lockNote);
            stack.setAlignment(progress, Pos.TOP_CENTER);
            stack.setAlignment(lockstate, Pos.TOP_CENTER);
            stack.setAlignment(lockNote, Pos.TOP_CENTER);
       
            root.getChildren().addAll(heatLevel, stack);
            
            Scene scene = new Scene(root, 500, 1000, Color.rgb(26, 26, 26));
            scene.getStylesheets().add(getClass().getResource("leapmotion.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

            controller = new Controller();
            listener = new LeapListener(this);
            controller.addListener(listener);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
