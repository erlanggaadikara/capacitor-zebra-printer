# capacitor-zebra-printer

Capacitor plugin for zebra printer

## Install

```bash
npm install capacitor-zebra-printer
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`bravo(...)`](#bravo)
* [`requestUSBPermission()`](#requestusbpermission)
* [`printByUSB(...)`](#printbyusb)
* [`requestBluetoothPermission()`](#requestbluetoothpermission)
* [`printByBluetooth(...)`](#printbybluetooth)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => any
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>any</code>

--------------------


### bravo(...)

```typescript
bravo(options: { value: string; }) => any
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>any</code>

--------------------


### requestUSBPermission()

```typescript
requestUSBPermission() => any
```

**Returns:** <code>any</code>

--------------------


### printByUSB(...)

```typescript
printByUSB(options?: USBOptions | undefined) => any
```

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#usboptions">USBOptions</a></code> |

**Returns:** <code>any</code>

--------------------


### requestBluetoothPermission()

```typescript
requestBluetoothPermission() => any
```

**Returns:** <code>any</code>

--------------------


### printByBluetooth(...)

```typescript
printByBluetooth(options?: BluetoothOptions | undefined) => any
```

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#bluetoothoptions">BluetoothOptions</a></code> |

**Returns:** <code>any</code>

--------------------


### Interfaces


#### USBOptions

| Prop      | Type                |
| --------- | ------------------- |
| **`msg`** | <code>string</code> |


#### BluetoothOptions

| Prop      | Type                |
| --------- | ------------------- |
| **`mac`** | <code>string</code> |
| **`msg`** | <code>string</code> |

</docgen-api>
