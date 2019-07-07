package com.relationship.view;

import com.relationship.DAO.CommodityDAO;
import com.relationship.DAO.UserDAO;
import com.relationship.domain.Commodity;
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
 * 文件名称：CommodityPane<br>
 * 初始作者：修罗大人<br>
 * 创建日期：2019-06-17 10:27<br>
 * 功能说明：<br>
 */
public class CommodityPane extends Pane {

    private Group group = new Group();

    private TableView tableView = new TableView();

    private ObservableList<Commodity> data = null;

    public CommodityPane() {
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
        Button createMenu = new Button("新增货物");
        createMenu.setOnAction(event -> {
            System.out.println("新增货物");

            createOrUpdate(null);

        });

        Button editMenu = new Button("编辑货物");
        editMenu.setOnAction(event -> {
            System.out.println("编辑货物");

            Commodity selectedCommodity = (Commodity) tableView.getSelectionModel().getSelectedItem();

            if (selectedCommodity == null)
            {
                XLUtil.showAlert("提示","请先选择货物");
                return;
            }

            createOrUpdate(selectedCommodity.getId());

        });

        Button deleteMenu = new Button("删除货物");
        deleteMenu.setOnAction(event -> {
            System.out.println("删除货物");

            Commodity selectedCommodity = (Commodity) tableView.getSelectionModel().getSelectedItem();
            if (selectedCommodity == null)
            {
                XLUtil.showAlert("提示","请先选择货物");
                return;
            }

            boolean confirm = XLUtil.showConfirm("确认", "确定删除该货物吗?");

            if (confirm == true)
            {
                //删除
                boolean execute = CommodityDAO.deleteCommodity(selectedCommodity.getId());

                if (execute == true)
                {
                    data.clear();
                    data.addAll(CommodityDAO.findCommodityList(null,null));
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

        TableColumn name = new TableColumn("货物名称");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn storage = new TableColumn("库存");
        storage.setCellValueFactory(new PropertyValueFactory<>("storage"));

        TableColumn price = new TableColumn("价格");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn customername = new TableColumn("货主名称");
        customername.setCellValueFactory(new PropertyValueFactory<>("customername"));

        TableColumn warehouse = new TableColumn("仓库");
        warehouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));

        data = FXCollections.observableArrayList();

        //查询货物
        data.addAll(CommodityDAO.findCommodityList(null,null));

        tableView.getColumns().addAll(number, name, storage, price, customername, warehouse);
        tableView.setItems(data);

    }

    //更新或新增
    public void createOrUpdate(Long id)
    {
        Stage stuStage = new Stage();
        stuStage.setTitle("录入货物信息");

        if (id != null)
        {
            stuStage.setTitle("更新货物信息");
        }

        //使用group可以用坐标布局
        Group group = new Group();

        //声明货物对象
        Commodity student = new Commodity();

        //货物编号
        Label numberLabel = new Label("货物编号:");
        numberLabel.setLayoutX(Constant.MARGER_LEFT);
        numberLabel.setLayoutY(Constant.V_MARGER);
        numberLabel.setPrefWidth(Constant.LABEL_WIDTH);
        numberLabel.setPrefHeight(Constant.C_HEIGHT);

        //货物编号文本框
        TextField numberTF = new TextField();
        numberTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        numberTF.setLayoutY(Constant.V_MARGER);
        numberTF.setPrefHeight(Constant.C_HEIGHT);

        //货物姓名
        Label nameLabel = new Label("货物名称:");
        nameLabel.setLayoutX(Constant.MARGER_LEFT);
        nameLabel.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT);
        nameLabel.setPrefWidth(Constant.LABEL_WIDTH);
        nameLabel.setPrefHeight(Constant.C_HEIGHT);

        //货物姓名文本框
        TextField nameTF = new TextField();
        nameTF.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        nameTF.setLayoutY(Constant.V_MARGER * 2 + Constant.C_HEIGHT);
        nameTF.setPrefHeight(Constant.C_HEIGHT);

        //库存
        Label storageLabel = new Label("库存:");
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
        ComboBox customerComboBox = new ComboBox();
        customerComboBox.setLayoutX(Constant.LABEL_WIDTH + Constant.MARGER_LEFT + Constant.H_MARGER);
        customerComboBox.setLayoutY(Constant.V_MARGER * 5 + Constant.C_HEIGHT * 4);
        customerComboBox.setPrefHeight(Constant.C_HEIGHT);
        customerComboBox.prefWidthProperty().bind(priceTF.widthProperty());

        //查询货主
        List<User> userList = UserDAO.findUser(1);
        customerComboBox.getItems().addAll(userList);
        if (userList != null && userList.size() > 0)
            customerComboBox.setValue(userList.get(0));

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

            String number = numberTF.getText();
            String commodityName = nameTF.getText();
            String storage = storageTF.getText();
            String price = priceTF.getText();
            String warehouse = warehouseTF.getText();
            User customer = (User) customerComboBox.getValue();
            boolean execute = false;

            Commodity commodity = new Commodity();
            commodity.setId(id);
            commodity.setNumber(number);
            commodity.setName(commodityName);
            commodity.setStorage(new BigDecimal(storage));
            commodity.setPrice(new BigDecimal(price));
            commodity.setWarehouse(warehouse);
            commodity.setCustomerid(customer.getId());
            commodity.setCustomername(customer.getUserName());


            if (id == null)
            {
                execute = CommodityDAO.createCommodity(commodity);
            }else{
                execute = CommodityDAO.updateCommodity(commodity);
            }

            stuStage.hide();
            if (execute == true)
            {
                //查询货物
                data.clear();
                data.addAll(CommodityDAO.findCommodityList(null,null));
                tableView.refresh();
                XLUtil.showAlert("提示","保存成功");
            }else{
                XLUtil.showAlert("提示","保存失败");
            }

        }));

        group.getChildren().addAll(numberLabel, numberTF, nameLabel, nameTF,storageLabel, storageTF,
                priceLabel, priceTF, customerLabel, customerComboBox, warehouseLabel, warehouseTF, saveBtn);

        stuStage.setScene(new Scene(group, Constant.SCENE_WIDTH, Constant.SCENE_HEIGHT * 2));
        stuStage.show();
    }

}
