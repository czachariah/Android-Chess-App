package com.example.chrischessapp;

public class MoveNode {
   public boolean isWhite;            // the color of the piece

    public int draw;               // does the current player want a draw
    public int resign;             // does the current player resign

    public int initialFile;        // the initial file of the piece
    public int initialRow;         // the initial row of the piece
    public int finalFile;          // the final file of the piece
    public int finalRow;           // the final row of the piece

    public MoveNode prev;          // the previous node in the LL
    public MoveNode next;          // tje next node in the LL

    public MoveNode() {
        // will fill in when making the node ...
    }


}
