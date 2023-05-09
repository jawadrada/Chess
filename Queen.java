package application;

import java.util.ArrayList;
import application.Player.PieceColor;

public class Queen extends Piece {

	public Queen(PieceColor color) {
	    super(color);
	}
	
	@Override
	public ArrayList<Move> legalMoves(ChessBoard board, Spot start) {
		// TODO Auto-generated method stub
		ArrayList<Move> moves = new ArrayList<>();
        int startRow = start.getRow();
        int startCol = start.getColumn();

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int newRow = startRow;
                int newCol = startCol;

                while (true) {
                    newRow += i;
                    newCol += j;
                    Spot end = board.getSpot(newRow, newCol);

                    if (end == null) {
                        break;
                    }

                    if (end.getIsSpotOccupied()) {
                        if (end.getPiece().getColor() != this.getColor()) {
                            moves.add(new Move(start, end));
                        }
                        break;
                    }

                    moves.add(new Move(start, end));
                }
            }
        }
        
     // Add all possible moves in the same row
        for (int col = 0; col < 8; col++) {
            if (col != startCol) {
                // Get the end position of the move
                Spot end = board.getSpot(startRow, col);
                // Create a Move object representing the move from the start position to the end
                // position
                Move move = new Move(start, end);
                // Add the move to the list of legal moves
                moves.add(move);
            }
        }

        // Add all possible moves in the same column
        for (int row = 0; row < 8; row++) {
            if (row != startRow) {
                Spot end = board.getSpot(row, startCol);
                Move move = new Move(start, end);
                moves.add(move);
            }
        }

        return moves;
	}

	@Override
	public boolean canMove(ChessBoard board, Spot start, Spot end) {
		// TODO Auto-generated method stub
		if (end.getIsSpotOccupied() && end.getPiece().getColor() == getColor()) {
            return false;
        }

        int startRow = start.getRow();
        int startCol = start.getColumn();
        int endRow = end.getRow();
        int endCol = end.getColumn();

        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // Check if the end spot is in the same diagonal as the start spot
        if (rowDiff == colDiff) {
            int rowIncrement = (endRow - startRow) > 0 ? 1 : -1;
            int colIncrement = (endCol - startCol) > 0 ? 1 : -1;

            int currentRow = startRow + rowIncrement;
            int currentCol = startCol + colIncrement;

            while (currentRow != endRow && currentCol != endCol) {
                Spot intermediateSpot = board.getSpot(currentRow, currentCol);
                if (intermediateSpot.getIsSpotOccupied()) {
                    return false;
                }
                currentRow += rowIncrement;
                currentCol += colIncrement;
            }
            return true;
        }
        
     // Check if the end spot is in the same row or column as the start spot
        if (startRow == endRow) {
            // Check if there are any pieces in the way of the horizontal move
            int minCol = Math.min(startCol, endCol);
            int maxCol = Math.max(startCol, endCol);
            for (int col = minCol + 1; col < maxCol; col++) {
                // Get the spot at the current position in the row
                Spot intermediateSpot = board.getSpot(startRow, col);
                // Check if the spot is occupied by a piece of the same color as the rook
                if (intermediateSpot.getIsSpotOccupied() && intermediateSpot.getPiece().getColor() == getColor()) {
                    return false;
                }
            }
            // There are no pieces blocking the rook's path
            return true;
        } else if (startCol == endCol) {
            // Check if there are any pieces in the way of the vertical move
            int minRow = Math.min(startRow, endRow);
            int maxRow = Math.max(startRow, endRow);
            for (int row = minRow + 1; row < maxRow; row++) {
                Spot intermediateSpot = board.getSpot(row, startCol);
                if (intermediateSpot.getIsSpotOccupied() && intermediateSpot.getPiece().getColor() == getColor()) {
                    return false;
                }
            }
            return true;
        } 
        else {
            // The end spot is neither in the same row nor column as the start spot
            return false;
        }

	}
}
