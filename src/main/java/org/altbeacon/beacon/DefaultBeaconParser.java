package org.altbeacon.beacon;

/**
 * @author Christian Melchior
 */
public class DefaultBeaconParser extends BeaconParser {

    @Override
    public byte[] constructAdvertismentPackage(Beacon beacon, int measuredPower) {
        throw new RuntimeException("Not supported for " + this.getClass().getName());
    }
}
