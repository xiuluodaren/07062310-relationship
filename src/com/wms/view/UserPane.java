package com.wms.view;

import com.wms.DAO.UserDAO;
import com.wms.domain.User;
import com.wms.util.Constant;
import com.wms.util.LoadPropertiesFile;
import com.wms.util.XLUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-16 20:33<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: 用户管理页面<br>
 */
public class UserPane extends Pane {

    private Group group = new Group();

    private TableView tableView = new TableView();

    private ObservableList<User> data = null;

    public UserPane() {
        super();
        getChildren().add(group);
        viewDidLoad();
    }

    //初始化方法,开始自定义布局
    private void viewDidLoad()
    {
        //构造菜单栏
        HBox menuHBox = new HBox();
        menuHBox.setLayoutX(0);
        menuHBox.setLayoutY(5);
        menuHBox.setPrefHeight(40);
        menuHBox.prefWidthProperty().bind(this.widthProperty());

        //构造 新增 编辑 删除 三个菜单
        Button createMenu = new Button("新增货主");
        createMenu.setOnAction(event -> {
            System.out.println("新增货主");

            createOrUpdate(null);

        });

        Button editMenu = new Button("编辑货主");
        editMenu.setOnAction(event -> {
            System.out.println("编辑货主");

            User selectedUser = (User) tableView.getSelectionModel().getSelectedItem();

            if (selectedUser == null)
            {
                XLUtil.showAlert("提示","请先选择货主");
                return;
            }

            createOrUpdate(selectedUser.getId());

        });

        Button deleteMenu = new Button("删除货主");
        deleteMenu.setOnAction(event -> {
            System.out.println("删除货主");

            User selectedUser = (User) tableView.getSelectionModel().getSelectedItem();
            if (selectedUser == null)
            {
                XLUtil.showAlert("提示","请先选择货主");
                return;
            }

            boolean confirm = XLUtil.showConfirm("确认", "确定删除该货主吗?");

            if (confirm == true)
            {
                //删除
                boolean execute = UserDAO.deletedUser(selectedUser.getId());

                if (execute == true)
                {
                    data.clear();
                    data.addAll(UserDAO.findUser(1));
                    tableView.refresh();
                    XLUtil.showAlert("提示","删除成功");
                }else{
                    XLUtil.showAlert("提示","删除失败");
                }

            }

        });

        //将按钮添加到菜单栏
        menuHBox.getChildren().addAll(createMenu, editMenu, deleteMenu);

        //构造一个列表
        tableView.prefWidthProperty().bind(this.widthProperty());
        tableView.prefHeightProperty().bind(this.heightProperty().subtract(menuHBox.heightProperty()));
        tableView.setLayoutX(0);
        tableView.setLayoutY(menuHBox.getPrefHeight());
        group.getChildren().addAll(menuHBox, tableView);

        //添加列
        TableColumn idCol = new TableColumn("编号");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn userName = new TableColumn("货主名称");
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));

        data = FXCollections.observableArrayList();

        //查询货主
        data.addAll(UserDAO.findUser(1));

        tableView.getColumns().addAll(idCol, userName);
        tableView.setItems(data);

    }

    //更新或新增
    public void createOrUpdate(Long id)
    {
        Stage stuStage = new Stage();
        stuStage.setTitle("录入货主信息");

        if (id != null)
        {
            stuStage.setTitle("更新货主信息");
        }

        //使用group可以用坐标布局
        Group group = new Group();

        //声明货主对象
        User student = new User();

        //货主姓名
        Label nameLabel = new Label("货主姓名:");
        nameLabel.setLayoutX(Constant.MARGER_LEFT);
        nameLabel.setLayoutY(Constant.V_MARGER);
        nameLabel.setPrefWidth(Constant.LABEL_WIDTH);
        nameLabel.setPrefHeight(Constant.C_HEIGHT);

        //货主姓名文本框
        TextField nameTF = new TextField();
        nameTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        nameTF.setLayoutY(Constant.V_MARGER);
        nameTF.setPrefHeight(Constant.C_HEIGHT);

        //保存按钮
        Button saveBtn = new Button("保存");
        saveBtn.setLayoutX(Constant.MARGER_LEFT);
        saveBtn.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT);
        saveBtn.prefWidthProperty().bind(stuStage.widthProperty().subtract(Constant.MARGER_LEFT * 3));
        saveBtn.setOnAction((event1 -> {
            System.out.println("点击了保存按钮");

            String userName = nameTF.getText();
            boolean execute = false;

            if (id == null)
            {
                execute = UserDAO.register(userName, LoadPropertiesFile.getProperty("initPwd"));
            }else{
                execute = UserDAO.updateUser(id, userName);
            }

            stuStage.hide();
            if (execute == true)
            {
                //查询货主
                data.clear();
                data.addAll(UserDAO.findUser(1));
                tableView.refresh();
                XLUtil.showAlert("提示","保存成功");
            }else{
                XLUtil.showAlert("提示","保存失败");
            }

        }));

        group.getChildren().addAll(nameLabel, nameTF, saveBtn);

        stuStage.setScene(new Scene(group, Constant.SCENE_WIDTH, 200 ));
        stuStage.show();
    }

}
