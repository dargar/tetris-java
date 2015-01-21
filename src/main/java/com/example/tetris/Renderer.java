package com.example.tetris;

import com.example.tetris.fonts.Numeric;
import com.example.tetris.pieces.AbstractPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.List;

import static com.example.tetris.Tetris.ROWS;
import static com.example.tetris.Tetris.COLUMNS;
import static com.example.tetris.Tetris.TILE_SIZE;

public class Renderer extends JFrame implements Runnable, KeyListener {

    private Tetris tetris;
    private BufferStrategy bs;

    public Renderer(Tetris tetris) {
        this.tetris = tetris;
    }

    @Override
    public void run() {
        Dimension size = new Dimension(530, 768);

        setTitle("Tetris");
        addKeyListener(this);

        Canvas canvas = new Canvas();
        canvas.setPreferredSize(size);

        add(canvas, BorderLayout.CENTER);

        pack();
        setVisible(true);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
    }

    public void render(long tick, int[][] board,
                       int linesCleared,
                       Optional<AbstractPiece> activePiece,
                       AbstractPiece nextPiece,
                       Optional<AbstractPiece> heldPiece) {
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        int boardOffsetX = 40;
        int boardOffsetY = 40;

        // Clear background
        Rectangle rect = g.getDeviceConfiguration().getBounds();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, rect.width, rect.height);

        // Find any completed rows
        List<Integer> completedRowIndices = new ArrayList<>();
        for (int r = 0; r < ROWS; r++) {
            boolean completedRow = true;
            for (int c = 0; c < COLUMNS; c++) {
                if (board[r][c] == 0) {
                    completedRow = false;
                    break;
                }
            }
            if (completedRow) {
                completedRowIndices.add(r);
            }
        }

        // Outline board
        g.setColor(new Color(0x33_33_33));
        g.fillRect(boardOffsetX - 10, boardOffsetY - 10, COLUMNS * TILE_SIZE + 20, ROWS * TILE_SIZE + 20);

        // Draw board background
        g.setColor(Color.BLACK);
        g.fillRect(boardOffsetX, boardOffsetY, COLUMNS * TILE_SIZE, ROWS * TILE_SIZE);

        // Draw existing blocks on board
        for (int r = 0; r < ROWS; r++) {
            int alpha;
            if (completedRowIndices.contains(r)) {
                alpha = (int) (Math.abs(Math.sin((double) tick / 100)) * 255);
            } else {
                alpha = 0xFF;
            }
            for (int c = 0; c < COLUMNS; c++) {
                if (board[r][c] != 0) {
                    drawBlock(g, board[r][c], alpha, TILE_SIZE, boardOffsetX + c * TILE_SIZE, boardOffsetY + r * TILE_SIZE);
                }
            }
        }

        // Draw active piece
        activePiece.ifPresent(piece -> {
            for (int[] block : piece.getPieceCoords()) {
                int r = block[1];
                int c = block[0];
                drawBlock(g, piece.getColourRGB(), 0xFF, TILE_SIZE, boardOffsetX + c * TILE_SIZE, boardOffsetY + r * TILE_SIZE);
            }
        });

        // Draw side panel
        int sidePanelOffsetX = boardOffsetX + TILE_SIZE * COLUMNS + 40;
        int sidePanelSize = 4 * TILE_SIZE;

        g.setColor(Color.BLACK);
        g.fillRect(sidePanelOffsetX, boardOffsetY, sidePanelSize, sidePanelSize);

        for (int[] block : nextPiece.getPieceCoords()) {
            int r = block[1];
            int c = block[0];
            drawBlock(g, nextPiece.getColourRGB(), 0xFF, TILE_SIZE, sidePanelOffsetX + c * TILE_SIZE, boardOffsetY + r * TILE_SIZE);
        }

        int holdPanelOffsetY = boardOffsetY + sidePanelSize + 40;

        g.setColor(Color.BLACK);
        g.fillRect(sidePanelOffsetX, holdPanelOffsetY, sidePanelSize, sidePanelSize);

        heldPiece.ifPresent(piece -> {
            for (int[] block : piece.getPieceCoords()) {
                int r = block[1];
                int c = block[0];
                drawBlock(g, piece.getColourRGB(), 0xFF, TILE_SIZE, sidePanelOffsetX + c * TILE_SIZE, holdPanelOffsetY + r * TILE_SIZE);
            }
        });

        int linesClearedPanelOffsetY = holdPanelOffsetY + sidePanelSize + 40;

        g.fillRect(sidePanelOffsetX, linesClearedPanelOffsetY, sidePanelSize, sidePanelSize);

        int fontWidth  = 3 * 10;
        int fontHeight = 5 * 10;
        int linesClearedX = sidePanelOffsetX + 3 * fontWidth;
        String linesClearedString = Integer.toString(linesCleared);
        linesClearedString = new StringBuilder(linesClearedString).reverse().toString();
        for (char c : linesClearedString.toCharArray()) {
            int[][] number = Numeric.numbers[Integer.parseInt(Character.toString(c))];
            g.fillRect(linesClearedX, linesClearedPanelOffsetY, fontWidth, fontHeight);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    if (number[i][j] == 1) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.fillRect(linesClearedX + j * 10, linesClearedPanelOffsetY + i * 10, 10, 10);
                }
            }
            linesClearedX -= fontWidth + 5;
        }

        // Clean up and flip
        g.dispose();
        bs.show();
    }

    private void drawBlock(Graphics2D g, int rgb, int alpha, int size, int x, int y) {
        int red   = (rgb & 0xFF0000) >> 16;
        int green = (rgb & 0x00FF00) >> 8;
        int blue  = (rgb & 0x0000FF);
        g.setColor(new Color(red, green, blue, alpha));
        g.fillRect(x, y, size, size);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        tetris.input(e.getKeyCode());
    }
}
