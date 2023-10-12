module org.koffa.guiquizzer {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.google.gson;

    requires com.dlsc.formsfx;

    opens org.koffa.guiquizzer to javafx.fxml, lombok, com.google.gson;

    exports org.koffa.guiquizzer;
}