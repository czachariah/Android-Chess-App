package com.example.chrischessapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 *
 * @author Chris Zachariah
 *
 */

public class Board {
    /**
     * This method builds the board.
     * @return => A 2-D 8x8 chess board with all the pieces in their original places to start the game
     */
    public static Piece[][] buildBoard() {
        Piece [][] board = new Piece[8][8];	// main board to be made and returned

        //Initialize all the pieces on the board to null first
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                board[i][j] = null;
            }
        }
        // fill row 7 with black pawns
        board[1][0] = new Pawn(1,0,false);
        board[1][1] = new Pawn(1,1,false);
        board[1][2] = new Pawn(1,2,false);
        board[1][3] = new Pawn(1,3,false);
        board[1][4] = new Pawn(1,4,false);
        board[1][5] = new Pawn(1,5,false);
        board[1][6] = new Pawn(1,6,false);
        board[1][7] = new Pawn(1,7,false);

        // fill row 2 with white pawns
        board[6][0] = new Pawn(6,0,true);
        board[6][1] = new Pawn(6,1,true);
        board[6][2] = new Pawn(6,2,true);
        board[6][3] = new Pawn(6,3,true);
        board[6][4] = new Pawn(6,4,true);
        board[6][5] = new Pawn(6,5,true);
        board[6][6] = new Pawn(6,6,true);
        board[6][7] = new Pawn(6,7,true);

        // fill in the black rooks
        board[0][0] = new Rook(0,0,false);
        board[0][7] = new Rook(0,7,false);

        // fill in the white rooks
        board[7][0] = new Rook(7,0,true);
        board[7][7] = new Rook(7,7,true);

        // fill in the black knights
        board[0][1] = new Knight(0,1,false);
        board[0][6] = new Knight(0,6,false);

        // fill in the white knights
        board[7][1] = new Knight(7,1,true);
        board[7][6] = new Knight(7,6,true);

        // fill in the black bishops
        board[0][2] = new Bishop(0,2,false);
        board[0][5] = new Bishop(0,5,false);

        // fill in the white bishops
        board[7][2] = new Bishop(7,2,true);
        board[7][5] = new Bishop(7,5,true);

        //fill in the black queen
        board[0][3] = new Queen(0,3,false);

        //fill in the white queen
        board[7][3] = new Queen(7,3,true);

        // fill in the black king
        board[0][4] = new King(0,4,false);

        // fill in the white king
        board[7][4] = new King(7,4,true);

        return board;
    } // ends the buildBoard method()

    /**
     * This method will check a square on the chess board to see if it is empty or not.
     * @param board => the chess board
     * @param x => the x-coordinate
     * @param y => the y-coordinate
     * @return => true if the square on the board is empty , false otherwise
     */
    public static boolean isEmpty(Piece[][] board, int x, int y) {
        if ((x > 7) || (x < 0) || (y > 7) || (y < 0) || (board[x][y] == null)) { // out of range OR square is null
            return true;
        } else {
            return false;
        }
    } // ends the isEmpty() method
} // ends the Board class
