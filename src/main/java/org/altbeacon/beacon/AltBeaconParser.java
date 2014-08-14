/**
 * Radius Networks, Inc.
 * http://www.radiusnetworks.com
 *
 * @author David G. Young
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.altbeacon.beacon;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

/**
 * A specific beacon parser designed to parse only AltBeacons from raw BLE packets detected by
 * Android.  By default, this is the only <code>BeaconParser</code> that is used by the library.
 * Additional <code>BeaconParser</code> instances can get created and registered with the library.
 * {@link BeaconParser See BeaconParser for more information.}
 */
public class AltBeaconParser extends BeaconParser {
    public static final String TAG = "AltBeaconParser";

    /**
     * Constructs an AltBeacon Parser and sets its layout
     */
    public AltBeaconParser() {
        super();
        this.setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
    }
    /**
     * Construct an AltBeacon from a Bluetooth LE packet collected by Android's Bluetooth APIs,
     * including the raw bluetooth device info
     *
     * @param scanData The actual packet bytes
     * @param rssi The measured signal strength of the packet
     * @param device The bluetooth device that was detected
     * @return An instance of an <code>Beacon</code>
     */
    @TargetApi(5)
    @Override
    public Beacon fromScanData(byte[] scanData, int rssi, BluetoothDevice device) {
        return fromScanData(scanData, rssi, device, new AltBeacon());
    }

    @Override
    public byte[] constructAdvertismentPackage(Beacon beacon, int measuredPower) {
        byte[] advertismentPackage = new byte[26];
        advertismentPackage[0] = (byte) 0x18;  // Radius Network ID, Big Endian
        advertismentPackage[1] = (byte) 0x01;
        advertismentPackage[2] = (byte) 0xBE;  // Identifies advertisement as AltBeacon
        advertismentPackage[3] = (byte) 0xAC;
        System.arraycopy(uuidToBytes(beacon.getId1().toString()), 0, advertismentPackage, 4, 16); // ID1
        System.arraycopy(uint16ToBytes(beacon.getId2().toInt()), 0, advertismentPackage, 20, 2); // ID2
        System.arraycopy(uint16ToBytes(beacon.getId3().toInt()), 0, advertismentPackage, 22, 2); // ID3
        advertismentPackage[24] = int8ToByte(measuredPower);
        advertismentPackage[25] = 0;
        return advertismentPackage;
    }
}
