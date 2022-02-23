package com.example.muzeumfrontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddStatueController extends Controller {
    @FXML
    private TextField addStTF;
    @FXML
    private Spinner<Integer> addStSp1;
    @FXML
    private Spinner<Integer> addStSp2;

    public void addData() {
        String person = addStTF.getText().trim();
        int height = addStSp1.getValue();
        int price = addStSp2.getValue();
        if (person.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The person field cannot be empty");
            alert.show();
            return;
        }
        try {
            Statue newStatue = new Statue(null, person, height, price);
            boolean successful = Api.addStatue(newStatue);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            if (successful) alert.setHeaderText("Addition successful");
            else alert.setHeaderText("Addition unsuccessful");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
