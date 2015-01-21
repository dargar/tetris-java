package com.example.tetris.pieces;

public class O extends AbstractPiece {

    private int[][][] pieceRotationsO = {
            {{1,1},{1,1}},
    };

    public O() {
        super(0xffff00);
        this.pieceRotations = pieceRotationsO;
    }

}
