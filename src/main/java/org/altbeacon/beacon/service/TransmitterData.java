package org.altbeacon.beacon.service;

import android.bluetooth.le.AdvertiseSettings;
import android.os.Parcel;
import android.os.Parcelable;

import org.altbeacon.beacon.Beacon;

/**
 * @author Christian Melchior.
 */
public class TransmitterData implements Parcelable {

    private Beacon beacon;
    private int advertiseMode; // See android.bluetooth.le.AdvertiseSettings
    private int txPowerLevel; // See android.bluetooth.le.AdvertiseSettings

    public TransmitterData(Beacon beacon) {
        this(beacon, AdvertiseSettings.ADVERTISE_MODE_BALANCED, AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
    }

    public TransmitterData(Beacon beacon, int advertiseMode, int txPowerLevel) {
        this.beacon = beacon;
        this.advertiseMode= advertiseMode;
        this.txPowerLevel = txPowerLevel;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public float getAdvertiseMode() {
        return advertiseMode;
    }

    public float getTxPowerLevel() {
        return txPowerLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(beacon, flags);
        out.writeInt(advertiseMode);
        out.writeInt(txPowerLevel);
    }

    public static final Parcelable.Creator<TransmitterData> CREATOR = new Parcelable.Creator<TransmitterData>() {
        public TransmitterData createFromParcel(Parcel in) {
            return new TransmitterData(in);
        }
        public TransmitterData[] newArray(int size) {
            return new TransmitterData[size];
        }
    };

    private TransmitterData(Parcel in) {
        beacon = in.readParcelable(this.getClass().getClassLoader());
        advertiseMode = in.readInt();
        txPowerLevel = in.readInt();
    }
}
