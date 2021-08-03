import { WebPlugin } from '@capacitor/core';

import type {
  BluetoothOptions,
  USBOptions,
  ZebraPrinterPlugin,
} from './definitions';

export class ZebraPrinterWeb extends WebPlugin implements ZebraPrinterPlugin {
  async bravo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  async requestUSBPermission(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
  async printByUSB(_options: USBOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
  async requestBluetoothPermission(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
  async printByBluetooth(_options: BluetoothOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
