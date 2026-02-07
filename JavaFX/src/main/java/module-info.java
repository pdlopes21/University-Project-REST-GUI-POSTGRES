module pt.ul.fc.di.css.javafxexample {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires transitive javafx.graphics;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires java.net.http;
    requires javafx.base;

    

    opens pt.ul.fc.di.css.javafxexample to javafx.fxml, javafx.web, 
            com.fasterxml.jackson.databind, com.fasterxml.jackson.core, com.fasterxml.jackson.datatype;
    opens pt.ul.fc.di.css.javafxexample.controller to javafx.fxml, com.fasterxml.jackson.databind, com.fasterxml.jackson.core, com.fasterxml.jackson.datatype;
    opens pt.ul.fc.di.css.javafxexample.dto to javafx.base, com.fasterxml.jackson.databind, com.fasterxml.jackson.core, com.fasterxml.jackson.datatype;

    exports pt.ul.fc.di.css.javafxexample;
    exports pt.ul.fc.di.css.javafxexample.dto;
    exports pt.ul.fc.di.css.javafxexample.model;
}