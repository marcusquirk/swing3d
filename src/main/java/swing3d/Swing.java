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

/**
 * A class JFrame that controls the JPanel SwingPanel, allowing the user to
 * change colours of a shape and its background, as well as changing the physics
 * acting on it such as the gravitational and damping constants. The user may also
 * choose between a circle and square and 'restart'its position and velocity. The
 * velocity is randomly generated when the shape is initialised and is re-
 * randomised at every restart.
 * 
 * I refactored the colour generation.
 * 
 * A potential improvement would be to refactor the creation of menus into a
 * method, but each menu is unique enough (number and kind of sub menus and names)
 * that I don't think such an effort is worth it yet. If I add more menus and a
 * pattern shows itself, I will reconsider.
 * 
 * @author marcus
 */

public class Swing extends JFrame implements ActionListener {

    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 600;
    private final String FRAME_TITLE = "Swing";
    private final int NUMBER_OF_COLOURS = 5;
    private final List<Color> palette = new ArrayList<>();
    private final List<Color> foregroundPalette = new ArrayList<>();
    private final SwingPanel panel;

    public Swing() {
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

        //A menu for choosing the shape
        String[] shapes = {"Circle", "Square"};
        JMenu shape = new JMenu("Shape");
        menuBar.add(shape);
        for (String i : shapes) {
            JMenuItem item = new JMenuItem(i);
            item.addActionListener(this);
            item.setActionCommand(i);
            shape.add(item);
        }
        
        //A menu for setting the gravitational constant g. 9.81 does not appear
        //to behave like a real object.
        Double[] phyOptions = {0.0,1.0,2.0,5.0,9.81,-2.0};
        JMenu physics = new JMenu("Physics");
        menuBar.add(physics);
        for (Double i  : phyOptions) {
            JMenuItem item = new JMenuItem("Gravity = " + i);
            item.addActionListener(this);
            item.setActionCommand("Gravity " + i);
            physics.add(item);
        }
        
        //A menu for setting the damping force d.
        Double[] dampOptions = {0.0,1.0,3.0,-1.0,-3.0};
        for (Double i  : dampOptions) {
            JMenuItem item = new JMenuItem("Damping = " + i);
            item.addActionListener(this);
            item.setActionCommand("Damping " + i);
            physics.add(item);
        }

        //A menu for resetting the shape. This is needed because g and d will
        //eventually bring the ball to rest at the bottom of the frame.
        JMenu restart = new JMenu("Restart");
        menuBar.add(restart);
        JMenuItem item = new JMenuItem("Restart");
        item.addActionListener(this);
        item.setActionCommand("Restart");
        restart.add(item);


        this.setVisible(true);
    } // Swing()
    
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
        else if (cmd == "Circle") {
            this.panel.setShape(cmd);
        } // if
        else if (cmd == "Square") {
            this.panel.setShape(cmd);
        } //if
        else if (cmd == "Restart"){
            this.panel.setCenterX(0.0);
            this.panel.setCenterY(-0.5);
            this.panel.setDirection();
        }
        else if (cmd.indexOf("Gravity") >= 0){
            String suffix = cmd.substring(7).trim();
            double index = Double.parseDouble(suffix);
            this.panel.setGravity(index);
        }
        else if (cmd.indexOf("Damping") >= 0){
            String suffix = cmd.substring(7).trim();
            double index = Double.parseDouble(suffix);
            this.panel.setDamping(index);
        }

    } // actionPerformed( ActionEvent )

    public static void main(String[] args) {
        Swing swing = new Swing();
    } // main( String [] )

} // Swing
