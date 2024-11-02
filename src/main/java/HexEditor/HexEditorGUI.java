package HexEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Set;


public class HexEditorGUI extends JFrame {
    private JTextArea textArea;
    private OpenFile openFile;
    private JComboBox<String> rowsComboBox;
    private JComboBox<String> colsComboBox;
    private JLabel pageInfoLabel;
    private JButton prevPageButton;
    private JButton nextPageButton;
    private JTextField currentPageField;
    private JButton goToPageButton;
    private PageInfoUpdater pageInfoUpdater;

    public HexEditorGUI() {
        this.setTitle("Hex editor by coolcupp");
        this.setSize(1500, 1000);
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

        // Create Rows and Columns JComboBoxes
        String[] values = new String[64];
        for (int i = 1; i <= 64; i++) {
            values[i - 1] = String.valueOf(i);
        }

        rowsComboBox = new JComboBox<>(values);
        colsComboBox = new JComboBox<>(values);
        rowsComboBox.setSelectedItem("16");
        colsComboBox.setSelectedItem("16");

        // Create buttons for Rows and Columns
        JButton increaseRowsButton = new JButton("+");
        JButton decreaseRowsButton = new JButton("-");
        JButton increaseColsButton = new JButton("+");
        JButton decreaseColsButton = new JButton("-");

        // Action listeners for the buttons
        increaseRowsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentValue = Integer.parseInt((String) rowsComboBox.getSelectedItem());
                if (currentValue < 64) {
                    rowsComboBox.setSelectedItem(String.valueOf(currentValue + 1));
                    pageInfoUpdater.update();
                }
            }
        });

        decreaseRowsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentValue = Integer.parseInt((String) rowsComboBox.getSelectedItem());
                if (currentValue > 1) {
                    rowsComboBox.setSelectedItem(String.valueOf(currentValue - 1));
                    pageInfoUpdater.update();
                }
            }
        });

        increaseColsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentValue = Integer.parseInt((String) colsComboBox.getSelectedItem());
                if (currentValue < 64) {
                    colsComboBox.setSelectedItem(String.valueOf(currentValue + 1));
                    pageInfoUpdater.update();
                }
            }
        });

        decreaseColsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentValue = Integer.parseInt((String) colsComboBox.getSelectedItem());
                if (currentValue > 1) {
                    colsComboBox.setSelectedItem(String.valueOf(currentValue - 1));
                    pageInfoUpdater.update();
                }
            }
        });

        // Create a panel for the combo boxes and buttons
        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        comboPanel.add(new JLabel("Rows:"));
        comboPanel.add(rowsComboBox);
        comboPanel.add(decreaseRowsButton);
        comboPanel.add(increaseRowsButton);
        comboPanel.add(new JLabel("Columns:"));
        comboPanel.add(colsComboBox);
        comboPanel.add(decreaseColsButton);
        comboPanel.add(increaseColsButton);

        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(comboPanel);

        this.setJMenuBar(menuBar);

        // Create a text pane for file view
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        // Create lower panel
        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowerPanel.setPreferredSize(new Dimension(100, 130));

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
        searchField.setForeground(Color.GRAY);

        // Adding FocusListener
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Поле для поиска")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Поле для поиска");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        JRadioButton exactMatchButton = new JRadioButton("Точный поиск");
        JRadioButton maskMatchButton = new JRadioButton("Поиск по маске");
        ButtonGroup group = new ButtonGroup();
        group.add(exactMatchButton);
        group.add(maskMatchButton);
        exactMatchButton.setSelected(true);
        JButton searchButton = new JButton("Поиск");

        // Make combobox
        JComboBox<String> modeComboBox = new JComboBox<>(new String[]{"Стандарт", "2 байта", "4 байта", "8 байт"});

        // Create JTextPane for selection view
        JTextArea selectionView = new JTextArea();
        JScrollPane scrollPaneSelectionView = new JScrollPane(selectionView);
        scrollPaneSelectionView.setPreferredSize(new Dimension(300, 120));
        SelectionInfo selectionInfo = new SelectionInfo(table, selectionView);


        // delete bytes button
        JButton deleteBytesButton = new JButton("Delete Bytes");
        lowerPanel.add(deleteBytesButton);
        JButton deleteBytesWithPaddingButton = new JButton("Delete with padding");
        lowerPanel.add(deleteBytesWithPaddingButton);



        // Инициализация ByteRemover
        ByteRemover byteRemover = new ByteRemover();
        ByteRemoverWithPadding byteRemoverWithPadding = new ByteRemoverWithPadding();
        // Добавление обработчика для кнопки удаления
//        deleteBytesButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int[] selectedRows = table.getSelectedRows();
//                if (selectedRows.length == 0) {
//                    JOptionPane.showMessageDialog(null, "Пожалуйста, выберите диапазон байтов для удаления.");
//                    return;
//                }
//
//                // Предполагаем, что удаляем байты с первой выделенной строки до последней
//                int startRow = selectedRows[0];
//                int endRow = selectedRows[selectedRows.length - 1];
//
//                try {
//                    byteRemover.removeBytes(openFile.getFile(), startRow, endRow, openFile.getPageSize(), openFile.getColumnCount());
//                    openFile.loadFile(openFile.getFile()); // Перезагрузить файл для обновления таблицы
//                    pageInfoUpdater.update(); // Обновить информацию о странице
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Ошибка при удалении байтов: " + ex.getMessage());
//                }
//            }
//        });
//        deleteBytesButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Set<Point> selectedCells = table.getSelectedCells();
//                if (selectedCells.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Пожалуйста, выберите байты для удаления.");
//                    return;
//                }
//
//                try {
//                    byteRemover.removeBytes(openFile.getFile(), selectedCells, openFile.getColumnCount());
//                    openFile.loadFile(openFile.getFile()); // Перезагрузить файл для обновления таблицы
//                    pageInfoUpdater.update(); // Обновить информацию о странице
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Ошибка при удалении байтов: " + ex.getMessage());
//                }
//            }
//        });
        deleteBytesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Point> selectedCells = table.getSelectedCells();
                if (selectedCells.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Пожалуйста, выберите байты для удаления.");
                    return;
                }

                try {
                    byteRemover.removeBytes(openFile.getFile(), selectedCells, openFile.getColumnCount(), openFile.getCurrentPage(), openFile.getPageSize());
                    openFile.loadFile(openFile.getFile()); // Перезагрузить файл для обновления таблицы
                    pageInfoUpdater.update(); // Обновить информацию о странице
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при удалении байтов: " + ex.getMessage());
                }
            }
        });
        deleteBytesWithPaddingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Point> selectedCells = table.getSelectedCells();
                if (selectedCells.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Пожалуйста, выберите байты для замены.");
                    return;
                }

                try {
                    ByteRemoverWithPadding byteRemoverWithPadding = new ByteRemoverWithPadding();
                    byteRemoverWithPadding.removeBytesWithPadding(openFile.getFile(), selectedCells, openFile.getColumnCount(), openFile.getCurrentPage(), openFile.getPageSize());
                    openFile.loadFile(openFile.getFile()); // Перезагрузить файл для обновления таблицы
                    pageInfoUpdater.update(); // Обновить информацию о странице
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при замене байтов: " + ex.getMessage());
                }
            }
        });





        // Add components to lowerPanel
        lowerPanel.add(searchField);
        lowerPanel.add(exactMatchButton);
        lowerPanel.add(maskMatchButton);
        lowerPanel.add(searchButton);
        lowerPanel.add(modeComboBox);
        lowerPanel.add(scrollPaneSelectionView);

        TableSelectionHandler tableSelectionHandler = new TableSelectionHandler(table, textArea);
        ByteSearch byteSearch = new ByteSearch(table, searchField, exactMatchButton, maskMatchButton, tableSelectionHandler);

        // Adding components to frame
        this.add(scrollPaneTable, BorderLayout.CENTER);
        this.add(scrollPaneTextPane, BorderLayout.EAST);
        this.add(lowerPanel, BorderLayout.SOUTH);

        // Create pagination controls
        prevPageButton = new JButton("Previous");
        nextPageButton = new JButton("Next");
        pageInfoLabel = new JLabel("Page: 1/1");

        // Create field for current page
        currentPageField = new JTextField(3);
        currentPageField.setHorizontalAlignment(JTextField.CENTER);
        currentPageField.setText("1");

        // Create Go button
        goToPageButton = new JButton("Go");

        prevPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (openFile != null && openFile.getCurrentPage() > 1) {
                    openFile.loadPage(openFile.getCurrentPage() - 1);
                    pageInfoUpdater.update();
                }
            }
        });

        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (openFile != null && openFile.getCurrentPage() < openFile.getTotalPages()) {
                    openFile.loadPage(openFile.getCurrentPage() + 1);
                    pageInfoUpdater.update();
                }
            }
        });

        goToPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int targetPage = Integer.parseInt(currentPageField.getText());
                    if (openFile != null && targetPage > 0 && targetPage <= openFile.getTotalPages()) {
                        openFile.loadPage(targetPage);
                        pageInfoUpdater.update();
                    } else {
                        JOptionPane.showMessageDialog(null, "Введите корректный номер страницы.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Некорректный номер страницы.");
                }
            }
        });

        lowerPanel.add(prevPageButton);
        lowerPanel.add(nextPageButton);
        lowerPanel.add(currentPageField);
        lowerPanel.add(goToPageButton);
        lowerPanel.add(pageInfoLabel);

        // Add searchButton listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byteSearch.performSearch();
            }
        });

        // Initialize file opener and viewer
        FileViewer fileViewer = new FileViewer(textArea);
        openFile = new OpenFile(table, rowsComboBox, colsComboBox, fileViewer);

        // Page info updater
        pageInfoUpdater = new PageInfoUpdater(pageInfoLabel, currentPageField, openFile);

        // Initialize tableResizer
        TableResizer tableResizer = new TableResizer(openFile, rowsComboBox, colsComboBox, pageInfoUpdater);

        // Add action listeners to file buttons
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    openFile.loadFile(selectedFile);
                    pageInfoUpdater.update(); // Update page info after loading file
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

        modeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = modeComboBox.getSelectedIndex();
                table.setSelectionMode(selectedIndex); // Установите режим выделения
                table.getSelectedCells().clear(); // Очистка предыдущего выделения
                table.repaint();
            }
        });

        this.setVisible(true);
    }
}
