package application;

import java.util.ArrayList;


//import com.example.Player.PieceColor;

/**
* 
* represents a Pawn piece in the chess game.
*/
public class Pawn extends Piece {

	private boolean firstMove;// bool to check it is the pawns first move

	/**
	 * 
	 * constructor for the pawn class.
	 * 
	 * @param color a PieceColor enum representing the color of the pawn
	 *              (either white or black)
	 *              sets the firstMove to true
	 */
	public Pawn(PieceColor color) {
		super(color);
		this.firstMove = true;

	}

	/**
	 * 
	 * returns whether this Pawn piece has moved before.
	 * 
	 * @return true if this Pawn has not moved before, false otherwise
	 */
	public boolean isFirstMove() {
		return firstMove;
	}

	/**
	 * 
	 * sets whether this Pawn piece has moved before.
	 * 
	 * @param firstMove true if this Pawn has not moved before, false otherwise
	 */
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	/**
	 * 
	 * returns a list of legal moves that this Pawn piece can make from its current
	 * position on the given board.
	 * 
	 * @param board the chess board
	 * 
	 * @param start the starting spot of this Pawn piece
	 * 
	 * @return a list of legal moves for this Pawn piece
	 */
	@Override
	public ArrayList<Move> legalMoves(ChessBoard board, Spot start) {
		ArrayList<Move> moves = new ArrayList<>();
		int direction = getColor() == PieceColor.WHITE ? -1 : 1;
		int row = start.getRow();
		int col = start.getColumn();

		// Check for one step forward
		Spot forwardSpot = board.getSpot(row + direction, col);
		if (forwardSpot != null && !forwardSpot.isSpotOccupied()) {
			moves.add(new Move(start, forwardSpot));

		}

		// Check for two steps forward if the pawn is at the initial position
		if ((row == 1 && getColor() == PieceColor.BLACK) || (row == 6 && getColor() == PieceColor.WHITE)) {
			Spot doubleForwardSpot = board.getSpot(row + 2 * direction, col);
			if (doubleForwardSpot != null && !doubleForwardSpot.isSpotOccupied() && !forwardSpot.isSpotOccupied()) {
				moves.add(new Move(start, doubleForwardSpot));

			}
		}

		// Check for diagonal captures
		for (int i = -1; i <= 1; i += 2) {
			Spot captureSpot = board.getSpot(row + direction, col + i);
			if (captureSpot != null && captureSpot.isSpotOccupied()
					&& captureSpot.getPiece().getColor() != getColor()) {
				moves.add(new Move(start, captureSpot));
			}
		}

		return moves;
	}

	/**
	 * 
	 * returns whether this Pawn piece can move from the starting spot to the ending
	 * spot on the given board.
	 * 
	 * @param board the chess board
	 * 
	 * @param start the starting spot of this Pawn piece
	 * 
	 * @param end   the ending spot of this Pawn piece
	 * 
	 * @return true if this Pawn piece can move to the ending spot, false otherwise
	 */
	@Override
	public boolean canMove(ChessBoard board, Spot start, Spot end) {

		if (end.getPiece() != null && end.getPiece().getColor() == this.getColor()) {
			return false; // Cannot move to a spot occupied by a piece of the same color
		}

		int xStart = start.getRow();
		int yStart = start.getColumn();
		int xEnd = end.getRow();
		int yEnd = end.getColumn();

		int yDirection = this.getColor() == PieceColor.WHITE ? -1 : 1;

		// Move one square forward
		if (xEnd == xStart + yDirection && yEnd == yStart) {
			return end.getPiece() == null;
		}

		// Move two squares forward for the pawn's first move
		if (this.isFirstMove() && xEnd == xStart + (2 * yDirection) && yEnd == yStart) {
			Spot inBetweenSpot = board.getSpot(xStart + yDirection, yStart);
			return end.getPiece() == null && inBetweenSpot.getPiece() == null;
		}

		// Capture a piece diagonally
		if (xEnd == xStart + yDirection && Math.abs(yEnd - yStart) == 1) {
			return end.getPiece() != null && end.getPiece().getColor() != this.getColor();
		}

		return false;
	}
}