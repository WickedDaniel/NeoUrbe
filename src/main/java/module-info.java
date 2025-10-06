module com.poo.neourbe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires java.desktop;
//    requires com.poo.neourbe;

    opens com.poo.neourbe to javafx.fxml;
    exports com.poo.neourbe;
}