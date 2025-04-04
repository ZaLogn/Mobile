package com.example.socket;
import android.Manifest;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class BlueControl extends AppCompatActivity {
    ImageButton btnTb1, btnTb2, btnDis;
    TextView txt1, txtMAC;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    Set<BluetoothDevice> pairedDevices1;
    String address = null;
    private ProgressDialog progress;
    int flaglamp1 = 0;
    int flaglamp2 = 0;

    // SPP UUID
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent newint = getIntent();
        address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS); // Nhận địa chỉ thiết bị Bluetooth

        setContentView(R.layout.activity_control);

        // Ánh xạ View
        btnTb1 = findViewById(R.id.btnTb1);
        btnTb2 = findViewById(R.id.btnTb2);
        txt1 = findViewById(R.id.textV1);
        txtMAC = findViewById(R.id.textViewMAC);
        btnDis = findViewById(R.id.btnDisc);

        // Kết nối Bluetooth
        new ConnectBT().execute();

        btnTb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thietTbil();
            }
        });

        btnTb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thietTbi2();
            }
        });

        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();
            }
        });
    }

    private void thietTbil() {
        if (btSocket != null) {
            try {
                if (this.flaglamp1 == 0) {
                    this.flaglamp1 = 1;
                    this.btnTb1.setBackgroundResource(R.drawable.img_button_on);
                    btSocket.getOutputStream().write("1".getBytes());
                    txt1.setText("Thiết bị số 1 đang bật");
                } else {
                    this.flaglamp1 = 0;
                    this.btnTb1.setBackgroundResource(R.drawable.img_button_off);
                    btSocket.getOutputStream().write("A".getBytes());
                    txt1.setText("Thiết bị số 1 đang tắt");
                }
            } catch (IOException e) {
                msg("Lỗi khi gửi dữ liệu!");
            }
        }
    }

    private void thietTbi2() {
        if (btSocket != null) {
            try {
                if (this.flaglamp2 == 0) {
                    this.flaglamp2 = 1;
                    this.btnTb2.setBackgroundResource(R.drawable.img_button_on);
                    btSocket.getOutputStream().write("2".getBytes());
                    txt1.setText("Thiết bị số 2 đang bật");
                } else {
                    this.flaglamp2 = 0;
                    this.btnTb2.setBackgroundResource(R.drawable.img_button_off);
                    btSocket.getOutputStream().write("B".getBytes());
                    txt1.setText("Thiết bị số 2 đang tắt");
                }
            } catch (IOException e) {
                msg("Lỗi khi gửi dữ liệu!");
            }
        }
    }

    private void Disconnect() {
        if (btSocket != null) {
            try {
                btSocket.close(); // Đóng kết nối
            } catch (IOException e) {
                msg("Lỗi khi đóng kết nối!");
            }
        }
        finish(); // Quay về màn hình trước đó
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(BlueControl.this, "Kết nối Bluetooth", "Vui lòng đợi...");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                msg("Kết nối thất bại! Hãy thử lại.");
                finish();
            } else {
                msg("Kết nối thành công!");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
    private void pairedDevicesList1() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        pairedDevices1 = myBluetooth.getBondedDevices();
        if (pairedDevices1.size() > 0) {
            for (BluetoothDevice bt : pairedDevices1) {
                txtMAC.setText(bt.getName() + " " + bt.getAddress());
            }
        } else {
            msg("Không tìm thấy thiết bị kết nối.");
        }
    }
}
