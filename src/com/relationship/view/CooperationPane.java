package com.relationship.view;

import com.browniebytes.javafx.control.DateTimePicker;
import com.relationship.DAO.CooperationDAO;
import com.relationship.domain.Cooperation;
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

import java.time.LocalDateTime;

/**
 * 文件名称：CooperationPane<br>
 * 初始作者：修罗大人<br>
 * 创建日期：2019-06-17 10:27<br>
 * 功能说明：<br>
 */
public class CooperationPane extends Pane {

    private Group group = new Group();

    private TableView tableView = new TableView();

    private ObservableList<Cooperation> data = null;

    public CooperationPane() {
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

            Cooperation selectedCooperation = (Cooperation) tableView.getSelectionModel().getSelectedItem();

            if (selectedCooperation == null)
            {
                XLUtil.showAlert("提示","请先选择活动");
                return;
            }

            createOrUpdate(selectedCooperation.getId());

        });

        Button deleteMenu = new Button("删除活动");
        deleteMenu.setOnAction(event -> {
            System.out.println("删除活动");

            Cooperation selectedCooperation = (Cooperation) tableView.getSelectionModel().getSelectedItem();
            if (selectedCooperation == null)
            {
                XLUtil.showAlert("提示","请先选择活动");
                return;
            }

            boolean confirm = XLUtil.showConfirm("确认", "确定删除该活动吗?");

            if (confirm == true)
            {
                //删除
                boolean execute = CooperationDAO.deleteCooperation(selectedCooperation.getId());

                if (execute == true)
                {
                    data.clear();
                    data.addAll(CooperationDAO.findCooperationList(null));
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

        TableColumn cooperationerName = new TableColumn("合作人");
        cooperationerName.setCellValueFactory(new PropertyValueFactory<>("cooperationerName"));

        TableColumn status = new TableColumn("合作状态");
        status.setCellValueFactory(new PropertyValueFactory<>("statusStr"));

        data = FXCollections.observableArrayList();

        //查询活动
        data.addAll(CooperationDAO.findCooperationList(null));

        tableView.getColumns().addAll(number, title, detail, startTime, endTime, cooperationerName, status);
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
        Cooperation student = new Cooperation();

        //标题
        Label titleLabel = new Label("标题:");
        titleLabel.setLayoutX(Constant.MARGER_LEFT);
        titleLabel.setLayoutY(Constant.V_MARGER * 1);
        titleLabel.setPrefWidth(Constant.LABEL_WIDTH);
        titleLabel.setPrefHeight(Constant.C_HEIGHT);

        //标题文本框
        TextField titleTF = new TextField();
        titleTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        titleTF.setLayoutY(Constant.V_MARGER * 1);
        titleTF.setPrefHeight(Constant.C_HEIGHT);

        //详情
        Label detailLabel = new Label("详情:");
        detailLabel.setLayoutX(Constant.MARGER_LEFT);
        detailLabel.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT * 1);
        detailLabel.setPrefWidth(Constant.LABEL_WIDTH);
        detailLabel.setPrefHeight(Constant.C_HEIGHT);

        //详情
        TextField detailTF = new TextField();
        detailTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        detailTF.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT * 1);
        detailTF.setPrefHeight(Constant.C_HEIGHT);

        //开始时间
        Label startTimeLabel = new Label("开始时间:");
        startTimeLabel.setLayoutX(Constant.MARGER_LEFT);
        startTimeLabel.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        startTimeLabel.setPrefWidth(Constant.LABEL_WIDTH);
        startTimeLabel.setPrefHeight(Constant.C_HEIGHT);

        //开始时间文本框
        DateTimePicker startTimeTF = new DateTimePicker();
        startTimeTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        startTimeTF.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        startTimeTF.setPrefHeight(Constant.C_HEIGHT);

        //结束时间
        Label endTimeLabel = new Label("结束时间:");
        endTimeLabel.setLayoutX(Constant.MARGER_LEFT);
        endTimeLabel.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        endTimeLabel.setPrefWidth(Constant.LABEL_WIDTH);
        endTimeLabel.setPrefHeight(Constant.C_HEIGHT);

        //结束时间
        DateTimePicker endTimeTF = new DateTimePicker();
        endTimeTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        endTimeTF.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        endTimeTF.setPrefHeight(Constant.C_HEIGHT);

        //合作人
        Label cooperationLabel = new Label("合作人:");
        cooperationLabel.setLayoutX(Constant.MARGER_LEFT);
        cooperationLabel.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        cooperationLabel.setPrefWidth(Constant.LABEL_WIDTH);
        cooperationLabel.setPrefHeight(Constant.C_HEIGHT);

        //合作人
        TextField cooperationTF = new TextField();
        cooperationTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        cooperationTF.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        cooperationTF.setPrefHeight(Constant.C_HEIGHT);

        //合作状态  0进行中 1已结束
        Label statusLabel = new Label("合作状态:");
        statusLabel.setLayoutX(Constant.MARGER_LEFT);
        statusLabel.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
        statusLabel.setPrefWidth(Constant.LABEL_WIDTH);
        statusLabel.setPrefHeight(Constant.C_HEIGHT);

//        //合作状态  0进行中 1已结束
//        TextField statusTF = new TextField();
//        statusTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
//        statusTF.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
//        statusTF.setPrefHeight(Constant.C_HEIGHT);

        //合作状态  0进行中 1已结束
        ComboBox statusTF = new ComboBox();
        statusTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        statusTF.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
        statusTF.setPrefHeight(Constant.C_HEIGHT);
        statusTF.getItems().addAll("进行中","已结束");

        //保存按钮
        Button saveBtn = new Button("保存");
        saveBtn.setLayoutX(Constant.MARGER_LEFT);
        saveBtn.setLayoutY(Constant.V_MARGER * 7 + Constant.C_HEIGHT * 6);
        saveBtn.prefWidthProperty().bind(stuStage.widthProperty().subtract(Constant.MARGER_LEFT * 3));
        saveBtn.setOnAction((event1 -> {
            System.out.println("点击了保存按钮");

            String title = titleTF.getText();
            String detail = detailTF.getText();
            ObjectProperty<LocalDateTime> property1 = startTimeTF.dateTimeProperty();
            LocalDateTime startTime = property1.getValue();
            ObjectProperty<LocalDateTime> property2 = endTimeTF.dateTimeProperty();
            LocalDateTime endTime = property2.getValue();
            String cooperationerName = cooperationTF.getText();

//            String status = statusTF.getText();
            Object value = statusTF.getValue();
            String status = null;
            if ("进行中".equals(value))
            {
                status = "0";
            }else{
                status = "1";
            }

            boolean execute = false;

            Cooperation cooperation = new Cooperation();
            cooperation.setId(id);
            cooperation.setTitle(title);
            cooperation.setDetail(detail);
            cooperation.setStartTime(startTime);
            cooperation.setEndTime(endTime);
//            cooperation.setCooperationerId();
            cooperation.setCooperationerName(cooperationerName);
            cooperation.setStatus(status);

            if (id == null)
            {
                execute = CooperationDAO.createCooperation(cooperation);
            }else{
                execute = CooperationDAO.updateCooperation(cooperation);
            }

            stuStage.hide();
            if (execute == true)
            {
                //查询活动
                data.clear();
                data.addAll(CooperationDAO.findCooperationList(null));
                tableView.refresh();
                XLUtil.showAlert("提示","保存成功");
            }else{
                XLUtil.showAlert("提示","保存失败");
            }

        }));

        group.getChildren().addAll(titleLabel, titleTF, detailLabel, detailTF,
                startTimeLabel, startTimeTF, endTimeLabel, endTimeTF, cooperationLabel, cooperationTF,
                statusLabel, statusTF, saveBtn);

        stuStage.setScene(new Scene(group, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT * 2));
        stuStage.show();
    }

}
