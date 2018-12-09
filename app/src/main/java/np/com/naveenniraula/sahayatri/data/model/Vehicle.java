package np.com.naveenniraula.sahayatri.data.model;

import java.security.NoSuchAlgorithmException;

import np.com.naveenniraula.sahayatri.util.MiscUtil;

public class Vehicle {

    private String model;
    private String registrationNumber;
    private int totalSeatCount;
    private int totalCrewCount;
    private String vehicleHash;

    public Vehicle() {
    }

    public Vehicle(
            String model,
            String registrationNumber,
            int totalSeatCount,
            int totalCrewCount
    ) {

        this.model = model;
        this.registrationNumber = registrationNumber;
        this.totalSeatCount = totalSeatCount;
        this.totalCrewCount = totalCrewCount;
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
            vehicleHash = MiscUtil.md5(sb.toString().toLowerCase().replaceAll(" ", ""));
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        return true;
    }

}
