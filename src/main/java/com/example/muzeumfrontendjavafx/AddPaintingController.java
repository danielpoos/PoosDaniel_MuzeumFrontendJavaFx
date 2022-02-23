package com.example.muzeumfrontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddPaintingController extends Controller {
    @FXML
    private TextField addPaTF;
    @FXML
    private Spinner<Integer> addPaSp;
    @FXML
    private CheckBox addPaCheck;

    public void addData() {
        String title = addPaTF.getText().trim();
        int year = addPaSp.getValue();
        boolean onDisplay = addPaCheck.isSelected();
        if (title.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The title field cannot be empty");
            alert.show();
            return;
        }
        try {
            Painting newPainting = new Painting(0, title, year, onDisplay);
            boolean successful = Api.addPainting(newPainting);
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

