package com.wms.view;

import com.wms.DAO.CommodityDAO;
import com.wms.DAO.StorageLogDAO;
import com.wms.DAO.StorageLogDAO;
import com.wms.DAO.UserDAO;
import com.wms.domain.Commodity;
import com.wms.domain.StorageLog;
import com.wms.domain.StorageLog;
import com.wms.domain.User;
import com.wms.util.Constant;
import com.wms.util.XLUtil;
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
 * 文件名称：StorageLogPane<br>
 * 初始作者：修罗大人<br>
 * 创建日期：2019-06-17 10:27<br>
 * 功能说明：<br>
 */
public class StorageOutPane extends Pane {

    private Group group = new Group();

    private TableView tableView = new TableView();

    private ObservableList<StorageLog> data = null;

    public StorageOutPane() {
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
        Button createMenu = new Button("新增出库");
        createMenu.setOnAction(event -> {
            System.out.println("新增出库");

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

        TableColumn beforestorage = new TableColumn("变动前库存");
        beforestorage.setCellValueFactory(new PropertyValueFactory<>("beforestorage"));

        TableColumn changestoreage = new TableColumn("库存变动");
        changestoreage.setCellValueFactory(new PropertyValueFactory<>("changestoreage"));

        TableColumn currentstorage = new TableColumn("当前库存");
        currentstorage.setCellValueFactory(new PropertyValueFactory<>("currentstorage"));

        TableColumn price = new TableColumn("价格");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn customername = new TableColumn("货主名称");
        customername.setCellValueFactory(new PropertyValueFactory<>("customername"));

        TableColumn warehouse = new TableColumn("仓库");
        warehouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));

        TableColumn createtime = new TableColumn("创建时间");
        createtime.setCellValueFactory(new PropertyValueFactory<>("createtimeStr"));


        data = FXCollections.observableArrayList();

        //查询库存
        data.addAll(StorageLogDAO.findStorageLogList(null, "1", null, null));

        tableView.getColumns().addAll(number, commodityName, beforestorage, changestoreage, currentstorage, price, customername, warehouse, createtime);
        tableView.setItems(data);

    }

    //更新或新增
    public void create()
    {
        Stage stuStage = new Stage();
        stuStage.setTitle("录入出库信息");

        //使用group可以用坐标布局
        Group group = new Group();

        //货主文本框
        TextField customerTF = new TextField();

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
            customerTF.setText(commodity.getCustomername());
        });

        //查询全部货物
        commodityComboBox.getItems().addAll(CommodityDAO.findCommodityList(null,null));

        //出库库存
        Label storageLabel = new Label("出库库存:");
        storageLabel.setLayoutX(Constant.MARGER_LEFT);
        storageLabel.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        storageLabel.setPrefWidth(Constant.LABEL_WIDTH);
        storageLabel.setPrefHeight(Constant.C_HEIGHT);

        //库存文本框
        TextField storageTF = new TextField();
        storageTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        storageTF.setLayoutY(Constant.V_MARGER * 3 + Constant.C_HEIGHT * 2);
        storageTF.setPrefHeight(Constant.C_HEIGHT);

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

        //货主
        Label customerLabel = new Label("货主:");
        customerLabel.setLayoutX(Constant.MARGER_LEFT);
        customerLabel.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        customerLabel.setPrefWidth(Constant.LABEL_WIDTH);
        customerLabel.setPrefHeight(Constant.C_HEIGHT);

        //货主文本框
        customerTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        customerTF.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        customerTF.setPrefHeight(Constant.C_HEIGHT);
        customerTF.prefWidthProperty().bind(priceTF.widthProperty());
        customerTF.setDisable(true);

        //仓库
        Label warehouseLabel = new Label("仓库:");
        warehouseLabel.setLayoutX(Constant.MARGER_LEFT);
        warehouseLabel.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
        warehouseLabel.setPrefWidth(Constant.LABEL_WIDTH);
        warehouseLabel.setPrefHeight(Constant.C_HEIGHT);

        //仓库文本框
        TextField warehouseTF = new TextField();
        warehouseTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        warehouseTF.setLayoutY(Constant.V_MARGER * 6 + Constant.C_HEIGHT * 5);
        warehouseTF.setPrefHeight(Constant.C_HEIGHT);


        //保存按钮
        Button saveBtn = new Button("保存");
        saveBtn.setLayoutX(Constant.MARGER_LEFT);
        saveBtn.setLayoutY(Constant.V_MARGER * 7 + Constant.C_HEIGHT * 6);
        saveBtn.prefWidthProperty().bind(stuStage.widthProperty().subtract(Constant.MARGER_LEFT * 3));
        saveBtn.setOnAction((event1 -> {
            System.out.println("点击了保存按钮");

            Commodity commodity = (Commodity) commodityComboBox.getValue();
            String storage = storageTF.getText();
            String price = priceTF.getText();
            String warehouse = warehouseTF.getText();

            boolean execute = false;

            StorageLog storageLog = new StorageLog();
            storageLog.setNumber(commodity.getNumber());
            storageLog.setName(commodity.getName());
            storageLog.setChangestoreage(new BigDecimal(storage));
            storageLog.setStoragetype(1);
            storageLog.setPrice(new BigDecimal(price));
            storageLog.setWarehouse(warehouse);
            storageLog.setCustomerid(commodity.getCustomerid());
            storageLog.setCustomername(commodity.getCustomername());
            storageLog.setCommodityId(commodity.getId());
            storageLog.setCommodityName(commodity.getName());

            execute = StorageLogDAO.createStorageLog(storageLog);

            stuStage.hide();
            if (execute == true)
            {
                //查询出库
                data.clear();
                data.addAll(StorageLogDAO.findStorageLogList(null, "1", null, null));
                tableView.refresh();
                XLUtil.showAlert("提示","保存成功");
            }else{
                XLUtil.showAlert("提示","保存失败");
            }

        }));

        group.getChildren().addAll(commodityLabel, commodityComboBox, numberLabel, numberTF,storageLabel, storageTF,
                priceLabel, priceTF, customerLabel, customerTF, warehouseLabel, warehouseTF, saveBtn);

        stuStage.setScene(new Scene(group, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT * 2));
        stuStage.show();
    }

}
