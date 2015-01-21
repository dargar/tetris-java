package com.example.tetris.pieces;

public class S extends AbstractPiece {

    private int[][][] pieceRotationsS = {
            {
                    {1,1,0},
                    {0,1,1},
                    {0,0,0}
            },
            {
                    {0,0,1},
                    {0,1,1},
                    {0,1,0}
            },
            {
                    {0,0,0},
                    {1,1,0},
                    {0,1,1}
            },
            {
                    {0,1,0},
                    {1,1,0},
                    {1,0,0}
            },
    };

    public S() {
        super(0x66ff33);
        this.pieceRotations = pieceRotationsS;
    }

}
