package controller;

import com.jfoenix.controls.JFXButton;
import dto.tm.LaundryEquipmentTM;
import dto.tm.LaundryItemTM;
import dto.tm.OrderTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.LaundryEquipmentModel;
import model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewLaundryEquipmentFormController implements Initializable {
    public AnchorPane viewLaundryEquipmentPane;
    public TableView<LaundryEquipmentTM> tblLaundryEquipment;

    public JFXButton btnBack;
    public TableColumn colMachineId;
    public TableColumn colType;
    public TableColumn colStatus;
    public TableColumn colRepairDate;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/laundryEquipmentForm.fxml"));
        viewLaundryEquipmentPane.getChildren().clear();
        viewLaundryEquipmentPane.getChildren().add(load);
    }

    private void getAll() {
        try {
            List<LaundryEquipmentTM> laundryEquipmentTMS = LaundryEquipmentModel.getAll();
            ObservableList<LaundryEquipmentTM> list = FXCollections.observableArrayList();
            for (LaundryEquipmentTM laundryEquipmentTM :laundryEquipmentTMS){
                list.add(
                        new LaundryEquipmentTM(
                                laundryEquipmentTM.getMachineId(),
                                laundryEquipmentTM.getMachineType(),
                                laundryEquipmentTM.getStatus(),
                                laundryEquipmentTM.getNextRepairDate()
                        ));
            }
            tblLaundryEquipment.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {

        colMachineId.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("machineType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRepairDate.setCellValueFactory(new PropertyValueFactory<>("nextRepairDate"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }
}
