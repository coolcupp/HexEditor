package HexEditor;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ByteCopier {
    private List<Byte> copiedBytes = new ArrayList<>();

    public void copyBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
        if (file == null) {
            throw new IOException("Файл не загружен.");
        }

        // Читаем содержимое файла
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        }

        // Вычисляем начальный адрес для текущей страницы
        int startAddress = (currentPage - 1) * pageSize * columnCount;

        // Копируем выделенные байты в буфер
        for (Point point : selectedCells) {
            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
            if (index >= 0 && index < fileContent.length) {
                copiedBytes.add(fileContent[index]); // Сохраняем байт в буфер
            }
        }

        // Выводим скопированные байты в консоль
        System.out.println("Скопированные байты: " + copiedBytes);
    }

    public List<Byte> getCopiedBytes() {
        return copiedBytes;
    }
}
