package com.farmalabfx.farmalabfx.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class homeController {

    @FXML
    public void clientePage(ActionEvent event) {

    }

    @FXML
    public void sairDoSistema(ActionEvent event) {
        Platform.exit();
    }
}
