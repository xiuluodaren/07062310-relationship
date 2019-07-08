package com.relationship;

import com.relationship.DAO.UserDAO;
import com.relationship.domain.User;
import com.relationship.util.Constant;
import com.relationship.util.LoadPropertiesFile;
import com.relationship.util.StringUtil;
import com.relationship.util.XLUtil;
import com.relationship.view.MainView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

 public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("人际关系管理系统");
        //使用group可以用坐标布局
        Group group = new Group();
        Scene scene = new Scene(group, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT);

        //用户名
        Label userNameLabel = new Label("用户名:");
        userNameLabel.setPrefWidth(Constant.LABEL_WIDTH);
        userNameLabel.setLayoutX(Constant.MARGER_LEFT);
        userNameLabel.setLayoutY(Constant.V_MARGER);
        userNameLabel.setPrefHeight(Constant.C_HEIGHT);

        TextField userNameTF = new TextField();
        userNameTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        userNameTF.setLayoutY(Constant.V_MARGER);
        userNameTF.setPrefHeight(Constant.C_HEIGHT);

        //密码
        Label pwdLabel = new Label("密码:");
        pwdLabel.setPrefWidth(Constant.LABEL_WIDTH);
        pwdLabel.setLayoutX(Constant.MARGER_LEFT);
        pwdLabel.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT);
        pwdLabel.setPrefHeight(Constant.C_HEIGHT);

        PasswordField pwdTF = new PasswordField();
        pwdTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        pwdTF.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT);
        pwdTF.setPrefHeight(Constant.C_HEIGHT);

        //登陆
        Button loginBtn = new Button("登陆");
        loginBtn.setLayoutX(Constant.MARGER_LEFT * 2 + Constant.LABEL_WIDTH);
        loginBtn.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        loginBtn.setPrefWidth(Constant.LABEL_WIDTH);
        loginBtn.setOnAction((event) -> {
            String userName = userNameTF.getText();
            String pwd = pwdTF.getText();

            if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(pwd))
            {
                XLUtil.showAlert("提示","用户名或密码不能为空");
                return;
            }

            login(primaryStage, userName, pwd);

        });

        //退出
        Button exitBtn = new Button("退出");
        exitBtn.setLayoutX(Constant.MARGER_LEFT * 3 + Constant.LABEL_WIDTH * 2);
        exitBtn.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        exitBtn.setPrefWidth(Constant.LABEL_WIDTH);
        exitBtn.setOnAction((event) -> {
            Platform.exit();
        });


        //添加组件
        group.getChildren().addAll(userNameLabel, userNameTF, pwdLabel, pwdTF, loginBtn, exitBtn);

        //禁止缩放
        primaryStage.setResizable(false);
        //给舞台设置屏幕
        primaryStage.setScene(scene);
        //显示
        primaryStage.show();

        //是否debug
        if ("true".equals(LoadPropertiesFile.getProperty("DEBUG")))
        {
            System.out.println("debug");
            login(primaryStage, "admin", "admin");
        }

    }

    private void login(Stage primaryStage, String userName, String pwd) {
         User user = UserDAO.login(userName, pwd);

         if (user == null)
         {
             XLUtil.showAlert("提示","登陆失败");
         }else{
             //登陆成功,跳转页面
             MainView mainView = new MainView(Constant.SCENE_WIDTH * 4,Constant.SCENE_HEIGHT * 3);
             mainView.show();

             //隐藏登陆页面
             primaryStage.hide();
         }
     }

    public static void main(String[] args) {
        launch(args);
    }
}
