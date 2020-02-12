package com.example.chrischessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayNewGame extends AppCompatActivity {

    Piece[][] board;
    boolean turn = true;        // true = white | false = black
    static boolean drawAsked = false;	// true one of the players has asked for a draw during their turn

    MoveNode head = null;       // LL to store the moves

    boolean gotFirstPlace = false;

    TileView lookAt;
    String currentTile;
    String movesToCheck;

    int[] checkMoves = new int[4]; // store the new moves before putting into LL


    boolean gameOver = false;	// determine when the game is done...
    King king = null;			// use this pointer to find the king



    /**
     * This method will create the board on screen and essentially run the "chess.java"
     * @param savedInstanceState => the Bundle
     * Should be able to play a full game here
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // connect to the correct View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_new_game);

        board = Board.buildBoard();
        printTable(board);

    } // ends the onCreate() method

    public void getInput(View touchedTile) {
        // need to get the first piece / spot
        if (!gotFirstPlace) {
            lookAt = (TileView) touchedTile;
            currentTile = getResources().getResourceName(lookAt.getId());
            currentTile = currentTile.substring(currentTile.length() - 2);
            movesToCheck = null;
            movesToCheck = "" + currentTile + " ";
            gotFirstPlace = true;
            System.out.println("here");
       // need to get the next square to put the piece
        } else {
            System.out.println("now here");
            lookAt = (TileView) touchedTile;
            currentTile = getResources().getResourceName(lookAt.getId());
            currentTile = currentTile.substring(currentTile.length() - 2);
            movesToCheck = movesToCheck + currentTile;

            // moves are valid and should be played on the board
            if (checkInput(board,movesToCheck,turn)) {
                System.out.println(movesToCheck);
                checkMoves = translateMoves(movesToCheck);
                if ((board[checkMoves[0]][checkMoves[1]] == null) || (board[checkMoves[0]][checkMoves[1]].isWhite != turn)) {
                    Toast.makeText(PlayNewGame.this, "Illegal move, try again!", Toast.LENGTH_SHORT).show();
                    gotFirstPlace = false;
                    movesToCheck = null;
                    return;
                } else if (board[checkMoves[0]][checkMoves[1]].isValidMove(board,checkMoves[0],checkMoves[1],checkMoves[2],checkMoves[3])) {
                    board = board[checkMoves[0]][checkMoves[1]].makeMove(board,checkMoves[0],checkMoves[1],checkMoves[2],checkMoves[3]);
                    printTable(board);

                } else {
                    Toast.makeText(PlayNewGame.this, "Illegal move, try again!", Toast.LENGTH_SHORT).show();
                    gotFirstPlace = false;
                    movesToCheck = null;
                    return;
                }
                turn = !turn;
                gotFirstPlace = false;
                movesToCheck = null;
                return;
            // moves are invalid and should not be played
            } else {
                Toast.makeText(PlayNewGame.this, "Illegal move, try again!", Toast.LENGTH_SHORT).show();
                gotFirstPlace = false;
                movesToCheck = null;
                return;
            }
        }


    } // ends the getInput() method

    /**
     * Will check to see if the input is valid or not.
     * @param board => compare the pieces on the board to make sure that right one's are chosen
     * @param s	=> the input from the user
     * @param turn => who's turn it is (white/black)
     * @return => true if input is valid , false otherwise
     */
    public static boolean checkInput(Piece[][] board , String s , boolean turn ) {
        // this is not just a regular set of moves
        if ((s.length() != 5) || (s.charAt(2) != ' ')) {
            if (s.length() >= 6) {
                // check for resignation (other team wins)
                if (s.equals("resign")) {
                    endGameNow(false,!turn);
                }
                // check for draw
                if (checkInput(board,s.substring(0,5),turn) && (s.substring(6).equals("draw?"))) {
                    drawAsked = true;
                    return true;
                }
                if ((s.length() == 7) && (Character.isLetter(s.charAt(0))) && (Character.isDigit(s.charAt(1))) && (Character.isWhitespace(s.charAt(2))) && (Character.isLetter(s.charAt(3))) && (Character.isDigit(s.charAt(4))) && (Character.isLetter(s.charAt(6)))) {
                    // check for pawn promotion (with argument = not queen)
                    int[] newMove = translateMoves(s);	// will need to translate the moves so that we can use the numbers to check the pieces on the board easily
                    if (board[newMove[0]][newMove[1]] instanceof Pawn) {
                        if (((board[newMove[0]][newMove[1]].isWhite == true) && (newMove[2] == 0)) || (((board[newMove[0]][newMove[1]].isWhite == false) && (newMove[2] == 7)))) {
                            if ((s.length() == 7) && (checkInput(board,s.substring(0, 5), turn)) && (s.charAt(5) == ' ') && (Character.isLetter(s.charAt(6)))) {
                                Pawn check = (Pawn)board[newMove[0]][newMove[1]];
                                if ((s.charAt(6) == 'p') || (s.charAt(6) == 'r') || (s.charAt(6) == 'n') || (s.charAt(6) == 'b')) {
                                    check.readyToPromote = s.charAt(6);
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        // now check if the input is 4 characters in the format: letter,number,space,letter,number
        if ((!Character.isLetter(s.charAt(0))) || (!Character.isDigit(s.charAt(1))) || (!Character.isWhitespace(s.charAt(2))) || (!Character.isLetter(s.charAt(3))) || (!Character.isDigit(s.charAt(4)))) {
            return false;
        }
        // make sure letters are in the correct range
        if ((!(s.charAt(0) >= 'a' && s.charAt(0) <= 'h')) || (!(s.charAt(3) >= 'a' && s.charAt(3) <= 'h'))) {
            return false;
        }
        // make sure numbers are in the correct range
        if ((!(s.charAt(1) >= '1' && s.charAt(1) <= '8')) || (!(s.charAt(4) >= '1' && s.charAt(4) <= '8'))) {
            return false;
        }
        // no duplicate entries
        if ((s.charAt(0) == s.charAt(3)) && (s.charAt(1) == s.charAt(4))) {
            return false;
        }
        // pawn promotion - w/o argument - queen
        int[] newMove = translateMoves(s);
        if (board[newMove[0]][newMove[1]] instanceof Pawn) {
            if (((board[newMove[0]][newMove[1]].isWhite == true) && (newMove[2] == 0)) || (((board[newMove[0]][newMove[1]].isWhite == false) && (newMove[2] == 7)))) {
                Pawn check = (Pawn)board[newMove[0]][newMove[1]];
                check.readyToPromote = 'q';
            }
        }

        return true;

    } // ends the getInput() method

    /**
     * This method will take the new moves in String from and translate it to a numbered form that can be easily used on the chess board
     * @param s => the string to be translated
     * @return => integer array (4 indices) with the new move => (format) oldRank,oldFile,newRank,newFile
     */
    public static int[] translateMoves(String s) {
        int[] moves = new int[4];

        // the ASCII values of (lower-case a through h) - 49 will give the results (0 through 7) respectively
        char oldFile = (char) (s.charAt(0) - 49);
        moves[1] = Character.getNumericValue(oldFile);

        moves[0] = 8 - Character.getNumericValue(s.charAt(1));

        // the ASCII values of (lower-case a through h) - 49 will give the results (0 through 7) respectively
        char a3 = (char) (s.charAt(3)-49);
        moves[3] = Character.getNumericValue(a3);

        moves[2] = 8 - Character.getNumericValue(s.charAt(4));

        return moves;
    } // ends the translateMoves() method
    /**
     * This method is used to print the chess board.
     * @param => Piece[8][8] chess board to be printed
     * @return => all the contents of the board will be printed
     */
    public void printTable(Piece[][] board) {
        androidx.gridlayout.widget.GridLayout grid = (androidx.gridlayout.widget.GridLayout)findViewById(R.id.Board);       // use this to get into the grid
        TileView child;                                                                                                     // use to change between views in the grid
        int place = 0;                                                                                                      // used to iterate through the imageViews and potentially change pieces


        for (int i = 0 ; i <= 8 ; i++) {
            if (i == 8) {										// this last row is used to print the column letters

            } else {
                for (int j = 0 ; j <= 8 ; j++) {
                    if (j == 8) {								// this last column is used to print the row numbers

                    } else {
                        if (board[i][j] == null) {				// if the square is null, then it is either blank or checkered
                            if (i % 2 == 0) {
                                if (j % 2 == 0) {
                                    //System.out.print("   ");	// even row, even column => blank
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageDrawable(null);
                                    place++;
                                } else {
                                    //System.out.print("## ");	// even row, odd column => checkered
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageDrawable(null);
                                    place++;
                                }
                            } else {
                                if (j % 2 == 0) {
                                    //System.out.print("## ");	// odd row, even column => checkered
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageDrawable(null);
                                    place++;
                                } else {
                                    //System.out.print("   ");	// odd row, odd column => blank
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageDrawable(null);
                                    place++;
                                }
                            }
                        } else {
                            if (board[i][j] instanceof Pawn) {			// square holds a Pawn
                                if (board[i][j].isWhite == true) {
                                   // System.out.print("wp ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.white_pawn);
                                    place++;
                                } else {
                                    //System.out.print("bp ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.black_pawn);
                                    place++;
                                }
                            } else if (board[i][j] instanceof Rook) { 	// square holds a Rook
                                if (board[i][j].isWhite == true) {
                                    //System.out.print("wR ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.white_rook);
                                    place++;
                                } else {
                                    //System.out.print("bR ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.black_rook);
                                    place++;
                                }
                            } else if (board[i][j] instanceof Knight) { // square holds a Knight
                                if (board[i][j].isWhite == true) {
                                    //System.out.print("wN ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.white_knight);
                                    place++;
                                } else {
                                    //System.out.print("bN ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.black_knight);
                                    place++;
                                }
                            } else if (board[i][j] instanceof Bishop) { // square holds a Bishop
                                if (board[i][j].isWhite == true) {
                                    //System.out.print("wB ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.white_bishop);
                                    place++;
                                } else {
                                    //System.out.print("bB ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.black_bishop);
                                    place++;
                                }
                            }  else if (board[i][j] instanceof Queen) { // square holds a Queen
                                if (board[i][j].isWhite == true) {
                                    //System.out.print("wQ ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.white_queen);
                                    place++;
                                } else {
                                    //System.out.print("bQ ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.black_queen);
                                    place++;
                                }
                            } else {									// square holds a King
                                if (board[i][j].isWhite == true) {
                                    //System.out.print("wK ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.white_king);
                                    place++;
                                } else {
                                    //System.out.print("bK ");
                                    child = (TileView)grid.getChildAt(place);
                                    child.setImageResource(R.drawable.black_king);
                                    place++;
                                }
                            }
                        }
                    }
                }
            }
        }
    } // ends the printTable() method

    /**
     * This method will end the game  given the right input
     * @param draw	=> true if there is a draw in the game
     * @param turn => true if white wins or false if black wins
     * @return => will end the program and either end the game in draw,white win or black win
     */
    public static void endGameNow(boolean draw , boolean turn) {
        if (draw) {
            System.out.println("draw");			// there was a draw
        } else if (turn) {
            System.out.println("White wins");	// white wins
        } else {
            System.out.println("Black wins");	// black wins
        }
        System.exit(0);							// now the game can officially end
    } // ends the endGameNow method()

} // ends the PlayNewGame() class

/*
        androidx.gridlayout.widget.GridLayout grid = (androidx.gridlayout.widget.GridLayout)findViewById(R.id.Board);
        TileView child = (TileView)grid.getChildAt(0);
        child.setImageDrawable(null);


        androidx.gridlayout.widget.GridLayout grid = (androidx.gridlayout.widget.GridLayout)findViewById(R.id.Board);
        for (int i = 0 ; i < grid.getChildCount() ; i++) {
            TileView child = (TileView)grid.getChildAt(i);
            child.setImageDrawable(null);
         }

        androidx.gridlayout.widget.GridLayout grid = (androidx.gridlayout.widget.GridLayout)findViewById(R.id.Board);
        for (int i = 0 ; i < grid.getChildCount() ; i++) {
            TileView child = (TileView)grid.getChildAt(i);
            child.setImageResource(R.drawable.black_bishop);
        }
 */