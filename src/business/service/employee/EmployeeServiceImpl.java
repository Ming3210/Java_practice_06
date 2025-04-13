package business.service.employee;

import business.dao.employee.EmployeeDAO;
import business.dao.employee.EmployeeDAOImpl;
import business.model.Employee;
import business.service.employee.EmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl() {
        employeeDAO = new EmployeeDAOImpl();
    }

    @Override
    public List<Employee> getEmployees(int page, int pageSize) {
        return employeeDAO.getEmployees(page, pageSize);
    }

    @Override
    public void addEmployee(Employee e) {
        employeeDAO.addEmployee(e);
    }

    @Override
    public void updateEmployee(Employee e) {
        employeeDAO.updateEmployee(e);
    }

    @Override
    public void deleteEmployee(String employeeId) {
        employeeDAO.deleteEmployee(employeeId);
    }

    @Override
    public List<Employee> searchEmployeeByNameAndAge(String name, int minAge, int maxAge) {
        return employeeDAO.searchEmployeeByNameAndAge(name, minAge, maxAge);
    }

    @Override
    public List<Employee> sortEmployeesBySalary() {
        return employeeDAO.sortEmployeesBySalary();
    }

    @Override
    public List<Employee> sortEmployeesByName() {
        return employeeDAO.sortEmployeesByName();
    }

    @Override
    public List<Employee> countEmployeesByDepartment() {
        return employeeDAO.countEmployeesByDepartment();
    }

    @Override
    public int totalEmployeeCount() {
        return employeeDAO.totalEmployeeCount();
    }

    @Override
    public String departmentWithMostEmployees() {
        return employeeDAO.departmentWithMostEmployees();
    }

    @Override
    public String departmentWithHighestSalary() {
        return employeeDAO.departmentWithHighestSalary();
    }
}
