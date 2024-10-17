package HexEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;


public class HexEditorGUI extends JFrame {
    private JTextArea textArea;
    private CellHighlighterTextAreaUpdater cellHighlighterTextAreaUpdater;

    public HexEditorGUI(){
        this.setTitle("Hex editor by coolcupp");
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);


        // create a text area for file view
        textArea = new JTextArea();
        textArea.setLineWrap(true); // включаем перенос строк
        textArea.setFont(new Font("Arial", Font.PLAIN, 14)); // установка шрифта
        textArea.setEditable(true);

        // create lower panel
        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowerPanel.setPreferredSize(new Dimension(100, 100));
        JTextField colsField = new JTextField(5);
        colsField.setText("16");
        JButton resizeButton = new JButton("Confirm");
        JLabel colsLabel = new JLabel("Columns:");

        // create table model and table
        DefaultTableModel tableModel = new DefaultTableModel();
        CustomTable table = new CustomTable(tableModel);
        // запрет на перетаскивание столбцов
        table.getTableHeader().setReorderingAllowed(false);
        // дает возможность добавления горизонтального ползунка
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);


        // create Scroll Pane and add table to it
        JScrollPane scrollPaneTable = new JScrollPane(table);

        // create Scroll Pane for textArea
        JScrollPane scrollPaneTextArea = new JScrollPane(textArea);
        scrollPaneTextArea.setPreferredSize(new Dimension(300, scrollPaneTextArea.getHeight()));

        // textAreaUpdater
        cellHighlighterTextAreaUpdater = new CellHighlighterTextAreaUpdater(textArea, table);


        JTextField searchField = new JTextField("Поле для поиска", 20);
        searchField.setForeground(Color.GRAY); // Установка серого цвета текста

        // Добавление FocusListener
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Поле для поиска")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK); // Изменение цвета текста на черный
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Поле для поиска");
                    searchField.setForeground(Color.GRAY); // Возвращение серого цвета текста
                }
            }
        });

        JRadioButton exactMatchButton = new JRadioButton("Точный поиск");
        JRadioButton maskMatchButton = new JRadioButton("Поиск по маске");
        ButtonGroup group = new ButtonGroup();
        group.add(exactMatchButton);
        group.add(maskMatchButton);
        exactMatchButton.setSelected(true); // Выбор по умолчанию
        JButton searchButton = new JButton("Поиск");

        // add components to lowerPanel
        lowerPanel.add(colsLabel);
        lowerPanel.add(colsField);
        lowerPanel.add(resizeButton);
        lowerPanel.add(searchField);
        lowerPanel.add(exactMatchButton);
        lowerPanel.add(maskMatchButton);
        lowerPanel.add(searchButton);





        ByteSearch byteSearch =
                new ByteSearch(table, searchField, exactMatchButton, maskMatchButton, textArea);

        // adding components to frame
        this.add(scrollPaneTable, BorderLayout.CENTER);
        this.add(scrollPaneTextArea, BorderLayout.EAST);
        this.add(lowerPanel, BorderLayout.SOUTH);

        // add searchButton listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byteSearch.performSearch();
            }
        });
        // add resize Action listener
        resizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableResizer tableResizer = new TableResizer(table, colsField);
                tableResizer.resizeTable();
            }
        });
        // add action listeners to file buttons
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    OpenFile openFile = new OpenFile(table, colsField); // create an object open file
                    openFile.loadFile(selectedFile);
                }
            }
        });
        // add save action Listener
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION){
                    // TODO
//                    SaveFile saveFile = new SaveFile();
//                    saveFile.saveToFile(tableModel);
//                    File selectedFile = fileChooser.getSelectedFile();
//                    SaveFile saveFile = new SaveFile(tableModel);
//                    saveFile.saveToFile(selectedFile);
//                    JOptionPane.showMessageDialog(null, "File was saved");

                }
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        this.setVisible(true);

    }
}
