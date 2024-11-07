package HexEditor;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ByteInserterWithChanging {
    private final ByteBuffer byteBuffer;

    public ByteInserterWithChanging(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public void replaceBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
        if (file == null || byteBuffer.isEmpty()) {
            throw new IOException("Файл не загружен или буфер пуст.");
        }

        // Читаем содержимое файла
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        }

        // Вычисляем начальный адрес для текущей страницы
        int startAddress = (currentPage - 1) * pageSize * columnCount;

        // Получаем байты из буфера
        List<Byte> bytesToReplace = byteBuffer.getBuffer();
        int bufferSize = bytesToReplace.size();

        // Находим первую выделенную ячейку
        int firstIndex = -1;
        for (Point point : selectedCells) {
            firstIndex = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
            break; // Выходим из цикла после первой найденной ячейки
        }

        // Проверяем, было ли найдено место для замены
        if (firstIndex == -1 || firstIndex >= fileContent.length) {
            throw new IOException("Не удалось определить место замены.");
        }

        // Заменяем байты, начиная с первой выделенной ячейки
        for (int i = 0; i < bufferSize; i++) {
            int indexToReplace = firstIndex + i;
            if (indexToReplace < fileContent.length) {
                fileContent[indexToReplace] = bytesToReplace.get(i); // Заменяем байт
            } else {
                break; // Если вышли за пределы массива, останавливаем замену
            }
        }

        // Сохраняем измененный файл
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileContent);
        }
    }
}
