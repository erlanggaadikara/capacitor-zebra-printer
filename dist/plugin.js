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

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        ZebraPrinterWeb: ZebraPrinterWeb
    });

    exports.ZebraPrinter = ZebraPrinter;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

}({}, capacitorExports));
//# sourceMappingURL=plugin.js.map
