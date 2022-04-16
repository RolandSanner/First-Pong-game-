import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class GameFrame extends JFrame{

    GamePanel panel = new GamePanel();

    public GameFrame() {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.DARK_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        //causes window to fit around the gamepanel
        this.setVisible(true);
        // makes the location open in the middle of the screen
        this.setLocationRelativeTo(null);

    }




}
