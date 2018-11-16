package dionicio.arduinobluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;



public class MainActivity extends AppCompatActivity implements  ReciverBT.OnBluetoothStateChange, AdapterView.OnItemClickListener {
    public static final String EXTRA_BLUETOOTH = "bluetooth_device";
    private ListView ls;
    private BaseAdapter adapter;
    private List<BluetoothDevice> devices;
    private BroadcastReceiver bReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bReceiver = new ReciverBT(this);

        ls = findViewById(R.id.list_devices);
        ls.setOnItemClickListener(this);

        try {
            devices = BluetoothUtils.listarDispositivos();
        } catch (Exception e) {
            Snackbar.make(ls, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }


        showDevices(devices);
    }



    private void showDevices(List<BluetoothDevice> devices) {
        if(devices != null){
            adapter = new AdaptaderBluetooth(this, R.layout.list_bt_layout, devices);
            ls.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            ls.setAdapter(null);
        }


    }

    private IntentFilter getFilter(){
        return  new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    }



    @Override
    public void onStateBtChange(int estado) {
        switch (estado){
            case BluetoothAdapter.STATE_ON:
                Toast.makeText(getApplicationContext(), "Bluetooth encendido", Toast.LENGTH_SHORT).show();
                try {
                    devices = BluetoothUtils.listarDispositivos();
                } catch (Exception e) {
                    Snackbar.make(ls, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }

                showDevices(devices);
                break;
            case BluetoothAdapter.STATE_OFF:
                Toast.makeText(getApplicationContext(), "Bluetooth apagado", Toast.LENGTH_SHORT).show();

                showDevices(null);
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(bReceiver, getFilter());
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bReceiver);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BluetoothDevice device = (BluetoothDevice) adapterView.getItemAtPosition(i);

        Intent intent = new Intent(getApplicationContext(), ArduinoActivity.class);
        intent.putExtra(EXTRA_BLUETOOTH, device);
        startActivity(intent);
    }


}


