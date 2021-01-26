package app.ui.screens.components;

import app.backend.LogicBroker;
import sun.rmi.runtime.Log;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by Mihailo on 2/4/2017.
 */
public class RequiredTehniquePanel extends JPanel {


    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 32);


    JButton btnRequiredTehnique1;
    JButton btnRequiredTehnique2;
    Color color;

    public RequiredTehniquePanel(Color color) {
        this.color = color;
        initConfiguration();
        initComponents();
        initGui();
        initListener();
    }

    private void initListener() {
        btnRequiredTehnique1.addActionListener(e -> {

            if (!btnRequiredTehnique1.isSelected() && LogicBroker.getInstance().getRoundNumber() == 1) {
                if (this.color == Color.RED) {
                    if(LogicBroker.getInstance().increaseRedScoreOnTablet(2)){
                        LogicBroker.getInstance().selectRequiredButton1(Color.RED);
                    }
                } else if (this.color == Color.BLUE) {
                    if(LogicBroker.getInstance().increaseBlueScoreOnTablet(2)){
                        LogicBroker.getInstance().selectRequiredButton1(Color.BLUE);
                    }
                }
            }

        });

        btnRequiredTehnique2.addActionListener(e -> {

            if (!btnRequiredTehnique2.isSelected() && LogicBroker.getInstance().getRoundNumber() == 2) {
                if (this.color == Color.RED) {
                    if(LogicBroker.getInstance().increaseRedScoreOnTablet(2)){
                        LogicBroker.getInstance().selectRequiredButton2(Color.RED);
                    }
                } else if (this.color == Color.BLUE) {
                    if(LogicBroker.getInstance().increaseBlueScoreOnTablet(2)){
                        LogicBroker.getInstance().selectRequiredButton2(Color.BLUE);
                    }
                }
            }

        });
    }

    public void selectRequiredButton2(){
        btnRequiredTehnique2.setBackground(Color.GREEN);
        btnRequiredTehnique2.setSelected(true);
    }


    public void selectRequiredButton1(){
        btnRequiredTehnique1.setBackground(Color.GREEN);
        btnRequiredTehnique1.setSelected(true);
    }

    private void initConfiguration() {
        this.setPreferredSize(new Dimension(120, 60));
        this.setMaximumSize(getPreferredSize());
        this.setLayout(new GridLayout(0, 2));
    }

    private void initGui() {
        Border border = LineBorder.createBlackLineBorder();
        btnRequiredTehnique1.setSize(new Dimension(60, 60));
        btnRequiredTehnique1.setBackground(Color.WHITE);
        btnRequiredTehnique1.setBorder(border);
        btnRequiredTehnique1.setContentAreaFilled(true);
        btnRequiredTehnique1.setFont(font);

        btnRequiredTehnique2.setSize(new Dimension(60, 60));
        btnRequiredTehnique2.setBackground(Color.WHITE);
        btnRequiredTehnique2.setBorder(border);
        btnRequiredTehnique2.setContentAreaFilled(true);
        btnRequiredTehnique2.setFont(font);

        this.add(btnRequiredTehnique1);
        this.add(btnRequiredTehnique2);
    }

    private void initComponents() {
        btnRequiredTehnique1 = new JButton("I");
        btnRequiredTehnique2 = new JButton("II");
    }

    public void reset() {
        btnRequiredTehnique1.setBackground(Color.WHITE);
        btnRequiredTehnique2.setBackground(Color.WHITE);
        btnRequiredTehnique1.setSelected(false);
        btnRequiredTehnique2.setSelected(false);
    }
}
