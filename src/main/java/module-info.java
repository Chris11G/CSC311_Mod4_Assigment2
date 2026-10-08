module org.example.csc311_mod4_assigment2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.csc311_mod4_assigment2 to javafx.fxml;
    exports org.example.csc311_mod4_assigment2;
}