package application;

import java.util.ArrayList;


//import com.example.Player.PieceColor;

/**
* 
* represents a chess piece.
*/
public abstract class Piece {

	protected PieceColor color; // the pieces color

	/**
	 * 
	 * creates a new Piece instance with the given color.
	 * 
	 * @param color the color of the piece
	 */
	public Piece(PieceColor color) {
		this.color = color;
	}

	/**
	 * 
	 * returns a list of all legal moves that this piece can make from the given
	 * spot on the board.
	 * 
	 * @param board the chess board on which the piece is placed
	 * @param start the spot on the board where the piece is placed
	 * @return a list of legal moves that the piece can make from the given spot
	 */
	public abstract ArrayList<Move> legalMoves(ChessBoard board, Spot start);

	/**
	 * 
	 * checks if the piece can move from the start spot to the end spot on the
	 * board.
	 * 
	 * @param board the chess board on which the piece is placed
	 * @param start the spot on the board where the piece is placed
	 * @param end   the spot on the board where the piece wants to move to
	 * @return true if the piece can move to the end spot, false otherwise
	 */
	public abstract boolean canMove(ChessBoard board, Spot start, Spot end);


	/**
	 * 
	 * returns the color of the piece.
	 * 
	 * @return the color of the piece
	 */
	public PieceColor getColor() {
		return color;
	}

	/**
	 * 
	 * sets the color of the piece.
	 * 
	 * @param color the new color of the piece
	 */
	public void setColor(PieceColor color) {
		this.color = color;
	}

	/**
	 * 
	 * returns the name of the image file for the piece.
	 * 
	 * @return the name of the image file for the piece
	 */
	public String getImageName() {
		String colorString = color == PieceColor.WHITE ? "white" : "black";
		// System.out.println(colorString + getClass().getSimpleName());
		return colorString + getClass().getSimpleName();
	}

}