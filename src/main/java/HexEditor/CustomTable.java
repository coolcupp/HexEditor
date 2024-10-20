////////package HexEditor;
////////
////////import javax.swing.*;
////////import javax.swing.table.DefaultTableModel;
////////import javax.swing.table.TableCellRenderer;
////////import java.awt.*;
////////import java.awt.event.MouseAdapter;
////////import java.awt.event.MouseEvent;
////////import java.util.HashSet;
////////import java.util.Set;
////////
////////public class CustomTable extends JTable {
////////    private int startRow = -1;
////////    private int startCol = -1;
////////    private Set<Point> selectedCells = new HashSet<>();
////////    private DefaultTableModel tableModel;
////////    private int selectionMode = 0; // 0 - стандарт, 1 - 2 байта, 2 - 4 байта, 3 - 8 байт
////////
////////    public CustomTable(DefaultTableModel tableModel) {
////////        this.tableModel = tableModel;
////////        setModel(tableModel);
////////        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
////////
////////        addMouseListener(new MouseAdapter() {
////////            @Override
////////            public void mousePressed(MouseEvent e) {
////////                int row = rowAtPoint(e.getPoint());
////////                int col = columnAtPoint(e.getPoint());
////////
////////                // Проверка, содержит ли ячейка значение и не является ли она одной из первых двух столбцов
////////                if (tableModel.getValueAt(row, col) != null && col > 1) {
////////                    startRow = row;
////////                    startCol = col;
////////                    selectedCells.clear();
////////
////////                    // В зависимости от выбранного режима
////////                    if (selectionMode == 0) { // Стандартный режим
////////                        fillStandardSelection(startRow, startCol, startRow, startCol);
////////                    } else { // Режимы 2, 4, 8 байт
////////                        handleByteSelection(startRow, startCol);
////////                    }
////////                    repaint();
////////                }
////////            }
////////
////////            @Override
////////            public void mouseReleased(MouseEvent e) {
////////                startRow = -1;
////////                startCol = -1;
////////                repaint();
////////            }
////////        });
////////
////////        addMouseMotionListener(new MouseAdapter() {
////////            @Override
////////            public void mouseDragged(MouseEvent e) {
////////                int row = rowAtPoint(e.getPoint());
////////                int col = columnAtPoint(e.getPoint());
////////
////////                // Проверка, что ячейка в пределах границ и содержит значение
////////                if (row >= 0 && row < getRowCount() && col >= 0 && col < getColumnCount() &&
////////                        tableModel.getValueAt(row, col) != null && col > 1) {
////////
////////                    // В зависимости от выбранного режима
////////                    selectedCells.clear();
////////
////////                    if (selectionMode == 0) { // Стандартный режим
////////                        fillStandardSelection(startRow, startCol, row, col);
////////                    } else { // Режимы 2, 4, 8 байт
////////                        if (row == startRow) { // Если мы в той же строке
////////                            handleByteSelection(row, col);
////////                        } else {
////////                            // Если строка изменилась, то выделяем все ячейки от начальной до текущей
////////                            fillStandardSelection(startRow, startCol, row, col);
////////                        }
////////                    }
////////                    repaint();
////////                }
////////            }
////////        });
////////    }
////////
////////    private void fillStandardSelection(int startRow, int startCol, int endRow, int endCol) {
////////        // Определение начальных и конечных точек
////////        int minRow = Math.min(startRow, endRow);
////////        int maxRow = Math.max(startRow, endRow);
////////        int minCol = Math.min(startCol, endCol);
////////        int maxCol = Math.max(startCol, endCol);
////////
////////        // Заполнение выделенных ячеек
////////        for (int r = minRow; r <= maxRow; r++) {
////////            int start = (r == minRow) ? minCol : 2; // Начало от minCol на первой строке, иначе с колонки 2
////////            int end = (r == maxRow) ? maxCol : getColumnCount() - 1; // Конец на maxCol на последней строке, иначе до последнего столбца
////////            for (int c = start; c <= end; c++) {
////////                if (tableModel.getValueAt(r, c) != null && c > 1) {
////////                    selectedCells.add(new Point(c, r));
////////                }
////////            }
////////        }
////////    }
////////
////////    private void handleByteSelection(int row, int col) {
////////        selectedCells.add(new Point(col, row));
////////        int count = (selectionMode == 1) ? 1 : (selectionMode == 2) ? 3 : 7;
////////
////////        // Выделение текущей ячейки и последующих
////////        for (int i = 1; i <= count; i++) {
////////            if (col + i < getColumnCount()) {
////////                selectedCells.add(new Point(col + i, row));
////////            }
////////        }
////////    }
////////
////////    public void setSelectionMode(int mode) {
////////        this.selectionMode = mode;
////////        selectedCells.clear();
////////        repaint();
////////    }
////////
////////    @Override
////////    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
////////        Component c = super.prepareRenderer(renderer, row, column);
////////        if (selectedCells.contains(new Point(column, row))) {
////////            c.setBackground(getSelectionBackground());
////////            c.setForeground(getSelectionForeground());
////////        } else {
////////            c.setBackground(getBackground());
////////            c.setForeground(getForeground());
////////        }
////////        return c;
////////    }
////////
////////    @Override
////////    public boolean isCellEditable(int row, int column) {
////////        return column > 1;
////////    }
////////
////////    @Override
////////    public boolean getCellSelectionEnabled() {
////////        return true;
////////    }
////////
////////    @Override
////////    public TableCellRenderer getCellRenderer(int row, int column) {
////////        return getDefaultRenderer(getColumnClass(column));
////////    }
////////
////////    public Set<Point> getSelectedCells() {
////////        return selectedCells;
////////    }
////////}
////////
////////
////////
////////
////////
////////
////////
////////package HexEditor;
////////
////////import javax.swing.*;
////////import javax.swing.table.DefaultTableModel;
////////import javax.swing.table.TableCellRenderer;
////////import java.awt.*;
////////import java.awt.event.MouseAdapter;
////////import java.awt.event.MouseEvent;
////////import java.util.HashSet;
////////import java.util.Set;
////////
////////public class CustomTable extends JTable {
////////    private int startRow = -1;
////////    private int startCol = -1;
////////    private Set<Point> selectedCells = new HashSet<>();
////////    private DefaultTableModel tableModel;
////////    private int selectionMode = 0; // 0 - стандарт, 1 - 2 байта, 2 - 4 байта, 3 - 8 байт
////////
////////    public CustomTable(DefaultTableModel tableModel) {
////////        this.tableModel = tableModel;
////////        setModel(tableModel);
////////        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
////////
////////        addMouseListener(new MouseAdapter() {
////////            @Override
////////            public void mousePressed(MouseEvent e) {
////////                int row = rowAtPoint(e.getPoint());
////////                int col = columnAtPoint(e.getPoint());
////////
////////                if (tableModel.getValueAt(row, col) != null && col > 1) {
////////                    startRow = row;
////////                    startCol = col;
////////                    selectedCells.clear();
////////                    fillStandardSelection(startRow, startCol, startRow, col);
////////                    repaint();
////////                }
////////            }
////////
////////            @Override
////////            public void mouseReleased(MouseEvent e) {
////////                startRow = -1;
////////                startCol = -1;
////////                repaint();
////////            }
////////        });
////////
////////        addMouseMotionListener(new MouseAdapter() {
////////            @Override
////////            public void mouseDragged(MouseEvent e) {
////////                int row = rowAtPoint(e.getPoint());
////////                int col = columnAtPoint(e.getPoint());
////////
////////                if (row >= 0 && row < getRowCount() && col >= 0 && col < getColumnCount() &&
////////                        tableModel.getValueAt(row, col) != null && col > 1) {
////////                    selectedCells.clear();
////////                    fillStandardSelection(startRow, startCol, row, col);
////////                    repaint();
////////                }
////////            }
////////        });
////////    }
////////
////////    private void fillStandardSelection(int startRow, int startCol, int endRow, int endCol) {
////////        // Определение начальных и конечных точек
////////        int minRow = Math.min(startRow, endRow);
////////        int maxRow = Math.max(startRow, endRow);
////////        int minCol = Math.min(startCol, endCol);
////////        int maxCol = Math.max(startCol, endCol);
////////
////////        // Заполнение выделенных ячеек
////////        if (minRow == maxRow) {
////////            // If in the same row, select from startCol to endCol
////////            for (int c = minCol; c <= maxCol; c++) {
////////                if (tableModel.getValueAt(minRow, c) != null && c > 1) {
////////                    selectedCells.add(new Point(c, minRow));
////////                }
////////            }
////////        } else {
////////            // Select from start cell to end of start row
////////            for (int c = startCol; c < getColumnCount(); c++) {
////////                if (tableModel.getValueAt(startRow, c) != null && c > 1) {
////////                    selectedCells.add(new Point(c, startRow));
////////                }
////////            }
////////
////////            // Select entire rows between startRow and endRow
////////            for (int r = minRow + 1; r < maxRow; r++) {
////////                for (int c = 2; c < getColumnCount(); c++) { // Starting from column 2
////////                    if (tableModel.getValueAt(r, c) != null) {
////////                        selectedCells.add(new Point(c, r));
////////                    }
////////                }
////////            }
////////
////////            // Select from the start of the last row to endCol
////////            for (int c = 2; c <= maxCol; c++) {
////////                if (tableModel.getValueAt(maxRow, c) != null) {
////////                    selectedCells.add(new Point(c, maxRow));
////////                }
////////            }
////////        }
////////    }
////////
////////    public void setSelectionMode(int mode) {
////////        this.selectionMode = mode;
////////        selectedCells.clear();
////////        repaint();
////////    }
////////
////////    @Override
////////    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
////////        Component c = super.prepareRenderer(renderer, row, column);
////////        if (selectedCells.contains(new Point(column, row))) {
////////            c.setBackground(getSelectionBackground());
////////            c.setForeground(getSelectionForeground());
////////        } else {
////////            c.setBackground(getBackground());
////////            c.setForeground(getForeground());
////////        }
////////        return c;
////////    }
////////
////////    @Override
////////    public boolean isCellEditable(int row, int column) {
////////        return column > 1;
////////    }
////////
////////    @Override
////////    public boolean getCellSelectionEnabled() {
////////        return true;
////////    }
////////
////////    @Override
////////    public TableCellRenderer getCellRenderer(int row, int column) {
////////        return getDefaultRenderer(getColumnClass(column));
////////    }
////////
////////    public Set<Point> getSelectedCells() {
////////        return selectedCells;
////////    }
////////}
//////
//////
//////package HexEditor;
//////
//////import javax.swing.*;
//////import javax.swing.table.DefaultTableModel;
//////import javax.swing.table.TableCellRenderer;
//////import java.awt.*;
//////import java.awt.event.MouseAdapter;
//////import java.awt.event.MouseEvent;
//////import java.util.HashSet;
//////import java.util.Set;
//////
//////public class CustomTable extends JTable {
//////    private int startRow = -1;
//////    private int startCol = -1;
//////    private Set<Point> selectedCells = new HashSet<>();
//////    private DefaultTableModel tableModel;
//////
//////    public CustomTable(DefaultTableModel tableModel) {
//////        this.tableModel = tableModel;
//////        setModel(tableModel);
//////        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//////
//////        addMouseListener(new MouseAdapter() {
//////            @Override
//////            public void mousePressed(MouseEvent e) {
//////                int row = rowAtPoint(e.getPoint());
//////                int col = columnAtPoint(e.getPoint());
//////
//////                if (tableModel.getValueAt(row, col) != null && col > 1) {
//////                    startRow = row;
//////                    startCol = col;
//////                    selectedCells.clear();
//////                    fillStandardSelection(startRow, startCol, startRow, col);
//////                    repaint();
//////                }
//////            }
//////
//////            @Override
//////            public void mouseReleased(MouseEvent e) {
//////                startRow = -1;
//////                startCol = -1;
//////                repaint();
//////            }
//////        });
//////
//////        addMouseMotionListener(new MouseAdapter() {
//////            @Override
//////            public void mouseDragged(MouseEvent e) {
//////                int row = rowAtPoint(e.getPoint());
//////                int col = columnAtPoint(e.getPoint());
//////
//////                if (row >= 0 && row < getRowCount() && col >= 0 && col < getColumnCount() &&
//////                        tableModel.getValueAt(row, col) != null && col > 1) {
//////                    selectedCells.clear();
//////                    fillStandardSelection(startRow, startCol, row, col);
//////                    repaint();
//////                }
//////            }
//////        });
//////    }
//////
//////    private void fillStandardSelection(int startRow, int startCol, int endRow, int endCol) {
//////        int minRow = Math.min(startRow, endRow);
//////        int maxRow = Math.max(startRow, endRow);
//////        int minCol = Math.min(startCol, endCol);
//////        int maxCol = Math.max(startCol, endCol);
//////
//////        selectedCells.clear(); // Очистка перед новым выделением
//////
//////        // Выделение всех ячеек в диапазоне
//////        for (int r = minRow; r <= maxRow; r++) {
//////            if (r == startRow) {
//////                // Выделение от стартового столбца до конца строки
//////                for (int c = startCol; c < getColumnCount(); c++) {
//////                    if (tableModel.getValueAt(r, c) != null && c > 1) {
//////                        selectedCells.add(new Point(c, r));
//////                    }
//////                }
//////            } else if (r == maxRow) {
//////                // Выделение от начала строки до конечного столбца
//////                for (int c = 2; c <= endCol; c++) {
//////                    if (tableModel.getValueAt(r, c) != null && c > 1) {
//////                        selectedCells.add(new Point(c, r));
//////                    }
//////                }
//////            } else {
//////                // Выделение всей строки между стартовой и конечной
//////                for (int c = 2; c < getColumnCount(); c++) {
//////                    if (tableModel.getValueAt(r, c) != null) {
//////                        selectedCells.add(new Point(c, r));
//////                    }
//////                }
//////            }
//////        }
//////    }
//////
//////    @Override
//////    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//////        Component c = super.prepareRenderer(renderer, row, column);
//////        if (selectedCells.contains(new Point(column, row))) {
//////            c.setBackground(getSelectionBackground());
//////            c.setForeground(getSelectionForeground());
//////        } else {
//////            c.setBackground(getBackground());
//////            c.setForeground(getForeground());
//////        }
//////        return c;
//////    }
//////
//////    @Override
//////    public boolean isCellEditable(int row, int column) {
//////        return column > 1;
//////    }
//////
//////    @Override
//////    public boolean getCellSelectionEnabled() {
//////        return true;
//////    }
//////
//////    @Override
//////    public TableCellRenderer getCellRenderer(int row, int column) {
//////        return getDefaultRenderer(getColumnClass(column));
//////    }
//////
//////    public Set<Point> getSelectedCells() {
//////        return selectedCells;
//////    }
//////}
////
//
//package HexEditor;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellRenderer;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.HashSet;
//import java.util.Set;
//
//public class CustomTable extends JTable {
//    private int startRow = -1;
//    private int startCol = -1;
//    private Set<Point> selectedCells = new HashSet<>();
//    private DefaultTableModel tableModel;
//
//    public CustomTable(DefaultTableModel tableModel) {
//        this.tableModel = tableModel;
//        setModel(tableModel);
//        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                int row = rowAtPoint(e.getPoint());
//                int col = columnAtPoint(e.getPoint());
//
//                if (tableModel.getValueAt(row, col) != null && col > 1) {
//                    startRow = row;
//                    startCol = col;
//                    selectedCells.clear();
//                    selectedCells.add(new Point(startCol, startRow)); // Выделяем только одну ячейку
//                    repaint();
//                }
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                startRow = -1;
//                startCol = -1;
//                repaint();
//            }
//        });
//
//        addMouseMotionListener(new MouseAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                int row = rowAtPoint(e.getPoint());
//                int col = columnAtPoint(e.getPoint());
//
//                if (row >= 0 && row < getRowCount() && col >= 0 && col < getColumnCount() &&
//                        tableModel.getValueAt(row, col) != null && col > 1) {
//                    selectedCells.clear();
//                    if (row == startRow) {
//                        // Выделение в пределах одной строки
//                        fillRowSelection(startRow, startCol, col);
//                    } else {
//                        // Выделение диапазона строк
//                        fillStandardSelection(startRow, startCol, row, col);
//                    }
//                    repaint();
//                }
//            }
//        });
//    }
//
//    private void fillRowSelection(int row, int startCol, int endCol) {
//        int minCol = Math.min(startCol, endCol);
//        int maxCol = Math.max(startCol, endCol);
//
//        // Выделение ячеек только в одной строке
//        for (int c = minCol; c <= maxCol; c++) {
//            if (tableModel.getValueAt(row, c) != null && c > 1) {
//                selectedCells.add(new Point(c, row));
//            }
//        }
//    }
//
//    private void fillStandardSelection(int startRow, int startCol, int endRow, int endCol) {
//        int minRow = Math.min(startRow, endRow);
//        int maxRow = Math.max(startRow, endRow);
//        int minCol = Math.min(startCol, endCol);
//        int maxCol = Math.max(startCol, endCol);
//
//        selectedCells.clear(); // Очистка перед новым выделением
//
//        // Выделение всех ячеек в диапазоне
//        for (int r = minRow; r <= maxRow; r++) {
//            if (r == startRow) {
//                for (int c = startCol; c < getColumnCount(); c++) {
//                    if (tableModel.getValueAt(r, c) != null && c > 1) {
//                        selectedCells.add(new Point(c, r));
//                    }
//                }
//            } else if (r == maxRow) {
//                for (int c = 2; c <= endCol; c++) {
//                    if (tableModel.getValueAt(r, c) != null && c > 1) {
//                        selectedCells.add(new Point(c, r));
//                    }
//                }
//            } else {
//                for (int c = 2; c < getColumnCount(); c++) {
//                    if (tableModel.getValueAt(r, c) != null) {
//                        selectedCells.add(new Point(c, r));
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//        Component c = super.prepareRenderer(renderer, row, column);
//        if (selectedCells.contains(new Point(column, row))) {
//            c.setBackground(getSelectionBackground());
//            c.setForeground(getSelectionForeground());
//        } else {
//            c.setBackground(getBackground());
//            c.setForeground(getForeground());
//        }
//        return c;
//    }
//
//    @Override
//    public boolean isCellEditable(int row, int column) {
//        return column > 1;
//    }
//
//    @Override
//    public boolean getCellSelectionEnabled() {
//        return true;
//    }
//
//    @Override
//    public TableCellRenderer getCellRenderer(int row, int column) {
//        return getDefaultRenderer(getColumnClass(column));
//    }
//
//    public Set<Point> getSelectedCells() {
//        return selectedCells;
//    }
//}
//
//


//package HexEditor;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellRenderer;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.HashSet;
//import java.util.Set;
//
//public class CustomTable extends JTable {
//    private int startRow = -1;
//    private int startCol = -1;
//    private Set<Point> selectedCells = new HashSet<>();
//    private DefaultTableModel tableModel;
//    private int selectionMode = 0; // 0 - стандарт, 1 - 2 байта, 2 - 4 байта, 3 - 8 байт
//
//    public CustomTable(DefaultTableModel tableModel) {
//        this.tableModel = tableModel;
//        setModel(tableModel);
//        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                int row = rowAtPoint(e.getPoint());
//                int col = columnAtPoint(e.getPoint());
//
//                if (tableModel.getValueAt(row, col) != null && col > 1) {
//                    startRow = row;
//                    startCol = col;
//                    selectedCells.clear();
//                    selectCells(row, col); // Выделение ячеек в зависимости от режима
//                    repaint();
//                }
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                startRow = -1;
//                startCol = -1;
//                repaint();
//            }
//        });
//
//        addMouseMotionListener(new MouseAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                // Можно добавить логику перетаскивания, если нужно
//            }
//        });
//    }
//
//    private void selectCells(int row, int col) {
//        int count = 0;
//        switch (selectionMode) {
//            case 0: // стандартный
//                selectedCells.add(new Point(col, row));
//                break;
//            case 1: // 2 байта
//                count = 2;
//                break;
//            case 2: // 4 байта
//                count = 4;
//                break;
//            case 3: // 8 байт
//                count = 8;
//                break;
//        }
//
//        int currentCol = col;
//        int currentRow = row;
//
//        while (count > 0) {
//            if (currentCol < getColumnCount()) {
//                selectedCells.add(new Point(currentCol, currentRow));
//                currentCol++;
//            } else {
//                // Переход на следующую строку
//                currentCol = 2; // Начинаем с колонки 2
//                currentRow++;
//                if (currentRow < getRowCount()) {
//                    selectedCells.add(new Point(currentCol, currentRow));
//                }
//            }
//            count--;
//        }
//    }
//
//    public void setSelectionMode(int mode) {
//        this.selectionMode = mode;
//    }
//
//    @Override
//    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//        Component c = super.prepareRenderer(renderer, row, column);
//        if (selectedCells.contains(new Point(column, row))) {
//            c.setBackground(getSelectionBackground());
//            c.setForeground(getSelectionForeground());
//        } else {
//            c.setBackground(getBackground());
//            c.setForeground(getForeground());
//        }
//        return c;
//    }
//
//    @Override
//    public boolean isCellEditable(int row, int column) {
//        return column > 1;
//    }
//
//    @Override
//    public boolean getCellSelectionEnabled() {
//        return true;
//    }
//
//    @Override
//    public TableCellRenderer getCellRenderer(int row, int column) {
//        return getDefaultRenderer(getColumnClass(column));
//    }
//
//    public Set<Point> getSelectedCells() {
//        return selectedCells;
//    }
//}


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

                if (tableModel.getValueAt(row, col) != null && col > 1) {
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

                if (selectionMode == 0) {
                    if (row >= 0 && row < getRowCount() && col >= 0 && col < getColumnCount() &&
                            tableModel.getValueAt(row, col) != null && col > 1) {
                        selectedCells.clear();
                        if (row == startRow) {
                            fillRowSelection(startRow, startCol, col);
                        } else {
                            fillStandardSelection(startRow, startCol, row, col);
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

        // Всегда добавляем первую выбранную ячейку
        selectedCells.add(new Point(currentCol, currentRow));
        count--;

        while (count > 0) {
            if (currentCol < getColumnCount() - 1) {
                currentCol++;
                selectedCells.add(new Point(currentCol, currentRow));
            } else {
                // Переход на следующую строку
                currentCol = 2; // Начинаем с колонки 2
                currentRow++;
                if (currentRow < getRowCount() && currentCol < getColumnCount()) {
                    selectedCells.add(new Point(currentCol, currentRow));
                } else {
                    // Если выходим за пределы таблицы, выходим из цикла
                    break;
                }
            }
            count--;
        }
    }



    private void fillRowSelection(int row, int startCol, int endCol) {
        int minCol = Math.min(startCol, endCol);
        int maxCol = Math.max(startCol, endCol);

        // Выделение ячеек только в одной строке
        for (int c = minCol; c <= maxCol; c++) {
            if (tableModel.getValueAt(row, c) != null && c > 1) {
                selectedCells.add(new Point(c, row));
            }
        }
    }

    private void fillStandardSelection(int startRow, int startCol, int endRow, int endCol) {
        int minRow = Math.min(startRow, endRow);
        int maxRow = Math.max(startRow, endRow);
        int minCol = Math.min(startCol, endCol);
        int maxCol = Math.max(startCol, endCol);

        selectedCells.clear(); // Очистка перед новым выделением

        // Выделение всех ячеек в диапазоне
        for (int r = minRow; r <= maxRow; r++) {
            if (r == startRow) {
                for (int c = startCol; c < getColumnCount(); c++) {
                    if (tableModel.getValueAt(r, c) != null && c > 1) {
                        selectedCells.add(new Point(c, r));
                    }
                }
            } else if (r == maxRow) {
                for (int c = 2; c <= endCol; c++) {
                    if (tableModel.getValueAt(r, c) != null && c > 1) {
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
        return column > 1;
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
