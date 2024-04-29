package component;

public enum Components {
    //SQUARE("■"),
    RECTANGLE("▬"),
    CIRCLE("○"),
    TRIANGLE("▲"),
    LINE("|");

    String title;

    Components(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
