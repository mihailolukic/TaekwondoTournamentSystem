package app.ui.frame;

import app.ui.screens.TVScreen;
import app.backend.LogicBroker;
import app.ui.screens.fighterAdministration.AdministrationScreen;
import app.ui.screens.LaptopScreen;
import app.util.Constants;
import org.json.JSONException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static app.util.Constants.PATTERN_MODE;
import static app.util.Constants.SPARING_MODE;

/**
 * Created by mihailol on 16.1.2017.
 */
public class MainFrame extends JFrame {

    Font fontTitle = new Font(Font.SANS_SERIF, Font.PLAIN, 40);
    Font fontText = new Font(Font.SANS_SERIF, Font.BOLD, 30);
    Border border = BorderFactory.createLineBorder(Color.BLACK,4);

    private int screenHeight;
    private int screenWidth;


    private JPanel panelTitle;
    private JPanel panelContent;
    private JLabel lblTitle;

    private JButton btnSparing;
    private JButton btnPatterns;
    private JButton btnFighterAdmin;

    public MainFrame(String title) {
        super(title);
        LogicBroker.getInstance().startServices();
        initConfiguration();
        initComponents();
        initGUI();
    }

    protected void initConfiguration() {
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenHeight = screenSize.height;
        this.screenWidth = screenSize.width;


    }

    private void initComponents() {
        panelTitle = new JPanel();
        panelContent = new JPanel();
        lblTitle = new JLabel();
        btnSparing = new JButton("Start sparing");
        btnFighterAdmin = new JButton("Administration");
        btnPatterns = new JButton("Start patterns");
    }


    protected void initGUI() {

        lblTitle.setText("Configuration");
        lblTitle.setFont(fontTitle);
        lblTitle.setForeground(Color.WHITE);
        panelTitle.setPreferredSize(new Dimension(getScreenWidth(),getScreenHeight()/5));
        panelTitle.setMaximumSize(panelTitle.getPreferredSize());
        panelTitle.setBackground(Color.RED);
        panelTitle.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTitle.setAlignmentY(Component.CENTER_ALIGNMENT);

        panelTitle.add(lblTitle);
        this.add(panelTitle);


        initButton(btnFighterAdmin);
        initButton(btnSparing);
        initButton(btnPatterns);

        btnFighterAdmin.addActionListener(e -> {
            AdministrationScreen administrationScreen = new AdministrationScreen("Fighter administation");
            administrationScreen.setVisible(true);
        });

        btnSparing.addActionListener(e -> startScreens(SPARING_MODE));

        btnPatterns.addActionListener(e -> startScreens(PATTERN_MODE));

        panelContent.setPreferredSize(new Dimension(getScreenWidth(),getScreenHeight()*4/5));
        panelContent.setMaximumSize(panelContent.getPreferredSize());
        panelContent.setLayout(new GridBagLayout());

        GridBagConstraints separatorConstraint = new GridBagConstraints();
        separatorConstraint.weightx = 0.4;
        separatorConstraint.fill = GridBagConstraints.NONE;

        GridBagConstraints labelConstraints;
        labelConstraints = new GridBagConstraints();
        labelConstraints.weightx = 0.6;
        labelConstraints.fill = GridBagConstraints.NONE;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.gridwidth = 1;

        panelContent.add(new JSeparator(JSeparator.HORIZONTAL), separatorConstraint);
        panelContent.add(new JSeparator(JSeparator.HORIZONTAL), separatorConstraint);
        panelContent.add(btnFighterAdmin,labelConstraints);
        panelContent.add(new JSeparator(JSeparator.HORIZONTAL), separatorConstraint);
        panelContent.add(btnSparing,labelConstraints);
        panelContent.add(new JSeparator(JSeparator.HORIZONTAL), separatorConstraint);
        panelContent.add(btnPatterns,labelConstraints);

        this.add(panelContent);

    }

    private void initButton(JButton button) {
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setFont(fontText);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(border);
        button.setPreferredSize(new Dimension(250,100));
        button.setMaximumSize(button.getPreferredSize());
    }


    private void startScreens(int mode) {
        if(LogicBroker.getInstance().checkNumberOfDevices()) {
            LaptopScreen screen = new LaptopScreen(mode);
            TVScreen tvScreen = new TVScreen(mode);
            LogicBroker.getInstance().setTimerInteval(120);
            screen.setVisible(true);
            tvScreen.setVisible(true);
            try {
                LogicBroker.getInstance().resetAll();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            JOptionPane.showMessageDialog(this,"Please connect all 4 tablets with laptop application.");
        }
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

}
