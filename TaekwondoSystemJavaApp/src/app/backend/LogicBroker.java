package app.backend;

import app.backend.brockers.TimerManager;
import app.util.Constants;
import app.backend.brockers.UIManager;
import app.backend.communicator.Communicator;
import app.backend.dataStorage.DataStorageManager;
import app.backend.dataStorage.model.Fighter;
import app.backend.interfaces.LogicInterface;
import app.backend.model.LogicCommand;
import app.ui.interfaces.AdministrationInteface;
import app.ui.interfaces.ViewInterface;
import org.json.JSONException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mihailol on 17.1.2017.
 */
public class LogicBroker implements LogicInterface {


    private static LogicBroker instance;
    private Communicator communicator;
    private DataStorageManager dataStorageManager;
    private UIManager uiManager;
    private TimerManager timerManager;


    private int mobileDevice1RedScore;
    private int mobileDevice1BlueScore;

    private int mobileDevice2RedScore;
    private int mobileDevice2BlueScore;

    private int mobileDevice3RedScore;
    private int mobileDevice3BlueScore;

    private int mobileDevice4RedScore;
    private int mobileDevice4BlueScore;

    private int yellowCardForRedPlayer;
    private int yellowCardForBluePlayer;
    private int warningsForRedPlayer;
    private int warningsForBluePlayer;


    private int roundNumber;


    public static void main(String[] args) {
        LogicBroker broker = LogicBroker.getInstance();
        broker.startServices();
    }

    public LogicBroker() {
        communicator = Communicator.getInstance();
        communicator.setLogicInterface(this);
        dataStorageManager = new DataStorageManager();
        uiManager = new UIManager();
        timerManager = new TimerManager();
        initVariables();
    }


    public static LogicBroker getInstance() {
        if (instance == null) {
            instance = new LogicBroker();
        }
        return instance;
    }

    private void initVariables() {
        mobileDevice1RedScore = 0;
        mobileDevice1BlueScore = 0;

        mobileDevice2RedScore = 0;
        mobileDevice2BlueScore = 0;

        mobileDevice3RedScore = 0;
        mobileDevice3BlueScore = 0;

        mobileDevice4RedScore = 0;
        mobileDevice4BlueScore = 0;

        yellowCardForRedPlayer = 0;
        yellowCardForBluePlayer = 0;

        warningsForRedPlayer = 0;
        warningsForBluePlayer = 0;

        roundNumber = 1;

    }


    public void startServices() {
        communicator.startServices();
    }

    @Override
    public void processTCPMessage(LogicCommand logicCommand) {

        String commandName = logicCommand.getCommandName();
        System.out.println("Logic broker : process TCP Message: " + commandName);
        switch (commandName) {
            case Constants.INCREASE_BLUE_CMD: {
                increaseBlueScore(logicCommand);
                break;
            }
            case Constants.INCREASE_RED_CMD: {
                increaseRedScore(logicCommand);
                break;
            }
            case Constants.CLIENT_DISCONECTED: {
                clientDisconnected(logicCommand.getDeviceNumber(), logicCommand.getDeviceAddress());
                break;
            }
            case Constants.INIT_TCP: {
                int deviceNumber = logicCommand.getDeviceNumber();
                try {
                    initializeTabletScore(deviceNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
            case Constants.GET_SCORE:{
                try {
                    int deviceIndex = logicCommand.getDeviceIndex();
                    initializeTabletScore(deviceIndex);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }

        }
    }

    private void initializeTabletScore(int deviceNumber) throws JSONException {
        switch (deviceNumber) {
            case 0:
                communicator.initializeTabletScore(deviceNumber, mobileDevice1RedScore, mobileDevice1BlueScore);
                break;
            case 1:
                communicator.initializeTabletScore(deviceNumber, mobileDevice2RedScore, mobileDevice2BlueScore);
                break;
            case 2:
                communicator.initializeTabletScore(deviceNumber, mobileDevice3RedScore, mobileDevice3BlueScore);
                break;
            case 3:
                communicator.initializeTabletScore(deviceNumber, mobileDevice4RedScore, mobileDevice4BlueScore);
                break;
        }
    }

    private void clientDisconnected(int deviceNumber, String deviceAddress) {
        timerManager.pauseTimer();
        communicator.removeIpAddress(deviceAddress);
        uiManager.showClientDisconnected(deviceNumber + 1);
    }

    @Override
    public void decreaseYellowCard(int typeOfPlayer) {
        switch (typeOfPlayer) {
            case Constants.RED_PLAYER:
                yellowCardForRedPlayer--;
                uiManager.setCardsNumber(typeOfPlayer, yellowCardForRedPlayer);
                try {
                    communicator.increaseRedScoreOnTablet(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                uiManager.setCardsNumber(typeOfPlayer, yellowCardForRedPlayer);
                break;
            case Constants.BLUE_PLAYER:
                yellowCardForBluePlayer--;
                try {
                    communicator.increaseBlueScoreOnTablet(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                uiManager.setCardsNumber(typeOfPlayer, yellowCardForBluePlayer);
                break;
        }
    }

    @Override
    public void increaseYellowCard(int typeOfPlayer) {
        switch (typeOfPlayer) {
            case Constants.RED_PLAYER:
                yellowCardForRedPlayer++;
                uiManager.setCardsNumber(typeOfPlayer, yellowCardForRedPlayer);
                try {
                    communicator.increaseRedScoreOnTablet(-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constants.BLUE_PLAYER:
                yellowCardForBluePlayer++;
                try {
                    communicator.increaseBlueScoreOnTablet(-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                uiManager.setCardsNumber(typeOfPlayer, yellowCardForBluePlayer);
                break;
        }
    }

    @Override
    public void increaseWarning(int typeOfPlayer) {
        switch (typeOfPlayer) {
            case Constants.RED_PLAYER:
                warningsForRedPlayer++;
                if (warningsForRedPlayer % 3 == 0) {
                    try {
                        communicator.increaseRedScoreOnTablet(-1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                uiManager.setWarningsNumber(typeOfPlayer, warningsForRedPlayer);
                break;
            case Constants.BLUE_PLAYER:
                warningsForBluePlayer++;
                if (warningsForBluePlayer % 3 == 0) {
                    try {
                        communicator.increaseBlueScoreOnTablet(-1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                uiManager.setWarningsNumber(typeOfPlayer, warningsForBluePlayer);
                break;
        }
    }

    @Override
    public void decreaseWarning(int typeOfPlayer) {
        switch (typeOfPlayer) {
            case Constants.RED_PLAYER:
                warningsForRedPlayer--;
                if (warningsForRedPlayer % 3 == 0) {
                    try {
                        communicator.increaseRedScoreOnTablet(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                uiManager.setWarningsNumber(typeOfPlayer, warningsForRedPlayer);
                break;
            case Constants.BLUE_PLAYER:
                warningsForBluePlayer--;
                if (warningsForBluePlayer % 3 == 0) {
                    try {
                        communicator.increaseBlueScoreOnTablet(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                uiManager.setWarningsNumber(typeOfPlayer, warningsForBluePlayer);
                break;
        }
    }

    @Override
    public void startTimer() {
        timerManager.startTimer();
    }

    @Override
    public void pauseTimer() {
        timerManager.pauseTimer();
    }

    @Override
    public void resetTimer() {
        timerManager.resetTimer();
    }

    @Override
    public void showTimerText(int time) {
        uiManager.showTimerText(time);
    }

    @Override
    public void setTimerInteval(int i) {
        timerManager.setInterval(i);
        uiManager.showTimerText(i);
    }

    @Override
    public void subscribeAdministrationScreenView(AdministrationInteface administrationScreen) {
        dataStorageManager.subscribeAdministrationScreenView(administrationScreen);
    }

    @Override
    public ArrayList<Fighter> getFighterList() {
        return dataStorageManager.getFighterList();
    }

    @Override
    public void importDataFromExcel(String absolutePath) {
        dataStorageManager.importDataFromExcel(absolutePath);
    }

    @Override
    //TODO MISA - ovo treba implementirati
    public ArrayList<String> getWeightCategories() {
        return dataStorageManager.getWeightCategories(null);
    }

    @Override
    public ArrayList<String> getAgeCategories() {
        return dataStorageManager.getAgeCategories();
    }


    @Override
    public ArrayList<String> getFormsNames() {
        return dataStorageManager.getFormsNames();
    }

    @Override
    public ArrayList<String> getGenderCategories() {
        return dataStorageManager.getGenderCategories();
    }

    @Override
    public ArrayList<Fighter> filterByCategories(String ageCategory, String weightCategory) {
        return dataStorageManager.filterByCategories(ageCategory, weightCategory);
    }

    @Override
    public void addFighter(Fighter fighter, int dialogResult) {
        try {
            dataStorageManager.addFighter(fighter, dialogResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFighter(int rowNumber, int dialogResult) {
        dataStorageManager.deleteFighter(rowNumber, dialogResult);
    }

    @Override
    public void changeWeightCategory(String ageCategory) {
        ArrayList<String> weightCategories = dataStorageManager.getWeightCategories(ageCategory);
        uiManager.setWeightCattegories(weightCategories);
        uiManager.setAgeCategoryOnEveryScreen(ageCategory);
    }

    @Override
    public void setSelectedWeightOneAllScreens(String item) {
        uiManager.setSelectedWeightOnAllScreens(item);
    }

    @Override
    public boolean increaseRedScoreOnTablet(int i) {
        if (!timerManager.isTimerStarted()) {
            return false;
        }
        try {
            communicator.increaseRedScoreOnTablet(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean increaseBlueScoreOnTablet(int i) {
        if (!timerManager.isTimerStarted()) {
            return false;
        }
        try {
            communicator.increaseBlueScoreOnTablet(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override

    public void increaseRoundNumber() {
        roundNumber++;
        uiManager.increaseRoundNumber(roundNumber);
    }

    @Override
    public void decreaseRoundNumber() {
        roundNumber--;
        uiManager.decreaseRoundNumber(roundNumber);
    }

    public void resetAll() throws JSONException {
        initVariables();
        resetTimer();
        communicator.resetAll();
        uiManager.resetAll();
    }

    @Override
    public ArrayList<String> getRoundTimes() {
        return timerManager.getRoundTimes();
    }

    @Override
    public void pauseAllTablets(boolean timerStarted) {
        try {
            communicator.pauseAllTablets(timerStarted);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startTimeout() {
        timerManager.startTimeOut();
    }

    @Override
    public ArrayList<String> getWeightCategoriesByAge(String ageCategory) {
        return dataStorageManager.getWeightCategories(ageCategory);
    }


    private void increaseRedScore(LogicCommand command) {
        switch (command.getDeviceNumber()) {
            case 0:
                mobileDevice1RedScore += command.getValue();
                uiManager.increaseRedScore(command.getDeviceNumber(), mobileDevice1RedScore);
                break;
            case 1:
                mobileDevice2RedScore += command.getValue();
                uiManager.increaseRedScore(command.getDeviceNumber(), mobileDevice2RedScore);
                break;
            case 2:
                mobileDevice3RedScore += command.getValue();
                uiManager.increaseRedScore(command.getDeviceNumber(), mobileDevice3RedScore);
                break;
            case 3:
                mobileDevice4RedScore += command.getValue();
                uiManager.increaseRedScore(command.getDeviceNumber(), mobileDevice4RedScore);
                break;
        }
    }

    private void increaseBlueScore(LogicCommand command) {
        switch (command.getDeviceNumber()) {
            case 0:
                mobileDevice1BlueScore += command.getValue();
                uiManager.increaseBlueScore(command.getDeviceNumber(), mobileDevice1BlueScore);
                break;
            case 1:
                mobileDevice2BlueScore += command.getValue();
                uiManager.increaseBlueScore(command.getDeviceNumber(), mobileDevice2BlueScore);
                break;
            case 2:
                mobileDevice3BlueScore += command.getValue();
                uiManager.increaseBlueScore(command.getDeviceNumber(), mobileDevice3BlueScore);
                break;
            case 3:
                mobileDevice4BlueScore += command.getValue();
                uiManager.increaseBlueScore(command.getDeviceNumber(), mobileDevice4BlueScore);
                break;
        }
    }

    public void updateTotalScore(ViewInterface interf) {
        int totalRedScore = 0;
        int totalBlueScore = 0;
        if (mobileDevice1RedScore > mobileDevice1BlueScore) {
            totalRedScore++;
        } else if (mobileDevice1RedScore < mobileDevice1BlueScore) {
            totalBlueScore++;
        }

        if (mobileDevice2RedScore > mobileDevice2BlueScore) {
            totalRedScore++;
        } else if (mobileDevice2RedScore < mobileDevice2BlueScore) {
            totalBlueScore++;
        }

        if (mobileDevice3RedScore > mobileDevice3BlueScore) {
            totalRedScore++;
        } else if (mobileDevice3RedScore < mobileDevice3BlueScore) {
            totalBlueScore++;
        }

        if (mobileDevice4RedScore > mobileDevice4BlueScore) {
            totalRedScore++;
        } else if (mobileDevice4RedScore < mobileDevice4BlueScore) {
            totalBlueScore++;
        }

        interf.updateTotalRedScore(totalRedScore);
        interf.updateTotalBlueScore(totalBlueScore);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void subscribeView(ViewInterface screen) {
        uiManager.subscribeView(screen);
    }

    public void selectRequiredButton1(Color color) {
        uiManager.selectRequiredButton1(color);
    }

    @Override
    public void selectRequiredButton2(Color color) {
        uiManager.selectRequiredButton2(color);
    }

    @Override
    public void setSelectedRedPlayerOnAllScreens(String item) {
        uiManager.setSelectedRedPlayerOnAllScreens(item);
    }

    public void setSelectedFormForRed(String item) {
        uiManager.setSelectedFormForRed(item);
    }

    public void setSelectedFormForBlue(String item) {
        uiManager.setSelectedFormForBlue(item);
    }

    @Override
    public void setSelectedBluePlayerOnAllScreens(String item) {
        uiManager.setSelectedBluePlayerOnAllScreens(item);
    }

    @Override
    public ArrayList<String> getTimeoutTimes() {
        return timerManager.getTimeoutTimes();
    }

    @Override
    public void setTimeoutInterval(int time) {
        timerManager.setTimeoutInterval(time);
    }

    @Override
    public ArrayList<String> getMaleCategoriesForAddDialog() {
        return dataStorageManager.getMaleCategoriesForAddDialog();
    }

    @Override
    public ArrayList<String> getFemaleCategoriesForAddDialog() {
        return dataStorageManager.getFemaleCategoriesForAddDialog();
    }

    @Override
    public void updateFighter(Fighter fighter, int dialogResult) {
        dataStorageManager.updateFighter(fighter, dialogResult);
    }

    @Override
    public boolean checkNumberOfDevices() {
        return communicator.checkNumberOfDevices();
    }


}
