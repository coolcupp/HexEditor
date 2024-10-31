//////////package HexEditor;
//////////
//////////import javax.swing.*;
//////////import javax.swing.table.DefaultTableModel;
//////////import java.io.File;
//////////import java.io.FileInputStream;
//////////import java.io.IOException;
//////////
//////////public class OpenFile {
//////////    private JTable table;
//////////    private DefaultTableModel tableModel;
//////////    private JTextField columnCountField;
//////////
//////////    public OpenFile(JTable table, JTextField columnCountField) {
//////////        this.table = table;
//////////        this.tableModel = (DefaultTableModel) table.getModel();
//////////        this.columnCountField = columnCountField;
//////////    }
//////////
//////////    public void loadFile(File file) {
//////////        int columnCount;
//////////
//////////        try {
//////////            columnCount = Integer.parseInt(columnCountField.getText());
//////////            if (columnCount < 1) {
//////////                throw new NumberFormatException("Количество столбцов должно быть положительным.");
//////////            }
//////////        } catch (NumberFormatException e) {
//////////            JOptionPane.showMessageDialog(null, "Некорректное количество столбцов: " + e.getMessage());
//////////            return;
//////////        }
//////////
//////////        // Очистка таблицы и добавление заголовков
//////////        tableModel.setRowCount(0); // Очистка строк
//////////        tableModel.setColumnCount(0); // Очистка столбцов
//////////        tableModel.addColumn("№");
//////////        tableModel.addColumn("Address");
//////////        for (int i = 1; i <= columnCount; i++) {
//////////            tableModel.addColumn(String.valueOf(i));
//////////        }
//////////
//////////        // Создание и запуск SwingWorker для загрузки файла
//////////        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
//////////            @Override
//////////            protected Void doInBackground() {
//////////                try (FileInputStream fis = new FileInputStream(file)) {
//////////                    byte[] buffer = new byte[columnCount];
//////////                    int bytesRead;
//////////                    int address = 0;
//////////                    int rowNum = 1;
//////////
//////////                    // Читаем файл порциями по заданному количеству байт
//////////                    while ((bytesRead = fis.read(buffer)) != -1) {
//////////                        Object[] row = new Object[columnCount + 2];
//////////                        row[0] = rowNum++;
//////////                        row[1] = String.format("0x%04X", address);
//////////                        for (int i = 0; i < columnCount; i++) {
//////////                            if (i < bytesRead) {
//////////                                row[i + 2] = String.format("%02X", buffer[i]);
//////////                            } else {
//////////                                row[i + 2] = ""; // Пустое значение если байт не прочитан
//////////                            }
//////////                        }
//////////                        publish(row); // Передаем строку для обновления модели
//////////                        address += columnCount;
//////////                    }
//////////                } catch (IOException e) {
//////////                    JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage());
//////////                }
//////////                return null;
//////////            }
//////////
//////////            @Override
//////////            protected void process(java.util.List<Object[]> chunks) {
//////////                for (Object[] row : chunks) {
//////////                    tableModel.addRow(row); // Добавляем строки в модель
//////////                }
//////////            }
//////////
//////////            @Override
//////////            protected void done() {
//////////                // Можно добавить действия по завершении загрузки, если нужно
//////////                System.out.println("Loading completed!");
//////////            }
//////////        };
//////////
//////////        worker.execute(); // Запускаем загрузку в фоновом режиме
//////////    }
//////////}
////////
////////package HexEditor;
////////
////////import javax.swing.*;
////////import javax.swing.table.DefaultTableModel;
////////import java.io.File;
////////import java.io.FileInputStream;
////////import java.io.IOException;
////////
////////public class OpenFile {
////////    private JTable table;
////////    private DefaultTableModel tableModel;
////////    private JTextField columnCountField;
////////    private FileViewer fileViewer;
////////
////////    public OpenFile(JTable table, JTextField columnCountField, FileViewer fileViewer) {
////////        this.table = table;
////////        this.tableModel = (DefaultTableModel) table.getModel();
////////        this.columnCountField = columnCountField;
////////        this.fileViewer = fileViewer;
////////    }
////////
////////    public void loadFile(File file) {
////////        int columnCount;
////////
////////        // Проверка корректности количества столбцов
////////        try {
////////            columnCount = Integer.parseInt(columnCountField.getText());
////////            if (columnCount < 1) {
////////                throw new NumberFormatException("Количество столбцов должно быть положительным.");
////////            }
////////        } catch (NumberFormatException e) {
////////            JOptionPane.showMessageDialog(null, "Некорректное количество столбцов: " + e.getMessage());
////////            return;
////////        }
////////
////////        // Очистка таблицы и добавление заголовков
////////        tableModel.setRowCount(0); // Очистка строк
////////        tableModel.setColumnCount(0); // Очистка столбцов
////////        tableModel.addColumn("№");
////////        tableModel.addColumn("Address");
////////        for (int i = 1; i <= columnCount; i++) {
////////            tableModel.addColumn(String.valueOf(i));
////////        }
////////
////////        // Создание и запуск SwingWorker для загрузки файла
////////        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
////////            @Override
////////            protected Void doInBackground() {
////////                try (FileInputStream fis = new FileInputStream(file)) {
////////                    byte[] buffer = new byte[columnCount];
////////                    int bytesRead;
////////                    int address = 0;
////////                    int rowNum = 1;
////////
////////                    // Читаем файл порциями по заданному количеству байт
////////                    while ((bytesRead = fis.read(buffer)) != -1) {
////////                        Object[] row = new Object[columnCount + 2];
////////                        row[0] = rowNum++;
////////                        row[1] = String.format("0x%04X", address);
////////                        for (int i = 0; i < columnCount; i++) {
////////                            if (i < bytesRead) {
////////                                row[i + 2] = String.format("%02X", buffer[i]);
////////                            } else {
////////                                row[i + 2] = ""; // Пустое значение если байт не прочитан
////////                            }
////////                        }
////////                        publish(row); // Передаем строку для обновления модели
////////                        address += columnCount;
////////                    }
////////                } catch (IOException e) {
////////                    JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage());
////////                }
////////                return null;
////////            }
////////
////////            @Override
////////            protected void process(java.util.List<Object[]> chunks) {
////////                for (Object[] row : chunks) {
////////                    tableModel.addRow(row); // Добавляем строки в модель
////////                }
////////            }
////////
////////            @Override
////////            protected void done() {
////////                // Обновляем текстовое поле после загрузки файла
////////                fileViewer.updateTextArea(tableModel);
////////                System.out.println("Loading completed!");
////////            }
////////        };
////////
////////        worker.execute(); // Запускаем загрузку в фоновом режиме
////////    }
////////}
//////
//////package HexEditor;
//////
//////import javax.swing.*;
//////import javax.swing.table.DefaultTableModel;
//////import java.io.File;
//////import java.io.FileInputStream;
//////import java.io.IOException;
//////
//////public class OpenFile {
//////    private JTable table;
//////    private DefaultTableModel tableModel;
//////    private JComboBox<String> columnCountComboBox; // Change to JComboBox
//////    private FileViewer fileViewer;
//////
//////    public OpenFile(JTable table, JComboBox<String> columnCountComboBox, FileViewer fileViewer) {
//////        this.table = table;
//////        this.tableModel = (DefaultTableModel) table.getModel();
//////        this.columnCountComboBox = columnCountComboBox; // Store JComboBox reference
//////        this.fileViewer = fileViewer;
//////    }
//////
//////    public void loadFile(File file) {
//////        int columnCount;
//////
//////        // Get the column count from JComboBox
//////        try {
//////            columnCount = Integer.parseInt((String) columnCountComboBox.getSelectedItem());
//////            if (columnCount < 1) {
//////                throw new NumberFormatException("Количество столбцов должно быть положительным.");
//////            }
//////        } catch (NumberFormatException e) {
//////            JOptionPane.showMessageDialog(null, "Некорректное количество столбцов: " + e.getMessage());
//////            return;
//////        }
//////
//////        // Очистка таблицы и добавление заголовков
//////        tableModel.setRowCount(0); // Очистка строк
//////        tableModel.setColumnCount(0); // Очистка столбцов
//////        tableModel.addColumn("№");
//////        tableModel.addColumn("Address");
//////        for (int i = 1; i <= columnCount; i++) {
//////            tableModel.addColumn(String.valueOf(i));
//////        }
//////
//////        // Создание и запуск SwingWorker для загрузки файла
//////        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
//////            @Override
//////            protected Void doInBackground() {
//////                try (FileInputStream fis = new FileInputStream(file)) {
//////                    byte[] buffer = new byte[columnCount];
//////                    int bytesRead;
//////                    int address = 0;
//////                    int rowNum = 1;
//////
//////                    // Читаем файл порциями по заданному количеству байт
//////                    while ((bytesRead = fis.read(buffer)) != -1) {
//////                        Object[] row = new Object[columnCount + 2];
//////                        row[0] = rowNum++;
//////                        row[1] = String.format("0x%04X", address);
//////                        for (int i = 0; i < columnCount; i++) {
//////                            if (i < bytesRead) {
//////                                row[i + 2] = String.format("%02X", buffer[i]);
//////                            } else {
//////                                row[i + 2] = ""; // Пустое значение если байт не прочитан
//////                            }
//////                        }
//////                        publish(row); // Передаем строку для обновления модели
//////                        address += columnCount;
//////                    }
//////                } catch (IOException e) {
//////                    JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage());
//////                }
//////                return null;
//////            }
//////
//////            @Override
//////            protected void process(java.util.List<Object[]> chunks) {
//////                for (Object[] row : chunks) {
//////                    tableModel.addRow(row); // Добавляем строки в модель
//////                }
//////            }
//////
//////            @Override
//////            protected void done() {
//////                // Обновляем текстовое поле после загрузки файла
//////                fileViewer.updateTextArea(tableModel);
//////                System.out.println("Loading completed!");
//////            }
//////        };
//////
//////        worker.execute(); // Запускаем загрузку в фоновом режиме
//////    }
//////}
////
////package HexEditor;
////
////import javax.swing.*;
////import javax.swing.table.DefaultTableModel;
////import java.io.File;
////import java.io.FileInputStream;
////import java.io.IOException;
////
////public class OpenFile {
////    private JTable table;
////    private DefaultTableModel tableModel;
////    private JComboBox<String> columnCountComboBox;
////    private FileViewer fileViewer;
////    private int currentPage = 1;
////    private int totalPages = 1;
////    private int pageSize;
////    private File file;
////    private int columnCount;
////
////    public OpenFile(JTable table, JComboBox<String> columnCountComboBox, FileViewer fileViewer) {
////        this.table = table;
////        this.tableModel = (DefaultTableModel) table.getModel();
////        this.columnCountComboBox = columnCountComboBox;
////        this.fileViewer = fileViewer;
////    }
////
////    public void loadFile(File file) {
////        this.file = file;
////
////        try {
////            columnCount = Integer.parseInt((String) columnCountComboBox.getSelectedItem());
////            if (columnCount < 1) {
////                throw new NumberFormatException("Количество столбцов должно быть положительным.");
////            }
////        } catch (NumberFormatException e) {
////            JOptionPane.showMessageDialog(null, "Некорректное количество столбцов: " + e.getMessage());
////            return;
////        }
////
////        // Рассчитываем размер страницы
////        pageSize = Integer.parseInt((String) columnCountComboBox.getSelectedItem());
////        long fileSize = file.length();
////        totalPages = (int) Math.ceil((double) fileSize / (pageSize * columnCount));
////
////        loadPage(currentPage);
////    }
////
////    public void loadPage(int pageNumber) {
////        if (pageNumber < 1 || pageNumber > totalPages) return;
////
////        currentPage = pageNumber;
////
////        // Очистка таблицы и добавление заголовков
////        tableModel.setRowCount(0);
////        tableModel.setColumnCount(0);
////        tableModel.addColumn("№");
////        tableModel.addColumn("Address");
////        for (int i = 1; i <= columnCount; i++) {
////            tableModel.addColumn(String.valueOf(i));
////        }
////
////        // Создание и запуск SwingWorker для загрузки файла
////        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
////            @Override
////            protected Void doInBackground() {
////                try (FileInputStream fis = new FileInputStream(file)) {
////                    byte[] buffer = new byte[columnCount];
////                    int address = (pageNumber - 1) * pageSize * columnCount;
////                    fis.skip(address);
////
////                    for (int rowNum = 1; rowNum <= pageSize; rowNum++) {
////                        int bytesRead = fis.read(buffer);
////                        Object[] row = new Object[columnCount + 2];
////                        row[0] = rowNum + ((pageNumber - 1) * pageSize); // Номер строки
////                        row[1] = String.format("0x%04X", address);
////
////                        for (int i = 0; i < columnCount; i++) {
////                            if (i < bytesRead) {
////                                row[i + 2] = String.format("%02X", buffer[i]);
////                            } else {
////                                row[i + 2] = ""; // Пустое значение если байт не прочитан
////                            }
////                        }
////                        publish(row); // Передаем строку для обновления модели
////                        address += columnCount;
////                        if (bytesRead < columnCount) break; // Выход, если прочитаны все байты
////                    }
////                } catch (IOException e) {
////                    JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage());
////                }
////                return null;
////            }
////
////            @Override
////            protected void process(java.util.List<Object[]> chunks) {
////                for (Object[] row : chunks) {
////                    tableModel.addRow(row); // Добавляем строки в модель
////                }
////            }
////
////            @Override
////            protected void done() {
////                // Обновляем текстовое поле после загрузки файла
////                fileViewer.updateTextArea(tableModel);
////                System.out.println("Loading completed!");
////            }
////        };
////
////        worker.execute(); // Запускаем загрузку в фоновом режиме
////    }
////
////    public int getCurrentPage() {
////        return currentPage;
////    }
////
////    public int getTotalPages() {
////        return totalPages;
////    }
////}
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
//    private JComboBox<String> columnCountComboBox;
//    private FileViewer fileViewer;
//    private int currentPage = 1;
//    private int totalPages = 1;
//    private int pageSize;
//    private File file;
//    private int columnCount;
//
//    public OpenFile(JTable table, JComboBox<String> columnCountComboBox, FileViewer fileViewer) {
//        this.table = table;
//        this.tableModel = (DefaultTableModel) table.getModel();
//        this.columnCountComboBox = columnCountComboBox;
//        this.fileViewer = fileViewer;
//    }
//
//    public void loadFile(File file) {
//        this.file = file;
//
//        try {
//            columnCount = Integer.parseInt((String) columnCountComboBox.getSelectedItem());
//            if (columnCount < 1) {
//                throw new NumberFormatException("Количество столбцов должно быть положительным.");
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "Некорректное количество столбцов: " + e.getMessage());
//            return;
//        }
//
//        // Get the number of rows from the rowsComboBox
//        int rowCount;
//        try {
//            rowCount = Integer.parseInt((String) columnCountComboBox.getSelectedItem());
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "Некорректное количество строк: " + e.getMessage());
//            return;
//        }
//
//        // Calculate page size
//        pageSize = rowCount; // now pageSize is based on rows
//        long fileSize = file.length();
//        totalPages = (int) Math.ceil((double) fileSize / (pageSize * columnCount));
//
//        loadPage(currentPage);
//    }
//
//    public void loadPage(int pageNumber) {
//        if (pageNumber < 1 || pageNumber > totalPages) return;
//
//        currentPage = pageNumber;
//
//        // Clear the table and add headers
//        tableModel.setRowCount(0);
//        tableModel.setColumnCount(0);
//        tableModel.addColumn("№");
//        tableModel.addColumn("Address");
//        for (int i = 1; i <= columnCount; i++) {
//            tableModel.addColumn(String.valueOf(i));
//        }
//
//        // Create and run SwingWorker to load file
//        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
//            @Override
//            protected Void doInBackground() {
//                try (FileInputStream fis = new FileInputStream(file)) {
//                    byte[] buffer = new byte[columnCount];
//                    int address = (pageNumber - 1) * pageSize * columnCount;
//                    fis.skip(address);
//
//                    for (int rowNum = 1; rowNum <= pageSize; rowNum++) {
//                        int bytesRead = fis.read(buffer);
//                        Object[] row = new Object[columnCount + 2];
//                        row[0] = rowNum + ((pageNumber - 1) * pageSize); // Row number
//                        row[1] = String.format("0x%04X", address);
//
//                        for (int i = 0; i < columnCount; i++) {
//                            if (i < bytesRead) {
//                                row[i + 2] = String.format("%02X", buffer[i]);
//                            } else {
//                                row[i + 2] = ""; // Empty value if byte not read
//                            }
//                        }
//                        publish(row); // Send row for model update
//                        address += columnCount;
//                        if (bytesRead < columnCount) break; // Exit if all bytes read
//                    }
//                } catch (IOException e) {
//                    JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage());
//                }
//                return null;
//            }
//
//            @Override
//            protected void process(java.util.List<Object[]> chunks) {
//                for (Object[] row : chunks) {
//                    tableModel.addRow(row); // Add rows to model
//                }
//            }
//
//            @Override
//            protected void done() {
//                // Update text area after file loading
//                fileViewer.updateTextArea(tableModel);
//                System.out.println("Loading completed!");
//            }
//        };
//
//        worker.execute(); // Start loading in the background
//    }
//
//    public int getCurrentPage() {
//        return currentPage;
//    }
//
//    public int getTotalPages() {
//        return totalPages;
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
    private JComboBox<String> rowsComboBox; // Изменено
    private JComboBox<String> columnCountComboBox;
    private FileViewer fileViewer;
    private int currentPage = 1;
    private int totalPages = 1;
    private int pageSize;
    private File file;
    private int columnCount;

    public OpenFile(JTable table, JComboBox<String> rowsComboBox, JComboBox<String> columnCountComboBox, FileViewer fileViewer) {
        this.table = table;
        this.tableModel = (DefaultTableModel) table.getModel();
        this.rowsComboBox = rowsComboBox; // Сохранить ссылку на rowsComboBox
        this.columnCountComboBox = columnCountComboBox;
        this.fileViewer = fileViewer;
    }

    public void loadFile(File file) {
        this.file = file;

        try {
            columnCount = Integer.parseInt((String) columnCountComboBox.getSelectedItem());
            if (columnCount < 1) {
                throw new NumberFormatException("Количество столбцов должно быть положительным.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Некорректное количество столбцов: " + e.getMessage());
            return;
        }

        // Получить количество строк из rowsComboBox
        int rowCount;
        try {
            rowCount = Integer.parseInt((String) rowsComboBox.getSelectedItem());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Некорректное количество строк: " + e.getMessage());
            return;
        }

        // Вычислить размер страницы
        pageSize = rowCount; // Теперь pageSize основан на строках
        long fileSize = file.length();
        totalPages = (int) Math.ceil((double) fileSize / (pageSize * columnCount));

        loadPage(currentPage);
    }

    public void loadPage(int pageNumber) {
        if (pageNumber < 1 || pageNumber > totalPages) return;

        currentPage = pageNumber;

        // Очистить таблицу и добавить заголовки
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        tableModel.addColumn("№");
        tableModel.addColumn("Address");
        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(String.valueOf(i));
        }

        // Создать и запустить SwingWorker для загрузки файла
        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() {
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[columnCount];
                    int address = (pageNumber - 1) * pageSize * columnCount;
                    fis.skip(address);

                    for (int rowNum = 0; rowNum < pageSize; rowNum++) {
                        int bytesRead = fis.read(buffer);
                        Object[] row = new Object[columnCount + 2];
                        row[0] = rowNum + 1 + ((pageNumber - 1) * pageSize); // Номер строки
                        row[1] = String.format("0x%04X", address);

                        for (int i = 0; i < columnCount; i++) {
                            if (i < bytesRead) {
                                row[i + 2] = String.format("%02X", buffer[i]);
                            } else {
                                row[i + 2] = ""; // Пустое значение, если байт не считан
                            }
                        }
                        publish(row); // Отправить строку для обновления модели
                        address += columnCount;
                        if (bytesRead < columnCount) break; // Выйти, если все байты считаны
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Object[]> chunks) {
                for (Object[] row : chunks) {
                    tableModel.addRow(row); // Добавить строки в модель
                }
            }

            @Override
            protected void done() {
                // Обновить текстовую область после загрузки файла
                fileViewer.updateTextArea(tableModel);
                System.out.println("Loading completed!");
            }
        };

        worker.execute(); // Начать загрузку в фоновом режиме
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
