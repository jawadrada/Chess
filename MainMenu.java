package application;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * 
 * the MainMenu class represents the main menu of a chess game.
 * 
 * its a VBox that contains a background image, welcome text, two text fields
 * for player names,
 * 
 * and a button to start the game.
 */

public class MainMenu extends VBox {

    private final TextField player1NameField = new TextField();
    private final TextField player2NameField = new TextField();
    private final Button startButton = new Button("PLAY");

    /**
     * 
     * creates a new MainMenu with default settings.
     * 
     * it sets the spacing between children to 10px, adds padding to the VBox,
     * 
     * and sets the preferred size of the VBox to 500x500.
     * 
     * it also initializes the background image, welcome text, player name fields,
     * 
     * and the start button.
     */
    public MainMenu() {
        super(10); // Set spacing between children to 10px
        setPadding(new Insets(20)); // Add padding to the VBox
        setPrefSize(500, 500); // Set size of the VBox

     // Add background image
        ImageView imageView = new ImageView(new Image("chessbg.jpg"));

        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Add text fields for player names
        Text welcomeText = new Text("Welcome to Chess Game");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        StackPane welcomePane = new StackPane(welcomeText);
        welcomePane.setAlignment(Pos.CENTER);

        getChildren().addAll(
                imageView,
                welcomePane,
                new Text("Player 1 Name:"),
                player1NameField,
                new Text("Player 2 Name:"),
                player2NameField,
                startButton);
    }

    /**
     * 
     * gets the TextField for the first player's name.
     * 
     * @return the TextField for the first player's name.
     */
    public TextField getPlayer1NameField() {
        return player1NameField;
    }

    /**
     * 
     * Gets the TextField for the second player's name.
     * 
     * @return the TextField for the second player's name.
     */
    public TextField getPlayer2NameField() {
        return player2NameField;
    }

    /**
     * 
     * gets the Button to start the game.
     * 
     * @return the Button to start the game.
     */
    public Button getStartButton() {
        return startButton;
    }
}