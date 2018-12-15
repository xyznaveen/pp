package np.com.naveenniraula.sahayatri.data.local;

import android.support.annotation.NonNull;

public class UserEntity {

    private String email;
    private long phoneNumber;
    private String name;
    private String password;
    private String profileUrl;
    private long registrationDate;
    private String userType;

    public UserEntity() {

    }

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @NonNull
    @Override
    public String toString() {
        return "Username: " + getEmail() + "; Password: " + getPassword();
    }
}
