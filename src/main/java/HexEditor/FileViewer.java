package HexEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FileViewer {
    private JTextArea textArea;

    public FileViewer(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void updateTextArea(DefaultTableModel model) {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 2; col < model.getColumnCount(); col++) { // Начинаем с 3-го столбца
                Object value = model.getValueAt(row, col);
                if (value instanceof String && !((String) value).isEmpty()) {
                    String hexValue = (String) value;
                    if (hexValue.matches("[0-9A-Fa-f]+")) {
                        int intValue = Integer.parseInt(hexValue, 16);
                        sb.append((intValue >= 32 && intValue <= 126) ? (char) intValue : '.');
                    }
                }
            }
        }
        textArea.setText(sb.toString());
    }
}
