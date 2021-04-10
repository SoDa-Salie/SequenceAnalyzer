package com.sequenceanalyzer;

import com.sequenceanalyzer.util.Osc;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class SequenceAnalyzer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/main_layout.fxml"));
        loader.setResources(ResourceBundle.getBundle("com.sequenceanalyzer.i18n.SequenceAnalyzerApp", Locale.ENGLISH));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("SequenceAnalyzer");
        primaryStage.setScene(scene);
        primaryStage.show();
        Osc.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
