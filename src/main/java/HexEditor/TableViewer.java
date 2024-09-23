package HexEditor;

import javax.swing.*;

public class TableViewer {
    private JTextArea textArea;
    private JTable table;
    public TableViewer(JTable table, JTextArea textArea){
        this.textArea = textArea;
        this.table = table;
        updateTextArea();
    }

    public void updateTextArea(){
        StringBuilder sb = new StringBuilder();

        for (int row = 1; row < table.getRowCount(); row++){
            for (int col = 2; col < table.getColumnCount(); col++){
                Object value = table.getValueAt(row, col);
                if (value != null){

                    String hexString = value.toString();
                    try{
                        int intValue = Integer.parseInt(hexString, 16);
                        sb.append((char) intValue);
                    } catch (NumberFormatException ex){
                        sb.append("?");
                    }
                }
            }
            textArea.setText(sb.toString());

        }

    }


}
