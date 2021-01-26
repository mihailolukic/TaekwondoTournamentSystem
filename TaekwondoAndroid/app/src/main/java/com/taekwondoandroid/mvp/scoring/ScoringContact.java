package com.taekwondoandroid.mvp.scoring;

/**
 * Created by Westi on 12/7/2016.
 */

public class ScoringContact {

    interface View{
        void errorOccured(String message);
        void increaseBlueScoreCommand(int value);
        void increaseRedScoreCommand(int value);
        void resetAll();
        void showPauseTabletDialog(boolean isTimerStarted);

        void undoBlueScoreCommand(Integer value);
        void undoRedScoreCommand(Integer value);
        void initScore(int redScore, int blueScore);
    }

    interface Presenter{

        void increaseBlueScore(int newScore);
        void increaseRedScore(int newScore);
        void undo();
        void getScores();
    }
}
