package com.example.muzeumfrontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class ChangeStatueController extends Controller{
    @FXML private TextField textfield0;
    @FXML private Spinner<Integer> spinner1;
    @FXML private Spinner<Integer> spinner2;
    private Statue chStatue;
    public Statue getChStatue() {return chStatue;}
    public void setChStatue(Statue chStatue) {
        textfield0.setText(chStatue.getPerson());
        spinner1.getValueFactory().setValue(chStatue.getHeight());
        spinner2.getValueFactory().setValue(chStatue.getPrice());
        this.chStatue = chStatue;
    }

    @FXML public void changeData() {
        String person = textfield0.getText().trim();
        int height = spinner1.getValue();
        int price = spinner2.getValue();
        if (person.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The person field cannot be empty");
            alert.show();
            return;
        }
        if ((height < 0 || height > 255) && (price < 10000)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The number input is not correct");
            alert.show();
            return;
        }
        chStatue.setPerson(person);
        chStatue.setHeight(height);
        chStatue.setPrice(price);
        try {
            Statue successful = Api.changeStatue(chStatue);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            if (successful != null) alert.setHeaderText("Change successful");
            else alert.setHeaderText("Change unsuccessful");
            alert.show();
        } catch (Exception e) {
            errorAlert(e);
        }
    }
}
