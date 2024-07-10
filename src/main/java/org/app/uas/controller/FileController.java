package org.app.uas.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileController {
    public TextField inputTextFilePath;
    public Button btnRead;
    public TextArea textAreaFileContent;
    public Button btnWrite;
    public TextField inputTextSourcePath;
    public TextField inputTextDestinationPath;
    public Button btnCopy;
    public Button btnBrowse;
    public TextField inputTextBrowsedFilePath;
    public RadioButton rbAutoBrowse, rbReadFileFromFilePath, rbWriteFromContent, rbCopySourceToDestination;
    public ToggleGroup selectorMethod;
    public Pane panelCopyDemo;
    public Pane panelBrowseDemo;
    public RadioButton rbStreamWriteFileContent;
    public Label labelWarnKeyListener2;
    public Label labelWarnKeyListener1;
    public Pane panelReadWrite;
    public Button btnRead1;

    private FileWriter fileWriter; // Reference to the file writer

    @FXML
    public void initialize() throws IOException {
        this.rbSelectorModeistener();
        coldStart();
    }

    public void onBtnReadClick(ActionEvent actionEvent) throws IOException {
        String path = inputTextFilePath.getText();

        String fileraw = readFile(path);
        StringBuilder fileContent = new StringBuilder();
        for (int i = 0; i < fileraw.length(); i++) {
            char character = fileraw.charAt(i);
            fileContent.append(character);
            fileContent.append('*');
        }

        textAreaFileContent.setText(String.valueOf(fileContent));
    }

    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString().trim();
    }

    public void onBtnWriteClick(ActionEvent actionEvent) throws IOException {
        String filePath = inputTextFilePath.getText();
        String content = textAreaFileContent.getText();
        writeFile(filePath, content);
    }

    private void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    public void onBtnCopyClick(ActionEvent actionEvent) throws IOException {
        String sourcePath = inputTextSourcePath.getText();
        String destinationPath = inputTextDestinationPath.getText();
        copyFile(sourcePath, destinationPath);
    }

    private void copyFile(String sourcePath, String destinationPath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(sourcePath);
             FileOutputStream outputStream = new FileOutputStream(destinationPath)) {
            byte[] buffer = new byte[4096]; // Adjust buffer size as needed
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private void rbSelectorModeistener() {
        selectorMethod.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            coldStart();
            RadioButton selectedButton = (RadioButton) selectorMethod.getSelectedToggle();
            String selectedOption = selectedButton.getText();

            switch (selectedOption) {
                case "Browse Demo":
                    panelBrowseDemo.setDisable(false);
                    break;
                case "Read File From Path":
                    panelReadWrite.setDisable(false);
                    btnRead.setDisable(false);
                    btnRead1.setDisable(false);
                    inputTextFilePath.setDisable(false);
                    inputTextFilePath.setDisable(false);
                    textAreaFileContent.setText("");
                    textAreaFileContent.editableProperty().set(false);
                    break;
                case "Write From Content":
                    panelReadWrite.setDisable(false);
                    inputTextFilePath.setDisable(false);
                    textAreaFileContent.editableProperty().set(true);
                    btnWrite.setDisable(false);
                    break;
                case "Copy Demo File":
                    panelCopyDemo.setDisable(false);
                    break;
                case "Stream Content Write to File":
                    panelReadWrite.setDisable(false);
                    textAreaFileContent.editableProperty().set(false);
                    labelWarnKeyListener1.setVisible(true);
                    labelWarnKeyListener2.setVisible(true);
                    break;
                default:
                    coldStart();
                    break;
            }
        });
    }

    private void coldStart() {
        textAreaFileContent.clear();
        labelWarnKeyListener1.setVisible(false);
        labelWarnKeyListener2.setVisible(false);
        panelBrowseDemo.setDisable(true);
        panelCopyDemo.setDisable(true);
        panelReadWrite.setDisable(true);
        btnRead.setDisable(true);
        btnRead1.setDisable(true);
        btnWrite.setDisable(true);
    }

    public void onBtnBrowseClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File selectedFile = fileChooser.showOpenDialog(null); // Pass the parent window (optional)
        if (selectedFile != null) {
            inputTextBrowsedFilePath.setText(selectedFile.getAbsolutePath());
            inputTextFilePath.setText(selectedFile.getAbsolutePath());
        }
    }

    public void onBtnRead1Click(ActionEvent actionEvent) throws IOException {
        String path = inputTextFilePath.getText();
        String fileContent = readFile(path).toLowerCase();
        textAreaFileContent.setText(fileContent);
    }
}
