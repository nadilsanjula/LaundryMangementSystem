package controller;

import com.jfoenix.controls.JFXButton;
import dto.tm.CustomerTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCustomerFormController implements Initializable {
    public TableColumn colNic;
    public TableColumn colTelNumber;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colCustomerID;
    public TableColumn colName;
    public TableView<CustomerTM> tblCustomer;
    public AnchorPane viewCustomerPane;
    public JFXButton btnBack;

    private void getAll() {
        try {
            List<CustomerTM> customerTMS = CustomerModel.getAll();
            ObservableList<CustomerTM> list = FXCollections.observableArrayList();
            for (CustomerTM customerTM :customerTMS){
                list.add(
                        new CustomerTM(
                                customerTM.getCustomerId(),
                                customerTM.getName(),
                                customerTM.getEmail(),
                                customerTM.getAddress(),
                                customerTM.getTelNum(),
                                customerTM.getNic()
                        ));
            }
            tblCustomer.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {

        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTelNumber.setCellValueFactory(new PropertyValueFactory<>("telNum"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/customerForm.fxml"));
        viewCustomerPane.getChildren().clear();
        viewCustomerPane.getChildren().add(load);

    }
}
