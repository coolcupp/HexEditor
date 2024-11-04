////////package HexEditor;
////////
////////import java.awt.*;
////////import java.io.File;
////////import java.io.FileInputStream;
////////import java.io.IOException;
////////import java.util.ArrayList;
////////import java.util.List;
////////import java.util.Set;
////////
////////public class ByteCopier {
////////    private List<Byte> copiedBytes = new ArrayList<>();
////////
////////    public void copyBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
////////        if (file == null) {
////////            throw new IOException("Файл не загружен.");
////////        }
////////
////////        // Читаем содержимое файла
////////        byte[] fileContent = new byte[(int) file.length()];
////////        try (FileInputStream fis = new FileInputStream(file)) {
////////            fis.read(fileContent);
////////        }
////////
////////        // Вычисляем начальный адрес для текущей страницы
////////        int startAddress = (currentPage - 1) * pageSize * columnCount;
////////
////////        // Копируем выделенные байты в буфер
////////        for (Point point : selectedCells) {
////////            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
////////            if (index >= 0 && index < fileContent.length) {
////////                copiedBytes.add(fileContent[index]); // Сохраняем байт в буфер
////////            }
////////        }
////////
////////        // Выводим скопированные байты в консоль
////////        System.out.println("Скопированные байты: " + copiedBytes);
////////    }
////////
////////    public List<Byte> getCopiedBytes() {
////////        return copiedBytes;
////////    }
////////}
//////
//////package HexEditor;
//////
//////import java.awt.*;
//////import java.io.File;
//////import java.io.FileInputStream;
//////import java.io.IOException;
//////import java.util.ArrayList;
//////import java.util.List;
//////import java.util.Set;
//////
//////public class ByteCopier {
//////    private ByteBuffer byteBuffer;
//////
//////    public ByteCopier(ByteBuffer byteBuffer) {
//////        this.byteBuffer = byteBuffer;
//////    }
//////
//////    public void copyBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
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
//////        // Вычисляем начальный адрес для текущей страницы
//////        int startAddress = (currentPage - 1) * pageSize * columnCount;
//////
//////        List<Byte> copiedBytes = new ArrayList<>();
//////        // Копируем выделенные байты в буфер
//////        for (Point point : selectedCells) {
//////            int index = startAddress + (point.y * columnCount + point.x - 2); // Общий индекс
//////            if (index >= 0 && index < fileContent.length) {
//////                copiedBytes.add(fileContent[index]); // Сохраняем байт в буфер
//////            }
//////        }
//////
//////        // Сохраняем в общий буфер
//////        byteBuffer.copyBytes(copiedBytes);
//////        System.out.println("Скопированные байты: " + copiedBytes);
//////    }
//////}
////
////package HexEditor;
////
////import java.awt.*;
////import java.io.File;
////import java.io.FileInputStream;
////import java.io.IOException;
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Set;
////
////public class ByteCopier {
////    private ByteBuffer byteBuffer;
////
////    public ByteCopier(ByteBuffer byteBuffer) {
////        this.byteBuffer = byteBuffer;
////    }
////
////    public void copyBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
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
////        // Вычисляем начальный адрес для текущей страницы
////        int startAddress = (currentPage - 1) * pageSize * columnCount;
////
////        // Создаем список для скопированных байтов
////        List<Byte> copiedBytes = new ArrayList<>();
////
////        // Считываем выделенные байты в порядке возрастания
////        List<Point> sortedCells = new ArrayList<>(selectedCells);
////        sortedCells.sort((p1, p2) -> {
////            int rowComparison = Integer.compare(p1.y, p2.y);
////            return rowComparison != 0 ? rowComparison : Integer.compare(p1.x, p2.x);
////        });
////
////        // Копируем байты из файла в порядке выделенных ячеек
////        for (Point point : sortedCells) {
////            int index = startAddress + (point.y * columnCount + point.x - 1); // Убедитесь, что используете корректное смещение
////            if (index >= 0 && index < fileContent.length) {
////                copiedBytes.add(fileContent[index]); // Сохраняем байт в буфер
////            }
////        }
////
////        // Сохраняем в общий буфер
////        byteBuffer.copyBytes(copiedBytes);
////        System.out.println("Скопированные байты: " + copiedBytes);
////    }
////}
//
//package HexEditor;
//
//import java.awt.*;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//public class ByteCopier {
//    private ByteBuffer byteBuffer;
//
//    public ByteCopier(ByteBuffer byteBuffer) {
//        this.byteBuffer = byteBuffer;
//    }
//
//    public void copyBytes(File file, Set<Point> selectedCells, int columnCount, int currentPage, int pageSize) throws IOException {
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
//        // Вычисляем начальный адрес для текущей страницы
//        int startAddress = (currentPage - 1) * pageSize * columnCount;
//
//        List<Byte> copiedBytes = new ArrayList<>();
//
//        // Сортируем выделенные ячейки
//        List<Point> sortedCells = new ArrayList<>(selectedCells);
//        sortedCells.sort((p1, p2) -> {
//            int rowComparison = Integer.compare(p1.y, p2.y);
//            return rowComparison != 0 ? rowComparison : Integer.compare(p1.x, p2.x);
//        });
//
//        // Копируем выделенные байты в порядке возрастания
//        for (Point point : sortedCells) {
//            // Используйте корректный индекс для доступа к массиву байтов
//            int index = startAddress + (point.y * columnCount + point.x - 1);
//            if (index >= 0 && index < fileContent.length) {
//                copiedBytes.add(fileContent[index]);
//            }
//        }
//
//        // Сохраняем в общий буфер
//        byteBuffer.copyBytes(copiedBytes);
//        System.out.println("Скопированные байты: " + copiedBytes);
//    }
//}

package HexEditor;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ByteCopier {
    private ByteBuffer byteBuffer;

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
        sortedCells.sort((p1, p2) -> {
            int rowComparison = Integer.compare(p1.y, p2.y);
            return rowComparison != 0 ? rowComparison : Integer.compare(p1.x, p2.x);
        });

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
        System.out.println("Скопированные байты: " + copiedBytes);
    }
}
