package com.calculator.ui;

import com.calculator.controllers.CalculatorController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.Set;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Calculator {

    public static Scene prepareScene() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.BOTTOM_CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPrefHeight(50.0);
        pane.setPrefWidth(50.0);

        VBox vBox =  new VBox();
        vBox.setPrefHeight(120.0);
        vBox.setPrefWidth(250.0);
        vBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.setPadding(new Insets(10, 10, 0, 10));
        vBox.setBorder(
            new Border(
                new BorderStroke(
                    Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));

        Label screen = new Label("");
        screen.setFont(new Font(14));
        screen.setTextFill(Color.GRAY);

        Label result = new Label("0");
        result.setFont(new Font("Arial",20));

        vBox.getChildren().add(screen);
        vBox.setAlignment(Pos.BOTTOM_RIGHT);
        vBox.getChildren().add(result);
        VBox.setVgrow(screen, Priority.ALWAYS);
        VBox.setVgrow(result, Priority.ALWAYS);

        root.setCenter(pane);
        root.setTop(vBox);

        CalculatorController controller = new CalculatorController(screen, result);

        Button[][] buttons = new Button[6][4];
        String[] buttonLabels = {
                "(", ")", "C", "<<",
                "1/x", "√x", "x²", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "%", "0", ".", "="
        };

        int labelIndex = 0;

        //these are the clear, equal, dot and special function buttons, they will get their own handlers
        final Set<Integer[]> SPECIAL_BUTTONS_LIST = Set.of(new Integer[]{0, 2}, new Integer[]{0, 3}, new Integer[]{1, 0}, new Integer[]{1, 1}, new Integer[]{1, 2}, new Integer[]{5, 2}, new Integer[]{5, 3});

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                Integer [] buttonPosition = {i, j};
                buttons[i][j] = new Button(buttonLabels[labelIndex]);
                buttons[i][j].setPrefSize(50, 50);

            if (!SPECIAL_BUTTONS_LIST.contains(buttonPosition)) {
                buttons[i][j].setOnAction(controller.onButtonAction());
            }
            if (controller.OPERATORS.contains(buttons[i][j].getText())){
                buttons[i][j].setOnAction(controller.onOperatorButtonAction());
            }

                pane.add(buttons[i][j], j, i);
                labelIndex++;
            }
        }
        buttons[0][2].setOnAction(controller.onCButtonAction());
        buttons[0][3].setOnAction(controller.onBackspaceButtonAction());
        buttons[1][0].setOnAction(controller.onOneSlashXButtonAction());
        buttons[1][1].setOnAction(controller.onXSquareRootButtonAction());
        buttons[1][2].setOnAction(controller.onXSquaredButtonAction());
        buttons[5][2].setOnAction(controller.onDotButtonAction());
        // where the magic actually happens
        buttons[5][3].setOnAction(controller.onEqualsButtonAction());

        return new Scene(root, 250, 500);
    }
}
