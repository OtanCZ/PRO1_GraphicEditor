package component;

public enum Components {
    SQUARE("■"),
    RECTANGLE("▬"),
    CIRCLE("○"),
    TRIANGLE("▲"),
    LINE("|"),
    TEXT("A");

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
