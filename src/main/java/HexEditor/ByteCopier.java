package HexEditor;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ByteCopier {
    private final ByteBuffer byteBuffer;

    public ByteCopier(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

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

        List<Byte> copiedBytes = new ArrayList<>();

        // Сортируем выделенные ячейки
        List<Point> sortedCells = new ArrayList<>(selectedCells);
        sortedCells.sort(Comparator.comparingInt((Point p) -> p.y).thenComparingInt(p -> p.x));

        // Копируем выделенные байты в порядке возрастания
        for (Point point : sortedCells) {
            // Используйте корректный индекс для доступа к массиву байтов
            int index = startAddress + (point.y * columnCount + point.x - 2); // Измените на -2 для правильного доступа
            if (index >= 0 && index < fileContent.length) {
                copiedBytes.add(fileContent[index]);
            }
        }

        // Сохраняем в общий буфер
        byteBuffer.copyBytes(copiedBytes);
    }
}
