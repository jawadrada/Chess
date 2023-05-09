package application;

import java.util.ArrayList;
import application.Player.PieceColor;

public class Knight extends Piece {

	private boolean firstMove;

	public Knight(PieceColor color) {
	    super(color);
	    this.firstMove = true;
	}
	
	public boolean getIsFirstMove() {
	    return firstMove;
	}

	public void setFirstMove(boolean firstMove) {
	    this.firstMove = firstMove;
	}
	
	@Override
	public ArrayList<Move> legalMoves(ChessBoard board, Spot start) {
		// TODO Auto-generated method stub
	    ArrayList<Move> moves = new ArrayList<>();

	    int[][] knightMoves = { {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1} };
	    for (int i = 0; i < knightMoves.length; i++) {
	        int newRow = start.getRow() + knightMoves[i][0];
	        int newCol = start.getColumn() + knightMoves[i][1];
	        
	        //checks to see if the row and column are with the bounds
	        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
	            Spot end = board.getSpot(newRow, newCol);
	            if (end.getPiece() == null || end.getPiece().getColor() != getColor()) {
	                moves.add(new Move(start, end));
	            }
	        }
	    }
	    
	    return moves;

	}

	@Override
	public boolean canMove(ChessBoard board, Spot start, Spot end) {
		// TODO Auto-generated method stub
		
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
		    
		 //Check if the move is an L-shape
		 if ((xDiff == 1 && yDiff == 2) || (xDiff == 2 && yDiff == 1)) {
		      return true;
		 }
		    
		return false;
	}
	
}

