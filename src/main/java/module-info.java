module com.calculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.slf4j;
    requires static lombok;
    requires commons.lang3;
    requires expr4j;

    opens com.calculator to javafx.fxml;
    exports com.calculator;
}