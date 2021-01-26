package app.ui.screens.fighterAdministration.dialogs;

import app.backend.LogicBroker;
import app.backend.dataStorage.CategoryFactory;
import app.backend.dataStorage.model.Fighter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

/**
 * Created by Mihailo on 1/31/2017.
 */
public class AddFighterDialog extends JDialog {

    private JTextField txtTimeMark;
    private JTextField txtNameAndSurname;
    private JTextField txtCountry;
    private JTextField txtClub;
    private JTextField txtChoose;
    private JComboBox<String> cbGender;
    private JComboBox<String> maleCategory;
    private JComboBox<String> femaleCategory;
    private JTextField txtDegree;
    private JComboBox<String> cbSparingYouth;
    private JComboBox<String> cbSparingOlderYouthMale;
    private JComboBox<String> cbSparingOlderYouthFemale;
    private JComboBox<String> cbSparingJuniorsMale;
    private JComboBox<String> cbSparingJuniorsFemale;
    private JComboBox<String> cbSparingSeniorsMale;
    private JComboBox<String> cbSparingSeniorsFemale;
    private JTextField txtDegreeUmpires;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtDateOfBirth;
    private JComboBox<String> cbSparingVeterans;
    private JButton btnOK;
    private JButton btnCancel;
    private Fighter fighter;


    public AddFighterDialog(String s) {
        super();
        this.setTitle(s);
        initConfiguration();
        initComponents();
        initGUI();
        initListeners();
    }

    public AddFighterDialog(Fighter fighter) {
        super();
        this.fighter = fighter;
        this.setTitle("Update fighter");
        initConfiguration();
        initComponents();
        initGUI();
        initListeners();
        fillData();
    }

    private void fillData() {
        txtTimeMark.setText(this.fighter.getTimeMark());
        txtNameAndSurname.setText(this.fighter.getNameAndSurname());
        txtCountry.setText(this.fighter.getCountry());
        txtClub.setText(this.fighter.getClub());
        cbGender.setSelectedItem(this.fighter.getGender());
        maleCategory.setSelectedItem(setComboBoxValue(this.fighter.getMaleCategory()));
        femaleCategory.setSelectedItem(setComboBoxValue(this.fighter.getFemaleCategory()));
        txtDegree.setText(this.fighter.getDegree());
        cbSparingYouth.setSelectedItem(setComboBoxValue(this.fighter.getSparingYouth()));
        cbSparingOlderYouthMale.setSelectedItem(setComboBoxValue(this.fighter.getSparingOlderYouthMale()));
        cbSparingOlderYouthFemale.setSelectedItem(setComboBoxValue(this.fighter.getSparingOlderYouthFemale()));
        cbSparingJuniorsMale.setSelectedItem(setComboBoxValue(this.fighter.getSparingJuniorsMale()));
        cbSparingJuniorsFemale.setSelectedItem(setComboBoxValue(this.fighter.getSparingJuniorsFemale()));
        cbSparingSeniorsMale.setSelectedItem(setComboBoxValue(this.fighter.getSparingSeniorsMale()));
        cbSparingSeniorsFemale.setSelectedItem(setComboBoxValue(this.fighter.getSparingSeniorsFemale()));
        txtDegreeUmpires.setText(this.fighter.getDegreeUmpires());
        txtEmail.setText(this.fighter.getEmail());
        txtPhone.setText(this.fighter.getPhone());
        txtDateOfBirth.setText(this.fighter.getDateOfBirth());
        btnOK.setText("Update");
        cbSparingVeterans.setSelectedItem(setComboBoxValue(this.fighter.getSparingVeterans()));
    }

    private void initListeners() {
        btnOK.addActionListener(e -> {
            if(txtNameAndSurname.getText()!=null && !txtNameAndSurname.getText().isEmpty()){
                addNewFighter();
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, "You must fill required (*) fields!", "Warning", JOptionPane.INFORMATION_MESSAGE);
            }

        });
        btnCancel.addActionListener(e -> {
            this.dispose();
        });
        cbGender.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) event.getItem();
                if(item.equals("Male")){
                    lockFemaleCategories();
                }
                else{
                    lockMaleCategories();
                }
            }
        });
        maleCategory.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) event.getItem();
                lockMaleWeightCategoryByAgeCategory(item);

            }
        });
        femaleCategory.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) event.getItem();
                lockFemaleWeightCategoryByAgeCategory(item);
            }
        });


    }

    private void lockFemaleWeightCategoryByAgeCategory(String item) {
        switch (item){
            case "Youth":
                lockAllWeightCategories();
                cbSparingYouth.setEnabled(true);
                break;
            case "Older Youth":
                lockAllWeightCategories();
                cbSparingOlderYouthFemale.setEnabled(true);
                break;
            case "Juniors":
                lockAllWeightCategories();
                cbSparingJuniorsFemale.setEnabled(true);
                break;
            case "Older Juniors":
                lockAllWeightCategories();
                cbSparingJuniorsFemale.setEnabled(true);
                break;
            case "Seniors":
                lockAllWeightCategories();
                cbSparingSeniorsFemale.setEnabled(true);
                break;
        }
    }

    private void lockMaleWeightCategoryByAgeCategory(String item) {
        switch (item){
            case "Youth":
                lockAllWeightCategories();
                cbSparingYouth.setEnabled(true);
                break;
            case "Older Youth":
                lockAllWeightCategories();
                cbSparingOlderYouthMale.setEnabled(true);
                break;
            case "Juniors":
                lockAllWeightCategories();
                cbSparingJuniorsMale.setEnabled(true);
                break;
            case "Older Juniors":
                lockAllWeightCategories();
                cbSparingJuniorsMale.setEnabled(true);
                break;
            case "Seniors":
                lockAllWeightCategories();
                cbSparingSeniorsMale.setEnabled(true);
                break;
            case "Veterans":
                lockAllWeightCategories();
                cbSparingVeterans.setEnabled(true);
                break;
        }
    }


    private void initConfiguration() {
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
//        this.setLayout(new FlowLayout());
    }

    private void initComponents() {
        txtTimeMark = new JTextField();
        txtNameAndSurname = new JTextField();
        txtCountry = new JTextField();
        txtClub = new JTextField();
        txtChoose = new JTextField();
        cbGender = new JComboBox();
        maleCategory = new JComboBox();
        femaleCategory = new JComboBox();
        txtDegree = new JTextField();
        cbSparingYouth = new JComboBox<String>();
        cbSparingOlderYouthMale = new JComboBox<String>();
        cbSparingOlderYouthFemale = new JComboBox<String>();
        cbSparingJuniorsMale = new JComboBox<String>();
        cbSparingJuniorsFemale = new JComboBox<String>();
        cbSparingSeniorsMale = new JComboBox<String>();
        cbSparingSeniorsFemale = new JComboBox<String>();
        txtDegreeUmpires = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtDateOfBirth = new JTextField();
        cbSparingVeterans = new JComboBox<String>();
        btnOK = new JButton("OK");
        btnCancel = new JButton("Cancel");

    }

    private void initGUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 3));

        panel.add(createPanel("Time mark", txtTimeMark));
        panel.add(createPanel("Name and surname *", txtNameAndSurname));
        panel.add(createPanel("Country", txtCountry));
        panel.add(createPanel("Club", txtClub));
        panel.add(createPanel("Choose", txtChoose));
        panel.add(createPanel("Gender", cbGender));
        panel.add(createPanel("Male category", maleCategory));
        panel.add(createPanel("Female category", femaleCategory));
        panel.add(createPanel("Degree", txtDegree));
        panel.add(createPanel("Sparing youth", cbSparingYouth));
        panel.add(createPanel("Sparing older youth male", cbSparingOlderYouthMale));
        panel.add(createPanel("Sparing older youth female", cbSparingOlderYouthFemale));
        panel.add(createPanel("Sparing juniors male", cbSparingJuniorsMale));
        panel.add(createPanel("Sparing juniors female", cbSparingJuniorsFemale));
        panel.add(createPanel("Sparing seniors male", cbSparingSeniorsMale));
        panel.add(createPanel("Sparing seniors female", cbSparingSeniorsFemale));
        panel.add(createPanel("Degree umpires", txtDegreeUmpires));
        panel.add(createPanel("Sparing veterans", cbSparingVeterans));
        panel.add(createPanel("Email", txtEmail));
        panel.add(createPanel("Phone", txtPhone));
        panel.add(createPanel("Date of birth", txtDateOfBirth));
        lockFemaleCategories();

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnOK.setPreferredSize(new Dimension(150, 20));
        btnOK.setMaximumSize(btnOK.getPreferredSize());
        btnCancel.setPreferredSize(new Dimension(150, 20));
        btnCancel.setMaximumSize(btnOK.getPreferredSize());

        buttonsPanel.add(btnOK);
        buttonsPanel.add(new JSeparator());
        buttonsPanel.add(btnCancel);
        panel.add(new JLabel());
        panel.add(buttonsPanel);

        this.setContentPane(panel);
        this.pack();

    }

    private void lockMaleCategories() {
        lockAllWeightCategories();
        maleCategory.setEnabled(false);
        femaleCategory.setEnabled(true);

    }
    private void lockFemaleCategories() {
        lockAllWeightCategories();
        femaleCategory.setEnabled(false);
        maleCategory.setEnabled(true);
    }

    private void lockAllWeightCategories() {
        cbSparingJuniorsFemale.setEnabled(false);
        cbSparingJuniorsMale.setEnabled(false);
        cbSparingOlderYouthFemale.setEnabled(false);
        cbSparingOlderYouthMale.setEnabled(false);
        cbSparingSeniorsFemale.setEnabled(false);
        cbSparingSeniorsMale.setEnabled(false);
        cbSparingVeterans.setEnabled(false);
        cbSparingYouth.setEnabled(false);
    }

    private JPanel createPanel(String text, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(150, 20));
        panel.add(label);
        panel.add(new JSeparator());
        if (component instanceof JTextField) {
            JTextField textField = (JTextField) component;
            textField.setPreferredSize(new Dimension(120, 20));
            panel.add(textField);
        } else if (component instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) component;
            comboBox.setPreferredSize(new Dimension(120, 20));
            switch (text) {
                case "Gender":
                    for (String category : LogicBroker.getInstance().getGenderCategories()) {
                        comboBox.addItem(category);
                    }
                    break;
                case "Male category":
                    for (String category : LogicBroker.getInstance().getMaleCategoriesForAddDialog()) {
                        comboBox.addItem(category);
                    }
                    break;
                case "Female category":
                    for (String category : LogicBroker.getInstance().getFemaleCategoriesForAddDialog()) {
                        comboBox.addItem(category);
                    }
                    break;
                case "Sparing youth":
                    for (String category : LogicBroker.getInstance().getWeightCategoriesByAge(CategoryFactory.FEMALE_YOUTH)) {
                        comboBox.addItem(category);
                    }
                    break;
                case "Sparing older youth male":
                    for (String category : LogicBroker.getInstance().getWeightCategoriesByAge(CategoryFactory.MALE_OLDER_YOUTH)) {
                        comboBox.addItem(category);
                    }
                    break;
                case "Sparing older youth female":
                    for (String category : LogicBroker.getInstance().getWeightCategoriesByAge(CategoryFactory.FEMALE_OLDER_YOUTH)) {
                        comboBox.addItem(category);
                    }
                    break;
                case "Sparing juniors male":
                    for (String category : LogicBroker.getInstance().getWeightCategoriesByAge(CategoryFactory.MALE_JUNIORS)) {
                        comboBox.addItem(category);
                    }
                    break;
                case "Sparing juniors female":
                    for (String category : LogicBroker.getInstance().getWeightCategoriesByAge(CategoryFactory.FEMALE_JUNIORS)) {
                        comboBox.addItem(category);
                    }
                    break;
                case "Sparing seniors male":
                    for (String category : LogicBroker.getInstance().getWeightCategoriesByAge(CategoryFactory.MALE_SENIOR)) {
                        comboBox.addItem(category);
                    }
                    break;
                case "Sparing seniors female":
                    for (String category : LogicBroker.getInstance().getWeightCategoriesByAge(CategoryFactory.FEMALE_SENIOR)) {
                        comboBox.addItem(category);
                    }
                    break;
                case "Sparing veterans":
                    for (String category : LogicBroker.getInstance().getWeightCategoriesByAge(CategoryFactory.MALE_VETERANS)) {
                        comboBox.addItem(category);
                    }
                    break;
            }

            panel.add(comboBox);
        }
        return panel;
    }


    private void addNewFighter(){
        Fighter fighter = new Fighter();
        fighter.setTimeMark(txtTimeMark.getText());
        fighter.setNameAndSurname(txtNameAndSurname.getText());
        fighter.setCountry(txtCountry.getText());
        fighter.setClub(txtClub.getText());
        fighter.setChoose(txtChoose.getText());
        fighter.setGender(cbGender.getSelectedItem().toString());
        fighter.setMaleCategory(getComboBoxValue(maleCategory.getSelectedItem().toString()));
        fighter.setFemaleCategory(getComboBoxValue(femaleCategory.getSelectedItem().toString()));
        fighter.setDegree(txtDegree.getText());
        fighter.setSparingYouth(getComboBoxValue(cbSparingYouth.getSelectedItem().toString()));
        fighter.setSparingOlderYouthMale(getComboBoxValue(cbSparingOlderYouthMale.getSelectedItem().toString()));
        fighter.setSparingOlderYouthFemale(getComboBoxValue(cbSparingOlderYouthFemale.getSelectedItem().toString()));
        fighter.setSparingJuniorsMale(getComboBoxValue(cbSparingJuniorsMale.getSelectedItem().toString()));
        fighter.setSparingJuniorsFemale(getComboBoxValue(cbSparingJuniorsFemale.getSelectedItem().toString()));
        fighter.setSparingSeniorsMale(getComboBoxValue(cbSparingSeniorsMale.getSelectedItem().toString()));
        fighter.setSparingSeniorsFemale(getComboBoxValue(cbSparingSeniorsFemale.getSelectedItem().toString()));
        fighter.setDegreeUmpires(txtDegreeUmpires.getText());
        fighter.setSparingVeterans(getComboBoxValue(cbSparingVeterans.getSelectedItem().toString()));
        fighter.setEmail(txtEmail.getText());
        fighter.setPhone(txtPhone.getText());
        fighter.setDateOfBirth(txtDateOfBirth.getText());
        if(this.fighter == null){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want to add fighter into excel file also ?", "Saving excel file..", dialogButton);
            LogicBroker.getInstance().addFighter(fighter,dialogResult);
        }
        else{
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want to update fighter in excel file also ?", "Saving excel file..", dialogButton);
            LogicBroker.getInstance().updateFighter(fighter,dialogResult);
        }

    }

    private String getComboBoxValue(String s) {
        if(s.equals("nothing")){
            return null;
        }
        return s;
    }

    private String setComboBoxValue(String s) {
        if(s == null){
            return "nothing";
        }
        return s;
    }



}
