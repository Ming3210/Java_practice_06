package business.dao.employee;

import business.config.ConnectionDB;
import business.model.Employee;
import business.model.EmployeeStatus;
import business.model.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> getEmployees(int page, int pageSize) {
        List<Employee> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL get_employees_paginated(?, ?)}";
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, (page - 1) * pageSize);
            callSt.setInt(2, pageSize);
            rs = callSt.executeQuery();

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getString("em_address"),
                        rs.getString("em_date_of_birth"),
                        rs.getInt("em_department_id"),
                        rs.getString("em_email"),
                        rs.getString("em_id"),
                        Gender.valueOf(rs.getString("em_gender")),
                        rs.getString("em_password"),
                        rs.getString("em_phone"),
                        rs.getString("em_role"),
                        rs.getDouble("em_salary"),
                        rs.getDouble("em_salary_tier"),
                        EmployeeStatus.valueOf(rs.getString("em_status")),
                        rs.getString("em_username")
                );
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return list;
    }

    @Override
    public void addEmployee(Employee e) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet generatedKeys = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL add_employee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            callSt = conn.prepareCall(sql);
            callSt.setString(1, e.getUsername());
            callSt.setString(2, e.getPassword());
            callSt.setString(3, e.getEmail());
            callSt.setString(4, e.getPhone());
            callSt.setDouble(5, e.getSalary());
            callSt.setDouble(6, e.getSalaryTier());
            callSt.setInt(7, e.getDepartmentID());
            callSt.setString(8, e.getRole());
            callSt.setString(9, e.getAddress());
            callSt.setString(10, e.getDateOfBirth());
            callSt.setString(11, e.getGender().name());
            callSt.setString(12, e.getStatus().name());

            // Thực thi câu lệnh
            callSt.executeUpdate();

            // Lấy ID nhân viên tự động sinh ra
            generatedKeys = callSt.getGeneratedKeys();
            if (generatedKeys.next()) {
                String employeeId = generatedKeys.getString(1);
                e.setEmployeeID(employeeId);  // Gán ID nhân viên
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }

    @Override
    public void updateEmployee(Employee e) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL update_employee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            callSt = conn.prepareCall(sql);
            callSt.setString(1, e.getEmployeeID());
            callSt.setString(2, e.getUsername());
            callSt.setString(3, e.getPassword());
            callSt.setString(4, e.getEmail());
            callSt.setString(5, e.getPhone());
            callSt.setDouble(6, e.getSalary());
            callSt.setDouble(7, e.getSalaryTier());
            callSt.setInt(8, e.getDepartmentID());
            callSt.setString(9, e.getRole());
            callSt.setString(10, e.getAddress());
            callSt.setString(11, e.getDateOfBirth());
            callSt.setString(12, e.getGender().name());
            callSt.setString(13, e.getStatus().name());

            callSt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }

    @Override
    public void deleteEmployee(String employeeId) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL delete_employee(?)}";
            callSt = conn.prepareCall(sql);
            callSt.setString(1, employeeId);
            callSt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }

    @Override
    public List<Employee> searchEmployeeByNameAndAge(String name, int minAge, int maxAge) {
        List<Employee> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL search_employee_by_name_age(?, ?, ?)}";
            callSt = conn.prepareCall(sql);
            callSt.setString(1, name);
            callSt.setInt(2, minAge);
            callSt.setInt(3, maxAge);
            rs = callSt.executeQuery();

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getString("em_address"),
                        rs.getString("em_date_of_birth"),
                        rs.getInt("em_department_id"),
                        rs.getString("em_email"),
                        rs.getString("em_id"),
                        Gender.valueOf(rs.getString("em_gender")),
                        rs.getString("em_password"),
                        rs.getString("em_phone"),
                        rs.getString("em_role"),
                        rs.getDouble("em_salary"),
                        rs.getDouble("em_salary_tier"),
                        EmployeeStatus.valueOf(rs.getString("em_status")),
                        rs.getString("em_username")
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return list;
    }

    @Override
    public List<Employee> sortEmployeesBySalary() {
        List<Employee> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL sort_employees_by_salary()}";
            callSt = conn.prepareCall(sql);
            rs = callSt.executeQuery();

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getString("em_address"),
                        rs.getString("em_date_of_birth"),
                        rs.getInt("em_department_id"),
                        rs.getString("em_email"),
                        rs.getString("em_id"),
                        Gender.valueOf(rs.getString("em_gender")),
                        rs.getString("em_password"),
                        rs.getString("em_phone"),
                        rs.getString("em_role"),
                        rs.getDouble("em_salary"),
                        rs.getDouble("em_salary_tier"),
                        EmployeeStatus.valueOf(rs.getString("em_status")),
                        rs.getString("em_username")
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return list;
    }

    @Override
    public List<Employee> sortEmployeesByName() {
        List<Employee> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL sort_employees_by_name()}";
            callSt = conn.prepareCall(sql);
            rs = callSt.executeQuery();

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getString("em_address"),
                        rs.getString("em_date_of_birth"),
                        rs.getInt("em_department_id"),
                        rs.getString("em_email"),
                        rs.getString("em_id"),
                        Gender.valueOf(rs.getString("em_gender")),
                        rs.getString("em_password"),
                        rs.getString("em_phone"),
                        rs.getString("em_role"),
                        rs.getDouble("em_salary"),
                        rs.getDouble("em_salary_tier"),
                        EmployeeStatus.valueOf(rs.getString("em_status")),
                        rs.getString("em_username")
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return list;
    }

    @Override
    public List<Employee> countEmployeesByDepartment() {
        List<Employee> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL count_employees_by_department()}";
            callSt = conn.prepareCall(sql);
            rs = callSt.executeQuery();

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getString("de_name"),
                        rs.getInt("employee_count")
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return list;
    }

    @Override
    public int totalEmployeeCount() {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        int totalEmployees = 0;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL total_employee_count()}";
            callSt = conn.prepareCall(sql);
            rs = callSt.executeQuery();

            if (rs.next()) {
                totalEmployees = rs.getInt("total_employees");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return totalEmployees;
    }

    @Override
    public String departmentWithMostEmployees() {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        String departmentName = "";

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL department_with_most_employees()}";
            callSt = conn.prepareCall(sql);
            rs = callSt.executeQuery();

            if (rs.next()) {
                departmentName = rs.getString("de_name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return departmentName;
    }

    @Override
    public String departmentWithHighestSalary() {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        String departmentName = "";

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL department_with_highest_salary()}";
            callSt = conn.prepareCall(sql);
            rs = callSt.executeQuery();

            if (rs.next()) {
                departmentName = rs.getString("de_name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return departmentName;
    }
}
