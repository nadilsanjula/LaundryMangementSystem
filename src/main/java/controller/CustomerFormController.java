package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class CustomerFormController {
    public AnchorPane customerPane;
    public JFXTextField txtCustomerId;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtNumber;
    public JFXTextField txtNIC;
    public JFXTextField txtAddress;
    public JFXButton btnView;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnSave;


    ObservableList<CustomerDTO> observableList = FXCollections.observableArrayList();


    public void btnSaveOnAction(ActionEvent actionEvent) {

        boolean isValead = validateCustomer();
        if (isValead) {

            String customerId = txtCustomerId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            Integer telNum = Integer.valueOf(txtNumber.getText());
            String nic = txtNIC.getText();
            String email = txtEmail.getText();


            try {
                boolean isSaved = CustomerModel.save(new CustomerDTO(customerId, name, email, address, telNum, nic));

                if (isSaved) {

                    new Alert(Alert.AlertType.CONFIRMATION, "Saved  !!!").show();
                    txtCustomerId.setText("");
                    txtName.setText("");
                    txtAddress.setText("");
                    txtNumber.setText("");
                    txtNIC.setText("");
                    txtEmail.setText("");
                    observableList.clear();

                } else {

                    new Alert(Alert.AlertType.ERROR, "Not saved  !!!").show();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateCustomer() {
        String id = txtCustomerId.getText();
        boolean idMatch = Pattern.matches("[C]\\d{3,}",id);
        if (!idMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid id").show();
            return false;
        }

        String name = txtName.getText();
        boolean nameMAtch = Pattern.matches("[A-za-z\\s]{4,}",name);
        if (!nameMAtch) {
            new Alert(Alert.AlertType.ERROR,"invalid name").show();
            return false;
        }

        String address = txtAddress.getText();
        boolean addressMatch= Pattern.matches("[A-za-z]{3,}",address);
        if (!addressMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid address").show();
            return false;
        }

        // int tel =Integer.parseInt(txtPhone.getText());
        boolean telMatch = Pattern.matches("[0-9]{10}",txtNumber.getText());
        if (!telMatch) {
            new Alert(Alert.AlertType.ERROR,"invalid tel").show();
            return false;
        }
        return true;
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean isValead = validateCustomer();
        if (isValead) {

            String customerId = txtCustomerId.getText();
            String name = txtName.getText();
            String email = txtEmail.getText();
            String address = txtAddress.getText();
            int telNum = Integer.valueOf(txtNumber.getText());
            String nic = txtNIC.getText();

            boolean isUpdated = false;
            try {
                isUpdated = CustomerModel.update(new CustomerDTO(customerId, name, email, address, telNum, nic));
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully").show();
                    txtCustomerId.setText("");
                    txtName.setText("");
                    txtAddress.setText("");
                    txtNumber.setText("");
                    txtNIC.setText("");
                    txtEmail.setText("");
                    observableList.clear();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Update failed").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();

        try {
            boolean isRemoved = CustomerModel.remove(customerId);

            if (isRemoved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully").show();
                txtCustomerId.setText("");
                txtName.setText("");
                txtAddress.setText("");
                txtNumber.setText("");
                txtNIC.setText("");
                txtEmail.setText("");
                observableList.clear();

            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/viewCustomerForm.fxml"));
        customerPane.getChildren().clear();
        customerPane.getChildren().add(load);
    }

    public void IdSearchOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();

        try {
            CustomerDTO customerDTO= CustomerModel.search(customerId);

            if (customerDTO != null) {
                txtCustomerId.setText(customerDTO.getCustomerId());
                txtName.setText(customerDTO.getName());
                txtEmail.setText(customerDTO.getEmail());
                txtAddress.setText(customerDTO.getAddress());
                txtNumber.setText(String.valueOf(customerDTO.getTelNum()));
                txtNIC.setText(customerDTO.getNic());
            }else {
                new Alert(Alert.AlertType.ERROR,"Invalid ID").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();

        try {
            CustomerDTO dto = CustomerModel.search(id);
            if(dto!=null){
                genarateReport(dto);
            }
        } catch (SQLException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    private void genarateReport(CustomerDTO dto) throws JRException {
        HashMap hashMap = new HashMap();
        hashMap.put("id",dto.getCustomerId());
        hashMap.put("name",dto.getName());
        hashMap.put("email",dto.getEmail());
        hashMap.put("address",dto.getAddress());
        hashMap.put("tel",Integer.toString(dto.getTelNum()));
        hashMap.put("nic",dto.getNic());

        InputStream resourceAsStream =  getClass().getResourceAsStream("/report/CustomerReport");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport= JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                hashMap,
                new JREmptyDataSource()
        );

        JasperViewer.viewReport(jasperPrint,false);

    }


}
