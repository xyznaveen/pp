package np.com.naveenniraula.sahayatri.data.model;

public class SeatModel {

    private int backgroundColor; // skip in booking model
    private boolean isSeat; // skip in booking model
    private boolean isAvailable; // calculated from booking status
    private boolean isSelected; // skip in booking model
    private boolean isFromExistingDataset; // skip in booking
    private String seatIdentifier;
    private String userKey;
    private String userName;
    private String vehicleKey;
    private String bookingKey;
    private long bookedOn;
    private String ownerName;

    public SeatModel() {
    }

    public SeatModel(int backgroundColor, boolean isSeat, boolean isAvailable, boolean isSelected,
                     boolean isFromExistingDataset, String seatIdentifier, String userKey,
                     String vehicleKey, String bookingKey, long bookedOn, String ownerName) {

        this.backgroundColor = backgroundColor;
        this.isSeat = isSeat;
        this.isAvailable = isAvailable;
        this.isSelected = isSelected;
        this.isFromExistingDataset = isFromExistingDataset;
        this.seatIdentifier = seatIdentifier;
        this.userKey = userKey;
        this.vehicleKey = vehicleKey;
        this.bookingKey = bookingKey;
        this.bookedOn = bookedOn;
        this.ownerName = ownerName;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isSeat() {
        return isSeat;
    }

    public void setSeat(boolean seat) {
        isSeat = seat;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isFromExistingDataset() {
        return isFromExistingDataset;
    }

    public void setFromExistingDataset(boolean fromExistingDataset) {
        isFromExistingDataset = fromExistingDataset;
    }

    public String getSeatIdentifier() {
        return seatIdentifier;
    }

    public void setSeatIdentifier(String seatIdentifier) {
        this.seatIdentifier = seatIdentifier;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVehicleKey() {
        return vehicleKey;
    }

    public void setVehicleKey(String vehicleKey) {
        this.vehicleKey = vehicleKey;
    }

    public String getBookingKey() {
        return bookingKey;
    }

    public void setBookingKey(String bookingKey) {
        this.bookingKey = bookingKey;
    }

    public long getBookedOn() {
        return bookedOn;
    }

    public void setBookedOn(long bookedOn) {
        this.bookedOn = bookedOn;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
