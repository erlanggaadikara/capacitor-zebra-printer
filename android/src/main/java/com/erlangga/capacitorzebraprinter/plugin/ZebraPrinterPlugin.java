package com.erlangga.capacitorzebraprinter.plugin;

import android.Manifest;

import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

@CapacitorPlugin(name = "ZebraPrinter",permissions = { @Permission(alias = "bluetooth", strings = { Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN }) })
public class ZebraPrinterPlugin extends Plugin {

    private ZebraPrinter implementation = new ZebraPrinter();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void requestUSBPermission(PluginCall call) {
        //usb permission
    }

    @PluginMethod
    public void printByUSB(PluginCall call) {
        //Message Zebra Printer
        String message = call.getString("msg");
        //usb print
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
    }
}
