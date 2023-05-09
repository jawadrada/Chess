package application;

// Make sure that the piece can only be moved if it's the correct person's turn

import java.util.ArrayList;

import application.Player.PieceColor;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Spot extends StackPane {

    private Rectangle tile;
    private boolean isSpotOccupied;
    private Piece piece;
    private ImageView imageView;
    private Piece pieceBeingDragged;
    private ImageView imageViewBeingDragged;
    private ChessBoard board;
    private final Color lightColor = Color.rgb(255, 206, 158);
    private final Color darkColor = Color.rgb(209, 139, 71);

    public Spot(Piece piece, Rectangle tile, boolean isSpotOccupied, ChessBoard board) {
        this.piece = piece;
        this.tile = tile;
        this.imageView = null;
        this.isSpotOccupied = isSpotOccupied;
        this.board = board;
        mouseEvents();
    }
    
    public Spot(Piece piece, Rectangle tile, ImageView imageView, boolean isSpotOccupied) {
        this.piece = piece;
        this.tile = tile;
        this.imageView = imageView;
        this.isSpotOccupied = isSpotOccupied;
    }
    
    public ChessBoard getBoard() {
        return board;
    }

    private void removePiece() {
        this.piece = null;
        setIsSpotOccupied(false);
    }

    private void removeImageView() {
        this.imageView = null;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        setIsSpotOccupied(piece != null);
    }

    public Rectangle getTile() {
        return tile;
    }

    public void getTileColor() {
        Color color = (Color) tile.getFill();
        System.out.println("Rectangle color: " + color.toString());
    }

    public Paint getColor() {
        return tile.getFill();
    }

    public void setTile(Rectangle tile) {
        this.tile = tile;
    }

    public double getTileX() {
        return tile.getX();
    }

    public double getTileY() {
        return tile.getY();
    }
    
    public int getRow() {
        return GridPane.getRowIndex(this);
    }

    public int getColumn() {
        return GridPane.getColumnIndex(this);
    }

    public boolean getIsSpotOccupied() {
        return isSpotOccupied;
    }
    
    public void setIsSpotOccupied(boolean isSpotOccupied) {
    	this.isSpotOccupied = isSpotOccupied;
    }
    
    public void highlight() {
        tile.setFill(Color.rgb(255, 255, 153, 0.5));
    }

    public void resetColor() {
        int row = getRow();
        int col = getColumn();
        tile.setFill((row + col) % 2 == 0 ? lightColor : darkColor);
    }
    
    public boolean canGo(PieceColor color, Piece piece) {
        if (color == piece.getColor()) {
            return true;
        } 
        else {
            return false;
        }
    }
    
    public void mouseEvents() {
        setOnMouseEntered(e -> {
        	if(this.getIsSpotOccupied() == true) {
         		setCursor(Cursor.CLOSED_HAND);	
         	}
        });

        setOnMousePressed(e -> {
            board.resetHighlights(); // Reset highlights on the board
            if (isSpotOccupied) {
                ArrayList<Move> legalMoves = piece.legalMoves(board, this);
                for (Move move : legalMoves) {
                    Spot destinationSpot = move.getEnd(); // Access the 'end' Spot directly
                    destinationSpot.highlight();
                }
                pieceBeingDragged = piece;
                imageViewBeingDragged = imageView;

                this.toFront();
                this.setCursor(Cursor.CLOSED_HAND);

            }
        });

        setOnMouseDragged(event -> {
            if (pieceBeingDragged != null) {
                double x = event.getX() - 25;
                double y = event.getY() - 25;
                imageViewBeingDragged.setTranslateX(x);
                imageViewBeingDragged.setTranslateY(y);
                this.setCursor(Cursor.CLOSED_HAND);
            }
        });
        
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

                Spot destinationSpot = board.getSpot(newRow, newCol);

                if (destinationSpot != null && pieceBeingDragged.canMove(board, this, destinationSpot)) {
                    if (pieceBeingDragged instanceof Pawn) {
                        ((Pawn) pieceBeingDragged).setFirstMove(false);
                    }
                    if (destinationSpot.getIsSpotOccupied()) {
                        ImageView capturedPieceImageView = destinationSpot.getImageView();
                        destinationSpot.removeImageView();
                        destinationSpot.getChildren().remove(capturedPieceImageView);
                    }
                    this.removePiece();
                    this.getChildren().remove(imageViewBeingDragged);
                    this.removeImageView();

                    this.setOnMousePressed(null);
                    this.setOnMouseDragged(null);
                    this.setOnMouseReleased(null);

                    destinationSpot.setPiece(pieceBeingDragged);
                    destinationSpot.getChildren().add(imageViewBeingDragged);
                    destinationSpot.setImageView(imageViewBeingDragged);
                    destinationSpot.setIsSpotOccupied(true);
                    destinationSpot.mouseEvents();

                    board.setSpot(newRow, newCol, destinationSpot);
                    board.setSpot(this.getRow(), this.getColumn(), this);
                 
                }

                // Reset the pieceBeingDragged
                pieceBeingDragged = null;
                imageViewBeingDragged = null;
                setCursor(Cursor.DEFAULT);

            }
        });
    }

}
