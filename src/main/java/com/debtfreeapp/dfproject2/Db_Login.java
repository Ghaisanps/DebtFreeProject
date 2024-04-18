package com.debtfreeapp.dfproject2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.EventObject;

public class DB_Utils {



    public static void changeScene(ActionEvent, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DB_Utils.class.getResource(fxmlFile));
                root = loader.load();
                Loggedincontroller loggedincontroller = loader.getController();
                loggedincontroller.setUserInformation(username);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql:localhost:3306/DebtFree", "root", "Ghaisan311203_");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO user (username, password VALUES (?,?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();

                changeScene(event, "LoggedIn.fxml", "Welcome", username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loginUser(ActionEvent event, String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement psStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql:localhost:3306/DebtFree", "root", "Ghaisan311203_");
            preparedStatement = connection.prepareStatement("SELECT password FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = psStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User not Found!");
                Alert setContextText = new Alert(Alert.AlertType.ERROR);
                alert.setContextText("Provided credentials are incorrect! ");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrivedPassword = resultSet.getString("password");
                    if (retrivedPassword.equals(password)) {
                        changeScene(event, "LoggedIn.fxml", "Welcome", username);
                    } else {
                        System.out.println("Wrong Password!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect! ");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    }


