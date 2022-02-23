package com.example.muzeumfrontendjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import static com.example.muzeumfrontendjavafx.Controller.newStage;

public class MainController {
    @FXML private TableView<Statue> statueTableView;
    @FXML private TableView<Painting> paintingTableView;
    @FXML private TableColumn<Statue,String> personCol;
    @FXML private TableColumn<Statue,Integer> heightCol;
    @FXML private TableColumn<Statue,Integer> priceCol;
    @FXML private TableColumn<Painting,String> titleCol;
    @FXML private TableColumn<Painting,Integer> yearCol;
    @FXML private TableColumn<Painting,Boolean> onDisplayCol;
    @FXML private TabPane tab;
    private int tabIndex;

    public void initialize(){
        loadStatueTable();
        loadPaintingTable();
        personCol.setCellValueFactory(new PropertyValueFactory<>("person"));
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        onDisplayCol.setCellValueFactory(new PropertyValueFactory<>("onDisplay"));
    }
    @FXML public void tabClick() {
        tabIndex = tab.getSelectionModel().getSelectedIndex();
        statueTableView.getSelectionModel().clearSelection();
        paintingTableView.getSelectionModel().clearSelection();
    }
    private void loadStatueTable(){
        try {
            List<Statue> statues = Api.getStatues();
            statueTableView.getItems().clear();
            for (Statue s:statues) {
                statueTableView.getItems().add(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadPaintingTable(){
        try {
            List<Painting> paintings = Api.getPaintings();
            paintingTableView.getItems().clear();
            for (Painting p:paintings) {
                paintingTableView.getItems().add(p);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addBtnClick(){
        if (tabIndex == 0){
            try{
                AddStatueController addController = (AddStatueController) newStage("add-statue-view.fxml", "Adding new statue");
                addController.getStage().setOnCloseRequest(event -> loadStatueTable());
                addController.getStage().show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else if (tabIndex == 1){
            try{
                AddPaintingController addController = (AddPaintingController) newStage("add-painting-view.fxml", "Adding new painting");
                addController.getStage().setOnCloseRequest(event -> loadPaintingTable());
                addController.getStage().show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void modBtnClick() {
        if (tabIndex == 0){
            if (selectedIndex(statueTableView.getSelectionModel().getSelectedIndex())) return;
            Statue onChangeStatue = statueTableView.getSelectionModel().getSelectedItem();
            try{
                ChangeStatueController changeController = (ChangeStatueController) newStage("change-statue-view.fxml", "Changing selected statue");
                changeController.setChStatue(onChangeStatue);
                changeController.getStage().setOnHiding(event -> statueTableView.refresh());
                changeController.getStage().setOnCloseRequest(event -> loadStatueTable());
                changeController.getStage().show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else if (tabIndex == 1){
            if (selectedIndex(paintingTableView.getSelectionModel().getSelectedIndex())) return;
            Painting onChangePainting = paintingTableView.getSelectionModel().getSelectedItem();
            try{
                ChangePaintingController changeController = (ChangePaintingController) newStage("change-painting-view.fxml", "Changing selected painting");
                changeController.setChPainting(onChangePainting);
                changeController.getStage().setOnHiding(event -> paintingTableView.refresh());
                changeController.getStage().setOnCloseRequest(event -> loadPaintingTable());
                changeController.getStage().show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void delBtnClick() {
        if (tabIndex == 0){
            if (selectedIndex(statueTableView.getSelectionModel().getSelectedIndex())) return;
            Statue onDeleteStatue = statueTableView.getSelectionModel().getSelectedItem();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Are you sure about deleting the statue of: "+ onDeleteStatue.getPerson());
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() != ButtonType.OK) return;
            try {
                boolean success = Api.deleteStatue(onDeleteStatue.getId());
                a = new Alert(Alert.AlertType.INFORMATION);
                if (success) {
                    a.setContentText("Deletion successful");
                }else{
                    a.setContentText("Deletion unsuccessful");
                }
                a.show();
                loadStatueTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (tabIndex == 1){
            if (selectedIndex(paintingTableView.getSelectionModel().getSelectedIndex())) return;
            Painting onDeletePainting = paintingTableView.getSelectionModel().getSelectedItem();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Are you sure about deleting this painting: "+ onDeletePainting.getTitle());
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() != ButtonType.OK) return;
            try {
                boolean success = Api.deletePainting(onDeletePainting.getId());
                a = new Alert(Alert.AlertType.INFORMATION);
                if (success) {
                    a.setContentText("Deletion successful");
                }else{
                    a.setContentText("Deletion unsuccessful");
                }
                a.show();
                loadPaintingTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private boolean selectedIndex(int selectedIndex) {
        if (selectedIndex == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No item was selected");
            alert.show();
            return true;
        }
        return false;
    }
}