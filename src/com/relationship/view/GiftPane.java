package com.relationship.view;

import com.browniebytes.javafx.control.DateTimePicker;
import com.relationship.DAO.GiftDAO;
import com.relationship.domain.Gift;
import com.relationship.util.Constant;
import com.relationship.util.XLUtil;
import javafx.beans.property.ObjectProperty;
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
import java.time.LocalDateTime;

/**
 * 文件名称：GiftPane<br>
 * 初始作者：修罗大人<br>
 * 创建日期：2019-06-17 10:27<br>
 * 功能说明：<br>
 */
public class GiftPane extends Pane {

    private Group group = new Group();

    private TableView tableView = new TableView();

    private ObservableList<Gift> data = null;

    public GiftPane() {
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
        Button createMenu = new Button("新增活动");
        createMenu.setOnAction(event -> {
            System.out.println("新增活动");

            createOrUpdate(null);

        });

        Button editMenu = new Button("编辑活动");
        editMenu.setOnAction(event -> {
            System.out.println("编辑活动");

            Gift selectedGift = (Gift) tableView.getSelectionModel().getSelectedItem();

            if (selectedGift == null)
            {
                XLUtil.showAlert("提示","请先选择活动");
                return;
            }

            createOrUpdate(selectedGift.getId());

        });

        Button deleteMenu = new Button("删除活动");
        deleteMenu.setOnAction(event -> {
            System.out.println("删除活动");

            Gift selectedGift = (Gift) tableView.getSelectionModel().getSelectedItem();
            if (selectedGift == null)
            {
                XLUtil.showAlert("提示","请先选择活动");
                return;
            }

            boolean confirm = XLUtil.showConfirm("确认", "确定删除该活动吗?");

            if (confirm == true)
            {
                //删除
                boolean execute = GiftDAO.deleteGift(selectedGift.getId());

                if (execute == true)
                {
                    data.clear();
                    data.addAll(GiftDAO.findGiftList(null));
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

        TableColumn name = new TableColumn("礼物名称");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn recvOrSend = new TableColumn("收送方式");
        recvOrSend.setCellValueFactory(new PropertyValueFactory<>("recvOrSendStr"));

        TableColumn liking = new TableColumn("喜欢程度");
        liking.setCellValueFactory(new PropertyValueFactory<>("likingStr"));

        TableColumn price = new TableColumn("价格");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn cause = new TableColumn("赠送原因");
        cause.setCellValueFactory(new PropertyValueFactory<>("cause"));

        TableColumn time = new TableColumn("时间");
        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn remark = new TableColumn("备注");
        remark.setCellValueFactory(new PropertyValueFactory<>("remark"));

        data = FXCollections.observableArrayList();

        //查询活动
        data.addAll(GiftDAO.findGiftList(null));

        tableView.getColumns().addAll(number, name, recvOrSend, liking, price, cause, time, remark);
        tableView.setItems(data);

    }

    //更新或新增
    public void createOrUpdate(Long id)
    {
        Stage stuStage = new Stage();
        stuStage.setTitle("录入活动信息");

        if (id != null)
        {
            stuStage.setTitle("更新活动信息");
        }

        //使用group可以用坐标布局
        Group group = new Group();

        //声明活动对象
        Gift student = new Gift();

        //礼物名称
        Label nameLabel = new Label("礼物名称:");
        nameLabel.setLayoutX(Constant.MARGER_LEFT);
        nameLabel.setLayoutY(Constant.V_MARGER * 1);
        nameLabel.setPrefWidth(Constant.LABEL_WIDTH);
        nameLabel.setPrefHeight(Constant.C_HEIGHT);

        //礼物名称
        TextField nameTF = new TextField();
        nameTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        nameTF.setLayoutY(Constant.V_MARGER * 1);
        nameTF.setPrefHeight(Constant.C_HEIGHT);

        //收送方式
        Label recvOrSendLabel = new Label("收送方式:");
        recvOrSendLabel.setLayoutX(Constant.MARGER_LEFT);
        recvOrSendLabel.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT * 1);
        recvOrSendLabel.setPrefWidth(Constant.LABEL_WIDTH);
        recvOrSendLabel.setPrefHeight(Constant.C_HEIGHT);

        //收送方式
        ComboBox recvOrSendTF = new ComboBox();
        recvOrSendTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        recvOrSendTF.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT * 1);
        recvOrSendTF.setPrefHeight(Constant.C_HEIGHT);
        recvOrSendTF.getItems().addAll("收到","送出");

        //喜欢程度
        Label likingLabel = new Label("喜欢程度:");
        likingLabel.setLayoutX(Constant.MARGER_LEFT);
        likingLabel.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        likingLabel.setPrefWidth(Constant.LABEL_WIDTH);
        likingLabel.setPrefHeight(Constant.C_HEIGHT);

        //喜欢程度
        ComboBox likingTF = new ComboBox();
        likingTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        likingTF.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        likingTF.setPrefHeight(Constant.C_HEIGHT);
        likingTF.getItems().addAll("一般","喜欢","很喜欢");

        //价格
        Label priceLabel = new Label("价格:");
        priceLabel.setLayoutX(Constant.MARGER_LEFT);
        priceLabel.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        priceLabel.setPrefWidth(Constant.LABEL_WIDTH);
        priceLabel.setPrefHeight(Constant.C_HEIGHT);

        //价格
        TextField priceTF = new TextField();
        priceTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        priceTF.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        priceTF.setPrefHeight(Constant.C_HEIGHT);

        //赠送原因
        Label causeLabel = new Label("赠送原因:");
        causeLabel.setLayoutX(Constant.MARGER_LEFT);
        causeLabel.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        causeLabel.setPrefWidth(Constant.LABEL_WIDTH);
        causeLabel.setPrefHeight(Constant.C_HEIGHT);

        //赠送原因
        TextField causeTF = new TextField();
        causeTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        causeTF.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        causeTF.setPrefHeight(Constant.C_HEIGHT);

        //时间
        Label timeLabel = new Label("时间:");
        timeLabel.setLayoutX(Constant.MARGER_LEFT);
        timeLabel.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
        timeLabel.setPrefWidth(Constant.LABEL_WIDTH);
        timeLabel.setPrefHeight(Constant.C_HEIGHT);

        //时间
        DateTimePicker timeTF = new DateTimePicker();
        timeTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        timeTF.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
        timeTF.setPrefHeight(Constant.C_HEIGHT);

        //备注
        Label remarkLabel = new Label("备注:");
        remarkLabel.setLayoutX(Constant.MARGER_LEFT);
        remarkLabel.setLayoutY(Constant.V_MARGER * 7 + Constant.C_HEIGHT * 6);
        remarkLabel.setPrefWidth(Constant.LABEL_WIDTH);
        remarkLabel.setPrefHeight(Constant.C_HEIGHT);

        //备注
        TextField remarkTF = new TextField();
        remarkTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        remarkTF.setLayoutY(Constant.V_MARGER * 7 + Constant.C_HEIGHT * 6);
        remarkTF.setPrefHeight(Constant.C_HEIGHT);

        //保存按钮
        Button saveBtn = new Button("保存");
        saveBtn.setLayoutX(Constant.MARGER_LEFT);
        saveBtn.setLayoutY(Constant.V_MARGER * 8 + Constant.C_HEIGHT * 7);
        saveBtn.prefWidthProperty().bind(stuStage.widthProperty().subtract(Constant.MARGER_LEFT * 3));
        saveBtn.setOnAction((event1 -> {
            System.out.println("点击了保存按钮");

            String name = nameTF.getText();
            String recvOrSend = (String) recvOrSendTF.getValue();
            Integer recvStr = null;
            if ("收到".equals(recvOrSend))
            {
                recvStr = 0;
            }else{
                recvStr = 1;
            }

            String liking = (String) recvOrSendTF.getValue();
            Integer likingStr = null;
            if ("一般".equals(recvOrSend))
            {
                likingStr = 0;
            }else if ("喜欢".equals(recvOrSend)){
                likingStr = 1;
            }else{
                likingStr = 2;
            }

            BigDecimal price = new BigDecimal(priceTF.getText());
            String cause = causeTF.getText();
            String remark = remarkTF.getText();

            ObjectProperty<LocalDateTime> property1 = timeTF.dateTimeProperty();
            LocalDateTime time = property1.getValue();

            boolean execute = false;

            Gift gift = new Gift();
            gift.setId(id);
            gift.setName(name);
            gift.setRecvOrSend(recvStr);
            gift.setLiking(likingStr);
            gift.setPrice(price);
            gift.setCause(cause);
            gift.setTime(time);
            gift.setRemark(remark);

            if (id == null)
            {
                execute = GiftDAO.createGift(gift);
            }else{
                execute = GiftDAO.updateGift(gift);
            }

            stuStage.hide();
            if (execute == true)
            {
                //查询活动
                data.clear();
                data.addAll(GiftDAO.findGiftList(null));
                tableView.refresh();
                XLUtil.showAlert("提示","保存成功");
            }else{
                XLUtil.showAlert("提示","保存失败");
            }

        }));

        group.getChildren().addAll(nameLabel, nameTF, recvOrSendLabel, recvOrSendTF,
                likingLabel, likingTF, priceLabel, priceTF, causeLabel, causeTF,
                timeLabel, timeTF, remarkLabel, remarkTF, saveBtn);

        stuStage.setScene(new Scene(group, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT * 2));
        stuStage.show();
    }

}
