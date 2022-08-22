import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {

    public static GameFrame newFrame;
    public static JFrame startFrame = new JFrame();
    static JLabel scoreLabel = new JLabel();
    JPanel buttonsPanel = new JPanel();
    JPanel startPanel = new JPanel();
    JButton reset = new JButton();

    ImageIcon titleScreen = new ImageIcon("2048TitleScreen_ForGame.jpg");
    JLabel image = new JLabel(titleScreen);


    GameFrame() {
        newFrame = this;
        this.setTitle("__--===|2048|===--__");
        this.setPreferredSize(new Dimension(527, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(new Point(0, 0));
        this.setLayout(new BorderLayout());

        this.add(new GamePanel(), BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        this.setResizable(false);
        this.setVisible(true);

        this.pack();

        scoreLabel.setFont(new FontUIResource("DIALOG", 1, 20));
        scoreLabel.setForeground(new Color(85, 85, 85));
        scoreLabel.setBackground(Color.LIGHT_GRAY);
        scoreLabel.setOpaque(true);

        reset.setFont(new FontUIResource("DIALOG", 1, 20));
        reset.setText("=[New Game]=");
        reset.setForeground(new Color(85, 85, 85));
        reset.setBackground(Color.LIGHT_GRAY);
        reset.addActionListener(e -> {
            resetGame();
        });

        buttonsPanel.setLayout(new GridLayout());
        buttonsPanel.add(scoreLabel);
        buttonsPanel.add(reset);
    }


    public void startScreen() {
        startPanel.setBackground(Color.lightGray);
        startPanel.setVisible(true);
        startPanel.setOpaque(true);
        startPanel.add(image);

        startFrame.setBounds(0, 0, 575, 685);
        startFrame.setVisible(true);
        startFrame.add(startPanel);
        startFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {
                    System.out.println("Close Start Frame");
                    startFrame.dispose();
                }
            }
        });
        Main.firstRun = false;
    }


    public static String getScore(int score){
        scoreLabel.setText("  SCORE: "+score);
        return  ("  SCORE: "+score);
    }

    public void resetGame(){
        GamePanel.score = 0;
        System.out.println("RESET!");
        this.dispose();
        new GameFrame();
    }


}
