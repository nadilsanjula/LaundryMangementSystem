package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.StaffDTO;
import dto.SupplierDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.StaffModel;
import model.SupplierModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SupplierFormController {
    public AnchorPane supplierPane;
    public JFXTextField txtSupplierId;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnView;
    public JFXTextField txtAddress;
    public JFXTextField txtNumber;
    public JFXTextField txtEmail;
    public JFXTextField txtName;

    ObservableList<SupplierDTO> observableList = FXCollections.observableArrayList();
    public void idSearchOnAction(ActionEvent actionEvent) {
        String supplierId = txtSupplierId.getText();

        try {
            SupplierDTO supplierDTO= SupplierModel.search(supplierId);

            if (supplierDTO != null) {
                txtSupplierId.setText(supplierDTO.getSupplierId());
                txtName.setText(supplierDTO.getName());
                txtEmail.setText(supplierDTO.getEmail());
                txtNumber.setText(String.valueOf(supplierDTO.getTelnum()));
                txtAddress.setText(supplierDTO.getAddress());
            }else {
                new Alert(Alert.AlertType.ERROR,"Invalid ID").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean isValead = validateSupplier();

        if (isValead) {
            String supplierId = txtSupplierId.getText();
            String name = txtName.getText();
            String email = txtEmail.getText();
            Integer telNum = Integer.valueOf(txtNumber.getText());
            String address = txtAddress.getText();

            try {
                boolean isSaved = SupplierModel.save(new SupplierDTO(supplierId, name, email, telNum, address));


                if (isSaved) {

                    new Alert(Alert.AlertType.CONFIRMATION, "Saved  !!!").show();
                    txtSupplierId.setText("");
                    txtName.setText("");
                    txtEmail.setText("");
                    txtNumber.setText("");
                    txtAddress.setText("");
                    observableList.clear();

                } else {

                    new Alert(Alert.AlertType.ERROR, "Not saved  !!!").show();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateSupplier() {
        String id = txtSupplierId.getText();
        boolean idMatch = Pattern.matches("[S]\\d{3,}", id);
        if (!idMatch) {
            new Alert(Alert.AlertType.ERROR, "invalid id").show();
            return false;
        }
        return true;
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean isValead = validateSupplier();

        if (isValead) {
            String supplierId = txtSupplierId.getText();
            String name = txtName.getText();
            String email = txtEmail.getText();
            Integer telNum = Integer.valueOf(txtNumber.getText());
            String address = txtAddress.getText();

            boolean isUpdated = false;
            try {
                isUpdated = SupplierModel.update(new SupplierDTO(supplierId, name, email, telNum, address));
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully").show();
                    txtSupplierId.setText("");
                    txtName.setText("");
                    txtEmail.setText("");
                    txtNumber.setText("");
                    txtAddress.setText("");
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
        String supplierId = txtSupplierId.getText();

        try {
            boolean isRemoved = SupplierModel.remove(supplierId);

            if (isRemoved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully").show();
                txtSupplierId.setText("");
                txtName.setText("");
                txtEmail.setText("");
                txtNumber.setText("");
                txtAddress.setText("");
                observableList.clear();


            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/viewSupplierForm.fxml"));
        supplierPane.getChildren().clear();
        supplierPane.getChildren().add(load);
    }
}
