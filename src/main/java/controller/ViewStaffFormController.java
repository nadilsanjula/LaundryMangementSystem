package controller;

import com.jfoenix.controls.JFXButton;
import dto.tm.CustomerTM;
import dto.tm.StaffTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;
import model.StaffModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewStaffFormController implements Initializable {
    public AnchorPane viewStaffPane;
    public TableView <StaffTM> tblStaff;
    public TableColumn colStaffId;
    public TableColumn colName;
    public TableColumn colTelNumber;
    public TableColumn colEmail;
    public TableColumn colJobRole;
    public JFXButton btnBack;

    private void getAll() {
        try {
            List<StaffTM> staffTMS = StaffModel.getAll();
            ObservableList<StaffTM> list = FXCollections.observableArrayList();
            for (StaffTM staffTM :staffTMS){
                list.add(
                        new StaffTM(
                                staffTM.getStaffId(),
                                staffTM.getName(),
                                staffTM.getEmail(),
                                staffTM.getTelNum(),
                                staffTM.getJobRole()
                        ));
            }
            tblStaff.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {

        colStaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelNumber.setCellValueFactory(new PropertyValueFactory<>("telNum"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/staffForm.fxml"));
        viewStaffPane.getChildren().clear();
        viewStaffPane.getChildren().add(load);

    }
}
