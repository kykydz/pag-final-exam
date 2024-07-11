package org.app.uas.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.app.uas.entity.Student;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DatabaseController implements Initializable {
    @javafx.fxml.FXML
    private TableColumn<Student, Number> tableColStudentAge;
    @javafx.fxml.FXML
    private TableColumn<Student, String> tableColStudentSex;
    @javafx.fxml.FXML
    private Button btnShowMen;
    @javafx.fxml.FXML
    private TableColumn<Student, String> tableColStudentName;
    @javafx.fxml.FXML
    private Button btnClearTable;
    @javafx.fxml.FXML
    private Button btnShowStudWithBInitName;
    @javafx.fxml.FXML
    private TableColumn<Student, String> tableColStudentNik;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private TableColumn<Student, String> tableColStudentAddress;
    @javafx.fxml.FXML
    private Button btnShowAllRecord;
    @javafx.fxml.FXML
    private Button btnShowStudWithAge17OrLess;
    @javafx.fxml.FXML
    private Button btnShowWomen;
    @javafx.fxml.FXML
    private AnchorPane paneStudents;
    @javafx.fxml.FXML
    private TableView<Student> tableViewStudent;
    @javafx.fxml.FXML
    private TextField textInputNik;
    @javafx.fxml.FXML
    private ToggleGroup sexRadioButtonGroup;
    @javafx.fxml.FXML
    private TextField textInputAge;
    @javafx.fxml.FXML
    private TextField textInputName;
    @javafx.fxml.FXML
    private TextField textInputAddress;

    private final ObservableList<Student> studentObservableList = FXCollections.observableArrayList();

    protected Connection connection;

    private static final String QUERY_ALL_RECORD = "SELECT * FROM student";

    private static final String INSERT_QUERY =
            "INSERT INTO student (nik, name, address, sex, age) VALUES (?, ?, ?, ?, ?)";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this._initTableStudentList();
        try {
            this.connection = this._initDatabaseConnection();
            this._showAllRecord();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void btnShowMenClick(ActionEvent actionEvent) throws SQLException {
        String QUERY = "SELECT * FROM student WHERE sex = ?";
        Object[] params = { "Male" };
        List<Student> womenStudents = this._getManyHelper(QUERY, params);
        this._refreshTable(womenStudents);
    }

    @javafx.fxml.FXML
    public void btnSaveClick(ActionEvent actionEvent) throws SQLException {
        String nik = textInputNik.getText();
        String name = textInputName.getText();
        String address = textInputAddress.getText();
        String sex = ((RadioButton) sexRadioButtonGroup.getSelectedToggle()).getText();
        Integer age = Integer.parseInt(textInputAge.getText());
        this._saveRecord(nik, name, address, sex, age);
        this._showAllRecord();
        this._clearForm();
    }

    @javafx.fxml.FXML
    public void btnShowWomenCllick(ActionEvent actionEvent) throws SQLException {
        String QUERY = "SELECT * FROM student WHERE sex = ?";
        Object[] params = { "Female" };
        List<Student> womenStudents = this._getManyHelper(QUERY, params);
        this._refreshTable(womenStudents);
    }

    @javafx.fxml.FXML
    public void btnShowStudWithAge17OrLessClick(ActionEvent actionEvent) throws SQLException {
        String QUERY = "SELECT * FROM student WHERE age < ?";
        Object[] params = { 17 };
        List<Student> womenStudents = this._getManyHelper(QUERY, params);
        this._refreshTable(womenStudents);
    }

    @javafx.fxml.FXML
    public void btnClearTableClick(ActionEvent actionEvent) {
        this._clearTable();
    }

    @javafx.fxml.FXML
    public void btnShowStudWithBInitNameClick(ActionEvent actionEvent) throws SQLException {
        String QUERY = "SELECT * FROM student WHERE name like ? or name like ?";
        Object[] params = { "b%", "B%" };
        List<Student> womenStudents = this._getManyHelper(QUERY, params);
        this._refreshTable(womenStudents);
    }

    @javafx.fxml.FXML
    public void btnShowAllRecordClick(ActionEvent actionEvent) throws SQLException {
        this._showAllRecord();
    }

    private Connection _initDatabaseConnection() throws SQLException {
        String database = "uas";
        String host = STR."jdbc:postgresql://localhost:5432/\{database}";
        String username = "postgres";
        String password = "qwer1234";
        return DriverManager.getConnection(host, username, password);
    }

    private void _initTableStudentList() {
        tableViewStudent.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableColStudentNik.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNik()));
        tableColStudentName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableColStudentAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        tableColStudentSex.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSex()));
        tableColStudentAge.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()));
    }

    private void _refreshTable(List<Student> students) {
        studentObservableList.setAll(students);
        tableViewStudent.setItems(studentObservableList);
    }

    private void _clearTable() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        tableViewStudent.setItems(students);
    }

    private void _saveRecord(String nik, String name, String address, String sex, Integer age) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY)) {
            ps.setString(1, nik);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, sex);
            ps.setInt(5, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void _showAllRecord() throws SQLException {
        List<Student> students = this._getManyHelper(QUERY_ALL_RECORD, (Object) null);
        this._refreshTable(students);
    }

    private List<Student> _getManyHelper(String query, Object... params) throws SQLException {
        List<Student> results = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            if (params != null) {
                int parameterIndex = 1;
                for (Object param : params) {
                    if (param != null) {
                        ps.setObject(parameterIndex++, param);
                    }
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                results.add(__mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
        return results;
    }

    protected Student __mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("nik"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("sex"),
                rs.getInt("age"));
    }

    private void _clearForm() {
        textInputNik.clear();
        textInputName.clear();
        textInputAddress.clear();
        textInputAge.clear();
    }
}
