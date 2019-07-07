package com.relationship.view;

import com.relationship.DAO.CommodityDAO;
import com.relationship.DAO.PickDAO;
import com.relationship.domain.Commodity;
import com.relationship.domain.Pick;
import com.relationship.domain.User;
import com.relationship.util.Constant;
import com.relationship.util.LocalData;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件名称：PickPane<br>
 * 初始作者：修罗大人<br>
 * 创建日期：2019-06-17 10:27<br>
 * 功能说明：<br>
 */
public class PickPane extends Pane {

    private Group group = new Group();

    private TableView tableView = new TableView();

    private ObservableList<Pick> data = null;

    public PickPane() {
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

        //构造 新增 菜单
        Button createMenu = new Button("新增提货单");
        createMenu.setOnAction(event -> {
            System.out.println("新增提货单");

            create();

        });

        //将按钮添加到菜单栏
        menuHBox.getChildren().addAll(createMenu);

        //构造一个列表
        tableView.prefWidthProperty().bind(this.widthProperty());
        tableView.prefHeightProperty().bind(this.heightProperty().subtract(menuHBox.heightProperty()));
        tableView.setLayoutX(0);
        tableView.setLayoutY(menuHBox.getPrefHeight());
        group.getChildren().addAll(menuHBox, tableView);

        //添加列
        TableColumn number = new TableColumn("货物编号");
        number.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn commodityName = new TableColumn("货物名称");
        commodityName.setCellValueFactory(new PropertyValueFactory<>("commodityName"));

        TableColumn price = new TableColumn("价格");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn nums = new TableColumn("数量");
        nums.setCellValueFactory(new PropertyValueFactory<>("nums"));

        TableColumn customername = new TableColumn("货主名称");
        customername.setCellValueFactory(new PropertyValueFactory<>("customername"));

        TableColumn createtime = new TableColumn("创建时间");
        createtime.setCellValueFactory(new PropertyValueFactory<>("createtimeStr"));

        TableColumn examineFlag = new TableColumn("审批状态");
        examineFlag.setCellValueFactory(new PropertyValueFactory<>("examineFlagStr"));

        TableColumn examineUser = new TableColumn("审批者");
        examineUser.setCellValueFactory(new PropertyValueFactory<>("examineUser"));

        TableColumn examineTime = new TableColumn("审批时间");
        examineTime.setCellValueFactory(new PropertyValueFactory<>("examineTimeStr"));


        data = FXCollections.observableArrayList();

        //查询库存
        User user = LocalData.getCurrentUser();
        data.addAll(PickDAO.findPickList(user.getId(),null));

        tableView.getColumns().addAll(number, commodityName, price, nums, customername, createtime, examineFlag, examineUser, examineTime);
        tableView.setItems(data);

    }

    //新增
    public void create()
    {
        User user = LocalData.getCurrentUser();

        Stage stage = new Stage();
        stage.setTitle("录入提货单信息");

        //使用group可以用坐标布局
        Group group = new Group();

        //货物编号
        Label numberLabel = new Label("货物编号:");
        numberLabel.setLayoutX(Constant.MARGER_LEFT);
        numberLabel.setLayoutY(Constant.C_HEIGHT);
        numberLabel.setPrefWidth(Constant.LABEL_WIDTH);
        numberLabel.setPrefHeight(Constant.C_HEIGHT);

        //货物编号文本框
        TextField numberTF = new TextField();
        numberTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        numberTF.setLayoutY( Constant.C_HEIGHT);
        numberTF.setPrefHeight(Constant.C_HEIGHT);
        numberTF.setDisable(true);

        //货物名称
        Label commodityLabel = new Label("货物名称:");
        commodityLabel.setLayoutX(Constant.MARGER_LEFT);
        commodityLabel.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT);
        commodityLabel.setPrefWidth(Constant.LABEL_WIDTH);
        commodityLabel.setPrefHeight(Constant.C_HEIGHT);

        //货物名称
        ComboBox commodityComboBox = new ComboBox();
        commodityComboBox.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        commodityComboBox.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT);
        commodityComboBox.setPrefHeight(Constant.C_HEIGHT);
        commodityComboBox.prefWidthProperty().bind(numberTF.widthProperty());
        commodityComboBox.setOnAction(event -> {
            Commodity commodity = (Commodity) commodityComboBox.getValue();
            numberTF.setText(commodity.getNumber());
        });

        //查询全部货物
        List<Commodity> commodityList = CommodityDAO.findCommodityList(null, user.getId());
        commodityComboBox.getItems().addAll(commodityList);
        if (commodityList != null && commodityList.size() > 0)
        {
            commodityComboBox.setValue(commodityList.get(0));
            numberTF.setText(commodityList.get(0).getNumber());
        }

        //数量
        Label numsLabel = new Label("数量:");
        numsLabel.setLayoutX(Constant.MARGER_LEFT);
        numsLabel.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        numsLabel.setPrefWidth(Constant.LABEL_WIDTH);
        numsLabel.setPrefHeight(Constant.C_HEIGHT);

        //数量
        TextField numsTF = new TextField();
        numsTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        numsTF.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        numsTF.setPrefHeight(Constant.C_HEIGHT);

        //价格
        Label priceLabel = new Label("价格:");
        priceLabel.setLayoutX(Constant.MARGER_LEFT);
        priceLabel.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        priceLabel.setPrefWidth(Constant.LABEL_WIDTH);
        priceLabel.setPrefHeight(Constant.C_HEIGHT);

        //价格文本框
        TextField priceTF = new TextField();
        priceTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        priceTF.setLayoutY(Constant.V_MARGER * 4 + Constant.C_HEIGHT * 3);
        priceTF.setPrefHeight(Constant.C_HEIGHT);


        //保存按钮
        Button saveBtn = new Button("保存");
        saveBtn.setLayoutX(Constant.MARGER_LEFT);
        saveBtn.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        saveBtn.prefWidthProperty().bind(stage.widthProperty().subtract(Constant.MARGER_LEFT * 3));
        saveBtn.setOnAction((event1 -> {
            System.out.println("点击了保存按钮");

            Commodity commodity = (Commodity) commodityComboBox.getValue();
            BigDecimal nums = new BigDecimal(numsTF.getText());
            BigDecimal price = new BigDecimal(priceTF.getText());

            boolean execute = false;

            Pick pick = new Pick(null,commodity.getNumber(),nums,price,commodity.getId(),commodity.getName()
            , LocalDateTime.now(),user.getId(),user.getUserName(),null,null,null);

            execute = PickDAO.createPick(pick);

            stage.hide();
            if (execute == true)
            {
                //查询提货单
                data.clear();
                data.addAll(PickDAO.findPickList(user.getId(),null));
                tableView.refresh();
                XLUtil.showAlert("提示","保存成功");
            }else{
                XLUtil.showAlert("提示","保存失败");
            }

        }));

        group.getChildren().addAll(commodityLabel, commodityComboBox, numberLabel, numberTF,numsLabel, numsTF,
                priceLabel, priceTF, saveBtn);

        stage.setScene(new Scene(group, Constant.SCENE_WIDTH, 250));
        stage.show();
    }

}
