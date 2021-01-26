package app.ui.screens;

import app.ui.screens.ParentScreen;
import app.ui.screens.components.RequiredTehniquePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Westi on 1/2/2017.
 */
public class TVScreen extends ParentScreen {

    public TVScreen(int mode) {
        super("TV Screen", true, mode);
    }

    public void fillTimePanel(int timePanelWidth, int timePanelHeight) {
        JPanel device1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        device1Panel.setBackground(leftColor);
        device1Panel.setPreferredSize(new Dimension(timePanelWidth * 2 / 5, timePanelHeight));
        device1Panel.add(this.device4Component);
        this.timePanel.add(device1Panel);
        this.timePanel.add(timeComponent);
        JPanel device3Panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        device3Panel.setBackground(rightColor);
        device3Panel.setPreferredSize(new Dimension(timePanelWidth * 2 / 5, timePanelHeight));
        device3Panel.add(this.device1Component);
        this.timePanel.add(device3Panel);

    }

    public void fillWarningPanel() {

        JPanel device2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        device2Panel.setBackground(leftColor);
        device2Panel.add(device3Component);
        device2Panel.add(Box.createHorizontalStrut(50));
        device2Panel.add(redRequiredTehniquePanel);
        JPanel device4Panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        device4Panel.setBackground(rightColor);
        device4Panel.add(blueRequiredTehniquePanel);
        device4Panel.add(Box.createHorizontalStrut(50));
        device4Panel.add(device2Component);
        this.warningPanel.add(device2Panel);
        this.warningPanel.add(warningBlueComponent);
        this.warningPanel.add(warningRedComponent);
        this.warningPanel.add(device4Panel);
    }


    @Override
    public void selectRequiredButton1(Color color) {
        if (color == Color.BLUE) {
            redRequiredTehniquePanel.selectRequiredButton1();
        } else {
            blueRequiredTehniquePanel.selectRequiredButton1();
        }
    }

    @Override
    public void selectRequiredButton2(Color color) {
        if (color == Color.BLUE) {
            redRequiredTehniquePanel.selectRequiredButton2();
        } else {
            blueRequiredTehniquePanel.selectRequiredButton2();
        }
    }


}
