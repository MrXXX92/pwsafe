package teamschwarz.pwsafe;

/**
 * Created by Mitsch on 28.11.14.
 */
public class PasswordItem {

    private String _description;
    private String _password;

    public PasswordItem(String description, String password) {
        _description = description;
        _password = password;
    }

    public String getDescription() {
        return _description;
    }

    public String getPassword() {
        return _password;
    }
}