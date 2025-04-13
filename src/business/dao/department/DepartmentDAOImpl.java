package business.dao.department;

import business.config.ConnectionDB;
import business.model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {

    @Override
    public List<Department> getDepartments(int page, int pageSize) {
        List<Department> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL get_departments_paginated(?, ?)}";
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, (page - 1) * pageSize);
            callSt.setInt(2, pageSize);
            rs = callSt.executeQuery();

            while (rs.next()) {
                Department d = new Department(
                        rs.getInt("de_id"),
                        rs.getString("de_name"),
                        rs.getString("de_description"),
                        rs.getBoolean("de_status")
                );
                list.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching departments: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return list;
    }

    @Override
    public void addDepartment(Department d) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL add_department(?, ?, ?)}";
            callSt = conn.prepareCall(sql);
            callSt.setString(1, d.getName());
            callSt.setString(2, d.getDescription());
            callSt.setBoolean(3, d.isStatus());
            callSt.execute();
        } catch (SQLException e) {
            System.err.println("Error adding department: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }

    @Override
    public void updateDepartment(Department d) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL update_department(?, ?, ?, ?)}";
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, d.getId());
            callSt.setString(2, d.getName());
            callSt.setString(3, d.getDescription());
            callSt.setBoolean(4, d.isStatus());
            callSt.execute();
        } catch (SQLException e) {
            System.err.println("Error updating department: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }

    @Override
    public boolean deleteDepartment(int id) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL delete_department_if_empty(?, ?)}";
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, id);
            callSt.registerOutParameter(2, Types.INTEGER);
            callSt.execute();
            return callSt.getInt(2) == 1;
        } catch (SQLException e) {
            System.err.println("Error deleting department: " + e.getMessage());
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }

    @Override
    public List<Department> searchByName(String keyword) {
        List<Department> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{CALL search_department_by_name(?)}";
            callSt = conn.prepareCall(sql);
            callSt.setString(1, keyword);
            rs = callSt.executeQuery();

            while (rs.next()) {
                Department d = new Department(
                        rs.getInt("de_id"),
                        rs.getString("de_name"),
                        rs.getString("de_description"),
                        rs.getBoolean("de_status")
                );
                list.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error searching department: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return list;
    }
}
