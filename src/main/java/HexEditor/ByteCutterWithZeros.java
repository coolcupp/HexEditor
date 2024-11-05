////package HexEditor;
////
////import java.awt.*;
////import java.io.File;
////import java.io.FileInputStream;
////import java.io.FileOutputStream;
////import java.io.IOException;
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Set;
////
////public class ByteCutterWithPadding {
////    private List<Byte> removedBytes = new ArrayList<>();
////
////    public void cutBytesWithPadding(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
////        if (file == null) {
////            throw new IOException("Файл не загружен.");
////        }
////
////        // Читаем содержимое файла
////        byte[] fileContent = new byte[(int) file.length()];
////        try (FileInputStream fis = new FileInputStream(file)) {
////            fis.read(fileContent);
////        }
////
////        byte[] modifiedContent = new byte[fileContent.length];
////
////        // Вычисляем начальный адрес для текущей страницы
////        int startAddress = (currentPage - 1) * pageSize * columnCount;
////
////        // Устанавливаем байты в новый массив
////        System.arraycopy(fileContent, 0, modifiedContent, 0, fileContent.length);
////
////        // Устанавливаем флаги для замены выделенных байтов на 00
////        for (Point point : selectedCells) {
////            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
////            if (index >= 0 && index < fileContent.length) {
////                removedBytes.add(fileContent[index]); // Сохраняем удаляемые байты
////                modifiedContent[index] = 0; // Заменяем на 00
////            }
////        }
////
////        // Сохраняем измененный файл
////        try (FileOutputStream fos = new FileOutputStream(file)) {
////            fos.write(modifiedContent);
////        }
////
////        // Выводим удаленные байты в консоль
////        System.out.println("Обнуленные байты: " + removedBytes);
////    }
////
////    public List<Byte> getRemovedBytes() {
////        return removedBytes;
////    }
////}
//
//package HexEditor;
//
//import java.awt.*;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//public class ByteCutterWithPadding {
//    private ByteBuffer byteBuffer;
//
//    public ByteCutterWithPadding(ByteBuffer byteBuffer) {
//        this.byteBuffer = byteBuffer;
//    }
//
//    public void cutBytesWithPadding(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
//        if (file == null) {
//            throw new IOException("Файл не загружен.");
//        }
//
//        // Читаем содержимое файла
//        byte[] fileContent = new byte[(int) file.length()];
//        try (FileInputStream fis = new FileInputStream(file)) {
//            fis.read(fileContent);
//        }
//
//        byte[] modifiedContent = new byte[fileContent.length];
//        List<Byte> removedBytes = new ArrayList<>(); // Список для хранения удалённых байтов
//
//        // Вычисляем начальный адрес для текущей страницы
//        int startAddress = (currentPage - 1) * pageSize * columnCount;
//
//        // Устанавливаем байты в новый массив
//        System.arraycopy(fileContent, 0, modifiedContent, 0, fileContent.length);
//
//        // Устанавливаем флаги для замены выделенных байтов на 00
//        for (Point point : selectedCells) {
//            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
//            if (index >= 0 && index < fileContent.length) {
//                removedBytes.add(fileContent[index]); // Сохраняем удаляемые байты
//                modifiedContent[index] = 0; // Заменяем на 00
//            }
//        }
//
//        // Сохраняем все удалённые байты в буфер
//        byteBuffer.copyBytes(removedBytes);
//
//        // Сохраняем измененный файл
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            fos.write(modifiedContent);
//        }
//
//        // Выводим удалённые байты из буфера в консоль
//        System.out.println("Обнуленные байты: " + byteBuffer.getBuffer());
//    }
//
//    public List<Byte> getRemovedBytes() {
//        return byteBuffer.getBuffer(); // Возвращаем содержимое буфера
//    }
//}

package HexEditor;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ByteCutterWithZeros {
    private ByteBuffer byteBuffer;

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
        sortedCells.sort((p1, p2) -> {
            int rowComparison = Integer.compare(p1.y, p2.y);
            return rowComparison != 0 ? rowComparison : Integer.compare(p1.x, p2.x);
        });

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

    public List<Byte> getRemovedBytes() {
        return byteBuffer.getBuffer(); // Возвращаем содержимое буфера
    }
}
