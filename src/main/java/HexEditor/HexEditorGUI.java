////package HexEditor;
////
////import javax.swing.*;
////import javax.swing.table.DefaultTableModel;
////import javax.xml.soap.Text;
////import java.awt.event.*;
////import java.awt.*;
////import java.io.File;
////
////
////public class HexEditorGUI extends JFrame {
////    private JTextArea textArea;
////
////    public HexEditorGUI(){
////        this.setTitle("Hex editor by coolcupp");
////        this.setSize(1200, 800);
////        this.setLocationRelativeTo(null);
////        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////
////        // create menu bar
////        JMenuBar menuBar = new JMenuBar();
////        JMenu fileMenu = new JMenu("File");
////        JMenuItem openItem = new JMenuItem("Open");
////        JMenuItem saveItem = new JMenuItem("Save");
////        JMenuItem exitItem = new JMenuItem("Exit");
////        fileMenu.add(openItem);
////        fileMenu.add(saveItem);
////        fileMenu.addSeparator();
////        fileMenu.add(exitItem);
////        menuBar.add(fileMenu);
////        this.setJMenuBar(menuBar);
////
////
////        // create a text area for file view
////        textArea = new JTextArea();
////        textArea.setLineWrap(true); // включаем перенос строк
////        textArea.setFont(new Font("Arial", Font.PLAIN, 14)); // установка шрифта
////        textArea.setEditable(true);
////
////        // create lower panel
////        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
////        lowerPanel.setPreferredSize(new Dimension(100, 130));
////        JTextField colsField = new JTextField(5);
////        colsField.setText("16");
////        JButton resizeButton = new JButton("Confirm");
////        JLabel colsLabel = new JLabel("Columns:");
////
////        // create table model and table
////        DefaultTableModel tableModel = new DefaultTableModel();
////        CustomTable table = new CustomTable(tableModel);
////        // запрет на перетаскивание столбцов
////        table.getTableHeader().setReorderingAllowed(false);
////        // дает возможность добавления горизонтального ползунка
////        // table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
////        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
////        table.setColumnSelectionAllowed(true);
////        table.setRowSelectionAllowed(true);
////
////
////        // create Scroll Pane and add table to it
////        JScrollPane scrollPaneTable = new JScrollPane(table);
////
////        // create Scroll Pane for textArea
////        JScrollPane scrollPaneTextArea = new JScrollPane(textArea);
////        scrollPaneTextArea.setPreferredSize(new Dimension(300, scrollPaneTextArea.getHeight()));
////
////        // textAreaUpdater
////        // TODO
////
////
////
////        JTextField searchField = new JTextField("Поле для поиска", 20);
////        searchField.setForeground(Color.GRAY); // Установка серого цвета текста
////
////        // Добавление FocusListener
////        searchField.addFocusListener(new FocusListener() {
////            @Override
////            public void focusGained(FocusEvent e) {
////                if (searchField.getText().equals("Поле для поиска")) {
////                    searchField.setText("");
////                    searchField.setForeground(Color.BLACK); // Изменение цвета текста на черный
////                }
////            }
////
////            @Override
////            public void focusLost(FocusEvent e) {
////                if (searchField.getText().isEmpty()) {
////                    searchField.setText("Поле для поиска");
////                    searchField.setForeground(Color.GRAY); // Возвращение серого цвета текста
////                }
////            }
////        });
////
////        JRadioButton exactMatchButton = new JRadioButton("Точный поиск");
////        JRadioButton maskMatchButton = new JRadioButton("Поиск по маске");
////        ButtonGroup group = new ButtonGroup();
////        group.add(exactMatchButton);
////        group.add(maskMatchButton);
////        exactMatchButton.setSelected(true); // Выбор по умолчанию
////        JButton searchButton = new JButton("Поиск");
////
////
////        // make combobox
////        JComboBox<String> modeComboBox = new JComboBox<>(new String[]{"Стандарт", "2 байта", "4 байта", "8 байт"});
////        modeComboBox.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                table.setSelectionMode(modeComboBox.getSelectedIndex());
////            }
////        });
////
////        // Create JTextArea for selection view
////        JTextArea selectionView = new JTextArea();
////        JScrollPane scrollPaneSelectionView = new JScrollPane(selectionView);
////        scrollPaneSelectionView.setPreferredSize(new Dimension(300, 120)); // Set preferred size for the scroll pane
////        SelectionInfo selectionInfo = new SelectionInfo(table, selectionView);
////
////        // add components to lowerPanel
////        lowerPanel.add(colsLabel);
////        lowerPanel.add(colsField);
////        lowerPanel.add(resizeButton);
////        lowerPanel.add(searchField);
////        lowerPanel.add(exactMatchButton);
////        lowerPanel.add(maskMatchButton);
////        lowerPanel.add(searchButton);
////        lowerPanel.add(modeComboBox);
////        lowerPanel.add(scrollPaneSelectionView);
////
////
////
////
////
////
////        ByteSearch byteSearch =
////                new ByteSearch(table, searchField, exactMatchButton, maskMatchButton, textArea);
////
////        // adding components to frame
////        this.add(scrollPaneTable, BorderLayout.CENTER);
////        this.add(scrollPaneTextArea, BorderLayout.EAST);
////        this.add(lowerPanel, BorderLayout.SOUTH);
////
////        // add searchButton listener
////        searchButton.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                byteSearch.performSearch();
////            }
////        });
////        // add resize Action listener
////        resizeButton.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                TableResizer tableResizer = new TableResizer(table, colsField);
////                tableResizer.resizeTable();
////            }
////        });
////        // add action listeners to file buttons
////        openItem.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent actionEvent) {
////                JFileChooser fileChooser = new JFileChooser();
////                int returnValue = fileChooser.showOpenDialog(null);
////                if (returnValue == JFileChooser.APPROVE_OPTION){
////                    File selectedFile = fileChooser.getSelectedFile();
////                    FileViewer fileViewer = new FileViewer(textArea);
////                    OpenFile openFile = new OpenFile(table, colsField, fileViewer); // create an object open file
////                    openFile.loadFile(selectedFile);
////                }
////            }
////        });
////        // add save action Listener
////        saveItem.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent actionEvent) {
////                JFileChooser fileChooser = new JFileChooser();
////                int returnValue = fileChooser.showSaveDialog(null);
////                if (returnValue == JFileChooser.APPROVE_OPTION){
////                    // TODO
//////                    SaveFile saveFile = new SaveFile();
//////                    saveFile.saveToFile(tableModel);
//////                    File selectedFile = fileChooser.getSelectedFile();
//////                    SaveFile saveFile = new SaveFile(tableModel);
//////                    saveFile.saveToFile(selectedFile);
//////                    JOptionPane.showMessageDialog(null, "File was saved");
////
////                }
////            }
////        });
////        exitItem.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent actionEvent) {
////                System.exit(0);
////            }
////        });
////
////        this.setVisible(true);
////
////    }
////}
//
//package HexEditor;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.event.*;
//import java.awt.*;
//import java.io.File;
//
//public class HexEditorGUI extends JFrame {
//    private JTextPane textPane; // Заменяем JTextArea на JTextPane
//
//    public HexEditorGUI() {
//        this.setTitle("Hex editor by coolcupp");
//        this.setSize(1200, 800);
//        this.setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Create menu bar
//        JMenuBar menuBar = new JMenuBar();
//        JMenu fileMenu = new JMenu("File");
//        JMenuItem openItem = new JMenuItem("Open");
//        JMenuItem saveItem = new JMenuItem("Save");
//        JMenuItem exitItem = new JMenuItem("Exit");
//        fileMenu.add(openItem);
//        fileMenu.add(saveItem);
//        fileMenu.addSeparator();
//        fileMenu.add(exitItem);
//        menuBar.add(fileMenu);
//        this.setJMenuBar(menuBar);
//
//        // Create a text pane for file view
//        textPane = new JTextPane();
//        textPane.setFont(new Font("Arial", Font.PLAIN, 14)); // Установка шрифта
//        textPane.setEditable(true);
//
//        // Create lower panel
//        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        lowerPanel.setPreferredSize(new Dimension(100, 130));
//        JTextField colsField = new JTextField(5);
//        colsField.setText("16");
//        JButton resizeButton = new JButton("Confirm");
//        JLabel colsLabel = new JLabel("Columns:");
//
//        // Create table model and table
//        DefaultTableModel tableModel = new DefaultTableModel();
//        CustomTable table = new CustomTable(tableModel);
//        table.getTableHeader().setReorderingAllowed(false);
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        table.setColumnSelectionAllowed(true);
//        table.setRowSelectionAllowed(true);
//
//        // Create Scroll Pane and add table to it
//        JScrollPane scrollPaneTable = new JScrollPane(table);
//
//        // Create Scroll Pane for textPane
//        JScrollPane scrollPaneTextPane = new JScrollPane(textPane);
//        scrollPaneTextPane.setPreferredSize(new Dimension(300, scrollPaneTextPane.getHeight()));
//
//        // Create search field
//        JTextField searchField = new JTextField("Поле для поиска", 20);
//        searchField.setForeground(Color.GRAY); // Установка серого цвета текста
//
//        // Добавление FocusListener
//        searchField.addFocusListener(new FocusListener() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (searchField.getText().equals("Поле для поиска")) {
//                    searchField.setText("");
//                    searchField.setForeground(Color.BLACK); // Изменение цвета текста на черный
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (searchField.getText().isEmpty()) {
//                    searchField.setText("Поле для поиска");
//                    searchField.setForeground(Color.GRAY); // Возвращение серого цвета текста
//                }
//            }
//        });
//
//        JRadioButton exactMatchButton = new JRadioButton("Точный поиск");
//        JRadioButton maskMatchButton = new JRadioButton("Поиск по маске");
//        ButtonGroup group = new ButtonGroup();
//        group.add(exactMatchButton);
//        group.add(maskMatchButton);
//        exactMatchButton.setSelected(true); // Выбор по умолчанию
//        JButton searchButton = new JButton("Поиск");
//
//        // Make combobox
//        JComboBox<String> modeComboBox = new JComboBox<>(new String[]{"Стандарт", "2 байта", "4 байта", "8 байт"});
//        modeComboBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                table.setSelectionMode(modeComboBox.getSelectedIndex());
//            }
//        });
//
//        // Create JTextPane for selection view
//        JTextPane selectionView = new JTextPane();
//        JScrollPane scrollPaneSelectionView = new JScrollPane(selectionView);
//        scrollPaneSelectionView.setPreferredSize(new Dimension(300, 120)); // Устанавливаем предпочтительный размер для панели прокрутки
//        SelectionInfo selectionInfo = new SelectionInfo(table, selectionView);
//
//        // Add components to lowerPanel
//        lowerPanel.add(colsLabel);
//        lowerPanel.add(colsField);
//        lowerPanel.add(resizeButton);
//        lowerPanel.add(searchField);
//        lowerPanel.add(exactMatchButton);
//        lowerPanel.add(maskMatchButton);
//        lowerPanel.add(searchButton);
//        lowerPanel.add(modeComboBox);
//        lowerPanel.add(scrollPaneSelectionView);
//
//        ByteSearch byteSearch = new ByteSearch(table, searchField, exactMatchButton, maskMatchButton, textPane);
//
//        // Adding components to frame
//        this.add(scrollPaneTable, BorderLayout.CENTER);
//        this.add(scrollPaneTextPane, BorderLayout.EAST);
//        this.add(lowerPanel, BorderLayout.SOUTH);
//
//        // Add searchButton listener
//        searchButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                byteSearch.performSearch();
//            }
//        });
//        // Add resize Action listener
//        resizeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                TableResizer tableResizer = new TableResizer(table, colsField);
//                tableResizer.resizeTable();
//            }
//        });
//        // Add action listeners to file buttons
//        openItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                JFileChooser fileChooser = new JFileChooser();
//                int returnValue = fileChooser.showOpenDialog(null);
//                if (returnValue == JFileChooser.APPROVE_OPTION) {
//                    File selectedFile = fileChooser.getSelectedFile();
//                    FileViewer fileViewer = new FileViewer(textPane); // Используем JTextPane
//                    OpenFile openFile = new OpenFile(table, colsField, fileViewer); // Создаем объект для открытия файла
//                    openFile.loadFile(selectedFile);
//                }
//            }
//        });
//        // Add save action Listener
//        saveItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                JFileChooser fileChooser = new JFileChooser();
//                int returnValue = fileChooser.showSaveDialog(null);
//                if (returnValue == JFileChooser.APPROVE_OPTION) {
//                    // TODO: Реализуйте сохранение файла
//                }
//            }
//        });
//        exitItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                System.exit(0);
//            }
//        });
//
//        this.setVisible(true);
//    }
//}

package HexEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.*;
import java.io.File;

public class HexEditorGUI extends JFrame {
    private JTextArea textArea;

    public HexEditorGUI() {
        this.setTitle("Hex editor by coolcupp");
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create menu bar
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

        // Create a text pane for file view
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Установка шрифта
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        // Create lower panel
        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowerPanel.setPreferredSize(new Dimension(100, 130));
        JTextField colsField = new JTextField(5);
        colsField.setText("16");
        JButton resizeButton = new JButton("Confirm");
        JLabel colsLabel = new JLabel("Columns:");

        // Create table model and table
        DefaultTableModel tableModel = new DefaultTableModel();
        CustomTable table = new CustomTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);

        // Create Scroll Pane and add table to it
        JScrollPane scrollPaneTable = new JScrollPane(table);

        // Create Scroll Pane for textArea
        JScrollPane scrollPaneTextPane = new JScrollPane(textArea);
        scrollPaneTextPane.setPreferredSize(new Dimension(300, scrollPaneTextPane.getHeight()));

        // Create search field
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

        // Make combobox
        JComboBox<String> modeComboBox = new JComboBox<>(new String[]{"Стандарт", "2 байта", "4 байта", "8 байт"});
        modeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.setSelectionMode(modeComboBox.getSelectedIndex());
            }
        });

        // Create JTextPane for selection view
        JTextArea selectionView = new JTextArea();
        JScrollPane scrollPaneSelectionView = new JScrollPane(selectionView);
        scrollPaneSelectionView.setPreferredSize(new Dimension(300, 120)); // Устанавливаем предпочтительный размер для панели прокрутки
        SelectionInfo selectionInfo = new SelectionInfo(table, selectionView);

        // Add components to lowerPanel
        lowerPanel.add(colsLabel);
        lowerPanel.add(colsField);
        lowerPanel.add(resizeButton);
        lowerPanel.add(searchField);
        lowerPanel.add(exactMatchButton);
        lowerPanel.add(maskMatchButton);
        lowerPanel.add(searchButton);
        lowerPanel.add(modeComboBox);
        lowerPanel.add(scrollPaneSelectionView);

        ByteSearch byteSearch = new ByteSearch(table, searchField, exactMatchButton, maskMatchButton, textArea);
        TableSelectionHandler tableSelectionHandler = new TableSelectionHandler(table, textArea);

        // Adding components to frame
        this.add(scrollPaneTable, BorderLayout.CENTER);
        this.add(scrollPaneTextPane, BorderLayout.EAST);
        this.add(lowerPanel, BorderLayout.SOUTH);

        // Add searchButton listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byteSearch.performSearch();
            }
        });
        // Add resize Action listener
        resizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableResizer tableResizer = new TableResizer(table, colsField);
                tableResizer.resizeTable();
            }
        });
        // Add action listeners to file buttons
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    FileViewer fileViewer = new FileViewer(textArea); // Используем JTextPane
                    OpenFile openFile = new OpenFile(table, colsField, fileViewer); // Создаем объект для открытия файла
                    openFile.loadFile(selectedFile);
                }
            }
        });
        // Add save action Listener
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // TODO: Реализуйте сохранение файла
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
