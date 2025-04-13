package business.dao.loginDAO;

import business.config.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

public class LoginDAOImp implements LoginDAO{
    public static int returnLoginCode = 0;
    @Override
    public int checkLogin(String username, String password) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            String sql = "{call login(?,?,?)}";
            callSt = conn.prepareCall(sql);
            callSt.setString(1, username);
            callSt.setString(2, password);
//            callSt.setInt(3,status);
//            callSt.setString(4, role);
            callSt.registerOutParameter(3, Types.INTEGER);
            callSt.execute();
            returnLoginCode = callSt.getInt(3);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return returnLoginCode;
    }
}
