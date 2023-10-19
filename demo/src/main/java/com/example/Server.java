package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import javafx.util.Duration;

import com.example.Controllers.ClientPageController;
import com.example.Controllers.ConnectionPageController;
import com.example.Controllers.MainPageController;
import com.example.Singletons.UserSingleton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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

    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;
    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ServerSocket serverSocket;
    private UserSingleton user;
    private ConnectionPageController connection;
    private DatagramPacket datagramPacket;
    private DatagramSocket datagramSocket;
    public Database db;
    

    public Server() {
        db = new Database();
        user = UserSingleton.getInstance();
    }


    public void startLocalServer() {
        try {
            serverSocket = new ServerSocket(db.getPort(user.getUserId()));
            System.out.println(user.getUsername() +": Server Established");
            datagramSocket = new DatagramSocket();
            // Accept requests from clients
            while (true) {
                Socket socket = serverSocket.accept();
                

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
    
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                String command = bufferedReader.readLine();

                // Client
                if (command.equals("Request Connection")) {
                    String serverId = bufferedReader.readLine();
                    establishClient(Integer.parseInt(serverId));
                }

                // Server 

            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Client
    private void establishClient(int serverId) {
        try {
            Socket socket = new Socket(db.getIp(serverId), db.getPort(serverId));
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);


            bufferedWriter.write("Establish Connection");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            outputStreamWriter.close();
            bufferedWriter.close();
            sendData(serverId);


        } catch (IOException | SQLException | AWTException e) {
            e.printStackTrace();
        }

    }

    // Client
    public void sendData(int serverId) throws AWTException, IOException, SQLException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InetAddress inetAddress = InetAddress.getByName(db.getIp(serverId));

		Robot r = new Robot();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            BufferedImage screen = r.createScreenCapture(new Rectangle((int) screenBounds.getWidth(), (int) screenBounds.getHeight()));
            System.out.println(user.getUsername() +": processing");
            try {
                ImageIO.write(screen, "jpg", byteArrayOutputStream);
                datagramPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), inetAddress, 1234);
                datagramSocket.send(datagramPacket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }));


        timeline.setCycleCount(10);
		timeline.play();
    }


    // Server
    public Image receiveData(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] sizeAr = new byte[4];
        inputStream.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
        System.out.println("Recieved Size: " + size);

        byte[] imageAr = new byte[size];
        inputStream.read(imageAr);


        BufferedImage screen = ImageIO.read(new ByteArrayInputStream(imageAr));
        Image image = SwingFXUtils.toFXImage(screen, null);

        return image;
    } 

    public void processData(Image screen) throws IOException {
        MainPageController controller = user.getLoader().getController();
        Platform.runLater(() -> {
            try {
                connection = controller.connection();
                connection.setScreen(screen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}


    // public void sendData(Socket socket) throws AWTException, IOException {
    //     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    //     outputStream = socket.getOutputStream();


    // public void sendData(Socket socket) throws AWTException, IOException {
    //     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    //     outputStream = socket.getOutputStream();

	// 	Robot r = new Robot();
    //     Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    //     while (true) {
    //         BufferedImage screen = r.createScreenCapture(new Rectangle((int) screenBounds.getWidth(), (int) screenBounds.getHeight()));
    //         System.out.println(user.getUsername() +": processing");
    //         ImageIO.write(screen, "jpg", byteArrayOutputStream);
    //         byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();


    //         outputStream.write(size);
    //         outputStream.write(byteArrayOutputStream.toByteArray());
    //         outputStream.flush();
    //     }

    // }


//     public void startLocalServer() {
//         try {
//             serverSocket = new ServerSocket(db.getPort(user.getUserId()));
//             System.out.println("connected to server");


//             while (true) {  
//                 Socket socket = serverSocket.accept();

//                 inputStreamReader = new InputStreamReader(socket.getInputStream());
//                 outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
    
//                 bufferedReader = new BufferedReader(inputStreamReader);
//                 bufferedWriter = new BufferedWriter(outputStreamWriter);

                
//                 // Client accept connection

//                 String command = bufferedReader.readLine();
//                 if (command.equals("Request Connection")) {
                    
//                     String serverIp = bufferedReader.readLine();
//                     establishClient(Integer.parseInt(serverIp));
//                 }
    
//                 if (command.equals("Establish Connection")) {
//                     String msg = bufferedReader.readLine();
//                     while (true) {
//                         InputStream inputStream = socket.getInputStream();
//                         byte[] sizeAr = new byte[4];
//                         inputStream.read(sizeAr);
//                         int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
//                         byte[] imageAr = new byte[size];
//                         BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

//                         Parent root = FXMLLoader.load(getClass().getResource("../../fxml/Connection.fxml"));
//                         Scene scene = new Scene(root);
//                         MainPageController controller = user.getLoader().getController();
//                         Stage stage = controller.getStage();
//                         stage.setScene(scene);

//                         ImageView view = (ImageView) root.getChildrenUnmodifiable().get(1);
//                     }
//                 }

                
//                 inputStreamReader.close();
//                 outputStreamWriter.close();
//                 bufferedReader.close();
//                 bufferedWriter.close(); 
//             }
//         } catch (IOException | SQLException e) {
//             e.printStackTrace();
//         }

//     }

//     public void establishClient(int serverId) {
//         try {
//             Socket socket = new Socket(db.getIp(serverId), db.getPort(serverId));

//             outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
//             bufferedWriter = new BufferedWriter(outputStreamWriter);

//             bufferedWriter.write("Establish Connection");
//             bufferedWriter.newLine();
//             bufferedWriter.flush();

//             sendData(outputStream);

//             socket.close();
//             inputStreamReader.close();
//             outputStreamWriter.close();
//             bufferedReader.close();
//             bufferedWriter.close(); 
//         } catch (IOException | SQLException | AWTException e) {
//             e.printStackTrace();
//         }
//     }




    
//     public void sendData(OutputStream outputStream) throws IOException, AWTException {

//         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        

// 		Robot r = new Robot();
// 		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05),
// 							event -> {
// 								Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
// 								BufferedImage screen = r.createScreenCapture(new Rectangle((int) screenBounds.getWidth(), (int) screenBounds.getHeight()));
// 								System.out.println("processing");
//                                 try {
//                                     ImageIO.write(screen, "jpg", byteArrayOutputStream);
//                                     byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
//                                     outputStream.write(size);
//                                     outputStream.write(byteArrayOutputStream.toByteArray());
//                                     outputStream.flush();
//                                 } catch (IOException e) {
//                                     e.printStackTrace();
//                                 }
// 							}));

// 		timeline.setCycleCount(Animation.INDEFINITE);
// 		timeline.play();
//     }

//     public void displayView() throws IOException {
//         Parent root = FXMLLoader.load(getClass().getResource("../../fxml/Connection.fxml"));
//         Scene scene = new Scene(root);
//         MainPageController controller = user.getLoader().getController();
//         Stage stage = controller.getStage();
//         stage.setScene(scene);

//         ImageView view = (ImageView) root.getChildrenUnmodifiable().get(1);
//     }
// }
