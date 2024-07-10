module org.app.uas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.app.uas to javafx.fxml;
    exports org.app.uas;
    opens org.app.uas.controller to javafx.fxml;
    exports org.app.uas.controller;
}