package com.example.lastone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Emprunter  implements Initializable {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = null;
    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private Button btnRechercher;

    @FXML
    private TextField txtCIN;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtClasse;

    @FXML
    private TableView<EmprunterCls> tab;

    @FXML
    private TableColumn<EmprunterCls, String> DateColumn;
    @FXML
    private TableColumn<EmprunterCls,Integer> CINColumn;

    @FXML
    private TableColumn<EmprunterCls, String> NomColumn;

    @FXML
    private TableColumn<EmprunterCls, String> PrenomColumn;

    @FXML
    private TableColumn<EmprunterCls, String> ClasseColumn;


    @FXML
    private TextField txtDate;

   ////////////////////////

    public ObservableList<EmprunterCls> getbook() {
        ObservableList<EmprunterCls> list = FXCollections.observableArrayList();

        String select = "SELECT * FROM biblio.emprunter";
        DatabaseConnection connection = new DatabaseConnection();
        con = connection.getConnection();
        try {
            st = con.prepareStatement(select);
            rs = st.executeQuery();
            while (rs.next()) {
                EmprunterCls book = new EmprunterCls();
                book.setCIN(rs.getInt("CIN"));
                book.setClasse(rs.getString("Classe"));
                book.setNom(rs.getString("Nom"));
                book.setPrenom(rs.getString("Prenom"));
                book.setDate(rs.getString("Date"));
                list.add(book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Emprunter.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public void affiche() {
        ObservableList<EmprunterCls> list = getbook();
        CINColumn.setCellValueFactory(new PropertyValueFactory<EmprunterCls, Integer>("CIN"));
        NomColumn.setCellValueFactory(new PropertyValueFactory<EmprunterCls, String>("Nom"));
        PrenomColumn.setCellValueFactory(new PropertyValueFactory<EmprunterCls, String>("Prenom"));
        ClasseColumn.setCellValueFactory(new PropertyValueFactory<EmprunterCls, String>("Classe"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<EmprunterCls, String>("Date"));

        tab.setItems(list);

    }

    private void insert() {
        DatabaseConnection connection = new DatabaseConnection();
        con = connection.getConnection();
        String insert = "INSERT INTO biblio.emprunter (CIN,Nom,Prenom,Classe,Date)VALUES(?,?,?,?,?)";
        try {
            st = con.prepareStatement(insert);
            st.setInt(1, Integer.parseInt(txtCIN.getText()));
            st.setString(3, txtNom.getText());
            st.setString(4, txtPrenom.getText());
            st.setString(2, txtClasse.getText());
            st.setString(4, txtDate.getText());
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(Emprunter.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    //

    public void delete() {
        DatabaseConnection connection = new DatabaseConnection();
        con = connection.getConnection();
        String delete = "DELETE FROM biblio.emprunter WHERE Nom  = ?";
        try {
            st = con.prepareStatement(delete);

            st.setString(2, txtNom.getText());
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(Emprunter.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    ///////////////////////////////////

    private void update() {
        DatabaseConnection connection = new DatabaseConnection();
        con = connection.getConnection();
        String update
                = "UPDATE emprunter SET Classe =?,CIN = ?,Prenom =?,Date=? where Nom =?";
        try {
            st = con.prepareStatement(update);
            st.setInt(4, Integer.parseInt(txtCIN.getText()));
            st.setString(1, txtClasse.getText());
            st.setString(2, txtNom.getText());
            st.setString(3, txtPrenom.getText());
            st.setString(3, txtDate.getText());

            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(Emprunter.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    void clear() {
        CINColumn.setText(null);
        ClasseColumn.setText(null);
        PrenomColumn.setText(null);
        NomColumn.setText(null);
        DateColumn.setText(null);
        // bsave.setDisable(false);
    }

    @FXML
    private void saveEvent(ActionEvent event) {
        insert();
        clear();
    }

    @FXML
    private void updateEvent(ActionEvent event) {
        update();
        clear();
        //  bsave.setDisable(false);
    }

    @FXML
    private void deleteEvent(ActionEvent event) {
        delete();
        clear();
    }

    @FXML
    private void clearEvent(ActionEvent event) {
        clear();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        affiche();

    }


    //////////////////////////////
    @FXML
    public void retour(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();
        int pred = 0;
        if (pred == 0) {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("choisi.fxml")));
            stage.setScene(scene);
            stage.show();
        }
    }

    public void Rechercher(ActionEvent actionEvent) {
    }
}
