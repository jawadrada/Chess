package application;

/**
 * 
 * represents a chess move from one spot to another.
 */
public class Move {

    private Spot start;
    private Spot end;
    private boolean castlingMove = false;

    /**
     * 
     * creates a new Move object.
     * 
     * @param start the starting spot of the move
     * @param end   the ending spot of the move
     */
    public Move(Spot start, Spot end) {
        this.start = start;
        this.end = end;

    }

    /**
     * 
     * creates a new Move object for a castling move.
     * 
     * @param start          the starting spot of the move
     * @param end            the ending spot of the move
     * @param isCastlingMove a boolean flag indicating if this move is a castling
     *                       move
     */
    public Move(Spot start, Spot end, boolean isCastlingMove) {
        this(start, end);
        this.castlingMove = isCastlingMove;
    }

    /**
     * 
     * returns the starting spot of the move.
     * 
     * @return the starting spot of the move
     */
    public Spot getStart() {
        return start;
    }

    /**
     * 
     * returns the ending spot of the move.
     * 
     * @return the ending spot of the move
     */
    public Spot getEnd() {
        return end;
    }

    /**
     * 
     * returns a boolean indicating if this move is a castling move.
     * 
     * @return a boolean indicating if this move is a castling move
     */
    public boolean isCastlingMove() {
        return castlingMove;
    }
}