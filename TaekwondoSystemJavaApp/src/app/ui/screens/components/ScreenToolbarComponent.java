package app.ui.screens.components;

import app.backend.LogicBroker;
import app.backend.dataStorage.model.Fighter;
import app.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import static javax.swing.SwingConstants.CENTER;

public class ScreenToolbarComponent extends JPanel {


    Font fontForNames = new Font(Font.SANS_SERIF, Font.BOLD, 28);
    Font fontForAgeCategories = new Font(Font.SANS_SERIF, Font.PLAIN, 26);
    Font fontForWeightCategory = new Font(Font.SANS_SERIF, Font.PLAIN, 24);

    private JComponent cbRedName;
    private JComponent cbAgeCategory;
    private JComponent cbWeightCategory;
    private JComponent cbBlueName;


    private JButton btnRoundIncrease;
    private JButton btnRoundDecrease;
    private JLabel lblRoundNo;


    private int informationPanelWidth;
    private int informationPanelHeight;
    private final boolean isTv;
    private int mode;

    public ScreenToolbarComponent(int informationPanelWidth, int informationPanelHeight, boolean isTv, int mode) {
        super();
        this.informationPanelWidth = informationPanelWidth;
        this.informationPanelHeight = informationPanelHeight;
        this.isTv = isTv;
        this.mode = mode;
        this.setPreferredSize(new Dimension(informationPanelWidth, informationPanelHeight));
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        if (isTv) {
            initTvUI();
        } else {
            initUI();
        }
    }

    private void initUI() {

        cbRedName = new JComboBox();
        cbRedName.setPreferredSize(new Dimension(24 * informationPanelWidth / 72, informationPanelHeight));
        cbRedName.setMaximumSize(cbRedName.getPreferredSize());
        cbRedName.setFont(fontForNames);
        cbRedName.setForeground(Color.BLACK);

        cbAgeCategory = new JComboBox();
        cbAgeCategory.setPreferredSize(new Dimension(16 * (informationPanelWidth / 72), informationPanelHeight));
        cbAgeCategory.setMaximumSize(cbAgeCategory.getPreferredSize());
        ((JComboBox)cbAgeCategory).addItem("");
        cbAgeCategory.setFont(fontForAgeCategories);
        for (String age : LogicBroker.getInstance().getAgeCategories()) {
            ((JComboBox)cbAgeCategory).addItem(age);
        }

        cbWeightCategory = new JComboBox();
        cbWeightCategory.setPreferredSize(new Dimension(6 * informationPanelWidth / 72, informationPanelHeight));
        cbWeightCategory.setMaximumSize(cbWeightCategory.getPreferredSize());
        cbWeightCategory.setFont(fontForWeightCategory);

        GridLayout gridLayout = new GridLayout(2, 0);
        JPanel ringInformation = new JPanel();
        ringInformation.setLayout(gridLayout);
        ringInformation.setPreferredSize(new Dimension(8 * informationPanelWidth / 72, informationPanelHeight));
        ringInformation.setMaximumSize(ringInformation.getPreferredSize());

        lblRoundNo = new JLabel("Round: ", CENTER);
        lblRoundNo.setText(lblRoundNo.getText() + "1");
        ringInformation.add(lblRoundNo);


        JPanel panelRoundButtons = new JPanel();
        panelRoundButtons.setLayout(new GridLayout(0, 2));
        btnRoundIncrease = new JButton("+");
        btnRoundDecrease = new JButton("-");
        panelRoundButtons.add(btnRoundIncrease);
        panelRoundButtons.add(btnRoundDecrease);
        ringInformation.add(panelRoundButtons);
        cbBlueName = new JComboBox();
        cbBlueName.setPreferredSize(new Dimension(24 * informationPanelWidth / 72, informationPanelHeight));
        cbBlueName.setMaximumSize(cbBlueName.getPreferredSize());
        cbBlueName.setFont(fontForNames);
        cbBlueName.setForeground(Color.BLACK);


        this.add(cbRedName);
        this.add(cbAgeCategory);
        this.add(cbWeightCategory);
        this.add(ringInformation);
        this.add(cbBlueName);

        if(mode == Constants.PATTERN_MODE){
            cbAgeCategory.setEnabled(false);
            cbWeightCategory.setEnabled(false);
            for (String age : LogicBroker.getInstance().getFormsNames()) {
                ((JComboBox)cbRedName).addItem(age);
            }
            for (String age : LogicBroker.getInstance().getFormsNames()) {
                ((JComboBox)cbBlueName).addItem(age);
            }
        }
        else{
            ((JComboBox)cbBlueName).setEditable(true);
            ((JComboBox)cbRedName).setEditable(true);

        }
        initListeners();
    }

    private void initListeners() {
        btnRoundIncrease.addActionListener(e -> {
            LogicBroker.getInstance().increaseRoundNumber();
        });

        btnRoundDecrease.addActionListener(e -> {
            LogicBroker.getInstance().decreaseRoundNumber();
        });

        ((JComboBox) cbAgeCategory).addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) event.getItem();
                LogicBroker.getInstance().changeWeightCategory(item);
            }
        });
        ((JComboBox) cbWeightCategory).addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) event.getItem();
                filterByCategories((String) ((JComboBox) cbAgeCategory).getSelectedItem(), item);
                LogicBroker.getInstance().setSelectedWeightOneAllScreens(item);
            }
        });

        ((JComboBox) cbRedName).addItemListener(event -> {
            if(mode == Constants.PATTERN_MODE){
                String item = (String) event.getItem();
                LogicBroker.getInstance().setSelectedFormForRed(item);
                return;
            }
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) event.getItem();
                LogicBroker.getInstance().setSelectedRedPlayerOnAllScreens(item);
            }
        });

        ((JComboBox) cbBlueName).addItemListener(event -> {
            if(mode == Constants.PATTERN_MODE){
                String item = (String) event.getItem();
                LogicBroker.getInstance().setSelectedFormForBlue(item);
                return;
            }
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) event.getItem();
                LogicBroker.getInstance().setSelectedBluePlayerOnAllScreens(item);
            }
        });
    }

    private void filterByCategories(String ageCategory, String weightCategory) {
        ((JComboBox) cbBlueName).removeAllItems();
        ((JComboBox) cbRedName).removeAllItems();
        ArrayList<Fighter> fighters = LogicBroker.getInstance().filterByCategories(ageCategory, weightCategory);
        for (Fighter fighter : fighters) {
            ((JComboBox) cbBlueName).addItem(fighter.getNameAndSurname());
            ((JComboBox) cbRedName).addItem(fighter.getNameAndSurname());
        }
    }

    private void initTvUI() {

        cbRedName = new JLabel();
        cbRedName.setPreferredSize(new Dimension(informationPanelWidth / 3, informationPanelHeight));
        cbRedName.setMaximumSize(cbRedName.getPreferredSize());
        cbRedName.setFont(fontForNames);
        cbRedName.setForeground(Color.BLACK);
        cbRedName.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cbAgeCategory = new JLabel();
        cbAgeCategory.setPreferredSize(new Dimension(19 * (informationPanelWidth / 72), informationPanelHeight));
        cbAgeCategory.setMaximumSize(cbAgeCategory.getPreferredSize());
        ((JLabel)cbAgeCategory).setText("");
        cbAgeCategory.setFont(fontForAgeCategories);
        cbAgeCategory.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        cbWeightCategory = new JLabel();
        cbWeightCategory.setPreferredSize(new Dimension(informationPanelWidth / 24, informationPanelHeight));
        cbWeightCategory.setMaximumSize(cbWeightCategory.getPreferredSize());
        cbWeightCategory.setFont(fontForWeightCategory);
        cbWeightCategory.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        GridLayout gridLayout = new GridLayout(2, 0);
        JPanel ringInformation = new JPanel();
        ringInformation.setLayout(gridLayout);
        ringInformation.setPreferredSize(new Dimension(informationPanelWidth / 9, informationPanelHeight));
        ringInformation.setMaximumSize(ringInformation.getPreferredSize());
        ringInformation.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        lblRoundNo = new JLabel("Round: ", CENTER);
        lblRoundNo.setText(lblRoundNo.getText() + "1");
        lblRoundNo.setFont(fontForAgeCategories);
        ringInformation.add(lblRoundNo);


        JPanel panelRoundButtons = new JPanel();
        panelRoundButtons.setLayout(new BoxLayout(panelRoundButtons, BoxLayout.X_AXIS));
        btnRoundIncrease = new JButton("+");
        btnRoundDecrease = new JButton("-");
        btnRoundDecrease.setVisible(false);
        btnRoundIncrease.setVisible(false);
        panelRoundButtons.add(btnRoundIncrease);
        panelRoundButtons.add(btnRoundDecrease);
        ringInformation.add(panelRoundButtons);
        cbBlueName = new JLabel();
        cbBlueName.setPreferredSize(new Dimension(informationPanelWidth / 3, informationPanelHeight));
        cbBlueName.setMaximumSize(cbBlueName.getPreferredSize());
        cbBlueName.setFont(fontForNames);
        cbBlueName.setForeground(Color.BLACK);
        cbBlueName.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.add(cbBlueName);
        this.add(cbAgeCategory);
        this.add(cbWeightCategory);
        this.add(ringInformation);
        this.add(cbRedName);
    }

    public void setWeightCattegories(ArrayList<String> weightCategories) {
        if(isTv){
            return;
        }
        ((JComboBox)cbWeightCategory).removeAllItems();
        ((JComboBox)cbWeightCategory).addItem("");
        if (weightCategories == null) {
            return;
        }
        for (String weight : weightCategories) {
            ((JComboBox)cbWeightCategory).addItem(weight);
        }
        filterByCategories((String) ((JComboBox) cbAgeCategory).getSelectedItem(), "");
    }

    public void increaseRoundNumber(int roundNumber) {
        lblRoundNo.setText("Round: " + roundNumber);
    }

    public void decreaseRoundNumber(int roundNumber) {
        lblRoundNo.setText("Round: " + roundNumber);
    }

    public void setSelectedRedPlayerOnAllScreens(String item) {
        if(isTv){
            ((JLabel)cbRedName).setText(item);
        }
        else{
            ((JComboBox)cbRedName).setSelectedItem(item);
        }
    }

    public void setSelectedBluePlayerOnAllScreens(String item) {
        if(isTv){
            ((JLabel)cbBlueName).setText(item);
        }
        else{
            ((JComboBox)cbBlueName).setSelectedItem(item);
        }
    }

    public void setAgeCategoryOnEveryScreen(String age) {
        if(isTv){
            ((JLabel)cbAgeCategory).setText(age);
        }
        else{
            ((JComboBox)cbAgeCategory).setSelectedItem(age);
        }
    }

    public void setWeightCategoryOnEveryScreen(String weight) {
        if(isTv){
            ((JLabel)cbWeightCategory).setText(weight);
        }
        else{
            ((JComboBox)cbWeightCategory).setSelectedItem(weight);
        }
    }
}
