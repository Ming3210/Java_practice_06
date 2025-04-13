package business.service.login;

import business.dao.loginDAO.LoginDAO;
import business.dao.loginDAO.LoginDAOImp;

public class LoginServiceImp implements LoginService{
    private final LoginDAO loginDAO;


    public LoginServiceImp() {
        loginDAO = new LoginDAOImp();
    }


    @Override
    public int checkLogin(String username, String password) {
        return loginDAO.checkLogin(username, password);
    }



}
