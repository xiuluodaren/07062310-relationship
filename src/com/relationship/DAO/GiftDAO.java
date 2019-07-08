package com.relationship.DAO;

import com.relationship.domain.Gift;
import com.relationship.util.DateUtil;
import com.relationship.util.JDBCConnection;
import com.relationship.util.XLUtil;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-16 13:59<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: <br>
 */
public class GiftDAO {

    /**
     * 查询活动列表
     * @return
     */
    public static List<Gift> findGiftList(Long Id)
    {
        String sql = "select `id`, `name`, `recvOrSend`, `liking`, `price`, `cause`, `time`, `remark`" +
                " from gift ";

        if (Id != null)
        {
            sql += " where id=" + Id;
        }

        ResultSet resultSet = JDBCConnection.query(sql);

        List<Gift> giftList = new ArrayList<>();

        try {
            while (resultSet.next())
            {
                Gift gift = new Gift();
                gift.setId(resultSet.getLong(1));
                gift.setName(resultSet.getString(2));
                gift.setRecvOrSend(resultSet.getInt(3));
                gift.setLiking(resultSet.getInt(4));
                gift.setPrice(resultSet.getBigDecimal(5));
                gift.setCause(resultSet.getString(6));
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                gift.setTime(LocalDateTime.parse(resultSet.getString(7).substring(0,19),df));
                gift.setRemark(resultSet.getString(8));

                giftList.add(gift);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return giftList;
    }

    /**
     * 更新活动
     * @return
     */
    public static boolean updateGift(Gift gift)
    {
        if (gift.getId() == null)
        {
            return false;
        }

        //先查询
        List<Gift> giftList = findGiftList(gift.getId());

        if (giftList == null || giftList.size() <= 0)
        {
            return false;
        }

        Gift oldFind = giftList.get(0);

        //用原来的值填充,方便更新
        XLUtil.fillObjectWithNull(oldFind,gift);

        String sql = "UPDATE `gift` SET " +
                "`name` = ?, `recvOrSend` = ?, `liking` = ?, `price` = ?, " +
                "`cause` = ?, `time` = ?, `remark` = ? WHERE `id` = ?;";

        boolean execute = JDBCConnection.execute(sql, gift.getName(), String.valueOf(gift.getRecvOrSend())
                , String.valueOf(gift.getLiking()),gift.getPrice().toString(), gift.getCause()
                , DateUtil.getDateTimeAsString(gift.getTime(),"yyyy-MM-dd HH:mm:ss")
                , gift.getRemark(), String.valueOf(gift.getId()));
        return execute;

    }

    /**
     * 删除活动
     * @return
     */
    public static boolean deleteGift(Long id)
    {
        String sql = "DELETE FROM gift WHERE id = ?;";

        boolean execute = JDBCConnection.execute(sql, String.valueOf(id));

        return execute;
    }

    /**
     * 创建活动
     * @param gift
     * @return
     */
    public static boolean createGift(Gift gift)
    {
        //构造更新sql
        String sql = "INSERT INTO `gift`(`name`, `recvOrSend`, `liking`, `price`, `cause`, `time`, `remark`) " +
                "VALUES (?,?,?,?,?,?,?);";

        //执行插入
        boolean execute = JDBCConnection.execute(sql, gift.getName(), String.valueOf(gift.getRecvOrSend())
                , String.valueOf(gift.getLiking()),gift.getPrice().toString(), gift.getCause()
                , DateUtil.getDateTimeAsString(gift.getTime(),"yyyy-MM-dd HH:mm:ss")
                , gift.getRemark()
                );

        return execute;
    }

}
