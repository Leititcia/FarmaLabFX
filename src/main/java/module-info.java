module com.farmalabfx.farmalabfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.management;

    opens com.farmalabfx.farmalabfx to javafx.fxml;
    exports com.farmalabfx.farmalabfx;
    exports com.farmalabfx.farmalabfx.controllers;
    opens com.farmalabfx.farmalabfx.controllers to javafx.fxml;
    exports com.farmalabfx.farmalabfx.models;
    opens com.farmalabfx.farmalabfx.models to javafx.fxml;
}