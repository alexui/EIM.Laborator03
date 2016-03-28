package com.politehnica.alexbudau.phonedialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private ImageButton backSpaceImageButton;
    private ImageButton callImageButton;
    private ImageButton rejectImageButton;
    private ImageButton contactsImageButton;
    private ArrayList<Button> dialerButtons;

    private class ClickListener implements View.OnClickListener {

        String initialText;
        StringBuilder stringBuilder;
        Intent intent;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backspace :
                    initialText = phoneNumberEditText.getText().toString();
                    stringBuilder = new StringBuilder(initialText);
                    stringBuilder.deleteCharAt(initialText.length() - 1);
                    phoneNumberEditText.setText(stringBuilder.toString());
                    phoneNumberEditText.setSelection(initialText.length() - 1);
                    break;
                case R.id.call :
                    intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                    break;
                case R.id.reject :
                    MainActivity.this.finish();
                    break;
                case R.id.contacts :
                    initialText = phoneNumberEditText.getText().toString();
                    if (initialText.length() > 0) {
                        intent = new Intent("com.politehnica.alexbudau.contactsmanager.intent.action.MainActivity");
                        intent.putExtra(Constants.PHONE_NUMBER_EXTRA, initialText);
                        startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQ_CODE);
                    }
                    break;
                default :
                    initialText = phoneNumberEditText.getText().toString();
                    stringBuilder = new StringBuilder(initialText);
                    stringBuilder.append(((Button) v).getText());
                    phoneNumberEditText.setText(stringBuilder.toString());
                    phoneNumberEditText.setSelection(initialText.length() + 1);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumberText);
        backSpaceImageButton = (ImageButton) findViewById(R.id.backspace);
        callImageButton = (ImageButton) findViewById(R.id.call);
        rejectImageButton = (ImageButton) findViewById(R.id.reject);
        contactsImageButton = (ImageButton) findViewById(R.id.contacts);

        View.OnClickListener clickListener = new ClickListener();

        backSpaceImageButton.setOnClickListener(clickListener);
        callImageButton.setOnClickListener(clickListener);
        rejectImageButton.setOnClickListener(clickListener);
        contactsImageButton.setOnClickListener(clickListener);

        dialerButtons = new ArrayList<>();
        for (int i = 0; i < Constants.phoneDialerButtons.length; i++) {

            Log.d("PhoneDialer", String.valueOf(i));
            Button b = (Button) findViewById(Constants.phoneDialerButtons[i]);
            b.setText(String.valueOf(i));
            b.setOnClickListener(clickListener);
            dialerButtons.add(b);
        }

        Button hash = (Button) findViewById(R.id.hash);
        Button star = (Button) findViewById(R.id.star);

        hash.setOnClickListener(clickListener);
        star.setOnClickListener(clickListener);
        dialerButtons.add(star);
        dialerButtons.add(hash);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Constants.CONTACTS_MANAGER_REQ_CODE == requestCode) {
            if (resultCode == RESULT_OK)
                Toast.makeText(this, Constants.EVERY_THING_OK, Toast.LENGTH_SHORT).show();
            if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, Constants.CANCELLED, Toast.LENGTH_LONG).show();
        }
    }
}
