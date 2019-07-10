package com.relationship.view;

import com.relationship.domain.User;
import com.relationship.util.Constant;
import com.relationship.util.JDBCConnection;
import com.relationship.util.LoadPropertiesFile;
import com.relationship.util.LocalData;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-16 15:49<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: 主窗体<br>
 */
public class MainView extends BaseView {

//    private static String[] menus = new String[]{"用户管理","货物管理","审核提货单","出库管理","库存明细"};
    private static List<String> menus = new ArrayList<>();

    private HBox mainHBox = new HBox();
    private Pane pane = null;

    public MainView(Double width, Double height) {
        super(width, height);
    }

    @Override
    public void show() {

        User user = LocalData.getCurrentUser();

        //查询菜单
        String sql = "SELECT menuName,paneName FROM menu where type = " + user.getType() + " order by sort";
        ResultSet query = JDBCConnection.query(sql);

        try {
            while (query.next())
            {
                String meunName = query.getString(1);
                String paneName = query.getString(2);
                menus.add(meunName + "-" + paneName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Stage stage = new Stage();
        //舞台最大化
        stage.setMaximized(true);
        stage.setTitle("人际关系管理系统");
        //使用group可以用坐标布局
        Group group = new Group();
        Scene scene = new Scene(group, width, height);

        //头部水平框
        Group headGroup = new Group();
        HBox headHBox = new HBox(headGroup);
        headHBox.setLayoutX(0);
        headHBox.setLayoutY(0);
        headHBox.prefWidthProperty().bind(scene.widthProperty());
        headHBox.setPrefHeight(200);

        //背景图片
        ImageView backgroundImage = new ImageView("background.png");
        backgroundImage.setLayoutX(0);
        backgroundImage.setLayoutY(0);
        backgroundImage.fitWidthProperty().bind(scene.widthProperty());
        backgroundImage.setFitHeight(200);

        //添加一个标签到头部
        Label headLabel = new Label("欢迎:" + user.getUserName());
        headLabel.setFont(new Font("Arial", 30));
        headLabel.setTextFill(Color.RED);
        headLabel.setLayoutX(400);
        headLabel.setLayoutY(150);
        headLabel.setPrefHeight(50);

        //作者信息
        Label nameLabel = new Label(LoadPropertiesFile.getProperty("message"));
        nameLabel.setFont(new Font("Arial", 30));
        nameLabel.setTextFill(Color.BLUE);
        nameLabel.setLayoutX(600);
        nameLabel.setLayoutY(150);
        nameLabel.setPrefHeight(50);
        nameLabel.setPrefWidth(200);

        //添加到头部水平框
        headGroup.getChildren().addAll(backgroundImage, headLabel, nameLabel);


        //左侧菜单框
        Group leftGroup = new Group();
        VBox leftVBox = new VBox(leftGroup);
        leftVBox.setLayoutX(0);
        leftVBox.setLayoutY(200);
        leftVBox.setPrefWidth(100);
        leftVBox.prefHeightProperty().bind(scene.heightProperty().subtract(200));
        leftVBox.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 1;" + "-fx-border-insets: 5;"
                + "-fx-border-color: black;");

        //添加菜单按钮
        for (int i = 0;i < menus.size();i++)
        {
            Button menuBtn = new Button(menus.get(i).split("-")[0]);
            menuBtn.setLayoutX(Constant.H_MARGER);
            menuBtn.setLayoutY(Constant.V_MARGER * (i + 1) + Constant.C_HEIGHT * i);
            menuBtn.setPrefHeight(Constant.C_HEIGHT);
            menuBtn.setPrefWidth(Constant.LABEL_WIDTH);
            int finalI = i;
            menuBtn.setOnAction(event -> {
                switchMenu(finalI);
            });
            leftGroup.getChildren().add(menuBtn);
        }

        //显示区域水平控件
        mainHBox.setLayoutX(100);
        mainHBox.setLayoutY(200);
        mainHBox.layoutXProperty().bind(leftVBox.widthProperty().add(leftVBox.layoutXProperty()));
        mainHBox.prefWidthProperty().bind(scene.widthProperty().subtract(100));
        mainHBox.prefHeightProperty().bind(scene.heightProperty().subtract(200));

        //添加组件
        group.getChildren().addAll(headHBox, leftVBox, mainHBox);

        //禁止缩放
//        stage.setResizable(false);
        //给舞台设置屏幕
        stage.setScene(scene);
        //显示
        stage.show();
    }

    /**
     * 选择菜单
     * @param index
     */
    public void switchMenu(int index)
    {
        String menu = menus.get(index);
        String paneName = menu.split("-")[1];

        //拼接包名
        paneName = getClass().getPackage().getName() + "." + paneName;
//        paneName = "com.relationship.view." + paneName;

        System.out.printf("选择菜单:%d %s\r\n", index, menu);
        mainHBox.getChildren().clear();

        try {
            Class<?> aClass = Class.forName(paneName);
            pane = (Pane) aClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置显示视图
        pane.prefWidthProperty().bind(mainHBox.widthProperty());
        pane.prefHeightProperty().bind(mainHBox.heightProperty());
        mainHBox.getChildren().add(pane);
    }

}
