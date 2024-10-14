////package HexEditor;
////
////import javax.swing.*;
////import javax.swing.table.DefaultTableModel;
////import java.io.File;
////import java.io.FileInputStream;
////import java.io.FileNotFoundException;
////import java.io.IOException;
////
////public class OpenFile {
////    private JTable table;
////    private DefaultTableModel tableModel;
////
////
////    public OpenFile(JTable table) {
////        this.table = table;
////        this.tableModel = (DefaultTableModel) table.getModel();
////    }
////
////    public void loadFile(File file) {
////        try (FileInputStream fis = new FileInputStream(file)) {
////            byte[] buffer = new byte[16];
////            int bytesRead;
////            int address = 0;
////            int rowNum = 1;
////            // clear table
////            tableModel.setRowCount(0);
////
////            // Читаем файл порциями по 16 байт
////            while ((bytesRead = fis.read(buffer)) != -1){
////                Object[] row = new Object[18];
////                row[0] = rowNum++;
////                row[1] = String.format("0x%04X", address);
////                for (int i = 0; i < 16; i++) {
////                    if (i < bytesRead) {
////                        row[i + 2] = String.format("%02X", buffer[i]);
////                    } else {
////                        row[i + 2] = ""; // Пустое значение если байт не прочитан
////                    }
////                }
////                tableModel.addRow(row);
////                address += 16;
////            }
////
////            } catch(IOException e){
////                JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage());
////            }
////        }
////    }
//
//
//package HexEditor;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//public class OpenFile {
//    private JTable table;
//    private DefaultTableModel tableModel;
//    private JTextField columnCountField;
//
//    public OpenFile(JTable table, JTextField columnCountField) {
//        this.table = table;
//        this.tableModel = (DefaultTableModel) table.getModel();
//        this.columnCountField = columnCountField;
//    }
//
//    public void loadFile(File file) {
//        int columnCount;
//
//        try {
//            columnCount = Integer.parseInt(columnCountField.getText());
//            if (columnCount < 1) {
//                throw new NumberFormatException("Количество столбцов должно быть положительным.");
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "Некорректное количество столбцов: " + e.getMessage());
//            return;
//        }
//
//        try (FileInputStream fis = new FileInputStream(file)) {
//            byte[] buffer = new byte[columnCount];
//            int bytesRead;
//            int address = 0;
//            int rowNum = 1;
//
//            // Очистка таблицы
//            tableModel.setRowCount(0);
//
//            // Читаем файл порциями по заданному количеству байт
//            while ((bytesRead = fis.read(buffer)) != -1) {
//                Object[] row = new Object[columnCount + 2];
//                row[0] = rowNum++;
//                row[1] = String.format("0x%04X", address);
//                for (int i = 0; i < columnCount; i++) {
//                    if (i < bytesRead) {
//                        row[i + 2] = String.format("%02X", buffer[i]);
//                    } else {
//                        row[i + 2] = ""; // Пустое значение если байт не прочитан
//                    }
//                }
//                tableModel.addRow(row);
//                address += columnCount;
//            }
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage());
//        }
//    }
//}

package HexEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class OpenFile {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField columnCountField;

    public OpenFile(JTable table, JTextField columnCountField) {
        this.table = table;
        this.tableModel = (DefaultTableModel) table.getModel();
        this.columnCountField = columnCountField;
    }

    public void loadFile(File file) {
        int columnCount;

        try {
            columnCount = Integer.parseInt(columnCountField.getText());
            if (columnCount < 1) {
                throw new NumberFormatException("Количество столбцов должно быть положительным.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Некорректное количество столбцов: " + e.getMessage());
            return;
        }

        // Очистка таблицы и добавление заголовков
        tableModel.setRowCount(0); // Очистка строк
        tableModel.setColumnCount(0); // Очистка столбцов
        tableModel.addColumn("№");
        tableModel.addColumn("Adress");
        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(String.valueOf(i));
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[columnCount];
            int bytesRead;
            int address = 0;
            int rowNum = 1;

            // Читаем файл порциями по заданному количеству байт
            while ((bytesRead = fis.read(buffer)) != -1) {
                Object[] row = new Object[columnCount + 2];
                row[0] = rowNum++;
                row[1] = String.format("0x%04X", address);
                for (int i = 0; i < columnCount; i++) {
                    if (i < bytesRead) {
                        row[i + 2] = String.format("%02X", buffer[i]);
                    } else {
                        row[i + 2] = ""; // Пустое значение если байт не прочитан
                    }
                }
                tableModel.addRow(row);
                address += columnCount;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage());
        }
    }
}
