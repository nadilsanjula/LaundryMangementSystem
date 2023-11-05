package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class ViewPaymentFormController {
    public JFXButton btnBack;
    public TableColumn colOrderId;
    public TableColumn colPaymentDate;
    public TableColumn colPaymentId;
    public AnchorPane viewPaymentPane;
    public TableView tblPayment;
    public TableColumn colAmount;

    public void btnBackOnAction(ActionEvent actionEvent) {
    }
}
