////package HexEditor;
////
////import java.io.ByteArrayOutputStream;
////import java.io.File;
////import java.io.FileInputStream;
////import java.io.FileOutputStream;
////import java.io.IOException;
////
////public class ByteRemover {
////
////    public void removeBytes(File file, int startRow, int endRow, int pageSize, int columnCount) throws IOException {
////        if (file == null) {
////            throw new IOException("Файл не загружен.");
////        }
////
////        long addressToDelete = startRow * columnCount; // Начальный адрес для удаления
////        int bytesToDelete = (endRow - startRow + 1) * columnCount; // Количество байтов для удаления
////
////        // Читаем содержимое файла
////        byte[] fileContent = new byte[(int) file.length()];
////        try (FileInputStream fis = new FileInputStream(file)) {
////            fis.read(fileContent);
////        }
////
////        // Создаем новый массив, в который будем записывать данные без удаляемых байтов
////        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
////
////        // Записываем байты до диапазона
////        outputStream.write(fileContent, 0, (int) addressToDelete);
////
////        // Пропускаем диапазон для удаления
////        outputStream.write(fileContent, (int) (addressToDelete + bytesToDelete),
////                (int) (fileContent.length - (addressToDelete + bytesToDelete)));
////
////        // Сохраняем измененный файл
////        try (FileOutputStream fos = new FileOutputStream(file)) {
////            fos.write(outputStream.toByteArray());
////        }
////    }
////}
//
//package HexEditor;
//
//import java.awt.*;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Set;
//
//public class ByteRemover {
//
//public void removeBytes(File file, Set<Point> selectedCells, int columnCount) throws IOException {
//    if (file == null) {
//        throw new IOException("Файл не загружен.");
//    }
//
//    // Читаем содержимое файла
//    byte[] fileContent = new byte[(int) file.length()];
//    try (FileInputStream fis = new FileInputStream(file)) {
//        fis.read(fileContent);
//    }
//
//    // Создаем новый массив, который будет содержать оставшиеся байты
//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    boolean[] toDelete = new boolean[fileContent.length];
//    System.out.println(toDelete);
//
//    // Устанавливаем флаги для удаления выделенных байтов
//    System.out.println(selectedCells);
//    for (Point point : selectedCells) {
//        int index = point.y * columnCount + point.x - 2; // индекс уменьшен на 2 из-за смещения.
//        if (index >= 0 && index < fileContent.length) {
//            toDelete[index] = true;
//        }
//    }
//
//    // Записываем только невыделенные байты в новый массив
//    for (int i = 0; i < fileContent.length; i++) {
//        if (!toDelete[i]) {
//            outputStream.write(fileContent[i]);
//        }
//    }
//
//    // Сохраняем измененный файл
//    try (FileOutputStream fos = new FileOutputStream(file)) {
//        fos.write(outputStream.toByteArray());
//    }
//}
//
//
//}

package HexEditor;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class ByteRemover {

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
