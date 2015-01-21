package com.example.tetris.pieces;

public class L extends AbstractPiece {

    private int[][][] pieceRotationsL = {
            {{0,0,1},{1,1,1},{0,0,0}},
            {{0,1,0},{0,1,0},{0,1,1}},
            {{0,0,0},{1,1,1},{1,0,0}},
            {{1,1,0},{0,1,0},{0,1,0}},
    };

    public L() {
        super(0xff6600);
        this.pieceRotations = pieceRotationsL;
    }

}
