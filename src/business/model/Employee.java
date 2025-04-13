package business.model;

public class Employee {
    private String username;
    private String password;
    private EmployeeStatus status;
    private String role;
    private String employeeID;
    private String email;
    private String phone;
    private String address;
    private Gender gender;
    private String dateOfBirth;
    private double salaryTier;
    private double salary;
    private int departmentID;
    private int employeeCount;

    public Employee(String address, String dateOfBirth, int departmentID, String email, String employeeID, Gender gender, String password, String phone, String role, double salary, double salaryTier, EmployeeStatus status, String username) {
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.departmentID = departmentID;
        this.email = email;
        this.employeeID = employeeID;
        this.gender = gender;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.salary = salary;
        this.salaryTier = salaryTier;
        this.status = status;
        this.username = username;
    }

    public Employee(String username, int employeeCount) {
        this.username = username;
        this.employeeCount = employeeCount;
    }

    public Employee() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalaryTier() {
        return salaryTier;
    }

    public void setSalaryTier(double salaryTier) {
        this.salaryTier = salaryTier;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
