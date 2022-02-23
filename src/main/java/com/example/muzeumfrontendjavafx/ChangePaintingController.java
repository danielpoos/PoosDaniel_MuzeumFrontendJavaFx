package com.example.muzeumfrontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class ChangePaintingController extends Controller{
    @FXML private TextField changePaTF;
    @FXML private Spinner<Integer> changePaSp;
    @FXML private CheckBox changePaCheck;
    private Painting chPainting;
    public Painting getChPainting() {return chPainting;}
    public void setChPainting(Painting chPainting) {
        changePaTF.setText(chPainting.getTitle());
        changePaSp.getValueFactory().setValue(chPainting.getYear());
        changePaCheck.allowIndeterminateProperty().setValue(chPainting.isOnDisplay());
        this.chPainting = chPainting;
    }

    @FXML public void changeData() {
        String title = changePaTF.getText().trim();
        int year = changePaSp.getValue();
        boolean onDisplay = changePaCheck.isSelected();
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
            boolean successful = Api.changePainting(chPainting);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            if (successful) alert.setHeaderText("Change successful");
            else alert.setHeaderText("Change unsuccessful");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
