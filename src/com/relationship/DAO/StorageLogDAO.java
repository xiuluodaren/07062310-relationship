package com.relationship.DAO;

import com.relationship.domain.Commodity;
import com.relationship.domain.StorageLog;
import com.relationship.util.DateUtil;
import com.relationship.util.JDBCConnection;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-15 18:18<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: <br>
 */
public class StorageLogDAO {

    //查询列表
    public static List<StorageLog> findStorageLogList(String id, String storageType, String commodityName, String customerName)
    {
        List<StorageLog> storageLogList = new ArrayList<>();

        //构造sql
        String sql = "SELECT id,number,name,storagetype,beforestorage,changestoreage,currentstorage,price,customerid,customername,warehouse,createtime,commodityId,commodityName FROM [storage_log] where 1=1 ";

        List<String> params = new ArrayList<>();

        if (id != null && !"".equals(id))
        {
            sql += " and id = ?";
            params.add(id);
        }

        if (storageType != null && !"".equals(storageType))
        {
            sql += " and storageType = ?";
            params.add(storageType);
        }

        if (commodityName != null && !"".equals(commodityName))
        {
            sql += " and commodityName = ?";
            params.add(commodityName);
        }

        if (customerName != null && !"".equals(customerName))
        {
            sql += " and customerName = ?";
            params.add(customerName);
        }

        //执行查询
        ResultSet resultSet = JDBCConnection.queryByList(sql,params);

        try {
            while (resultSet.next())
            {
                Long newId = resultSet.getLong(1);
                String number = resultSet.getString(2);
                String name = resultSet.getString(3);
                Integer storagetype = resultSet.getInt(4);
                BigDecimal beforestorage = resultSet.getBigDecimal(5);
                BigDecimal changestoreage = resultSet.getBigDecimal(6);
                BigDecimal currentstorage = resultSet.getBigDecimal(7);
                BigDecimal price = resultSet.getBigDecimal(8);
                Long customerId = resultSet.getLong(9);
                String customerNameNew = resultSet.getString(10);
                String warehouse = resultSet.getString(11);
                Time time = resultSet.getTime(12);
                Date date = resultSet.getDate(12);
                //修正时间戳
                LocalDateTime createtime = DateUtil.getDateTimeOfTimestamp(date.getTime() + time.getTime() + 28800000);
                Long commodityId = resultSet.getLong(13);
                String commodityNameNew = resultSet.getString(14);

                StorageLog storageLog = new StorageLog(newId, number,commodityId, commodityNameNew, name, storagetype, beforestorage, changestoreage,
                        currentstorage, price, customerId, customerNameNew, warehouse, createtime);
                storageLogList.add(storageLog);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return storageLogList;
    }

    //创建
    public static boolean createStorageLog(StorageLog storageLog)
    {
        //修改商品库存
        if (storageLog.getCommodityId() != null)
        {
            List<Commodity> commodityList = CommodityDAO.findCommodityList(storageLog.getCommodityId(),null);

            if (commodityList != null && commodityList.size() > 0)
            {
                Commodity commodity = commodityList.get(0);
                BigDecimal oldStorage = commodity.getStorage();
                BigDecimal changeStorage = storageLog.getChangestoreage();
                if (storageLog.getStoragetype() == 1)
                {
                    //出库
                    changeStorage = changeStorage.multiply(new BigDecimal("-1"));
                }
                BigDecimal newStorage = oldStorage.add(changeStorage);

                commodity.setStorage(newStorage);
                boolean b = CommodityDAO.updateCommodity(commodity);

                //修改日志信息
                storageLog.setBeforestorage(oldStorage);
                storageLog.setCurrentstorage(newStorage);
            }

        }

        //构造sql
        String sql = "INSERT INTO [storage_log]([number], [name], [storageType], [beforeStorage], [changeStoreage], [currentStorage], [price], [customerId], [customerName], [warehouse], [createTime], [commodityId], [commodityName]) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        String createTime = LocalDateTime.now(Clock.system(ZoneId.systemDefault())).toString();

        //插入库存日志
        boolean execute = JDBCConnection.execute(sql, storageLog.getNumber(), storageLog.getName(), String.valueOf(storageLog.getStoragetype()),
                String.valueOf(storageLog.getBeforestorage()), String.valueOf(storageLog.getChangestoreage()), String.valueOf(storageLog.getCurrentstorage()),
                String.valueOf(storageLog.getPrice()), String.valueOf(storageLog.getCustomerid()), storageLog.getCustomername(), storageLog.getWarehouse(),
                createTime,String.valueOf(storageLog.getCommodityId()),storageLog.getCommodityName()
        );


        return execute;
    }

}
