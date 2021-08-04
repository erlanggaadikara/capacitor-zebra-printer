package com.erlangga.capacitorzebraprinter.plugin;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Looper;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.zebra.sdk.comm.BluetoothConnectionInsecure;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterUsb;
import com.zebra.sdk.printer.discovery.DiscoveryHandler;
import com.zebra.sdk.printer.discovery.UsbDiscoverer;
import java.util.LinkedList;
import java.util.List;

@CapacitorPlugin(
        name = "ZebraPrinter",
        permissions = { @Permission(alias = "bluetooth", strings = { Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN }) }
)
public class ZebraPrinterPlugin extends Plugin {

    private static final String LOG_TAG = "ZebraBluetoothPrinter";

    private static final String ACTION_USB_PERMISSION = "com.andromedia.wms.USB_PERMISSION";
    private IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);

    private PendingIntent mPermissionIntent;
    private boolean hasPermissionToCommunicate = false;

    private UsbManager mUsbManager;
    private DiscoveredPrinterUsb discoveredPrinterUsb;

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", "success");
        call.resolve(ret);
    }

    @PluginMethod
    public void requestUSBPermission(PluginCall call) {
        Context context = this.getContext();
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);

        new Thread(
                new Runnable() {
                    public void run() {
                        // Find connected printers
                        UsbDiscoveryHandler handler = new UsbDiscoveryHandler();
                        UsbDiscoverer.findPrinters(context, handler);
                        try {
                            while (!handler.discoveryComplete) {
                                Thread.sleep(100);
                            }

                            if (handler.printers != null && handler.printers.size() > 0) {
                                discoveredPrinterUsb = handler.printers.get(0);

                                if (!mUsbManager.hasPermission(discoveredPrinterUsb.device)) {
                                    mUsbManager.requestPermission(discoveredPrinterUsb.device, mPermissionIntent);
                                } else {
                                    hasPermissionToCommunicate = true;
                                    JSObject ret = new JSObject();
                                    ret.put("status", "Success");
                                    call.resolve(ret);
                                }
                            } else {
                                JSObject ret = new JSObject();
                                ret.put("status", "Error");
                                ret.put("msg", "No UsbDiscoverer Found");
                                call.resolve(ret);
                            }
                        } catch (Exception e) {
                            JSObject ret = new JSObject();
                            ret.put("status", "Error");
                            ret.put("msg", e.getMessage());
                            call.resolve(ret);
                        }
                    }
                }
        )
                .start();
    }

    @PluginMethod
    public void printByUSB(PluginCall call) {
        String message = call.getString("msg");
        JSObject ret = new JSObject();

        if (hasPermissionToCommunicate) {
            Connection conn = null;
            try {
                conn = discoveredPrinterUsb.getConnection();
                conn.open();
                conn.write(message.getBytes());
            } catch (ConnectionException e) {
                ret.put("status", "Error");
                ret.put("msg", e.getMessage());
                call.resolve(ret);
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (ConnectionException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            ret.put("status", "Error");
            ret.put("msg", "No Permission to communicate");
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void requestBluetoothPermission(PluginCall call) {
        //Bluetooth print
        if (getPermissionState("bluetooth") != PermissionState.GRANTED) {
            requestPermissionForAlias("bluetooth", call, "bluetoothPermsCallback");
        } else {
            JSObject ret = new JSObject();
            ret.put("status", true);
            call.resolve(ret);
        }
    }

    @PermissionCallback
    private void bluetoothPermsCallback(PluginCall call) {
        if (getPermissionState("bluetooth") == PermissionState.GRANTED) {
            JSObject ret = new JSObject();
            ret.put("status", true);
            call.resolve(ret);
        } else {
            call.reject("Permission is required to use bluetooth");
        }
    }

    @PluginMethod
    public void printByBluetooth(PluginCall call) {
        //Mac and Message Zebra Printer
        String mac = call.getString("mac");
        String message = call.getString("msg");
        //bluetooth print
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Instantiate insecure connection for given Bluetooth MAC Address.
                            Connection thePrinterConn = new BluetoothConnectionInsecure(mac);

                            // if (isPrinterReady(thePrinterConn)) {

                            // Initialize
                            Looper.prepare();

                            // Open the connection - physical connection is established here.
                            thePrinterConn.open();

                            SGD.SET("device.languages", "zpl", thePrinterConn);
                            thePrinterConn.write(message.getBytes());

                            // Close the insecure connection to release resources.
                            thePrinterConn.close();

                            Looper.myLooper().quit();
                            JSObject ret = new JSObject();
                            ret.put("value", "Done");
                            call.resolve(ret);
                        } catch (Exception e) {
                            call.reject(e.getMessage());
                        }
                    }
                }
        )
                .start();
    }

    private Boolean isPrinterReady(Connection connection) throws ConnectionException, ZebraPrinterLanguageUnknownException {
        Boolean isOK = false;
        connection.open();
        // Creates a ZebraPrinter object to use Zebra specific functionality like getCurrentStatus()
        ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
        PrinterStatus printerStatus = printer.getCurrentStatus();
        if (printerStatus.isReadyToPrint) {
            isOK = true;
        } else if (printerStatus.isPaused) {
            throw new ConnectionException("Cannot print because the printer is paused");
        } else if (printerStatus.isHeadOpen) {
            throw new ConnectionException("Cannot print because the printer media door is open");
        } else if (printerStatus.isPaperOut) {
            throw new ConnectionException("Cannot print because the paper is out");
        } else {
            throw new ConnectionException("Cannot print");
        }
        return isOK;
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            hasPermissionToCommunicate = true;
                        }
                    }
                }
            }
        }
    };

    class UsbDiscoveryHandler implements DiscoveryHandler {

        public List<DiscoveredPrinterUsb> printers;
        public boolean discoveryComplete = false;

        public UsbDiscoveryHandler() {
            printers = new LinkedList<DiscoveredPrinterUsb>();
        }

        public void foundPrinter(final DiscoveredPrinter printer) {
            printers.add((DiscoveredPrinterUsb) printer);
        }

        public void discoveryFinished() {
            discoveryComplete = true;
        }

        public void discoveryError(String message) {
            discoveryComplete = true;
        }
    }
}
