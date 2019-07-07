package com.wms.view;

import com.wms.DAO.StorageLogDAO;
import com.wms.DAO.UserDAO;
import com.wms.domain.StorageLog;
import com.wms.domain.User;
import com.wms.util.Constant;
import com.wms.util.LoadPropertiesFile;
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

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-16 20:33<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: 库存明细页面<br>
 */
public class StorageLogPane extends Pane {

    private Group group = new Group();

    public StorageLogPane() {
        super();
        getChildren().add(group);
        viewDidLoad();
    }

    //初始化方法,开始自定义布局
    private void viewDidLoad()
    {
        //构造一个列表
        TableView tableView = new TableView();
        tableView.prefWidthProperty().bind(this.widthProperty());
        tableView.prefHeightProperty().bind(this.heightProperty());
        tableView.setLayoutX(0);
        tableView.setLayoutY(0);
        group.getChildren().addAll(tableView);

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


        ObservableList<StorageLog> data = FXCollections.observableArrayList();

        //查询库存
        data.addAll(StorageLogDAO.findStorageLogList(null, null, null, null));

        tableView.getColumns().addAll(number, commodityName, beforestorage, changestoreage, currentstorage, price, customername, warehouse, createtime);
        tableView.setItems(data);

    }

}
