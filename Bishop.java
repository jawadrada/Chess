package application;

import java.util.ArrayList;


/**
 * 
 * the Bishop class extends the Piece class and represents a bishop chess piece.
 * 
 * It contains methods for computing the legal moves and checking if a move is
 * valid.
 */
public class Bishop extends Piece {
    /**
     * 
     * constructor for the Bishop class.
     * 
     * @param color a PieceColor enum representing the color of the bishop
     *              (either white or black)
     */
    public Bishop(PieceColor color) {
        super(color);
    }

    /**
     * 
     * computes all the legal moves that the bishop can make from the
     * starting spot on the given board.
     * 
     * @param board the ChessBoard object representing the current state of the
     *              chess board
     * 
     * @param start the Spot object representing the starting spot of the bishop
     * 
     * @return an ArrayList of all legal moves that the bishop can make
     *         from the starting spot
     */
    @Override
    public ArrayList<Move> legalMoves(ChessBoard board, Spot start) {
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

                    if (end.isSpotOccupied()) {
                        if (end.getPiece().getColor() != getColor()) {
                            moves.add(new Move(start, end));
                        }
                        break;
                    }

                    moves.add(new Move(start, end));
                }
            }
        }

        return moves;
    }

    /**
     * 
     * checks if the bishop can move from the given starting spot to the given
     * ending spot on the given board.
     * 
     * @param board the ChessBoard object representing the current state of the
     *              chess board
     * 
     * @param start the Spot object representing the starting spot of the bishop
     * 
     * @param end   the Spot object representing the ending spot of the bishop
     * 
     * @return true if the bishop can legally move from the starting spot to the
     *         ending spot,
     *         false otherwise
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

        // Check if the end spot is in the same diagonal as the start spot
        if (rowDiff == colDiff) {
            int rowIncrement = (endRow - startRow) > 0 ? 1 : -1;
            int colIncrement = (endCol - startCol) > 0 ? 1 : -1;

            int currentRow = startRow + rowIncrement;
            int currentCol = startCol + colIncrement;

            while (currentRow != endRow && currentCol != endCol) {
                Spot intermediateSpot = board.getSpot(currentRow, currentCol);
                if (intermediateSpot.isSpotOccupied()) {
                    return false;
                }
                currentRow += rowIncrement;
                currentCol += colIncrement;
            }
            return true;
        }

        return false;
    }
}