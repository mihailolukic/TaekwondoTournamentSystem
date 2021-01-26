package com.taekwondoandroid.mvp.scoring;

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
 * Created by Westi on 12/7/2016.
 */
@EActivity(R.layout.scoring_activity_layout)
public class ScoringActivity extends BaseActivity implements ScoringContact.View {

    private static final String TAG = ScoringActivity.class.getSimpleName();
    ScoringPresenter presenter;

    @ViewById
    TextView txtRedScore;

    @ViewById
    TextView txtBlueScore;

    @ViewById
    Button btnUndo;

    private ProgressDialog progressDialog;

    private int blueScore = 0;
    private int redScore = 0;

    @AfterViews
    @Override
    protected void initView() {
        presenter = new ScoringPresenter(getApplicationContext());
        initPresenter_(presenter);
        presenter.setView(this);
        initScore(0,0);
        presenter.getScores();
        showPauseTabletDialog(false);
    }

    @Click
    void btnUndo(){
        presenter.undo();
    }

    @Click
    void btnBlue1() {
        increaseBlueScore(1);
    }

    @Click
    void btnBlue2() {
        increaseBlueScore(2);
    }

    @Click
    void btnBlue3() {
        increaseBlueScore(3);
    }

    @Click
    void btnBlue4() {
        increaseBlueScore(4);
    }

    @Click
    void btnBlue5() {
        increaseBlueScore(5);
    }


    @Click
    void btnRed1() {
        increaseRedScore(1);
    }

    @Click
    void btnRed2() {
        increaseRedScore(2);
    }

    @Click
    void btnRed3() {
        increaseRedScore(3);
    }

    @Click
    void btnRed4() {
        increaseRedScore(4);
    }

    @Click
    void btnRed5() {
        increaseRedScore(5);
    }

    private void increaseRedScore(int newScore) {
        redScore += newScore;
        presenter.increaseRedScore(newScore);
        refreshRedScore();
    }


    private void increaseBlueScore(int newScore) {
        blueScore += newScore;
        presenter.increaseBlueScore(newScore);
        refreshBlueScore();
    }

    @Override
    public void increaseBlueScoreCommand(int value) {
        Log.d(TAG, "increaseBlueScoreCommand: " + value);
        increaseBlueScore(value);
    }

    @Override
    public void increaseRedScoreCommand(int value) {
        Log.d(TAG, "increaseRedScoreCommand: " + value);
        increaseRedScore(value);
    }

    private void refreshBlueScore() {
        txtBlueScore.setText(String.valueOf(blueScore));
    }

    private void refreshRedScore() {
        txtRedScore.setText(String.valueOf(redScore));
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

    @Override
    public void errorOccured(String message) {
        if(!isFinishing()) {
            Log.d(TAG, "errorOccured: ");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.error));
            builder.setMessage(message);
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ConfigurationActivity_.intent(ScoringActivity.this).start();
                    dialogInterface.dismiss();
                    ScoringActivity.this.finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    @Override
    public void resetAll() {
        Log.d(TAG, "resetAll: ");
        redScore = 0;
        blueScore = 0;
        refreshBlueScore();
        refreshRedScore();
        showPauseTabletDialog(false);
    }

    @Override
    public void showPauseTabletDialog(boolean isTimerStarted) {
        if(!isTimerStarted){
            if(progressDialog == null){
                progressDialog = ProgressDialog.show(this, null, "Timer is paused. Please wait", true);
            }

        }
        else{
            if(progressDialog!=null){
                progressDialog.dismiss();
                progressDialog = null;
            }
        }

    }

    @Override
    public void undoBlueScoreCommand(Integer value) {
        blueScore += value;
        refreshBlueScore();
    }

    @Override
    public void undoRedScoreCommand(Integer value) {
        redScore += value;
        refreshRedScore();
    }

    @Override
    public void initScore(int redScore, int blueScore) {
        Log.d(TAG, "initScore: redScore" + redScore + " blueScore:" + blueScore);
        this.redScore = redScore;
        this.blueScore = blueScore;
        refreshBlueScore();
        refreshRedScore();
    }

}
