package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import javafx.util.Duration;

import com.example.Controllers.MainPageController;
import com.example.Singletons.UserSingleton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.Rectangle;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class Server {
    private Socket socket;
    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ServerSocket serverSocket;
    private UserSingleton user;
    public Database db;
    

    public Server() {
        db = new Database();
        user = UserSingleton.getInstance();
    }

    public void startLocalServer() {
        try {
            serverSocket = new ServerSocket(db.getPort(user.getUserId()));
            System.out.println("connected to server");


            while (true) {  
                socket = serverSocket.accept();

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
    
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                // Client accept connection

                String command = bufferedReader.readLine();
                if (command.equals("Request Connection")) {
                    
                    String serverIp = bufferedReader.readLine();
                    establishClient(Integer.parseInt(serverIp));
                }
    
                if (command.equals("Establish Connection")) {
                    String msg = bufferedReader.readLine();
                    
                }

                
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close(); 
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void establishClient(int serverId) {
        try {
            socket = new Socket(db.getIp(serverId), db.getPort(serverId));

            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader);


            bufferedWriter.write("Establish Connection");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            for (int i = 0; i < 1; i++) {

                bufferedWriter.write(user.getUsername() + ": hello word");
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }

            socket.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            bufferedReader.close();
            bufferedWriter.close(); 
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendData(OutputStreamWriter outputStreamWriter) throws IOException, AWTException {

		Robot r = new Robot();
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05),
							event -> {
								Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
								BufferedImage screen = r.createScreenCapture(new Rectangle((int) screenBounds.getWidth(), (int) screenBounds.getHeight()));
								System.out.println("processing");

							}));

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
    }

    public void displayView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/Connection.fxml"));
        Scene scene = new Scene(root);
        MainPageController controller = user.getLoader().getController();
        Stage stage = controller.getStage();
        stage.setScene(scene);

        ImageView view = (ImageView) root.getChildrenUnmodifiable().get(1);
    }
}
