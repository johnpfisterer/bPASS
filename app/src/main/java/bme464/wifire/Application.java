package bme464.wifire;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;

/**
 * Created by John Pfisterer on 10/16/2015.
 */
public class Application extends android.app.Application {
    static public String test = "This is a test";
    public void onCreate(){
        super.onCreate();
        // Initializes Bluetooth adapter.
    }
}
