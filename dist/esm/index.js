import { registerPlugin } from '@capacitor/core';
const ZebraPrinter = registerPlugin('ZebraPrinter', {
    web: () => import('./web').then(m => new m.ZebraPrinterWeb()),
});
export * from './definitions';
export { ZebraPrinter };
//# sourceMappingURL=index.js.map