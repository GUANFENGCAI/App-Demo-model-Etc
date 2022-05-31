package MBS;

public class Customer {

    private int customerID;
    private String Name;
    private String Password;

    public Customer(int customerID, String Name, String Password){
        this.customerID = customerID;
        this.Name = Name;
        this.Password = Password;
    }

    public int getCustomerID(){
        return customerID;
    }

    public String getName(){
        return Name;
    }

    public String getPassword(){
        return Password;
    }

    public void setName(String newName){
        this.Name = newName;
    }

    public void setPassword(String newPassword){
        this.Password = newPassword;
    }

}
