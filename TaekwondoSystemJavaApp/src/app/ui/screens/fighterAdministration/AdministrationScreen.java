package app.ui.screens.fighterAdministration;

import app.backend.LogicBroker;
import app.backend.dataStorage.model.Fighter;
import app.ui.interfaces.AdministrationInteface;
import app.ui.screens.fighterAdministration.dialogs.AddFighterDialog;
import app.ui.screens.fighterAdministration.table.TableModel;
import app.ui.screens.fighterAdministration.toolbarActions.AddFighterAction;
import app.ui.screens.fighterAdministration.toolbarActions.DeleteFighterAction;
import app.ui.screens.fighterAdministration.toolbarActions.ImportExcelAction;
import app.ui.screens.fighterAdministration.toolbarActions.ModifyFighterAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mihailo on 1/29/2017.
 */
public class AdministrationScreen extends JDialog implements AdministrationInteface {

    private JToolBar toolBar;
    private JTable table;
    private TableModel tableModel;

    private ImportExcelAction importExcelAction;
    private AddFighterAction addFighterAction;
//    private ModifyFighterAction modifyFighterAction;
    private DeleteFighterAction deleteFighterAction;

    public AdministrationScreen(String title) {
        super();
        this.setTitle(title);
        LogicBroker.getInstance().subscribeAdministrationScreenView(this);
        initConfiguration();
        initComponents();
        initActions();
        initGUI();

    }

    private void initActions() {
        importExcelAction = new ImportExcelAction(this);
        addFighterAction = new AddFighterAction();
//        modifyFighterAction = new ModifyFighterAction(this);
        deleteFighterAction = new DeleteFighterAction(this);
    }

    private void initComponents() {
        toolBar = new JToolBar();
        tableModel = new TableModel();
        table = new JTable(tableModel);
    }

    private void initGUI() {
        initToolbar();
        initTable();

    }

    private void initTable() {
        table.setBackground(Color.WHITE);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }

    private void initToolbar() {
        toolBar.setFloatable(false);
        toolBar.add(importExcelAction);
        toolBar.addSeparator();
        toolBar.add(addFighterAction);
//        toolBar.addSeparator();
//        toolBar.add(modifyFighterAction);
        toolBar.addSeparator();
        toolBar.add(deleteFighterAction);

        this.add(toolBar, BorderLayout.NORTH);

    }


    private void initConfiguration() {
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

    @Override
    public void refreshTable() {
        tableModel.refreshTable();
    }

    @Override
    public void removeSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1){
            return;
        }
        tableModel.removeSelectedRow(selectedRow);
    }

    @Override
    public void errorOccured(String s) {
        JOptionPane.showMessageDialog(this,s);
    }

    @Override
    public void updateFighter() {
        int selectedRow = table.getSelectedRow();
        Fighter fighter = tableModel.getFighter(selectedRow);
        AddFighterDialog dialog = new AddFighterDialog(fighter);
        dialog.setVisible(true);
    }
}
