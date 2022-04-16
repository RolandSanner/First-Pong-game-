import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class GamePanel extends JPanel implements Runnable{
    // making it static, so they all share the same game width
    //  Final makes it permanent
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH *(0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    PongPaddles paddle1;
    PongPaddles paddle2;
    PongBall ball;
    Score score;



    //Constructor
    public GamePanel() {
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        // takes in key inputs for focusable
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();

    }


    //METHODS
    public void newBall(){
        // starts ball randomly on the y axis
        random = new Random();
        //passing in the coordinates to where the ball will start
        ball = new PongBall((GAME_WIDTH/2)-(BALL_DIAMETER/2), random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);

    }

    public void newPaddles(){
        //setting paddle location for P1 to the very far left
        paddle1 = new PongPaddles(0,(GAME_HEIGHT/2) -(PADDLE_HEIGHT /2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        // paddle 2 location
        paddle2 = new PongPaddles(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2) -(PADDLE_HEIGHT /2),PADDLE_WIDTH,PADDLE_HEIGHT,2);

    }

    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);

    }
    // calls the paddles from their classes
    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        Toolkit.getDefaultToolkit().sync();


    }
    public void move(){
        // calls the move method from each class and makes the move smoother and less clunky
        paddle1.move();
        paddle2.move();
        ball.move();
    }
    public void checkCollision(){
        // this code will bounce balls off panels
        if(ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // optional to add more speed per bounce off panel
            if (ball.yVelocity > 0 )
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if(ball.intersects(paddle2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // optional to add more speed per bounce off panel
            if (ball.yVelocity > 0 )
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }




        // this will bounce the ball off window edges
        if(ball.y <= 0){
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= GAME_HEIGHT-BALL_DIAMETER){
            ball.setYDirection(-ball.yVelocity);
        }



        // Prevnts paddles from going past window edges
        if(paddle1.y<0)
            paddle1.y=0;
        if(paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;

        if(paddle2.y<0)
            paddle2.y=0;
        if(paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;

        //Gives 1 point if paddle scores and creates new paddles and ball

        if(ball.x <=0) {
            score.player2++;
            newPaddles();
            newBall();
            System.out.println("Player 2: "+score.player2);
        }
        if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("Player 1: "+score.player1);
        }


    }
    public void run(){
        //Creating a basic game loop
        long lastTime = System.nanoTime();
        double amountOfTics = 60.0;
        double nanoSecs = 1000000000 / amountOfTics;
        double delta = 0;

        while (true){
            long now = System.nanoTime();
            delta += (now - lastTime) /nanoSecs;
            lastTime = now;
            if(delta >= 1){
                move();
                checkCollision();
                repaint();
                delta--;

            }
        }

    }
        // AL is short for Action Listener
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);

        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }



}
