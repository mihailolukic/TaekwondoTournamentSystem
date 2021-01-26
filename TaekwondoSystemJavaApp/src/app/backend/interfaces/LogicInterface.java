package app.backend.interfaces;

import app.backend.model.LogicCommand;
import app.ui.interfaces.AdministrationInteface;
import app.backend.dataStorage.model.Fighter;
import org.json.JSONException;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by mihailol on 17.1.2017.
 */
public interface LogicInterface {
    void processTCPMessage(LogicCommand logicCommand);
    void decreaseYellowCard(int typeOfPlayer);
    void increaseYellowCard(int typeOfPlayer);
    void increaseWarning(int typeOfPlayer);
    void decreaseWarning(int typeOfPlayer);

    void startTimer();
    void pauseTimer();
    void resetTimer();
    void showTimerText(int i);
    void setTimerInteval(int i);

    void subscribeAdministrationScreenView(AdministrationInteface administrationScreen);
    ArrayList<Fighter> getFighterList();
    void importDataFromExcel(String absolutePath);
    ArrayList<String> getWeightCategories();
    ArrayList<String> getAgeCategories();
    ArrayList<String> getGenderCategories();

    ArrayList<Fighter> filterByCategories(String ageCategory, String weightCategory);

    void addFighter(Fighter fighter, int dialogResult);
    void deleteFighter(int number, int rowNumber);

    void changeWeightCategory(String item);
    void setSelectedWeightOneAllScreens(String item);
    ArrayList<String> getWeightCategoriesByAge(String ageCategory);

    boolean increaseRedScoreOnTablet(int i);

    boolean increaseBlueScoreOnTablet(int i);

    void increaseRoundNumber();

    void decreaseRoundNumber();
    void resetAll() throws JSONException;

    ArrayList<String> getRoundTimes();

    void pauseAllTablets(boolean timerStarted);

    void startTimeout();

    void selectRequiredButton1(Color red);
    void selectRequiredButton2(Color red);

    void setSelectedRedPlayerOnAllScreens(String item);
    void setSelectedBluePlayerOnAllScreens(String item);
    ArrayList<String> getTimeoutTimes();

    void setTimeoutInterval(int time);

    ArrayList<String> getMaleCategoriesForAddDialog();
    ArrayList<String> getFemaleCategoriesForAddDialog();

    void updateFighter(Fighter fighter, int dialogResult);

    boolean checkNumberOfDevices();

    ArrayList<String> getFormsNames();
}
