package np.com.naveenniraula.sahayatri.data.model;

public class BookingModel {

    private long bookedOn;
    private String paymentStatus; // Paid, Unpaid
    private String bookMode; // On Hold, Booked, Not Booked
    private String userKey;
    private String vehicleKey;
    private String seatIdentifier;

    public BookingModel() {
    }

    public BookingModel(long bookedOn, String paymentStatus, String bookMode,
                        String userKey, String vehicleKey, String seatIdentifier) {
        this.bookedOn = bookedOn;
        this.paymentStatus = paymentStatus;
        this.bookMode = bookMode;
        this.userKey = userKey;
        this.vehicleKey = vehicleKey;
        this.seatIdentifier = seatIdentifier;
    }

    public long getBookedOn() {
        return bookedOn;
    }

    public void setBookedOn(long bookedOn) {
        this.bookedOn = bookedOn;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBookMode() {
        return bookMode;
    }

    public void setBookMode(String bookMode) {
        this.bookMode = bookMode;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getVehicleKey() {
        return vehicleKey;
    }

    public void setVehicleKey(String vehicleKey) {
        this.vehicleKey = vehicleKey;
    }

    public String getSeatIdentifier() {
        return seatIdentifier;
    }

    public void setSeatIdentifier(String seatIdentifier) {
        this.seatIdentifier = seatIdentifier;
    }
}
