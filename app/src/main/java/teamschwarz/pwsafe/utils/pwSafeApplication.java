package teamschwarz.pwsafe.utils;

import android.app.Application;

/**
 * Created by UNI on 06.02.2015.
 */
public class pwSafeApplication extends Application {

    private String masterpw;

    public String getMasterpw() {
        return masterpw;
    }

    public void setMasterpw(String masterpw) {
        this.masterpw = masterpw;
    }




}
