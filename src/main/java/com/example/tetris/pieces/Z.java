package com.example.tetris.pieces;

public class Z extends AbstractPiece {

    private int[][][] pieceRotationsZ = {
            {
                    {0,1,1},
                    {1,1,0},
                    {0,0,0}
            },
            {
                    {0,1,0},
                    {0,1,1},
                    {0,0,1}
            },
            {
                    {0,0,0},
                    {0,1,1},
                    {1,1,0}
            },
            {
                    {1,0,0},
                    {1,1,0},
                    {0,1,0}
            },
    };

    public Z() {
        super(0xff0000);
        this.pieceRotations = pieceRotationsZ;
    }

}
