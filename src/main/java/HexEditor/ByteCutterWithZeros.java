package HexEditor;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ByteCutterWithZeros {
    private final ByteBuffer byteBuffer;

    public ByteCutterWithZeros(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public void cutBytesWithPadding(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
        if (file == null) {
            throw new IOException("Файл не загружен.");
        }

        // Читаем содержимое файла
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        }

        // Модифицированное содержимое файла
        byte[] modifiedContent = new byte[fileContent.length];
        List<Byte> removedBytes = new ArrayList<>(); // Список для хранения удалённых байтов

        // Вычисляем начальный адрес для текущей страницы
        int startAddress = (currentPage - 1) * pageSize * columnCount;

        // Устанавливаем байты в новый массив
        System.arraycopy(fileContent, 0, modifiedContent, 0, fileContent.length);

        // Сортируем выделенные ячейки
        List<Point> sortedCells = new ArrayList<>(selectedCells);
        sortedCells.sort(Comparator.comparingInt((Point p) -> p.y).thenComparingInt(p -> p.x));

        // Устанавливаем флаги для замены выделенных байтов на 00
        for (Point point : sortedCells) {
            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
            if (index >= 0 && index < fileContent.length) {
                removedBytes.add(fileContent[index]); // Сохраняем удаляемые байты
                modifiedContent[index] = 0; // Заменяем на 00
            }
        }

        // Сохраняем все удалённые байты в буфер
        byteBuffer.copyBytes(removedBytes);

        // Сохраняем изменённый файл
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(modifiedContent);
        }

        // Выводим удалённые байты из буфера в консоль
        System.out.println("Обнулённые байты: " + byteBuffer.getBuffer());
    }
}
