package HexEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableResizer {
    private OpenFile openFile;
    private JComboBox<String> rowsComboBox;
    private JComboBox<String> columnCountComboBox;
    private PageInfoUpdater pageInfoUpdater;

    public TableResizer(OpenFile openFile, JComboBox<String> rowsComboBox, JComboBox<String> columnCountComboBox, PageInfoUpdater pageInfoUpdater) {
        this.openFile = openFile;
        this.rowsComboBox = rowsComboBox;
        this.columnCountComboBox = columnCountComboBox;
        this.pageInfoUpdater = pageInfoUpdater;

        // Add action listeners to combo boxes
        rowsComboBox.addActionListener(new ResizeAction());
        columnCountComboBox.addActionListener(new ResizeAction());
    }

    private class ResizeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (openFile != null && openFile.getFile() != null) {
                // Reload the file with new row and column counts
                openFile.loadFile(openFile.getFile());
                pageInfoUpdater.update();
            }
        }
    }
}
