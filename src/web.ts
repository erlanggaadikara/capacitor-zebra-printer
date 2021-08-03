import { WebPlugin } from '@capacitor/core';

import {
  BluetoothOptions,
  USBOptions,
  ZebraPrinterPlugin,
} from './definitions';

export class ZebraPrinterWeb extends WebPlugin implements ZebraPrinterPlugin {
  async bravo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async requestUSBPermission(): Promise<void> {}
  async printByUSB(options: USBOptions): Promise<void> {
    console.log('ECHO', options);
  }
  async requestBluetoothPermission(): Promise<void> {}
  async printByBluetooth(options: BluetoothOptions): Promise<void> {
    console.log('ECHO', options);
  }
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
