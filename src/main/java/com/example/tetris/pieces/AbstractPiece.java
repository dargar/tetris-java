package com.example.tetris.pieces;

import com.example.tetris.Direction;

public abstract class AbstractPiece {

    private int rotationIndex;
    protected int[][][] pieceRotations;

    private int row;
    private int column;

    private int colourRGB;

    public AbstractPiece(int colourRGB) {
        this.rotationIndex = 0;
        this.row = 0;
        this.column = 0;
        this.colourRGB = colourRGB;
    }

    public void move(Direction direction) {
        switch (direction) {
            case LEFT:
                column--;
                break;
            case RIGHT:
                column++;
                break;
            case UP:
                row--;
                break;
            case DOWN:
                row++;
                break;
        }
    }

    public void rotate(Direction direction) {
        switch (direction) {
            case RIGHT:
                rotationIndex = (rotationIndex + 1) % pieceRotations.length;
                break;
            case LEFT:
                rotationIndex = (rotationIndex - 1) % pieceRotations.length;
                if (rotationIndex < 0) {
                    rotationIndex += pieceRotations.length;
                }
                break;
        }

    }

    public int[][] getPieceCoords() {
        int[][] coords = new int[4][2];
        int dimensions = pieceRotations[rotationIndex].length;
        int count = 0;
        for (int r = 0; r < dimensions; r++) {
            for (int c = 0; c < dimensions; c++) {
                if (pieceRotations[rotationIndex][r][c] == 1) {
                    coords[count][1] = r + row;
                    coords[count][0] = c + column;
                    count++;
                }
            }
        }
        return coords;
    }

    public int getColourRGB() {
        return colourRGB;
    }

}
