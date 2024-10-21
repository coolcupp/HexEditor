//package HexEditor;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.Set;
//
//public class SelectionInfo {
//    private CustomTable table;
//    private JTextArea outputArea;
//
//    public SelectionInfo(CustomTable table, JTextArea outputArea) {
//        this.table = table;
//        this.outputArea = outputArea;
//
//        table.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                updateSelectionInfo();
//            }
//        });
//    }
//
//    private void updateSelectionInfo() {
//        Set<Point> selectedCells = table.getSelectedCells();
//        if (selectedCells.isEmpty()) {
//            outputArea.setText("");
//            return;
//        }
//
//        StringBuilder infoBuilder = new StringBuilder();
//        int[] byteValues = new int[selectedCells.size()];
//        int index = 0;
//
//        for (Point point : selectedCells) {
//            Object value = table.getValueAt(point.y, point.x);
//            if (value instanceof String) {
//                try {
//                    byteValues[index++] = Integer.parseInt((String) value, 16);
//                } catch (NumberFormatException ignored) {
//                    infoBuilder.append("Invalid value: ").append(value).append("\n");
//                }
//            }
//        }
//
//        // Убираем отображение строки
//
//        // Обработка числовых значений
//        if (byteValues.length >= 1) {
//            // int8 и uint8
//            byte int8Value = (byte) (byteValues[0] & 0xFF);
//            int uint8Value = int8Value & 0xFF;
//            infoBuilder.append(String.format("int8: %d, uint8: %d\n", int8Value, uint8Value));
//        }
//        if (byteValues.length >= 2) {
//            // int16 и uint16
//            short int16Value = (short) ((byteValues[0] & 0xFF) | ((byteValues[1] & 0xFF) << 8));
//            int uint16Value = int16Value & 0xFFFF;
//            infoBuilder.append(String.format("int16: %d, uint16: %d\n", int16Value, uint16Value));
//        }
//        if (byteValues.length >= 3) {
//            // int24 и uint24
//            int int24Value = (byteValues[0] & 0xFF) | ((byteValues[1] & 0xFF) << 8) | ((byteValues[2] & 0xFF) << 16);
//            long uint24Value = int24Value & 0xFFFFFFL;
//            infoBuilder.append(String.format("int24: %d, uint24: %d\n", int24Value, uint24Value));
//        }
//        if (byteValues.length >= 4) {
//            // int32 и uint32
//            int int32Value = (byteValues[0] & 0xFF) | ((byteValues[1] & 0xFF) << 8) |
//                    ((byteValues[2] & 0xFF) << 16) | ((byteValues[3] & 0xFF) << 24);
//            long uint32Value = int32Value & 0xFFFFFFFFL;
//            infoBuilder.append(String.format("int32: %d, uint32: %d\n", int32Value, uint32Value));
//
//            // float32 (single)
//            float floatValue = Float.intBitsToFloat(int32Value);
//            infoBuilder.append(String.format("single (float32): %.6f\n", floatValue));
//        }
//        if (byteValues.length >= 8) {
//            // double (float64)
//            long doubleBits = 0;
//            for (int i = 0; i < 8; i++) {
//                doubleBits |= ((long) byteValues[i] & 0xFF) << (56 - i * 8);
//            }
//            double doubleValue = Double.longBitsToDouble(doubleBits);
//            infoBuilder.append(String.format("double (float64): %.6f\n", doubleValue));
//        }
//
//        outputArea.setText(infoBuilder.toString().trim());
//    }
//}


package HexEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

public class SelectionInfo {
    private CustomTable table;
    private JTextArea outputArea;

    public SelectionInfo(CustomTable table, JTextArea outputArea) {
        this.table = table;
        this.outputArea = outputArea;

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                updateSelectionInfo();
            }
        });
    }

//    private void updateSelectionInfo() {
//        Set<Point> selectedCells = table.getSelectedCells();
//        if (selectedCells.isEmpty()) {
//            outputArea.setText("");
//            return;
//        }
//
//        StringBuilder infoBuilder = new StringBuilder();
//        int[] byteValues = new int[selectedCells.size()];
//        int index = 0;
//
//        for (Point point : selectedCells) {
//            Object value = table.getValueAt(point.y, point.x);
//            if (value instanceof String) {
//                try {
//                    byteValues[index++] = Integer.parseInt((String) value, 16);
//                } catch (NumberFormatException ignored) {
//                    infoBuilder.append("Invalid value: ").append(value).append("\n");
//                }
//            }
//        }
//
//        // Убираем отображение строки
//        // Обработка числовых значений
//        if (byteValues.length >= 1) {
//            // int8 и uint8
//            byte int8Value = (byte) (byteValues[0] & 0xFF);
//            int uint8Value = int8Value & 0xFF;
//            infoBuilder.append(String.format("int8: %d, uint8: %d\n", int8Value, uint8Value));
//        }
//        if (byteValues.length >= 2) {
//            // int16 и uint16
//            short int16Value = (short) ((byteValues[0] & 0xFF) | ((byteValues[1] & 0xFF) << 8));
//            int uint16Value = int16Value & 0xFFFF;
//            infoBuilder.append(String.format("int16: %d, uint16: %d\n", int16Value, uint16Value));
//        }
//        if (byteValues.length >= 3) {
//            // int24 и uint24
//            int int24Value = (byteValues[0] & 0xFF) | ((byteValues[1] & 0xFF) << 8) | ((byteValues[2] & 0xFF) << 16);
//            long uint24Value = int24Value & 0xFFFFFFL;
//            infoBuilder.append(String.format("int24: %d, uint24: %d\n", int24Value, uint24Value));
//        }
//        if (byteValues.length >= 4) {
//            // int32 и uint32
//            int int32Value = (byteValues[0] & 0xFF) | ((byteValues[1] & 0xFF) << 8) |
//                    ((byteValues[2] & 0xFF) << 16) | ((byteValues[3] & 0xFF) << 24);
//            long uint32Value = int32Value & 0xFFFFFFFFL;
//            infoBuilder.append(String.format("int32: %d, uint32: %d\n", int32Value, uint32Value));
//
//            // float32 (single)
//            float floatValue = Float.intBitsToFloat(int32Value);
//            infoBuilder.append(String.format("single (float32): %.6f\n", floatValue));
//        }
//        if (byteValues.length >= 8) {
//            // double (float64)
//            long doubleBits = 0;
//            for (int i = 0; i < 8; i++) {
//                doubleBits |= ((long) byteValues[i] & 0xFF) << (8 * i);
//            }
//            double doubleValue = Double.longBitsToDouble(doubleBits);
//            infoBuilder.append(String.format("double (float64): %.6f\n", doubleValue));
//        }
//
//        outputArea.setText(infoBuilder.toString().trim());
//    }
private void updateSelectionInfo() {
    Set<Point> selectedCells = table.getSelectedCells();
    if (selectedCells.isEmpty()) {
        outputArea.setText("");
        return;
    }

    StringBuilder infoBuilder = new StringBuilder();
    int[] byteValues = new int[selectedCells.size()];
    int index = 0;

    // Считываем все выделенные байты
    for (Point point : selectedCells) {
        Object value = table.getValueAt(point.y, point.x);
        if (value instanceof String) {
            try {
                byteValues[index++] = Integer.parseInt((String) value, 16);
            } catch (NumberFormatException ignored) {
                infoBuilder.append("Invalid value: ").append(value).append("\n");
            }
        }
    }

    // Теперь обработаем значения в зависимости от количества байт
    if (byteValues.length >= 1) {
        // Обработка int8 и uint8
        byte int8Value = (byte) (byteValues[0] & 0xFF);
        int uint8Value = int8Value & 0xFF;
        infoBuilder.append(String.format("int8: %d, uint8: %d\n", int8Value, uint8Value));
    }
    if (byteValues.length >= 2) {
        // Обработка int16 и uint16
        short int16Value = (short) ((byteValues[0] & 0xFF) | ((byteValues[1] & 0xFF) << 8));
        int uint16Value = int16Value & 0xFFFF;
        infoBuilder.append(String.format("int16: %d, uint16: %d\n", int16Value, uint16Value));
    }
    if (byteValues.length >= 3) {
        // Обработка int24 и uint24
        int int24Value = (byteValues[0] & 0xFF) | ((byteValues[1] & 0xFF) << 8) | ((byteValues[2] & 0xFF) << 16);
        long uint24Value = int24Value & 0xFFFFFFL;
        infoBuilder.append(String.format("int24: %d, uint24: %d\n", int24Value, uint24Value));
    }
    if (byteValues.length >= 4) {
        // Обработка int32 и uint32
        int int32Value = (byteValues[0] & 0xFF) | ((byteValues[1] & 0xFF) << 8) |
                ((byteValues[2] & 0xFF) << 16) | ((byteValues[3] & 0xFF) << 24);
        long uint32Value = int32Value & 0xFFFFFFFFL;
        infoBuilder.append(String.format("int32: %d, uint32: %d\n", int32Value, uint32Value));

        // float32 (single)
        float floatValue = Float.intBitsToFloat(int32Value);
        infoBuilder.append(String.format("single (float32): %.6f\n", floatValue));
    }
    if (byteValues.length >= 8) {
        // double (float64)
        long doubleBits = 0;
        for (int i = 0; i < 8; i++) {
            doubleBits |= ((long) byteValues[i] & 0xFF) << (8 * i);
        }
        double doubleValue = Double.longBitsToDouble(doubleBits);
        infoBuilder.append(String.format("double (float64): %.6f\n", doubleValue));
    }

    outputArea.setText(infoBuilder.toString().trim());
}

}
