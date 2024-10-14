package HexEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;


public class HexEditorGUI extends JFrame {
    private JTextArea textArea;

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
//        textArea.setWrapStyleWord(true); // перенос по словам
        textArea.setFont(new Font("Arial", Font.PLAIN, 14)); // установка шрифта
        textArea.setEditable(true);







        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowerPanel.setPreferredSize(new Dimension(100, 100));
        JTextField colsField = new JTextField(5);
        colsField.setText("16");
        JButton resizeButton = new JButton("Confirm");
        JLabel colsLabel = new JLabel("Columns:");


        // create table model and table
        DefaultTableModel tableModel = new DefaultTableModel();
//        tableModel.addColumn("№");
//        tableModel.addColumn("Adress");
//        for (int i = 1; i <= Integer.parseInt(colsField.getText()); i++){
//            tableModel.addColumn(String.valueOf(i));
//        }

        CustomTable table = new CustomTable(tableModel);
        // запрет на перетаскивание столбцов
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        // установка режима выделения по ячейкам
//        table.setCellSelectionEnabled(true);
//        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);


        // create Scroll Pane and add table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // create Scroll Pane for textArea
        JScrollPane scrollPaneTextArea = new JScrollPane(textArea);

        scrollPaneTextArea.setPreferredSize(new Dimension(300, scrollPaneTextArea.getHeight()));



        // textAreaUpdater
        CellHighlighterTextAreaUpdater cellHighlighterTextAreaUpdater = new CellHighlighterTextAreaUpdater(textArea, table);


        // create lower panel

        lowerPanel.add(colsLabel);
        lowerPanel.add(colsField);
        lowerPanel.add(resizeButton);




        this.add(scrollPane, BorderLayout.CENTER);
        this.add(scrollPaneTextArea, BorderLayout.EAST);
        this.add(lowerPanel, BorderLayout.SOUTH);









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
