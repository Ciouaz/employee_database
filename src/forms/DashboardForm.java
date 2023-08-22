package forms;

import application.ConnectToDatabase;
import forms.admin.AdminLoginForm;
import forms.user.LoginForm;
import forms.user.RegistrationForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DashboardForm extends JFrame {
    private JPanel dashboardPanel;
    private JButton btnRegister;
    private JButton loginButton;
    private JButton loginAdminButton;


    public DashboardForm(JFrame parent) {
        setTitle("Dashboard");
        setContentPane(dashboardPanel);
        setMinimumSize(new Dimension(500, 550));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(parent);

        connectToDatabase();
        setVisible(true);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new RegistrationForm(DashboardForm.this);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new LoginForm(DashboardForm.this);

            }
        });
        loginAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLoginForm(DashboardForm.this);
            }
        });
    }

    private void connectToDatabase() {

        try {
            Connection conn = DriverManager.getConnection(
                    ConnectToDatabase.MYSQL_SERVER_URL,
                    ConnectToDatabase.USERNAME,
                    ConnectToDatabase.PASSWORD
            );

            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS MyStore");
            statement.close();
            conn.close();

            conn = DriverManager.getConnection(
                    ConnectToDatabase.DB_URL,
                    ConnectToDatabase.USERNAME,
                    ConnectToDatabase.PASSWORD
            );

            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "phone VARCHAR(200),"
                    + "address VARCHAR(200),"
                    + "password VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}