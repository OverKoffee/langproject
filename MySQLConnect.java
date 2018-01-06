package redmal;

import java.sql.*;

public class MySQLConnect {
    private Connection dbConnection = null;
    private String url1 = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3214145";
    private String url2 = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3214145/UserDatabase";
    private String user = "sql3214145";
    private String password = "EmrDHlTHv3";
    private PreparedStatement preparedStatement;

    public MySQLConnect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(url1, user, password);
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
                    "' and password='" + pw + "'";
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
            System.out.println("An error occurred while trying to connect to database.");
            System.out.println(exc);
        }finally{
            return userVerified;
        }
    }

}
