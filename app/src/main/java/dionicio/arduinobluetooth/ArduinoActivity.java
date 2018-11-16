package dionicio.arduinobluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ArduinoActivity extends AppCompatActivity implements View.OnClickListener{
    private BluetoothDevice bluetoothDevice;
    private boolean isOn = false;

    private Button button, buttonOtro;
    private BluetoothSocket bluetoothSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arduino);

        bluetoothDevice = getIntent().getExtras().getParcelable(MainActivity.EXTRA_BLUETOOTH);

        setTitle(bluetoothDevice.getName());
        getSupportActionBar().setSubtitle(bluetoothDevice.getAddress());

        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        buttonOtro = findViewById(R.id.buttonOtro);
        buttonOtro.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            bluetoothSocket = BluetoothUtils.getBluetoothSocket(bluetoothDevice);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(bluetoothSocket != null){
            try {
                bluetoothSocket.connect();

            } catch (IOException e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getBaseContext(), "Intenta en mil años", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if(bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(String data) throws IOException {
        if(bluetoothSocket.isConnected()){
            bluetoothSocket.getOutputStream().write(data.getBytes());
        }

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                button.setText(isOn ? getString(R.string.encender) : getString(R.string.apagar));

                    try {
                       send(isOn ? "0" : "1" );
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                Log.d("led", isOn ? "0" : "1" );

                isOn = !isOn;
                break;

            case R.id.buttonOtro:
                try {
                    send("x");
                } catch (IOException e) {
                   Log.d("BTExe", e.getMessage());
                }
                break;
        }
    }


}
