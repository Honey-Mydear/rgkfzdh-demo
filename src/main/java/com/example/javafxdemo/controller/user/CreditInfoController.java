package com.example.javafxdemo.controller.user;

import com.example.javafxdemo.controller.Controller;
import com.example.javafxdemo.controller.Handler;
import com.example.javafxdemo.controller.MainController;
import com.example.javafxdemo.data.CurrentData;
import com.example.javafxdemo.utils.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.FileReader;

public class CreditInfoController implements Controller {

    @FXML
    private Button confirmInput;
    @FXML
    private TextField cardID;
    @FXML
    private PasswordField password;

    public AnchorPane CreditInfoAnchor;

    public void init(){
        cardID.setTextFormatter(new TextFormatter<String>(new IntegerFilter()));
    }

    @FXML
    public void onClickConfirmInput(){
        Alert alert;
        if (cardID.getText().trim().isEmpty() || cardID.getText() == null || password.getText().trim().isEmpty() || password.getText()  == null) {
            alert = new Alert(Alert.AlertType.ERROR, "Empty input! Please input something!");
            alert.showAndWait();
            System.out.println("empty");
        } else {
            if(creditCheck(cardID.getText().trim(),password.getText().trim())){
                MainController main = (MainController) Handler.getController(Page.MAIN);
                main.loadRoot(Page.CHECKINVIEW);
            }else{
                alert = new Alert(Alert.AlertType.ERROR, "Error! Please input correct info!");
                alert.showAndWait();
            }

        }
    }

    private boolean creditCheck(String no, String pass){
        File file = new File(ClassPath.classPath+"Credit.json");
        try {
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(new FileReader(file));
            JsonArray array = object.get("CreditCard").getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject subObject = array.get(i).getAsJsonObject();
                String name = CurrentData.userData.getFirstname()+" "+CurrentData.userData.getSurname();
                String nameInFile = subObject.get("name").getAsString();
                String noInFile = subObject.get("cardNo").getAsString();
                String passInFile = subObject.get("cardPass").getAsString();
                if (nameInFile.equals(name)&&no.equals(noInFile)&&pass.equals(passInFile)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
