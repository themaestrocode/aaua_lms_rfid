module com.themaestrocode.aaualms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.themaestrocode.aaualms to javafx.fxml;
    exports com.themaestrocode.aaualms;
}