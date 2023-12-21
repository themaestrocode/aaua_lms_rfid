module com.themaestrocode.aaualms {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.themaestrocode.aaualms to javafx.fxml;
    exports com.themaestrocode.aaualms;
    exports com.themaestrocode.aaualms.Controllers;
    opens com.themaestrocode.aaualms.Controllers to javafx.fxml;
}