module com.example.tubes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires charm.down.core;

    requires java.sql;
    opens com.example.tubes to javafx.fxml;
    exports com.example.tubes;
    opens com.example.tubes.auth;
    opens com.example.tubes.admin;
    opens com.example.tubes.dosen;
    opens com.example.tubes.quiz;
    opens com.example.tubes.mahasiswa;
}