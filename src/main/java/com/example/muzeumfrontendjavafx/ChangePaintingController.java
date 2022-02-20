package com.example.muzeumfrontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class ChangePaintingController extends Controller{
    @FXML private TextField textfield0;
    @FXML private Spinner<Integer> spinner1;
    @FXML private CheckBox check2;
    private Painting chPainting;
    public Painting getChPainting() {return chPainting;}
    public void setChPainting(Painting chPainting) {
        textfield0.setText(chPainting.getTitle());
        spinner1.getValueFactory().setValue(chPainting.getYear());
        check2.allowIndeterminateProperty().setValue(chPainting.isOnDisplay());
        this.chPainting = chPainting;
    }

    @FXML public void changeData() {
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
        chPainting.setTitle(title);
        chPainting.setYear(year);
        chPainting.setOnDisplay(onDisplay);
        try {
            Painting successful = Api.changePainting(chPainting);
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
