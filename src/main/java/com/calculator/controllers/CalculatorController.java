package com.calculator.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;

import tk.pratanumandal.expr4j.ExpressionEvaluator;

import java.util.List;

import static java.lang.Double.NaN;

@Slf4j
public class CalculatorController {

    private Label screen;
    private Label result;
    private ExpressionEvaluator evaluator;

    public final List<String> OPERATORS = List.of("%", "/", "*", "-", "+", "=");

    public CalculatorController(Label screen, Label result) {
        this.screen = screen;
        this.result = result;
        evaluator = new ExpressionEvaluator();
    }

    public EventHandler<ActionEvent> onButtonAction() {
        return event -> {
          Button clickedButton = (Button) event.getSource();
          screen.setText(screen.getText() + clickedButton.getText());
        };
    }

    public EventHandler<ActionEvent> onOperatorButtonAction() {
        return event -> {
            Button clickedButton = (Button) event.getSource();
            String operator = clickedButton.getText();
            //add it to the expression if the result isn't empty
            if (!result.getText().equals("0")) {
                screen.setText(result.getText() + operator);
                result.setText("0");
            } else {
                screen.setText(screen.getText() + operator);
            }
        };
    }

    private void handlePreviousResult(String function) {
        if (!result.getText().equals("0")) {
            screen.setText(screen.getText() + function + result.getText());
        } else {
            screen.setText(function + screen.getText());
        }
    }

    public EventHandler<ActionEvent> onCButtonAction() {
        return event -> {
            screen.setText("");
            result.setText("0");
        };
    }

    public EventHandler<ActionEvent> onOneSlashXButtonAction() {
        String function = "1 / ";
    return event -> {
      if (!screen.getText().isEmpty() || !screen.getText().equals("0")) {
        handlePreviousResult(function);
      }
    };
    }

    public EventHandler<ActionEvent> onXSquaredButtonAction() {
        String function = " ^ 2";
        return event -> {
            if (!screen.getText().isEmpty() || !screen.getText().equals("0")) {
                handlePreviousResult(function);
            }
        };
    }

    public EventHandler<ActionEvent> onXSquareRootButtonAction() {
        String function = "sqrt( ";
        return event -> {
            if (!screen.getText().isEmpty() || !screen.getText().equals("0")) {
                handlePreviousResult(function);
            }
        };
    }

    public EventHandler<ActionEvent> onBackspaceButtonAction() {
        return event -> {
            if (!screen.getText().isEmpty()) {
                screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
            }
        };
    }

    public EventHandler<ActionEvent> onEqualsButtonAction() {
        return event -> {
            if (!screen.getText().isEmpty() || !screen.getText().equals("0")) {
                try {
                    int intResult;
                    double expressionResult = evaluator.evaluate(screen.getText());
                    if (expressionResult == (int)expressionResult) {
                        intResult = (int)expressionResult;
                        result.setText(String.valueOf(intResult));
                        log.info(String.valueOf(intResult));
                    }
                    //if no value change it to an error message
                    else if (Double.isNaN(expressionResult)) {
                        result.setText("ERROR");
                    }
                    else {
                        result.setText(String.valueOf(expressionResult));
                        log.info(String.valueOf(expressionResult));
                    }
                }
                catch (Exception e) {
                    log.error(e.getMessage());
                    result.setText("ERROR");
                }
                finally{
                    screen.setText("");
                }
            }
        };
    }

    public EventHandler<ActionEvent> onDotButtonAction() {
        return event -> {
            //add the dot only if the expression isn't empty, doesn't already contain a dot and doesn't end in an operator
          if (!screen.getText().isEmpty() && !screen.getText().contains(".") && !OPERATORS.contains(screen.getText().substring(screen.getText().length() - 1))) {
            screen.setText(screen.getText() + ".");
          }
        };
    }

}
