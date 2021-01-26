package app.ui.screens.components.dialogs;


import app.backend.LogicBroker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Mihailo on 2/9/2017.
 */
public class SetRoundTimeDialog extends JDialog {

    private JLabel lblChangeTime;
    private JComboBox<String> cbRoundTimes;
    private JLabel lblChangeTimeout;
    private JComboBox<String> cbTimeoutTimes;
    private JButton btnOk;
    private JButton btnCancel;

    public SetRoundTimeDialog() {
        super();
        this.setModal(true);
        setTitle("Change round time");
        initConfiguration();
        initComponents();
        initGui();
        initTimeValues();
        initListeners();
    }

    private void initListeners() {
        btnCancel.addActionListener(e -> {
            dispose();
        });

        btnOk.addActionListener(e -> {
            if (cbRoundTimes.getSelectedItem() != null) {
                int time = 0;
                switch (cbRoundTimes.getSelectedItem().toString()) {
                    case "00:30":
                        time = 30;
                        break;
                    case "01:00":
                        time = 60;
                        break;
                    case "01:30":
                        time = 90;
                        break;
                    case "02:00":
                        time = 120;
                        break;
                    case "05:00":
                        time = 300;
                        break;
                }
                LogicBroker.getInstance().setTimerInteval(time);
                dispose();
            }
            if (cbTimeoutTimes.getSelectedItem() != null) {
                int time = 0;
                switch (cbTimeoutTimes.getSelectedItem().toString()) {
                    case "00:30":
                        time = 30;
                        break;
                    case "01:00":
                        time = 60;
                        break;
                }
                LogicBroker.getInstance().setTimeoutInterval(time);
                dispose();
            }
        });
    }

    private void initTimeValues() {
        cbRoundTimes.removeAllItems();
        ArrayList<String> values = LogicBroker.getInstance().getRoundTimes();
        for (String value : values) {
            cbRoundTimes.addItem(value);
        }

        cbTimeoutTimes.removeAllItems();
        ArrayList<String> valuesTimeout = LogicBroker.getInstance().getTimeoutTimes();
        for (String value : valuesTimeout) {
            cbTimeoutTimes.addItem(value);
        }
    }

    private void initComponents() {
        lblChangeTime = new JLabel("Choose round time:");
        cbRoundTimes = new JComboBox<>();
        btnOk = new JButton("OK");
        btnCancel = new JButton("Cancel");
        lblChangeTimeout = new JLabel("Chose timeout time:");
        cbTimeoutTimes = new JComboBox<>();

    }

    private void initGui() {
        JPanel panelRoundTime = new JPanel();
        panelRoundTime.setLayout(new FlowLayout(FlowLayout.LEFT));
        lblChangeTime.setPreferredSize(new Dimension(150, 20));
        panelRoundTime.add(lblChangeTime);
        panelRoundTime.add(new JSeparator());
        cbRoundTimes.setPreferredSize(new Dimension(200, 20));
        panelRoundTime.add(cbRoundTimes);
        this.add(panelRoundTime);

        JPanel panelTimeoutTime = new JPanel();
        panelTimeoutTime.setLayout(new FlowLayout(FlowLayout.LEFT));
        lblChangeTimeout.setPreferredSize(new Dimension(150, 20));
        panelTimeoutTime.add(lblChangeTimeout);
        panelTimeoutTime.add(new JSeparator());
        cbTimeoutTimes.setPreferredSize(new Dimension(200, 20));
        panelTimeoutTime.add(cbTimeoutTimes);
        this.add(panelTimeoutTime);

        this.add(new JLabel());

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnOk.setPreferredSize(new Dimension(100, 30));
        btnOk.setMaximumSize(btnOk.getPreferredSize());
        btnCancel.setPreferredSize(new Dimension(100, 30));
        btnCancel.setMaximumSize(btnOk.getPreferredSize());

        buttonsPanel.add(btnOk);
        buttonsPanel.add(new JSeparator());
        buttonsPanel.add(btnCancel);
        this.add(buttonsPanel);
        this.pack();

    }

    private void initConfiguration() {
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new GridLayout(4, 0));
    }
}
