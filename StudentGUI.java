package com;

import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class StudentGUI {

	    static final String URL = "jdbc:mysql://localhost:3306/student_db";
	    static final String USER = "your_username";
	    static final String PASSWORD = "your_password";

        public static void main(String[] args) {

        JFrame frame = new JFrame("Student Management System");

        frame.setSize(900, 650);
        frame.setLayout(null);
        JLabel title = new JLabel("STUDENT MANAGEMENT SYSTEM");
        title.setBounds(250, 10, 400, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        frame.add(title);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(50, 50, 100, 30);

        JTextField txtName = new JTextField();
        txtName.setBounds(150, 50, 150, 30);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(50, 100, 100, 30);

        JTextField txtAge = new JTextField();
        txtAge.setBounds(150, 100, 150, 30);

        JLabel lblCourse = new JLabel("Course:");
        lblCourse.setBounds(50, 150, 100, 30);
        
        JTextField txtCourse = new JTextField();
        txtCourse.setBounds(150, 150, 150, 30);
        
        JLabel lblId = new JLabel("Student ID:");
        lblId.setBounds(50, 190, 100, 30);

        JTextField txtId = new JTextField();
        txtId.setBounds(150, 190, 150, 30);
        String[] columns = {"ID", "Name", "Age", "Course"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBounds(50, 330, 800, 250);  
      

        JButton btnAdd = new JButton("Add Student");
        btnAdd.setBounds(150, 220, 120, 30);

        JButton btnView = new JButton("View Students");
        btnView.setBounds(300, 220, 140, 30);
        JButton btnDelete = new JButton("Delete Student");
        btnDelete.setBounds(150, 270, 140, 30);
        JButton btnUpdate = new JButton("Update Student");
        btnUpdate.setBounds(300, 270, 140, 30);
        JButton btnSearch = new JButton("Search Student");
        btnSearch.setBounds(450, 270, 140, 30);

        frame.add(btnSearch);
        frame.add(btnUpdate);
        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnView);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            	String name = txtName.getText();
            	String age = txtAge.getText();
            	String course = txtCourse.getText();

            	if(name.isEmpty() || age.isEmpty() || course.isEmpty()) {
            	    JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            	    return;
            	}


                try {

                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    String insert = "INSERT INTO students(name, age, course) VALUES (?, ?, ?)";

                    PreparedStatement ps = con.prepareStatement(insert);

                    ps.setString(1, name);
                    ps.setInt(2, Integer.parseInt(age));
                    ps.setString(3, course);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Student Added Successfully!");
                    txtName.setText("");
                    txtAge.setText("");
                    txtCourse.setText("");
                    txtId.setText("");

                    con.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    String select = "SELECT * FROM students";

                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery(select);
                    model.setRowCount(0);

                    while(rs.next()) {

                    	model.addRow(new Object[] {
                    		    rs.getInt("id"),
                    		    rs.getString("name"),
                    		    rs.getInt("age"),
                    		    rs.getString("course")
                    		});
                    }

                    con.close();

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String id = txtId.getText();

                if(id.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter Student ID!");
                    return;
                }

                try {

                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    String deleteQuery = "DELETE FROM students WHERE id = ?";

                    PreparedStatement psDelete = con.prepareStatement(deleteQuery);

                    psDelete.setInt(1, Integer.parseInt(id));

                    int rowsDeleted = psDelete.executeUpdate();

                    if(rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(frame, "Student Deleted Successfully!");
                        txtId.setText("");
                        txtName.setText("");
                        txtAge.setText("");
                        txtCourse.setText("");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Student Not Found!");
                    }

                    con.close();

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String id = txtId.getText();
                String name = txtName.getText();
                String age = txtAge.getText();
                String course = txtCourse.getText();

                if(id.isEmpty() || name.isEmpty() || age.isEmpty() || course.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                    return;
                }

                try {

                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    String updateQuery =
                            "UPDATE students SET name=?, age=?, course=? WHERE id=?";

                    PreparedStatement psUpdate =
                            con.prepareStatement(updateQuery);

                    psUpdate.setString(1, name);
                    psUpdate.setInt(2, Integer.parseInt(age));
                    psUpdate.setString(3, course);
                    psUpdate.setInt(4, Integer.parseInt(id));

                    int rows = psUpdate.executeUpdate();

                    if(rows > 0) {
                        JOptionPane.showMessageDialog(frame,
                                "Student Updated Successfully!");
                        txtId.setText("");
                        txtName.setText("");
                        txtAge.setText("");
                        txtCourse.setText("");
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "Student Not Found!");
                    }

                    con.close();

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String id = txtId.getText();

                if(id.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Please enter Student ID!");
                    return;
                }

                try {

                    Connection con =
                            DriverManager.getConnection(URL, USER, PASSWORD);

                    String searchQuery =
                            "SELECT * FROM students WHERE id=?";

                    PreparedStatement ps =
                            con.prepareStatement(searchQuery);

                    ps.setInt(1, Integer.parseInt(id));

                    ResultSet rs = ps.executeQuery();

                    if(rs.next()) {

                        txtName.setText(rs.getString("name"));
                        txtAge.setText(String.valueOf(rs.getInt("age")));
                        txtCourse.setText(rs.getString("course"));

                    } else {

                        JOptionPane.showMessageDialog(frame,
                                "Student Not Found!");
                    }

                    con.close();

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        table.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {

                int row = table.getSelectedRow();

                txtId.setText(table.getValueAt(row, 0).toString());
                txtName.setText(table.getValueAt(row, 1).toString());
                txtAge.setText(table.getValueAt(row, 2).toString());
                txtCourse.setText(table.getValueAt(row, 3).toString());

            }
        });
        frame.add(lblName);
        frame.add(txtName);

        frame.add(lblAge);
        frame.add(txtAge);

        frame.add(lblCourse);
        frame.add(txtCourse);
        
        frame.add(lblId);
        frame.add(txtId);
        frame.add(scrollPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}