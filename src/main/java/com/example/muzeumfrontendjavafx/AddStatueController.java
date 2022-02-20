package com.example.muzeumfrontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddStatueController extends Controller {
    @FXML
    private TextField textfield0;
    @FXML
    private Spinner<Integer> spinner1;
    @FXML
    private Spinner<Integer> spinner2;

    public void addData() {
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
        try {
            Statue newStatue = new Statue(0, person, height, price);
            Statue successful = Api.addStatue(newStatue);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            if (successful != null) alert.setHeaderText("Addition successful");
            else alert.setHeaderText("Addition unsuccessful");
            alert.show();
        } catch (Exception e) {
            errorAlert(e);
        }
    }

}
