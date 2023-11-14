package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.PlaceOrderDTO;
import dto.StaffDTO;
import dto.tm.CartTM;
import dto.tm.CustomerTM;
import dto.tm.ItemTM;
import dto.tm.StaffTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public JFXButton btnPlaceOrder;
    public JFXComboBox cmbCustomerId;
    public Label lblCustomerName;
    public Label lblOrderId;
    public Label lblOrderDate;
    public JFXComboBox cmbItemId;
    public Label lblDesc;
    public Label lbblUnitePrice;
    public TextField txtQty;
    public JFXButton btnCart;
    public TableView tblCart;
    public TableColumn colAction;
    public TableColumn colTotal;
    public TableColumn colItemId;
    public TableColumn colDesc;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public Label lblTotal;
    public JFXDatePicker dateDeliver;
    public JFXComboBox cmbStaffId;

    private CustomerModel customerModel = new CustomerModel();
    private StaffModel staffModel = new StaffModel();
    private ObservableList<CartTM> obList = FXCollections.observableArrayList();
    private PlaceOrderModel placeOrderModel = new PlaceOrderModel();

    private ItemModel itemModel = new ItemModel();


    public void btnCartOnAction(ActionEvent actionEvent) {
        String itemId = (String) cmbItemId.getValue();
        String description = lblDesc.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lbblUnitePrice.getText());
        double tot = unitPrice * qty;
        Button btn = new Button("Remove");

        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);


        if (!obList.isEmpty()) {
            for (int i = 0; i < tblCart.getItems().size(); i++) {
                if (colItemId.getCellData(i).equals(itemId)) {
                    int col_qty = (int) colQty.getCellData(i);
                    qty += col_qty;
                    tot = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(tot);

                    calculateTotal();
                    tblCart.refresh();
                    return;
                }
            }
        }
        var cartTm = new CartTM(itemId, description, qty, unitPrice, tot, btn);

        obList.add(cartTm);

        tblCart.setItems(obList);
        calculateTotal();
        txtQty.clear();
    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblCart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblCart.refresh();
                calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblTotal.setText(String.valueOf(total));
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        LocalDate pickupDate = LocalDate.parse(lblOrderDate.getText());
        LocalDate deliverDate = dateDeliver.getValue();
        double amount = Double.parseDouble(lblTotal.getText());
        String customerId = (String) cmbCustomerId.getValue();
        String staffId = (String) cmbStaffId.getValue();

        List<CartTM> cartTMList = new ArrayList<>();
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            CartTM cartTm = obList.get(i);

            cartTMList.add(cartTm);
        }

        System.out.println("Place order form controller: " + cartTMList);
        var placeOrderDto = new PlaceOrderDTO(orderId, pickupDate,deliverDate,amount, customerId,staffId, cartTMList);
        try {
            boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Success!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbCustomerIdOnAction(ActionEvent actionEvent) {
        String customerId = (String) cmbCustomerId.getValue();

        try {
            CustomerDTO customerDto = customerModel.search(customerId);
            lblCustomerName.setText(customerDto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerTM> idList = customerModel.getAll();

            for (CustomerTM dto : idList) {
                obList.add(dto.getCustomerId());
            }

            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextOrderId() {
        try {
            String orderId = OrderModel.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        generateNextOrderId();
        loadCustomerIds();
        loadItemCodes();
        setCellValueFactory();
        loadStaffId();
    }

    public void cmbItemIdOnAction(ActionEvent actionEvent) {
        String itemId = (String) cmbItemId.getValue();

        txtQty.requestFocus();
        try {
            ItemDTO dto = ItemModel.search(itemId);
            lblDesc.setText(dto.getDescription());
            lbblUnitePrice.setText(String.valueOf(dto.getUnitPrice()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemTM> itemDtos = itemModel.getAll();

            for (ItemTM dto : itemDtos) {
                obList.add(dto.getItemId());
            }
            cmbItemId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void cmbStaffIdOnAction(ActionEvent actionEvent) {
        String staffdId = (String) cmbStaffId.getValue();

        txtQty.requestFocus();
        try {
            StaffDTO dto = StaffModel.search(staffdId);
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
            cmbStaffId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
