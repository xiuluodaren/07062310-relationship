package com.relationship.view;

import com.relationship.DAO.PickDAO;
import com.relationship.domain.Pick;
import com.relationship.util.XLUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Map;

/**
 * 文件名称：PickPane<br>
 * 初始作者：修罗大人<br>
 * 创建日期：2019-06-17 10:27<br>
 * 功能说明：<br>
 */
public class PickManagerPane extends Pane {

    private Group group = new Group();

    private TableView tableView = new TableView();

    private ObservableList<Pick> data = null;

    public PickManagerPane() {
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
        Button createMenu = new Button("审核提货单");
        createMenu.setOnAction(event -> {
            System.out.println("审核提货单");

            Pick pick = (Pick) tableView.getSelectionModel().getSelectedItem();

            if (pick == null)
            {
                XLUtil.showAlert("提示","请先选择提货单");
                return;
            }

            boolean confirm = XLUtil.showConfirm("确认", "确定批准该提货单吗?");

            if (confirm == true)
            {
                Map<String,String> map = PickDAO.examinePick(pick.getId());
                if (map.get("success").equals("true"))
                {
                    data.clear();
                    data.addAll(PickDAO.findPickList(null,null));
                    tableView.refresh();
                    XLUtil.showAlert("提示","成功");
                }else{
                    XLUtil.showAlert("提示",map.get("msg"));
                }
            }

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
        data.addAll(PickDAO.findPickList(null,null));

        tableView.getColumns().addAll(number, commodityName, price, nums, customername, createtime, examineFlag, examineUser, examineTime);
        tableView.setItems(data);

    }

}
