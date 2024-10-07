package HexEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class CustomTable extends JTable {
    private int startRow = -1;
    private int startCol = -1;
    private Set<Point> selectedCells = new HashSet<>();
    private DefaultTableModel tableModel;

    public CustomTable(DefaultTableModel tableModel) {
        setModel(tableModel);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int col = columnAtPoint(e.getPoint());

                // Проверка, содержит ли ячейка значение и не является ли она одной из первых двух столбцов
                if (tableModel.getValueAt(row, col) != null && col > 1) {
                    startRow = row;
                    startCol = col;
                    selectedCells.clear();
                    selectedCells.add(new Point(startCol, startRow));
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                startRow = -1;
                startCol = -1;
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int col = columnAtPoint(e.getPoint());

                // Проверка, содержит ли ячейка значение и не является ли она одной из первых двух столбцов
                if (row == startRow && tableModel.getValueAt(row, col) != null && col > 1) {
                    selectedCells.clear();
                    int minCol = Math.min(startCol, col);
                    int maxCol = Math.max(startCol, col);
                    for (int c = minCol; c <= maxCol; c++) {
                        if (tableModel.getValueAt(row, c) != null && c > 1) {
                            selectedCells.add(new Point(c, row));
                        }
                    }
                    repaint();
                }
            }
        });
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (selectedCells.contains(new Point(column, row))) {
            c.setBackground(getSelectionBackground());
            c.setForeground(getSelectionForeground());
        } else {
            c.setBackground(getBackground());
            c.setForeground(getForeground());
        }
        return c;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Запрещаем редактирование первых двух столбцов
        return column > 1;
    }

    @Override
    public boolean getCellSelectionEnabled() {
        // Запрещаем выделение первых двух столбцов
        return true;
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        // Возвращаем стандартный рендерер, который не будет подсвечивать ячейки
        return getDefaultRenderer(getColumnClass(column));
    }

    public Set<Point> getSelectedCells() {
        return selectedCells;
    }
}