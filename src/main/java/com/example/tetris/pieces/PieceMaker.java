package com.example.tetris.pieces;

import java.util.Random;

public class PieceMaker {

    private Random random;

    public PieceMaker() {
        random = new Random();
    }

    public AbstractPiece make() {
        int pieceIndex = random.nextInt(7);
        switch (pieceIndex) {
            case 0:
                return new I();
            case 1:
                return new T();
            case 2:
                return new S();
            case 3:
                return new Z();
            case 4:
                return new J();
            case 5:
                return new L();
            case 6:
                return new O();
            default:
                throw new RuntimeException(String.format("Piece index '%s' does not correspond to any producible piece.", pieceIndex));
        }
    }

}
