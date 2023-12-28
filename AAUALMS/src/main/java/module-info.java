module com.themaestrocode.aaualms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.themaestrocode.aaualms to javafx.fxml;
    exports com.themaestrocode.aaualms;
}