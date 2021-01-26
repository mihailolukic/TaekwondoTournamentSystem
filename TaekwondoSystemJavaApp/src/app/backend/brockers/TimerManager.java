package app.backend.brockers;

import app.backend.LogicBroker;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mihailo on 1/26/2017.
 */
public class TimerManager {

    private ArrayList<String> roundTimes = new ArrayList<>();
    private ArrayList<String> timeoutTimes = new ArrayList<>();
    private boolean timerStarted = false;
    private boolean timeOutStarted = false;

    private final int DELAY = 1000;
    private final int PERIOD = 1000;
    private int interval = 0;
    private int mainTimerInterval = 0;
    private int timeOutTimeInterval = 0;
    private int maintimeOutTimeInterval = 30;
    private Timer timer;
    private Timer timerTimeOut;

    public TimerManager() {
        timer = new Timer();
        roundTimes.add("00:30");
        roundTimes.add("01:00");
        roundTimes.add("01:30");
        roundTimes.add("02:00");
        roundTimes.add("05:00");

        timeoutTimes.add("00:30");
        timeoutTimes.add("01:00");
    }

    public void startTimer() {
        if (isTimeOutStarted() || isTimerStarted()) {
            return;
        }
        timer = new Timer();
        //interval = mainTimerInterval;
        setTimerStarted(true);
        setTimeOutStarted(false);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                if (interval == 0) {
                    timer.cancel();
                    interval = mainTimerInterval;
                    setTimerStarted(false);
                    LogicBroker.getInstance().showTimerText(mainTimerInterval);
                } else {
                    LogicBroker.getInstance().showTimerText(--interval);
                }
            }
        }, DELAY, PERIOD);

    }

    public void startTimeOut() {
        if (isTimeOutStarted() || isTimerStarted()) {
            return;
        }
        timeOutTimeInterval = maintimeOutTimeInterval;
        setTimerStarted(false);
        setTimeOutStarted(true);
        timerTimeOut = new Timer();
        timerTimeOut.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                if (timeOutTimeInterval == 0) {
                    timerTimeOut.cancel();
                    timeOutTimeInterval = maintimeOutTimeInterval;
                    setTimeOutStarted(false);
                    LogicBroker.getInstance().showTimerText(mainTimerInterval);
                } else {
                    LogicBroker.getInstance().showTimerText(--timeOutTimeInterval);
                }
            }
        }, DELAY, PERIOD);
    }


    public void pauseTimer() {
        if (isTimeOutStarted()) {
            return;
        }
        setTimerStarted(false);
        timer.cancel();
    }


    public void setInterval(int interval) {
        if (timer != null) {
            timer.cancel();
            setTimerStarted(false);
        }
        this.interval = interval;
        this.mainTimerInterval = interval;
    }

    public ArrayList<String> getRoundTimes() {
        return roundTimes;
    }

    public boolean isTimerStarted() {
        return timerStarted;
    }

    private void setTimerStarted(boolean timerStarted) {
        this.timerStarted = timerStarted;
        LogicBroker.getInstance().pauseAllTablets(timerStarted);
    }

    public boolean isTimeOutStarted() {
        return timeOutStarted;
    }

    public void setTimeOutStarted(boolean timeOutStarted) {
        this.timeOutStarted = timeOutStarted;
    }

    public ArrayList<String> getTimeoutTimes() {
        return timeoutTimes;
    }

    public void setTimeoutInterval(int interval) {
        if (timerTimeOut != null) {
            timerTimeOut.cancel();
            setTimeOutStarted(false);
        }
        this.timeOutTimeInterval = interval;
        this.maintimeOutTimeInterval = interval;
    }

    public void resetTimer() {
        if (isTimeOutStarted()) {
            return;
        }
        timer.cancel();
        setTimerStarted(false);
        this.interval = this.mainTimerInterval;
        LogicBroker.getInstance().showTimerText(mainTimerInterval);
    }

}
