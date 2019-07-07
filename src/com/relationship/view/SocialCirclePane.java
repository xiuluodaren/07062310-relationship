package com.relationship.view;

import com.relationship.DAO.SocialCircleDAO;
import com.relationship.DAO.UserDAO;
import com.relationship.domain.SocialCircle;
import com.relationship.domain.User;
import com.relationship.util.Constant;
import com.relationship.util.XLUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;

/**
 * 文件名称：SocialCirclePane<br>
 * 初始作者：修罗大人<br>
 * 创建日期：2019-06-17 10:27<br>
 * 功能说明：<br>
 */
public class SocialCirclePane extends Pane {

    private Group group = new Group();

    private TableView tableView = new TableView();

    private ObservableList<SocialCircle> data = null;

    public SocialCirclePane() {
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
        Button createMenu = new Button("新增社交圈");
        createMenu.setOnAction(event -> {
            System.out.println("新增社交圈");

            createOrUpdate(null);

        });

        Button editMenu = new Button("编辑社交圈");
        editMenu.setOnAction(event -> {
            System.out.println("编辑社交圈");

            SocialCircle selectedSocialCircle = (SocialCircle) tableView.getSelectionModel().getSelectedItem();

            if (selectedSocialCircle == null)
            {
                XLUtil.showAlert("提示","请先选择社交圈");
                return;
            }

            createOrUpdate(selectedSocialCircle.getId());

        });

        Button deleteMenu = new Button("删除社交圈");
        deleteMenu.setOnAction(event -> {
            System.out.println("删除社交圈");

            SocialCircle selectedSocialCircle = (SocialCircle) tableView.getSelectionModel().getSelectedItem();
            if (selectedSocialCircle == null)
            {
                XLUtil.showAlert("提示","请先选择社交圈");
                return;
            }

            boolean confirm = XLUtil.showConfirm("确认", "确定删除该社交圈吗?");

            if (confirm == true)
            {
                //删除
                boolean execute = SocialCircleDAO.deleteSocialCircle(selectedSocialCircle.getId());

                if (execute == true)
                {
                    data.clear();
                    data.addAll(SocialCircleDAO.findSocialCircleList(null));
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
        TableColumn number = new TableColumn("编号");
        number.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn name = new TableColumn("社交圈名称");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn principal = new TableColumn("负责人");
        principal.setCellValueFactory(new PropertyValueFactory<>("principal"));

        TableColumn mobilePhone = new TableColumn("手机");
        mobilePhone.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));

        TableColumn QQ = new TableColumn("QQ");
        QQ.setCellValueFactory(new PropertyValueFactory<>("QQ"));

        TableColumn wx = new TableColumn("微信");
        wx.setCellValueFactory(new PropertyValueFactory<>("wx"));

        data = FXCollections.observableArrayList();

        //查询社交圈
        data.addAll(SocialCircleDAO.findSocialCircleList(null));

        tableView.getColumns().addAll(number, name, principal, mobilePhone, QQ, wx);
        tableView.setItems(data);

    }

    //更新或新增
    public void createOrUpdate(Long id)
    {
        Stage stuStage = new Stage();
        stuStage.setTitle("录入社交圈信息");

        if (id != null)
        {
            stuStage.setTitle("更新社交圈信息");
        }

        //使用group可以用坐标布局
        Group group = new Group();

        //声明社交圈对象
        SocialCircle student = new SocialCircle();

        //社交圈编号
        Label numberLabel = new Label("社交圈编号:");
        numberLabel.setLayoutX(Constant.MARGER_LEFT);
        numberLabel.setLayoutY(Constant.V_MARGER);
        numberLabel.setPrefWidth(Constant.LABEL_WIDTH);
        numberLabel.setPrefHeight(Constant.C_HEIGHT);

        //社交圈编号文本框
        TextField numberTF = new TextField();
        numberTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        numberTF.setLayoutY(Constant.V_MARGER);
        numberTF.setPrefHeight(Constant.C_HEIGHT);

        //社交圈姓名
        Label nameLabel = new Label("社交圈名称:");
        nameLabel.setLayoutX(Constant.MARGER_LEFT);
        nameLabel.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT);
        nameLabel.setPrefWidth(Constant.LABEL_WIDTH);
        nameLabel.setPrefHeight(Constant.C_HEIGHT);

        //社交圈姓名文本框
        TextField nameTF = new TextField();
        nameTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        nameTF.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT);
        nameTF.setPrefHeight(Constant.C_HEIGHT);

        //负责人
        Label principalLabel = new Label("负责人:");
        principalLabel.setLayoutX(Constant.MARGER_LEFT);
        principalLabel.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        principalLabel.setPrefWidth(Constant.LABEL_WIDTH);
        principalLabel.setPrefHeight(Constant.C_HEIGHT);

        //负责人文本框
        TextField principalTF = new TextField();
        principalTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        principalTF.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        principalTF.setPrefHeight(Constant.C_HEIGHT);

        //手机
        Label mobilePhoneLabel = new Label("手机:");
        mobilePhoneLabel.setLayoutX(Constant.MARGER_LEFT);
        mobilePhoneLabel.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        mobilePhoneLabel.setPrefWidth(Constant.LABEL_WIDTH);
        mobilePhoneLabel.setPrefHeight(Constant.C_HEIGHT);

        //手机文本框
        TextField mobilePhoneTF = new TextField();
        mobilePhoneTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        mobilePhoneTF.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        mobilePhoneTF.setPrefHeight(Constant.C_HEIGHT);

        //QQ
        Label QQLabel = new Label("QQ:");
        QQLabel.setLayoutX(Constant.MARGER_LEFT);
        QQLabel.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        QQLabel.setPrefWidth(Constant.LABEL_WIDTH);
        QQLabel.setPrefHeight(Constant.C_HEIGHT);

        //QQ文本框
        TextField QQTF = new TextField();
        QQTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        QQTF.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        QQTF.setPrefHeight(Constant.C_HEIGHT);

        //微信
        Label wxLabel = new Label("微信:");
        wxLabel.setLayoutX(Constant.MARGER_LEFT);
        wxLabel.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
        wxLabel.setPrefWidth(Constant.LABEL_WIDTH);
        wxLabel.setPrefHeight(Constant.C_HEIGHT);

        //微信文本框
        TextField wxTF = new TextField();
        wxTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        wxTF.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
        wxTF.setPrefHeight(Constant.C_HEIGHT);


        //保存按钮
        Button saveBtn = new Button("保存");
        saveBtn.setLayoutX(Constant.MARGER_LEFT);
        saveBtn.setLayoutY(Constant.V_MARGER * 7 + Constant.C_HEIGHT * 6);
        saveBtn.prefWidthProperty().bind(stuStage.widthProperty().subtract(Constant.MARGER_LEFT * 3));
        saveBtn.setOnAction((event1 -> {
            System.out.println("点击了保存按钮");

            String number = numberTF.getText();
            String socialCircleName = nameTF.getText();
            String principal = principalTF.getText();
            String mobilePhone = mobilePhoneTF.getText();
            String wx = wxTF.getText();
            String QQ = QQTF.getText();
            boolean execute = false;

            SocialCircle socialCircle = new SocialCircle();
            socialCircle.setId(id);
            socialCircle.setNumber(number);
            socialCircle.setName(socialCircleName);
            socialCircle.setPrincipal(principal);
            socialCircle.setMobilePhone(mobilePhone);
            socialCircle.setWx(wx);
            socialCircle.setQQ(QQ);

            if (id == null)
            {
                execute = SocialCircleDAO.createSocialCircle(socialCircle);
            }else{
                execute = SocialCircleDAO.updateSocialCircle(socialCircle);
            }

            stuStage.hide();
            if (execute == true)
            {
                //查询社交圈
                data.clear();
                data.addAll(SocialCircleDAO.findSocialCircleList(null));
                tableView.refresh();
                XLUtil.showAlert("提示","保存成功");
            }else{
                XLUtil.showAlert("提示","保存失败");
            }

        }));

        group.getChildren().addAll(numberLabel, numberTF, nameLabel, nameTF,principalLabel, principalTF,
                mobilePhoneLabel, mobilePhoneTF, QQLabel, QQTF, wxLabel, wxTF, saveBtn);

        stuStage.setScene(new Scene(group, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT * 2));
        stuStage.show();
    }

}
