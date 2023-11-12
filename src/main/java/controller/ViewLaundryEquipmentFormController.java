package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ViewLaundryEquipmentFormController {
    public AnchorPane viewLaundryEquipmentPane;
    public TableView tblLaundryEquipment;
    public TableColumn colItemId;
    public TableColumn colName;
    public TableColumn colDesc;
    public TableColumn colQuantity;
    public JFXButton btnBack;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/laundryEquipmentForm.fxml"));
        viewLaundryEquipmentPane.getChildren().clear();
        viewLaundryEquipmentPane.getChildren().add(load);
    }
}
