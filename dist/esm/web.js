import { WebPlugin } from '@capacitor/core';
export class ZebraPrinterWeb extends WebPlugin {
    async bravo(options) {
        console.log('ECHO', options);
        return options;
    }
    async requestUSBPermission() { }
    async printByUSB(options) {
        console.log('ECHO', options);
    }
    async requestBluetoothPermission() { }
    async printByBluetooth(options) {
        console.log('ECHO', options);
    }
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
}
//# sourceMappingURL=web.js.map