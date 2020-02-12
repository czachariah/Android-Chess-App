package com.example.chrischessapp;


/**
 *
 * @author Chris Zachariah
 *
 */
public class Rook extends Piece {
    /**
     * This is the parameterized Rook constructor
     * @param => x-coordinate
     * @param => y-coordinate
     * @param => color of the piece
     */
    public Rook(int x , int y , boolean isWhite) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        hadFirstMove = false;
    } // ends the parameterized Rook constructor
    /**
     * Will check to make sure that the new move is valid for the Bishop
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
            // check if the path is clear or not
            if (this.isPathClear(board, oldX, oldY, newX, newY, direction)) {
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
                // path not clear
            } else {
                return false;
            }
            // path can't be reached
        } else {
            return false;
        }
    } // ends the isValidMove() method
    /**
     * Assuming that canReach() and isPathClear() returns valid and true results, this method re-creates the board and makes the new rook move
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
     * @return => a new board with the Rook moved will be done
     */
    public Piece[][] makeMove(Piece[][] board, int oldX, int oldY , int newX , int newY) {
        board[newX][newY] = null;
        board[newX][newY] = new Rook(newX,newY,board[oldX][oldY].isWhite);
        board[newX][newY].hadFirstMove = true;
        board[oldX][oldY] = null;
        return board;
    } // ends the makeMove() method
    /**
     * Checks all the directions that the rook can move to in order to find the new square
     * @param board => the chess board
     * @param oldX => the initial x-coordinate of the piece
     * @param oldY => the initial y-coordinate of the piece
     * @param newX => the final x-coordinate of the piece
     * @param newY => the final y-coordinate of the piece
     * @return => returns an integer if the new square is valid for the rook to get to or returns 0 if not
     */
    public int canReach(Piece[][] board, int oldX, int oldY , int newX , int newY) {
        int i = 0;
        int j = 0;

        // white rook
        if (board[oldX][oldY].isWhite == true) {
            // check north = 1
            for (i = oldX - 1 , j = oldY ; i >= 0 && j == oldY ; i--) {
                if ((i == newX) && (j == newY)) {
                    return 1;
                }
            } // ends the for-loop (white-north)

            // check east = 2
            for (i = oldX , j = oldY + 1 ; i == oldX && j <= 7 ; j++) {
                if ((i == newX) && (j == newY)) {
                    return 2;
                }
            } // ends the for-loop (white-east)

            // check south = 3
            for(i = oldX + 1 , j = oldY ; i <= 7 && j == oldY ; i++) {
                if ((i == newX) && (j == newY)) {
                    return 3;
                }
            } // ends the for-loop (white-south)

            //check west = 4
            for (i = oldX , j = oldY - 1 ; i == oldX && j >= 0 ; j--) {
                if ((i == newX) && (j == newY)) {
                    return 4;
                }
            } // ends the for-loop (white-west)

            // the new square is not found in any of the valid directions of the queen and thus is an invalid move
            return 0;
            // black rook
        } else {
            // check north = 1
            for (i = oldX + 1 , j = oldY ; i <= 7 && j == oldY ; i++) {
                if ((i == newX) && (j == newY)) {
                    return 1;
                }
            } // ends the for-loop (white-north)

            // check east = 2
            for (i = oldX , j = oldY - 1 ; i == oldX && j >= 0 ; j--) {
                if ((i == newX) && (j == newY)) {
                    return 2;
                }
            } // ends the for-loop (white-east)

            // check south = 3
            for(i = oldX - 1 , j = oldY ; i >= 0 && j == oldY ; i--) {
                if ((i == newX) && (j == newY)) {
                    return 3;
                }
            } // ends the for-loop (white-south)

            //check west = 4
            for (i = oldX , j = oldY + 1 ; i == oldX && j <= 7 ; j++) {
                if ((i == newX) && (j == newY)) {
                    return 4;
                }
            } // ends the for-loop (white-west)

            // the new square is not found in any of the valid directions of the queen and thus is an invalid move
            return 0;
        }
    } // ends the canReach() method
    /**
     * Will check all the pieces between the starting and the ending squares of the rook to make sure that there is no other piece in the way
     * @param board => the chess board
     * @param oldX => the initial x-coordinate of the piece
     * @param oldY => the initial y-coordinate of the piece
     * @param newX => the final x-coordinate of the piece
     * @param newY => the final y-coordinate of the piece
     * @param direction => direction that the new sqare can be found with respect to the current piece
     * @return true if the path is clear, false otherwise
     */
    public boolean isPathClear(Piece[][] board, int oldX, int oldY , int newX , int newY, int direction) {
        int i = 0;
        int j = 0;

        // white bishop
        if (board[oldX][oldY].isWhite == true) {
            // north
            if (direction == 1) {
                for (i = oldX - 1 , j = oldY ; i > newX && j == oldY ; i--) {
                    if (!Board.isEmpty(board, i, j)) {
                        return false;
                    }
                } // ends the for-loop (white-north)
                return true;
                // east
            } else if (direction == 2) {
                for (i = oldX , j = oldY + 1 ; i == oldX && j < newY ; j++) {
                    if (!Board.isEmpty(board, i, j)) {
                        return false;
                    }
                } // ends the for-loop (white-east)
                return true;
                // south
            } else if (direction == 3) {
                for(i = oldX + 1 , j = oldY ; i < newX && j == oldY ; i++) {
                    if (!Board.isEmpty(board, i, j)) {
                        return false;
                    }
                } // ends the for-loop (white-south)
                return true;
                // west
            } else {
                for (i = oldX , j = oldY - 1 ; i == oldX && j > newY ; j--) {
                    if (!Board.isEmpty(board, i, j)) {
                        return false;
                    }
                } // ends the for-loop (white-west)
                return true;
            }
            // black bishop
        } else {
            // north
            if (direction == 1) {
                for (i = oldX + 1 , j = oldY ; i < newX && j == oldY ; i++) {
                    if (!Board.isEmpty(board, i, j)) {
                        return false;
                    }
                } // ends the for-loop (white-north)
                return true;
                // east
            } else if (direction == 2) {
                for (i = oldX , j = oldY - 1 ; i == oldX && j > newY ; j--) {
                    if (!Board.isEmpty(board, i, j)) {
                        return false;
                    }
                } // ends the for-loop (white-east)
                return true;
                // south
            } else if (direction == 3) {
                for(i = oldX - 1 , j = oldY ; i > newX && j == oldY ; i--) {
                    if (!Board.isEmpty(board, i, j)) {
                        return false;
                    }
                } // ends the for-loop (white-south)
                return true;
                // west
            } else {
                for (i = oldX , j = oldY + 1 ; i == oldX && j < newY ; j++) {
                    if (!Board.isEmpty(board, i, j)) {
                        return false;
                    }
                } // ends the for-loop (white-west)
                return true;
            }
        }
    } // ends the isPathClear method
} // ends the Rook class

