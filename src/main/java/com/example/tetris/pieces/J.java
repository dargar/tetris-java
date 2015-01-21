package com.example.tetris.pieces;

public class J extends AbstractPiece {

    private int[][][] pieceRotationsJ = {
            {{1,0,0},{1,1,1},{0,0,0}},
            {{0,1,1},{0,1,0},{0,1,0}},
            {{0,0,0},{1,1,1},{0,0,1}},
            {{0,1,0},{0,1,0},{1,1,0}},
    };

    public J() {
        super(0x0000ff);
        this.pieceRotations = pieceRotationsJ;
    }

}
