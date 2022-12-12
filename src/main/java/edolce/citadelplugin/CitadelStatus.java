package edolce.citadelplugin;


/*
* Enum that provide the info about a STATUS
 */
public enum CitadelStatus {
    LOCKED("&c&lLOCKED", "Starts in:"),OPEN("&a&lOPEN", "Close in:");

    private final String type;
    private final String key;

    CitadelStatus(String type, String key) {
        this.type = type;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }
}
