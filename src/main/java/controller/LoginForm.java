package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import java.io.IOException;

public class LoginForm {
    public JFXTextField txtUserName;
    public JFXButton btnLogin;
    public JFXTextField txtPassword;
    public AnchorPane loginPane;

    public void btnLoginOnAction(ActionEvent actionEvent) {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        // Check for user role based on username
        if ("user".equals(username)) {
            // User role
            String validUserPassword = "1234";

            if (password.equals(validUserPassword)) {
                // Username and password are correct for the user role, proceed to userDashboard
                loadDashboard("/view/dashboardForm.fxml");
                return; // Exit the method
            }
        }


        // Username or password is incorrect, show an error message
        showErrorDialog("Login Failed", "Invalid username or password. Please try again.");
    }

    private void loadDashboard(String dashboardFXMLPath) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource(dashboardFXMLPath));
            loginPane.getChildren().clear();
            loginPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
