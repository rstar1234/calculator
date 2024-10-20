package com.calculator;

import com.calculator.ui.Calculator;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        Scene scene = Calculator.prepareScene();
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/calculator/icon/icon-calculator.png")));
        stage.show();
        Parent root = scene.getRoot();
        root.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}