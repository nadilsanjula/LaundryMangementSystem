package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
import dto.LaundryItemDTO;
import dto.OrderDTO;
import dto.tm.ItemTM;
import dto.tm.OrderTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.ItemModel;
import model.LaundryItemModel;
import model.OrderModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LaundryItemFormController {
    public JFXButton btnView;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnSave;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQuantityAvailable;
    public JFXTextField txtDescription;
    public JFXTextField txtName;
    public JFXTextField txtLaundryItemId;
    public AnchorPane laundryItemPane;
    public JFXComboBox comItemId;

    ObservableList<LaundryItemDTO> observableList = FXCollections.observableArrayList();
    private ItemModel itemModel = new ItemModel();


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String laundryItemId = txtLaundryItemId.getText();
        String name = txtName.getText();
        int qtyAvailble = Integer.parseInt(txtQuantityAvailable.getText());
        String desc = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String itemId = (String) comItemId.getValue();
        try {
            boolean isSaved = LaundryItemModel.save(new LaundryItemDTO(laundryItemId,name,qtyAvailble,desc,unitPrice,itemId));


            if (isSaved) {

                new Alert(Alert.AlertType.CONFIRMATION, "Saved  !!!").show();
                txtLaundryItemId.setText("");
                txtName.setText("");
                txtQuantityAvailable.setText("");
                txtDescription.setText("");
                txtUnitPrice.setText("");
                comItemId.setValue("");
                observableList.clear();

            } else {

                new Alert(Alert.AlertType.ERROR, "Not saved  !!!").show();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String laundryItemId = txtLaundryItemId.getText();
        String name = txtName.getText();
        int qtyAvailble = Integer.parseInt(txtQuantityAvailable.getText());
        String desc = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String itemId = (String) comItemId.getValue();

        boolean isUpdated = false;
        try {
            isUpdated = LaundryItemModel.update(new LaundryItemDTO(laundryItemId,name,qtyAvailble,desc,unitPrice,itemId));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully").show();
                txtLaundryItemId.setText("");
                txtName.setText("");
                txtQuantityAvailable.setText("");
                txtDescription.setText("");
                txtUnitPrice.setText("");
                comItemId.setValue("");
                observableList.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String laundryItemId = txtLaundryItemId.getText();

        try {
            boolean isRemoved = LaundryItemModel.remove(laundryItemId);

            if (isRemoved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully").show();
                txtLaundryItemId.setText("");
                txtName.setText("");
                txtQuantityAvailable.setText("");
                txtDescription.setText("");
                txtUnitPrice.setText("");
                comItemId.setValue("");
                observableList.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/viewLaundryItemForm.fxml"));
        laundryItemPane.getChildren().clear();
        laundryItemPane.getChildren().add(load);
    }

    public void IdSearchOnAction(ActionEvent actionEvent) {
        String laundryItemId = txtLaundryItemId.getText();

        try {
            LaundryItemDTO laundryItemDTO= LaundryItemModel.search(laundryItemId);

            if (laundryItemDTO != null) {
                txtLaundryItemId.setText(laundryItemDTO.getLaundryItemId());
                txtName.setText(laundryItemDTO.getName());
                txtQuantityAvailable.setText(String.valueOf(laundryItemDTO.getQtyAvailable()));
                txtDescription.setText(laundryItemDTO.getDesc());
                txtUnitPrice.setText(String.valueOf(laundryItemDTO.getUnitePrice()));
                comItemId.setValue(laundryItemDTO.getItemId());
            }else {
                new Alert(Alert.AlertType.ERROR,"Invalid ID").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void comItemIdOnAction(ActionEvent actionEvent) {
        String itemId = (String) comItemId.getValue();

        try {
            ItemDTO itemDTO = ItemModel.search(itemId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemTM> itemTMS = itemModel.getAll();

            for (ItemTM dto : itemTMS) {
                obList.add(dto.getItemId());
            }
            comItemId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        loadItemId();
    }
}
