package com.example.javafxdemo.controller.FirstPage.login;

import com.example.javafxdemo.controller.*;
import com.example.javafxdemo.utils.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
/**
 * Class ScanFileController is used to control scan-id.fxml
 * which for scanning the ID card
 *
 * @author RongxiYe
 * @version 1.0
 */
public class ScanFileController implements Controller {
    /**
     * the confirmation button of uploading the file
     */
    @FXML
    private Button confirmUpload;
    /**
     * the hyperlink to hello-view.fxml
     */
    @FXML
    private Hyperlink booknum;
    /**
     * the hyperlink to input-id.fxml
     */
    @FXML
    private Hyperlink inputID;
    /**
     * the file uploaded for scanning
     */
    private File file;

    /**
     * init() is used to initialize.
     */
    @Override
    public void init(){}

    /**
     * onClickConfirmUpload() is used to react to the click of confirm button.
     * If the id number and name is correct, it will fetch the information.
     * If the id number and name cannot be found, it will alert illegal.
     */
    @FXML
    public void onClickConfirmUpload(){
        try {
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(new FileReader(file));
            String id = object.get("id").getAsString();
            String name = object.get("name").getAsString();
            UserData ud = Utils.login(name,id);
            Utils.loginCheck(ud,"Illegal id card! Please re-upload!");
            file=null;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * onClickUploadId() is used to upload the file for id checking.
     * After clicking the button, user can choose a file to upload.
     */
    @FXML
    public void onClickUploadId(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );
        file = fileChooser.showOpenDialog(Handler.stage);
    }

    /**
     * inputBooknum() is used to get to the hello-view.fxml after clicking hyperlink.
     */
    @FXML
    public void inputBooknum(){
        MainController main = (MainController) Handler.getController(Page.MAIN);
        main.loadRoot(Page.BOOKNUMLOGIN);
    }

    /**
     * inputId() is used to get to the input-id.fxml after clicking hyperlink.
     */
    @FXML
    protected void inputID(){
        MainController main = (MainController)Handler.getController(Page.MAIN);
        main.loadRoot(Page.IDINFOLOGIN);
    }

}
