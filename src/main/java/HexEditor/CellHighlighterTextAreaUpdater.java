package HexEditor;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

public class CellHighlighterTextAreaUpdater implements TableModelListener {
    private JTextArea textArea;
    private JTable table;
    private Highlighter highlighter;
    private int dataColumnsCount;

    public CellHighlighterTextAreaUpdater(JTextArea textArea, JTable table) {
        this.textArea = textArea;
        this.table = table;
        this.highlighter = new Highlighter(textArea);
        this.textArea.setEditable(false);

        // Initialize dataColumnsCount based on table columns
        updateDataColumnsCount();

        // Add listener to tableModel
        table.getModel().addTableModelListener(this);

        // Add mouse listener to table for highlighting
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    highlightSelectedSymbols();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                highlightSelectedSymbols();
            }
        });

        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                highlightSelectedSymbols();
            }
        });
    }

    @Override
    public void tableChanged(TableModelEvent tableModelEvent) {
        updateTextArea();
        updateDataColumnsCount(); // Update the column count whenever the table changes
    }

    private void updateDataColumnsCount() {
        this.dataColumnsCount = table.getColumnCount() - 2; // Adjust based on table structure
    }

    private void updateTextArea() {
        StringBuilder sb = new StringBuilder();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 2; col < model.getColumnCount(); col++) { // Начинаем с 3-го столбца
                Object value = model.getValueAt(row, col);
                if (value instanceof String && !((String) value).isEmpty()) {
                    String hexValue = (String) value;
                    if (hexValue.matches("[0-9A-Fa-f]+")) {
                        int intValue = Integer.parseInt(hexValue, 16);
                        sb.append((intValue >= 32 && intValue <= 126) ? (char) intValue : '.');
                    }
                }
            }
        }
        textArea.setText(sb.toString());
    }

    public void highlightSelectedSymbols() {
        Set<Point> selectedCells = ((CustomTable) table).getSelectedCells();
        highlighter.clearHighlights();

        for (Point cell : selectedCells) {
            int row = cell.y;
            int col = cell.x;

            // Check if the cell is not empty and contains a value
            Object value = table.getValueAt(row, col);
            if (!(value instanceof String) || ((String) value).isEmpty()) {
                continue; // Skip empty cells
            }

            // Calculate character index
            int charIndex = (row * dataColumnsCount) + (col - 2); // Adjust for column offset

            // Check character index bounds
            if (charIndex >= 0 && charIndex < textArea.getText().length()) {
                highlighter.highlight(charIndex);
            }
        }

        // Move caret in JTextArea
        try {
            if (!selectedCells.isEmpty()) {
                Point firstCell = selectedCells.iterator().next();
                int firstCharIndex = (firstCell.y * dataColumnsCount) + (firstCell.x - 2);
                if (firstCharIndex >= 0 && firstCharIndex < textArea.getText().length()) {
                    textArea.setCaretPosition(firstCharIndex);
                    textArea.scrollRectToVisible(textArea.modelToView(firstCharIndex));
                }
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    // Separate class for highlighting
    private static class Highlighter {
        private JTextArea textArea;

        public Highlighter(JTextArea textArea) {
            this.textArea = textArea;
        }

        public void highlight(int charIndex) {
            try {
                textArea.getHighlighter().addHighlight(charIndex, charIndex + 1,
                        new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }

        public void clearHighlights() {
            textArea.getHighlighter().removeAllHighlights();
        }
    }
}
