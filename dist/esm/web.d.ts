import { WebPlugin } from '@capacitor/core';
import type { BluetoothOptions, USBOptions, ZebraPrinterPlugin } from './definitions';
export declare class ZebraPrinterWeb extends WebPlugin implements ZebraPrinterPlugin {
    bravo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    requestUSBPermission(): Promise<void>;
    printByUSB(_options: USBOptions): Promise<void>;
    requestBluetoothPermission(): Promise<void>;
    printByBluetooth(_options: BluetoothOptions): Promise<void>;
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
