package com.example.lastone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Choisi {

    @FXML
    private Button id1;

    @FXML
    private Button id2;

    @FXML
    private Button id3;

    @FXML
    void Clickme(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root =null;
        if(event.getSource()==id1){
            stage = (Stage) id1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("inscription.fxml"));
        }
        else if (event.getSource()==id2){
            stage = (Stage) id2.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("livre.fxml"));
        }
        else if (event.getSource()==id3){
            stage = (Stage) id3.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("emprunter.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();





    }

}
