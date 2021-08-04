var capacitorZebraPrinter = (function (exports, core) {
    'use strict';

    const ZebraPrinter = core.registerPlugin('ZebraPrinter', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.ZebraPrinterWeb()),
    });

    class ZebraPrinterWeb extends core.WebPlugin {
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

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        ZebraPrinterWeb: ZebraPrinterWeb
    });

    exports.ZebraPrinter = ZebraPrinter;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

}({}, capacitorExports));
//# sourceMappingURL=plugin.js.map
