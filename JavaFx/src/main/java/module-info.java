module com.fh.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;


    opens com.fh.javafx to javafx.fxml;
    exports com.fh.javafx;
}