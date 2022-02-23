package com.example.muzeumfrontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class ChangeStatueController extends Controller{
    @FXML private TextField changeStTF;
    @FXML private Spinner<Integer> changeStSp1;
    @FXML private Spinner<Integer> changeStSp2;
    private Statue chStatue;
    public Statue getChStatue() {return chStatue;}
    public void setChStatue(Statue chStatue) {
        changeStTF.setText(chStatue.getPerson());
        changeStSp1.getValueFactory().setValue(chStatue.getHeight());
        changeStSp2.getValueFactory().setValue(chStatue.getPrice());
        this.chStatue = chStatue;
    }

    @FXML public void changeData() {
        String person = changeStTF.getText().trim();
        int height = changeStSp1.getValue();
        int price = changeStSp2.getValue();
        if (person.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The person field cannot be empty");
            alert.show();
            return;
        }/*
        if ((height < 0 || height > 255) && (price < 10000)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The number input is not correct");
            alert.show();
            return;
        }*/
        chStatue.setPerson(person);
        chStatue.setHeight(height);
        chStatue.setPrice(price);
        try {
            boolean successful = Api.changeStatue(chStatue);
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
