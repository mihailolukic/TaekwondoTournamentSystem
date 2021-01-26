package app.ui.screens;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Westi on 12/10/2016.
 */
public class LaptopScreen extends ParentScreen {


    public LaptopScreen(int mode) {
        super("Laptop Taekwondo screen", false, mode);
    }


    public void fillTimePanel(int timePanelWidth, int timePanelHeight) {
        device1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        device1Panel.setBackground(leftColor);
        device1Panel.setPreferredSize(new Dimension(timePanelWidth * 2 / 5, timePanelHeight));
        device1Panel.add(this.device2Component);
        device1Panel.add(Box.createHorizontalStrut(50));
        device1Panel.add(timeout);

        this.timePanel.add(device1Panel);
        this.timePanel.add(timeComponent);

        device3Panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        device3Panel.setBackground(rightColor);
        device3Panel.setPreferredSize(new Dimension(timePanelWidth * 2 / 5, timePanelHeight));

        device3Panel.add(resetAll);
        device3Panel.add(Box.createHorizontalStrut(50));
        device3Panel.add(this.device3Component);
        this.timePanel.add(device3Panel);
    }

    public void fillWarningPanel() {

        device2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        device2Panel.setBackground(leftColor);
        device2Panel.add(device1Component);
        device2Panel.add(Box.createHorizontalStrut(50));
        device2Panel.add(redRequiredTehniquePanel);
        device4Panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        device4Panel.setBackground(rightColor);
        device4Panel.add(blueRequiredTehniquePanel);
        device4Panel.add(Box.createHorizontalStrut(50));
        device4Panel.add(device4Component);
        this.warningPanel.add(device2Panel);
        this.warningPanel.add(warningRedComponent);
        this.warningPanel.add(warningBlueComponent);
        this.warningPanel.add(device4Panel);
    }

    @Override
    public void selectRequiredButton1(Color color) {
        if (color == Color.RED) {
            redRequiredTehniquePanel.selectRequiredButton1();
        } else {
            blueRequiredTehniquePanel.selectRequiredButton1();
        }
    }

    @Override
    public void selectRequiredButton2(Color color) {
        if (color == Color.RED) {
            redRequiredTehniquePanel.selectRequiredButton2();
        } else {
            blueRequiredTehniquePanel.selectRequiredButton2();
        }
    }


}
