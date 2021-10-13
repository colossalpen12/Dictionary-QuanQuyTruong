module tudienbachkhoa.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires charm.glisten;
    requires java.sql;
    requires com.jfoenix;

    opens tudienbachkhoa.dictionary to javafx.fxml;
    exports tudienbachkhoa.dictionary;

}