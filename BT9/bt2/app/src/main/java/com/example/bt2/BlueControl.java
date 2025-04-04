package com.example.bt2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.UUID;

public class BlueControl extends AppCompatActivity {

    ImageButton btnTb1, btnTb2, btnDisc;
    TextView textView, textViewMAC, textV1;
    String address = null;
    private static final String TAG = "BlueControl";
    private static final int RECIEVE_MESSAGE = 1;
    private Handler handler;
    private BluetoothAdapter myBluetooth = null;
    private BluetoothSocket btSocket = null;
    private ConnectedThread mConnectedThread;
    //SPP UUID. Look for it on your robot's Bluetooth controller.
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        //ánh xạ
        btnTb1 = (ImageButton) findViewById(R.id.btnTb1);
        btnTb2 = (ImageButton) findViewById(R.id.btnTb2);
        btnDisc = (ImageButton) findViewById(R.id.btnDisc);
        textView = (TextView) findViewById(R.id.textView);
        textViewMAC = (TextView) findViewById(R.id.textViewMAC);
        textV1 = (TextView) findViewById(R.id.textV1);

        Intent newint = getIntent();
        address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //get the bluetooth adapter
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        checkBTState();

        textViewMAC.setText(address); //show the MAC address on the screen

        btnTb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnLed();   //method to turn on
            }
        });

        btnTb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffLed();  //method to turn off
            }
        });

        btnDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect(); //close connection
            }
        });

        handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                if (msg.what == RECIEVE_MESSAGE) {
                    byte[] readBuf = (byte[]) msg.obj;
                    String strIncom = new String(readBuf, 0, msg.arg1);
                    textV1.append("n" + strIncom);
                }
            }
        };

        btConnection();
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(myUUID);
        //creates secure outgoing connection with Hc-05
    }

    @Override
    public void onResume() {
        super.onResume();

        //Set up a pointer to the remote node using it's address.
        BluetoothDevice myDevice = myBluetooth.getRemoteDevice(address);

        //create and connect socket
        try {
            btSocket = createBluetoothSocket(myDevice);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the connection.  This blocks until it connects or throws an exception.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and turn it on if it isn't
    private void checkBTState() {

        if (myBluetooth == null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (myBluetooth.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void turnOffLed() {
        mConnectedThread.write("0");    // Send "0" via Bluetooth
        textV1.append("\nSent Data: Turn Offn");
    }

    private void turnOnLed() {
        mConnectedThread.write("1");   // Send "1" via Bluetooth
        textV1.append("\nSent Data: Turn Onn");
    }

    private void Disconnect() {
        if (btSocket != null) //If the btSocket is busy
        {
            try {
                btSocket.close(); //close connection
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "Error closing socket", Toast.LENGTH_LONG).show();
            }
        }
        finish(); //return to the first layout
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received data
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    handler.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send the obtained bytes to the UI Activity
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered string into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }

    private void btConnection() {
        //if the device is not connected, try to connect
        if (btSocket == null || !btSocket.isConnected()) {
            try {
                myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                btSocket.connect();//start connection
            } catch (IOException e) {
                try {
                    btSocket.close();
                } catch (IOException e2) {
                    //insert code to deal with this
                }
            }
        }
    }
}
