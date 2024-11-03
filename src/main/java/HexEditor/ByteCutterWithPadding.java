package HexEditor;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ByteCutterWithPadding {
    private List<Byte> removedBytes = new ArrayList<>();

    public void cutBytesWithPadding(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
        if (file == null) {
            throw new IOException("Файл не загружен.");
        }

        // Читаем содержимое файла
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        }

        byte[] modifiedContent = new byte[fileContent.length];

        // Вычисляем начальный адрес для текущей страницы
        int startAddress = (currentPage - 1) * pageSize * columnCount;

        // Устанавливаем байты в новый массив
        System.arraycopy(fileContent, 0, modifiedContent, 0, fileContent.length);

        // Устанавливаем флаги для замены выделенных байтов на 00
        for (Point point : selectedCells) {
            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
            if (index >= 0 && index < fileContent.length) {
                removedBytes.add(fileContent[index]); // Сохраняем удаляемые байты
                modifiedContent[index] = 0; // Заменяем на 00
            }
        }

        // Сохраняем измененный файл
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(modifiedContent);
        }

        // Выводим удаленные байты в консоль
        System.out.println("Обнуленные байты: " + removedBytes);
    }

    public List<Byte> getRemovedBytes() {
        return removedBytes;
    }
}
