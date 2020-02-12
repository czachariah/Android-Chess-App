package com.example.chrischessapp;


/**
 *
 * @author Chris Zachariah
 *
 */
public class Pawn extends Piece {

    public boolean canEnpassant;	// this is used to give permission to a pawn on the 4th or 5th ranks to enPassant when the right conditions are met
    public char readyToPromote;		// use this is a boolean to know when a pawn is ready to promote
    /**
     * This is the parameterized Pawn constructor
     * @param => x-coordinate
     * @param => y-coordinate
     * @param => color of the pawn
     */
    public Pawn(int x , int y , boolean isWhite) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        canEnpassant = false;
        readyToPromote = 'x';
        hadFirstMove = false;
    } // ends the parameterized Pawn constructor

    /**
     * Will check to make sure that the new move is valid for the Pawn
     * @param board => the chess board
     * @param oldX => the initial x-coordinate of the piece
     * @param oldY => the initial y-coordinate of the piece
     * @param newX => the final x-coordinate of the piece
     * @param newY => the final y-coordinate of the piece
     * @return => true if the move is valid, false otherwise
     */
    public boolean isValidMove(Piece[][] board, int oldX, int oldY , int newX , int newY) {
        // the way the numbers are checked is dependent on the color of the piece
        // specific check for white pawns
        if (this.isWhite == true) {
            // the white pawns are going bottom to top
            if ((newX >= oldX) || (newX < 0) || newX > 7 || (newY < 0) || (newY > 7)) {
                return false;
            }
            // not first move ... cannot move two squares
            if (((oldX-newX) == 2) && (board[oldX][oldY].hadFirstMove == true) && (oldY == newY)) {
                return false;
                // first move ... can move two squares, but the new square must be empty
            } else if (((oldX-newX) == 2) && (board[oldX][oldY].hadFirstMove == false) && (oldY == newY)) {
                if ((board[newX][newY] != null) || (board[oldX-1][newY] != null)) {
                    return false;
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        this.canEnpassant = true;
                        return true;
                    } else {
                        return false;
                    }
                }
                // trying to move one square up ... make sure that the new square is empty
            } else if (((oldX-newX) == 1) && (oldY == newY)) {
                if (board[newX][newY] != null) {
                    return false;
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // here we have to consider that the pawn is trying to take another piece
            } else {
                if ((newX == (oldX - 1)) && (newY == (oldY - 1))) {
                    if ((board[newX][newY] != null) && ((board[newX][newY].isWhite) != (this.isWhite))) {
                        if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if ((board[newX][newY] == null) && (board[oldX][newY] != null) && (board[oldX][newY] instanceof Pawn) && ((board[oldX][newY].isWhite) != (this.isWhite))) {
                        Pawn check = (Pawn)board[oldX][newY];
                        if (check.canEnpassant == true) {
                            if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else if ((newX == (oldX - 1)) && (newY == (oldY + 1))) {
                    if ((board[newX][newY] != null) && ((board[newX][newY].isWhite) != (this.isWhite))) {
                        if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if ((board[newX][newY] == null) && (board[oldX][newY] != null) && (board[oldX][newY] instanceof Pawn) && ((board[oldX][newY].isWhite) != (this.isWhite))) {
                        Pawn check = (Pawn)board[oldX][newY];
                        if (check.canEnpassant == true) {
                            if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            // specific check for black pawns
        } else {
            // the black pawns are going top to bottom
            if ((newX <= oldX) || (newX < 0) || (newX > 7) || (newY < 0) || (newY > 7)) {
                return false;
            }
            // not first move ... cannot move two squares
            if (((newX-oldX) == 2) && (this.hadFirstMove == true) && (oldY == newY)) {
                return false;
                // first move ... can move two squares, but the new square must be empty
            } else if (((newX-oldX) == 2) && (this.hadFirstMove == false) && (oldY == newY)) {
                if ((board[newX][newY] != null) || (board[oldX+1][newY] != null)) {
                    return false;
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        this.canEnpassant = true;
                        return true;
                    } else {
                        return false;
                    }
                }
                // trying to move one square up ... make sure that the new square is empty
            } else if (((newX-oldX) == 1) && (oldY == newY)) {
                if (board[newX][newY] != null) {
                    return false;
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // here we have to consider that the pawn is trying to take another piece or trying to do an enPassant
            } else {
                if ((newX == (oldX + 1)) && (newY == (oldY - 1))) {
                    if ((board[newX][newY] != null) && ((board[newX][newY].isWhite) != (this.isWhite))) {
                        if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if ((board[newX][newY] == null) && (board[oldX][newY] != null) && (board[oldX][newY] instanceof Pawn) && ((board[oldX][newY].isWhite) != (this.isWhite))) {
                        Pawn check = (Pawn)board[oldX][newY];
                        if (check.canEnpassant == true) {
                            if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else if ((newX == (oldX + 1)) && (newY == (oldY + 1))) {
                    if ((board[newX][newY] != null) && ((board[newX][newY].isWhite) != (this.isWhite))) {
                        if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if ((board[oldX][newY] != null) && (board[oldX][newY] instanceof Pawn) && ((board[oldX][newY].isWhite) != (this.isWhite))) {
                        Pawn check = (Pawn)board[oldX][newY];
                        if (check.canEnpassant == true) {
                            if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
    }// ends the isValidMove() method

    /**
     * Will make the new move on another board and check whether the King is safe or not
     * @param board => the chess board
     * @param oldX => the initial x-coordinate of the piece
     * @param oldY => the initial y-coordinate of the piece
     * @param newX => the final x-coordinate of the piece
     * @param newY => the final y-coordinate of the piece
     * @return => true if the king is still in check after the new move, false otherwise
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
                    Pawn otherPointer = (Pawn)updatedBoard[i][j];
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
     * @return => a new board with the pawn moved will be done
     */
    public Piece[][] makeMove(Piece[][] board, int oldX, int oldY , int newX , int newY) {
        Pawn pointer;
        Pawn oldPointer = (Pawn)board[oldX][oldY];

        if (board[oldX][oldY].isWhite) {
            // if the pawn is ready to promote, then change the piece on the new square
            if (oldPointer.readyToPromote != 'x') {
                if (oldPointer.readyToPromote == 'q') {
                    board[newX][newY] = null;
                    board[newX][newY] = new Queen(newX,newY,board[oldX][oldY].isWhite);
                    board[oldX][oldY] = null;
                    return board;
                } else if (oldPointer.readyToPromote == 'p') {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    board[oldX][oldY] = null;
                    return board;
                } else if (oldPointer.readyToPromote == 'r') {
                    board[newX][newY] = null;
                    board[newX][newY] = new Rook(newX,newY,board[oldX][oldY].isWhite);
                    board[oldX][oldY] = null;
                    return board;
                } else if (oldPointer.readyToPromote == 'n') {
                    board[newX][newY] = null;
                    board[newX][newY] = new Knight(newX,newY,board[oldX][oldY].isWhite);
                    board[oldX][oldY] = null;
                    return board;
                } else if (oldPointer.readyToPromote == 'b') {
                    board[newX][newY] = null;
                    board[newX][newY] = new Bishop(newX,newY,board[oldX][oldY].isWhite);
                    board[oldX][oldY] = null;
                    return board;
                }
            }
            if ((newX == (oldX - 1)) && (newY == (oldY - 1))) {
                if ((board[newX][newY] != null) && ((board[newX][newY].isWhite) != (this.isWhite))) {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    return board;
                } else if ((board[newX][newY] == null) && (board[oldX][newY] != null) && (board[oldX][newY] instanceof Pawn) && ((board[oldX][newY].isWhite) != (this.isWhite))) {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    board[oldX][newY] = null;
                    return board;
                } else {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    return board;
                }
            } else if ((newX == (oldX - 1)) && (newY == (oldY + 1))) {
                if ((board[newX][newY] != null) && ((board[newX][newY].isWhite) != (this.isWhite))) {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    return board;
                } else if ((board[newX][newY] == null) && (board[oldX][newY] != null) && (board[oldX][newY] instanceof Pawn) && ((board[oldX][newY].isWhite) != (this.isWhite))) {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    board[oldX][newY] = null;
                    return board;
                } else {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    return board;
                }
            } else {
                board[newX][newY] = null;
                board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                pointer = (Pawn)board[newX][newY];
                pointer.hadFirstMove = true;
                pointer.canEnpassant = oldPointer.canEnpassant;
                board[oldX][oldY] = null;
                return board;
            }
        } else {
            // if the pawn is ready to promote, then change the piece on the new square
            if (oldPointer.readyToPromote != 'x') {
                if (oldPointer.readyToPromote == 'q') {
                    board[newX][newY] = null;
                    board[newX][newY] = new Queen(newX,newY,board[oldX][oldY].isWhite);
                    board[oldX][oldY] = null;
                    return board;
                } else if (oldPointer.readyToPromote == 'p') {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    board[oldX][oldY] = null;
                    return board;
                } else if (oldPointer.readyToPromote == 'r') {
                    board[newX][newY] = null;
                    board[newX][newY] = new Rook(newX,newY,board[oldX][oldY].isWhite);
                    board[oldX][oldY] = null;
                    return board;
                } else if (oldPointer.readyToPromote == 'n') {
                    board[newX][newY] = null;
                    board[newX][newY] = new Knight(newX,newY,board[oldX][oldY].isWhite);
                    board[oldX][oldY] = null;
                    return board;
                } else if (oldPointer.readyToPromote == 'b') {
                    board[newX][newY] = null;
                    board[newX][newY] = new Bishop(newX,newY,board[oldX][oldY].isWhite);
                    board[oldX][oldY] = null;
                    return board;
                }
            }
            if ((newX == (oldX + 1)) && (newY == (oldY - 1))) {
                if ((board[newX][newY] != null) && ((board[newX][newY].isWhite) != (this.isWhite))) {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    return board;
                } else if ((board[newX][newY] == null) && (board[oldX][newY] != null) && (board[oldX][newY] instanceof Pawn) && ((board[oldX][newY].isWhite) != (this.isWhite))) {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    board[oldX][newY] = null;
                    return board;
                } else {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    return board;
                }
            } else if ((newX == (oldX + 1)) && (newY == (oldY + 1))) {
                if ((board[newX][newY] != null) && ((board[newX][newY].isWhite) != (this.isWhite))) {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    return board;
                } else if ((board[oldX][newY] != null) && (board[oldX][newY] instanceof Pawn) && ((board[oldX][newY].isWhite) != (this.isWhite))) {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    board[oldX][newY] = null;
                    return board;
                } else {
                    board[newX][newY] = null;
                    board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                    pointer = (Pawn)board[newX][newY];
                    pointer.hadFirstMove = true;
                    pointer.canEnpassant = oldPointer.canEnpassant;
                    board[oldX][oldY] = null;
                    return board;
                }
            } else {
                board[newX][newY] = null;
                board[newX][newY] = new Pawn(newX,newY,board[oldX][oldY].isWhite);
                pointer = (Pawn)board[newX][newY];
                pointer.hadFirstMove = true;
                pointer.canEnpassant = oldPointer.canEnpassant;
                board[oldX][oldY] = null;
                return board;
            }
        }
    } // ends the makeMove() method
} // ends the Pawn class
