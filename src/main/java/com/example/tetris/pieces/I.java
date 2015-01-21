package com.example.tetris.pieces;

public class I extends AbstractPiece {

    private int[][][] pieceRotationsI = {
        {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}},
        {{0,0,1,0},{0,0,1,0},{0,0,1,0},{0,0,1,0}},
        {{0,0,0,0},{0,0,0,0},{1,1,1,1},{0,0,0,0}},
        {{0,1,0,0},{0,1,0,0},{0,1,0,0},{0,1,0,0}},
    };

    public I() {
        super(0x00ffff);
        this.pieceRotations = pieceRotationsI;
    }

}
