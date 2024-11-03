package HexEditor;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ByteCutter {
    private List<Byte> removedBytes = new ArrayList<>();

    public void cutBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
        if (file == null) {
            throw new IOException("Файл не загружен.");
        }

        // Читаем содержимое файла
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean[] toDelete = new boolean[fileContent.length];

        // Вычисляем начальный адрес для текущей страницы
        int startAddress = (currentPage - 1) * pageSize * columnCount;

        // Устанавливаем флаги для удаления выделенных байтов
        for (Point point : selectedCells) {
            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
            if (index >= 0 && index < fileContent.length) {
                removedBytes.add(fileContent[index]); // Сохраняем удаляемые байты
                toDelete[index] = true;
            }
        }

        // Записываем только невыделенные байты в новый массив
        for (int i = 0; i < fileContent.length; i++) {
            if (!toDelete[i]) {
                outputStream.write(fileContent[i]);
            }
        }

        // Сохраняем измененный файл
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(outputStream.toByteArray());
        }

        // Выводим удаленные байты в консоль
        System.out.println("Удаленные байты: " + removedBytes);
    }

    public List<Byte> getRemovedBytes() {
        return removedBytes;
    }
}
