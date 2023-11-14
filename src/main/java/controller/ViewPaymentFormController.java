package controller;

import com.jfoenix.controls.JFXButton;
import dto.tm.PaymentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.PaymentModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewPaymentFormController implements Initializable {
    public JFXButton btnBack;
    public TableColumn colOrderId;
    public TableColumn colPaymentDate;
    public TableColumn colPaymentId;
    public AnchorPane viewPaymentPane;
    public TableView tblPayment;
    public TableColumn colAmount;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/paymentForm.fxml"));
        viewPaymentPane.getChildren().clear();
        viewPaymentPane.getChildren().add(load);
    }

    private void getAll() {
        try {
            List<PaymentTM> paymentTMS = PaymentModel.getAll();
            ObservableList<PaymentTM> list = FXCollections.observableArrayList();
            for (PaymentTM paymentTM : paymentTMS) {
                list.add(
                        new PaymentTM(
                                paymentTM.getPaymentId(),
                                paymentTM.getAmount(),
                                paymentTM.getPaymentDate(),
                                paymentTM.getOrderId()
                        ));
            }
            tblPayment.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

}
