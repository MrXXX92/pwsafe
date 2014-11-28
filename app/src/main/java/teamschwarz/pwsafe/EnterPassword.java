package teamschwarz.pwsafe;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EnterPassword extends Activity {

    EditText masterPassword;
    Button accessButton;
    EditText newDescription;
    EditText newPassword;
    Button saveButton;
    List<PasswordItem> passwords = new ArrayList<PasswordItem>();
    ListView passwordsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        masterPassword = (EditText) findViewById(R.id.editTextMasterPassword);
        accessButton = (Button) findViewById(R.id.buttonAccess);
        newDescription = (EditText) findViewById(R.id.editTextDescription);
        newPassword = (EditText) findViewById(R.id.editTextPassword);
        saveButton = (Button) findViewById(R.id.buttonSavePassword);
        passwordsListView = (ListView) findViewById(R.id.listViewPasswords);

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

        accessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Decoding passwords...", Toast.LENGTH_SHORT).show();

                //Zur Übersichtsseite springen
            }
        });

        newDescription.addTextChangedListener(new TextWatcher() {
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
                PasswordItem passwortItem = new PasswordItem(newDescription.getText().toString(), newPassword.getText().toString());
                passwords.add(passwortItem);
                Toast.makeText(getApplicationContext(), "New password has been added", Toast.LENGTH_SHORT).show();
            }
        });

        populatePasswordList();
    }

    private boolean isValidPasswordItem() {
        return (String.valueOf(newDescription.getText()).trim().length() > 0) && (String.valueOf(newPassword.getText()).trim().length() > 0);
    }

    private void populatePasswordList() {
        ArrayAdapter<PasswordItem> PasswordAdapter = new PasswordListAdapter();
        passwordsListView.setAdapter(PasswordAdapter);
    }

    private class PasswordListAdapter extends ArrayAdapter<PasswordItem> {
        public PasswordListAdapter() {
            super (EnterPassword.this, R.layout.activity_view_passwords, passwords);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.activity_view_passwords, parent, false);

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
        getMenuInflater().inflate(R.menu.menu_enter_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}