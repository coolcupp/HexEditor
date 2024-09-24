package HexEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;

public class HexEditorGUI extends JFrame {
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


        // create table model and table
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("№");
        tableModel.addColumn("Adress");
        for (int i = 1; i <= 16; i++){
            tableModel.addColumn(String.valueOf(i));
        }

        JTable table = new JTable(tableModel);
        // запрет на перетаскивание столбцов
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // установка режима выделения по ячейкам
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        // create a text area for file view
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true); // включаем перенос строк
//        textArea.setWrapStyleWord(true); // перенос по словам
        textArea.setFont(new Font("Arial", Font.PLAIN, 14)); // установка шрифта
        textArea.setEditable(true);


        // панель для таблицы и текстового поля
        JPanel tbltxtPanel = new JPanel(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;


        // добавление таблицы в панель
        gbc.gridx = 0; // столбец
        gbc.gridy = 0; // строка
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JScrollPane scrollPaneTable = new JScrollPane(table);
        tbltxtPanel.add(scrollPaneTable, gbc);

        // Добавление текстового поля в панель
        gbc.gridy = 1; // Перемещение на следующую строку
        gbc.weighty = 0; // Нет дополнительного веса
        JScrollPane scrollPaneTextArea = new JScrollPane(textArea);
        tbltxtPanel.add(scrollPaneTextArea, gbc);

        // Добавление панели в главное окно
        this.add(tbltxtPanel, BorderLayout.CENTER);




//        // create Scroll Pane and add table to it
//        JScrollPane scrollPane = new JScrollPane(table);
//        this.add(scrollPane, BorderLayout.CENTER);
//
//
//        // create Scroll Pane for textArea
//        JScrollPane scrollPaneTextArea = new JScrollPane(textArea);
//        this.add(scrollPaneTextArea, BorderLayout.EAST);
//        scrollPaneTextArea.setPreferredSize(new Dimension(300, scrollPaneTextArea.getHeight()));

        new TextAreaUpdater(textArea, table);


        // add action listeners to file buttons
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    OpenFile openFile = new OpenFile(table); // create an object open file
                    openFile.loadFile(selectedFile);
                }
            }
        });
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
