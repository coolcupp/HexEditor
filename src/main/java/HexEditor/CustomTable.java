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
    private int endRow = -1;
    private int endCol = -1;
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
                endRow = -1;
                endCol = -1;
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int col = columnAtPoint(e.getPoint());

                // Проверка, содержит ли ячейка значение и не является ли она одной из первых двух столбцов
                if (row >= 0 && row < getRowCount() && col >= 0 && col < getColumnCount() &&
                        tableModel.getValueAt(row, col) != null && col > 1) {

                    endRow = row;
                    endCol = col;
                    selectedCells.clear();

                    // Определение начальных и конечных точек
                    int minRow = Math.min(startRow, endRow);
                    int maxRow = Math.max(startRow, endRow);
                    int minCol = Math.min(startCol, endCol);
                    int maxCol = Math.max(startCol, endCol);

                    // Заполнение выделенных ячеек
                    for (int r = minRow; r <= maxRow; r++) {
                        int start = (r == minRow) ? minCol : 2; // Начало от minCol на первой строке, иначе с колонки 2
                        int end = (r == maxRow) ? maxCol : getColumnCount() - 1; // Конец на maxCol на последней строке, иначе до последнего столбца
                        for (int c = start; c <= end; c++) {
                            if (tableModel.getValueAt(r, c) != null && c > 1) {
                                selectedCells.add(new Point(c, r));
                            }
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
        // Запрет редактирования первых двух столбцов
        return column > 1;
    }

    @Override
    public boolean getCellSelectionEnabled() {
        // Разрешаем выделение ячеек
        return true;
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        // Возвращаем стандартный рендерер без подсветки
        return getDefaultRenderer(getColumnClass(column));
    }

    public Set<Point> getSelectedCells() {
        return selectedCells;
    }
}
