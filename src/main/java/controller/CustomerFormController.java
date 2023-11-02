package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerFormController {
    public AnchorPane customerPane;
    public JFXTextField txtCustomerId;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtNumber;
    public JFXTextField txtNIC;
    public JFXTextField txtAddress;
    public JFXButton btnView;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnSave;


    ObservableList<CustomerDTO> observableList = FXCollections.observableArrayList();


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        Integer telNum = Integer.valueOf(txtNumber.getText());
        String nic = txtNIC.getText();
        String email = txtEmail.getText();


        try {
            boolean isSaved = CustomerModel.save(new CustomerDTO(customerId, name, email,address, telNum, nic));


            if (isSaved) {

                new Alert(Alert.AlertType.CONFIRMATION, "Saved  !!!").show();
                txtCustomerId.setText("");
                txtName.setText("");
                txtAddress.setText("");
                txtNumber.setText("");
                txtNIC.setText("");
                txtEmail.setText("");
                observableList.clear();

            } else {

                new Alert(Alert.AlertType.ERROR, "Not saved  !!!").show();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        int telNum = Integer.valueOf(txtNumber.getText());
        String nic = txtNIC.getText();

        boolean isUpdated = false;
        try {
            isUpdated = CustomerModel.update(new CustomerDTO(customerId, name,email, address,telNum, nic));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully").show();
                txtCustomerId.setText("");
                txtName.setText("");
                txtAddress.setText("");
                txtNumber.setText("");
                txtNIC.setText("");
                txtEmail.setText("");
                observableList.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();

        try {
            boolean isRemoved = CustomerModel.remove(customerId);

            if (isRemoved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully").show();
                txtCustomerId.setText("");
                txtName.setText("");
                txtAddress.setText("");
                txtNumber.setText("");
                txtNIC.setText("");
                txtEmail.setText("");
                observableList.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/viewCustomerForm.fxml"));
        customerPane.getChildren().clear();
        customerPane.getChildren().add(load);
    }

    public void IdSearchOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();

        try {
            CustomerDTO customerDTO= CustomerModel.search(customerId);

            if (customerDTO != null) {
                txtCustomerId.setText(customerDTO.getCustomerId());
                txtName.setText(customerDTO.getName());
                txtEmail.setText(customerDTO.getEmail());
                txtAddress.setText(customerDTO.getAddress());
                txtNumber.setText(String.valueOf(customerDTO.getTelNum()));
                txtNIC.setText(customerDTO.getNic());
            }else {
                new Alert(Alert.AlertType.ERROR,"Invalid ID").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
