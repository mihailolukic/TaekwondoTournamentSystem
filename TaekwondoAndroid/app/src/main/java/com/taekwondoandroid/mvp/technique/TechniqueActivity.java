package com.taekwondoandroid.mvp.technique;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.taekwondoandroid.R;
import com.taekwondoandroid.mvp.configuration.ConfigurationActivity_;
import com.taekwondoandroid.mvp.parentClasses.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Mihailo on 1/4/2018.
 */
@EActivity(R.layout.technique_activity_layout)
public class TechniqueActivity extends BaseActivity implements TechniqueContract.View {

    private TechniquePresenter presenter;

    @ViewById
    Button btnTechnicalContent;
    @ViewById
    Button btnPower;
    @ViewById
    Button btnBalance;
    @ViewById
    Button btnBreatheControl;
    @ViewById
    Button btnRhytm;

    @ViewById
    TextView lblRedScore;
    @ViewById
    TextView lblBlueScore;

    @ViewById
    TextView lblScoreLeft;
    @ViewById
    TextView lblScoreRight;

    public final int TECHNICAL_CONTENT_MODE = 0;
    public final int POWER_MODE = 1;
    public final int BALANCE_MODE = 2;
    public final int BREATHE_CONTROL_MODE = 3;
    public final int RYTHM_MODE = 4;

    private final int TECHNIQUE_LIMIT = 10;
    private final int OTHER_LIMIT = 6;
    private final int ZERO_LIMIT = 0;

    private int selectedMode = TECHNICAL_CONTENT_MODE;

    private int technicalContentRedScore = 0;
    private int technicalContentBlueScore = 0;

    private int powerRedScore = 0;
    private int powerBlueScore = 0;

    private int balanceRedScore = 0;
    private int balanceBlueScore = 0;

    private int breatheControlRedScore = 0;
    private int breatheControlBlueScore = 0;

    private int rythmRedScore = 0;
    private int rythmBlueScore = 0;

    private ProgressDialog progressDialog;
    private int recentRedScore = 0;
    private int totalRedScore = 0;
    private int recentBlueScore = 0;
    private int totalBlueScore = 0;


    @AfterViews
    @Override
    protected void initView() {
        presenter = new TechniquePresenter(getApplicationContext());
        initPresenter_(presenter);
        presenter.setView(this);
        clearValues();
        showPauseTabletDialog(false);
        selectedMode = TECHNICAL_CONTENT_MODE;
        selectProperButton();
        presenter.getScores();
    }

    public void showPauseTabletDialog(boolean isTimerStarted) {
        if (!isTimerStarted) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(this, null, "Timer is paused. Please wait", true);
            }
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    @Override
    public void resetAll() {
        totalBlueScore = 0;
        totalRedScore = 0;
        lblScoreLeft.setText(String.valueOf(totalRedScore));
        lblScoreRight.setText(String.valueOf(totalBlueScore));
        clearValues();
        showPauseTabletDialog(false);
    }

    @Override
    public void initScores(int redScore, int blueScore) {
        totalBlueScore = blueScore;
        totalRedScore = redScore;
        lblScoreLeft.setText(String.valueOf(totalRedScore));
        lblScoreRight.setText(String.valueOf(totalBlueScore));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setMessage(getString(R.string.closing_app_message));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Click
    void btnRhytm() {
        selectedMode = RYTHM_MODE;
        selectProperButton();
        refreshRedScoreText();
        refreshBlueScoreText();
    }


    @Click
    void btnBreatheControl() {
        selectedMode = BREATHE_CONTROL_MODE;
        selectProperButton();
        refreshRedScoreText();
        refreshBlueScoreText();
    }


    @Click
    void btnBalance() {
        selectedMode = BALANCE_MODE;
        selectProperButton();
        refreshRedScoreText();
        refreshBlueScoreText();
    }

    @Click
    void btnPower() {
        selectedMode = POWER_MODE;
        selectProperButton();
        refreshRedScoreText();
        refreshBlueScoreText();
    }

    @Click
    void btnTechnicalContent() {
        selectedMode = TECHNICAL_CONTENT_MODE;
        selectProperButton();
        refreshRedScoreText();
        refreshBlueScoreText();
    }

    @Click
    void btnOptional() {

    }

    @Click
    void btnDesignated() {

    }

    @Click
    void btnSendLeft() {
        sendScore();
    }

    @Click
    void btnSendRight() {
        sendScore();
    }


    @Click
    void btnRedDecrease() {
        changeRedScore(-1);
    }


    @Click
    void btnRedIncrease() {
        changeRedScore(1);
    }

    @Click
    void btnBlueDecrease() {
        changeBlueScore(-1);
    }

    @Click
    void btnBlueIncrease() {
        changeBlueScore(1);
    }

    private void changeBlueScore(int i) {
        switch (selectedMode) {
            case TECHNICAL_CONTENT_MODE:
                if (technicalContentBlueScore >= ZERO_LIMIT && technicalContentBlueScore < TECHNIQUE_LIMIT) {
                    technicalContentBlueScore += i;
                    if (technicalContentBlueScore < 0) {
                        technicalContentBlueScore = 0;
                    }
                }
                break;
            case RYTHM_MODE:
                if (rythmBlueScore >= ZERO_LIMIT && rythmBlueScore < OTHER_LIMIT) {
                    rythmBlueScore += i;
                    if (rythmBlueScore < 0) {
                        rythmBlueScore = 0;
                    }
                }
                break;
            case BALANCE_MODE:
                if (balanceBlueScore >= ZERO_LIMIT && balanceBlueScore < OTHER_LIMIT) {
                    balanceBlueScore += i;
                    if (balanceBlueScore < 0) {
                        balanceBlueScore = 0;
                    }
                }
                break;
            case BREATHE_CONTROL_MODE:
                if (breatheControlBlueScore >= ZERO_LIMIT && breatheControlBlueScore < OTHER_LIMIT) {
                    breatheControlBlueScore += i;
                    if (breatheControlBlueScore < 0) {
                        breatheControlBlueScore = 0;
                    }
                }
                break;
            case POWER_MODE:
                if (powerBlueScore >= ZERO_LIMIT && powerBlueScore < OTHER_LIMIT) {
                    powerBlueScore += i;
                    if (powerBlueScore < 0) {
                        powerBlueScore = 0;
                    }
                }
                break;
        }
        refreshBlueScoreText();
        refreshBlueRecentScore();
    }

    private void changeRedScore(int i) {
        switch (selectedMode) {
            case TECHNICAL_CONTENT_MODE:
                if (technicalContentRedScore >= ZERO_LIMIT && technicalContentBlueScore < TECHNIQUE_LIMIT) {
                    technicalContentRedScore += i;
                    if (technicalContentRedScore < 0) {
                        technicalContentRedScore = 0;
                    }
                }
                break;
            case RYTHM_MODE:
                if (rythmRedScore >= ZERO_LIMIT && rythmRedScore < OTHER_LIMIT) {
                    rythmRedScore += i;
                    if (rythmRedScore < 0) {
                        rythmRedScore = 0;
                    }
                }
                break;
            case BALANCE_MODE:
                if (balanceRedScore >= ZERO_LIMIT && balanceRedScore < OTHER_LIMIT) {
                    balanceRedScore += i;
                    if (balanceRedScore < 0) {
                        balanceRedScore = 0;
                    }
                }
                break;
            case BREATHE_CONTROL_MODE:
                if (breatheControlRedScore >= ZERO_LIMIT && breatheControlRedScore < OTHER_LIMIT) {
                    breatheControlRedScore += i;
                    if (breatheControlRedScore < 0) {
                        breatheControlRedScore = 0;
                    }
                }
                break;
            case POWER_MODE:
                if (powerRedScore >= ZERO_LIMIT && powerRedScore < OTHER_LIMIT) {
                    powerRedScore += i;
                    if (powerRedScore < 0) {
                        powerRedScore = 0;
                    }
                }
                break;
        }
        refreshRedScoreText();
        refreshRedRecentScore();
    }

    private void refreshRedRecentScore() {
        recentRedScore = technicalContentRedScore + rythmRedScore + balanceRedScore + breatheControlRedScore + powerRedScore;
    }

    private void refreshBlueRecentScore() {
        recentBlueScore = technicalContentBlueScore + rythmBlueScore + balanceBlueScore + breatheControlBlueScore + powerBlueScore;

    }

    private void refreshRedScoreText() {
        switch (selectedMode) {
            case TECHNICAL_CONTENT_MODE:
                lblRedScore.setText(String.valueOf(technicalContentRedScore));
                break;
            case RYTHM_MODE:
                lblRedScore.setText(String.valueOf(rythmRedScore));
                break;
            case BALANCE_MODE:
                lblRedScore.setText(String.valueOf(balanceRedScore));
                break;
            case BREATHE_CONTROL_MODE:
                lblRedScore.setText(String.valueOf(breatheControlRedScore));
                break;
            case POWER_MODE:
                lblRedScore.setText(String.valueOf(powerRedScore));
                break;
        }
    }

    private void refreshBlueScoreText() {
        switch (selectedMode) {
            case TECHNICAL_CONTENT_MODE:
                lblBlueScore.setText(String.valueOf(technicalContentBlueScore));
                break;
            case RYTHM_MODE:
                lblBlueScore.setText(String.valueOf(rythmBlueScore));
                break;
            case BALANCE_MODE:
                lblBlueScore.setText(String.valueOf(balanceBlueScore));
                break;
            case BREATHE_CONTROL_MODE:
                lblBlueScore.setText(String.valueOf(breatheControlBlueScore));
                break;
            case POWER_MODE:
                lblBlueScore.setText(String.valueOf(powerBlueScore));
                break;
        }
    }


    private void sendScore() {
        totalRedScore += recentRedScore;
        totalBlueScore += recentBlueScore;
        lblScoreLeft.setText(String.valueOf(totalRedScore));
        lblScoreRight.setText(String.valueOf(totalBlueScore));
        presenter.increaseBlueScore(recentBlueScore);
        presenter.increaseRedScore(recentRedScore);
        clearValues();
    }

    private void clearValues() {

        recentBlueScore = 0;
        recentRedScore = 0;

        technicalContentRedScore = 0;
        technicalContentBlueScore = 0;

        powerRedScore = 0;
        powerBlueScore = 0;

        balanceRedScore = 0;
        balanceBlueScore = 0;

        breatheControlRedScore = 0;
        breatheControlBlueScore = 0;

        rythmRedScore = 0;
        rythmBlueScore = 0;

        refreshBlueScoreText();
        refreshRedScoreText();
        refreshBlueRecentScore();
        refreshRedRecentScore();
    }


    private void selectProperButton() {
        btnBalance.setSelected(selectedMode == BALANCE_MODE);
        btnTechnicalContent.setSelected(selectedMode == TECHNICAL_CONTENT_MODE);
        btnPower.setSelected(selectedMode == POWER_MODE);
        btnBreatheControl.setSelected(selectedMode == BREATHE_CONTROL_MODE);
        btnRhytm.setSelected(selectedMode == RYTHM_MODE);
    }

    @Override
    public void errorOccured(String message) {
        if (!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.error));
            builder.setMessage(message);
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ConfigurationActivity_.intent(TechniqueActivity.this).start();
                    dialogInterface.dismiss();
                    TechniqueActivity.this.finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void increaseRedScoreCommand(int value) {
        Log.d("MISA", "red score: " + value);
    }

    @Override
    public void increaseBlueScoreCommand(int value) {
        Log.d("MISA", "blue score: " + value);
    }
}
