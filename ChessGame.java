package application;

import application.Player.PieceColor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class ChessGame extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private Player player1;
    private Player player2;
    private ChessBoard board;
    private String winningColor;
    
	public String getWinningColor() {
		return winningColor;
	}

	public void setWinningColor(String winningColor) {
		this.winningColor = winningColor;
	}
	
    public boolean gameIsOver() {
        return true;
    }
	
	public void mainGameDisplay(Stage primaryStage) {
        // Create main menu and add it to a new Scene
        MainMenu mainMenu = new MainMenu();
        Scene menuScene = new Scene(mainMenu);

        // Set the primary stage's scene to the main menu Scene
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(menuScene);
        primaryStage.show();

        // Launch the game with the chosen players when the start button is pressed
        mainMenu.getStartButton().setOnAction(e -> {
            // Get player names from text fields
            String player1Name = mainMenu.getPlayer1NameField().getText();
            String player2Name = mainMenu.getPlayer2NameField().getText();

            // Assign random colors to the players
            PieceColor player1Color = PieceColor.WHITE;
            PieceColor player2Color = PieceColor.BLACK;

            // Create player instances with the chosen names and colors
            player1 = new WhitePlayer(player1Name, player1Color, true);
            player2 = new BlackPlayer(player2Name, player2Color, false);

            // Create HBox to hold player labels
            HBox playerLabels = new HBox(20);
            playerLabels.setStyle("-fx-border-color: black; -fx-border-width: 3px;");
            playerLabels.setPadding(new Insets(5));
            playerLabels.setAlignment(Pos.CENTER);
            
            //creates two player labels with their entered names and piece colors
            Label player1Label = new Label(player1.getName() +  "(White)"  + "       VS ");
            player1Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            player1Label.setTextFill(Color.BLACK);
            Label player2Label = new Label(player2.getName() + "(Black)");
            player2Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            player2Label.setTextFill(Color.BLACK);
            
            playerLabels.getChildren().addAll(player1Label, player2Label);

            // Create chess board
            board = new ChessBoard(player1,player2);

            // Create VBox to hold player labels and chess board
            VBox gameLayout = new VBox(0);
            gameLayout.getChildren().addAll(playerLabels, board);

            // Create MenuBar
            MenuBar menuBar = new MenuBar();
            Menu fileMenu = new Menu("File");
            MenuItem saveItem = new MenuItem("Save");
            fileMenu.getItems().add(saveItem);
            saveItem.setOnAction(event -> {
                // include code to save the chess board
                // output all information to an output file
                // save who's turn it is
                // save the position of the pieces
            });
            menuBar.getMenus().addAll(fileMenu);

            // Create StackPane to hold VBox and MenuBar
            StackPane gameRoot = new StackPane();
            gameRoot.getChildren().addAll(gameLayout, menuBar);
            StackPane.setAlignment(menuBar, Pos.TOP_CENTER);
            StackPane.setMargin(gameLayout, new Insets(25, 0, 0, 0));

            // Launch the game with the chosen players
            Scene gameScene = new Scene(gameRoot);
            primaryStage.setScene(gameScene);
        });
        
//        if (gameIsOver() == true) {
//        	gameOverDisplay(primaryStage);
//        }
	}
	
	public void gameOverDisplay(Stage primaryStage) {
		//creates a game over label with a font style "Arcade Classic" and color red
		Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setFont(Font.font("Arcade Classic", 48));
        gameOverLabel.setTextFill(Color.RED);
        
        //creates a label with the winning color of the chess game
        Label winnerLabel = new Label(winningColor +  " wins");
        winnerLabel.setFont(Font.font("Verdana", 28));
        winnerLabel.setTextFill(Color.WHITE);
        
        //create two buttons play again and exit
        Button playAgain = new Button("Play Again");
        Button exit = new Button("Exit");
        
        //set the width of the two buttons to 75
        playAgain.setPrefWidth(75);
        exit.setPrefWidth(75);

        //create a hbox and add the two buttons too it
        //center the hbox, add spacing in between the two buttons,add padding around the hbox
        HBox button = new HBox(playAgain, exit);
        button.setAlignment(Pos.CENTER);
        button.setSpacing(40);
        button.setPadding(new Insets(10));
        
        //create a vbox and add the two lables and the buttons so they appear one after another
        //set the backround color to black and center the vbox
        VBox vbox = new VBox(20, gameOverLabel,winnerLabel, button);
        vbox.setStyle("-fx-background-color: black");
        vbox.setAlignment(Pos.CENTER);
        
        
        //add mouse events to change the cursor to the hand when it is entered in either of the buttons
        //change it back to the default cursor when it leaves
        playAgain.setOnMouseEntered(e -> {
        	playAgain.setCursor(Cursor.HAND);
        });

        playAgain.setOnMouseExited(e -> {
        	playAgain.setCursor(Cursor.DEFAULT);
        });

        exit.setOnMouseEntered(e -> {
        	exit.setCursor(Cursor.HAND);
        });

        exit.setOnMouseExited(e -> {
        	exit.setCursor(Cursor.DEFAULT);
        });
        
        //if the play again button is hit the game is started up again
        playAgain.setOnAction(e -> {
        	mainGameDisplay(primaryStage);
        });	
        
        //if the exit button is clicked the program closes
        exit.setOnAction(e -> {
        	System.exit(0);
        });
        
        //creates the new scene with a width of 350 and height of 200
        Scene scene = new Scene(vbox, 350, 200);
        
        //displays the scene 
        primaryStage.setScene(scene);
        primaryStage.show();
	}
    
    @Override
    public void start(Stage primaryStage) {
    	mainGameDisplay(primaryStage);
    }
 
    }

    
