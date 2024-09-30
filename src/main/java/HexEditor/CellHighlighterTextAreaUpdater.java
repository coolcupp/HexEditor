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
public class CellHighlighterTextAreaUpdater implements TableModelListener {
    private JTextArea textArea;
    private JTable table;
    private Highlighter highlighter;
    private int dataColumnsCount;

    public CellHighlighterTextAreaUpdater(JTextArea textArea, JTable table) {
        this.textArea = textArea;
        this.table = table;
        this.highlighter = new Highlighter(textArea);
        this.dataColumnsCount = table.getColumnCount() - 2; // Количество столбцов с данными

        // add listener to tableModel
        table.getModel().addTableModelListener(this);

        // Add mouse listener to table for highlighting
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    highlightSymbol(e.getPoint());
                }
            }
        });
    }

    @Override
    public void tableChanged(TableModelEvent tableModelEvent) {
        updateTextArea();
    }

    private void updateTextArea() {
        StringBuilder sb = new StringBuilder();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 2; col < model.getColumnCount(); col++) { // Начинаем с 3-го столбца
                Object value = model.getValueAt(row, col);
                if (value instanceof String) {
                    try {
                        String hexValue = (String) value;
                        if (hexValue.matches("[0-9A-Fa-f]+")) {
                            int intValue = Integer.parseInt(hexValue, 16);
                            if (intValue >= 32 && intValue <= 126) {
                                sb.append((char) intValue);
                            } else {
                                sb.append('.');
                            }
                        }
                    } catch (NumberFormatException ex) {
                        sb.append('.');
                    }
                }
            }
        }
        textArea.setText(sb.toString());
    }

    private void highlightSymbol(Point point) {
        int row = table.rowAtPoint(point);
        int col = table.columnAtPoint(point);

        // Изменение расчета индекса символа
        int charIndex = (row * dataColumnsCount) + (col - 2); // Учитываем смещение столбцов

        // Highlight the corresponding character in JTextArea
        highlighter.highlight(charIndex);

        // Перемещение курсора в JTextArea
        try {
            textArea.setCaretPosition(charIndex); // Перемещение каретки в JTextArea
            textArea.scrollRectToVisible(textArea.modelToView(charIndex)); // Скроллинг до выделенного символа
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
            // Clear previous highlights
            textArea.getHighlighter().removeAllHighlights();

            try {
                // Apply the highlight
                textArea.getHighlighter().addHighlight(charIndex, charIndex + 1,
                        new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    }
}
