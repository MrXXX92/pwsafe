package teamschwarz.pwsafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NewPasswordActivity extends Activity {

    EditText newDescription;
    EditText newPassword;
    Button saveButton;
    List<PasswordItem> passwords = new ArrayList<PasswordItem>();
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        // Zurück-Button aktivieren
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Aufruf verarbeiten
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String password = intent.getStringExtra("password");
        final int position = intent.getIntExtra("position", -1);

        //Titel je nach Aufruf setzen (Neu oder Detail-Ansicht)
        TextView titleView = (TextView) findViewById(R.id.textViewDetailTitle);
        titleView.setText(title);

        newDescription = (EditText) findViewById(R.id.editTextNewDescription);
        //falls eine Beschreibung übergeben wurde, diese auch anzeigen
        if (description != null && !description.isEmpty()) {
            newDescription.setText(description);
        }

        newPassword = (EditText) findViewById(R.id.editTextNewPassword);
        //falls ein Passwort übergeben wurde, diese auch anzeigen
        if (password != null && !password.isEmpty()) {
            newPassword.setText(password);
        }

        //Save-Button Detail-Maske initial klickbar
        saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setEnabled(isValidPasswordItem());


        newDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Button nur klickbar, wenn das eingegebene Passwort valide ist
                saveButton.setEnabled(isValidPasswordItem());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setEnabled(isValidPasswordItem());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordItem currentItem = new PasswordItem(newDescription.getText().toString(),
                        newPassword.getText().toString());

                if (position == -1l){
                    //Keine Position übergeben -> neues Passwort
                    // hinzufügen
                    PasswordlistActivity.passwords.add(currentItem);
                    //Felder leeren
                    newDescription.setText("");
                    newPassword.setText("");
                    Toast.makeText(getApplicationContext(), R.string.password_added, Toast.LENGTH_SHORT).show();
                } else {
                    //bestehendes Passwort in der Bearbeitung
                    // eingegebene Werte speichern
                    PasswordItem listItem = PasswordlistActivity.passwords.get(position);
                    listItem.setDescription(currentItem.getDescription());
                    listItem.setPassword(currentItem.getPassword());
                    //Felder leeren
                    newDescription.setText("");
                    newPassword.setText("");
                    Toast.makeText(getApplicationContext(), R.string.password_updated, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidPasswordItem() {
        return (String.valueOf(newDescription.getText()).trim().length() > 0) &&
                (String.valueOf(newPassword.getText()).trim().length() > 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks.
        switch (item.getItemId()) {
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

    private void openSettings() {
        //neue Aktivität für Settings öffnen
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}