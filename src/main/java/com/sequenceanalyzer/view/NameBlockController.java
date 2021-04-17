package com.sequenceanalyzer.view;

import com.sequenceanalyzer.util.Osc;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class NameBlockController {

    @FXML public Label project_name;
    @FXML public Button playAndStop;


    public void play(MouseEvent mouseEvent) {
        Osc.getInstance().start();
    }
}
