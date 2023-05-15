package application;

import java.util.ArrayList;

/**
 * 
 * Queen class represents a queen chess piece and extends the Piece class.
 * 
 * it defines the legal moves that a queen can make and checks if a move is
 * valid.
 */
public class Queen extends Piece {
    /**
     * 
     * constructor for the queen class.
     * 
     * @param color a PieceColor enum representing the color of the bishop
     *              (either white or black)
     */
    public Queen(PieceColor color) {
        super(color);
    }

    /**
     * 
     * returns a list of legal moves that the queen can make from the given start
     * spot on the given board.
     * 
     * @param board the chess board on which the queen is placed
     * 
     * @param start the starting spot of the queen
     * 
     * @return an ArrayList of all legal moves that the queen can make from the
     *         starting spot
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

                if (canMove(board, start, end)) {
                    Move move = new Move(start, end);
                    // Add the move to the list of legal moves
                    moves.add(move);
                }
            }
        }

        // Add all possible moves in the same column
        for (int row = 0; row < 8; row++) {
            if (row != startRow) {
                Spot end = board.getSpot(row, startCol);
                if (canMove(board, start, end)) {
                    Move move = new Move(start, end);
                    // Add the move to the list of legal moves
                    moves.add(move);
                }
            }
        }

        return moves;
    }

    /**
     * 
     * checks if the move from the start spot to the end spot is a valid move for
     * the queen.
     * 
     * @param board the chess board on which the queen is placed
     * 
     * @param start the starting spot of the queen
     * 
     * @param end   the end spot of the queen
     * 
     * @return true if the move is a valid move for the queen, false otherwise
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

        // Check if the end spot is in the same row or column as the start spot
        if (startRow == endRow) {
            // Check if there are any pieces in the way of the horizontal move
            int minCol = Math.min(startCol, endCol);
            int maxCol = Math.max(startCol, endCol);
            for (int col = minCol + 1; col < maxCol; col++) {
                // Get the spot at the current position in the row
                Spot currentSpot = board.getSpot(startRow, col);
                // Check if the spot is occupied by a piece of the same color as the rook
                if (currentSpot.isSpotOccupied()) {
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
                Spot currentSpot = board.getSpot(row, startCol);
                if (currentSpot.isSpotOccupied()) {
                    return false;
                }
            }
            return true;
        } else {
            // The end spot is neither in the same row nor column as the start spot
            return false;
        }

    }
}