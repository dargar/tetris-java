package com.example.tetris.pieces;

public class T extends AbstractPiece {

    private int[][][] pieceRotationsT = {
            {{0,1,0},{1,1,1},{0,0,0}},
            {{0,1,0},{0,1,1},{0,1,0}},
            {{0,0,0},{1,1,1},{0,1,0}},
            {{0,1,0},{1,1,0},{0,1,0}},
    };

    public T() {
        super(0x660033);
        this.pieceRotations = pieceRotationsT;
    }

}
