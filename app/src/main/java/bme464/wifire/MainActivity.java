package bme464.wifire;

import android.content.Intent;
import android.content.SharedPreferences;
// ACCEL
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
// Timer Tasks
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.os.Handler;


import android.os.Handler;

import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //static public String name = "Enter Name or ID";
    public String name;

    // Accelerometer Variables
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float vals[] = new float[3];
    private float gravity[] = new float[3];
    private float linear_acceleration[] = new float[3];
    private int accelcount;

    //Timer variables
    private int elasped_time = 0;
    final Handler myHandler = new Handler();
    TextView timerstring;
    private float abs_accel[] = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        name = preferences.getString("ID", "Enter Name or ID");
        TextView textView_name = (TextView) findViewById(R.id.textView_name);
        textView_name.setText(name);

        /* Configure the accelerometer to report data at the fastest rate. */
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,
                mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        /*
        Accelerometer, SENSOR_DELAY_FASTEST: 18-20 ms
        Accelerometer, SENSOR_DELAY_GAME: 37-39 ms
        Accelerometer, SENSOR_DELAY_UI: 85-87 ms
        Accelerometer, SENSOR_DELAY_NORMAL: 215-230 ms */

        accelcount = 0;

        // Timer
        timerstring = (TextView)findViewById(R.id.textView_temp);
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask(){
            @Override
            public void run(){UpdateGUI();}
        },0,1000);
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
        // Currently used to view accelerometer data.
        TextView edit_field_x = (TextView) findViewById(R.id.textView_heart);
        TextView edit_field_y = (TextView) findViewById(R.id.textView_breath);
        TextView edit_field_z = (TextView) findViewById(R.id.textView_O2);

        String message_x = "x: " + vals[0];
        String message_y = "y: " + vals[1];
        String message_z = "z: " + vals[2];

        edit_field_x.setText(message_x);
        edit_field_y.setText(message_y);
        edit_field_z.setText(message_z);
        //android:text="98 BPM"
        //android:id="@+id/textView_heart"
        //Intent intent = new Intent(this, bpm_graph_activity.class);
        //startActivity(intent);
    }
    public void click_breath(View view){
        Intent intent = new Intent(this, rpm_graph_activity.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)//
    {
        Sensor mySensor = sensorEvent.sensor;

        final float alpha = (float)0.8;

        /* Save the sensor variables locally so we can manipulate and display later. */
        for (int j = 0; j < 3; j++) {
            vals[j] = sensorEvent.values[j];
        }

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * vals[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * vals[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * vals[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = vals[0] - gravity[0];
        linear_acceleration[1] = vals[1] - gravity[1];
        linear_acceleration[2] = vals[2] - gravity[2];

        if (accelcount == 4){
            // accelcount for SENSOR_DELAY_FASTEST: was 20
            TextView edit_field_x = (TextView) findViewById(R.id.textView_heart);
            TextView edit_field_y = (TextView) findViewById(R.id.textView_breath);
            TextView edit_field_z = (TextView) findViewById(R.id.textView_O2);

            String message_x = "x: " + linear_acceleration[0];
            String message_y = "y: " + linear_acceleration[1];
            String message_z = "z: " + linear_acceleration[2];

            edit_field_x.setText(message_x);
            edit_field_y.setText(message_y);
            edit_field_z.setText(message_z);

            accelcount = 0;
        }
        accelcount++;
        for (int i=0; i<3; i++){
            abs_accel[i] = Math.abs(linear_acceleration[i]);
            if (abs_accel[i] > 0.5) {
                elasped_time = 0;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void UpdateGUI(){
        elasped_time++;
        myHandler.post(myRunnable);
    }

    final Runnable myRunnable = new Runnable(){
        public void run(){
            timerstring.setText(String.valueOf(elasped_time));
        }
    };
}
