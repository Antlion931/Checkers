package com.example.checkersgrid;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameMenu extends VBox
{
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private final ChoiceBox<String> choiceBox;
    public GameMenu()
    {
        setAlignment(Pos.CENTER);
        setSpacing(50);
        setBackground(new Background(new BackgroundFill(Color.SEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        Label label = new Label("CHECKERS");
        label.setFont(new Font(60));
        choiceBox = new ChoiceBox<>();
        Button confirmType = new Button("CONFIRM");

        String[] choices = {"Klasyczne", "Polskie", "Wybijanka"};
        choiceBox.getItems().addAll(choices);

        getChildren().addAll(label, choiceBox, confirmType);
        listenSocket();

        confirmType.setOnAction(this::chooseType);
    }
    private void chooseType(ActionEvent actionEvent)
    {
        String type = choiceBox.getValue();
        out.println("CHOSE_GAME_TYPE " + type);
        try {
            String result = in.readLine();
            if(result.equals("OK"))
            {
                Factory factory = new BoardFactory();
                SimpleBoard board = factory.getBoard(type);
                int size = board.getSize();

                GameController gameController = new GameController(board, in, out);
                gameController.startGame();

                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(board.getPane(), (80 * size), (80 * size));
                stage.setResizable(false);
                stage.setTitle(type);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void listenSocket() {
        try {
            socket = new Socket("localhost", 44444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }
}
