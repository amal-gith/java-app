package com.example.lastone;

import java.sql.Connection;
import java.sql.DriverManager;



public class DatabaseConnection {

        public Connection link;

        public Connection getConnection() {
            String databaseName="biblio";
            String databaseUser="root";
            String databasePassword="Amal123*";
            String url="jdbc:mysql://localhost:3306/"+databaseName;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                link= DriverManager.getConnection(url,databaseUser,databasePassword);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return link;
        }

}
