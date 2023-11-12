package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LaundryEquipmentFormController {
    public AnchorPane laundryEquipmentPane;
    public JFXTextField txtMachineId;
    public JFXTextField txtStatus;
    public JFXTextField txtMachineType;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnView;
    public JFXDatePicker dateRepair;

    public void IdSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/viewLaundryEquipmentForm.fxml"));
        laundryEquipmentPane.getChildren().clear();
        laundryEquipmentPane.getChildren().add(load);
    }
}
