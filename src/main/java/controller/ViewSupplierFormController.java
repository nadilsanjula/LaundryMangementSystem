package controller;

import com.jfoenix.controls.JFXButton;
import dto.tm.StaffTM;
import dto.tm.SupplierTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.StaffModel;
import model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewSupplierFormController implements Initializable {
    public AnchorPane viewSupplierPane;
    public TableView<SupplierTM> tblSupplier;
    public TableColumn colSupplierId;
    public TableColumn colName;
    public TableColumn colTelNumber;
    public TableColumn colEmail;
    public TableColumn colAddress;
    public JFXButton btnBack;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/supplierForm.fxml"));
        viewSupplierPane.getChildren().clear();
        viewSupplierPane.getChildren().add(load);
    }

    private void setCellValueFactory() {

        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelNumber.setCellValueFactory(new PropertyValueFactory<>("telnum"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void getAll() {
        try {
            List<SupplierTM> supplierTMS = SupplierModel.getAll();
            ObservableList<SupplierTM> list = FXCollections.observableArrayList();
            for (SupplierTM supplierTM :supplierTMS){
                list.add(
                        new SupplierTM(
                                supplierTM.getSupplierId(),
                                supplierTM.getName(),
                                supplierTM.getEmail(),
                                supplierTM.getTelnum(),
                                supplierTM.getAddress()
                        ));
            }
            tblSupplier.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
