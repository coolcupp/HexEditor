package HexEditor.bytes;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class ByteRemoverWithShift {

    public void removeBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
        if (file == null) {
            throw new IOException("Файл не загружен.");
        }

        // Читаем содержимое файла
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        }

        // Создаем новый массив, который будет содержать оставшиеся байты
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean[] toDelete = new boolean[fileContent.length];

        // Вычисляем начальный адрес для текущей страницы
        int startAddress = (currentPage - 1) * pageSize * columnCount;

        // Устанавливаем флаги для удаления выделенных байтов
        for (Point point : selectedCells) {
            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
            if (index >= 0 && index < fileContent.length) {
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
    }
}
