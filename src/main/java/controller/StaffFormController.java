package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.StaffDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;
import model.ItemModel;
import model.StaffModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class StaffFormController {
    public AnchorPane staffPane;
    public JFXTextField txtStaffId;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtNumber;
    public JFXTextField txtRole;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnView;

    ObservableList<StaffDTO> observableList = FXCollections.observableArrayList();

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean isValead = validateStaff();

        if (isValead) {
            String staffId = txtStaffId.getText();
            String name = txtName.getText();
            String email = txtEmail.getText();
            Integer num = Integer.valueOf(txtNumber.getText());
            String jobRole = txtRole.getText();

            try {
                boolean isSaved = StaffModel.save(new StaffDTO(staffId, name, email, num, jobRole));


                if (isSaved) {

                    new Alert(Alert.AlertType.CONFIRMATION, "Saved  !!!").show();
                    txtStaffId.setText("");
                    txtName.setText("");
                    txtNumber.setText("");
                    txtNumber.setText("");
                    txtRole.setText("");
                    txtEmail.setText("");
                    observableList.clear();

                } else {

                    new Alert(Alert.AlertType.ERROR, "Not saved  !!!").show();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateStaff() {
        String id = txtStaffId.getText();
        boolean idMatch = Pattern.matches("[S]\\d{3,}", id);
        if (!idMatch) {
            new Alert(Alert.AlertType.ERROR, "invalid id").show();
            return false;
        }
        return true;
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean isValead = validateStaff();

        if (isValead) {
            String staffId = txtStaffId.getText();
            String name = txtName.getText();
            Integer num = Integer.valueOf(txtNumber.getText());
            String jobRole = txtRole.getText();
            String email = txtEmail.getText();

            boolean isUpdated = false;
            try {
                isUpdated = StaffModel.update(new StaffDTO(staffId, name, email, num, jobRole));
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully").show();
                    txtStaffId.setText("");
                    txtName.setText("");
                    txtNumber.setText("");
                    txtRole.setText("");
                    txtEmail.setText("");
                    observableList.clear();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Update failed").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String staffId = txtStaffId.getText();

        try {
            boolean isRemoved = StaffModel.remove(staffId);

            if (isRemoved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully").show();
                txtStaffId.setText("");
                txtName.setText("");
                txtEmail.setText("");
                txtNumber.setText("");
                txtRole.setText("");
                observableList.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/viewStaffForm.fxml"));
        staffPane.getChildren().clear();
        staffPane.getChildren().add(load);
    }

    public void idSearchOnAction(ActionEvent actionEvent) {
        String staffId = txtStaffId.getText();

        try {
            StaffDTO staffDTO= StaffModel.search(staffId);

            if (staffDTO != null) {
                txtStaffId.setText(staffDTO.getStaffId());
                txtName.setText(staffDTO.getName());
                txtEmail.setText(staffDTO.getEmail());
                txtNumber.setText(String.valueOf(staffDTO.getTelNum()));
                txtRole.setText(staffDTO.getJobRole());
            }else {
                new Alert(Alert.AlertType.ERROR,"Invalid ID").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
