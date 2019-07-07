package com.relationship.DAO;

import com.relationship.domain.Commodity;
import com.relationship.domain.Pick;
import com.relationship.domain.User;
import com.relationship.util.DateUtil;
import com.relationship.util.JDBCConnection;
import com.relationship.util.LocalData;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-15 18:18<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: <br>
 */
public class PickDAO {

    //查询列表
    public static List<Pick> findPickList(Long customerId, Long id)
    {
        List<Pick> pickList = new ArrayList<>();

        //构造sql
        String sql = "SELECT id,number,nums,price,commodityId,commodityName,createtime,customerid,customername,examineFlag,examineUser,examineTime FROM [pick] where 1=1 ";

        List<String> params = new ArrayList<>();

        if (customerId != null && !"".equals(customerId))
        {
            sql += " and customerid = ?";
            params.add(String.valueOf(customerId));
        }

        if (id != null)
        {
            sql += " and id = ?";
            params.add(String.valueOf(id));
        }

        //执行查询
        ResultSet resultSet = JDBCConnection.queryByList(sql,params);

        try {
            while (resultSet.next())
            {
                Long idNew = resultSet.getLong(1);
                String number = resultSet.getString(2);
                BigDecimal nums = resultSet.getBigDecimal(3);
                BigDecimal price = resultSet.getBigDecimal(4);
                Long commodityId = resultSet.getLong(5);
                String commodityName = resultSet.getString(6);
                Long customerIdNew = resultSet.getLong(8);
                String customerNameNew = resultSet.getString(9);
                Integer examineFlag = resultSet.getInt(10);
                String examineUser = resultSet.getString(11);
                Time time = resultSet.getTime(7);
                Date date = resultSet.getDate(7);

                Time examineTime = resultSet.getTime(12);
                Date examineDate = resultSet.getDate(12);

                LocalDateTime createtime = null;
                LocalDateTime examine = null;
                //修正时间戳
                if (time != null && date != null)
                    createtime = DateUtil.getDateTimeOfTimestamp(date.getTime() + time.getTime() + 28800000);
                if (examineTime != null && examineDate != null)
                    examine = DateUtil.getDateTimeOfTimestamp(examineDate.getTime() + examineTime.getTime() + 28800000);

                Pick pick = new Pick(idNew, number, nums, price, commodityId, commodityName, createtime, customerIdNew,
                        customerNameNew, examineFlag, examineUser, examine);
                pickList.add(pick);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pickList;
    }

    //创建
    public static boolean createPick(Pick pick)
    {
        //构造sql
        String sql = "INSERT INTO [pick] ( [number], [nums], [price], [customerId], [customerName], [createTime], " +
                "[commodityId], [commodityName]) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";

        String createTime = LocalDateTime.now(Clock.system(ZoneId.systemDefault())).toString();

        //执行插入
        boolean execute = JDBCConnection.execute(sql,pick.getNumber(),String.valueOf(pick.getNums()),String.valueOf(pick.getPrice()),String.valueOf(pick.getCustomerid())
        ,pick.getCustomername(),createTime,String.valueOf(pick.getCommodityId()),pick.getCommodityName());

        return execute;
    }

    //审核
    public static Map<String, String> examinePick(Long id)
    {
        Map<String,String> map = new HashMap<>();
        map.put("success","true");

        //先查询
        List<Pick> pickList = findPickList(null, id);
        if (pickList == null || pickList.size() <= 0)
        {
            map.put("success","false");
            map.put("msg","未查询到提货申请单");
            return map;
        }

        Pick pick = pickList.get(0);

        //查询商品,判断库存
        List<Commodity> commodityList = CommodityDAO.findCommodityList(pick.getCommodityId(), null);

        if (commodityList == null || commodityList.size() <= 0)
        {
            //未查询到商品
            map.put("success","false");
            map.put("msg","未查询到商品");
            return map;
        }

        Commodity commodity = commodityList.get(0);

        //判断库存
        if (commodity.getStorage().compareTo(pick.getNums()) < 0)
        {
            //库存不足
            map.put("success","false");
            map.put("msg","库存不足");
            return map;
        }

        String examineTime = LocalDateTime.now(Clock.system(ZoneId.systemDefault())).toString();

        String sql = "UPDATE [pick] SET [examineFlag] = 1, [examineUser] = ?," +
                "[examineTime] = ? WHERE [id] = ?;";

        User user = LocalData.getCurrentUser();
        boolean execute = JDBCConnection.execute(sql, user.getUserName(),
                DateUtil.getDateTimeAsString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss"),
                String.valueOf(id));

        return map;

    }

}
