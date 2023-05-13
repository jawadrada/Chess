package application;

import java.util.ArrayList;


//fix the king's legal move method so that it only adds moves that does'nt put the king in check 
//fix the isCheck method so it properly determines if the king is in check
//determine how to access the chessGame class after the initial mainGameDisplay is launched
//so if it is check mate you can display the gameOver graphic
//link the two scenes so the gamoOver scene displays over the mainGameDisplay

import application.Player.PieceColor;

public class King extends Piece {

	public King(PieceColor color) {
		super(color);
	}

	@Override
	public ArrayList<Move> legalMoves(ChessBoard board, Spot start) {
	    ArrayList<Move> moves = new ArrayList<>();
	    int startRow = start.getRow();
	    int startCol = start.getColumn();
	    PieceColor color = getColor();

	    // Loop through all potential moves for the king
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
	                Spot temp = start;
	                canMove(board, start, end);
	                if (!isCheck(board, start)) {
	                    moves.add(new Move(start, end));
	                }
	                canMove(board, temp, end); // Undo the potential move
	            }
	            
	            if (end != null && (!end.isSpotOccupied() || end.getPiece().getColor() != color)) {
	                canMove(board, start, end);
	            }
	        }
	    }

	    return moves;
	}

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
		return rowDiff <= 1 && colDiff <= 1;
	}
	
	public boolean isCheck(ChessBoard board, Spot start) {
	    int kingRow = start.getRow();
	    int kingCol = start.getColumn();

	    // Check if any opposing piece can attack the king
	    for (int row = 0; row < Constants.BOARD_SIZE; row++) {
	        for (int col = 0; col < Constants.BOARD_SIZE; col++) {
	            Spot spot = board.getSpot(row, col);
	            if (spot.isSpotOccupied() && spot.getPiece().getColor() != getColor()) {
	                //Piece piece = spot.getPiece();
	                ArrayList<Move> legalMoves = legalMoves(board, spot);
	                for (Move move : legalMoves) {
	                    if (move.getEnd().getRow() == kingRow && move.getEnd().getColumn() == kingCol) {
	                        return true; // King is in check
	                    }
	                }
	            }
	        }
	    }

	    return false; // King is not in check
	}
	
	public boolean checkMate(ChessBoard board,Spot start) {
	
		ArrayList<Move> legalMoves = this.legalMoves(board, start);
		
		//returns false if the king is not in check
		if(isCheck(board,start) == false) {
			return  false;
		}
		
		//if the legal moves arrayList is not empty that means the king has a legal move available therefore not in checkMate
		if(!legalMoves.isEmpty()) {
			return false;
		}
		
		//if true it is check mate
		return true;
	}
}


