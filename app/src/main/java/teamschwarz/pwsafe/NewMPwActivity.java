package teamschwarz.pwsafe;

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


public class NewMPwActivity extends Activity {

    private EditText masterPassword1;
    private Button saveButton;
    private EditText masterPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mpw);

        // Zurück-Button aktivieren
        getActionBar().setDisplayHomeAsUpEnabled(true);

        masterPassword1 = (EditText) findViewById(R.id.editTextNew1);
        masterPassword1.addTextChangedListener(new TextWatcher() {
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

        masterPassword2 = (EditText) findViewById(R.id.editTextNew2);
        masterPassword2.addTextChangedListener(new TextWatcher() {
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
                //TODO Masterpasswort ändern
                masterPassword1.setText("");
                masterPassword2.setText("");
                Toast.makeText(getApplicationContext(), R.string.mpwUpdated, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidPasswordItem() {
        String new1 = String.valueOf(masterPassword1.getText());
        String new2 = String.valueOf(masterPassword2.getText());

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
