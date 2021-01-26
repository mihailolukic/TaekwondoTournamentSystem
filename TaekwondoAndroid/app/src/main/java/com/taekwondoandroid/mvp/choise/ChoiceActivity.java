package com.taekwondoandroid.mvp.choise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.TextView;

import com.taekwondoandroid.R;
import com.taekwondoandroid.mvp.configuration.ConfigurationActivity_;
import com.taekwondoandroid.mvp.parentClasses.BaseActivity;
import com.taekwondoandroid.mvp.scoring.ScoringActivity;
import com.taekwondoandroid.mvp.scoring.ScoringActivity_;
import com.taekwondoandroid.mvp.technique.TechniqueActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Mihailo on 1/10/2018.
 */
@EActivity(R.layout.choise_activity_layout)
public class ChoiceActivity extends BaseActivity implements ChoiceContract.View {

    private ChoicePresenter presenter;

    @ViewById
    TextView judgeNumber;

    @AfterViews
    @Override
    protected void initView() {
        presenter = new ChoicePresenter(getApplicationContext());
        initPresenter_(presenter);
        presenter.setView(this);
        presenter.initTCP();
        judgeNumber.setText("JUDGE " + (presenter.getDeviceIndex() + 1));

    }


    @Click
    public void btnFight() {
        ScoringActivity_.intent(this).start();
    }


    @Click
    public void btnTechnique() {
        TechniqueActivity_.intent(this).start();
    }

    @Override
    public void errorOccured(String message) {
        if(!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.error));
            builder.setMessage(message);
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ConfigurationActivity_.intent(ChoiceActivity.this).start();
                    dialogInterface.dismiss();
                    ChoiceActivity.this.finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
