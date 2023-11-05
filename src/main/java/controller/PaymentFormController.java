package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dto.OrderDTO;
import dto.PaymentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.OrderModel;
import model.PaymentModel;

import java.sql.SQLException;
import java.time.LocalDate;

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
    }

    public void btnViewOnAction(ActionEvent actionEvent) {
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
}
