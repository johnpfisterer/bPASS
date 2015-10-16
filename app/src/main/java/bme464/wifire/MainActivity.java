package bme464.wifire;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    //static public String name = "Enter Name or ID";
    public String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.getDefaultSharedPreferences(this);
        name = getPreferences(MODE_PRIVATE).getString("ID","Enter Name or ID");
        TextView textView_name = (TextView) findViewById(R.id.textView_name);
        textView_name.setText(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void click_temp(View view){
        Intent intent = new Intent(this, temp_graph_activity.class);
        startActivity(intent);
    }
    public void click_O2(View view){
        Intent intent = new Intent(this, O2_graph_activity.class);
        startActivity(intent);
    }
    public void click_heart(View view){
        Intent intent = new Intent(this, bpm_graph_activity.class);
        startActivity(intent);
    }
    public void click_breath(View view){
        Intent intent = new Intent(this, rpm_graph_activity.class);
        startActivity(intent);
    }
}
