package dionicio.arduinobluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothUtils {
    public static final String IDENTIFICADOR_PUERTO_ESTANDAR = "00001101-0000-1000-8000-00805F9B34FB";//Identificador de Puerto Serial Estándar
    public static final String FORMATO_ENCODING = "ISO-8859-1";//Formato que permite la impresion de simbolos especiales



    public BluetoothUtils() {

    }

    /*public Printer establecerConexion() throws IOException {
        UUID uuid = UUID.fromString(IDENTIFICADOR_PUERTO_ESTANDAR);
        BluetoothSocket bluetoothSocket = this.dispositivo.createRfcommSocketToServiceRecord(uuid);

        bluetoothSocket.connect();

        ProtocolAdapter mProtocolAdapter = new ProtocolAdapter(bluetoothSocket.getInputStream(), bluetoothSocket.getOutputStream());

        if(mProtocolAdapter.isProtocolEnabled()) {
            mPrinterChannel = mProtocolAdapter.getChannel(ProtocolAdapter.CHANNEL_PRINTER);
            mPrinter = new Printer(mPrinterChannel.getInputStream(), mPrinterChannel.getOutputStream());
        }else{
            mPrinter = new Printer(mProtocolAdapter.getRawInputStream(),mProtocolAdapter.getRawOutputStream());
        }

        return mPrinter;
    }*/

    public static BluetoothSocket getBluetoothSocket(BluetoothDevice device) throws IOException {
        if (device ==  null) return null;

        UUID uuid = UUID.fromString(IDENTIFICADOR_PUERTO_ESTANDAR);
        BluetoothSocket bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);


        return bluetoothSocket;
    }

    //Provee una lista de dispositivos pareados con el actual dispositivo
    public static ArrayList<BluetoothDevice> listarDispositivos() throws Exception {
        ArrayList<BluetoothDevice> dispositivos = new ArrayList<>();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();

        if(defaultAdapter == null){
            throw new Exception("Este dispositivo no Soporta Bluetooh");
        }

        Set<BluetoothDevice> dispositivosEmparejados = defaultAdapter.getBondedDevices();

        if(dispositivosEmparejados.size() > 0) {
            for(BluetoothDevice dispositivo : dispositivosEmparejados) {
                dispositivos.add(dispositivo);
            }
        }

        return dispositivos;
    }
    //Permite seleccionar un dispositivo(impresora) emparejado y luego conectarse...
    public static BluetoothDevice seleccionarDispositivo(List<BluetoothDevice> devices, String nombre){
        for(BluetoothDevice device : devices){
            if(device.getName().equals(nombre) || device.getAddress().equals(nombre)){
                return device;
            }
        }
        return null;
    }
}