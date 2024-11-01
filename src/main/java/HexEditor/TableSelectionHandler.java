//////package HexEditor;
//////
//////import javax.swing.*;
//////import javax.swing.event.ListSelectionEvent;
//////import javax.swing.event.ListSelectionListener;
//////import javax.swing.table.DefaultTableModel;
//////
//////public class TableSelectionHandler implements ListSelectionListener {
//////    private JTable table;
//////    private JTextArea textArea;
//////
//////    public TableSelectionHandler(JTable table, JTextArea textArea) {
//////        this.table = table;
//////        this.textArea = textArea;
//////
//////        // Добавляем слушатель выделения к таблице
//////        table.getSelectionModel().addListSelectionListener(this);
//////        table.getColumnModel().getSelectionModel().addListSelectionListener(this);
//////    }
//////
//////    @Override
//////    public void valueChanged(ListSelectionEvent e) {
//////        // Проверяем, что событие не вызвано программно
//////        if (e.getValueIsAdjusting()) return;
//////
//////        // Получаем выбранные ячейки
//////        int[] selectedRows = table.getSelectedRows();
//////        int[] selectedColumns = table.getSelectedColumns();
//////
//////        // Если выделение есть, обрабатываем его
//////        if (selectedRows.length > 0 && selectedColumns.length > 0) {
//////            // Предполагаем, что мы берем первую выделенную ячейку
//////            int row = selectedRows[0];
//////            int col = selectedColumns[0];
//////
//////            // Получаем порядковый номер символа
//////            int charIndex = (row * (table.getColumnCount() - 2)) + (col - 2); // Учитываем номер столбца и строку
//////
//////            // Получаем текст из textArea и выделяем нужный символ
//////            String text = textArea.getText();
//////            if (charIndex >= 0 && charIndex < text.length()) {
//////                // Создаем выделение
//////                textArea.select(charIndex, charIndex + 1); // Выделяем один символ
//////                textArea.requestFocus(); // Фокусируемся на textArea
//////            }
//////        }
//////    }
//////}
////
////package HexEditor;
////
////import javax.swing.*;
////import javax.swing.event.ListSelectionEvent;
////import javax.swing.event.ListSelectionListener;
////
////public class TableSelectionHandler implements ListSelectionListener {
////    private JTable table;
////    private JTextArea textArea;
////
////    public TableSelectionHandler(JTable table, JTextArea textArea) {
////        this.table = table;
////        this.textArea = textArea;
////
////        // Добавляем слушатель выделения к таблице
////        table.getSelectionModel().addListSelectionListener(this);
////        table.getColumnModel().getSelectionModel().addListSelectionListener(this);
////    }
////
////    @Override
////    public void valueChanged(ListSelectionEvent e) {
////        // Проверяем, что событие не вызвано программно
////        if (e.getValueIsAdjusting()) return;
////
////        // Получаем выбранные ячейки
////        int[] selectedRows = table.getSelectedRows();
////        int[] selectedColumns = table.getSelectedColumns();
////
////        // Если выделение есть, обрабатываем его
////        if (selectedRows.length > 0 && selectedColumns.length > 0) {
////            // Находим границы выделения
////            int minRow = selectedRows[0];
////            int maxRow = selectedRows[selectedRows.length - 1];
////            int minCol = selectedColumns[0];
////            int maxCol = selectedColumns[selectedColumns.length - 1];
////
////            // Вычисляем начальный и конечный индексы символов
////            int startCharIndex = minRow * (table.getColumnCount() - 2) + (minCol - 2);
////            int endCharIndex = (maxRow * (table.getColumnCount() - 2) + (maxCol - 2)) + 1; // Включительно
////
////            // Получаем текст из textArea и выделяем нужный диапазон
////            String text = textArea.getText();
////            if (startCharIndex >= 0 && endCharIndex <= text.length()) {
////                // Создаем выделение
////                textArea.select(startCharIndex, endCharIndex); // Выделяем диапазон символов
////                textArea.requestFocus(); // Фокусируемся на textArea
////            }
////        }
////    }
////}
//
//package HexEditor;
//
//import javax.swing.*;
//import javax.swing.text.BadLocationException;
//import javax.swing.text.DefaultHighlighter;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class TableSelectionHandler {
//    private JTable table;
//    private JTextArea textArea;
//
//    public TableSelectionHandler(JTable table, JTextArea textArea) {
//        this.table = table;
//        this.textArea = textArea;
//        addSelectionListener();
//    }
//
//    private void addSelectionListener() {
//        table.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                highlightTextInTextArea();
//            }
//        });
//    }
//
//    public void highlightTextInTextArea() {
//        // Очистка выделения в текстовой области
//        textArea.getHighlighter().removeAllHighlights();
//
//        for (Point cell : ((CustomTable) table).getSelectedCells()) {
//            int row = cell.y;
//            int col = cell.x;
//
//            // Проверяем, что ячейка содержит значение
//            Object value = table.getValueAt(row, col);
//            if (value instanceof String && !((String) value).isEmpty()) {
//                // Получаем текстовый эквивалент
//                int charIndex = (row * (table.getColumnCount() - 2)) + (col - 2); // Рассчитываем индекс символа
//
//                // Выделяем соответствующий символ в JTextArea
//                try {
//                    textArea.getHighlighter().addHighlight(charIndex, charIndex + 1,
//                            new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
//                } catch (BadLocationException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
//

package HexEditor;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableSelectionHandler {
    private JTable table;
    private JTextArea textArea;

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
