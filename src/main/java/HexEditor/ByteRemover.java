//package HexEditor;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class ByteRemover {
//
//    public void removeBytes(File file, int startRow, int endRow, int pageSize, int columnCount) throws IOException {
//        if (file == null) {
//            throw new IOException("Файл не загружен.");
//        }
//
//        long addressToDelete = startRow * columnCount; // Начальный адрес для удаления
//        int bytesToDelete = (endRow - startRow + 1) * columnCount; // Количество байтов для удаления
//
//        // Читаем содержимое файла
//        byte[] fileContent = new byte[(int) file.length()];
//        try (FileInputStream fis = new FileInputStream(file)) {
//            fis.read(fileContent);
//        }
//
//        // Создаем новый массив, в который будем записывать данные без удаляемых байтов
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        // Записываем байты до диапазона
//        outputStream.write(fileContent, 0, (int) addressToDelete);
//
//        // Пропускаем диапазон для удаления
//        outputStream.write(fileContent, (int) (addressToDelete + bytesToDelete),
//                (int) (fileContent.length - (addressToDelete + bytesToDelete)));
//
//        // Сохраняем измененный файл
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            fos.write(outputStream.toByteArray());
//        }
//    }
//}

package HexEditor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteRemover {

    public void removeBytes(File file, int startRow, int endRow, int pageSize, int columnCount) throws IOException {
        if (file == null) {
            throw new IOException("Файл не загружен.");
        }

        // Вычисляем начальный и конечный адреса для удаления
        long startAddress = (startRow * columnCount);
        long endAddress = (endRow + 1) * columnCount;
        System.out.println("startAddress: " + startAddress + "\n" + "endAddress: " + endAddress);

        // Читаем содержимое файла
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        }

        // Создаем новый массив
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Записываем байты до начального адреса
        outputStream.write(fileContent, 0, (int) startAddress);

        // Пропускаем диапазон для удаления
        outputStream.write(fileContent, (int) endAddress,
                (int) (fileContent.length - endAddress));

        // Сохраняем измененный файл
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(outputStream.toByteArray());
        }
    }

}
