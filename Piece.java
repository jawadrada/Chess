package application;

import java.util.ArrayList;
import application.Player.PieceColor;

public abstract class Piece {
	
	private boolean isKilled;	
    protected PieceColor color;
	
	public abstract ArrayList<Move> legalMoves(ChessBoard board, Spot start);
	public abstract boolean canMove(ChessBoard board, Spot start, Spot end);
	public abstract boolean isCheck(ChessBoard board, Spot start);
	public abstract boolean checkMate(ChessBoard board, Spot start);
	
	public Piece(PieceColor color) {
	    this.color = color;
	    this.isKilled = false;
	}
	
    public PieceColor getColor() {
	    return color;
	}

	public void setColor(PieceColor color) {
	    this.color = color;
	}

	public boolean getIsKilled() {
		return isKilled;
	}

	public void setIsKilled(boolean isKilled) {
		this.isKilled = isKilled;
	}
	
     public String getImageName() {
	     String colorString = color == PieceColor.WHITE ? "white" : "black";
	     return colorString + getClass().getSimpleName();
	}
	
}
