package application;


import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A class that represents a spot on a chess board.
 */
public class Spot extends StackPane {

    private Piece piece;// initial piece
    private Rectangle tile; // initial tile
    private ImageView imageView; // initial image
    private boolean isSpotOccupied; // checks if the spot is occupied
    protected Piece pieceBeingDragged; // current piece
    protected ImageView imageViewBeingDragged; // curent image
    // colors for the tiles
    private final Color lightColor = Color.rgb(255, 206, 158);
    private final Color darkColor = Color.rgb(209, 139, 71);
    private double xCoor; // x position of the cursor
    private double yCoor; // y position of the cursor
    private ChessBoard board; // current board

    /**
     * Creates a new Spot object.
     *
     * @param piece          The piece that occupies this spot, or null if the spot
     *                       is empty.
     * @param tile           The rectangular tile that represents this spot.
     * @param isSpotOccupied A boolean value indicating whether this spot is
     *                       currently occupied by a piece.
     * @param board          The chess board that this spot belongs to.
     * @param player         The player who is currently taking their turn.
     */
    public Spot(Piece piece, Rectangle tile, boolean isSpotOccupied, ChessBoard board, Player player) {
        this.piece = piece;
        this.tile = tile;
        this.imageView = null;
        this.isSpotOccupied = isSpotOccupied;
        this.board = board;
        this.updateEventListeners(player);
    }

    /**
     * removes the piece from this spot.
     */
    public void removePiece() {

        piece = null; // set the piece reference to null
        setIsSpotOccupied(false);

    }

    /**
     * removes the image view from this spot.
     */
    public void removeImageView() {

        imageView = null; // set the piece reference to null

    }

    /**
     *
     * @return The image view for this spot.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Sets the image view for this spot.
     *
     * @param imageView The new image view for this spot.
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     *
     * @return The piece that occupies this spot.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Sets the piece that occupies this spot.
     *
     * @param piece that occupies this spot.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        setIsSpotOccupied(piece != null);

    }

    /**
     *
     * @return The row index of this spot in the chess board.
     */
    public int getRow() {
        return GridPane.getRowIndex(this);
    }

    /**
     *
     * @return The column index of this spot in the chess board.
     */
    public int getColumn() {
        return GridPane.getColumnIndex(this);
    }

    /**
     *
     * @return True if this spot is currently occupied by a piece, false otherwise.
     */
    public boolean isSpotOccupied() {
        return isSpotOccupied;
    }

    /**
     * sets whether this spot is currently occupied by a piece.
     *
     * @param isSpotOccupied indicating whether this spot is currently
     *                       occupied by a piece.
     */
    public void setIsSpotOccupied(boolean isSpotOccupied) {
        this.isSpotOccupied = isSpotOccupied;
    }

    /**
     * 
     * changes the color of the tile to highlight color.
     */
    public void highlight() {
        tile.setFill(Color.rgb(170, 162, 58));
    }

    /**
     * 
     * Resets the color of the tile to its original color.
     * The color of the tile depends on its position on the board.
     */
    public void resetColor() {
        int row = getRow();
        int col = getColumn();
        tile.setFill((row + col) % 2 == 0 ? lightColor : darkColor);
    }

    /**
     * 
     * updates event listeners for current Spot.
     * 
     * @param player the current player
     */
    public void updateEventListeners(Player player) {
        /**
         * 
         * sets the on mouse pressed event for this Spot.
         * 
         * if the player is on their turn, resets the highlights on the board and
         * highlights the Spot
         * 
         * and all legal moves for the selected piece.
         * 
         * @param event the mouse pressed event
         */
        setOnMousePressed(event -> {

            if (player.isTurn()) {
                board.resetHighlights(); // Reset highlights on the board
                if (isSpotOccupied) {
                    ArrayList<Move> legalMoves = piece.legalMoves(board, this);
                    for (Move move : legalMoves) {
                        Spot endSpot = move.getEnd(); // Access the 'end' Spot directly
                        endSpot.highlight();
                    }
                    pieceBeingDragged = piece;
                    imageViewBeingDragged = imageView;

                    this.toFront();
                    this.setCursor(Cursor.CLOSED_HAND);

                }
            }

        });

        /**
         * 
         * sets the on mouse dragged event for this Spot.
         * 
         * If a piece is being dragged updates the image view of the piece being
         * dragged.
         * 
         * @param event the mouse dragged event
         */
        setOnMouseDragged(event -> {
            if (player.isTurn()) {
                if (pieceBeingDragged != null) {

                    xCoor = event.getX() - 25;
                    yCoor = event.getY() - 25;

                    imageViewBeingDragged.setTranslateX(xCoor);
                    imageViewBeingDragged.setTranslateY(yCoor);

                }
            }
        });
        /**
         * 
         * Sets the on mouse released event for this Spot.
         * 
         * If a piece was being dragged, moves the piece to the destination Spot if the
         * move is legal.
         * 
         * @param event the mouse released event
         */
        setOnMouseReleased(event -> {

            board.resetHighlights();
            if (pieceBeingDragged != null) {

                imageViewBeingDragged.setTranslateX(0);
                imageViewBeingDragged.setTranslateY(0);

                // Calculate the destination row and column based on the local coordinates
                // within the GridPane
                Point2D localPoint = board.sceneToLocal(event.getSceneX(), event.getSceneY());
                int newRow = (int) Math.floor(localPoint.getY() / 50);
                int newCol = (int) Math.floor(localPoint.getX() / 50);

                // get the spot where the piece is being dropped
                Spot endSpot = board.getSpot(newRow, newCol);

                // check if the spot being dropped on is not the current spot and is occupied by
                // the same player's piece
                if (endSpot != this && this != null && this.isSpotOccupied()
                        && this.getPiece().getColor() == player.getPieceColor()) {

                    // check if it's the current player's turn
                    if (player.isTurn()) {

                        // check if the piece being dragged can move to the destination spot
                        if (endSpot != null && pieceBeingDragged.canMove(board, this, endSpot)) {

                            // if the piece being dragged is a pawn, check if it has moved for the first
                            // time and promote it if it reaches the opposite end of the board
                            if (pieceBeingDragged instanceof Pawn) {
                                ((Pawn) pieceBeingDragged).setFirstMove(false);
                                if (endSpot.getRow() == 0 && pieceBeingDragged.getColor() == PieceColor.WHITE) {
                                    pieceBeingDragged = new Queen(PieceColor.WHITE);
                                    String imagePath = ChessBoard.IMAGE_PATH + "\\" + pieceBeingDragged.getImageName()
                                            + ".png";
                                    File imageFile = new File(imagePath);
                                    if (imageFile.exists()) {
                                        Image image = new Image(imageFile.toURI().toString());
                                        ImageView imageView = new ImageView(image);
                                        imageView.setFitWidth(40);
                                        imageView.setFitHeight(40);
                                        imageViewBeingDragged.setImage(image);
                                    }

                                } else if (endSpot.getRow() == 7 && pieceBeingDragged.getColor() == PieceColor.BLACK) {
                                    pieceBeingDragged = new Queen(PieceColor.BLACK);
                                    String imagePath = ChessBoard.IMAGE_PATH + "\\" + pieceBeingDragged.getImageName()
                                            + ".png";
                                    File imageFile = new File(imagePath);
                                    if (imageFile.exists()) {
                                        Image image = new Image(imageFile.toURI().toString());
                                        ImageView imageView = new ImageView(image);
                                        imageView.setFitWidth(40);
                                        imageView.setFitHeight(40);
                                        imageViewBeingDragged.setImage(image);
                                    }
                                }

                            }
                            // check if the ending spot is occupied by a piece and remove it from the
                            // board

                            if (endSpot.isSpotOccupied()) {
                                // if the pice is a king we changed its isKilled value to true
                                // and sets the gameover value to true
                                if (endSpot.getPiece() instanceof King) {
                                    ((King) endSpot.getPiece()).changeIsKilled();
                                    ChessGame.isGameOver.setValue(true);
                                }
                                ImageView capturedPieceImageView = endSpot.getImageView();
                                endSpot.removeImageView();
                                endSpot.getChildren().remove(capturedPieceImageView);

                            }
                            // remove the piece from the current spot
                            this.removePiece();
                            this.getChildren().remove(imageViewBeingDragged);
                            this.removeImageView();

                            // remove the event listeners from the current spot
                            this.setOnMousePressed(null);
                            this.setOnMouseDragged(null);
                            this.setOnMouseReleased(null);

                            // place the piece on the destination spot
                            endSpot.setPiece(pieceBeingDragged);
                            endSpot.getChildren().add(imageViewBeingDragged);
                            endSpot.setImageView(imageViewBeingDragged);
                            endSpot.setIsSpotOccupied(true);
                            // get an ArrayList of the current plaers on the board
                            ArrayList<Player> players = board.players();
                            // change both players turn
                            players.get(0).changeTurn();
                            players.get(1).changeTurn();

                            // update the event listeners for the ending spot.
                            endSpot.updateEventListeners(player);
                        }

                    }

                }

                setCursor(Cursor.DEFAULT);

            }
        });
    }

}