package app.ui.screens.fighterAdministration.toolbarActions;

import app.ui.screens.fighterAdministration.AdministrationScreen;
import app.util.ImageFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Mihailo on 1/29/2017.
 */
public class ModifyFighterAction extends AbstractAction {

    private AdministrationScreen administrationScreen;

    public ModifyFighterAction(AdministrationScreen administrationScreen){
        super("Modify"/*, ImageFactory.getInstance().getImage(ImageFactory.ImageType.EDIT_ICON)*/);
        this.administrationScreen = administrationScreen;
        putValue(SHORT_DESCRIPTION,"Modify selected fighter");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        administrationScreen.updateFighter();
    }
}
