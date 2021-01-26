package app.backend.dataStorage;

import app.backend.LogicBroker;
import app.ui.interfaces.AdministrationInteface;
import app.backend.dataStorage.model.Fighter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mihailol on 17.1.2017.
 */
public class DataStorageManager {

    //Cached memory
    private ArrayList<Fighter> fighters = new ArrayList<>();
    private ArrayList<String> ageCategories = new ArrayList<>();
    private ArrayList<String> formsNames = new ArrayList<>();

    private AdministrationInteface administrationScreen;
    private ExcelParser excelParser;
    private ArrayList<String> genderCategory = new ArrayList<>();

    private CategoryFactory categoryFactory = new CategoryFactory();
    private FormsManager formsManager = new FormsManager();

    public DataStorageManager() {
        genderCategory.add("Male");
        genderCategory.add("Female");
        ageCategories = categoryFactory.getAgeCategoties();
        formsNames = formsManager.getNames();
    }

    public void subscribeAdministrationScreenView(AdministrationInteface administrationScreen) {
        this.administrationScreen = administrationScreen;
    }

    public ArrayList<Fighter> getFighterList() {
        return fighters;
    }

    public void importDataFromExcel(String absolutePath) {
        excelParser = new ExcelParser();
        try {
            if (fighters.isEmpty()) {
                fighters = excelParser.parseExcel(absolutePath);
            } else {
                fighters.addAll(excelParser.parseExcel(absolutePath));
            }
            administrationScreen.refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getWeightCategories(String ageCategory) {
        return categoryFactory.getWeightCategoryByAge(ageCategory);
    }


    public ArrayList<String> getAgeCategories() {
        return ageCategories;
    }


    public ArrayList<String> getFormsNames() {
        return formsNames;
    }

    public ArrayList<String> getGenderCategories() {
        return genderCategory;
    }

    public ArrayList<Fighter> filterByCategories(String ageCategory, String weightCategory) {
        if ((ageCategory == null && weightCategory == null)) {
            return fighters;
        } else if (ageCategory != null && weightCategory != null) {
            return filterByAllCategories(ageCategory, weightCategory);
        } else {
            if (ageCategory != null) {
                return filterByAgeCategories(ageCategory);
            } else if (weightCategory != null) {
                return filterByWeightCategory(weightCategory);
            } else {
                return null;
            }
        }
    }

    private ArrayList<Fighter> filterByWeightCategory(String weightCategory) {
        ArrayList<Fighter> tempFighters = new ArrayList<>();
        for (Fighter fighter : fighters) {
            if (weightCategory.equals(fighter.getWeightCategory())) {
                tempFighters.add(fighter);
            }
        }
        return tempFighters;
    }

    private ArrayList<Fighter> filterByAgeCategories(String ageCategory) {
        ArrayList<Fighter> tempFighters = new ArrayList<>();
        for (Fighter fighter : fighters) {
            if (ageCategory.equals(fighter.getAgeCategory())) {
                tempFighters.add(fighter);
            }
        }
        return tempFighters;
    }

    private ArrayList<Fighter> filterByAllCategories(String ageCategory, String weightCategory) {
        ArrayList<Fighter> tempFighters = new ArrayList<>();
        for (Fighter fighter : fighters) {
            if (ageCategory.equals(fighter.getAgeCategory()) && (weightCategory.equals(fighter.getWeightCategory()))) {
                tempFighters.add(fighter);
            }
        }
        return tempFighters;
    }

    public void addFighter(Fighter fighter, int dialogResult) throws IOException {
        int fighterId = 0;
        if (dialogResult == JOptionPane.YES_OPTION) {
            if (excelParser == null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls", "xlsx");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog((Component) administrationScreen);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    importDataFromExcel(selectedFile.getAbsolutePath());
                    fighterId = excelParser.writeFighterIntoFile(fighter);
                }
            } else {
                fighterId = excelParser.writeFighterIntoFile(fighter);
            }
        }
        fighter.setId(fighterId);
        fighters.add(fighter);
        administrationScreen.refreshTable();
    }

    public void deleteFighter(int rowNumber, int dialogResult) {
        Fighter fighter = fighters.get(rowNumber);
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                excelParser.deleteRowFromExcel(fighter.getId());
            } catch (IOException e) {
               administrationScreen.errorOccured("Excel file is probably opened in another program. Please close it and try again");
                return;
            }
        }
        fighters.remove(rowNumber);
        administrationScreen.refreshTable();
    }

    public ArrayList<String> getMaleCategoriesForAddDialog() {
        return categoryFactory.getMaleCategoriesForAddDialog();
    }


    public ArrayList<String> getFemaleCategoriesForAddDialog() {
        return categoryFactory.getFemaleCategoriesForAddDialog();
    }

    public void updateFighter(Fighter fighter, int dialogResult) {
        fighters.remove(fighter);
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                excelParser.deleteRowFromExcel(fighter.getId());
                fighter.setId(excelParser.writeFighterIntoFile(fighter));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fighters.add(fighter);
        administrationScreen.refreshTable();
    }

}
