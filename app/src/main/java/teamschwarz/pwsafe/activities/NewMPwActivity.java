package teamschwarz.pwsafe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import teamschwarz.pwsafe.R;
import teamschwarz.pwsafe.utils.PasswordItem;
import teamschwarz.pwsafe.utils.XMLParser;
import teamschwarz.pwsafe.utils.pwSafeApplication;


public class NewMPwActivity extends Activity {

    public String getMasterPassword() {
        return ((pwSafeApplication)this.getApplication()).getMasterpw();
    }

    public void setMasterPassword(final String master){
        ((pwSafeApplication)this.getApplication()).setMasterpw(master);
    }

    private EditText newMasterPassword;
    private EditText newMasterPasswordRepetition;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mpw);

        // Zurück-Button aktivieren
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Aufruf verarbeiten
        Intent intent = getIntent();

        newMasterPassword = (EditText) findViewById(R.id.editTextNew1);
        newMasterPassword.addTextChangedListener(new TextWatcher() {
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

        newMasterPasswordRepetition = (EditText) findViewById(R.id.editTextNew2);
        newMasterPasswordRepetition.addTextChangedListener(new TextWatcher() {
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

        saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Activity zum Anzeigen der Passwörter öffnen
                Intent intent = new Intent(NewMPwActivity.this, PasswordlistActivity.class);

                //XML-Datei mit dem alten Master Passwort auslesen
                PasswordlistActivity.passwords = XMLParser.readXML(getMasterPassword());

                //Masterpasswort speichern
                setMasterPassword(newMasterPassword.getText().toString());

                //XML-Datei mit dem neuen Master Passwort schreiben
                if (!XMLParser.writeXML(PasswordlistActivity.passwords, getMasterPassword())) {
                    Toast.makeText(getApplicationContext(), "Could not write file to external storage", Toast.LENGTH_SHORT).show();
                }

                newMasterPassword.setText("");
                newMasterPasswordRepetition.setText("");
                Toast.makeText(getApplicationContext(), R.string.mpwUpdated, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });
    }

    private boolean isValidPasswordItem() {
        String new1 = String.valueOf(newMasterPassword.getText());
        String new2 = String.valueOf(newMasterPasswordRepetition.getText());

        //Valide, wenn die beiden Passwörter gleich und nicht leer sind
        return (new1.equals(new2) &&
                (new1.trim().length() > 0));
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
