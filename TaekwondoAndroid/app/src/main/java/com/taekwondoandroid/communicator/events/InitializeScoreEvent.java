package com.taekwondoandroid.communicator.events;

/**
 * Created by Mihailo on 2/22/2017.
 */
public class InitializeScoreEvent extends BaseEvent {
    private final int redScore;
    private final int blueScore;

    public InitializeScoreEvent(int redScore, int blueScore) {
        super(EventType.INITIALIZE_SCORE);
        this.redScore = redScore;
        this.blueScore = blueScore;
    }

    public int getRedScore() {
        return redScore;
    }

    public int getBlueScore() {
        return blueScore;
    }
}
