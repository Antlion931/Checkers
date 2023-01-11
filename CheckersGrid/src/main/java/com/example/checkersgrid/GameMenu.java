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

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class GameMenu extends VBox
{
    private final ChoiceBox<String> choiceBox;
    private Scanner in;
    private PrintWriter out;
    public GameMenu(Scanner in, PrintWriter out)
    {
        this.in = in;
        this.out = out;
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

        confirmType.setOnAction(this::chooseType);
    }
    private void chooseType(ActionEvent actionEvent)
    {
        Factory factory = new BoardFactory();
        String type = choiceBox.getValue();
        SimpleBoard board = factory.getBoard(type);
        int size = board.getSize();

        GameController gameController = new GameController(board, in, out);
        var pool = Executors.newFixedThreadPool(200);
        pool.execute(gameController.new ServerCommunicator());

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(board.getPane(), (80 * size), (80 * size));
        stage.setResizable(false);
        stage.setTitle(type);
        stage.setScene(scene);
        stage.show();
    }
}
