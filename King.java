package application;

import java.util.ArrayList;
import application.Player.PieceColor;

public class King extends Piece {

	public King(PieceColor color) {
	    super(color);
	}

	@Override
	public ArrayList<Move> legalMoves(ChessBoard board, Spot start) {
		// TODO Auto-generated method stub
		ArrayList moves = new ArrayList();
		
		return moves;
	}

	@Override
	public boolean canMove(ChessBoard board, Spot start, Spot end) {
		// TODO Auto-generated method stub
		return false;
	}
	
}



