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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private ImageButton backSpaceImageButton;
    private ImageButton callImageButton;
    private ImageButton rejectImageButton;
    private ArrayList<Button> dialerButtons;

    private class dialButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String initialText = phoneNumberEditText.getText().toString();
            StringBuilder stringBuilder = new StringBuilder(initialText);
            stringBuilder.append(((Button) v).getText());
            phoneNumberEditText.setText(stringBuilder.toString());
            phoneNumberEditText.setSelection(initialText.length() + 1);
        }
    }

    private class backSpaceImageButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String initialText = phoneNumberEditText.getText().toString();
            StringBuilder stringBuilder = new StringBuilder(initialText);
            stringBuilder.deleteCharAt(initialText.length() - 1);
            phoneNumberEditText.setText(stringBuilder.toString());
            phoneNumberEditText.setSelection(initialText.length() - 1);
        }
    }

    private class callImageButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        }
    }
    private class rejectImageButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            MainActivity.this.finish();
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

        backSpaceImageButton.setOnClickListener(new backSpaceImageButtonListener());
        callImageButton.setOnClickListener(new callImageButtonListener());
        rejectImageButton.setOnClickListener(new rejectImageButtonListener());

        dialerButtons = new ArrayList<>();
        for (int i = 0; i < Constants.phoneDialerButtons.length; i++) {

            Log.d("PhoneDialer", String.valueOf(i));
            Button b = (Button) findViewById(Constants.phoneDialerButtons[i]);
            b.setText(String.valueOf(i));
            b.setOnClickListener(new dialButtonListener());
            dialerButtons.add(b);
        }

        Button hash = (Button) findViewById(R.id.hash);
        Button star = (Button) findViewById(R.id.star);

        hash.setOnClickListener(new dialButtonListener());
        star.setOnClickListener(new dialButtonListener());
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
}
