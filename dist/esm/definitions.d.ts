export interface ZebraPrinterPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    bravo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    requestUSBPermission(): Promise<void>;
    printByUSB(options?: USBOptions): Promise<void>;
    requestBluetoothPermission(): Promise<void>;
    printByBluetooth(options?: BluetoothOptions): Promise<void>;
}
export interface USBOptions {
    msg: string;
}
export interface BluetoothOptions {
    mac: string;
    msg: string;
}
