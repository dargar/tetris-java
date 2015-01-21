package com.example.tetris;

import com.example.tetris.pieces.AbstractPiece;
import com.example.tetris.pieces.PieceMaker;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

public class Tetris {

    public static final int ROWS = 20;
    public static final int COLUMNS = 10;
    public static final int TILE_SIZE = 30;
    private static final long MIN_TICK_DURATION = 300;

    private Renderer renderer;
    private PieceMaker pieceMaker = new PieceMaker();

    private long tick = 0;
    private long tickDuration = 1000;
    private int linesCleared = 0;
    private boolean gameOver = false;
    private int[][] board = new int[ROWS][COLUMNS];
    private AbstractPiece nextPiece = pieceMaker.make();
    private Optional<AbstractPiece> activePiece = Optional.empty();
    private Optional<AbstractPiece> heldPiece = Optional.empty();

    public Tetris() {
        try {
            SwingUtilities.invokeAndWait(renderer = new Renderer(this));
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void start() throws InterruptedException {
        long currentTime = System.currentTimeMillis();
        while (!gameOver) {
            if (tick >= tickDuration) {
                update();
                tick = 0;
            }
            renderer.render(tick, board, linesCleared, activePiece, nextPiece, heldPiece);
            Thread.sleep(10);
            long timeSnap = System.currentTimeMillis();
            tick += timeSnap - currentTime;
            currentTime = timeSnap;
        }
    }

    public void input(int key) {
        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP: // Rotate piece right
                activePiece.ifPresent(piece -> {
                    piece.rotate(Direction.RIGHT);
                    if (isColliding(board, piece)) {
                        piece.rotate(Direction.LEFT);
                    }
                });
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                activePiece.ifPresent(piece -> {
                    piece.move(Direction.LEFT);
                    if (isColliding(board, piece)) {
                        piece.move(Direction.RIGHT);
                    }
                });
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                activePiece.ifPresent(piece -> {
                    piece.move(Direction.DOWN);
                    if (isColliding(board, piece)) {
                        piece.move(Direction.UP);
                    }
                });
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                activePiece.ifPresent(piece -> {
                    piece.move(Direction.RIGHT);
                    if (isColliding(board, piece)) {
                        piece.move(Direction.LEFT);
                    }
                });
                break;
            case KeyEvent.VK_SPACE: // Drop piece
                // TODO: Prevent player from perpetually drop-moving piece
                activePiece.ifPresent(piece -> {
                    while (!isColliding(board, piece)) {
                        piece.move(Direction.DOWN);
                    }
                    piece.move(Direction.UP);
                    tick = tickDuration / 2;
                });
                break;
            // TODO: Prevent player from cycling through next pieces
            case KeyEvent.VK_F: // Hold piece
                if (!heldPiece.isPresent()) {
                    heldPiece = Optional.of(nextPiece);
                    nextPiece = pieceMaker.make();
                }
                break;
            case KeyEvent.VK_G: // Release piece
                heldPiece.ifPresent(held -> nextPiece = held);
                heldPiece = Optional.empty();
                break;
        }
    }

    private void update() {
        if (!activePiece.isPresent() && Arrays.stream(board[0]).sum() != 0) {
            gameOver = true;
            System.exit(0);
        }

        if (linesCleared > 0 && linesCleared % 5 == 0) {
            tickDuration = Math.max(MIN_TICK_DURATION, tickDuration - 100);
        }

        if (!activePiece.isPresent()) {
            for (int r = 0; r < ROWS; r++) {
                boolean filled = true;
                for (int c = 0; c < COLUMNS; c++) {
                    if (board[r][c] == 0) {
                        filled = false;
                    }
                }
                if (filled) {
                    linesCleared++;
                    for (int c = 0; c < COLUMNS; c++) {
                        board[r][c] = 0;
                    }
                    for (int i = r - 1; i >= 0; i--) {
                        System.arraycopy(board[i], 0, board[i + 1], 0, COLUMNS);
                    }
                }
            }

            activePiece = Optional.of(nextPiece);
            nextPiece = pieceMaker.make();
            return;
        }

        AbstractPiece piece = activePiece.get();
        piece.move(Direction.DOWN);
        if (isColliding(board, piece)) {
            piece.move(Direction.UP);
            int r, c;
            for (int[] block : piece.getPieceCoords()) {
                r = block[1];
                c = block[0];
                board[r][c] = piece.getColourRGB();
            }
            activePiece = Optional.empty();
        }
    }

    public boolean isColliding(final int[][] board, final AbstractPiece piece) {
        int row, col;
        for (int[] block : piece.getPieceCoords()) {
            row = block[1];
            col = block[0];
            if (!(0 <= row && row < ROWS)) {
                return true;
            }
            if (!(0 <= col && col < COLUMNS)) {
                return true;
            }
            if (board[row][col] != 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        new Tetris().start();
    }
}
