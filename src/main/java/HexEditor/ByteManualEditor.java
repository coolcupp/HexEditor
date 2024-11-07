package HexEditor;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteManualEditor {

    // Метод для обновления байта в файле
    public static void updateByteInFile(File file, int columnCount, int currentPage, int pageSize, Point cell, byte newValue) throws IOException {
        if (file == null || !file.exists()) {
            throw new IOException("Файл не найден.");
        }

        // Читаем содержимое файла
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        }

        // Вычисляем начальный адрес для текущей страницы
        int startAddress = (currentPage - 1) * pageSize * columnCount;

        // Рассчитываем индекс ячейки в файле
        int indexToUpdate = startAddress + (cell.y * columnCount + cell.x - 2); // Общий индекс ячейки

        // Проверяем, что индекс в пределах файла
        if (indexToUpdate >= fileContent.length) {
            throw new IOException("Попытка обновить байт за пределами файла.");
        }

        // Обновляем байт
        fileContent[indexToUpdate] = newValue;

        // Сохраняем измененный файл
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileContent);
        }
    }

    // Метод для проверки корректности введенного значения
    public static boolean isValidByte(String inputValue) {
        try {
            // Преобразуем строку в байт (ожидаем, что строка в шестнадцатеричном формате)
            Integer.parseInt(inputValue, 16);  // Преобразуем в 16-ричный формат
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
