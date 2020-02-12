package com.example.chrischessapp;


/**
 *
 * @author Chris Zachariah
 *
 */
public class Knight extends Piece {
    /**
     * This is the parameterized Knight constructor
     * @param => x-coordinate
     * @param => y-coordinate
     * @param => color of the piece
     */
    public Knight(int x , int y , boolean isWhite) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
    } // ends the parameterized Knight constructor
    /**
     * Will check to make sure that the new move is valid for the Knight
     * @param board => the chess board
     * @param oldX => the initial x-coordinate of the piece
     * @param oldY => the initial y-coordinate of the piece
     * @param newX => the final x-coordinate of the piece
     * @param newY => the final y-coordinate of the piece
     * @return => true if the move is valid, false otherwise
     */
    public boolean isValidMove(Piece[][] board, int oldX, int oldY , int newX , int newY) {
        // check if the new square can be reached
        int direction = this.canReach(board, oldX, oldY, newX, newY);

        // if the new square can be reached, then check if the path is clear
        if (direction != 0) {
            // check to see if the new square is empty or not
            if (!Board.isEmpty(board, newX, newY)) {
                // square not empty: make sure piece is from opposing side and the king is not in check after the move
                if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                    return true;
                } else {
                    return false;
                }
                // empty: check if the king is in check after the move
            } else {
                if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                    return true;
                } else {
                    return false;
                }
            }
            // path can't be reached
        } else {
            return false;
        }
    } // ends the isValidMove() method
    /**
     * Assuming that canReach() and isPathClear() returns valid and true results, this method re-creates the board and makes the new knight move
     * @param board => the chess board
     * @param oldX => the initial x-coordinate of the piece
     * @param oldY => the initial y-coordinate of the piece
     * @param newX => the final x-coordinate of the piece
     * @param newY => the final y-coordinate of the piece
     * @return => true if the king is in check after the new move, false otherwise
     */
    public boolean phantomMove(Piece[][] board, int oldX, int oldY , int newX , int newY) {
        // create a new board
        Piece[][] updatedBoard =  new Piece[8][8];
        boolean team = board[oldX][oldY].isWhite;

        // set all the pieces on the new board to null
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                updatedBoard[i][j] = null;
            }
        }

        //copy the arrangement of pieces from the original board to this new board
        for (int i = 0 ; i < 8 ; i++) {
            for ( int j = 0 ; j < 8 ; j++) {
                // spot in original board is null
                if (board[i][j] == null) {
                    updatedBoard[i][j] = null;
                    // spot in original board is a Pawn
                } else if (board[i][j] instanceof Pawn) {
                    Pawn pointer = (Pawn)board[i][j];
                    updatedBoard[i][j] = new Pawn(pointer.x,pointer.y,pointer.isWhite);
                    Pawn otherPointer = (Pawn) updatedBoard[i][j];
                    otherPointer.canEnpassant = pointer.canEnpassant;
                    otherPointer.hadFirstMove = pointer.hadFirstMove;
                    otherPointer.readyToPromote = pointer.readyToPromote;
                    // spot in original board is a Rook
                } else if (board[i][j] instanceof Rook) {
                    Rook pointer = (Rook)board[i][j];
                    updatedBoard[i][j] = new Rook(pointer.x,pointer.y,pointer.isWhite);
                    Rook otherPointer = (Rook) updatedBoard[i][j];
                    otherPointer.hadFirstMove = pointer.hadFirstMove;
                    // spot in original board is Knight
                } else if (board[i][j] instanceof Knight) {
                    Knight pointer = (Knight)board[i][j];
                    updatedBoard[i][j] = new Knight(pointer.x,pointer.y,pointer.isWhite);
                    // spot in original board is Bishop
                } else if (board[i][j] instanceof Bishop) {
                    Bishop pointer = (Bishop)board[i][j];
                    updatedBoard[i][j] = new Bishop(pointer.x,pointer.y,pointer.isWhite);
                    // spot in original board is Queen
                } else if (board[i][j] instanceof Queen) {
                    Queen pointer = (Queen)board[i][j];
                    updatedBoard[i][j] = new Queen(pointer.x,pointer.y,pointer.isWhite);
                    // spot in original board is King
                } else {
                    King pointer = (King)board[i][j];
                    updatedBoard[i][j] = new King(pointer.x,pointer.y,pointer.isWhite);
                    King otherPointer = (King) updatedBoard[i][j];
                    otherPointer.hadFirstMove = pointer.hadFirstMove;
                }
            } // ends the inner-for-loop
        } // ends the outer-for-loop

        // now make the actual move for the King and check if he is in check
        updatedBoard = updatedBoard[oldX][oldY].makeMove(updatedBoard, oldX, oldY, newX, newY);

        // find the king on the board
        King king = null;
        int x = 0;
        int y = 0;
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                if ((updatedBoard[i][j] != null) && (updatedBoard[i][j] instanceof King) && (updatedBoard[i][j].isWhite == team)) {
                    king = (King)updatedBoard[i][j];
                    x = i;
                    y = j;
                }
            }
        }
        return king.isInCheck(updatedBoard, x, y, team);
    } // ends the phantomMove() method
    /**
     * This method will make the move that the player wants. Assumes that isValidMove() = true;
     * @param board => the chess board
     * @param oldX => the initial x-coordinate of the piece
     * @param oldY => the initial y-coordinate of the piece
     * @param newX => the final x-coordinate of the piece
     * @param newY => the final y-coordinate of the piece
     * @return => a new board with the Knight moved will be done
     */
    public Piece[][] makeMove(Piece[][] board, int oldX, int oldY , int newX , int newY) {
        board[newX][newY] = null;
        board[newX][newY] = new Knight(newX,newY,board[oldX][oldY].isWhite);
        board[newX][newY].hadFirstMove = true;
        board[oldX][oldY] = null;
        return board;
    } // ends the makeMove() method
    /**
     * Checks all the directions that the knight can move to in order to find the new square
     * @param board => the chess board
     * @param oldX => the initial x-coordinate of the piece
     * @param oldY => the initial y-coordinate of the piece
     * @param newX => the final x-coordinate of the piece
     * @param newY => the final y-coordinate of the piece
     * @return => returns an integer that represents a "direction" that the knight can travel to get to the new square or 0 if the given new square is not valid
     */
    public int canReach(Piece[][] board, int oldX, int oldY , int newX , int newY) {
        int i = 0;
        int j = 0;

        // white knight
        if (board[oldX][oldY].isWhite == true) {
            i = oldX - 2;
            j = oldY - 1;
            //check north-left => 1
            if ((i >= 0) && (j >= 0) ) {
                if ((i == newX) && (j == newY)) {
                    return 1;
                }
            }

            i = oldX - 2;
            j = oldY + 1;
            //check north-right => 2
            if ((i >= 0) && (j <= 7) ) {
                if ((i == newX) && (j == newY)) {
                    return 2;
                }
            }

            i = oldX - 1;
            j = oldY + 2;
            // check east-left => 3
            if ((i >= 0) && (j <= 7) ) {
                if ((i == newX) && (j == newY)) {
                    return 3;
                }
            }

            i = oldX + 1;
            j = oldY + 2;
            // check east-right => 4
            if ((i <= 7) && (j <= 7) ) {
                if ((i == newX) && (j == newY)) {
                    return 4;
                }
            }

            i = oldX + 2;
            j = oldY + 1;
            // check south-left => 5
            if ((i <= 7) && (j <= 7) ) {
                if ((i == newX) && (j == newY)) {
                    return 5;
                }
            }

            i = oldX + 2;
            j = oldY - 1;
            //check south-right => 6
            if ((i <= 7) && (j >= 0) ) {
                if ((i == newX) && (j == newY)) {
                    return 6;
                }
            }

            i = oldX + 1;
            j = oldY - 2;
            //check west-left => 7
            if ((i <= 7) && (j >= 0) ) {
                if ((i == newX) && (j == newY)) {
                    return 7;
                }
            }

            i = oldX - 1;
            j = oldY - 2;
            //check west-right => 8
            if ((i >= 0) && (j >= 0) ) {
                if ((i == newX) && (j == newY)) {
                    return 8;
                }
            }

            // the new square is not a valid square that the white knight can move to
            return 0;
            // black knight
        } else {
            i = oldX + 2;
            j = oldY + 1;
            //check north-left => 1
            if ((i <= 7) && (j <= 7) ) {
                if ((i == newX) && (j == newY)) {
                    return 1;
                }
            }

            i = oldX + 2;
            j = oldY - 1;
            //check north-right => 2
            if ((i <= 7) && (j >= 0) ) {
                if ((i == newX) && (j == newY)) {
                    return 2;
                }
            }

            i = oldX + 1;
            j = oldY - 2;
            // check east-left => 3
            if ((i <= 7) && (j >= 0) ) {
                if ((i == newX) && (j == newY)) {
                    return 3;
                }
            }

            i = oldX + 1;
            j = oldY - 2;
            // check east-right => 4
            if ((i <= 7) && (j >= 0) ) {
                if ((i == newX) && (j == newY)) {
                    return 4;
                }
            }

            i = oldX - 2;
            j = oldY - 1;
            // check south-left => 5
            if ((i >= 0) && (j >= 0) ) {
                if ((i == newX) && (j == newY)) {
                    return 5;
                }
            }

            i = oldX - 2;
            j = oldY + 1;
            //check south-right => 6
            if ((i >= 0) && (j <= 7) ) {
                if ((i == newX) && (j == newY)) {
                    return 6;
                }
            }

            i = oldX - 1;
            j = oldY + 2;
            //check west-left => 7
            if ((i >= 0) && (j <= 7) ) {
                if ((i == newX) && (j == newY)) {
                    return 7;
                }
            }

            i = oldX + 1;
            j = oldY + 2;
            //check west-right => 8
            if ((i <= 7) && (j <= 7) ) {
                if ((i == newX) && (j == newY)) {
                    return 8;
                }
            }

            // the new square is not a valid square that the white knight can move to
            return 0;
        }
    } // ends the canReach() method
} // ends the Knight class
