package one.ruhland.chocol8.swing;

import one.ruhland.chocol8.chip.Machine;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;

public class MainWindow extends JFrame {

    private static final FileNameExtensionFilter FILTER = new FileNameExtensionFilter("CHIP-8", "ch8");

    private final Machine machine;

    private String lastFolder;

    public MainWindow(final Machine machine) {
        this.machine = machine;

        if(!(machine.getGraphics() instanceof SwingGraphics)) {
            throw new IllegalStateException("Trying to initialize the Swing frontend with a graphics implementation " +
                    "of type " + machine.getGraphics().getClass().getCanonicalName() + "! " +
                    "This frontend only works with " + SwingGraphics.class.getCanonicalName() + "!");
        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chocol8 CHIP");
        setResizable(false);

        setupMenu();

        add(((SwingGraphics) machine.getGraphics()).getPanel());
        pack();
    }

    private void setupMenu() {
        var menuBar = new JMenuBar();

        var fileMenu = new JMenu("File");
        var optionsMenu = new JMenu("Options");

        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);

        // Setup file menu
        var openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(lastFolder);
            chooser.setFileFilter(FILTER);

            int ret = chooser.showOpenDialog(this);
            lastFolder = chooser.getCurrentDirectory().getAbsolutePath();

            if(ret == JFileChooser.APPROVE_OPTION) {
                try {
                    machine.loadProgram(chooser.getSelectedFile().getAbsolutePath());
                    machine.run();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        var closeItem = new JMenuItem("Close");
        closeItem.addActionListener(e -> {
            machine.reset();
        });

        var exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            setVisible(false);
            dispose();
            System.exit(0);
        });

        fileMenu.add(openItem);
        fileMenu.add(closeItem);
        fileMenu.add(exitItem);

        // Setup options menu
        var scaleMenu = new JMenu("Scale factor");

        for(int i = 1; i <= 16; i++) {
            final int factor = i;

            var item = new JMenuItem(factor + "X");
            item.addActionListener(e -> {
                ((SwingGraphics) machine.getGraphics()).getPanel().setScaleFactor(factor);
                pack();
            });

            scaleMenu.add(item);
        }

        optionsMenu.add(scaleMenu);

        setJMenuBar(menuBar);
    }
}