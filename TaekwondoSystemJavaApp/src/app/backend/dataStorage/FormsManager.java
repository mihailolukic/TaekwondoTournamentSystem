package app.backend.dataStorage;

import java.util.ArrayList;

public class FormsManager {

    private ArrayList<String> names = new ArrayList<>();

    public FormsManager() {
        this.names = new ArrayList<>();
        names.add("");
        names.add("CHON-JI");
        names.add("DAN-GUN");
        names.add("DO-SAN");
        names.add("WON-HYO");
        names.add("YUL-GOK");
        names.add("JOON-GUN");
        names.add("TOI GYE");
        names.add("HWA RANG");
        names.add("CHOONG MOO");
        names.add("KWAN GAE");
        names.add("PO EUN");
        names.add("GE BAEK");
        names.add("EVI-AM");
        names.add("CHOONG-JANG");
        names.add("JUCHE");
        names.add("SAM IL");
        names.add("YO SIN");
        names.add("CHOI YONG");
        names.add("UI-JI");
        names.add("SE JON");
        names.add("MOON MOO");
        names.add("SO SAN");
        names.add("YON GAE");
        names.add("TONG-IL");
    }

    public ArrayList<String> getNames() {
        return names;
    }
}
