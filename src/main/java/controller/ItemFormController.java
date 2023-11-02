package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.StaffDTO;
import dto.tm.OrderTM;
import dto.tm.StaffTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.ItemModel;
import model.OrderModel;
import model.StaffModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ItemFormController {
    public JFXButton btnView;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnSave;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQuantity;
    public JFXTextField txtName;
    public JFXTextField txtDescription;
    public JFXTextField txtItemId;
    public AnchorPane itemPane;
    public JFXComboBox cmbOrderId;

    private OrderModel orderModel = new OrderModel();
    ObservableList<ItemDTO> observableList = FXCollections.observableArrayList();

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String itemId = txtItemId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        Integer qty = Integer.valueOf(txtQuantity.getText());
        Double unitPrice = Double.valueOf((txtUnitPrice.getText()));
        String orderId = (String) cmbOrderId.getValue();

        try {
            boolean isSaved = ItemModel.save(new ItemDTO(itemId, name,description,qty,unitPrice,orderId));


            if (isSaved) {

                new Alert(Alert.AlertType.CONFIRMATION, "Saved  !!!").show();
                txtItemId.setText("");
                txtName.setText("");
                txtDescription.setText("");
                txtQuantity.setText("");
                txtUnitPrice.setText("");
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
        String itemId = txtItemId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        Integer qty = Integer.valueOf(txtQuantity.getText());
        Double unitPrice = Double.valueOf((txtUnitPrice.getText()));
        String orderId = (String) cmbOrderId.getValue();

        boolean isUpdated = false;
        try {
            isUpdated = ItemModel.update(new ItemDTO(itemId, name,description,qty,unitPrice,orderId));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully").show();
                txtItemId.setText("");
                txtName.setText("");
                txtDescription.setText("");
                txtQuantity.setText("");
                txtUnitPrice.setText("");
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
        String itemId = txtItemId.getText();

        try {
            boolean isRemoved = ItemModel.remove(itemId);

            if (isRemoved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully").show();
                txtItemId.setText("");
                txtName.setText("");
                txtDescription.setText("");
                txtQuantity.setText("");
                txtUnitPrice.setText("");
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
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/viewItemForm.fxml"));
        itemPane.getChildren().clear();
        itemPane.getChildren().add(load);
    }

    public void IdSearchOnAction(ActionEvent actionEvent) {
        String itemId = txtItemId.getText();

        try {
            ItemDTO itemDTO= ItemModel.search(itemId);

            if (itemDTO != null) {
                txtItemId.setText(itemDTO.getItemId());
                txtName.setText(itemDTO.getName());
                txtDescription.setText(itemDTO.getDescription());
                txtQuantity.setText(String.valueOf(itemDTO.getQty()));
                txtUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
                cmbOrderId.setValue(itemDTO.getOrderId());
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
            OrderDTO orderDTO = orderModel.search(orderId);
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
