package com.example.chrischessapp;

/**
 *
 * @author Chris Zachariah
 *
 */
public abstract class Piece {

    public int x;					// x-coordinate on the board
    public int y;					// y-coordinate on the board
    public boolean isWhite;			// determine the color of the piece ( true = White | false = Black )
    public boolean hadFirstMove;	// determine if the piece was moved already (helps with Pawns and King and Rook for Castling)

    public abstract boolean isValidMove(Piece[][] board, int oldX, int oldY , int newX , int newY);
    public abstract Piece[][] makeMove(Piece[][] board, int oldX, int oldY , int newX , int newY);
    public abstract boolean phantomMove(Piece[][] board, int oldX, int oldY , int newX , int newY);
} // ends the Piece class
