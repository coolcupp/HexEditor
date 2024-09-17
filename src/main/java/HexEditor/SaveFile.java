package HexEditor;

import javax.swing.table.DefaultTableModel;
import java.io.*;

public class SaveFile {
    DefaultTableModel tableModel;

    public SaveFile(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void saveToFile(File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            int rowCount = tableModel.getRowCount();
            int columnCount = tableModel.getColumnCount();

            // запись данных таблицы в файл
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    byte[] data = String.valueOf(tableModel.getValueAt(i, j)).getBytes();
                    fos.write(data);
                    // если не последняя ячейка - добавляем разделитель
                    if (j < columnCount - 1) {
                        fos.write('\t'); // используем табуляцию
                    }
                }
                fos.write('\n');
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


