package teamschwarz.pwsafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;


public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Zurück-Button aktivieren
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openMPWPopup(View view) {
        //neue Aktivität für neues MasterPassword öffnen
        Intent intent = new Intent(this, NewMPwActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                NavUtils.navigateUpTo(this, upIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
