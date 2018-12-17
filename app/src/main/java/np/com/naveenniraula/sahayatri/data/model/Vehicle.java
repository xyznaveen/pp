package np.com.naveenniraula.sahayatri.data.model;

import java.security.NoSuchAlgorithmException;

import np.com.naveenniraula.sahayatri.util.MessageHelper;

public class Vehicle {

    private String key;
    private String model;
    private String registrationNumber;
    private int totalSeatCount;
    private int totalCrewCount;
    private String vehicleHash;
    private String operationMode;
    private String vehicleOwnerKey;

    public Vehicle() {
    }

    public Vehicle(String model, String registrationNumber,
                   int totalSeatCount, int totalCrewCount,
                   String vehicleHash, String operationMode, String vehicleOwnerKey) {

        this.model = model;
        this.registrationNumber = registrationNumber;
        this.totalSeatCount = totalSeatCount;
        this.totalCrewCount = totalCrewCount;
        this.vehicleHash = vehicleHash;
        this.operationMode = operationMode;
        this.vehicleOwnerKey = vehicleOwnerKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getTotalSeatCount() {
        return totalSeatCount;
    }

    public void setTotalSeatCount(int totalSeatCount) {
        this.totalSeatCount = totalSeatCount;
    }

    public int getTotalCrewCount() {
        return totalCrewCount;
    }

    public void setTotalCrewCount(int totalCrewCount) {
        this.totalCrewCount = totalCrewCount;
    }

    public String getVehicleHash() {
        return vehicleHash;
    }

    public boolean generateVehicleHash() {

        StringBuilder sb = new StringBuilder(model.trim());
        sb.append(registrationNumber.trim());
        try {
            vehicleHash = MessageHelper.md5(sb.toString().toLowerCase().replaceAll(" ", ""));
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        return true;
    }

    public void setVehicleHash(String vehicleHash) {
        this.vehicleHash = vehicleHash;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getVehicleOwnerKey() {
        return vehicleOwnerKey;
    }

    public void setVehicleOwnerKey(String vehicleOwnerKey) {
        this.vehicleOwnerKey = vehicleOwnerKey;
    }
}
