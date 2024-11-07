package HexEditor;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableSelectionHandler {
    private final JTable table;
    private final JTextArea textArea;

    public TableSelectionHandler(JTable table, JTextArea textArea) {
        this.table = table;
        this.textArea = textArea;
        addSelectionListener();
    }

    private void addSelectionListener() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                highlightTextInTextArea();
            }
        });
    }

    public void highlightTextInTextArea() {
        // Очистка выделения в текстовой области
        textArea.getHighlighter().removeAllHighlights();

        for (Point cell : ((CustomTable) table).getSelectedCells()) {
            int row = cell.y;
            int col = cell.x;

            // Проверяем, что ячейка содержит значение
            Object value = table.getValueAt(row, col);
            if (value instanceof String && !((String) value).isEmpty()) {
                // Получаем текстовый эквивалент
                int charIndex = (row * (table.getColumnCount() - 2)) + (col - 2); // Рассчитываем индекс символа

                // Выделяем соответствующий символ в JTextArea
                try {
                    textArea.getHighlighter().addHighlight(charIndex, charIndex + 1,
                            new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
