package com.relationship.DAO;

import com.relationship.domain.Active;
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
public class ActiveDAO {

    /**
     * 查询活动列表
     * @return
     */
    public static List<Active> findActiveList(Long Id)
    {
        String sql = "select `id`, `title`, `detail`, `startTime`, `endTime`, `initiator`, `address`, `summary`, `createTime` from active ";

        if (Id != null)
        {
            sql += " where id=" + Id;
        }

        ResultSet resultSet = JDBCConnection.query(sql);

        List<Active> activeList = new ArrayList<>();

        try {
            while (resultSet.next())
            {
                Active active = new Active();
                active.setId(resultSet.getLong(1));
                active.setTitle(resultSet.getString(2));
                active.setDetail(resultSet.getString(3));
//                active.setStartTime(resultSet.getString(5));
//                active.setEndTime(resultSet.getString(6));
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                active.setStartTime(LocalDateTime.parse(resultSet.getString(4).substring(0,19),df));
                active.setEndTime(LocalDateTime.parse(resultSet.getString(5).substring(0,19),df));
                active.setInitiator(resultSet.getString(6));
                active.setAddress(resultSet.getString(7));
                active.setSummary(resultSet.getString(8));
                active.setCreateTime(LocalDateTime.parse(resultSet.getString(9).substring(0,19),df));

                activeList.add(active);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return activeList;
    }

    /**
     * 更新活动
     * @return
     */
    public static boolean updateActive(Active active)
    {
        if (active.getId() == null)
        {
            return false;
        }

        //先查询
        List<Active> activeList = findActiveList(active.getId());

        if (activeList == null || activeList.size() <= 0)
        {
            return false;
        }

        Active oldFind = activeList.get(0);

        //用原来的值填充,方便更新
        XLUtil.fillObjectWithNull(oldFind,active);

        String sql = "UPDATE `active` SET `title` = ?, `detail` = ?, `startTime` = ?, `endTime` = ?, `initiator` = ?, " +
                "`address` = ?, `summary` = ?, `createTime` = ? WHERE `id` = ?;";

        boolean execute = JDBCConnection.execute(sql, active.getTitle(), active.getDetail(), DateUtil.getDateTimeAsString(active.getStartTime(),"yyyy-MM-dd HH:mm:ss")
                , DateUtil.getDateTimeAsString(active.getEndTime(),"yyyy-MM-dd HH:mm:ss"), active.getInitiator(), active.getAddress()
                , active.getSummary(), DateUtil.getDateTimeAsString(active.getCreateTime(),"yyyy-MM-dd HH:mm:ss"),String.valueOf(active.getId()));
        return execute;

    }

    /**
     * 删除活动
     * @return
     */
    public static boolean deleteActive(Long id)
    {
        String sql = "DELETE FROM active WHERE id = ?;";

        boolean execute = JDBCConnection.execute(sql, String.valueOf(id));

        return execute;
    }

    /**
     * 创建活动
     * @param active
     * @return
     */
    public static boolean createActive(Active active)
    {
        //构造更新sql
        String sql = "INSERT INTO `active`(`title`, `detail`, `startTime`, `endTime`, `initiator`, `address`, `summary`, `createTime`) " +
                "VALUES (?,?,?,?,?,?,?,?);";

        //执行插入
        boolean execute = JDBCConnection.execute(sql, active.getTitle(), active.getDetail(), DateUtil.getDateTimeAsString(active.getStartTime(),"yyyy-MM-dd HH:mm:ss")
                , DateUtil.getDateTimeAsString(active.getEndTime(),"yyyy-MM-dd HH:mm:ss"), active.getInitiator(), active.getAddress()
                , active.getSummary(), DateUtil.getDateTimeAsString(active.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));

        return execute;
    }

}
