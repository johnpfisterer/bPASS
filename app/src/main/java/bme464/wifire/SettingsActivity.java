package bme464.wifire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "bme464.wifire.MainActivity.MESSAGE";
    public boolean name_set = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void name_enter(View view){
        name_set = true;
        Intent intent = new Intent(this, MainActivity.class);
        EditText Name_entry = (EditText) findViewById(R.id.name_entry);
        String message = Name_entry.getText().toString();
        //@string/name_ID = message;
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
}
