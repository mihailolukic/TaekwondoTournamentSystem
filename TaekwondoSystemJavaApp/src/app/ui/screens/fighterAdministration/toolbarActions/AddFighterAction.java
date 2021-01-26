package app.ui.screens.fighterAdministration.toolbarActions;

import app.ui.screens.fighterAdministration.dialogs.AddFighterDialog;
import app.util.ImageFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Mihailo on 1/29/2017.
 */
public class AddFighterAction extends AbstractAction {

    public AddFighterAction() {
        super("Add"/*, ImageFactory.getInstance().getImage(ImageFactory.ImageType.ADD_ICON)*/);
        putValue(SHORT_DESCRIPTION,"Add new fighter");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AddFighterDialog dialog = new AddFighterDialog("Add new fighter");
        dialog.setVisible(true);
    }
}
