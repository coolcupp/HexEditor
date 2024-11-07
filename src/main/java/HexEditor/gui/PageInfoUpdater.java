package HexEditor.gui;

import HexEditor.files.OpenFile;

import javax.swing.*;

public class PageInfoUpdater {
    private final JLabel pageInfoLabel;
    private final JTextField currentPageField;
    private final OpenFile openFile;

    public PageInfoUpdater(JLabel pageInfoLabel, JTextField currentPageField, OpenFile openFile) {
        this.pageInfoLabel = pageInfoLabel;
        this.currentPageField = currentPageField;
        this.openFile = openFile;
    }

    public void update() {
        if (openFile != null) {
            pageInfoLabel.setText("Page: " + openFile.getCurrentPage() + "/" + openFile.getTotalPages());
            currentPageField.setText(String.valueOf(openFile.getCurrentPage()));
        }
    }
}
