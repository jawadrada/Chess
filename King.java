package application;

import java.util.ArrayList;


/**
 * 
 * a class representing a king chess piece.
 */
public class King extends Piece {
	boolean isKilled;

	/**
	 * 
	 * constructor for the king class.
	 * 
	 * @param color a PieceColor enum representing the color of the king
	 *              (either white or black)
	 *              sets the firstMove to true
	 */
	public King(PieceColor color) {
		super(color);
		isKilled = false;
	}

	public boolean isKilled() {
		return isKilled;
	}

	public void changeIsKilled() {
		this.isKilled = !isKilled;

	}

	/**
	 * 
	 * returns a list of all legal moves for this King piece on the given board
	 * 
	 * starting from the given spot.
	 * 
	 * @param board the ChessBoard on which the King is placed.
	 * 
	 * @param start the starting spot of the King.
	 * 
	 * @return a list of all legal moves for the King.
	 */
	@Override
	public ArrayList<Move> legalMoves(ChessBoard board, Spot start) {

	    ArrayList<Move> moves = new ArrayList<>();
	    int startRow = start.getRow();
	    int startCol = start.getColumn();

	    for (int i = -1; i <= 1; i++) {
	        for (int j = -1; j <= 1; j++) {
	            if (i == 0 && j == 0) {
	                continue; // Skip the king's current spot
	            }

	            int newRow = startRow + i;
	            int newCol = startCol + j;
	            Spot end = board.getSpot(newRow, newCol);
	            // Check if the potential move is legal and does not put the king in check
	            if (end != null && (!end.isSpotOccupied() || end.getPiece().getColor() != color)) {
	                // Make the potential move and check if the king is in check
	                Piece originalPiece = end.getPiece();
	                start.setPiece(null);
	                end.setPiece(this);
	                if (!isCheck(board)) {
	                    moves.add(new Move(start, end));
	                }
	                // Undo the potential move
	                start.setPiece(this);
	                end.setPiece(originalPiece);
	            }
	        }
	    }

	    return moves;
	}


	/**
	 * 
	 * checks whether or not this King piece can move to the given end spot
	 * 
	 * on the given board starting from the given start spot.
	 * 
	 * @param board the ChessBoard on which the King is placed.
	 * 
	 * @param start the starting spot of the King.
	 * 
	 * @param end   the end spot for the King to move to.
	 * 
	 * @return true if the King can move to the end spot, false otherwise.
	 */
	@Override
	public boolean canMove(ChessBoard board, Spot start, Spot end) {
	    if (end.isSpotOccupied() && end.getPiece().getColor() == getColor()) {
	        return false;
	    }

	    int startRow = start.getRow();
	    int startCol = start.getColumn();
	    int endRow = end.getRow();
	    int endCol = end.getColumn();

	    int rowDiff = Math.abs(startRow - endRow);
	    int colDiff = Math.abs(startCol - endCol);

	    // Check if the end spot is adjacent to the start spot
	    if (rowDiff <= 1 && colDiff <= 1) {
	        // Make the potential move and check if the king is in check
	        Piece originalPiece = end.getPiece();
	        start.setPiece(null);
	        end.setPiece(this);
	        boolean isInCheck = isCheck(board);

	        // Undo the potential move
	        start.setPiece(this);
	        end.setPiece(originalPiece);

	        return !isInCheck;
	    }

	    return false;
	}


	/**
	 * 
	 * checks if the given king is in check on the given chess board.
	 * 
	 * @param board the chess board to check for checkmate
	 * @param start the spot containing the king to check for checkmate
	 * @return true if the king is in check, false otherwise
	 */
	public boolean isCheck(ChessBoard board) {
	    int kingRow = -1;
	    int kingCol = -1;

	    // Find the king's position on the board
	    for (int row = 0; row < 8; row++) {
	        for (int col = 0; col < 8; col++) {
	            Piece piece = board.getSpot(row, col).getPiece();
	            if (piece == this) {
	                kingRow = row;
	                kingCol = col;
	                break;
	            }
	        }
	    }

	    // Loop through all spots on the board
	    for (int row = 0; row < 8; row++) {
	        for (int col = 0; col < 8; col++) {
	            Spot spot = board.getSpot(row, col);
	            Piece piece = spot.getPiece();

	            // If there is a piece and its color is different from the king's color
	            if (piece != null && piece.getColor() != this.getColor()) {
	                if (piece.canMove(board, spot, board.getSpot(kingRow, kingCol))) {
	                    return true; // The king can be captured by an opponent's piece
	                }
	            }
	        }
	    }

	    return false; // The king is not in check
	}


	/**
	 * 
	 * checks if the given king is in checkmate on the given chess board.
	 * 
	 * @param board the chess board to check for checkmate
	 * @param start the spot containing the king to check for checkmate
	 * @return true if the king is in checkmate, false otherwise
	 */
	public boolean checkMate(ChessBoard board) {
	    if (!isCheck(board)) {
	        return false; // The king is not in check, so it's not checkmate
	    }

	    // Loop through all spots on the board
	    for (int row = 0; row < 8; row++) {
	        for (int col = 0; col < 8; col++) {
	            Spot startSpot = board.getSpot(row, col);
	            Piece piece = startSpot.getPiece();

	            // If there is a piece and its color is the same as the king's color
	            if (piece != null && piece.getColor() == this.getColor()) {
	                ArrayList<Move> legalMoves = piece.legalMoves(board, startSpot);

	                for (Move move : legalMoves) {
	                    ChessBoard tempBoard = new ChessBoard(8, board.whitePlayer, board.blackPlayer);
	                    for (int r = 0; r < 8; r++) {
	                        for (int c = 0; c < 8; c++) {
	                            tempBoard.spots[r][c].setPiece(board.spots[r][c].getPiece());
	                        }
	                    }
	                    tempBoard.spots[move.getEnd().getRow()][move.getEnd().getColumn()].setPiece(tempBoard.spots[move.getStart().getRow()][move.getStart().getColumn()].getPiece());
	                    tempBoard.spots[move.getStart().getRow()][move.getStart().getColumn()].setPiece(null);

	                    if (!this.isCheck(tempBoard)) {
	                        return false; // There's a legal move that removes the check
	                    }
	                }
	            }
	        }
	    }

	    return true; // All possible moves still result in the king being in check
	}

}