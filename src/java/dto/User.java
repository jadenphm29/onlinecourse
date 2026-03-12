package dto;

public class User {
    private int    userId;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;
    private String status;
    private double balance; 

    public User(int userId, String fullName, String email, String password,
                String phone, String address, String role, String status) {
        this.userId   = userId;
        this.fullName = fullName;
        this.email    = email;
        this.password = password;
        this.phone    = phone;
        this.address  = address;
        this.role     = role;
        this.status   = status;
    }

    public User(int userId, String fullName, String email, String password,
                String phone, String address, String role, String status,
                double balance) {
        this(userId, fullName, email, password, phone, address, role, status);
        this.balance = balance;
    }

    public User() {}

    public int    getUserId()               { return userId; }
    public void   setUserId(int userId)     { this.userId = userId; }
    public String getFullName()             { return fullName; }
    public void   setFullName(String v)     { this.fullName = v; }
    public String getEmail()                { return email; }
    public void   setEmail(String v)        { this.email = v; }
    public String getPassword()             { return password; }
    public void   setPassword(String v)     { this.password = v; }
    public String getPhone()                { return phone; }
    public void   setPhone(String v)        { this.phone = v; }
    public String getAddress()              { return address; }
    public void   setAddress(String v)      { this.address = v; }
    public String getRole()                 { return role; }
    public void   setRole(String v)         { this.role = v; }
    public String getStatus()               { return status; }
    public void   setStatus(String v)       { this.status = v; }

    public double getBalance()              { return balance; }
    public void   setBalance(double v)      { this.balance = v; }
}