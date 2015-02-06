package teamschwarz.pwsafe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import teamschwarz.pwsafe.R;
import teamschwarz.pwsafe.utils.XMLParser;
import teamschwarz.pwsafe.utils.pwSafeApplication;

public class MainActivity extends Activity {

    EditText masterPassword;
    Button accessButton;

    private void setMaster(final String masterPw){
        ((pwSafeApplication)this.getApplication()).setMasterpw(masterPw);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        masterPassword = (EditText) findViewById(R.id.editTextMasterPassword);
        masterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accessButton.setEnabled(String.valueOf(masterPassword.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        accessButton = (Button) findViewById(R.id.buttonAccess);
        accessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Eingegebenes MasterPasswort speichern...
                EditText pwText = (EditText) findViewById(R.id.editTextMasterPassword);
                String password = pwText.getText().toString();

                //... und der neuen Aktivität mitgeben
                Intent intent = new Intent(MainActivity.this, PasswordlistActivity.class);

                //Passwort Liste mit den Werten aus der XML-Datei befüllen
                PasswordlistActivity.passwords = XMLParser.readXML(password);
                setMaster(password);
                startActivity(intent);
            }
        });
    }
}