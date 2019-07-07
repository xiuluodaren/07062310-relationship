package com.wms.DAO;

import com.wms.domain.Commodity;
import com.wms.util.JDBCConnection;
import com.wms.util.XLUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-15 10:18<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: <br>
 */
public class CommodityDAO {

    //查询列表
    public static List<Commodity> findCommodityList(Long id,Long customerId)
    {
        List<Commodity> commodityList = new ArrayList<>();

        //构造sql
        String sql = "SELECT id,number,name,storage,price,customerId,customerName,warehouse " +
                "FROM [commodity] where 1=1 ";

        if (id != null)
        {
            sql += " and id = " + id;
        }

        if (customerId != null)
        {
            sql += " and customerId = " + customerId;
        }

        //执行查询
        ResultSet resultSet = JDBCConnection.query(sql);

        try {
            while (resultSet.next())
            {
                Long newId = resultSet.getLong(1);
                String number = resultSet.getString(2);
                String name = resultSet.getString(3);
                BigDecimal storage = resultSet.getBigDecimal(4);
                BigDecimal price = resultSet.getBigDecimal(5);
                Long customerIdNew = resultSet.getLong(6);
                String customerName = resultSet.getString(7);
                String warehouse = resultSet.getString(8);

                Commodity commodity = new Commodity(newId, number, name, storage, price, customerIdNew, customerName, warehouse);
                commodityList.add(commodity);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commodityList;
    }

    //更新
    public static boolean updateCommodity(Commodity commodity)
    {
        if (commodity.getId() == null)
        {
            return false;
        }

        //先查询
        List<Commodity> commodityList = findCommodityList(commodity.getId(),null);

        if (commodityList == null || commodityList.size() <= 0)
        {
            return false;
        }

        Commodity oldCommmodity = commodityList.get(0);

        //用原来的值填充,方便更新
        XLUtil.fillObjectWithNull(oldCommmodity,commodity);

        //构造更新sql
        String sql = "UPDATE [commodity] SET [number] = ?, [name] = ?, [storage] = ?, [price] = ?, [customerId] = ?, [customerName] = ?, [warehouse] = ? WHERE [id] = ?;";

        //执行更新
        boolean execute = JDBCConnection.execute(sql, commodity.getNumber(), commodity.getName(), String.valueOf(commodity.getStorage()),
                String.valueOf(commodity.getPrice()), String.valueOf(commodity.getCustomerid()), commodity.getCustomername(),
                commodity.getWarehouse(),String.valueOf(commodity.getId()));

        return execute;
    }

    //创建
    public static boolean createCommodity(Commodity commodity)
    {
        //构造更新sql
        String sql = "INSERT INTO [commodity]([number], [name], [storage], [price], [customerId], [customerName], [warehouse]) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        //执行插入
        boolean execute = JDBCConnection.execute(sql, commodity.getNumber(), commodity.getName(), String.valueOf(commodity.getStorage()),
                String.valueOf(commodity.getPrice()), String.valueOf(commodity.getCustomerid()), commodity.getCustomername(),
                commodity.getWarehouse());

        return execute;
    }

    //删除
    public static boolean deleteCommodity(Long id)
    {
        //构造更新sql
        String sql = "DELETE FROM [commodity] WHERE id = ?";

        //执行删除
        boolean execute = JDBCConnection.execute(sql, String.valueOf(id));

        return execute;
    }

}
