package com.example2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PrimaryController {

    //跳到第二視窗
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML private javafx.scene.control.Button exit;

    //離開
    @FXML
    private void exit(){
        Stage stage=(Stage)exit.getScene().getWindow();
        stage.close();
    }
}
