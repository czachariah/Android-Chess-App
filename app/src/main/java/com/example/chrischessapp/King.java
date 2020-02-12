package com.example.chrischessapp;


/**
 *
 * @author Chris Zachariah
 *
 */
public class King extends Piece {

    public char castle = 'x';

    /**
     * This is the parameterized King constructor
     * @param => x-coordinate
     * @param => y-coordinate
     * @param => color of the piece
     */
    public King(int x , int y , boolean isWhite) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        hadFirstMove = false;
        castle = 'x';
    } // ends the parameterized King constructor

    /**
     * Will check to make sure that the new move is valid for the King
     * @param board	=> the chess board
     * @param oldX => the initial x position of the piece
     * @param oldY => the initial y position of the piece
     * @param newX => the final x position of the piece
     * @param newY => the final y position of the piece
     * @return => true if the move is valid | false if the move is not valid
     */
    public boolean isValidMove(Piece[][] board, int oldX, int oldY , int newX , int newY) {
        // it is the white king
        if (this.isWhite == true) {
            // deal with castling
            // char = a
            if ((oldX == 7) && (oldY == 4) && (newX == 7) && (newY == 6) && (board[oldX][oldY] != null) && (board[oldX][oldY] instanceof King) && (board[oldX][oldY].hadFirstMove == false) && (board[7][7] != null) && (board[7][7] instanceof Rook) && (board[7][7].hadFirstMove == false) && (Board.isEmpty(board,7,5)) && (Board.isEmpty(board,7,6))) {
                if ((!this.phantomMove(board,7,4,7,5)) && (!this.phantomMove(board,7,4,7,6))) {
                    King pointer = (King)board[7][4];
                    pointer.castle = 'a';
                    return true;
                }
            }
            // char = b
            if ((oldX == 7) && (oldY == 4) && (newX == 7) && (newY == 2) && (board[oldX][oldY] != null) && (board[oldX][oldY] instanceof King) && (board[oldX][oldY].hadFirstMove == false) && (board[7][0] != null) && (board[7][0] instanceof Rook) && (board[7][0].hadFirstMove == false) && (Board.isEmpty(board,7,3)) && (Board.isEmpty(board,7,2)) && (Board.isEmpty(board,7,1)) ) {
                if ((!this.phantomMove(board,7,4,7,3)) && (!this.phantomMove(board,7,4,7,2))) {
                    King pointer = (King)board[7][4];
                    pointer.castle = 'b';
                    return true;
                }
            }

            // deal with northwest
            if ((newX == oldX - 1) && (newY == oldY - 1)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with north
            } else if ((newX == oldX - 1) && (newY == oldY)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with northeast
            } else if ((newX == oldX - 1) && (newY == oldY + 1)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with east
            } else if ((newX == oldX) && (newY == oldY + 1)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with southeast
            } else if ((newX == oldX + 1) && (newY == oldY + 1)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with south
            } else if ((newX == oldX + 1) && (newY == oldY)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with southwest
            } else if ((newX == oldX + 1) && (newY == oldY - 1)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with west
            } else if ((newX == oldX) && (newY == oldY - 1)){
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
            // it is the black king
        } else {
            // deal with castling
            // char = c
            if ((oldX == 0) && (oldY == 4) && (newX == 0) && (newY == 6) && (board[oldX][oldY] != null) && (board[oldX][oldY] instanceof King) && (board[oldX][oldY].hadFirstMove == false) && (board[0][7] != null) && (board[0][7] instanceof Rook) && (board[0][7].hadFirstMove == false) && (Board.isEmpty(board,0,5)) && (Board.isEmpty(board,0,6))) {
                if ((!this.phantomMove(board,0,4,0,5)) && (!this.phantomMove(board,0,4,0,6))) {
                    King pointer = (King)board[0][4];
                    pointer.castle = 'c';
                    return true;
                }
            }
            // char = d
            if ((oldX == 0) && (oldY == 4) && (newX == 0) && (newY == 2) && (board[oldX][oldY] != null) && (board[oldX][oldY] instanceof King) && (board[oldX][oldY].hadFirstMove == false) && (board[0][0] != null) && (board[0][0] instanceof Rook) && (board[0][0].hadFirstMove == false) && (Board.isEmpty(board,0,3)) && (Board.isEmpty(board,0,2)) && (Board.isEmpty(board,0,1)) ) {
                if ((!this.phantomMove(board,0,4,0,3)) && (!this.phantomMove(board,0,4,0,2))) {
                    King pointer = (King)board[0][4];
                    pointer.castle = 'd';
                    return true;
                }
            }

            // deal with northwest
            if ((newX == oldX + 1) && (newY == oldY + 1)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with north
            } else if ((newX == oldX + 1) && (newY == oldY)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with northeast
            } else if ((newX == oldX + 1) && (newY == oldY - 1)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with east
            } else if ((newX == oldX) && (newY == oldY - 1)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with southeast
            } else if ((newX == oldX - 1) && (newY == oldY - 1)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with south
            } else if ((newX == oldX - 1) && (newY == oldY)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with southwest
            } else if ((newX == oldX - 1) && (newY == oldY + 1)) {
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                // deal with west
            } else if ((newX == oldX) && (newY == oldY + 1)){
                if (!Board.isEmpty(board, newX, newY)) {
                    if ((board[newX][newY].isWhite != this.isWhite) && (!this.phantomMove(board, oldX, oldY, newX, newY))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (!this.phantomMove(board, oldX, oldY, newX, newY)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    } // ends the isValidMove() method

    /**
     * Will play the move on another board and check if the king is safe after the move is conducted
     *  @param board	=> the chess board
     * @param oldX => the initial x position of the piece
     * @param oldY => the initial y position of the piece
     * @param newX => the final x position of the piece
     * @param newY => the final y position of the piece
     * @return => true if the king is in check after the move | false if the king is not in check or danger after the move
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
        King king = (King) updatedBoard[newX][newY];
        return king.isInCheck(updatedBoard, newX, newY, team);
    } // ends the phantomMove() method

    /**
     * This method will make the move that the player wants. Assumes that isValidMove() = true;
     *  @param board	=> the chess board
     * @param oldX => the initial x position of the piece
     * @param oldY => the initial y position of the piece
     * @param newX => the final x position of the piece
     * @param newY => the final y position of the piece
     * @return => a new board with the King moved will be done
     */
    public Piece[][] makeMove(Piece[][] board, int oldX, int oldY , int newX , int newY) {
        King pointer = (King)board[oldX][oldY];

        if (pointer.castle == 'x') {
            board[newX][newY] = null;
            board[newX][newY] = new King(newX,newY,board[oldX][oldY].isWhite);
            board[newX][newY].hadFirstMove = true;
            board[oldX][oldY] = null;
            return board;
        } else if (pointer.castle == 'a') {
            board[7][6] = new King(newX,newY,board[oldX][oldY].isWhite);
            King pointer2 = (King)board[7][6];
            pointer2.isWhite = true;
            pointer2.hadFirstMove = true;
            pointer2.castle = 'x';
            board[7][4] = null;
            board[7][5] = new Rook(7,5,board[7][7].isWhite);
            board[7][5].hadFirstMove = true;
            board[7][7] = null;
            return board;
        } else if (pointer.castle == 'b') {
            board[7][2] = new King(newX,newY,board[oldX][oldY].isWhite);
            King pointer2 = (King)board[7][2];
            pointer2.isWhite = true;
            pointer2.hadFirstMove = true;
            pointer2.castle = 'x';
            board[7][4] = null;
            board[7][3] = new Rook(7,3,board[7][0].isWhite);
            board[7][3].hadFirstMove = true;
            board[7][0] = null;
            return board;
        } else if (pointer.castle == 'c') {
            board[0][6] = new King(newX,newY,false);
            King pointer2 = (King)board[0][6];
            pointer2.isWhite = false;
            pointer2.hadFirstMove = true;
            pointer2.castle = 'x';
            board[0][4] = null;
            board[0][5] = new Rook(0,5,false);
            board[0][5].isWhite = false;
            board[0][5].hadFirstMove = true;
            board[0][7] = null;
            return board;
        } else {
            board[0][2] = new King(newX,newY,false);
            King pointer2 = (King)board[0][2];
            pointer2.isWhite = false;;
            pointer2.hadFirstMove = true;
            pointer2.castle = 'x';
            board[0][4] = null;
            board[0][3] = new Rook(0,3,false);
            board[0][3].isWhite = false;
            board[0][3].hadFirstMove = true;
            board[0][0] = null;
            return board;
        }
    } // ends the makeMove() method

    /**
     * Will check to see if the king piece is in check
     * @param board	=> the chess board
     * @param x	=> x coordinate for the king
     * @param y	=> y coordinate for the king
     * @param team => the color of the current piece
     * @return => true if the king is in check, false otherwise
     */
    public boolean isInCheck(Piece[][] board, int x, int y , boolean team) {
        // check for Rook/Queen (from north)
        for (int i = x-1; i >= 0; i--) {
            if (!Board.isEmpty(board, i, y)) {
                if (((board[i][y] instanceof Rook) || (board[i][y] instanceof Queen)) && (board[i][y].isWhite != team)) {
                    return true;
                } else {
                    break; // break out of this for-loop to go to the next for-loop
                }
            }
        }
        // check for Rook/Queen (from east)
        for (int i = y+1; i <= 7; i++) {
            if (!Board.isEmpty(board, x, i)) {
                if (((board[x][i] instanceof Rook) || (board[x][i] instanceof Queen)) && (board[x][i].isWhite != team)) {
                    return true;
                } else {
                    break; // break out of this for-loop to go to the next for-loop
                }
            }
        }
        // check for Rook/Queen (from south)
        for (int i = x+1; i <= 7; i++) {
            if (!Board.isEmpty(board, i, y)) {
                if (((board[i][y] instanceof Rook) || (board[i][y] instanceof Queen)) && (board[i][y].isWhite != team)) {
                    return true;
                } else {
                    break; // break out of this for-loop to go to the next for-loop
                }
            }
        }
        // check for Rook/Queen (from west)
        for (int i = y-1; i >= 0; i--) {
            if (!Board.isEmpty(board, x, i)) {
                if (((board[x][i] instanceof Rook) || (board[x][i] instanceof Queen)) && (board[x][i].isWhite != team)) {
                    return true;
                } else {
                    break; // break out of this for-loop to go to the next for-loop
                }
            }
        }
        // check for Bishop/Queen (from northeast)
        for (int i = x+1, j = y-1; i <= 7 && j >= 0; i++, j--) {
            if (!Board.isEmpty(board, i, j)) {
                if (((board[i][j] instanceof Bishop) || (board[i][j] instanceof Queen)) && (board[i][j].isWhite != team)) {
                    return true;
                } else {
                    break;
                }
            }
        }
        // check for Bishop/Queen (from southeast)
        for (int i = x+1, j = y+1; i <= 7 && j <= 7; i++, j++) {
            if (!Board.isEmpty(board, i, j)) {
                if (((board[i][j] instanceof Bishop) || (board[i][j] instanceof Queen)) && (board[i][j].isWhite != team)) {
                    return true;
                } else {
                    break;
                }
            }
        }
        // check for Bishop/Queen (from southwest)
        for (int i = x-1, j = y+1; i >= 0 && j <= 7; i--, j++) {
            if (!Board.isEmpty(board, i, j)) {
                if (((board[i][j] instanceof Bishop) || (board[i][j] instanceof Queen)) && (board[i][j].isWhite != team)) {
                    return true;
                } else {
                    break;
                }
            }
        }
        // check for Bishop/Queen (from northwest)
        for (int i = x-1, j = y-1; i >= 0 && j >= 0; i--, j--) {
            if (!Board.isEmpty(board, i, j)) {
                if (((board[i][j] instanceof Bishop) || (board[i][j] instanceof Queen)) && (board[i][j].isWhite != team)) {
                    return true;
                } else {
                    break;
                }
            }
        }
        // white King check for black pawn
        if (team == true) {
            if (((!Board.isEmpty(board, x-1, y-1)) && (board[x-1][y-1] instanceof Pawn) && (board[x-1][y-1].isWhite != team)) || ((!Board.isEmpty(board, x-1, y+1)) && (board[x-1][y+1] instanceof Pawn) && (board[x-1][y+1].isWhite != team))) {
                return true;
            }
            // black King check for white pawn
        } else {
            if (((!Board.isEmpty(board, x+1, y-1)) && (board[x+1][y-1] instanceof Pawn) && (board[x+1][y-1].isWhite != team)) || ((!Board.isEmpty(board, x+1, y+1)) && (board[x+1][y+1] instanceof Pawn) && (board[x+1][y+1].isWhite != team))) {
                return true;
            }
        }
        // check for knights (check all possible postions)
        if ((!Board.isEmpty(board, x+2, y+1)) && (board[x+2][y+1] instanceof Knight) && (board[x+2][y+1].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x+2, y-1)) && (board[x+2][y-1] instanceof Knight) && (board[x+2][y-1].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x-2, y+1)) && (board[x-2][y+1] instanceof Knight) && (board[x-2][y+1].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x-2, y-1)) && (board[x-2][y-1] instanceof Knight) && (board[x-2][y-1].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x+1, y+2)) && (board[x+1][y+2] instanceof Knight) && (board[x+1][y+2].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x+1, y-2)) && (board[x+1][y-2] instanceof Knight) && (board[x+1][y-2].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x-1, y+2)) && (board[x-1][y+2] instanceof Knight) && (board[x-1][y+2].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x-1, y-2)) && (board[x-1][y-2] instanceof Knight) && (board[x-1][y-2].isWhite != team)) {
            return true;
        }
        // check for king (this will be used later when checking for checkmate/stalemate when trying to move the King around)
        // King
        if ((!Board.isEmpty(board, x+1, y)) && (board[x+1][y] instanceof King) && (board[x+1][y].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x-1, y)) && (board[x-1][y] instanceof King) && (board[x-1][y].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x, y+1)) && (board[x][y+1] instanceof King) && (board[x][y+1].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x, y-1)) && (board[x][y-1] instanceof King) && (board[x][y-1].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x+1, y+1)) && (board[x+1][y+1] instanceof King) && (board[x+1][y+1].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x-1, y+1)) && (board[x-1][y+1] instanceof King) && (board[x-1][y+1].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x+1, y-1)) && (board[x+1][y-1] instanceof King) && (board[x+1][y-1].isWhite != team)) {
            return true;
        }
        if ((!Board.isEmpty(board, x-1, y-1)) && (board[x-1][y-1] instanceof King) && (board[x-1][y-1].isWhite != team)) {
            return true;
        }
        return false;
    } // ends the isInCheck()
    /**
     * @param board => the chess board
     * @param x	=> x coordinate for the king
     * @param y	=> y coordinate for the king
     * @param team => the color of the current piece
     * @return => true if the king is in check-mate , false otherwise
     */
    public boolean isInCheckMate(Piece[][] board, int x, int y , boolean team) {
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;

        for (i = 0 ; i < 8 ; i++) {
            for (j = 0 ; j < 8 ; j++) {
                if ((board[i][j] != null) && (board[i][j].isWhite == team)) {
                    for (k = 0 ; k < 8 ; k++) {
                        for (l = 0 ; l < 8 ; l++) {
                            if (board[i][j].isValidMove(board,i,j,k,l)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    } // ends the isInCheckMate() method
    /**
     * @param board > the chess board
     * @param x	=> x coordinate for the king
     * @param y	=> y coordinate for the king
     * @param team => the color of the current piece
     * @return => true if the king is in stale-mate, false otherwise
     */
    public boolean isInStalemate(Piece[][] board, int x, int y , boolean team) {
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;

        for (i = 0 ; i < 8 ; i++) {
            for (j = 0 ; j < 8 ; j++) {
                if ((board[i][j] != null) && (board[i][j].isWhite == team)) {
                    for (k = 0 ; k < 8 ; k++) {
                        for (l = 0 ; l < 8 ; l++) {
                            if (board[i][j].isValidMove(board,i,j,k,l)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    } // ends the isInStalemate() method
} // ends the King class