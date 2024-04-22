
import component.Circle;
import component.Components;
import listener.ComponentChangeListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.NumberFormat;

public class EditorWindow extends JFrame implements ComponentChangeListener {

    private final DrawingCanvas drawingCanvas;

    private ComponentTableModel componentTableModel;

    private final JTable tableComponents;

    private final ComponentList componentList;

    private final int TOOLBAR_WIDTH = 200;

    private final ProjectSaver projectSaver;

    private final JLabel editingTime;

    private int selectedComponentIndex;


    public EditorWindow(int w, int h) throws HeadlessException {
        setSize(w, h);
        setTitle("My Perfect Vector Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.drawingCanvas = new DrawingCanvas(w, h, this);
        this.componentList = ComponentList.getINSTANCE();
        this.projectSaver = new ProjectSaver(componentList);
        setVisible(true);
        setLayout(new BorderLayout());

        JPanel barPanel = new JPanel();
        barPanel.setLayout(new BoxLayout(barPanel, BoxLayout.Y_AXIS));
        add(barPanel, BorderLayout.PAGE_START);
        add(drawingCanvas, BorderLayout.CENTER);

        JMenuBar mainMenuBar = new JMenuBar();
        barPanel.add(mainMenuBar);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        barPanel.add(toolBar);


        JMenu fileMenu = new JMenu("File");
        mainMenuBar.add(fileMenu);

        JMenuItem openProject = new JMenuItem("Open project");
        openProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        openProject.addActionListener(click -> {

            JFileChooser openChooser = new JFileChooser();
            openChooser.setDialogTitle("Choose file to open");
            openChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON projects", "json");
            openChooser.setFileFilter(filter);

            int result = openChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedFilePath = openChooser.getSelectedFile().getAbsolutePath();
                System.out.println(selectedFilePath);
                projectSaver.loadProject(selectedFilePath);
                updateAll();
            }
        });

        JMenuItem saveProject = new JMenuItem("Save project");
        saveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveProject.addActionListener(click -> {
            JFileChooser saveChooser = new JFileChooser();
            saveChooser.setDialogTitle("Specify a project to save");
            int result = saveChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String saveLocation = saveChooser.getSelectedFile().getAbsolutePath();
                projectSaver.saveProject(saveLocation + ".json");
                System.out.println("Save: " + saveLocation + ".json");
            }

        });

        fileMenu.add(openProject);
        fileMenu.add(saveProject);

        JComboBox<String> componentsComboBox = new JComboBox<>();
        for (Components c : Components.values()) {
            componentsComboBox.addItem(c.getTitle());
        }

        JPanel panRgb = new JPanel(new FlowLayout());

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);

        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0);
        numberFormatter.setMaximum(255);

        JLabel labRed = new JLabel("R: ");
        JFormattedTextField txtRed = new JFormattedTextField(numberFormatter);
        txtRed.setText("0");
        txtRed.setColumns(3);
        panRgb.add(labRed);
        panRgb.add(txtRed);

        JLabel labGreen = new JLabel("G: ");
        JFormattedTextField txtGreen = new JFormattedTextField(numberFormatter);
        txtGreen.setText("0");
        txtGreen.setColumns(3);
        panRgb.add(labGreen);
        panRgb.add(txtGreen);

        JLabel labBlue = new JLabel("B: ");
        JFormattedTextField txtBlue = new JFormattedTextField(numberFormatter);
        txtBlue.setText("0");
        txtBlue.setColumns(3);

        panRgb.add(labBlue);
        panRgb.add(txtBlue);


        panRgb.setMaximumSize(new Dimension(TOOLBAR_WIDTH, 35));

        toolBar.add(componentsComboBox);
        toolBar.add(panRgb);

        componentTableModel = new ComponentTableModel(componentList);

        tableComponents = new JTable(componentTableModel);

        tableComponents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableComponents.setPreferredSize(new Dimension(200, 100));
        tableComponents.setPreferredScrollableViewportSize(tableComponents.getPreferredSize());

        tableComponents.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                drawingCanvas.setSelectedComponentIndex(tableComponents.getSelectedRow());
            }
        });

        JScrollPane scrollTable = new JScrollPane(tableComponents);

        scrollTable.setMaximumSize(new Dimension(TOOLBAR_WIDTH, 100));

        toolBar.add(scrollTable);


        componentsComboBox.addActionListener(e -> {
            // I fucking hate this code, it's not mine though and I won't be remaking it
            drawingCanvas.setComponentEnumSelectedIndex(componentsComboBox.getSelectedIndex());
        });

        editingTime = new JLabel("00:00:00");
        //add(editingTime, BorderLayout.PAGE_END);
    }

    @Override
    public void onComponentsChange() {
        tableComponents.repaint();
    }

    @Override
    public void updateTableRow() {
        tableComponents.changeSelection(componentTableModel.getRowCount() - 1, 0, true, false);
    }

    public void updateAll() {
        drawingCanvas.repaint();
        onComponentsChange();
        updateTableRow();
    }

    public void updateTime() {
        componentList.addEditTime();
        //10584
        Long editTime = componentList.getEditTime();
        int hours = (int) (editTime / 3600); //10584 / 3600 = 2,94 = 2
        int minutes = (int) (editTime / 60); //10584 / 60 = 176,4 = 176 - (hours * 60) = 56
        int seconds = (int) (editTime - (3600 * hours - 60 * minutes));

        editingTime.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }
}
