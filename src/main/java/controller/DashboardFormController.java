package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFormController {
    public ImageView dashboard_image;
    public JFXButton homeBtn;
    public JFXButton orderBtn;
    public JFXButton customerBtn;
    public JFXButton salaryBtn;
    public JFXButton laundryEquipmentBtn;
    public JFXButton paymentBtn;
    public JFXButton staffBtn;
    public JFXButton laundryItemBtn;
    public JFXButton orderItemBtn;
    public JFXButton logoutBtn;
    public AnchorPane load;
    public Label lblTime;
    public Label lblDate;

    private void updateTimeLabel() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String formattedTime = timeFormat.format(new Date());
        lblTime.setText(formattedTime);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(new Date());
        lblDate.setText(formattedDate);
    }

    public void initialize() {
        updateTimeLabel();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        updateTimeLabel();
                    }
                }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public void homeBtnOnAction(ActionEvent actionEvent) {
    }

    public void customerBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/customerForm.fxml"));
        load.getChildren().clear();
        load.getChildren().add(root);
    }

    public void orderBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/orderForm.fxml"));
        load.getChildren().clear();
        load.getChildren().add(root);
    }

    public void salaryBtnOnAction(ActionEvent actionEvent) {
    }

    public void laundryEquipmentBtnOnAction(ActionEvent actionEvent) throws IOException {

    }

    public void paymentBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/paymentForm.fxml"));
        load.getChildren().clear();
        load.getChildren().add(root);
    }

    public void staffBtnBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/staffForm.fxml"));
        load.getChildren().clear();
        load.getChildren().add(root);
    }

    public void laundryItemBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/laundryItemForm.fxml"));
        load.getChildren().clear();
        load.getChildren().add(root);
    }

    public void itemBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/itemForm.fxml"));
        load.getChildren().clear();
        load.getChildren().add(root);
    }

    public void logoutBtnOnAction(ActionEvent actionEvent) {
    }
}
