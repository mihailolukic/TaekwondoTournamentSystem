package app.ui.screens.fighterAdministration.toolbarActions;

import app.backend.LogicBroker;
import app.ui.screens.fighterAdministration.AdministrationScreen;
import app.util.ImageFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by Mihailo on 1/29/2017.
 */
public class ImportExcelAction extends AbstractAction {

    private AdministrationScreen administrationScreen;

    public ImportExcelAction(AdministrationScreen administrationScreen) {
        super("Import"/*, ImageFactory.getInstance().getImage(ImageFactory.ImageType.IMPORT_ICON)*/);
        this.administrationScreen = administrationScreen;
        putValue(SHORT_DESCRIPTION,"Import from excel file");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls","xlsx");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(administrationScreen);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            LogicBroker.getInstance().importDataFromExcel(selectedFile.getAbsolutePath());
        }

    }
}
