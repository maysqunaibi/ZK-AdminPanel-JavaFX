module com.managment.assignmentdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.managment.assignmentdemo to javafx.fxml;
    exports com.managment.assignmentdemo;
}