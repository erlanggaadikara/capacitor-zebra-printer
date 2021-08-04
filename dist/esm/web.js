import { WebPlugin } from '@capacitor/core';
export class ZebraPrinterWeb extends WebPlugin {
    async bravo(options) {
        console.log('ECHO', options);
        return options;
    }
    async requestUSBPermission() {
        throw this.unimplemented('Not implemented on web.');
    }
    async printByUSB(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
    async requestBluetoothPermission() {
        throw this.unimplemented('Not implemented on web.');
    }
    async printByBluetooth(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
}
//# sourceMappingURL=web.js.map