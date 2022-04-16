import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class Score extends Rectangle{

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;

    public Score(int GAME_WIDTH, int GAME_HEIGHT) {
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }

    public void draw(Graphics G){
        G.setColor(Color.WHITE);
        G.setFont(new Font("Risotto Script", Font.PLAIN,60));
        G.drawLine(GAME_WIDTH / 2,0,GAME_WIDTH/2,GAME_HEIGHT);

    }
}
