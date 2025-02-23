package com.example.regextestergui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox mainBox = new VBox();

        TextField regexField = new TextField();
        regexField.setPromptText("Enter the Regex used to test");
        regexField.setPrefWidth(220);

        TextArea textArea = new TextArea();
        textArea.setPromptText("Enter values to be tested here seperated by new lines");
        textArea.setPrefHeight(350);
        textArea.setPadding(new Insets(0, 20, 20, 20));

        TextFlow textFlow = new TextFlow();
        textFlow.setPadding(new Insets(0, 20, 20, 20));

        Button testButton = new Button("Test");
        testButton.setTextFill(Color.GREEN);

        Button returnButton = new Button("Return");
        returnButton.setTextFill(Color.GOLDENROD);

        Button restartButton = new Button("Restart");
        restartButton.setTextFill(Color.RED);

        AtomicBoolean inverted = new AtomicBoolean(false);
        CheckBox invert = new CheckBox("Invert");
        invert.setPadding(new Insets(0, 0, 3, 0));
        invert.setOnAction(event -> {
            inverted.set(invert.isSelected());
            if (invert.isSelected()) {
                textArea.setPromptText("Enter regex to be tested here seperated by new lines");
                regexField.setPromptText("Enter the String used to test");
            }
            else {
                textArea.setPromptText("Enter values to be tested here seperated by new lines");
                regexField.setPromptText("Enter the Regex used to test");
            }
        });

        HBox buttonBox = new HBox(regexField, testButton, returnButton, restartButton, invert);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(20, 20, 20, 20));

        restartButton.setOnAction(e -> {
            regexField.clear();
            textArea.clear();
            mainBox.getChildren().removeAll(mainBox.getChildren());
            mainBox.getChildren().addAll(buttonBox, textArea);
        });
        returnButton.setOnAction(e -> {
            mainBox.getChildren().removeAll(mainBox.getChildren());
            mainBox.getChildren().addAll(buttonBox, textArea);
        });

        testButton.setOnAction(event -> {
            try {
                textFlow.getChildren().clear();
                String regex = regexField.getText();
                List<String> inputs = Arrays.stream(textArea.getText().split("\n")).toList();

                List<Text> texts = inputs.stream().map(input -> {
                    String processedInput = "  " + input;
                    if (!inverted.get()) {
                        processedInput += (input.matches(regex) ? " -> Matches" : " -> Doesn't Match") + "\n";
                    }
                    else {
                        processedInput += (regex.matches(input) ? " -> Matches" : " -> Doesn't Match") + "\n";
                    }
                    Text temp = new Text(processedInput);
                    if (!inverted.get()) {
                        temp.setFill((input.matches(regex) ? Color.GREEN : Color.RED));
                    }
                    else {
                        temp.setFill((regex.matches(input) ? Color.GREEN : Color.RED));
                    }
                    return temp;
                }).toList();

                textFlow.getChildren().addAll(texts);

                mainBox.getChildren().removeAll(mainBox.getChildren());
                mainBox.getChildren().addAll(buttonBox, textFlow);
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Regex");
                alert.setHeaderText(null);
                alert.setContentText("There is en error in the formmating og your regex:\n" + e.getMessage());
                alert.showAndWait();
            }
        });


        mainBox.getChildren().addAll(buttonBox, textArea);

        Scene scene = new Scene(mainBox, 500, 500);
        stage.setTitle("Regex GUI Tester: Exam 2025");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}