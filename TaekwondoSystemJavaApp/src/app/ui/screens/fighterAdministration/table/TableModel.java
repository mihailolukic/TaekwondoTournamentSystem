package app.ui.screens.fighterAdministration.table;

import app.backend.LogicBroker;
import app.backend.dataStorage.model.Fighter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Mihailo on 1/29/2017.
 */
public class TableModel extends AbstractTableModel {

    ArrayList<Fighter> fighters = null;
    private String[] tableHeader =
            {"Time mark","Name and surname", "Country","Club",
            "Choose", "Gender", "Male category", "Female category","Degree",
            "Sparing youth", "Sparing older youth male","Sparing older youth female",
            "Sparing juniors male", "Sparing juniors female","Sparing seniors male",
            "Sparing seniors female", "Degree umpires","Sparing veterans","Email",
            "Phone", "Date of birth"};

    public TableModel() {
        this.fighters = LogicBroker.getInstance().getFighterList();
    }


    @Override
    public int getRowCount() {
        return fighters.size();
    }

    @Override
    public int getColumnCount() {
        return tableHeader.length;
    }

    @Override
    public String getColumnName(int index) {
        return tableHeader[index];
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Fighter fighter = fighters.get(rowIndex);
        switch (columnIndex){
            case 0:
                return fighter.getTimeMark();
            case 1:
                return fighter.getNameAndSurname();
            case 2:
                return fighter.getCountry();
            case 3:
                return fighter.getClub();
            case 4:
                return fighter.getChoose();
            case 5:
                return fighter.getGender();
            case 6:
                return fighter.getMaleCategory();
            case 7:
                return fighter.getFemaleCategory();
            case 8:
                return fighter.getDegree();
            case 9:
                return fighter.getSparingYouth();
            case 10:
                return fighter.getSparingOlderYouthMale();
            case 11:
                return fighter.getSparingOlderYouthFemale();
            case 12:
                return fighter.getSparingJuniorsMale();
            case 13:
                return fighter.getSparingJuniorsFemale();
            case 14:
                return fighter.getSparingSeniorsMale();
            case 15:
                return fighter.getSparingSeniorsFemale();
            case 16:
                return fighter.getDegreeUmpires();
            case 17:
                return fighter.getSparingVeterans();
            case 18:
                return fighter.getEmail();
            case 19:
                return fighter.getPhone();
            case 20:
                return fighter.getDateOfBirth();
        }
        return null;
    }

    public void refreshTable(){
        fighters = LogicBroker.getInstance().getFighterList();
        fireTableDataChanged();
    }

    public void removeSelectedRow(int selectedRow) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete fighter from excel file also ?", "Deleting item from excel file..", dialogButton);

        LogicBroker.getInstance().deleteFighter(selectedRow, dialogResult);
    }

    public Fighter getFighter(int selectedRow) {
        return fighters.get(selectedRow);
    }
}
