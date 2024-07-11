module org.app.uas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.app.uas to javafx.fxml;
    exports org.app.uas;
    exports org.app.uas.entity;
    opens org.app.uas.controller to javafx.fxml;
    exports org.app.uas.controller;
}