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
    private int selectionMode = 0; // 0 - стандарт, 1 - 2 байта, 2 - 4 байта, 3 - 8 байт

    public CustomTable(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
        setModel(tableModel);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int col = columnAtPoint(e.getPoint());

                // Prevent selection if the column is less than 2
                if (tableModel.getValueAt(row, col) != null && col >= 2) {
                    startRow = row;
                    startCol = col;
                    selectedCells.clear();

                    if (selectionMode == 0) {
                        selectedCells.add(new Point(startCol, startRow)); // Выделяем только одну ячейку
                    } else {
                        selectCells(row, col); // Выделяем в зависимости от режима
                    }
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

                if (selectionMode == 0 && col >= 2) { // Ensure column is valid
                    if (row >= 0 && row < getRowCount() &&
                            tableModel.getValueAt(row, col) != null) {
                        selectedCells.clear();
                        if (startRow == row) {
                            fillRowSelection(startRow, startCol, col);
                        } else {
                            // Handle reverse selection
                            if (row < startRow) {
                                fillStandardSelection(row, col, startRow, startCol);
                            } else {
                                fillStandardSelection(startRow, startCol, row, col);
                            }
                        }
                        repaint();
                    }
                }
            }
        });
    }

    private void selectCells(int row, int col) {
        int count = 0;
        switch (selectionMode) {
            case 1: // 2 байта
                count = 2;
                break;
            case 2: // 4 байта
                count = 4;
                break;
            case 3: // 8 байт
                count = 8;
                break;
        }

        int currentCol = col;
        int currentRow = row;

        // Always add the first selected cell
        selectedCells.add(new Point(currentCol, currentRow));
        count--;

        while (count > 0) {
            if (currentCol < getColumnCount() - 1) {
                currentCol++;
                selectedCells.add(new Point(currentCol, currentRow));
            } else {
                // Move to the next row
                currentCol = 2; // Start from column 2
                currentRow++;
                if (currentRow < getRowCount() && currentCol < getColumnCount()) {
                    selectedCells.add(new Point(currentCol, currentRow));
                } else {
                    break; // Exit loop if out of bounds
                }
            }
            count--;
        }
    }

    private void fillRowSelection(int row, int startCol, int endCol) {
        int minCol = Math.min(startCol, endCol);
        int maxCol = Math.max(startCol, endCol);

        // Select cells only in one row
        for (int c = minCol; c <= maxCol; c++) {
            if (tableModel.getValueAt(row, c) != null && c >= 2) {
                selectedCells.add(new Point(c, row));
            }
        }
    }

    private void fillStandardSelection(int startRow, int startCol, int endRow, int endCol) {
        int minRow = Math.min(startRow, endRow);
        int maxRow = Math.max(startRow, endRow);
        int minCol = Math.min(startCol, endCol);
        int maxCol = Math.max(startCol, endCol);

        selectedCells.clear(); // Clear before new selection

        // Select all cells in range
        for (int r = minRow; r <= maxRow; r++) {
            if (r == startRow) {
                for (int c = startCol; c < getColumnCount(); c++) {
                    if (tableModel.getValueAt(r, c) != null && c >= 2) {
                        selectedCells.add(new Point(c, r));
                    }
                }
            } else if (r == maxRow) {
                for (int c = 2; c <= endCol; c++) {
                    if (tableModel.getValueAt(r, c) != null && c >= 2) {
                        selectedCells.add(new Point(c, r));
                    }
                }
            } else {
                for (int c = 2; c < getColumnCount(); c++) {
                    if (tableModel.getValueAt(r, c) != null) {
                        selectedCells.add(new Point(c, r));
                    }
                }
            }
        }
    }

    public void setSelectionMode(int mode) {
        this.selectionMode = mode;
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
        return column >= 2; // Cells in first two columns are not editable
    }

    @Override
    public boolean getCellSelectionEnabled() {
        return true;
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return getDefaultRenderer(getColumnClass(column));
    }

    public Set<Point> getSelectedCells() {
        return selectedCells;
    }
}
