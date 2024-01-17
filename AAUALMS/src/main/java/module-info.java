module com.themaestrocode.aaualms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javax.mail.api;


    opens com.themaestrocode.aaualms to javafx.fxml;
    exports com.themaestrocode.aaualms;
    opens com.themaestrocode.aaualms.entity to javafx.base;
    opens com.themaestrocode.aaualms.utility to javafx.base;

}