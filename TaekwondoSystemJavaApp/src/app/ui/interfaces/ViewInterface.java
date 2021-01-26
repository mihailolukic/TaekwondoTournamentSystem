package app.ui.interfaces;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by mihailol on 17.1.2017.
 */
public interface ViewInterface {

    void increaseBlueDeviceScore(int deviceNumber, int value);
    void increaseRedDeviceScore(int deviceNumber, int value);

    void updateTotalRedScore(int totalRedScore);
    void updateTotalBlueScore(int totalBlueScore);
    void setCardsNumber(int typeOfPlayer, int numberCards);
    void setWarningsNumber(int typeOfPlayer, int warningsForRedPlayer);
    void showTimerText(int time);

    void setWeightCattegories(ArrayList<String> weightCategories);

    void setAgeCategoryOnEveryScreen(String ageCategory);
    void setSelectedWeightOnAllScreens(String item);

    void increaseRoundNumber(int roundNumber);

    void decreaseRoundNumber(int roundNumber);
    void resetAll();
    void selectRequiredButton1(Color color);
    void selectRequiredButton2(Color color);

    void setSelectedRedPlayerOnAllScreens(String item);
    void setSelectedBluePlayerOnAllScreens(String item);

    void showClientDisconnected(int deviceNumber);
}
