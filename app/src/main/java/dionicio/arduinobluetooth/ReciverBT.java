package dionicio.arduinobluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReciverBT extends BroadcastReceiver {
    private OnBluetoothStateChange onBluetoothStateChangeListener;

    public ReciverBT(OnBluetoothStateChange onBluetoothStateChange) {
        this.onBluetoothStateChangeListener = onBluetoothStateChange;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
//android.bluetooth.adapter.action.STATE_CHANGED
        if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
            int estado = intent.getExtras().getInt(BluetoothAdapter.EXTRA_STATE);

            if(onBluetoothStateChangeListener != null) onBluetoothStateChangeListener.onStateBtChange(estado);
        }
    }

    public interface OnBluetoothStateChange{
        void onStateBtChange(int estado);
    }
}
