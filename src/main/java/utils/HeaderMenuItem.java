package utils;

public enum HeaderMenuItem {
    LOGIN("//a[text()='LOGIN']"),
    ADD ("//a[text()='ADD']"),
    CONTACTS("//a[text()='CONTACTS']"),
    ABOUT("//a[text()='ABOUT']"),
    HOME("//a[text()='ABOUT']"),
    SIGNOUT("//a[text()='ABOUT']");

        private final String locator;
    HeaderMenuItem(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }
}
