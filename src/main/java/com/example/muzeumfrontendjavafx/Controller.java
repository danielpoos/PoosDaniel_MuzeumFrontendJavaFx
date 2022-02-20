package com.example.muzeumfrontendjavafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    @FXML private TableView<Statue> statueTableView;
    @FXML private TableView<Painting> paintingTableView;
    @FXML private TableColumn<Statue,String> personCol;
    @FXML private TableColumn<Statue,Integer> heightCol;
    @FXML private TableColumn<Statue,Integer> priceCol;
    @FXML private TableColumn<Painting,String> titleCol;
    @FXML private TableColumn<Painting,Integer> yearCol;
    @FXML private TableColumn<Painting,Boolean> onDisplayCol;
    @FXML private TabPane tab;
    protected int tabIndex;
    protected Stage stage;

    public void initialize(){
        personCol.setCellValueFactory(new PropertyValueFactory<>("person"));
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        onDisplayCol.setCellValueFactory(new PropertyValueFactory<>("onDisplay"));
        loadStatueTable();
        loadPaintingTable();
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
                addController.stage.setOnCloseRequest(event -> loadStatueTable());
                addController.stage.show();
            }catch (IOException e){
                errorAlert(e);
            }
        }
        else if (tabIndex == 1){
            try{
                AddPaintingController addController = (AddPaintingController) newStage("add-painting-view.fxml", "Adding new painting");
                addController.stage.setOnCloseRequest(event -> loadPaintingTable());
                addController.stage.show();
            }catch (IOException e){
                errorAlert(e);
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
                changeController.stage.setOnHiding(event -> statueTableView.refresh());
                changeController.stage.show();
            }catch (IOException e){
                errorAlert(e);
            }
        }
        else if (tabIndex == 1){
            if (selectedIndex(paintingTableView.getSelectionModel().getSelectedIndex())) return;
            Painting onChangePainting = paintingTableView.getSelectionModel().getSelectedItem();
            try{
                ChangePaintingController changeController = (ChangePaintingController) newStage("change-painting-view.fxml", "Changing selected painting");
                changeController.setChPainting(onChangePainting);
                changeController.stage.setOnHiding(event -> paintingTableView.refresh());
                changeController.stage.show();
            }catch (IOException e){
                errorAlert(e);
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
            boolean s = result.get() == ButtonType.OK;
            if (s) return;
            try {
                boolean success = Api.deleteStatue(onDeleteStatue.getId());
                loadStatueTable();
            } catch (Exception e) {
                errorAlert(e);
            }
        }
        else if (tabIndex == 1){
            if (selectedIndex(paintingTableView.getSelectionModel().getSelectedIndex())) return;
            Painting onDeletePainting = paintingTableView.getSelectionModel().getSelectedItem();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Are you sure about deleting this painting: "+ onDeletePainting.getTitle());
            Optional<ButtonType> result = a.showAndWait();
            boolean s = result.get() == ButtonType.OK;
            if (s) return;
            try {
                boolean success = Api.deletePainting(onDeletePainting.getId());
                loadPaintingTable();
            } catch (Exception e) {
                errorAlert(e);
            }
        }
    }
    private boolean selectedIndex(int selectedIndex) {
        if (selectedIndex == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No item was selected");
            alert.getButtonTypes().add(ButtonType.OK);
            alert.show();
            return true;
        }
        return false;
    }
    public static Controller newStage(String fxml, String title) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Controller controller = fxmlLoader.getController();
        controller.stage = stage;
        return controller;
    }
    protected void errorAlert(Exception e){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(e.getClass().toString());
        a.setContentText(e.getMessage());
        Timer t = new Timer();
        t.schedule(new TimerTask(){@Override public void run() {Platform.runLater(a::show);}},500);
    }
}