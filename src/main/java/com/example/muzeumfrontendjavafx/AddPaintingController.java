package com.example.muzeumfrontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddPaintingController extends Controller {
    @FXML
    private TextField textfield0;
    @FXML
    private Spinner<Integer> spinner1;
    @FXML
    private CheckBox check2;

    public void addData() {
        String title = textfield0.getText().trim();
        int year = spinner1.getValue();
        boolean onDisplay = check2.isSelected();
        if (title.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The title field cannot be empty");
            alert.show();
            return;
        }
        try {
            Painting newPainting = new Painting(0, title, year, onDisplay);
            Painting successful = Api.addPainting(newPainting);
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

