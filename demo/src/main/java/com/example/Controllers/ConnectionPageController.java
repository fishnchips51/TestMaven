package com.example.Controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ConnectionPageController {

    @FXML
    private ImageView screenCapture;

    @FXML
    private Pane test;

    public void setScreen(Image image) throws IOException {
        System.out.println("Displying image");
        screenCapture.setImage(image);
   
    }
}
