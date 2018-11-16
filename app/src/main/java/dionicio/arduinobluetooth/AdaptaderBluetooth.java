package dionicio.arduinobluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdaptaderBluetooth extends BaseAdapter {
    private Context context;
    private int layoutResource;
    private List<BluetoothDevice> bluetoothDevices;

    public AdaptaderBluetooth(Context context, int layoutResource, List<BluetoothDevice> bluetoothDevices) {
        this.context = context;
        this.layoutResource = layoutResource;
        this.bluetoothDevices = bluetoothDevices;
    }

    @Override
    public int getCount() {
        return bluetoothDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return bluetoothDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BluetoothDevice device = (BluetoothDevice) getItem(i);

        view = LayoutInflater.from(context).inflate(layoutResource, null);

        TextView txtName = view.findViewById(R.id.device_name);
        txtName.setText(device.getName());

        TextView txtMac = view.findViewById(R.id.device_mac);
        txtMac.setText(device.getAddress());

        return view;
    }
}
