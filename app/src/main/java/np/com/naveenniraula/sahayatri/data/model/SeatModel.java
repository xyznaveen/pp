package np.com.naveenniraula.sahayatri.data.model;

import android.graphics.Color;

public class SeatModel {

    private String seatNumber;
    private boolean isAvailable;
    private int backgroundColor;
    private boolean isSelected;

    public SeatModel() {
    }

    public SeatModel(String seatNumber, boolean isAvailable, int backgroundColor, boolean isSelected) {
        this.seatNumber = seatNumber;
        this.isAvailable = isAvailable;
        this.backgroundColor = backgroundColor;
        this.isSelected = isSelected;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
