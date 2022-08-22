import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener{
    public static String line = ("=-=-=-=-=-=-=-=-");
    public static boolean canUp = false, canDown = false, canLeft = false, canRight = false;
    static final int PANEL_WIDTH = 500, PANEL_HEIGHT = 500;
    static final int TILE_SIZE = 100;
    static final int DELAY = 100;
    static final Dimension TILE_DIMENSION = new Dimension(TILE_SIZE, TILE_SIZE);
    public static int[][] board;
    public static int score = 0;
    public boolean madeIt = false;

    JPanel buttonPanel = new JPanel();
    Timer timer = new Timer(DELAY, this);


    GamePanel(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.addKeyListener(new MyKeyListener());
        this.setBackground(Color.LIGHT_GRAY);
        this.setFocusable(true);
        this.setVisible(true);
        this.add(buttonPanel);
        this.timer.start();

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        buttonPanel.setLayout(new GridLayout(4,4,25,25));
        buttonPanel.setBackground(new Color(0,0,0,0));
        buttonPanel.setVisible(true);

        initNewBoard();
        upDateBoard(board);
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawBackground(g);
    }


    public void drawBackground(Graphics g){
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        for (int i = 0; i <= PANEL_WIDTH; i += PANEL_WIDTH / 4) {
            g.drawRect(i, 0, 10, PANEL_HEIGHT + 10);
            g.fillRect(i, 0, 10, PANEL_HEIGHT + 10);
            g.drawRect(0, i, PANEL_WIDTH, 10);
            g.fillRect(0, i, PANEL_WIDTH, 10);
        }
    }


    public static void initNewBoard(){
        board = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        generateRandTile(board);
        printBoard(board);
    }


    public static void generateRandTile(int[][] board){
        int rand1 = (int) (Math.random() * 4), rand2 = (int) (Math.random() * 4);
        if (board[rand1][rand2] == 0) {
            board[rand1][rand2] = 2;
        } else {
            generateRandTile(board);
        }
    }


    public static void printBoard(int[][] board){
        System.out.println(line.repeat(2));
        for (int i = 0; i < 4; i++) {
            for (int y = 0; y < 4; y++) {
                System.out.print(String.format("%6d", board[i][y]));
            }
            System.out.println("\n");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }


    public static void slideUp(int[][] board){
        if (checkUp(board)) {
            canUp = false;
            canDown = false;
            canLeft = false;
            canRight = false;
            moveUp(board);
            moveUp(board);
            combineUp(board);
            moveUp(board);
            generateRandTile(board);
            printBoard(board);
        } else if (!checkUp(board)) {
            System.out.println("Nope");
            canUp = true;
        }
    }


    public static boolean checkUp(int[][] board){
        boolean canCombine = false;
        boolean hasSpace = false;
        boolean canMove = false;

        for(int d = 1; d < 4; d++){
            for(int a = 0; a < 4; a++){
                if(board[d][a]!=0 && board[d-1][a] == 0){
                    hasSpace = true;
                }
            }
        }
        for (int d = 0; d < 3; d++) {
            for (int a = 0; a < 4; a++) {
                if (board[d][a] != 0 && board[d][a] == board[d + 1][a]) {
                    canCombine = true;
                }
            }
        }
        if (canCombine || hasSpace) {
            canMove = true;
        }
        return canMove;
    }


    public static void moveUp(int[][] board){
        for(int d = 1; d < 4; d++){
            for(int a = 0; a < 4; a++){
                if(board[d][a]!=0 && board[d-1][a] == 0){
                    board[d - 1][a] = board[d][a];
                    board[d][a] = 0;
                }
            }
        }
    }


    public static void combineUp(int[][] board){
        for (int d = 0; d < 3; d++) {
            for (int a = 0; a < 4; a++) {
                if (board[d][a] == board[d+1][a]) {
                    board[d][a] = 2 * board[d][a];
                    board[d + 1][a] = 0;
                    score += board[d][a];
                }
            }
        }
    }



    public static void slideDown(int[][] board) {
        if (checkDown(board)) {
            canUp = false;
            canDown = false;
            canLeft = false;
            canRight = false;
            moveDown(board);
            moveDown(board);
            combineDown(board);
            moveDown(board);
            generateRandTile(board);
            printBoard(board);
        } else if (!checkDown(board)) {
            System.out.println("Nope");
            canDown = true;
        }
    }


    public static boolean checkDown(int[][] board) {
        boolean canCombine = false;
        boolean hasSpace = false;
        boolean canMove = false;

        for(int d = 2; d >= 0; d--){
            for(int a = 0; a < 4; a++){
                if(board[d][a] != 0 && board[d+1][a] == 0){
                    hasSpace = true;
                }
            }
        }

        for(int d = 3; d > 0; d--){
            for(int a = 0; a<4; a++){
                if(board[d][a] != 0 && board[d-1][a] == board[d][a]){
                    canCombine = true;
                }
            }
        }

        if (canCombine || hasSpace) {
            canMove = true;
        }

        return canMove;
    }


    public static void moveDown(int[][] board) {
        for (int d = 2; d >= 0; d--) {
            for (int a = 0; a < 4; a++) {
                if (board[d][a] != 0 && board[d + 1][a] == 0) {
                    board[d + 1][a] = board[d][a];
                    board[d][a] = 0;
                }
            }
        }
    }


    public static void combineDown(int[][] board) {
        for (int d = 3; d > 0; d--) {
            for (int a = 0; a < 4; a++) {
                if (board[d][a] !=0 && board[d][a] == board[d - 1][a]) {
                    board[d][a] = 2 * board[d][a];
                    board[d - 1][a] = 0;
                    score += board[d][a];
                }
            }
        }
    }



    public static void slideLeft(int[][] board) {
        if (checkLeft(board)) {
            canUp = false;
            canDown = false;
            canLeft = false;
            canRight = false;
            moveLeft(board);
            moveLeft(board);
            combineLeft(board);
            moveLeft(board);
            generateRandTile(board);
            printBoard(board);
        } else if (!checkLeft(board)) {
            System.out.println("Nope");
            canLeft = true;
        }
    }


    public static boolean checkLeft(int[][] board) {
        boolean canCombine = false;
        boolean hasSpace = false;
        boolean canMove = false;

        for (int d = 0; d < 4; d++) {
            for (int a = 1; a < 4; a++) {
                if (board[d][a] != 0 && board[d][a - 1] == 0) {
                    hasSpace = true;
                }
            }
        }
        for (int d = 0; d < 4; d++) {
            for (int a = 0; a < 3; a++) {
                if (board[d][a] != 0 && board[d][a] == board[d][a+1]) {
                    canCombine = true;
                }
            }
        }

        if (canCombine || hasSpace) {
            canMove = true;
        }

        return canMove;
    }


    public static void moveLeft(int[][] board) {
        for (int d = 0; d < 4; d++) {
            for (int a = 1; a < 4; a++) {
                if (board[d][a] != 0 && board[d][a - 1] == 0) {
                    board[d][a - 1] = board[d][a];
                    board[d][a] = 0;
                }
            }
        }
    }


    public static void combineLeft(int[][] board) {
        for (int d = 0; d < 4; d++) {
            for (int a = 0; a < 3; a++) {
                if (board[d][a] != 0 && board[d][a] == board[d][a+1]) {
                    board[d][a] = 2 * board[d][a];
                    board[d][a+1] = 0;
                    score += board[d][a];
                }
            }
        }
    }



    public static void slideRight(int[][] board){
        if (checkRight(board)) {
            canUp = false;
            canDown = false;
            canLeft = false;
            canRight = false;
            moveRight(board);
            moveRight(board);
            combineRight(board);
            moveRight(board);
            generateRandTile(board);
            printBoard(board);
        } else if (!checkRight(board)) {
            System.out.println("Nope");
            canRight = true;
        }
    }


    public static boolean checkRight(int[][] board) {
        boolean canCombine = false;
        boolean hasSpace = false;
        boolean canMove = false;

        for (int d = 0; d < 4; d++) {
            for (int a = 0; a < 3; a++) {
                if (board[d][a] != 0 && board[d][a + 1] == 0) {
                    hasSpace = true;
                }
            }
        }
        for (int d = 0; d < 4; d++) {
            for (int a = 3; a > 0; a--) {
                if (board[d][a] != 0 && board[d][a] == board[d][a - 1]) {
                    canCombine = true;
                }
            }
        }

        if (canCombine || hasSpace) {
            canMove = true;
        }

        return canMove;
    }


    public static void moveRight(int[][] board) {
        for (int d = 0; d < 4; d++) {
            for (int a = 0; a < 3; a++) {
                if (board[d][a + 1] == 0) {
                    board[d][a + 1] = board[d][a];
                    board[d][a] = 0;
                }
            }
        }
    }


    public static void combineRight(int[][] board) {
        for (int d = 0; d < 4; d++) {
            for (int a = 3; a > 0; a--) {
                if (board[d][a] == board[d][a - 1]) {
                    board[d][a] = 2 * board[d][a];
                    board[d][a-1] = 0;
                    score += board[d][a];
                }
            }
        }
    }


    public void upDateBoard(int[][] board){
        Color tileCol = null;
        int tileNum = 0;
        buttonPanel.removeAll();
        GameFrame.getScore(score);
        for (int d = 0; d < 4; d++) {
            for (int a = 0; a < 4; a++) {
                if (board[d][a] != 0) {
                    switch (board[d][a]) {
                        case 2:
                            tileCol = new Color(143, 143, 143);
                            tileNum = 2;
                            break;
                        case 4:
                            tileCol = new Color(116, 116, 93);
                            tileNum = 4;
                            break;
                        case 8:
                            tileCol = new Color(91, 109, 82);
                            tileNum = 8;
                            break;
                        case 16:
                            tileCol = new Color(65,120,60);
                            tileNum = 16;
                            break;
                        case 32:
                            tileCol = new Color(66, 181, 61);
                            tileNum = 32;
                            break;
                        case 64:
                            tileCol = new Color(56, 216, 52);
                            tileNum = 64;
                            break;
                        case 128:
                            tileCol = new Color(60,255, 138);
                            tileNum = 128;
                            break;
                        case 256:
                            tileCol = new Color(65,200,150);
                            tileNum = 256;
                            break;
                        case 512:
                            tileCol = new Color(15, 181, 217);
                            tileNum = 512;
                            break;
                        case 1024:
                            tileCol = new Color(134, 87, 181);
                            tileNum = 1024;
                            break;
                        case 2048:
                            tileCol = new Color(184, 79, 226);
                            tileNum = 2048;
                            break;
                        default:
                            tileCol = new Color(137, 4, 18);
                            tileNum = board[d][a];
                            break;
                    }

                    NumTile newTile = new NumTile(TILE_DIMENSION, tileCol, tileNum);
                    buttonPanel.remove(buttonPanel);
                    buttonPanel.add(newTile.makeTile(tileNum));

                }else if(board[d][a]==0){
                    NumTile newTile = new NumTile(TILE_DIMENSION, tileCol, tileNum);
                    buttonPanel.add(newTile.makeTile(tileNum)).setVisible(false);
                }
                if(board[d][a] == 2048 && !madeIt){
                    youMadeIt();
                    madeIt = true;
                }
            }
        }
        if(canUp&&canDown&&canLeft&&canRight){
            canUp = false;
            canDown = false;
            canLeft = false;
            canRight = false;
            gameOver();
        }
    }


    public void youMadeIt(){
        JFrame winFrame = new JFrame();
        JPanel winPanel = new JPanel();
        JLabel winLabel = new JLabel();
        JButton keepPlaying = new JButton();
        JButton playAgain = new JButton();
        JLabel finalScore = new JLabel();

        winFrame.setBounds(65,200,400,145);
        winFrame.setTitle("YOU WIN!");
        winFrame.add(winPanel);
        winFrame.setVisible(true);

        winPanel.setLayout(new GridLayout(4,1));
        winPanel.add(winLabel);
        winPanel.add(playAgain);
        winPanel.add(keepPlaying);
        winPanel.add(finalScore);

        winLabel.setText("---++===CONGRATULATIONS! YOU MADE IT!===++---");
        winLabel.setHorizontalAlignment(JLabel.CENTER);
        winLabel.setVisible(true);

        playAgain.setText("[Play Again]");
        playAgain.setBackground(Color.LIGHT_GRAY);

        keepPlaying.setText("[Keep Playing]");
        keepPlaying.setBackground(Color.LIGHT_GRAY);

        winFrame.add(winPanel);
        winPanel.add(playAgain);
        winPanel.add(keepPlaying);
        winPanel.add(finalScore);

        playAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                winFrame.dispose();
                GameFrame.newFrame.resetGame();
            }
        });
        keepPlaying.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                winFrame.dispose();
            }
        });
    }

    public void gameOver(){
        JFrame loseFrame = new JFrame();
        JPanel losePanel = new JPanel();
        JButton tryAgain = new JButton();
        JLabel gameOver = new JLabel();

        loseFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        loseFrame.setPreferredSize(new Dimension(500,500));
        loseFrame.setBounds(165,245,200, 100);
        loseFrame.addWindowListener(new MyWindowEventHandler());
        loseFrame.setVisible(true);
        loseFrame.add(losePanel);

        losePanel.setPreferredSize(new Dimension(500,500));
        losePanel.setBackground(new Color(50,50,50,50));
        losePanel.setLayout(new GridLayout(2,1));
        losePanel.setVisible(true);
        losePanel.add(tryAgain);
        losePanel.add(gameOver);

        gameOver.setText("Game Over!");
        gameOver.setHorizontalAlignment(JLabel.CENTER);

        tryAgain.setText("[Try Again]");
        tryAgain.setBackground(Color.LIGHT_GRAY);
        tryAgain.setVisible(true);
        tryAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loseFrame.dispose();
                GameFrame.newFrame.resetGame();
            }
        });
    }


    public class MyKeyListener implements KeyListener {

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    slideUp(board);
                    upDateBoard(board);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    slideDown(board);
                    upDateBoard(board);
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    slideRight(board);
                    upDateBoard(board);
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    slideLeft(board);
                    upDateBoard(board);
                    break;
            }
            revalidate();
        }
        @Override
        public void keyPressed(KeyEvent e) {
        }
        @Override
        public void keyTyped(KeyEvent e) {
        }
    }


    public class MyWindowEventHandler extends WindowAdapter{
        public void windowClosing(WindowEvent evt) {
            GameFrame.newFrame.resetGame();
        }
    }
}


