import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessBoard {
    static final int TILE_SIZE = 80;
    static final int BOARD_SIZE = 8;

    static JLabel draggedPiece = null;
    static Point dragOffset = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);

        // Board background
        ImageIcon boardImg = new ImageIcon("sprites/board/board.png");
        JLabel background = new JLabel(boardImg);
        background.setBounds(0, 0, TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);

        // Layered Pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE));

        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Add sample black pieces
        String[] pieceNames = { "BK", "BB", "BN", "BR", "BP", "BP", "BP" };
        int[] xPositions = { 4, 2, 1, 7, 0, 1, 2 };
        int[] yPositions = { 0, 0, 0, 0, 1, 1, 1 };

        for (int i = 0; i < pieceNames.length; i++) {
            String name = pieceNames[i];
            int x = xPositions[i];
            int y = yPositions[i];

            ImageIcon icon = new ImageIcon("sprites/sprites/simple/" + name + ".gif");
            JLabel piece = new JLabel(icon);
            piece.setBounds(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            addDragFunctionality(piece, layeredPane);
            layeredPane.add(piece, JLayeredPane.PALETTE_LAYER);
        }

        frame.add(layeredPane);
        frame.pack();
        frame.setVisible(true);
    }

    private static void addDragFunctionality(JLabel piece, JLayeredPane layeredPane) {
        piece.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                draggedPiece = piece;
                dragOffset = e.getPoint();
                layeredPane.moveToFront(piece);
            }

            public void mouseReleased(MouseEvent e) {
                if (draggedPiece != null) {
                    int x = (draggedPiece.getX() + TILE_SIZE / 2) / TILE_SIZE;
                    int y = (draggedPiece.getY() + TILE_SIZE / 2) / TILE_SIZE;

                    x = Math.max(0, Math.min(BOARD_SIZE - 1, x));
                    y = Math.max(0, Math.min(BOARD_SIZE - 1, y));

                    draggedPiece.setLocation(x * TILE_SIZE, y * TILE_SIZE);
                    draggedPiece = null;
                }
            }
        });

        piece.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (draggedPiece != null) {
                    int newX = draggedPiece.getX() + e.getX() - dragOffset.x;
                    int newY = draggedPiece.getY() + e.getY() - dragOffset.y;
                    draggedPiece.setLocation(newX, newY);
                }
            }
        });
    }
}
