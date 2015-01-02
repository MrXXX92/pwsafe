package teamschwarz.pwsafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

//import android.support.v4.app.NavUtils;


public class PasswordlistActivity extends Activity {

    List<PasswordItem> passwords = new ArrayList<PasswordItem>();
    ListView passwordsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordlist);

        //Aufruf verarbeiten
        Intent intent = getIntent();
        String mpw = intent.getStringExtra(MainActivity.CURRENT_MPW);
        // hier kann man dann was mit dem eingegebenen MasterPasswort machen

        // Zurück-Button aktivieren
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Liste der Passwörter anzeigen
        passwordsListView = (ListView) findViewById(R.id.listViewPasswords);

        populatePasswordList();
    }

    private void populatePasswordList() {
        ArrayAdapter<PasswordItem> PasswordAdapter = new PasswordListAdapter();
        passwordsListView.setAdapter(PasswordAdapter);
    }

    private class PasswordListAdapter extends ArrayAdapter<PasswordItem> {
        public PasswordListAdapter() {
            super(PasswordlistActivity.this, R.layout.password_item, passwords);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.password_item, parent, false);

            PasswordItem currentPassword = passwords.get(position);

            TextView description = (TextView) view.findViewById(R.id.editTextItemDescription);
            description.setText(currentPassword.getDescription());
            TextView password = (TextView) view.findViewById(R.id.editTextItemPassword);
            password.setText(currentPassword.getPassword());

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Das Menü mit New & Settings einblenden
        getMenuInflater().inflate(R.menu.menu_passwordlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_new:
                openNew();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;

            // Respond to the action bar's Up/Home button
            //TODO Up-Button funktioniert nicht
            //case android.R.id.home:
            //NavUtils.navigateUpFromSameTask(this);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openNew() {
        //neue Aktivität zum Hinzufügen eines PWs öffnen
        Intent intent = new Intent(this, NewPasswordActivity.class);
        startActivity(intent);
    }
}