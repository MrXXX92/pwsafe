package teamschwarz.pwsafe;

/**
 * Created by Mitsch on 28.11.14.
 */
public class PasswordItem {

    private String _description;
    private String _username;
    private String _password;

    public PasswordItem(String description, String username, String password) {
        _description = description;
        _username = username;
        _password = password;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description){
        this._description = description;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password){
        this._password = password;
    }

    public String getUsername() {
        return _username;
    }

    public void setPUsername(String username){
        this._username = username;
    }
}