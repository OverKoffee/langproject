package redmal;

import java.sql.*;

public class MySQLConnect {
    private Connection dbConnection = null;
    private String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3214145";
    private String user = "sql3214145";
    private String password = "EmrDHlTHv3";
    private PreparedStatement preparedStatement;

    public MySQLConnect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(url, user, password);
            if (dbConnection != null){
                System.out.println("Connected to database successfully.");
            }
        }catch (ClassNotFoundException | SQLException exc){
            System.out.println("An error occurred while trying to connect to database.");
            System.out.println(exc);
        }
    }

    
    public boolean verifyLogin(String username, String pw) {
        boolean userVerified = false;
        try {
            String selectSQL = "SELECT * FROM UserDatabase WHERE Username ='" + username +
                    "' and Password='" + pw + "'";
            preparedStatement = dbConnection.prepareStatement(selectSQL);

            //Execute select SQL statement
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                userVerified = true;
                System.out.println("Logged in successfully.");
            }
            else {
                userVerified = false;
            }
        }catch(SQLException exc){
            System.out.println(exc);
        }finally{
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
            return userVerified;
        }
    }

    public void createNewUser(String username, String pw, String email){
        try {
            String selectSQL = "SELECT * FROM UserDatabase WHERE Username ='" + username +
                    "' and password='" + pw + "'";
            preparedStatement = dbConnection.prepareStatement(selectSQL);

            //Execute select SQL statement
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                System.out.println("Logged in successfully.");
            }
            else {
            }
        }catch(SQLException exc){
            System.out.println(exc);
        }finally{
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

}
