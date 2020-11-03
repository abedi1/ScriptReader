package com.favor;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Simple Hello World JavaFX program
public class UI extends Application {
    public String fullPath = "";
    public int textIndex = -1;
    public String characterInput ="";
    public String voiceActor = "";
    public ArrayList<String> sceneTranscript = new ArrayList<>();
    public ReturnObject returnObject = new ReturnObject("",-1);
    public static void main(String[] args) {
        launch(args);
    }

    // JavaFX entry point
    @Override
    public void start(Stage primaryStage) throws Exception {
        String message = "Taming of the Shrew Script Reader";
        Label lines = new Label();
        lines.setWrapText(false);

        //Top layout
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        Button buttonStart = new Button("Start");

        Button continueButton = new Button("Continue Line Reading");

        Button helpButton = new Button("What were my lines");
        hbox.getChildren().addAll(buttonStart, continueButton, helpButton);
        hbox.setAlignment(Pos.CENTER);
        // end of top layout

        // CENTER layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // column 0 row 0
        Text title = new Text("Your lines were");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.setAlignment(Pos.TOP_CENTER);
        grid.add(title, 0, 0);

        // Big text boc underneat that
        grid.add(lines, 0, 1);


        // TODO: LEFT TEXT
        GridPane left = new GridPane();
        left.setHgap(10);
        left.setVgap(10);
        left.setPadding(new Insets(0, 10, 0, 10));

        // CHARACTER SELECTIOn
        Text characterText = new Text("Pick your character");
        characterText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        left.add(characterText,0,1);
        final ComboBox<String> characters = new ComboBox();
        characters.getItems().addAll(
                "Bella",
                "Bernard",
                "Brent",
                "Designer",
                "Greg",
                "Greta",
                "Henrietta",
                "Keith",
                "Lucy",
                "Peter",
                "Petra",
                "Servant",
                "Trina",
                "Widower"
        );
        left.add(characters,1,1);

        // ACT Selection
        Text actText = new Text("What act");
        actText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        left.add(actText,0,2);
        final ComboBox<String> act = new ComboBox();
        act.getItems().addAll(
                "Act 1","Act 2","Act 3", "Act 4", "Act 5"
        );
        left.add(act,1,2);

        // SCENE SELECTION
        String[] act1Scene = {"Scene 1", "Scene 2"};
        String[] act2Scene = {"Scene 1"};
        String[] act3Scene = {"Scene 1", "Scene 2"};
        String[] act4Scene = {"Scene 1", "Scene 2", "Scene 3", "Scene 4", "Scene 5"};
        String[] act5Scene = {"Scene 1", "Scene 2"};
        final ComboBox<String> scene = new ComboBox();
        act.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                scene.getItems().clear();
                if(newValue.contains("1")) {
                    for(int i = 0; i<act1Scene.length;i++){
                        scene.getItems().add(act1Scene[i]);
                    }
                } else if(newValue.contains("2")) {
                    for(int i = 0; i<act2Scene.length;i++){
                        scene.getItems().add(act2Scene[i]);
                    }
                }else if(newValue.contains("3")) {
                    for (int i = 0; i < act3Scene.length; i++) {
                        scene.getItems().add(act3Scene[i]);
                    }
                } else if(newValue.contains("4")) {
                    for (int i = 0; i < act4Scene.length; i++) {
                        scene.getItems().add(act4Scene[i]);
                    }
                } else if (newValue.contains("5")){
                    for(int i = 0; i< act5Scene.length; i++){
                        scene.getItems().add(act5Scene[i]);
                    }
                }
            }
        });
        Text sceneText = new Text("What scene");
        sceneText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        left.add(sceneText,0,3);
        left.add(scene,1,3);


        Text voiceText = new Text("Pick your voice actor");
        voiceText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ComboBox<String> voice = new ComboBox();
       voice.getItems().addAll("British Man 1", "British Woman 1", "British Man 2", "British Man 3", "American Man 1", "American Man 2", "American Woman 1");
       left.add(voiceText,0,4);
       left.add(voice,1,4);

        Text information = new Text("Please select your act before selecting your scene");
        information.setFont(Font.font("Arial", 12));
        left.add(information,0,5,2,1);
        Text information2 = new Text("Start has a bit of a lag to it. Just give it a couple of seconds");
        information2.setFont(Font.font("Arial", 12));
        left.add(information2,0,6,2,1);
        // TODO: LEFT TEXT END

        Label error = new Label();
        error.setTextFill(Color.RED);
        error.setWrapText(true);
        error.setVisible(false);
        left.add(error,0,7,2,1);

        // A layout container for com.favor.UI controls
        BorderPane border = new BorderPane();
        border.setTop(hbox);
        border.setLeft(left);

        border.setCenter(grid);


        // Top level container for all view content
        Scene screen = new Scene(border, primaryStage.getWidth(), primaryStage.getHeight());

        // primaryStage is the main top level window created by platform
        primaryStage.setTitle(message);
        primaryStage.setScene(screen);
        primaryStage.show();


        buttonStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                error.setVisible(false);
                lines.setText(" ");
                returnObject = new ReturnObject("",-1); // reset
                sceneTranscript.clear();
                //PreProcessing stuff
                {
                    // PRE-processing stuff
                    String[] inputVoice = {"British Man 1", "British Woman 1", "British Man 2", "British Man 3", "American Man 1", "American Man 2", "American Woman 1"};
                    String[] outputVoice = {"dfki-obadiah-hsmm", "dfki-poppy-hsmm", "dfki-spike-hsmm", "dfki-prudence-hsmm", "cmu-rms-hsmm", "cmu-bdl-hsmm", "cmu-slt-hsmm"};
                    voiceActor = outputVoice[0];
                    for (int i = 0; i < inputVoice.length; i++) {
                        if (voice.getValue().equals(inputVoice[i])) {
                            voiceActor = outputVoice[i];
                            break; // all done
                        }
                    }
                    //selecting scene
                    String firstHalfText = "C:\\Users\\Aman\\IdeaProjects\\scriptReader\\src\\main\\scripts\\";
                    String[] textFiles = {"1-1.txt.txt", "1-2.txt.txt", "2-1.txt.txt", "3-1.txt.txt", "3-2.txt.txt",
                            "4-1.txt.txt", "4-2.txt.txt", "4-3.txt.txt", "4-4.txt.txt", "4-5.txt.txt", "5-1.txt.txt", "5-2.txt.txt"};

                    String[] act1Scene = {"Scene 1", "Scene 2"};
                    String[] act3Scene = {"Scene 1", "Scene 2"};
                    String[] act4Scene = {"Scene 1", "Scene 2", "Scene 3", "Scene 4", "Scene 5"};
                    String[] act5Scene = {"Scene 1", "Scene 2"};

                    //Selecting text file;
                    if (act.getValue().equals("Act 1")) {
                        if (scene.getValue().equals(act1Scene[0])) {
                            textIndex = 0;
                        } else {
                            textIndex = 1;
                        }
                    } else if (act.getValue().equals("Act 2")) {
                        textIndex = 2;
                    } else if (act.getValue().equals("Act 3")) {
                        if (scene.getValue().equals(act3Scene[0])) {
                            textIndex = 3;
                        } else {
                            textIndex = 4;
                        }
                    } else if (act.getValue().equals("Act 4")) {
                        if (scene.getValue().equals(act4Scene[0])) {
                            textIndex = 5;
                        } else if (scene.getValue().equals(act4Scene[1])) {
                            textIndex = 6;
                        } else if (scene.getValue().equals(act4Scene[2])) {
                            textIndex = 7;
                        } else if (scene.getValue().equals(act4Scene[3])) {
                            textIndex = 8;
                        } else {
                            textIndex = 9;
                        }
                    } else {
                        if (scene.getValue().equals(act5Scene[0])) {
                            textIndex = 10;
                        } else {
                            textIndex = 11;
                        }
                    }
                    String fullPath = firstHalfText + textFiles[textIndex];

                    //selecting character;
                    characterInput = characters.getValue().toUpperCase();

                    File file = new File(fullPath);

                    try {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            sceneTranscript.add(scanner.nextLine());
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if(sceneTranscript.get(0).contains(characterInput)){
                    error.setText("You speak first in this scene");
                    error.setVisible(true);
                    Voice speaker = new Voice(voiceActor);
                    try{
                        speaker.say("You speak first in this scene");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                returnObject = startCode(0);
                error.setText("It is your turn. When ready please press continue or the what were my lines button");
                error.setVisible(true);
            }
        });

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (returnObject.index <0){
                    error.setText("Please press start before pressing continue");
                    error.setVisible(true);
                } else{
                    error.setVisible(false);
                    lines.setText(returnObject.yourLines);
                    if(returnObject.index == sceneTranscript.size()-1){
                        error.setText("This scene is over. Please select another scene");
                        error.setVisible(true);
                    } else {
                        error.setText("Please press continue when ready");
                        error.setVisible(true);
                    }
                }

            }
        });

        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(returnObject.index<0){
                    error.setText("Please press start before pressing continue");
                    error.setVisible(true);
                } else {
                    error.setVisible(false);
                    lines.setText(" ");

                    if(sceneTranscript.get(returnObject.index).contains(characterInput)){
                        error.setText("You speak first in this scene");
                        error.setVisible(true);
                        Voice speaker = new Voice(voiceActor);
                        try{
                            speaker.say("You speak first in this scene");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    returnObject = startCode(returnObject.index);
                    if(returnObject.index == sceneTranscript.size()-1){
                        error.setText("This scene is over. Please select another scene");
                        error.setVisible(true);
                    } else {
                        error.setText("It is your turn. When finished please press continue or the what were my lines button");
                        error.setVisible(true);
                    }

                }
            }
        });

    }

    public ReturnObject startCode(int transcriptIndex)  {
        //TODO: com.favor.Voice stuff;
        String yourLines = "";
        boolean speak = true;
        Voice speaker = new Voice(voiceActor);

        while (transcriptIndex < sceneTranscript.size()) {

            if (sceneTranscript.get(transcriptIndex).contains(characterInput)) {
                transcriptIndex++;
                speak = false;
            } else if (speak == false && Main.allCaps(sceneTranscript.get(transcriptIndex))) {
                break;
            }
            if (speak) {
                try {
                    speaker.say(sceneTranscript.get(transcriptIndex));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else{
                yourLines = yourLines + "\n" + sceneTranscript.get(transcriptIndex);
            }
            transcriptIndex++;
        }

        return new ReturnObject(yourLines, transcriptIndex);
    }

    public GridPane addGBox(){
        GridPane left = new GridPane();
        left.setHgap(10);
        left.setVgap(10);
        left.setPadding(new Insets(0, 10, 0, 10));

        Text character = new Text("Pick your character");
        character.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        left.add(character,0,0);
        final ComboBox characters = new ComboBox();
        characters.getItems().addAll(
                "Bella",
                "Bernard",
                "Brent",
                "Designer",
                "Greg",
                "Greta",
                "Henrietta",
                "Keith",
                "Lucy",
                "Peter",
                "Petra",
                "Servant",
                "Trina",
                "Widower"
                );
        left.add(characters,1,0);

        Text actText = new Text("What Act");
        actText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        left.add(actText,0,1);
        final ComboBox act = new ComboBox();
        act.getItems().addAll(
                "Act 1","Act 2","Act 3", "Act 4", "Act 5"
        );
        left.add(characters,1,1);
        String[] act1Scene = {"Scene 1", "Scene 2"};
        String[] act2Scene = {"Scene 1"};
        String[] act3Scene = {"Scene 1", "Scene 2"};
        String[] act4Scene = {"Scene 1", "Scene 2", "Scene 3", "Scene 4", "Scene 5"};
        String[] act5Scene = {"Scene 1", "Scene 2"};
        final ComboBox scene = new ComboBox();
        act.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                scene.getItems().removeAll();
                if(newValue.contains("1")) {
                    for(int i = 0; i<act1Scene.length;i++){
                        scene.getItems().add(act1Scene[i]);
                    }
                } else if(newValue.contains("2")) {
                    for(int i = 0; i<act2Scene.length;i++){
                        scene.getItems().add(act2Scene[i]);
                    }
                }else if(newValue.contains("3")) {
                    for (int i = 0; i < act3Scene.length; i++) {
                        scene.getItems().add(act3Scene[i]);
                    }
                } else if(newValue.contains("4")) {
                    for (int i = 0; i < act4Scene.length; i++) {
                        scene.getItems().add(act4Scene[i]);
                    }
                } else if (newValue.contains("5")){
                    for(int i = 0; i< act5Scene.length; i++){
                        scene.getItems().add(act5Scene);
                    }
                }
            }
        });
        Text sceneText = new Text("What Scene");
        actText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        left.add(actText,0,3);
        left.add(scene,1,3);
        Text information = new Text("Please select your act before selecting your scene");
        actText.setFont(Font.font("Arial", 12));
        left.add(information,0,4,1,0);
        return left;
    }


}