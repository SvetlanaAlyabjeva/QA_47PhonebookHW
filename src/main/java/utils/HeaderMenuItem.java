package utils;

public enum HeaderMenuItem {
    LOGIN("//a[text()='LOGIN']"),
    ADD ("//a[text()='ADD']"),
    CONTACTS("//a[text()='CONTACTS']"),
    ABOUT("//a[text()='ABOUT']"),
    HOME("//a[text()='HOME']"),
    SIGN_OUT("//button[text()='Sign Out']");

        private final String locator;
    HeaderMenuItem(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }
}
