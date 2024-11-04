//////package HexEditor;
//////
//////import java.awt.*;
//////import java.io.ByteArrayOutputStream;
//////import java.io.File;
//////import java.io.FileInputStream;
//////import java.io.FileOutputStream;
//////import java.io.IOException;
//////import java.util.ArrayList;
//////import java.util.List;
//////import java.util.Set;
//////
//////public class ByteCutter {
//////    private List<Byte> removedBytes = new ArrayList<>();
//////
//////    public void cutBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
//////        if (file == null) {
//////            throw new IOException("Файл не загружен.");
//////        }
//////
//////        // Читаем содержимое файла
//////        byte[] fileContent = new byte[(int) file.length()];
//////        try (FileInputStream fis = new FileInputStream(file)) {
//////            fis.read(fileContent);
//////        }
//////
//////        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//////        boolean[] toDelete = new boolean[fileContent.length];
//////
//////        // Вычисляем начальный адрес для текущей страницы
//////        int startAddress = (currentPage - 1) * pageSize * columnCount;
//////
//////        // Устанавливаем флаги для удаления выделенных байтов
//////        for (Point point : selectedCells) {
//////            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
//////            if (index >= 0 && index < fileContent.length) {
//////                removedBytes.add(fileContent[index]); // Сохраняем удаляемые байты
//////                toDelete[index] = true;
//////            }
//////        }
//////
//////        // Записываем только невыделенные байты в новый массив
//////        for (int i = 0; i < fileContent.length; i++) {
//////            if (!toDelete[i]) {
//////                outputStream.write(fileContent[i]);
//////            }
//////        }
//////
//////        // Сохраняем измененный файл
//////        try (FileOutputStream fos = new FileOutputStream(file)) {
//////            fos.write(outputStream.toByteArray());
//////        }
//////
//////        // Выводим удаленные байты в консоль
//////        System.out.println("Вырезанные байты: " + removedBytes);
//////    }
//////
//////    public List<Byte> getRemovedBytes() {
//////        return removedBytes;
//////    }
//////}
////
////package HexEditor;
////
////import java.awt.*;
////import java.io.ByteArrayOutputStream;
////import java.io.File;
////import java.io.FileInputStream;
////import java.io.FileOutputStream;
////import java.io.IOException;
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Set;
////
////public class ByteCutter {
////    private ByteBuffer byteBuffer;
////
////    public ByteCutter(ByteBuffer byteBuffer) {
////        this.byteBuffer = byteBuffer;
////    }
////
////    public void cutBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
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
////        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
////        boolean[] toDelete = new boolean[fileContent.length];
////
////        // Вычисляем начальный адрес для текущей страницы
////        int startAddress = (currentPage - 1) * pageSize * columnCount;
////
////        // Устанавливаем флаги для удаления выделенных байтов
////        for (Point point : selectedCells) {
////            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
////            if (index >= 0 && index < fileContent.length) {
////                // Вместо List.of() используем Arrays.asList()
////                byteBuffer.copyBytes(new ArrayList<Byte>() {{ add(fileContent[index]); }}); // Сохраняем вырезанные байты в буфер
////                toDelete[index] = true;
////            }
////        }
////
////        // Записываем только невыделенные байты в новый массив
////        for (int i = 0; i < fileContent.length; i++) {
////            if (!toDelete[i]) {
////                outputStream.write(fileContent[i]);
////            }
////        }
////
////        // Сохраняем измененный файл
////        try (FileOutputStream fos = new FileOutputStream(file)) {
////            fos.write(outputStream.toByteArray());
////        }
////
////        // Выводим удаленные байты из буфера в консоль
////        System.out.println("Вырезанные байты: " + byteBuffer.getBuffer());
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
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//public class ByteCutter {
//    private ByteBuffer byteBuffer;
//
//    public ByteCutter(ByteBuffer byteBuffer) {
//        this.byteBuffer = byteBuffer;
//    }
//
//    public void cutBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
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
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        boolean[] toDelete = new boolean[fileContent.length];
//        List<Byte> removedBytes = new ArrayList<>(); // Список для хранения вырезанных байтов
//
//        // Вычисляем начальный адрес для текущей страницы
//        int startAddress = (currentPage - 1) * pageSize * columnCount;
//
//        // Устанавливаем флаги для удаления выделенных байтов
//        for (Point point : selectedCells) {
//            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
//            if (index >= 0 && index < fileContent.length) {
//                removedBytes.add(fileContent[index]); // Сохраняем вырезанные байты
//                toDelete[index] = true;
//            }
//        }
//
//        // Сохраняем все вырезанные байты в буфер
//        byteBuffer.copyBytes(removedBytes);
//
//        // Записываем только невыделенные байты в новый массив
//        for (int i = 0; i < fileContent.length; i++) {
//            if (!toDelete[i]) {
//                outputStream.write(fileContent[i]);
//            }
//        }
//
//        // Сохраняем измененный файл
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            fos.write(outputStream.toByteArray());
//        }
//
//        // Выводим удаленные байты из буфера в консоль
//        System.out.println("Вырезанные байты: " + byteBuffer.getBuffer());
//    }
//}

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
    private ByteBuffer byteBuffer;

    public ByteCutter(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

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
        List<Byte> removedBytes = new ArrayList<>(); // Список для хранения вырезанных байтов

        // Вычисляем начальный адрес для текущей страницы
        int startAddress = (currentPage - 1) * pageSize * columnCount;

        // Сортируем выделенные ячейки
        List<Point> sortedCells = new ArrayList<>(selectedCells);
        sortedCells.sort((p1, p2) -> {
            int rowComparison = Integer.compare(p1.y, p2.y);
            return rowComparison != 0 ? rowComparison : Integer.compare(p1.x, p2.x);
        });

        // Устанавливаем флаги для удаления выделенных байтов
        for (Point point : sortedCells) {
            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
            if (index >= 0 && index < fileContent.length) {
                removedBytes.add(fileContent[index]); // Сохраняем вырезанные байты
                toDelete[index] = true;
            }
        }

        // Сохраняем все вырезанные байты в буфер
        byteBuffer.copyBytes(removedBytes);

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

        // Выводим удаленные байты из буфера в консоль
        System.out.println("Вырезанные байты: " + removedBytes);
    }
}
