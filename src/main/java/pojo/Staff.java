package pojo;

/**
 * Author : abhishek
 * Created on 4/3/15.
 */
public class Staff {

    private String staffNum;
    private String fname;
    private String lname;
    private String status;
    private String addrStreet;
    private String addrCity;
    private String addrCountry;
    private String postalCode;
    private String gender;
    private String dob;
    private String position;
    private String workLocation;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddrCountry() {
        return addrCountry;
    }

    public void setAddrCountry(String addrCountry) {
        this.addrCountry = addrCountry;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffNum='" + staffNum + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", status='" + status + '\'' +
                ", addrStreet='" + addrStreet + '\'' +
                ", addrCity='" + addrCity + '\'' +
                ", addrCountry='" + addrCountry + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", position='" + position + '\'' +
                ", workLocation='" + workLocation + '\'' +
                '}';
    }
}
