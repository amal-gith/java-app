

module com.example.lastone {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.sql;

        opens com.example.lastone to javafx.fxml;
        exports com.example.lastone;

        }