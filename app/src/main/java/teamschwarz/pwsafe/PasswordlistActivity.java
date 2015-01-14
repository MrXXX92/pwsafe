package teamschwarz.pwsafe;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class PasswordlistActivity extends ListActivity {

    static List<PasswordItem> passwords = new ArrayList<PasswordItem>();
    ListView passwordsListView;
    private String mpw;

    public String getMpw(){
        return mpw;
    }

    public void setMpw(final String mpw){
        this.mpw = mpw;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordlist);

        //Aufruf verarbeiten
        Intent intent = getIntent();
        setMpw(intent.getStringExtra("CURRENT_MPW"));
        // TODO hier kann man dann was mit dem eingegebenen MasterPasswort machen

        // Zurück-Button aktivieren
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Liste der Passwörter anzeigen
        passwordsListView = (ListView) findViewById(android.R.id.list);

        populatePasswordList();
    }

    private void populatePasswordList() {
        ArrayAdapter<PasswordItem> PasswordAdapter = new PasswordListAdapter();
        passwordsListView.setAdapter(PasswordAdapter);

        //initial Testdaten einfügen
        if (passwords.isEmpty()) {
            passwords.add(new PasswordItem("testPW1", "DummyUser1", "pw1"));
            passwords.add(new PasswordItem("testPW2", "DummyUser2", "pw2"));
            passwords.add(new PasswordItem("testPW3", "DummyUser3", "pw3"));
        }
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

            TextView description = (TextView) view.findViewById(R.id.textViewItemDescription);
            description.setText(currentPassword.getDescription());

            return view;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        openDetail(position);
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
                openSettings();
                return true;

            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                NavUtils.navigateUpTo(this, upIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openDetail(int position) {
        //neue Aktivität zur Ansicht eines PWs öffnen
        Intent intent = new Intent(this, NewPasswordActivity.class);
        PasswordItem pwClicked = passwords.get(position);
        intent.putExtra("title", "Detail");
        intent.putExtra("description", pwClicked.getDescription());
        intent.putExtra("username", pwClicked.getUsername());
        intent.putExtra("password", pwClicked.getPassword());
        intent.putExtra("position", position);
        intent.putExtra("mpw", getMpw());
        startActivity(intent);
    }

    private void openNew() {
        //neue Aktivität zum Hinzufügen eines PWs öffnen
        Intent intent = new Intent(this, NewPasswordActivity.class);
        intent.putExtra("title", "New Password");
        intent.putExtra("mpw", getMpw());
        startActivity(intent);
    }

    private void openSettings() {
        //neue Aktivität für Settings öffnen
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}