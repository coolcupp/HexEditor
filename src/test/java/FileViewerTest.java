import HexEditor.files.FileViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.junit.jupiter.api.Assertions.*;

public class FileViewerTest {
    private JTextArea textArea;
    private FileViewer fileViewer;

    @BeforeEach
    public void setUp() {
        // Создаём реальные объекты
        textArea = new JTextArea();
        fileViewer = new FileViewer(textArea);
    }

    @Test
    public void testUpdateTextAreaWithValidHexValues() {
        // Создаём DefaultTableModel с тестовыми данными
        Object[][] data = {
                {"Row1", "Column1", "48", "65", "6C", "6F"},  // "Hello"
                {"Row2", "Column2", "57", "6F", "72", "6C"}   // "World"
        };

        // Указываем названия столбцов
        Object[] columnNames = {"Index", "Description", "Byte1", "Byte2", "Byte3", "Byte4"};

        // Создаём модель таблицы
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Вызываем метод для обновления текста в textArea
        fileViewer.updateTextArea(model);

        // Проверяем содержимое JTextArea
        String expectedText = "HeloWorl";
        assertEquals(expectedText, textArea.getText());
    }

    @Test
    public void testUpdateTextAreaWithEmptyHexValues() {
        // Создаём DefaultTableModel с тестовыми данными
        Object[][] data = {
                {"Row1", "Column1", "", "", "", ""},  // Пустые значения
                {"Row2", "Column2", "", "", "", ""}   // Пустые значения
        };

        // Указываем названия столбцов
        Object[] columnNames = {"Index", "Description", "Byte1", "Byte2", "Byte3", "Byte4"};

        // Создаём модель таблицы
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Вызываем метод для обновления текста в textArea
        fileViewer.updateTextArea(model);

        // Проверяем, что текст в textArea пустой
        assertEquals("", textArea.getText());
    }

    @Test
    public void testUpdateTextAreaWithNonPrintableHexValues() {
        // Создаём DefaultTableModel с тестовыми данными
        Object[][] data = {
                {"Row1", "Column1", "00", "01", "02", "03"},  // Не отображаемые символы
                {"Row2", "Column2", "10", "20", "30", "40"}   // Не отображаемые символы
        };

        // Указываем названия столбцов
        Object[] columnNames = {"Index", "Description", "Byte1", "Byte2", "Byte3", "Byte4"};

        // Создаём модель таблицы
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Вызываем метод для обновления текста в textArea
        fileViewer.updateTextArea(model);

        // Проверяем, что текст в textArea состоит из точек, так как символы не отображаются
        String expectedText = "..... 0@";
        assertEquals(expectedText, textArea.getText());
    }

    @Test
    public void testUpdateTextAreaWithMixedHexValues() {
        // Создаём DefaultTableModel с тестовыми данными
        Object[][] data = {
                {"Row1", "Column1", "41", "42", "43", "44"},  // "ABCD"
                {"Row2", "Column2", "31", "32", "33", "34"}   // "1234"
        };

        // Указываем названия столбцов
        Object[] columnNames = {"Index", "Description", "Byte1", "Byte2", "Byte3", "Byte4"};

        // Создаём модель таблицы
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Вызываем метод для обновления текста в textArea
        fileViewer.updateTextArea(model);

        // Проверяем, что текст в textArea соответствует ожидаемому
        String expectedText = "ABCD1234";
        assertEquals(expectedText, textArea.getText());
    }
}
