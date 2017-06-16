package com.jsf.entitymanager;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

@ManagedBean
@SessionScoped
public class LoginBean {

    private static final String PERSISTENCE_UNIT_NAME = "JSFEntityManager";

    private String name;
    private String password;
    private String userName;

    public LoginBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Method To Check User's Authentication Credentials
    public String validateLoginCredentials() {
        String validationResult = "";

        EntityManager entityMgrObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
        Query queryObj = entityMgrObj.createQuery("SELECT u FROM UserEntityManager u WHERE u.login = :login and u.password = :password");
        queryObj.setParameter("login", userName);
        queryObj.setParameter("password", password);

        try {
            UserEntityManager userResultSetObj = (UserEntityManager) queryObj.getSingleResult();
            if (
                    (userResultSetObj != null) &&
                            (userName.equalsIgnoreCase(userResultSetObj.getLogin()) &&
                                    (password.equals(userResultSetObj.getPassword())))) {
                validationResult = "success";
                name = userResultSetObj.getName();
            }
        }
        catch (Exception exObj) {
            validationResult = "login";
            FacesContext.getCurrentInstance().addMessage("loginForm:loginName", new FacesMessage("Username Or Password Is Incorrect"));
        }
        return validationResult;
    }
}
