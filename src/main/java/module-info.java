module com.example.stockmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.jfoenix;
    requires java.sql;
    requires atlantafx.base;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.javafx;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    //itextpdf
    requires itextpdf;

    opens com.example.stockmanagement to javafx.fxml;

    exports com.example.stockmanagement;

    opens com.example.stockmanagement.Controller to javafx.fxml;

    exports com.example.stockmanagement.Controller;

    opens com.example.stockmanagement.Model.Classes to javafx.fxml;

    exports com.example.stockmanagement.Model.Classes;

    opens com.example.stockmanagement.Model.Users to javafx.fxml;

    exports com.example.stockmanagement.Model.Users;
}
