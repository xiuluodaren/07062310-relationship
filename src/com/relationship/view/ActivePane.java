package com.relationship.view;

import com.browniebytes.javafx.control.DateTimePicker;
import com.relationship.DAO.ActiveDAO;
import com.relationship.DAO.SocialCircleDAO;
import com.relationship.domain.Active;
import com.relationship.domain.SocialCircle;
import com.relationship.util.Constant;
import com.relationship.util.DateUtil;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 文件名称：ActivePane<br>
 * 初始作者：修罗大人<br>
 * 创建日期：2019-06-17 10:27<br>
 * 功能说明：<br>
 */
public class ActivePane extends Pane {

    private Group group = new Group();

    private TableView tableView = new TableView();

    private ObservableList<Active> data = null;

    public ActivePane() {
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

            Active selectedActive = (Active) tableView.getSelectionModel().getSelectedItem();

            if (selectedActive == null)
            {
                XLUtil.showAlert("提示","请先选择活动");
                return;
            }

            createOrUpdate(selectedActive.getId());

        });

        Button deleteMenu = new Button("删除活动");
        deleteMenu.setOnAction(event -> {
            System.out.println("删除活动");

            Active selectedActive = (Active) tableView.getSelectionModel().getSelectedItem();
            if (selectedActive == null)
            {
                XLUtil.showAlert("提示","请先选择活动");
                return;
            }

            boolean confirm = XLUtil.showConfirm("确认", "确定删除该活动吗?");

            if (confirm == true)
            {
                //删除
                boolean execute = ActiveDAO.deleteActive(selectedActive.getId());

                if (execute == true)
                {
                    data.clear();
                    data.addAll(ActiveDAO.findActiveList(null));
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

        TableColumn title = new TableColumn("标题");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn detail = new TableColumn("详情");
        detail.setCellValueFactory(new PropertyValueFactory<>("detail"));

        TableColumn startTime = new TableColumn("开始时间");
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTimeStr"));

        TableColumn endTime = new TableColumn("结束时间");
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTimeStr"));

        TableColumn initiator = new TableColumn("发起方");
        initiator.setCellValueFactory(new PropertyValueFactory<>("initiator"));

        TableColumn address = new TableColumn("活动地址");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn summary = new TableColumn("活动总结");
        summary.setCellValueFactory(new PropertyValueFactory<>("summary"));

        TableColumn createTime = new TableColumn("创建时间");
        createTime.setCellValueFactory(new PropertyValueFactory<>("createTimeStr"));

        data = FXCollections.observableArrayList();

        //查询活动
        data.addAll(ActiveDAO.findActiveList(null));

        tableView.getColumns().addAll(number, title, detail, startTime, endTime, initiator, address, summary, createTime);
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
        Active student = new Active();

        //活动名称
        Label nameLabel = new Label("活动名称:");
        nameLabel.setLayoutX(Constant.MARGER_LEFT);
        nameLabel.setLayoutY(Constant.V_MARGER * 1);
        nameLabel.setPrefWidth(Constant.LABEL_WIDTH);
        nameLabel.setPrefHeight(Constant.C_HEIGHT);

        //活动名称文本框
        TextField nameTF = new TextField();
        nameTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        nameTF.setLayoutY(Constant.V_MARGER * 1);
        nameTF.setPrefHeight(Constant.C_HEIGHT);

        //标题
        Label titleLabel = new Label("标题:");
        titleLabel.setLayoutX(Constant.MARGER_LEFT);
        titleLabel.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT * 1);
        titleLabel.setPrefWidth(Constant.LABEL_WIDTH);
        titleLabel.setPrefHeight(Constant.C_HEIGHT);

        //标题文本框
        TextField titleTF = new TextField();
        titleTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        titleTF.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT * 1);
        titleTF.setPrefHeight(Constant.C_HEIGHT);

        //详情
        Label detailLabel = new Label("详情:");
        detailLabel.setLayoutX(Constant.MARGER_LEFT);
        detailLabel.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        detailLabel.setPrefWidth(Constant.LABEL_WIDTH);
        detailLabel.setPrefHeight(Constant.C_HEIGHT);

        //详情文本框
        TextArea detailTF = new TextArea();
        detailTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        detailTF.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        detailTF.setPrefHeight(Constant.C_HEIGHT * 5);
        detailTF.prefWidthProperty().bind(stuStage.widthProperty().subtract(detailTF.getLayoutX())
                .subtract(Constant.H_MARGER * 3));

        //开始时间
        Label startTimeLabel = new Label("开始时间:");
        startTimeLabel.setLayoutX(Constant.MARGER_LEFT);
        startTimeLabel.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 7);
        startTimeLabel.setPrefWidth(Constant.LABEL_WIDTH);
        startTimeLabel.setPrefHeight(Constant.C_HEIGHT);

        //开始时间文本框
        DateTimePicker startTimeTF = new DateTimePicker();
        startTimeTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        startTimeTF.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 7);
        startTimeTF.setPrefHeight(Constant.C_HEIGHT);

        //结束时间
        Label endTimeLabel = new Label("结束时间:");
        endTimeLabel.setLayoutX(Constant.MARGER_LEFT);
        endTimeLabel.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 8);
        endTimeLabel.setPrefWidth(Constant.LABEL_WIDTH);
        endTimeLabel.setPrefHeight(Constant.C_HEIGHT);

        //结束时间
        DateTimePicker endTimeTF = new DateTimePicker();
        endTimeTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        endTimeTF.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 8);
        endTimeTF.setPrefHeight(Constant.C_HEIGHT);

        //发起方
        Label initiatorLabel = new Label("发起方:");
        initiatorLabel.setLayoutX(Constant.MARGER_LEFT);
        initiatorLabel.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 9);
        initiatorLabel.setPrefWidth(Constant.LABEL_WIDTH);
        initiatorLabel.setPrefHeight(Constant.C_HEIGHT);

        //发起方
        TextField initiatorTF = new TextField();
        initiatorTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        initiatorTF.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 9);
        initiatorTF.setPrefHeight(Constant.C_HEIGHT);

        //活动地址
        Label addressLabel = new Label("活动地址:");
        addressLabel.setLayoutX(Constant.MARGER_LEFT);
        addressLabel.setLayoutY(Constant.V_MARGER * 7 + Constant.C_HEIGHT * 10);
        addressLabel.setPrefWidth(Constant.LABEL_WIDTH);
        addressLabel.setPrefHeight(Constant.C_HEIGHT);

        //活动地址
        TextField addressTF = new TextField();
        addressTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        addressTF.setLayoutY(Constant.V_MARGER * 7 + Constant.C_HEIGHT * 10);
        addressTF.setPrefHeight(Constant.C_HEIGHT);

        //活动总结
        Label summaryLabel = new Label("活动总结:");
        summaryLabel.setLayoutX(Constant.MARGER_LEFT);
        summaryLabel.setLayoutY(Constant.V_MARGER * 8 + Constant.C_HEIGHT * 11);
        summaryLabel.setPrefHeight(Constant.C_HEIGHT);

        //活动总结
        TextArea summaryTF = new TextArea();
        summaryTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        summaryTF.setLayoutY(Constant.V_MARGER * 8 + Constant.C_HEIGHT * 11);
        summaryTF.setPrefHeight(Constant.C_HEIGHT * 5);
//        summaryTF.setPrefWidth(Constant.LABEL_WIDTH);
        summaryTF.prefWidthProperty().bind(stuStage.widthProperty().subtract(summaryTF.getLayoutX())
            .subtract(Constant.H_MARGER * 3));

//        //创建时间
//        Label createTimeLabel = new Label("创建时间:");
//        createTimeLabel.setLayoutX(Constant.MARGER_LEFT);
//        createTimeLabel.setLayoutY(Constant.V_MARGER * 9 + Constant.C_HEIGHT * 16);
//        createTimeLabel.setPrefWidth(Constant.LABEL_WIDTH);
//        createTimeLabel.setPrefHeight(Constant.C_HEIGHT);
//
//        //创建时间
//        DateTimePicker createTimeTF = new DateTimePicker();
//        createTimeTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
//        createTimeTF.setLayoutY(Constant.V_MARGER * 9 + Constant.C_HEIGHT * 16);
//        createTimeTF.setPrefHeight(Constant.C_HEIGHT);

        //保存按钮
        Button saveBtn = new Button("保存");
        saveBtn.setLayoutX(Constant.MARGER_LEFT);
        saveBtn.setLayoutY(Constant.V_MARGER * 10 + Constant.C_HEIGHT * 17);
        saveBtn.prefWidthProperty().bind(stuStage.widthProperty().subtract(Constant.MARGER_LEFT * 3));
        saveBtn.setOnAction((event1 -> {
            System.out.println("点击了保存按钮");

            String activeName = nameTF.getText();
            String title = titleTF.getText();
            String detail = detailTF.getText();
            ObjectProperty<LocalDateTime> property1 = startTimeTF.dateTimeProperty();
            LocalDateTime startTime = property1.getValue();
            ObjectProperty<LocalDateTime> property2 = endTimeTF.dateTimeProperty();
            LocalDateTime endTime = property2.getValue();
            String initiator = initiatorTF.getText();
            String address = addressTF.getText();
            String summary = summaryTF.getText();

            boolean execute = false;

            Active active = new Active();
            active.setId(id);
            active.setTitle(title);
            active.setDetail(detail);
            active.setStartTime(startTime);
            active.setEndTime(endTime);
            active.setInitiator(initiator);
            active.setAddress(address);
            active.setSummary(summary);
            active.setCreateTime(LocalDateTime.now());

            if (id == null)
            {
                execute = ActiveDAO.createActive(active);
            }else{
                execute = ActiveDAO.updateActive(active);
            }

            stuStage.hide();
            if (execute == true)
            {
                //查询活动
                data.clear();
                data.addAll(ActiveDAO.findActiveList(null));
                tableView.refresh();
                XLUtil.showAlert("提示","保存成功");
            }else{
                XLUtil.showAlert("提示","保存失败");
            }

        }));

        group.getChildren().addAll(nameLabel, nameTF, titleLabel, titleTF,
                detailLabel, detailTF, startTimeLabel, startTimeTF, endTimeLabel, endTimeTF, initiatorLabel, initiatorTF,
                addressLabel, addressTF, summaryLabel, summaryTF, saveBtn);

        stuStage.setScene(new Scene(group, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT * 3));
        stuStage.show();
    }

}
