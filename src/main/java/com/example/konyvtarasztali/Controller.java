package com.example.konyvtarasztali;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ClientInfoStatus;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    @FXML
    private TableView<Konyv> booksTB;
    @FXML
    private TableColumn<Konyv, String> cimTC;
    @FXML
    private TableColumn<Konyv, String> szerzoTC;
    @FXML
    private TableColumn<Konyv, Integer> kiadasTC;
    @FXML
    private TableColumn<Konyv, Integer> oldalTC;

    private Adatbazis _db;
    private List<Konyv> _konyvek = new ArrayList<>();

    @FXML
    public void initialize(){
        cimTC.setCellValueFactory(new PropertyValueFactory<>("title"));
        szerzoTC.setCellValueFactory(new PropertyValueFactory<>("author"));
        kiadasTC.setCellValueFactory(new PropertyValueFactory<>("publish_year"));
        oldalTC.setCellValueFactory(new PropertyValueFactory<>("page_count"));
        try {
            _db = new Adatbazis();
        } catch (SQLException e) {
            Platform.runLater(() -> {
                alert(Alert.AlertType.WARNING, "Hiba", e.getMessage());
                Platform.exit();
            });
        }
    }

    private void ReadKonyv() throws SQLException {
        List<Konyv> konyvek = _db.getAll();
        booksTB.getItems().clear();
        booksTB.getItems().addAll(konyvek);
        _konyvek.addAll(konyvek);
    }

    public void DeleteClick(ActionEvent actionEvent) {
        Konyv selected = SelectedKonyv();
        if (selected == null) return;

        Optional<ButtonType> optionalButtonType =
                alert(Alert.AlertType.CONFIRMATION,"Biztos, hogy törölni szeretné a kiválasztott könyvet?","");
        if (optionalButtonType.isEmpty() || !optionalButtonType.get().equals(ButtonType.OK) && !optionalButtonType.get().equals(ButtonType.YES)){
            return;
        }
        try {
            if (_db.DeleteKonyv(selected.getId())) {
                alert(Alert.AlertType.WARNING, "Sikeres Törlés!", "");
            }else{
                alert(Alert.AlertType.WARNING, "Sikertelen törlés!", "");
            }
            ReadKonyv();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }

    private void sqlAlert(SQLException e) {
        Platform.runLater(() -> {
            alert(Alert.AlertType.WARNING, "Hiba történt az adatbázis kapcsolat kialakításakor!",
                    e.getMessage());
        });
    }


    private Konyv SelectedKonyv() {
        int id = booksTB.getSelectionModel().getSelectedIndex();
        if (id == -1) {
            alert(Alert.AlertType.WARNING, "Előbb válasszon ki egy könyvet a táblázatból!","");
            return null;
        }
        Konyv selected = booksTB.getSelectionModel().getSelectedItem();
        return selected;
    }

    private Optional<ButtonType> alert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }
}
