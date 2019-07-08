package com.relationship.DAO;

import com.relationship.domain.Cooperation;
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
public class CooperationDAO {

    /**
     * 查询活动列表
     * @return
     */
    public static List<Cooperation> findCooperationList(Long Id)
    {
        String sql = "select `id`, `titile`, `cooperationerName`, `startTime`, `endTime`, `detail`, `status`" +
                " from cooperation ";

        if (Id != null)
        {
            sql += " where id=" + Id;
        }

        ResultSet resultSet = JDBCConnection.query(sql);

        List<Cooperation> cooperationList = new ArrayList<>();

        try {
            while (resultSet.next())
            {
                Cooperation cooperation = new Cooperation();
                cooperation.setId(resultSet.getLong(1));
                cooperation.setTitle(resultSet.getString(2));
                cooperation.setCooperationerName(resultSet.getString(3));
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                cooperation.setStartTime(LocalDateTime.parse(resultSet.getString(4).substring(0,19),df));
                cooperation.setEndTime(LocalDateTime.parse(resultSet.getString(5).substring(0,19),df));
                cooperation.setDetail(resultSet.getString(6));
                cooperation.setStatus(resultSet.getString(7));

                cooperationList.add(cooperation);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return cooperationList;
    }

    /**
     * 更新活动
     * @return
     */
    public static boolean updateCooperation(Cooperation cooperation)
    {
        if (cooperation.getId() == null)
        {
            return false;
        }

        //先查询
        List<Cooperation> cooperationList = findCooperationList(cooperation.getId());

        if (cooperationList == null || cooperationList.size() <= 0)
        {
            return false;
        }

        Cooperation oldFind = cooperationList.get(0);

        //用原来的值填充,方便更新
        XLUtil.fillObjectWithNull(oldFind,cooperation);

        String sql = "UPDATE `cooperation` " +
                "SET `titile`=?,`cooperationerName`=?,`startTime`=?,`endTime`=?,`detail`=?,`status`=? WHERE `id`=?;";

        boolean execute = JDBCConnection.execute(sql, cooperation.getTitle(), cooperation.getCooperationerName()
                , DateUtil.getDateTimeAsString(cooperation.getStartTime(),"yyyy-MM-dd HH:mm:ss")
                , DateUtil.getDateTimeAsString(cooperation.getEndTime(),"yyyy-MM-dd HH:mm:ss")
                , cooperation.getDetail(), cooperation.getStatus(), String.valueOf(cooperation.getId()));
        return execute;

    }

    /**
     * 删除活动
     * @return
     */
    public static boolean deleteCooperation(Long id)
    {
        String sql = "DELETE FROM cooperation WHERE id = ?;";

        boolean execute = JDBCConnection.execute(sql, String.valueOf(id));

        return execute;
    }

    /**
     * 创建活动
     * @param cooperation
     * @return
     */
    public static boolean createCooperation(Cooperation cooperation)
    {
        //构造更新sql
        String sql = "INSERT INTO `cooperation`(`titile`, `cooperationerName`, `startTime`, `endTime`, `detail`, `status`) " +
                "VALUES (?,?,?,?,?,?);";

        //执行插入
        boolean execute = JDBCConnection.execute(sql, cooperation.getTitle(), cooperation.getCooperationerName()
                , DateUtil.getDateTimeAsString(cooperation.getStartTime(),"yyyy-MM-dd HH:mm:ss")
                , DateUtil.getDateTimeAsString(cooperation.getEndTime(),"yyyy-MM-dd HH:mm:ss")
                , cooperation.getDetail(), cooperation.getStatus()
                );

        return execute;
    }

}
