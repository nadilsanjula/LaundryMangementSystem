package controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import db.DBConnection;
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

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.util.*;

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

        String textToEncode = "Order Id = " + orderId +
                "\npickupDate  = " + pickupDate +
                "\nDeliver Date = " + deliverDate +
                "\nAmount = " + amount +
                "\nCustomerId = " + customerId +
                "\nStaffId = " + staffId;
        String filePath = "qr-code.png"; // Output file path


        String customerEmail = getEmailFromDatabase(customerId);
        if (customerEmail != null && !customerEmail.isEmpty()) {
            sendEmail(customerEmail, filePath);
        } else {
            System.out.println("Customer email not found.");
        }

        int width = 300; // Width of the QR code image
        int height = 300; // Height of the QR code image

        try {
            // Create a QR code writer
            MultiFormatWriter writer = new MultiFormatWriter();

            // Set up encoding hints (optional)
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Generate a QR code matrix
            BitMatrix bitMatrix = writer.encode(textToEncode, BarcodeFormat.QR_CODE, width, height, hints);

            // Convert the matrix to an image and save it to a file
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(filePath));

            System.out.println("QR Code generated successfully!");
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    private String getEmailFromDatabase(String customerId) {
        return DatabaseHelper.getCustomerEmailById(customerId);
    }


    // Assuming you have a DatabaseHelper class with the following structure:
    public static class DatabaseHelper {

        private static String getCustomerEmailById(String customerId) {
            String email = null;

            try (Connection connection = DBConnection.getConnection()) {
                String sql = "SELECT email FROM customer WHERE customerId = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, customerId);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            email = resultSet.getString("email");
                            System.out.println("Found email in database: " + email);
                        } else {
                            System.out.println("Email not found in database for customer ID: " + customerId);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return email;
        }


    }


    private void sendEmail(String toEmail, String filePath) {
        final String username = "nadilsanjula2002@gmail.com"; // Your email
        final String password = "arjl asap prge fpia"; // Your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Use your email provider's SMTP host
        props.put("mail.smtp.port", "587"); // Use your email provider's SMTP port
        System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        System.setProperty("mail.smtp.ssl.enable", "true");
        System.setProperty("mail.smtp.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");




        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your Order QR Code");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Thank you for placing the order. Here is your QR code.");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            javax.activation.DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("qr-code.png");
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);


            // Send message
            Transport.send(message);

            System.out.println("Email sent successfully with QR code attachment.");

        } catch (MessagingException e) {
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
