package com.example.konyvtarasztali;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class Main extends javafx.application.Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/konyvtarasztali/konyvtar-view.fxml"));
        Scene scene =  new Scene(fxmlLoader.load(), 720, 420);;
        stage.setTitle("Könyvtár");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if (Arrays.asList(args).contains("--stat")){
            Statisztika.main(args);
        }else{
            launch();
        }
    }
}
