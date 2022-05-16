/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

/**
 *
 * @author USER
 */
public class UserSession {
    private static String userLogin;
    private static String username;
    private static String userId;
    
    static void setUserLogin (String userLogin){
        UserSession.userLogin = userLogin;                
    }
    public static String getUserLogin(){
        return userLogin;
    } 

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserSession.username = username;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        UserSession.userId = userId;
    }

    
     
    
    
}
