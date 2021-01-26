package app.util;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static app.util.ImageFactory.ImageType.*;

/**
 * Created by mihailol on 9.2.2017.
 */
public class ImageFactory {

    public enum ImageType{
        ADD_ICON, DELETE_ICON, EDIT_ICON, FIGHT_ADMINISTRATION, IMPORT_ICON, PLAY, WIFI_SETTINGS
    }

    private static ImageFactory instance;

    private static HashMap<ImageType, ImageIcon> images = new HashMap<>();

    public static ImageFactory getInstance(){
        if(instance == null){
            instance = new ImageFactory();
        }
        return instance;
    }

    public ImageFactory() {

        try {
            initImages();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initImages() throws IOException {
        images.put(ADD_ICON,new ImageIcon("src/app/util/images/add.png"));
        images.put(DELETE_ICON,new ImageIcon("src/app/util/images/delete.png"));
        images.put(EDIT_ICON,new ImageIcon("src/app/util/images/edit.png"));
        images.put(IMPORT_ICON,new ImageIcon("src/app/util/images/import.png"));
        images.put(FIGHT_ADMINISTRATION,new ImageIcon(ImageIO.read(new File("src/app/util/images/fighter_administration.png"))));
        images.put(PLAY,new ImageIcon(ImageIO.read(new File("src/app/util/images/play.png"))));
        images.put(WIFI_SETTINGS,new ImageIcon(ImageIO.read(new File("src/app/util/images/wifi_settings.png"))));
    }

    public ImageIcon getImage(ImageType type){
        if(images.containsKey(type)){
            return images.get(type);
        }
        return null;
    }


}
