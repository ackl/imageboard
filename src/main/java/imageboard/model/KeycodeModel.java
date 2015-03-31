package imageboard.model;

public class KeycodeModel {

    private String keycode;
    private boolean valid;
    private long expiry;

    public KeycodeModel() {}

    public String getKeycode() {
        return keycode;
    } public void setKeycode(String keycode) {
        this.keycode = keycode;
    }

    public boolean getValid() {
        return valid;
    } public void setValid(boolean valid) {
        this.valid = valid;
    }

    public long getExpiry() {
        return expiry;
    } public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

}
