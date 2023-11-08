package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.PaymentDTO;
import dto.tm.CustomerTM;
import dto.tm.OrderTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;
import model.OrderModel;
import model.PaymentModel;
import model.StaffModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PaymentFormController {
    public AnchorPane paymentPane;
    public JFXTextField txtPaymentId;
    public JFXTextField txtAmount;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnView;
    public JFXDatePicker paymentDate;
    public JFXComboBox cmbOrderId;

    private OrderModel orderModel = new OrderModel();
    ObservableList<PaymentDTO> observableList = FXCollections.observableArrayList();
    public void btnSaveOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        LocalDate date = paymentDate.getValue();
        String orderId = (String) cmbOrderId.getValue();


        try {
            boolean isSaved = PaymentModel.save(new PaymentDTO(paymentId, amount, date,orderId));


            if (isSaved) {

                new Alert(Alert.AlertType.CONFIRMATION, "Saved  !!!").show();
                txtPaymentId.setText("");
                txtAmount.setText("");
                paymentDate.setValue(LocalDate.parse(""));
                cmbOrderId.setValue("");
                observableList.clear();

            } else {

                new Alert(Alert.AlertType.ERROR, "Not saved  !!!").show();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        LocalDate date = paymentDate.getValue();
        String orderId = (String) cmbOrderId.getValue();

        boolean isUpdated = false;
        try {
            isUpdated = PaymentModel.update(new PaymentDTO(paymentId, amount, date,orderId));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully").show();
                txtPaymentId.setText("");
                txtAmount.setText("");
                paymentDate.setValue(LocalDate.parse(""));
                cmbOrderId.setValue("");
                observableList.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();

        try {
            boolean isRemoved = PaymentModel.remove(paymentId);

            if (isRemoved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully").show();
                txtPaymentId.setText("");
                txtAmount.setText("");
                paymentDate.setValue(LocalDate.parse(""));
                cmbOrderId.setValue("");
                observableList.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/viewPaymentForm.fxml"));
        paymentPane.getChildren().clear();
        paymentPane.getChildren().add(load);
    }

    public void idSearchOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();

        try {
            PaymentDTO paymentDTO = PaymentModel.search(paymentId);

            if (paymentDTO != null) {
                txtPaymentId.setText(paymentDTO.getPaymentId());
                txtAmount.setText(String.valueOf(paymentDTO.getAmount()));
                paymentDate.setValue(paymentDTO.getPaymentDate());
                cmbOrderId.setValue(paymentDTO.getOrderId());
            }else {
                new Alert(Alert.AlertType.ERROR,"Invalid ID").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbOrderIdOnAction(ActionEvent actionEvent) {
        String orderId = (String) cmbOrderId.getValue();

        try {
            OrderDTO orderDTO = OrderModel.search(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<OrderTM> orderTMS = orderModel.getAll();

            for (OrderTM dto : orderTMS) {
                obList.add(dto.getOrderId());
            }
            cmbOrderId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        loadOrderId();
    }
}
