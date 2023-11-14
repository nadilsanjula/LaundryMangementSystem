package controller;

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
import model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewOrderFormController implements Initializable {
    public AnchorPane viewOrderPane;
    public TableView <OrderTM> tblOrder;
    public TableColumn colOrderID;
    public TableColumn colPickupDate;
    public TableColumn colDeliveryDate;
    public TableColumn colAmount;
    public TableColumn colStatus;
    public TableColumn colCustomerID;
    public TableColumn colStaffID;


    private void getAll() {
        try {
            List<OrderTM> orderTMList = OrderModel.getAll();
            ObservableList<OrderTM> list = FXCollections.observableArrayList();
            for (OrderTM orderTM :orderTMList){
                list.add(
                        new OrderTM(
                                orderTM.getOrderId(),
                                orderTM.getPickupDate(),
                                orderTM.getDeliveryDate(),
                                orderTM.getAmount(),
                                orderTM.getCustomerId(),
                                orderTM.getStaffid()
                        ));
            }
            tblOrder.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {

        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPickupDate.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        colDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colStaffID.setCellValueFactory(new PropertyValueFactory<>("staffid"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/orderForm.fxml"));
        viewOrderPane.getChildren().clear();
        viewOrderPane.getChildren().add(load);
    }
}
