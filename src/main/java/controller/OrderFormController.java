package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.StaffDTO;
import dto.tm.CustomerTM;
import dto.tm.StaffTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;
import model.OrderModel;
import model.StaffModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderFormController {
    public JFXButton btnView;
    public JFXButton btnDelete;
    public JFXDatePicker PickupDate;
    public JFXDatePicker DeliveryDate;
    public JFXButton btnUpdate;
    public JFXButton btnSave;
    public JFXTextField txtTotalAmount;
    public JFXTextField txtOrderId;
    public AnchorPane orderPane;
    public JFXTextField txtStatus;
    public JFXComboBox comCustomerId;
    public JFXComboBox comStaffId;
    public JFXButton btnplaceOrder;

    private CustomerModel customerModel = new CustomerModel();
    private StaffModel staffModel = new StaffModel();
    ObservableList<OrderDTO> observableList = FXCollections.observableArrayList();



    public void btnSaveOnAction(ActionEvent actionEvent) {
        String orderId = txtOrderId.getText();
        LocalDate pickupDate = PickupDate.getValue();
        LocalDate deliveryDate = DeliveryDate.getValue();
        Double amount = Double.valueOf((txtTotalAmount.getText()));
        String customerId = (String) comCustomerId.getValue();
        String staffId = (String) comStaffId.getValue();


        try {
            boolean isSaved = OrderModel.save(new OrderDTO(orderId, pickupDate, deliveryDate,amount, customerId,staffId));


            if (isSaved) {

                new Alert(Alert.AlertType.CONFIRMATION, "Saved  !!!").show();
                txtOrderId.setText("");
                PickupDate.getValue();
                DeliveryDate.getValue();
                txtTotalAmount.setText("");
                comCustomerId.setValue("");
                comStaffId.setValue("");
                observableList.clear();

            } else {

                new Alert(Alert.AlertType.ERROR, "Not saved  !!!").show();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String orderId = txtOrderId.getText();
        LocalDate pickupDate = PickupDate.getValue();
        LocalDate deliveryDate = DeliveryDate.getValue();
        Double amount = Double.valueOf((txtTotalAmount.getText()));
        String customerId = (String) comCustomerId.getValue();
        String staffId = (String) comStaffId.getValue();

        boolean isUpdated = false;
        try {
            isUpdated = OrderModel.update(new OrderDTO(orderId,pickupDate,deliveryDate,amount,customerId,staffId));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully").show();
                txtOrderId.setText("");
                PickupDate.getValue();
                DeliveryDate.getValue();
                txtTotalAmount.setText("");
                comCustomerId.setValue("");
                comStaffId.setValue("");
                observableList.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String orderId = txtOrderId.getText();

        try {
            boolean isRemoved = OrderModel.remove(orderId);

            if (isRemoved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully").show();
                txtOrderId.setText("");
                PickupDate.getValue();
                DeliveryDate.getValue();
                txtTotalAmount.setText("");
                comCustomerId.setValue("");
                comStaffId.setValue("");
                observableList.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/viewOrderForm.fxml"));
        orderPane.getChildren().clear();
        orderPane.getChildren().add(load);
    }

    public void cmbCustomerIdOnAction(ActionEvent actionEvent) {
        String customerId = (String) comCustomerId.getValue();

        try {
            CustomerDTO customerDTO = CustomerModel.search(customerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerTM> customerDTOS = customerModel.getAll();

            for (CustomerTM dto : customerDTOS) {
                obList.add(dto.getCustomerId());
            }
            comCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        loadCustomerId();
        loadStaffId();
    }

    public void comStaffIdOnAction(ActionEvent actionEvent) {
        String staffId = (String) comStaffId.getValue();

        try {
            StaffDTO staffDTO = StaffModel.search(staffId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadStaffId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<StaffTM> staffTMS = staffModel.getAll();

            for (StaffTM dto : staffTMS) {
                obList.add(dto.getStaffId());
            }
            comStaffId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void idSearchOnAction(ActionEvent actionEvent) {
        String orderId = txtOrderId.getText();

        try {
            OrderDTO orderDTO= OrderModel.search(orderId);

            if (orderDTO != null) {
                txtOrderId.setText(orderDTO.getOrderId());
                PickupDate.setValue(orderDTO.getPickupDate());
                DeliveryDate.setValue(orderDTO.getDeliveryDate());
                txtTotalAmount.setText(String.valueOf(orderDTO.getAmount()));
                comCustomerId.setValue(orderDTO.getCustomerId());
                comStaffId.setValue(orderDTO.getStaffid());
            }else {
                new Alert(Alert.AlertType.ERROR,"Invalid ID").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/placeOrderForm.fxml"));
        orderPane.getChildren().clear();
        orderPane.getChildren().add(load);
    }
}
