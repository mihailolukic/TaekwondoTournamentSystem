package app.ui.screens.components;

import app.backend.LogicBroker;
import app.util.Constants;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Westi on 1/17/2017.
 */
public class WarningPanel extends JPanel {

    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 32);
    Border border = BorderFactory.createLineBorder(Color.BLACK);

    private boolean inverse;
    private final boolean showControls;
    private int typeOfPlayer;
    private JLabel numberOfCards;
    private JLabel numberOfWarnings;

    private JButton btnIncreaseCard;
    private JButton btnDecreaseCard;

    private JButton btnIncreaseWarning;
    private JButton btnDecreaseWarning;
    private JPanel buttonsPanelCards;
    private JPanel buttonsPanelWarning;
    JPanel panelWarnings;
    JPanel panelCards;


    public WarningPanel(boolean inverse, int typeOfPlayer, boolean showControls) {
        super();
        this.inverse = inverse;
        this.showControls = showControls;
        this.typeOfPlayer = typeOfPlayer;
        if(typeOfPlayer == Constants.RED_PLAYER){
            this.setBackground(Color.RED);
        }
        else if(typeOfPlayer == Constants.BLUE_PLAYER){
            this.setBackground(Color.BLUE);
        }

        this.setLayout(new GridLayout(0,2));
        createGui();
    }

    private void createGui() {
        if(inverse){
            createWarningPanel();
            createCardPanel();
        }
        else{
            createCardPanel();
            createWarningPanel();
        }
        showButtons();
        initListeners();

    }

    private void showButtons() {
        if(showControls == false){
            btnDecreaseCard.setVisible(false);
            btnIncreaseCard.setVisible(false);
            btnDecreaseWarning.setVisible(false);
            btnIncreaseWarning.setVisible(false);
            buttonsPanelCards.setBorder(null);
            buttonsPanelWarning.setBorder(null);
        }

    }

    private void initListeners() {
        btnDecreaseCard.addActionListener(e -> {
            LogicBroker.getInstance().decreaseYellowCard(this.typeOfPlayer);
        });
        btnIncreaseCard.addActionListener(e -> {
            LogicBroker.getInstance().increaseYellowCard(this.typeOfPlayer);
        });
        btnIncreaseWarning.addActionListener(e -> {
            LogicBroker.getInstance().increaseWarning(this.typeOfPlayer);
        });

        btnDecreaseWarning.addActionListener(e -> {
            LogicBroker.getInstance().decreaseWarning(this.typeOfPlayer);
        });
    }

    private void createWarningPanel() {
        panelWarnings = new JPanel(new GridLayout(3,0));

        JLabel lblWarningTitle = new JLabel("Warning", SwingConstants.CENTER);
        lblWarningTitle.setBorder(border);
        panelWarnings.add(lblWarningTitle);

        JPanel textPanel = new JPanel();
        textPanel.setBorder(border);

        numberOfWarnings = new JLabel("0",SwingConstants.CENTER);
        numberOfWarnings.setFont(font);
        textPanel.add(numberOfWarnings);

        panelWarnings.add(textPanel);

        buttonsPanelWarning = new JPanel(new GridLayout(0,2));
        buttonsPanelWarning.setBorder(border);
        btnIncreaseWarning = new JButton("+");
        btnDecreaseWarning = new JButton("-");
        buttonsPanelWarning.add(btnIncreaseWarning);
        buttonsPanelWarning.add(btnDecreaseWarning);

        if(typeOfPlayer == Constants.BLUE_PLAYER){
            buttonsPanelWarning.setBackground(Color.BLUE);
        }
        else{
            buttonsPanelWarning.setBackground(Color.RED);
        }

        panelWarnings.add(buttonsPanelWarning);

        this.add(panelWarnings);
    }

    private void createCardPanel() {
        panelCards = new JPanel(new GridLayout(3,0));
        JLabel lblCardTitle = new JLabel("Yellow cards", SwingConstants.CENTER);
        lblCardTitle.setBorder(border);
        panelCards.add(lblCardTitle);

        numberOfCards = new JLabel("0",SwingConstants.CENTER);
        numberOfCards.setFont(font);

        JPanel panelText = new JPanel();
        panelText.setBackground(Color.YELLOW);
        panelText.setBorder(border);
        panelText.add(numberOfCards);
        panelCards.add(panelText);

        buttonsPanelCards = new JPanel(new GridLayout(0,2));
        buttonsPanelCards.setBorder(border);
        btnIncreaseCard = new JButton("+");
        btnDecreaseCard = new JButton("-");
        buttonsPanelCards.add(btnIncreaseCard);
        buttonsPanelCards.add(btnDecreaseCard);

        if(typeOfPlayer == Constants.BLUE_PLAYER){
            buttonsPanelCards.setBackground(Color.BLUE);
        }
        else{
            buttonsPanelCards.setBackground(Color.RED);
        }

        panelCards.add(buttonsPanelCards);

        this.add(panelCards);
    }

    public void setCardsNumber(int numberCards) {
        numberOfCards.setText(String.valueOf(numberCards));
    }

    public void setWarningsNumber(int warningsForRedPlayer) {
        numberOfWarnings.setText(String.valueOf(warningsForRedPlayer));
    }

    public void reset() {
        numberOfWarnings.setText(String.valueOf(0));
        numberOfCards.setText(String.valueOf(0));
    }

    public void setPanelVisible(boolean visible){
        panelWarnings.setVisible(visible);
        panelCards.setVisible(visible);
    }
}
