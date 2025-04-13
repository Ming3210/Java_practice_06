package business.service.employee;

import business.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees(int page, int pageSize);
    void addEmployee(Employee e);
    void updateEmployee(Employee e);
    void deleteEmployee(String employeeId);
    List<Employee> searchEmployeeByNameAndAge(String name, int minAge, int maxAge);
    List<Employee> sortEmployeesBySalary();
    List<Employee> sortEmployeesByName();
    List<Employee> countEmployeesByDepartment();
    int totalEmployeeCount();
    String departmentWithMostEmployees();
    String departmentWithHighestSalary();
}
