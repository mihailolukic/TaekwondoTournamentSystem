package app.ui.screens.components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Westi on 1/15/2017.
 */
public class ScoringPanel extends JPanel {

    Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 260);
    Font fontTV = new Font(Font.SANS_SERIF, Font.PLAIN, 320);

    JPanel redPanel;
    JPanel bluePanel;
    JLabel redScore;
    JLabel blueScore;

    public ScoringPanel(int panelWidth, int panelHeight, boolean inverse) {
        super();
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setLayout(new GridLayout(0,2));
        initGui(inverse);

    }

    private void initGui(boolean inverse) {
        redPanel = new JPanel(new GridBagLayout());
        redPanel.setBackground(Color.RED);
        redScore = new JLabel("0");
        redScore.setMaximumSize(redPanel.getPreferredSize());
        if(inverse){
            redScore.setFont(fontTV);
        }
        else{
            redScore.setFont(font);
        }

        redScore.setForeground(Color.BLACK);
        redPanel.add(redScore, new GridBagConstraints());

        bluePanel = new JPanel(new GridBagLayout());
        bluePanel.setBackground(Color.BLUE);
        blueScore = new JLabel("0");
        if(inverse){
            blueScore.setFont(fontTV);
        }
        else{
            blueScore.setFont(font);
        }
        blueScore.setForeground(Color.BLACK);

        bluePanel.add(blueScore,new GridBagConstraints());

        if(!inverse){
            this.add(redPanel);
            this.add(bluePanel);
        }
        else{
            this.add(bluePanel);
            this.add(redPanel);
        }

    }

    public void updateTotalRedScore(int totalRedScore) {
        redScore.setText(String.valueOf(totalRedScore));
    }

    public void updateTotalBlueScore(int totalBlueScore) {
        blueScore.setText(String.valueOf(totalBlueScore));
    }

    public void reset() {
        redScore.setText(String.valueOf(0));
        blueScore.setText(String.valueOf(0));
    }
}
