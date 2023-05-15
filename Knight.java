package application;

import java.util.ArrayList;


/**
 * 
 * The Knight class represents a chess piece of type Knight.
 * 
 * It extends the Piece class and implements the legalMoves and canMove methods.
 */
public class Knight extends Piece {

	private boolean firstMove;

	/**
	 * 
	 * constructor for the knight class.
	 * 
	 * @param color a PieceColor enum representing the color of the knight
	 *              (either white or black)
	 *              sets the firstMove to true
	 */
	public Knight(PieceColor color) {
		super(color);
		this.firstMove = true;
	}

	/**
	 * 
	 * gets whether this is the first move of the piece.
	 * 
	 * @return true if it's the first move, false otherwise
	 */
	public boolean getIsFirstMove() {
		return firstMove;
	}

	/**
	 * 
	 * sets whether this is the first move of the piece.
	 * 
	 * @param firstMove true if it's the first move, false otherwise
	 */
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * returns a list of legal moves for the knight from the given starting spot on
	 * the given board.
	 * 
	 * @param board the chess board
	 * 
	 * @param start the starting spot
	 * 
	 * @return a list of legal moves for the knight
	 */

	@Override
	public ArrayList<Move> legalMoves(ChessBoard board, Spot start) {

		ArrayList<Move> moves = new ArrayList<>();

		int[][] knightMoves = { { 2, 1 }, { 1, 2 }, { -1, 2 }, { -2, 1 }, { -2, -1 }, { -1, -2 }, { 1, -2 },
				{ 2, -1 } };
		for (int i = 0; i < knightMoves.length; i++) {
			int newRow = start.getRow() + knightMoves[i][0];
			int newCol = start.getColumn() + knightMoves[i][1];

			// checks to see if the row and column are with the bounds
			if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
				Spot end = board.getSpot(newRow, newCol);
				if (end.getPiece() == null || end.getPiece().getColor() != getColor()) {
					moves.add(new Move(start, end));
				}
			}
		}

		return moves;

	}

	/**
	 * 
	 * returns true if the knight can move from the starting spot to the ending spot
	 * on the given board.
	 * 
	 * @param board the chess board
	 * 
	 * @param start the starting spot
	 * 
	 * @param end   the ending spot
	 * 
	 * @return true if the move is legal, false otherwise
	 */
	@Override
	public boolean canMove(ChessBoard board, Spot start, Spot end) {

		if (end.getPiece() != null && end.getPiece().getColor() == this.getColor()) {
			// Cannot move to a spot occupied by a piece of the same color
			return false;
		}

		int xStart = start.getRow();
		int yStart = start.getColumn();
		int xEnd = end.getRow();
		int yEnd = end.getColumn();

		int xDiff = Math.abs(xEnd - xStart);
		int yDiff = Math.abs(yEnd - yStart);

		// Check if the move is an L-shape
		if ((xDiff == 1 && yDiff == 2) || (xDiff == 2 && yDiff == 1)) {
			return true;
		}

		return false;
	}

}