//package HexEditor;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//
//public class TableResizer {
//    private JTable table;
//    private DefaultTableModel tableModel;
//    private JTextField columnCountField;
//
//    public TableResizer(JTable table, JTextField columnCountField) {
//        this.table = table;
//        this.tableModel = (DefaultTableModel) table.getModel();
//        this.columnCountField = columnCountField;
//    }
//
//    public void resizeTable() {
//        int newColumnCount;
//
//        // Считываем количество столбцов из текстового поля
//        try {
//            newColumnCount = Integer.parseInt(columnCountField.getText());
//            if (newColumnCount < 1 || newColumnCount > 16) {
//                throw new NumberFormatException("Количество столбцов должно быть от 1 до 16.");
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "Некорректное количество столбцов: " + e.getMessage());
//            return;
//        }
//
//        int rowCount = tableModel.getRowCount();
//        int oldColumnCount = tableModel.getColumnCount() - 2; // Учитываем дополнительные столбцы (№ и Adress)
//
//        // Рассчитываем общее количество ячеек
//        int totalCells = rowCount * oldColumnCount;
//
//        // Рассчитываем необходимое количество строк в новой таблице
//        int newRowCount = (int) Math.ceil((double) totalCells / newColumnCount);
//
//        // Создаем новый массив данных
//        Object[][] newData = new Object[newRowCount][newColumnCount + 2]; // +2 для дополнительных столбцов (№ и Adress)
//
//        // Переносим данные
//        int dataIndex = 0;
//        for (int row = 0; row < newRowCount; row++) {
//            newData[row][0] = row + 1; // Номер
//            newData[row][1] = String.format("0x%04X", row * newColumnCount); // Адрес
//
//            for (int col = 0; col < newColumnCount; col++) {
//                if (dataIndex < totalCells) {
//                    int oldRow = dataIndex / oldColumnCount;
//                    int oldCol = dataIndex % oldColumnCount + 2; // +2 для учета дополнительных столбцов
//                    newData[row][col + 2] = tableModel.getValueAt(oldRow, oldCol);
//                    dataIndex++;
//                } else {
//                    newData[row][col + 2] = ""; // Пустое значение, если данных нет
//                }
//            }
//        }
//
//        // Обновляем модель таблицы
//        tableModel.setRowCount(0); // Очищаем старую модель
//        tableModel.setColumnCount(0); // Очищаем старые столбцы
//
//        // Добавляем новые столбцы
//        tableModel.addColumn("№");
//        tableModel.addColumn("Adress");
//        for (int i = 1; i <= newColumnCount; i++) {
//            tableModel.addColumn(String.valueOf(i));
//        }
//
//        // Заполняем таблицу новыми данными
//        for (Object[] row : newData) {
//            tableModel.addRow(row);
//        }
//    }
//}


package HexEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableResizer {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField columnCountField;

    public TableResizer(JTable table, JTextField columnCountField) {
        this.table = table;
        this.tableModel = (DefaultTableModel) table.getModel();
        this.columnCountField = columnCountField;
    }

    public void resizeTable() {
        int newColumnCount;

        // Считываем количество столбцов из текстового поля
        try {
            newColumnCount = Integer.parseInt(columnCountField.getText());
            if (newColumnCount < 1 || newColumnCount > 16) {
                throw new NumberFormatException("Количество столбцов должно быть от 1 до 16.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Некорректное количество столбцов: " + e.getMessage());
            return;
        }

        int rowCount = tableModel.getRowCount();
        int oldColumnCount = tableModel.getColumnCount() - 2; // Учитываем дополнительные столбцы (№ и Adress)

        // Рассчитываем общее количество ячеек
        int totalCells = rowCount * oldColumnCount;

        // Рассчитываем необходимое количество строк в новой таблице
        int newRowCount = (int) Math.ceil((double) totalCells / newColumnCount);

        // Создаем новый массив данных
        Object[][] newData = new Object[newRowCount][newColumnCount + 2]; // +2 для дополнительных столбцов (№ и Adress)

        // Переносим данные
        int dataIndex = 0;
        for (int row = 0; row < newRowCount; row++) {
            newData[row][0] = row + 1; // Номер
            newData[row][1] = String.format("0x%04X", row * newColumnCount); // Адрес

            for (int col = 0; col < newColumnCount; col++) {
                if (dataIndex < totalCells) {
                    int oldRow = dataIndex / oldColumnCount;
                    int oldCol = dataIndex % oldColumnCount + 2; // +2 для учета дополнительных столбцов
                    newData[row][col + 2] = tableModel.getValueAt(oldRow, oldCol);
                    dataIndex++;
                } else {
                    newData[row][col + 2] = ""; // Пустое значение, если данных нет
                }
            }
        }

        // Удаляем пустые строки
        int actualRowCount = 0;
        for (int row = 0; row < newRowCount; row++) {
            boolean isEmpty = true;
            for (int col = 2; col < newColumnCount + 2; col++) {
                if (newData[row][col] != null && !newData[row][col].toString().isEmpty()) {
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                actualRowCount++;
            }
        }

        // Создаем новый массив с актуальным количеством строк
        Object[][] finalData = new Object[actualRowCount][newColumnCount + 2];
        int finalIndex = 0;

        for (int row = 0; row < newRowCount; row++) {
            boolean isEmpty = true;
            for (int col = 2; col < newColumnCount + 2; col++) {
                if (newData[row][col] != null && !newData[row][col].toString().isEmpty()) {
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                finalData[finalIndex++] = newData[row];
            }
        }

        // Обновляем модель таблицы
        tableModel.setRowCount(0); // Очищаем старую модель
        tableModel.setColumnCount(0); // Очищаем старые столбцы

        // Добавляем новые столбцы
        tableModel.addColumn("№");
        tableModel.addColumn("Adress");
        for (int i = 1; i <= newColumnCount; i++) {
            tableModel.addColumn(String.valueOf(i));
        }

        // Заполняем таблицу новыми данными
        for (Object[] row : finalData) {
            tableModel.addRow(row);
        }
    }
}
