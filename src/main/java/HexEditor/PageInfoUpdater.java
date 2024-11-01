package HexEditor;

import javax.swing.*;

public class PageInfoUpdater {
    private JLabel pageInfoLabel;
    private JTextField currentPageField;
    private OpenFile openFile;

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
