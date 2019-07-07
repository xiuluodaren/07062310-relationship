package com.relationship.view;

import com.relationship.DAO.FriendDAO;
import com.relationship.DAO.SocialCircleDAO;
import com.relationship.domain.Friend;
import com.relationship.domain.SocialCircle;
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

/**
 * 文件名称：FriendPane<br>
 * 初始作者：修罗大人<br>
 * 创建日期：2019-06-17 10:27<br>
 * 功能说明：<br>
 */
public class FriendPane extends Pane {

    private Group group = new Group();

    private TableView tableView = new TableView();

    private ObservableList<Friend> data = null;

    public FriendPane() {
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
        Button createMenu = new Button("新增联系人");
        createMenu.setOnAction(event -> {
            System.out.println("新增联系人");

            createOrUpdate(null);

        });

        Button editMenu = new Button("编辑联系人");
        editMenu.setOnAction(event -> {
            System.out.println("编辑联系人");

            Friend selectedFriend = (Friend) tableView.getSelectionModel().getSelectedItem();

            if (selectedFriend == null)
            {
                XLUtil.showAlert("提示","请先选择联系人");
                return;
            }

            createOrUpdate(selectedFriend.getId());

        });

        Button deleteMenu = new Button("删除联系人");
        deleteMenu.setOnAction(event -> {
            System.out.println("删除联系人");

            Friend selectedFriend = (Friend) tableView.getSelectionModel().getSelectedItem();
            if (selectedFriend == null)
            {
                XLUtil.showAlert("提示","请先选择联系人");
                return;
            }

            boolean confirm = XLUtil.showConfirm("确认", "确定删除该联系人吗?");

            if (confirm == true)
            {
                //删除
                boolean execute = FriendDAO.deleteFriend(selectedFriend.getId());

                if (execute == true)
                {
                    data.clear();
                    data.addAll(FriendDAO.findFriendList(null));
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
        number.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("姓名");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn principal = new TableColumn("性别");
        principal.setCellValueFactory(new PropertyValueFactory<>("sex"));

        TableColumn mobilePhone = new TableColumn("手机");
        mobilePhone.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));

        TableColumn QQ = new TableColumn("QQ");
        QQ.setCellValueFactory(new PropertyValueFactory<>("QQ"));

        TableColumn wx = new TableColumn("微信");
        wx.setCellValueFactory(new PropertyValueFactory<>("wx"));

        TableColumn socialCircleName = new TableColumn("社交圈");
        socialCircleName.setCellValueFactory(new PropertyValueFactory<>("socialCircleName"));

        data = FXCollections.observableArrayList();

        //查询联系人
        data.addAll(FriendDAO.findFriendList(null));

        tableView.getColumns().addAll(number, name, principal, mobilePhone, QQ, wx, socialCircleName);
        tableView.setItems(data);

    }

    //更新或新增
    public void createOrUpdate(Long id)
    {
        Stage stuStage = new Stage();
        stuStage.setTitle("录入联系人信息");

        if (id != null)
        {
            stuStage.setTitle("更新联系人信息");
        }

        //使用group可以用坐标布局
        Group group = new Group();

        //声明联系人对象
        Friend student = new Friend();

        //联系人姓名
        Label nameLabel = new Label("姓名:");
        nameLabel.setLayoutX(Constant.MARGER_LEFT);
        nameLabel.setLayoutY(Constant.V_MARGER * 1);
        nameLabel.setPrefWidth(Constant.LABEL_WIDTH);
        nameLabel.setPrefHeight(Constant.C_HEIGHT);

        //联系人姓名文本框
        TextField nameTF = new TextField();
        nameTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        nameTF.setLayoutY(Constant.V_MARGER * 1);
        nameTF.setPrefHeight(Constant.C_HEIGHT);

        //性别
        Label sexLabel = new Label("性别:");
        sexLabel.setLayoutX(Constant.MARGER_LEFT);
        sexLabel.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT * 1);
        sexLabel.setPrefWidth(Constant.LABEL_WIDTH);
        sexLabel.setPrefHeight(Constant.C_HEIGHT);

        //性别文本框
        TextField sexTF = new TextField();
        sexTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        sexTF.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT * 1);
        sexTF.setPrefHeight(Constant.C_HEIGHT);

        //手机
        Label mobilePhoneLabel = new Label("手机:");
        mobilePhoneLabel.setLayoutX(Constant.MARGER_LEFT);
        mobilePhoneLabel.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        mobilePhoneLabel.setPrefWidth(Constant.LABEL_WIDTH);
        mobilePhoneLabel.setPrefHeight(Constant.C_HEIGHT);

        //手机文本框
        TextField mobilePhoneTF = new TextField();
        mobilePhoneTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        mobilePhoneTF.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        mobilePhoneTF.setPrefHeight(Constant.C_HEIGHT);

        //QQ
        Label QQLabel = new Label("QQ:");
        QQLabel.setLayoutX(Constant.MARGER_LEFT);
        QQLabel.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        QQLabel.setPrefWidth(Constant.LABEL_WIDTH);
        QQLabel.setPrefHeight(Constant.C_HEIGHT);

        //QQ文本框
        TextField QQTF = new TextField();
        QQTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        QQTF.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        QQTF.setPrefHeight(Constant.C_HEIGHT);

        //微信
        Label wxLabel = new Label("微信:");
        wxLabel.setLayoutX(Constant.MARGER_LEFT);
        wxLabel.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        wxLabel.setPrefWidth(Constant.LABEL_WIDTH);
        wxLabel.setPrefHeight(Constant.C_HEIGHT);

        //微信文本框
        TextField wxTF = new TextField();
        wxTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        wxTF.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        wxTF.setPrefHeight(Constant.C_HEIGHT);

        //社交圈
        Label socialCircleLabel = new Label("社交圈:");
        socialCircleLabel.setLayoutX(Constant.MARGER_LEFT);
        socialCircleLabel.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
        socialCircleLabel.setPrefWidth(Constant.LABEL_WIDTH);
        socialCircleLabel.setPrefHeight(Constant.C_HEIGHT);

        //社交圈
        ComboBox socialCircleBox = new ComboBox();
        socialCircleBox.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        socialCircleBox.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
        socialCircleBox.setPrefHeight(Constant.C_HEIGHT);
        socialCircleBox.prefWidthProperty().bind(wxTF.widthProperty());
        socialCircleBox.setOnAction(event -> {
            SocialCircle socialCircle = (SocialCircle) socialCircleBox.getValue();
        });

        //查询全部社交圈
        socialCircleBox.getItems().addAll(SocialCircleDAO.findSocialCircleList(null));

        //保存按钮
        Button saveBtn = new Button("保存");
        saveBtn.setLayoutX(Constant.MARGER_LEFT);
        saveBtn.setLayoutY(Constant.V_MARGER * 7 + Constant.C_HEIGHT * 6);
        saveBtn.prefWidthProperty().bind(stuStage.widthProperty().subtract(Constant.MARGER_LEFT * 3));
        saveBtn.setOnAction((event1 -> {
            System.out.println("点击了保存按钮");

            String friendName = nameTF.getText();
            String sex = sexTF.getText();
            String mobilePhone = mobilePhoneTF.getText();
            String QQ = QQTF.getText();
            String wx = wxTF.getText();
            SocialCircle socialCircle = (SocialCircle) socialCircleBox.getValue();
            boolean execute = false;

            Friend friend = new Friend();
            friend.setId(id);
            friend.setName(friendName);
            friend.setSex(sex);
            friend.setMobilePhone(mobilePhone);
            friend.setWx(wx);
            friend.setQQ(QQ);
            friend.setSocialCircleId(socialCircle.getId());
            friend.setSocialCircleName(socialCircle.getName());

            if (id == null)
            {
                execute = FriendDAO.createFriend(friend);
            }else{
                execute = FriendDAO.updateFriend(friend);
            }

            stuStage.hide();
            if (execute == true)
            {
                //查询联系人
                data.clear();
                data.addAll(FriendDAO.findFriendList(null));
                tableView.refresh();
                XLUtil.showAlert("提示","保存成功");
            }else{
                XLUtil.showAlert("提示","保存失败");
            }

        }));

        group.getChildren().addAll(nameLabel, nameTF, sexLabel, sexTF,
                mobilePhoneLabel, mobilePhoneTF, QQLabel, QQTF, wxLabel, wxTF, socialCircleLabel, socialCircleBox, saveBtn);

        stuStage.setScene(new Scene(group, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT * 2));
        stuStage.show();
    }

}
