package MBS;

public class Employee {
    private int id;
    private String staffName;
    private String userName;
    private String userPassword;
    private String userIdentity;

    public Employee(int id, String staffName, String userName, String userPassword, String userIdentity) {
        this.id = id;
        this.staffName = staffName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userIdentity = userIdentity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    @Override
    public String toString() {
        return "["
                + id + "*"
                + staffName + "*"
                + userName + "*"
                + userPassword + "*"
                + userIdentity +
                "]";
    }
}
