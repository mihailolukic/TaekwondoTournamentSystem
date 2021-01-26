package app.ui.screens.fighterAdministration.toolbarActions;

import app.ui.screens.fighterAdministration.AdministrationScreen;
import app.util.ImageFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Mihailo on 1/29/2017.
 */
public class DeleteFighterAction extends AbstractAction {

    private AdministrationScreen administrationScreen;

    public DeleteFighterAction(AdministrationScreen administrationScreen) {
        super("Delete"/*, ImageFactory.getInstance().getImage(ImageFactory.ImageType.DELETE_ICON)*/);
        this.administrationScreen = administrationScreen;
        putValue(SHORT_DESCRIPTION,"Delete selected fighter");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        administrationScreen.removeSelectedRow();
    }
}
