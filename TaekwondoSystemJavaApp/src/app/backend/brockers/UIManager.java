package app.backend.brockers;

import app.backend.LogicBroker;
import app.ui.interfaces.ViewInterface;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mihailo on 1/25/2017.
 */
public class UIManager {

    private ArrayList<ViewInterface> viewInterfaces;

    public UIManager() {
        this.viewInterfaces = new ArrayList<>();
    }

    public void subscribeView(ViewInterface screen) {
        viewInterfaces.add(screen);
    }

    public void increaseRedScore(int deviceNumber, int score) {
        for (ViewInterface interf : viewInterfaces) {
            System.out.println("LogicBroker: processTCPMessage: case: " + deviceNumber + " value: " + score);
            interf.increaseRedDeviceScore(deviceNumber, score);
            LogicBroker.getInstance().updateTotalScore(interf);
        }
    }

    public void increaseBlueScore(int deviceNumber, int score) {
        for (ViewInterface interf : viewInterfaces) {
            System.out.println("LogicBroker: processTCPMessage: case: " + deviceNumber + " value: " + score);
            interf.increaseBlueDeviceScore(deviceNumber, score);
            LogicBroker.getInstance().updateTotalScore(interf);
        }
    }

    public void setCardsNumber(int typeOfPlayer, int numberCards) {
        for (ViewInterface interf : viewInterfaces) {
            interf.setCardsNumber(typeOfPlayer, numberCards);
        }
    }

    public void setWarningsNumber(int typeOfPlayer, int warningsForRedPlayer) {
        for (ViewInterface interf : viewInterfaces) {
            interf.setWarningsNumber(typeOfPlayer, warningsForRedPlayer);
        }
    }

    public void showTimerText(int time) {
        for (ViewInterface interf : viewInterfaces) {
            interf.showTimerText(time);
        }
    }


    public void setWeightCattegories(ArrayList<String> weightCategories) {
        for (ViewInterface interf : viewInterfaces) {
            interf.setWeightCattegories(weightCategories);
        }
    }

    public void setSelectedWeightCategory(String weightCategory) {
        for (ViewInterface interf : viewInterfaces) {
            interf.setAgeCategoryOnEveryScreen(weightCategory);
        }
    }

    public void setAgeCategoryOnEveryScreen(String ageCategory) {
        for (ViewInterface interf : viewInterfaces) {
            interf.setAgeCategoryOnEveryScreen(ageCategory);
        }
    }

    public void setSelectedWeightOnAllScreens(String item) {
        for (ViewInterface interf : viewInterfaces) {
            interf.setSelectedWeightOnAllScreens(item);
        }
    }


    public void increaseRoundNumber(int roundNumber) {
        for (ViewInterface interf : viewInterfaces) {
            interf.increaseRoundNumber(roundNumber);
        }
    }

    public void decreaseRoundNumber(int roundNumber) {
        for (ViewInterface interf : viewInterfaces) {
            interf.decreaseRoundNumber(roundNumber);
        }
    }

    public void resetAll(){
        for (ViewInterface interf : viewInterfaces) {
            interf.resetAll();
        }
    }

    public void selectRequiredButton1(Color color) {
        for (ViewInterface interf : viewInterfaces) {
            interf.selectRequiredButton1(color);
        }
    }

    public void selectRequiredButton2(Color color) {
        for (ViewInterface interf : viewInterfaces) {
            interf.selectRequiredButton2(color);
        }
    }

    public void setSelectedRedPlayerOnAllScreens(String item) {
        for (ViewInterface interf : viewInterfaces) {
            interf.setSelectedRedPlayerOnAllScreens(item);
        }
    }

    public void setSelectedFormForRed(String item) {
        for (ViewInterface interf : viewInterfaces) {
            interf.setSelectedRedPlayerOnAllScreens(item);
        }
    }

    public void setSelectedFormForBlue(String item) {
        for (ViewInterface interf : viewInterfaces) {
            interf.setSelectedBluePlayerOnAllScreens(item);
        }
    }

    public void setSelectedBluePlayerOnAllScreens(String item) {
        for (ViewInterface interf : viewInterfaces) {
            interf.setSelectedBluePlayerOnAllScreens(item);
        }
    }

    public void showClientDisconnected(int deviceNumber) {
        for (ViewInterface interf : viewInterfaces) {
            interf.showClientDisconnected(deviceNumber);
        }
    }


}
