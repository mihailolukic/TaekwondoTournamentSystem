package app.ui.screens.components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by Westi on 1/10/2017.
 */
public class MobileDevicePanel extends JPanel {

    Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 40);

    JLabel txtBlueScore;
    JLabel txtRedScore;


    public MobileDevicePanel() {
        super();
        this.setPreferredSize(new Dimension(150, 75));
        this.setMaximumSize(getPreferredSize());
        this.setLayout(new GridLayout(0, 2));
        initGui();
    }

    private void initGui() {

        Border border = LineBorder.createBlackLineBorder();
        txtBlueScore = new JLabel("", SwingConstants.CENTER);
        txtBlueScore.setBackground(Color.BLUE);
        txtBlueScore.setOpaque(true);
        txtBlueScore.setBorder(border);
        txtBlueScore.setText(String.valueOf(0));
        txtBlueScore.setFont(font);
        txtBlueScore.setForeground(Color.WHITE);

        txtRedScore = new JLabel("", SwingConstants.CENTER);
        txtRedScore.setBackground(Color.RED);
        txtRedScore.setOpaque(true);
        txtRedScore.setBorder(border);
        txtRedScore.setText(String.valueOf(0));
        txtRedScore.setFont(font);
        txtRedScore.setForeground(Color.WHITE);

        this.add(txtBlueScore);
        this.add(txtRedScore);

    }


    public void updateRedScore(int redScore) {
        System.out.println("MobileDevicePanel: updateRedScore:" + redScore);
        txtRedScore.setText(String.valueOf(redScore));
        invalidate();
    }

    public void updateBlueScore(int blueScore) {
        txtBlueScore.setText(String.valueOf(blueScore));
    }

    public void reset() {
        txtBlueScore.setText(String.valueOf(0));
        txtRedScore.setText(String.valueOf(0));
    }
}
