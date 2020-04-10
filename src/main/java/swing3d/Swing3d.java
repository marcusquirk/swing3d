package swing3d;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Swing3d extends JFrame implements ActionListener {

    private final int FRAME_WIDTH = 300;
    private final int FRAME_HEIGHT = 300;
    private final String FRAME_TITLE = "Swing";
    private final int NUMBER_OF_COLOURS = 5;
    private final List<Color> palette = new ArrayList<>();
    private final List<Color> foregroundPalette = new ArrayList<>();
    private final SwingPanel panel;

    public Swing3d() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(FRAME_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = this.getContentPane();
        this.panel = new SwingPanel();
        pane.add(panel);

        Random rng = new Random();
        randomColours(rng, palette);
        this.panel.setBackground(palette.get(0));

        randomColours(rng, foregroundPalette);
        this.panel.setColour(foregroundPalette.get(0));

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu colourMenu = new JMenu("Colour");
        menuBar.add(colourMenu);

        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            String label = "Colour " + i;
            JMenuItem item = new JMenuItem(label);
            item.addActionListener(this);
            item.setActionCommand(label);
            colourMenu.add(item);
        } // for

        JMenu foregroundColour = new JMenu("Shape Colour");
        menuBar.add(foregroundColour);
        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            String label = "Shape Colour " + i;
            JMenuItem item = new JMenuItem(label);
            item.addActionListener(this);
            //The name of the command is different, so that we don't have
            //two commands with the word 'colour' in them.
            item.setActionCommand("Foreground " + i);
            foregroundColour.add(item);
        } // for

        
        this.setVisible(true);
    } // Swing3d()
    
    /**
     * Create a list of random colours.
     * @param rng The already-initialised random number generator.
     * @param palette The list of colours to be filled up.
     */
    public void randomColours(Random rng, List<Color> palette){
        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            int red = 64 + rng.nextInt(128);
            int green = 64 + rng.nextInt(128);
            int blue = 64 + rng.nextInt(128);
            Color colour = new Color(red, green, blue);
            palette.add(colour);
        } // for
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.indexOf("Colour") >= 0) {
            String suffix = cmd.substring(6).trim();
            int index = Integer.parseInt(suffix);
            this.panel.setBackground(palette.get(index));
        } // if
        else if (cmd.indexOf("Foreground") >= 0) {
            String suffix = cmd.substring(10).trim();
            int index = Integer.parseInt(suffix);
            this.panel.setColour(foregroundPalette.get(index));
        } // if
        

    } // actionPerformed( ActionEvent )

    public static void main(String[] args) {
        Swing3d swing = new Swing3d();
    } // main( String [] )

} // Swing3d
