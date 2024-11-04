//package HexEditor;
//
//import java.awt.*;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.Set;
//
//public class ByteInserter {
//    private List<Byte> copiedBytes;
//
//    public ByteInserter(List<Byte> copiedBytes) {
//        this.copiedBytes = copiedBytes;
//    }
//
//    public void insertBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
//        if (file == null || copiedBytes.isEmpty()) {
//            throw new IOException("Файл не загружен или буфер пуст.");
//        }
//
//        // Читаем содержимое файла
//        byte[] fileContent = new byte[(int) file.length()];
//        try (FileInputStream fis = new FileInputStream(file)) {
//            fis.read(fileContent);
//        }
//
//        // Вычисляем начальный адрес для текущей страницы
//        int startAddress = (currentPage - 1) * pageSize * columnCount;
//        int insertPosition = -1;
//
//        // Находим последнюю выделенную ячейку
//        for (Point point : selectedCells) {
//            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
//            if (index >= 0 && index < fileContent.length) {
//                insertPosition = Math.max(insertPosition, index); // Запоминаем максимальный индекс
//            }
//        }
//
//        // Проверяем, было ли найдено место для вставки
//        if (insertPosition == -1) {
//            throw new IOException("Не удалось определить место вставки.");
//        }
//
//        // Создаем новый массив для сохранения измененного содержимого
//        byte[] newContent = new byte[fileContent.length + copiedBytes.size()];
//
//        // Копируем байты до места вставки
//        System.arraycopy(fileContent, 0, newContent, 0, insertPosition + 1);
//
//        // Вставляем скопированные байты
//        for (int i = 0; i < copiedBytes.size(); i++) {
//            newContent[insertPosition + 1 + i] = copiedBytes.get(i);
//        }
//
//        // Копируем оставшиеся байты
//        System.arraycopy(fileContent, insertPosition + 1, newContent, insertPosition + 1 + copiedBytes.size(), fileContent.length - insertPosition - 1);
//
//        // Сохраняем измененный файл
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            fos.write(newContent);
//        }
//
//        // Выводим вставленные байты в консоль
//        System.out.println("Вставленные байты: " + copiedBytes);
//    }
//}

package HexEditor;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ByteInserter {
    private ByteBuffer byteBuffer;

    public ByteInserter(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public void insertBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
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
        int insertPosition = -1;

        // Находим последнюю выделенную ячейку
        for (Point point : selectedCells) {
            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
            if (index >= 0 && index < fileContent.length) {
                insertPosition = Math.max(insertPosition, index); // Запоминаем максимальный индекс
            }
        }

        // Проверяем, было ли найдено место для вставки
        if (insertPosition == -1) {
            throw new IOException("Не удалось определить место вставки.");
        }

        // Получаем скопированные байты из буфера
        List<Byte> copiedBytes = byteBuffer.getBuffer();

        // Создаем новый массив для сохранения измененного содержимого
        byte[] newContent = new byte[fileContent.length + copiedBytes.size()];

        // Копируем байты до места вставки
        System.arraycopy(fileContent, 0, newContent, 0, insertPosition + 1);

        // Вставляем скопированные байты
        for (int i = 0; i < copiedBytes.size(); i++) {
            newContent[insertPosition + 1 + i] = copiedBytes.get(i);
        }

        // Копируем оставшиеся байты
        System.arraycopy(fileContent, insertPosition + 1, newContent, insertPosition + 1 + copiedBytes.size(), fileContent.length - insertPosition - 1);

        // Сохраняем измененный файл
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(newContent);
        }

        // Выводим вставленные байты в консоль
        System.out.println("Вставленные байты: " + copiedBytes);
    }
}
