package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class HelloController implements Initializable {

    private final int NUM_OF_ROWS = 6;
    private final int NUM_OF_COLUMNS = 4;

    private InputHelper inputHelper;

    private Map<String, Button> buttons;
    private final String[][] textForButtons =
                   {{"%", "CE", "C", "<="},
                    {"1/x", "^2", "√", "/"},
                    {"7", "8", "9", "*"},
                    {"4", "5", "6", "-"},
                    {"1", "2", "3", "+"},
                    {"+/-", "0", ",", "="}};

    @FXML
    private GridPane buttonsPanel;

    @FXML
    private Label textCalculate;

    @FXML TextArea input;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputHelper = new InputHelper(textCalculate, input);

        buttons = new HashMap<>();

        Pattern p = Pattern.compile("(-?\\d+,?\\d*E?((\\+?)|(-?))\\d*)?");
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) input.setText(oldValue);
        });

        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLUMNS; j++) {
                String id = "button" + textForButtons[i][j];
                Button button = new Button();
                button.setText(textForButtons[i][j]);
                button.setId(id);
                button.setOnAction(this::click);
                button.setPrefHeight(100);
                button.setPrefWidth(100);
                buttons.put(id, button);
                buttonsPanel.add(button, j, i);
            }
        }
    }

    private void click(ActionEvent actionEvent) {
        String id = ((Node)actionEvent.getSource()).getId();
        if(buttons.containsKey(id)) {
            if (buttons.get(id).getText().equals("CE")) inputHelper.clearAllFields();
            else if (buttons.get(id).getText().equals("C")) inputHelper.clearInputField();
            else if (buttons.get(id).getText().equals("<=")) inputHelper.eraseSym();
            else if (buttons.get(id).getText().equals("+/-")) inputHelper.negative();
            else if (buttons.get(id).getText().equals("^2")) inputHelper.square();
            else if (buttons.get(id).getText().equals("1/x")) inputHelper.recNum();
            else if (buttons.get(id).getText().equals("=")) inputHelper.result();
            else {
                inputHelper.addSym(buttons.get(id).getText());
            }
        }
    }
}