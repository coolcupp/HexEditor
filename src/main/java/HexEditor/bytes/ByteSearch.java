package HexEditor.bytes;

import HexEditor.table.CustomTable;
import HexEditor.gui.TableSelectionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ByteSearch {
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField searchField;
    private final JRadioButton exactMatchRadioButton;
    private final JRadioButton maskMatchRadioButton;
    private final TableSelectionHandler tableSelectionHandler; // Добавляем ссылку на TableSelectionHandler

    public ByteSearch(JTable table, JTextField searchField, JRadioButton exactMatchRadioButton,
                      JRadioButton maskMatchRadioButton, TableSelectionHandler tableSelectionHandler) {
        this.table = table;
        this.tableModel = (DefaultTableModel) table.getModel();
        this.searchField = searchField;
        this.exactMatchRadioButton = exactMatchRadioButton;
        this.maskMatchRadioButton = maskMatchRadioButton;
        this.tableSelectionHandler = tableSelectionHandler; // Инициализация
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
        tableSelectionHandler.highlightTextInTextArea(); // Вызываем подсветку в JTextArea
    }

    private List<Point> searchExactMatch(String input) {
        List<Point> foundCells = new ArrayList<>();
        String[] exactParts = input.split(" ");

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            for (int col = 2; col <= tableModel.getColumnCount() - exactParts.length; col++) {
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
                        foundCells.add(new Point(col + i, row));
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
            for (int col = 2; col <= tableModel.getColumnCount() - maskParts.length; col++) {
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
                        foundCells.add(new Point(col + i, row));
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
}
