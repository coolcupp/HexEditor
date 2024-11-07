package HexEditor.gui;

import HexEditor.files.OpenFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableResizer {
    private final OpenFile openFile;
    private final PageInfoUpdater pageInfoUpdater;

    public TableResizer(OpenFile openFile, JComboBox<String> rowsComboBox, JComboBox<String> columnCountComboBox, PageInfoUpdater pageInfoUpdater) {
        this.openFile = openFile;
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
