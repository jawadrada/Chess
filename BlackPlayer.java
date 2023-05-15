package application;


/**
 * 
 * this class represents a player who controls the black pieces in a game of
 * chess.
 */
public class BlackPlayer implements Player {
    /**
     * the name of the player.
     */
    protected String name;
    /**
     * whether it is currently the player's turn.
     */
    protected boolean isTurn;

    /**
     * Constructs a new BlackPlayer with the specified name and turn status.
     *
     * @param name   the name of the player
     * @param isTurn true if it is the player's turn, false otherwise
     */
    public BlackPlayer(String name, boolean isTurn) {
        this.name = name;
        this.isTurn = isTurn;
    }

    /**
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return true if it is currently the player's turn, false otherwise
     */
    public boolean isTurn() {
        return isTurn;
    }

    /**
     * sets whether it is currently the player's turn.
     *
     * @param isTurn true if it is the player's turn, false otherwise
     */
    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    /**
     * changes the turn status of the player to the opposite of its current value.
     */
    public void changeTurn() {
        isTurn = !isTurn;
    }

    /**
     * returns the color of the piece
     *
     * @return the color of the pieces that the player is playing with
     */
    public PieceColor getPieceColor() {
        return PieceColor.BLACK;
    }
}