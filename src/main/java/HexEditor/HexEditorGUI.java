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
        table.getTableHeader().setReorderingAllowed(false); // запрет на перетаскивание столбцов
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // запрет на изменение размера столбцов
        // установка режима выделения по ячейкам
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        // add action listeners to file buttons
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    OpenFile openFile = new OpenFile(table); // create a object open file
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





        // create Scroll Pane and add table to it
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);



        this.setVisible(true);
    }
}
