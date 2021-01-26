package app.ui.screens;

import app.backend.LogicBroker;
import app.ui.interfaces.ViewInterface;
import app.ui.screens.components.*;
import app.util.Constants;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class ParentScreen extends JDialog implements ViewInterface {

    private final boolean isTv;
    protected final int mode;
    private int screenWidth;
    private int screenHeight;

    private ScoringPanel scorePanel;
    private ScreenToolbarComponent toolbar;

    Color leftColor;
    Color rightColor;
    JPanel timePanel;

    JPanel warningPanel;
    WarningPanel warningRedComponent;
    WarningPanel warningBlueComponent;
    MobileDevicePanel device4Component;
    MobileDevicePanel device1Component;
    MobileDevicePanel device3Component;
    MobileDevicePanel device2Component;
    RequiredTehniquePanel redRequiredTehniquePanel;
    RequiredTehniquePanel blueRequiredTehniquePanel;
    TimerComponent timeComponent;
    JButton timeout;
    JButton resetAll;

    JPanel device1Panel;
    JPanel device2Panel;
    JPanel device3Panel;
    JPanel device4Panel;


    public ParentScreen(String title, boolean isTv, int mode) {
        super();
        this.isTv = isTv;
        this.mode = mode;
        this.setTitle(title);
        if (isTv) {
            leftColor = Color.BLUE;
            rightColor = Color.RED;
        } else {
            rightColor = Color.BLUE;
            leftColor = Color.RED;
        }
        LogicBroker.getInstance().subscribeView(this);
        initUI();
        changeTVScreenSize();
    }


    private void initUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, screenSize.width, screenSize.height);
        this.screenWidth = screenSize.width;
        this.screenHeight = screenSize.height;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(isTv);
        this.setVisible(true);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);

        timePanel = new JPanel();
        timePanel.setPreferredSize(new Dimension(screenWidth, screenHeight * 1 / 10));
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));

        scorePanel = new ScoringPanel(screenWidth, screenHeight * 9 / 20, isTv);

        warningPanel = new JPanel(new GridLayout(0, 4));
        warningPanel.setPreferredSize(new Dimension(screenWidth, screenHeight * 3 / 20));
        warningPanel.setMinimumSize(warningPanel.getPreferredSize());
        warningPanel.setBackground(Color.BLACK);

        toolbar = new ScreenToolbarComponent(screenWidth, screenHeight / 10, isTv, mode);
        timeComponent = new TimerComponent(screenWidth * 1 / 5, screenHeight / 10, isTv);

        timeout = new JButton("Timeout");
        timeout.setPreferredSize(new Dimension(120, 60));
        timeout.setMaximumSize(getPreferredSize());

        resetAll = new JButton("Reset all");
        resetAll.setPreferredSize(new Dimension(120, 60));
        resetAll.setMaximumSize(getPreferredSize());

        device2Component = new MobileDevicePanel();
        device3Component = new MobileDevicePanel();
        device4Component = new MobileDevicePanel();
        device1Component = new MobileDevicePanel();

        warningRedComponent = new WarningPanel(false, Constants.RED_PLAYER, !isTv);
        warningBlueComponent = new WarningPanel(true, Constants.BLUE_PLAYER, !isTv);

        redRequiredTehniquePanel = new RequiredTehniquePanel(Color.RED);
        blueRequiredTehniquePanel = new RequiredTehniquePanel(Color.BLUE);

        fillTimePanel(screenWidth, screenHeight / 10);
        fillWarningPanel();
        this.add(toolbar);
        this.add(timePanel);
        this.add(scorePanel);
        this.add(warningPanel);
        timeout.addActionListener(e -> {
            LogicBroker.getInstance().startTimeout();
        });

        resetAll.addActionListener(e -> {
            try {
                LogicBroker.getInstance().resetAll();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        });


        if(mode == Constants.PATTERN_MODE){
            warningRedComponent.setPanelVisible(false);
            warningBlueComponent.setPanelVisible(false);
            redRequiredTehniquePanel.setVisible(false);
            blueRequiredTehniquePanel.setVisible(false);
        }

    }

    private void changeTVScreenSize() {
        if (!isTv) {
            return;
        }
        GraphicsDevice otherScreen = getOtherScreen(this);
        JFrame frameOnOtherScreen = new JFrame(otherScreen.getDefaultConfiguration());
        setLocationRelativeTo(frameOnOtherScreen);
        int width = otherScreen.getDisplayMode().getWidth();
        int height = otherScreen.getDisplayMode().getHeight();
        frameOnOtherScreen.dispose();
        this.setResizable(true);
        this.setSize(new Dimension(width, height));
    }


    @Override
    public void increaseRedDeviceScore(int deviceNumber, int value) {
        System.out.println("BaseDialog: increaseRedDeviceScore: deviceNumber:" + deviceNumber + " value:" + value);
        switch (deviceNumber) {
            case 0:
                device1Component.updateRedScore(value);
                break;
            case 1:
                device2Component.updateRedScore(value);
                break;
            case 2:
                device3Component.updateRedScore(value);
                break;
            case 3:
                device4Component.updateRedScore(value);
                break;
        }
    }


    @Override
    public void increaseBlueDeviceScore(int deviceNumber, int value) {
        switch (deviceNumber) {
            case 0:
                device1Component.updateBlueScore(value);
                break;
            case 1:
                device2Component.updateBlueScore(value);
                break;
            case 2:
                device3Component.updateBlueScore(value);
                break;
            case 3:
                device4Component.updateBlueScore(value);
                break;
        }
    }

    @Override
    public void updateTotalRedScore(int totalRedScore) {
        scorePanel.updateTotalRedScore(totalRedScore);
    }

    @Override
    public void updateTotalBlueScore(int totalBlueScore) {
        scorePanel.updateTotalBlueScore(totalBlueScore);
    }


    @Override
    public void setCardsNumber(int typeOfPlayer, int numberCards) {
        switch (typeOfPlayer) {
            case Constants.RED_PLAYER:
                warningRedComponent.setCardsNumber(numberCards);
                break;
            case Constants.BLUE_PLAYER:
                warningBlueComponent.setCardsNumber(numberCards);
                break;
        }
    }


    @Override
    public void setWarningsNumber(int typeOfPlayer, int warningsForRedPlayer) {
        switch (typeOfPlayer) {
            case Constants.RED_PLAYER:
                warningRedComponent.setWarningsNumber(warningsForRedPlayer);
                break;
            case Constants.BLUE_PLAYER:
                warningBlueComponent.setWarningsNumber(warningsForRedPlayer);
                break;
        }
    }

    public void resetAll() {
        device1Component.reset();
        device2Component.reset();
        device3Component.reset();
        device4Component.reset();
        scorePanel.reset();
        redRequiredTehniquePanel.reset();
        blueRequiredTehniquePanel.reset();
        warningBlueComponent.reset();
        warningRedComponent.reset();
    }


    @Override
    public void showTimerText(int time) {
        timeComponent.showTimerText(time);
    }

    @Override
    public void increaseRoundNumber(int roundNumber) {
        toolbar.increaseRoundNumber(roundNumber);
    }

    @Override
    public void decreaseRoundNumber(int roundNumber) {
        toolbar.decreaseRoundNumber(roundNumber);
    }


    @Override
    public void setWeightCattegories(ArrayList<String> weightCategories) {
        toolbar.setWeightCattegories(weightCategories);
    }

    @Override
    public void setAgeCategoryOnEveryScreen(String weightCategory) {
        if (isTv) {
            toolbar.setAgeCategoryOnEveryScreen(weightCategory);
        }
    }

    @Override
    public void setSelectedWeightOnAllScreens(String item) {
        if (isTv) {
            toolbar.setWeightCategoryOnEveryScreen(item);
        }
    }


    @Override
    public void setSelectedRedPlayerOnAllScreens(String item) {
        toolbar.setSelectedRedPlayerOnAllScreens(item);
    }

    @Override
    public void setSelectedBluePlayerOnAllScreens(String item) {
        toolbar.setSelectedBluePlayerOnAllScreens(item);
    }

    @Override
    public void showClientDisconnected(int deviceNumber) {
        if (isTv) {
            return;
        }
        JOptionPane.showMessageDialog(null, "Tablet with number " + deviceNumber + " has been disconnected. Please connect it again and set score !");
    }

    private GraphicsDevice getOtherScreen(Component component) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        if (graphicsEnvironment.getScreenDevices().length == 1) {
            return graphicsEnvironment.getScreenDevices()[0];
        }

        GraphicsDevice theWrongOne = component.getGraphicsConfiguration().getDevice();
        for (GraphicsDevice dev : graphicsEnvironment.getScreenDevices()) {
            if (dev != theWrongOne) {
                return dev;
            }
        }
        return null;
    }


    protected abstract void fillWarningPanel();

    protected abstract void fillTimePanel(int screenWidth, int height);


}
