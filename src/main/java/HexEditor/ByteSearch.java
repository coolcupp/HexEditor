//package HexEditor;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ByteSearch {
//    private JTable table;
//    private DefaultTableModel tableModel;
//    private JTextField searchField;
//    private JRadioButton exactMatchRadioButton;
//    private JRadioButton maskMatchRadioButton;
//
//    public ByteSearch(JTable table, JTextField searchField, JRadioButton exactMatchRadioButton, JRadioButton maskMatchRadioButton) {
//        this.table = table;
//        this.tableModel = (DefaultTableModel) table.getModel();
//        this.searchField = searchField;
//        this.exactMatchRadioButton = exactMatchRadioButton;
//        this.maskMatchRadioButton = maskMatchRadioButton;
//    }
//
//    public void performSearch() {
//        String input = searchField.getText().trim();
//        List<Point> foundCells = new ArrayList<>();
//
//        if (exactMatchRadioButton.isSelected()) {
//            foundCells = searchExactMatch(input);
//        } else if (maskMatchRadioButton.isSelected()) {
//            foundCells = searchWithMask(input);
//        }
//
//        highlightFoundCells(foundCells);
//    }
//
//    private List<Point> searchExactMatch(String input) {
//        List<Point> foundCells = new ArrayList<>();
//        String[] exactParts = input.split(" "); // Разделяем ввод на части
//
//        for (int row = 0; row < tableModel.getRowCount(); row++) {
//            for (int col = 2; col <= tableModel.getColumnCount() - exactParts.length; col++) { // Учитываем длину массива
//                boolean matches = true;
//
//                for (int i = 0; i < exactParts.length; i++) {
//                    Object value = tableModel.getValueAt(row, col + i);
//                    if (value instanceof String) {
//                        String cellValue = (String) value;
//                        if (!cellValue.equals(exactParts[i])) {
//                            matches = false;
//                            break;
//                        }
//                    } else {
//                        matches = false;
//                        break;
//                    }
//                }
//
//                if (matches) {
//                    for (int i = 0; i < exactParts.length; i++) {
//                        foundCells.add(new Point(col + i, row)); // Добавляем все найденные ячейки
//                    }
//                }
//            }
//        }
//
//        return foundCells;
//    }
//
//    private List<Point> searchWithMask(String input) {
//        List<Point> foundCells = new ArrayList<>();
//        String[] maskParts = input.split(" ");
//
//        for (int row = 0; row < tableModel.getRowCount(); row++) {
//            for (int col = 2; col <= tableModel.getColumnCount() - maskParts.length; col++) { // Учитываем длину маски
//                boolean matches = true;
//
//                for (int i = 0; i < maskParts.length; i++) {
//                    Object value = tableModel.getValueAt(row, col + i);
//                    if (value instanceof String) {
//                        String cellValue = (String) value;
//                        String maskPart = maskParts[i];
//                        if (!matchesMask(cellValue, maskPart)) {
//                            matches = false;
//                            break;
//                        }
//                    } else {
//                        matches = false;
//                        break;
//                    }
//                }
//
//                if (matches) {
//                    for (int i = 0; i < maskParts.length; i++) {
//                        foundCells.add(new Point(col + i, row)); // Добавляем все найденные ячейки
//                    }
//                }
//            }
//        }
//
//        return foundCells;
//    }
//
//    private boolean matchesMask(String cellValue, String maskPart) {
//        return maskPart.equals("?") || cellValue.equals(maskPart);
//    }
//
//    private void highlightFoundCells(List<Point> foundCells) {
//        ((CustomTable) table).getSelectedCells().clear();
//
//        for (Point cell : foundCells) {
//            ((CustomTable) table).getSelectedCells().add(cell);
//        }
//
//        table.repaint();
//    }
//}

package HexEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ByteSearch {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JRadioButton exactMatchRadioButton;
    private JRadioButton maskMatchRadioButton;
    private JTextArea textArea; // Добавлено поле для JTextArea

    public ByteSearch(JTable table, JTextField searchField, JRadioButton exactMatchRadioButton,
                      JRadioButton maskMatchRadioButton, JTextArea textArea) {
        this.table = table;
        this.tableModel = (DefaultTableModel) table.getModel();
        this.searchField = searchField;
        this.exactMatchRadioButton = exactMatchRadioButton;
        this.maskMatchRadioButton = maskMatchRadioButton;
        this.textArea = textArea; // Инициализация JTextArea
    }

    public void performSearch() {
        String input = searchField.getText().trim();
        List<Point> foundCells = new ArrayList<>();

        if (exactMatchRadioButton.isSelected()) {
            foundCells = searchExactMatch(input);
        } else if (maskMatchRadioButton.isSelected()) {
            foundCells = searchWithMask(input);
        }

        highlightFoundCells(foundCells);
        highlightTextInTextArea(foundCells); // Выделение текста в JTextArea
    }

    private List<Point> searchExactMatch(String input) {
        List<Point> foundCells = new ArrayList<>();
        String[] exactParts = input.split(" "); // Разделяем ввод на части

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            for (int col = 2; col <= tableModel.getColumnCount() - exactParts.length; col++) { // Учитываем длину массива
                boolean matches = true;

                for (int i = 0; i < exactParts.length; i++) {
                    Object value = tableModel.getValueAt(row, col + i);
                    if (value instanceof String) {
                        String cellValue = (String) value;
                        if (!cellValue.equals(exactParts[i])) {
                            matches = false;
                            break;
                        }
                    } else {
                        matches = false;
                        break;
                    }
                }

                if (matches) {
                    for (int i = 0; i < exactParts.length; i++) {
                        foundCells.add(new Point(col + i, row)); // Добавляем все найденные ячейки
                    }
                }
            }
        }

        return foundCells;
    }

    private List<Point> searchWithMask(String input) {
        List<Point> foundCells = new ArrayList<>();
        String[] maskParts = input.split(" ");

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            for (int col = 2; col <= tableModel.getColumnCount() - maskParts.length; col++) { // Учитываем длину маски
                boolean matches = true;

                for (int i = 0; i < maskParts.length; i++) {
                    Object value = tableModel.getValueAt(row, col + i);
                    if (value instanceof String) {
                        String cellValue = (String) value;
                        String maskPart = maskParts[i];
                        if (!matchesMask(cellValue, maskPart)) {
                            matches = false;
                            break;
                        }
                    } else {
                        matches = false;
                        break;
                    }
                }

                if (matches) {
                    for (int i = 0; i < maskParts.length; i++) {
                        foundCells.add(new Point(col + i, row)); // Добавляем все найденные ячейки
                    }
                }
            }
        }

        return foundCells;
    }

    private boolean matchesMask(String cellValue, String maskPart) {
        return maskPart.equals("?") || cellValue.equals(maskPart);
    }

    private void highlightFoundCells(List<Point> foundCells) {
        ((CustomTable) table).getSelectedCells().clear();

        for (Point cell : foundCells) {
            ((CustomTable) table).getSelectedCells().add(cell);
        }

        table.repaint();
    }

    private void highlightTextInTextArea(List<Point> foundCells) {
        // Очистка выделения в текстовой области
        textArea.getHighlighter().removeAllHighlights();

        for (Point cell : foundCells) {
            int row = cell.y;
            int col = cell.x;

            // Проверяем, что ячейка содержит значение
            Object value = table.getValueAt(row, col);
            if (value instanceof String && !((String) value).isEmpty()) {
                // Получаем текстовый эквивалент
                int charIndex = (row * (tableModel.getColumnCount() - 2)) + (col - 2); // Рассчитываем индекс символа

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
