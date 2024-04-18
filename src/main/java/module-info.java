module com.debtfreeapp.dfproject2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.debtfreeapp.dfproject2 to javafx.fxml;
    exports com.debtfreeapp.dfproject2;
}