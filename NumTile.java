import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.FontUIResource;

public class NumTile {
    private static Dimension tileSize;
    private static Color tileColour;
    private static int tileNum;


    public NumTile(Dimension tileSize, Color tileColour, int tileNum){
        this.tileColour = tileColour;
        this.tileSize = tileSize;
        this.tileNum = tileNum;
    }


    JButton makeTile(int tileNum) {
        JButton newTile = new JButton();
        newTile.setFont(new FontUIResource("DIALOG", 1, 20));
        newTile.setText(String.valueOf(tileNum));
        newTile.setPreferredSize(tileSize);
        newTile.setBackground(tileColour);
        newTile.setVisible(true);
        return newTile;
    }
}
