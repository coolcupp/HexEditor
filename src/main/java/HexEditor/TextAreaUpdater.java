package HexEditor;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLOutput;
import java.util.zip.CheckedOutputStream;

public class TextAreaUpdater implements TableModelListener {
    private JTextArea textArea;
    private JTable table;

    public TextAreaUpdater(JTextArea textArea, JTable table){
        this.textArea = textArea;
        this.table = table;

        // add listener to tableModel
        table.getModel().addTableModelListener(this);
    }

    @Override
    public void tableChanged(TableModelEvent tableModelEvent) {
        updateTextArea();
    }

    private void updateTextArea(){
        StringBuilder sb = new StringBuilder();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int row = 0; row < model.getRowCount(); row++){
            for (int col = 0; col < model.getColumnCount(); col++){
                Object value = model.getValueAt(row, col);
                if (value instanceof String){
                    try{
                        String hexValue = (String) value;
                        if (hexValue.matches("[0-9A-Fa-f]+")) {
                            int intValue = Integer.parseInt(hexValue, 16);
                            if (intValue >= 32 && intValue <= 126){
                                sb.append((char) intValue);
                            }
                            else {
                                sb.append('.');
                            }
                        }
                    } catch (NumberFormatException ex){
                        sb.append('.');
                    }

                }
            }
        }
        textArea.setText(sb.toString());
    }
}
