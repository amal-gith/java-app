package com.example.lastone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Inscription  implements Initializable {
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
    private TableView<InscriptionCls> tab;

    @FXML
    private TableColumn<InscriptionCls,Integer> CINColumn;

    @FXML
    private TableColumn<InscriptionCls,String> NomColumn;

    @FXML
    private TableColumn<InscriptionCls,String> PrenomColumn;

    @FXML
    private TableColumn<InscriptionCls, String> ClasseColumn;
    ///////////////////////


    public ObservableList<InscriptionCls> getbook() {
        ObservableList<InscriptionCls> list = FXCollections.observableArrayList();

        String select = "SELECT * FROM biblio.etudiants";
        DatabaseConnection connection = new DatabaseConnection();
        con = connection.getConnection();
        try {
            st = con.prepareStatement(select);
            rs = st.executeQuery();
            while (rs.next()) {
                InscriptionCls book = new InscriptionCls();
                book.setCIN(rs.getInt("CIN"));
                book.setClasse(rs.getString("Clasee"));
                book.setNom(rs.getString("Nom"));
                book.setPrenom(rs.getString("Prenom"));

                list.add(book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Inscription.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public void affiche() {
        ObservableList<InscriptionCls> list = getbook();
        CINColumn.setCellValueFactory(new PropertyValueFactory<InscriptionCls, Integer>("CIN"));
        NomColumn.setCellValueFactory(new PropertyValueFactory<InscriptionCls, String>("Nom"));
        PrenomColumn.setCellValueFactory(new PropertyValueFactory<InscriptionCls, String>("Prenom"));
        ClasseColumn.setCellValueFactory(new PropertyValueFactory<InscriptionCls, String>("Classe"));


        tab.setItems(list);

    }

    private void insert() {
        DatabaseConnection connection = new DatabaseConnection();
        con = connection.getConnection();
        String insert = "INSERT INTO biblio.etudiants (CIN,Clasee,Nom,Prenom)VALUES(?,?,?,?)";
        try {
            st = con.prepareStatement(insert);
            st.setInt(1, Integer.parseInt(txtCIN.getText()));
            st.setString(2, txtClasse.getText());
            st.setString(3, txtNom.getText());
            st.setString(4, txtPrenom.getText());

            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(Inscription.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    //

    public void delete() {
        DatabaseConnection connection = new DatabaseConnection();
        con = connection.getConnection();
        String delete = "DELETE FROM biblio.etudiants  WHERE CIN = ?";
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, Integer.parseInt(txtCIN.getText()));
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(Inscription.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    ///////////////////////////////////

    private void update() {
        DatabaseConnection connection = new DatabaseConnection();
        con = connection.getConnection();
        String update
                = "UPDATE etudiants SET Clasee =?,Nom = ?,Prenom =? where CIN =?";
        try {
            st = con.prepareStatement(update);
            st.setInt(4, Integer.parseInt(txtCIN.getText()));
            st.setString(1, txtClasse.getText());
            st.setString(2, txtNom.getText());
            st.setString(3, txtPrenom.getText());

            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(Inscription.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    void clear() {
        CINColumn.setText(null);
        ClasseColumn.setText(null);
        PrenomColumn.setText(null);
        NomColumn.setText(null);
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

    /////////////////////////////////////







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        affiche();

    }
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


    public void tablehandleButtonAction(javafx.scene.input.MouseEvent mouseEvent) {
        InscriptionCls book = tab.getSelectionModel().getSelectedItem();
        txtCIN.setText(String.valueOf(book.getCIN()));
        txtClasse.setText(book.getClasse());
        txtNom.setText(book.getNom());
        txtPrenom.setText(book.getPrenom());
        //btnAjouter.setDisable(false);
       //btnModifier.setDisable(true);
        //btnSupprimer.setDisable(true);
        //btnRechercher.setDisable(true);
    }

    public void Clickme(ActionEvent actionEvent) {
    }
}
//////////////////////////////////////////////////////////////////////////////
