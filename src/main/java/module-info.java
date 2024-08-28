module com.photon {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.postgresql.jdbc;
    requires javafx.graphics;
    requires java.sql; // Add this line

    opens com.photon to javafx.fxml;
    exports com.photon;
}
