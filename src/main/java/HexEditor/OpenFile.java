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
        tableModel.addColumn("Address");
        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(String.valueOf(i));
        }

        // Создание и запуск SwingWorker для загрузки файла
        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() {
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
                        publish(row); // Передаем строку для обновления модели
                        address += columnCount;
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Object[]> chunks) {
                for (Object[] row : chunks) {
                    tableModel.addRow(row); // Добавляем строки в модель
                }
            }

            @Override
            protected void done() {
                // Можно добавить действия по завершении загрузки, если нужно
                System.out.println("Loading completed!");
            }
        };

        worker.execute(); // Запускаем загрузку в фоновом режиме
    }
}
