package app.ui.screens.components;

import app.backend.LogicBroker;
import app.ui.screens.components.dialogs.SetRoundTimeDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TimerComponent extends JPanel {

    private final int panelWidth;
    private final int panelHeight;
    private boolean isTv;


    private JLabel lblTime;
    private JButton btnStart;
    private JButton btnPause;

    public TimerComponent(int panelWidth, int panelHeight, boolean isTv) {
        super();
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.isTv = isTv;
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(this.panelWidth, this.panelHeight));
        this.setLayout((isTv)? new GridLayout(1,1): new GridLayout(2,0));
        initGui();
        initListeners();
    }

    private void initGui() {

        lblTime = new JLabel("01:20", SwingConstants.CENTER);
        lblTime.setFont((isTv)? new Font(Font.SANS_SERIF, Font.PLAIN, 80) : new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        lblTime.setForeground(Color.BLACK);
        this.add(lblTime);

        btnStart = new JButton("Start");
        btnPause = new JButton("Pause");

        if(!isTv){
            JPanel buttonPanel = new JPanel(new GridLayout(0,2));
            buttonPanel.add(btnStart);
            buttonPanel.add(btnPause);
            this.add(buttonPanel);
        }

        btnStart.setVisible(!isTv);
        btnPause.setVisible(!isTv);
    }

    private void initListeners() {
        btnStart.addActionListener(e -> {
            LogicBroker.getInstance().startTimer();
        });

        btnPause.addActionListener(e -> {
            LogicBroker.getInstance().pauseTimer();
        });
        lblTime.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SetRoundTimeDialog dialog = new SetRoundTimeDialog();
                dialog.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void showTimerText(int time) {
        String timeText = formatSecondsIntoText(time);
        lblTime.setText(timeText);
    }

    private String formatSecondsIntoText(int time) {
        String display = String.format("%d:%02d", time / 60, time % 60);
        return display;
    }


}
