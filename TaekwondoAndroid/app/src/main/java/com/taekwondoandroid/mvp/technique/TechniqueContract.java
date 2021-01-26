package com.taekwondoandroid.mvp.technique;

/**
 * Created by Mihailo on 1/4/2018.
 */

public class TechniqueContract {

    public interface View{

        void errorOccured(String message);

        void increaseRedScoreCommand(int value);

        void increaseBlueScoreCommand(int value);

        void showPauseTabletDialog(boolean isTimerStarted);

        void resetAll();

        void initScores(int redScore, int blueScore);
    }

    public interface Presenter{

        void increaseBlueScore(int blueScore);

        void increaseRedScore(int redScore);

        void getScores();
    }
}
