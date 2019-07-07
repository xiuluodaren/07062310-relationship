package com.wms.view;

import com.wms.DAO.UserDAO;
import com.wms.domain.User;
import com.wms.util.Constant;
import com.wms.util.StringUtil;
import com.wms.util.XLUtil;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-16 12:40<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: <br>
 */
public class RegisterView extends BaseView {

    public RegisterView(Double width, Double height) {
        super(width, height);
    }

    @Override
    public void show ()
    {
        Stage stage = new Stage();
        
        stage.setTitle("注册");
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

        //确认密码
        Label confirmPwdLabel = new Label("确认密码:");
        confirmPwdLabel.setPrefWidth(Constant.LABEL_WIDTH);
        confirmPwdLabel.setLayoutX(Constant.MARGER_LEFT);
        confirmPwdLabel.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        confirmPwdLabel.setPrefHeight(Constant.C_HEIGHT);

        PasswordField confirmPwdTF = new PasswordField();
        confirmPwdTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        confirmPwdTF.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        confirmPwdTF.setPrefHeight(Constant.C_HEIGHT);

        //注册
        Button registerBtn = new Button("注册");
        registerBtn.setLayoutX(Constant.MARGER_LEFT);
        registerBtn.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        registerBtn.setPrefWidth(Constant.LABEL_WIDTH * 3);
        registerBtn.setOnAction(event -> {

            String userName = userNameTF.getText();
            String pwd = pwdTF.getText();
            String confirmPwd = confirmPwdTF.getText();

            //校验密码
            if (StringUtil.isEmpty(pwd) || !pwd.equals(confirmPwd))
            {
                XLUtil.showAlert("提示","两次输入密码不一致");
                return;
            }else{
                //注册
                boolean register = UserDAO.register(userName, pwd);
                if (register == true)
                {
                    XLUtil.showAlert("成功","注册成功");
                }else{
                    XLUtil.showAlert("失败","注册失败");
                }
            }

        });

        //添加组件
        group.getChildren().addAll(userNameLabel, userNameTF, pwdLabel, pwdTF, confirmPwdLabel, confirmPwdTF, registerBtn);

        //禁止缩放
        stage.setResizable(false);
        //给舞台设置屏幕
        stage.setScene(scene);
        //显示
        stage.show();

    }

}
