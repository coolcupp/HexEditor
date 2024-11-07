package HexEditor.gui;

import HexEditor.table.CustomTable;
import HexEditor.files.FileViewer;
import HexEditor.files.OpenFile;
import HexEditor.bytes.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;


public class HexEditorGUI extends JFrame {
    private final OpenFile openFile;
    private final JComboBox<String> rowsComboBox;
    private final JComboBox<String> colsComboBox;
    private final JTextField currentPageField;
    private final PageInfoUpdater pageInfoUpdater;

    public HexEditorGUI() {
        this.setTitle("Hex editor by coolcupp");
        this.setSize(1500, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // создание menubar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);


        // создание combobox для кол-ва строк и столбцов
        String[] values = new String[64];
        for (int i = 1; i <= 64; i++) {
            values[i - 1] = String.valueOf(i);
        }
        rowsComboBox = new JComboBox<>(values);
        colsComboBox = new JComboBox<>(values);
        rowsComboBox.setSelectedItem("16");
        colsComboBox.setSelectedItem("16");

        // создание "+" и "-" кнопок для изменения числа строк и столбцов
        JButton increaseRowsButton = new JButton("+");
        JButton decreaseRowsButton = new JButton("-");
        JButton increaseColsButton = new JButton("+");
        JButton decreaseColsButton = new JButton("-");

        // добавление обработчиков событий к кнопкам
        increaseRowsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentValue = Integer.parseInt((String) Objects.requireNonNull(rowsComboBox.getSelectedItem()));
                if (currentValue < 64) {
                    rowsComboBox.setSelectedItem(String.valueOf(currentValue + 1));
                    pageInfoUpdater.update();
                }
            }
        });
        decreaseRowsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentValue = Integer.parseInt((String) Objects.requireNonNull(rowsComboBox.getSelectedItem()));
                if (currentValue > 1) {
                    rowsComboBox.setSelectedItem(String.valueOf(currentValue - 1));
                    pageInfoUpdater.update();
                }
            }
        });
        increaseColsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentValue = Integer.parseInt((String) Objects.requireNonNull(colsComboBox.getSelectedItem()));
                if (currentValue < 64) {
                    colsComboBox.setSelectedItem(String.valueOf(currentValue + 1));
                    pageInfoUpdater.update();
                }
            }
        });
        decreaseColsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentValue = Integer.parseInt((String) Objects.requireNonNull(colsComboBox.getSelectedItem()));
                if (currentValue > 1) {
                    colsComboBox.setSelectedItem(String.valueOf(currentValue - 1));
                    pageInfoUpdater.update();
                }
            }
        });

        // создание панели для функционала ресайза таблицы
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

        // создание текстовой области для файлового отображения
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        // создание нижней панели
        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowerPanel.setPreferredSize(new Dimension(100, 175));

        // создание таблицы и модели таблицы
        DefaultTableModel tableModel = new DefaultTableModel();
        CustomTable table = new CustomTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);

        // создание scrollpane для таблицы и отображения данных
        JScrollPane scrollPaneTable = new JScrollPane(table);
        JScrollPane scrollPaneTextPane = new JScrollPane(textArea);
        scrollPaneTextPane.setPreferredSize(new Dimension(300, scrollPaneTextPane.getHeight()));

        // создание поля поиска
        JTextField searchField = new JTextField("Поле для поиска", 20);
        searchField.setForeground(Color.GRAY);

        // обработка поля поиска
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

        // создание кнопок для поиска
        JRadioButton exactMatchButton = new JRadioButton("Точный поиск");
        JRadioButton maskMatchButton = new JRadioButton("Поиск по маске");
        ButtonGroup group = new ButtonGroup();
        group.add(exactMatchButton);
        group.add(maskMatchButton);
        exactMatchButton.setSelected(true);
        JButton searchButton = new JButton("Поиск");

        // создание combobox для выбора режима выделения
        JComboBox<String> modeComboBox = new JComboBox<>(new String[]{"Стандарт", "2 байта", "4 байта", "8 байт"});

        // создание панели для отображения информации о диапазоне байт
        JTextArea selectionView = new JTextArea();
        JScrollPane scrollPaneSelectionView = new JScrollPane(selectionView);
        scrollPaneSelectionView.setPreferredSize(new Dimension(300, 120));
        new SelectionInfo(table, selectionView);


        // создание и добавление кнопок для операций над байтами
        JButton deleteBytesButton = new JButton("Delete Bytes");
        lowerPanel.add(deleteBytesButton);
        JButton deleteBytesWithPaddingButton = new JButton("Delete with padding");
        lowerPanel.add(deleteBytesWithPaddingButton);
        JButton cutButton = new JButton("Cut Bytes");
        lowerPanel.add(cutButton);
        JButton cutWithPaddingButton = new JButton("Cut with padding");
        lowerPanel.add(cutWithPaddingButton);
        JButton copyButton = new JButton("Copy Bytes");
        lowerPanel.add(copyButton);
        JButton insertButton = new JButton("Insert Bytes");
        lowerPanel.add(insertButton);
        JButton replaceButton = new JButton("Replace Button");
        lowerPanel.add(replaceButton);


        // инициализация буфера
        ByteBuffer byteBuffer = new ByteBuffer();

        // инициализация объектов для работы с байтами
        ByteRemoverWithShift byteRemoverWithShift = new ByteRemoverWithShift();
        ByteCutterWithShift byteCutterWithShift = new ByteCutterWithShift(byteBuffer);
        ByteCutterWithZeros byteCutterWithZeros = new ByteCutterWithZeros(byteBuffer);
        ByteCopier byteCopier = new ByteCopier(byteBuffer);
        ByteInserterWithChanging byteInserterWithChanging = new ByteInserterWithChanging(byteBuffer);


        // обработка кнопок для операций над байтами
        deleteBytesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Point> selectedCells = table.getSelectedCells();
                if (selectedCells.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Пожалуйста, выберите байты для удаления.");
                    return;
                }

                try {
                    byteRemoverWithShift.removeBytes(openFile.getFile(), selectedCells, openFile.getColumnCount(), openFile.getCurrentPage(), openFile.getPageSize());
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
                    ByteRemoverWithZeros byteRemoverWithZeros = new ByteRemoverWithZeros();
                    byteRemoverWithZeros.removeBytesWithPadding(openFile.getFile(), selectedCells, openFile.getColumnCount(), openFile.getCurrentPage(), openFile.getPageSize());
                    openFile.loadFile(openFile.getFile()); // Перезагрузить файл для обновления таблицы
                    pageInfoUpdater.update(); // Обновить информацию о странице
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при замене байтов: " + ex.getMessage());
                }
            }
        });
        cutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Point> selectedCells = table.getSelectedCells();
                try {
                    byteCutterWithShift.cutBytes(
                            openFile.getFile(),
                            selectedCells,
                            openFile.getColumnCount(),
                            openFile.getCurrentPage(),
                            openFile.getPageSize());
                    openFile.loadFile(openFile.getFile());
                    pageInfoUpdater.update();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cutWithPaddingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Point> selectedCells = table.getSelectedCells();
                try {
                    byteCutterWithZeros.cutBytesWithPadding(
                            openFile.getFile(),
                            selectedCells,
                            openFile.getColumnCount(),
                            openFile.getCurrentPage(),
                            openFile.getPageSize());
                    openFile.loadFile(openFile.getFile());
                    pageInfoUpdater.update();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Point> selectedCells = table.getSelectedCells();
                try {
                    byteCopier.copyBytes(
                            openFile.getFile(),
                            selectedCells,
                            openFile.getColumnCount(),
                            openFile.getCurrentPage(),
                            openFile.getPageSize());
                    openFile.loadFile(openFile.getFile());
                    pageInfoUpdater.update();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Point> selectedCells = table.getSelectedCells();
                if (selectedCells.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Пожалуйста, выберите ячейку для вставки.");
                    return;
                }
                try {
                    // убеждаемся что есть byteBuffer
                    ByteInserterWithShift byteInserterWithShift = new ByteInserterWithShift(byteBuffer);
                    byteInserterWithShift.insertBytes(
                            openFile.getFile(),
                            selectedCells,
                            openFile.getColumnCount(),
                            openFile.getCurrentPage(),
                            openFile.getPageSize());
                    openFile.loadFile(openFile.getFile()); // Перезагрузить файл для обновления таблицы
                    pageInfoUpdater.update(); // Обновить информацию о странице
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при вставке байтов: " + ex.getMessage());
                }
            }
        });
        replaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Point> selectedCells = table.getSelectedCells();
                try {
                    byteInserterWithChanging.replaceBytes(
                            openFile.getFile(),
                            selectedCells,
                            openFile.getColumnCount(),
                            openFile.getCurrentPage(),
                            openFile.getPageSize());
                    openFile.loadFile(openFile.getFile()); // Перезагрузить файл для обновления таблицы
                    pageInfoUpdater.update(); // Обновить информацию о странице
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при замене байтов: " + ex.getMessage());
                }
            }
        });


        // добавление слушателя на таблицу для реализации ручного редактирования ячейки пользователем
        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // Проверяем, что изменение произошло в ячейке
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == -1 || row == -1) {
                    return; // Игнорируем некорректные события
                }

                // Получаем новое значение из таблицы
                String newValue = (String) table.getValueAt(row, column);

                // Проверяем, что введенное значение корректно
                if (!ByteManualEditor.isValidByte(newValue)) { // Теперь вызываем через имя класса
                    JOptionPane.showMessageDialog(null, "Ошибка: введено невалидное значение.");
                    return;
                }
                try {
                    // Преобразуем строку в байт (в формате Hex)
                    byte newByteValue = (byte) Integer.parseInt(newValue, 16);

                    // Рассчитываем страницу и размер
                    Set<Point> selectedCells = table.getSelectedCells(); // Например, берем первую выделенную ячейку
                    Point selectedCell = selectedCells.iterator().next(); // Например, берем первую ячейку
                    int columnCount = openFile.getColumnCount();
                    int currentPage = openFile.getCurrentPage();
                    int pageSize = openFile.getPageSize();

                    // Проверяем, что файл загружен
                    if (openFile.getFile() == null) {
                        throw new IOException("Файл не загружен.");
                    }

                    // Обновляем байт в файле
                    ByteManualEditor.updateByteInFile(openFile.getFile(), columnCount, currentPage, pageSize, selectedCell, newByteValue);

                    // Перезагружаем файл и обновляем интерфейс
                    openFile.loadFile(openFile.getFile()); // Перезагрузка файла
                    pageInfoUpdater.update(); // Обновление информации о странице

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при обновлении файла: " + ex.getMessage());
                }
            }
        });


        // добавление компонентов в нижнюю панель
        lowerPanel.add(searchField);
        lowerPanel.add(exactMatchButton);
        lowerPanel.add(maskMatchButton);
        lowerPanel.add(searchButton);
        lowerPanel.add(modeComboBox);
        lowerPanel.add(scrollPaneSelectionView);


        // обработчик выделения в таблице
        TableSelectionHandler tableSelectionHandler = new TableSelectionHandler(table, textArea);
        ByteSearch byteSearch = new ByteSearch(table, searchField, exactMatchButton, maskMatchButton, tableSelectionHandler);

        // добавление компонентов в frame
        this.add(scrollPaneTable, BorderLayout.CENTER);
        this.add(scrollPaneTextPane, BorderLayout.EAST);
        this.add(lowerPanel, BorderLayout.SOUTH);

        // создание кнопок навигации по страницам
        JButton prevPageButton = new JButton("Previous");
        JButton nextPageButton = new JButton("Next");
        JLabel pageInfoLabel = new JLabel("Page: 1/1");

        // создание поля для отображения текущего номера страницы
        currentPageField = new JTextField(3);
        currentPageField.setHorizontalAlignment(JTextField.CENTER);
        currentPageField.setText("1");

        // создание кнопки перехода на страницу
        JButton goToPageButton = new JButton("Go");


        // обработка кнопок навигации
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
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byteSearch.performSearch();
            }
        });


        // добавление компонентов в нижнюю панель
        lowerPanel.add(prevPageButton);
        lowerPanel.add(nextPageButton);
        lowerPanel.add(currentPageField);
        lowerPanel.add(goToPageButton);
        lowerPanel.add(pageInfoLabel);


        // инициализация объекта для открытия и отображения файла
        FileViewer fileViewer = new FileViewer(textArea);
        openFile = new OpenFile(table, rowsComboBox, colsComboBox, fileViewer);
        pageInfoUpdater = new PageInfoUpdater(pageInfoLabel, currentPageField, openFile);
        new TableResizer(openFile, rowsComboBox, colsComboBox, pageInfoUpdater);


        // добавление различных слушателей на кнопки
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
