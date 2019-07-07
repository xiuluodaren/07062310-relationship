package com.relationship.DAO;

import com.relationship.domain.SocialCircle;
import com.relationship.util.JDBCConnection;
import com.relationship.util.XLUtil;

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
public class SocialCircleDAO {

    //查询列表
    public static List<SocialCircle> findSocialCircleList(Long id,Long customerId)
    {
        List<SocialCircle> SocialCircleList = new ArrayList<>();

        //构造sql
        String sql = "SELECT id,`number`, `name`, `principal`, `mobilePhone`, `QQ`, `wx` " +
                "FROM social_circle where 1=1 ";

        if (id != null)
        {
            sql += " and id = " + id;
        }

        //执行查询
        ResultSet resultSet = JDBCConnection.query(sql);

        try {
            while (resultSet.next())
            {
                Long newId = resultSet.getLong(1);
                String number = resultSet.getString(2);
                String name = resultSet.getString(3);
                String principal = resultSet.getString(4);
                String mobilePhone = resultSet.getString(5);
                String QQ = resultSet.getString(6);
                String wx = resultSet.getString(7);

                SocialCircle SocialCircle = new SocialCircle(newId, number, name, principal, mobilePhone, QQ, wx);
                SocialCircleList.add(SocialCircle);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SocialCircleList;
    }

    //更新
    public static boolean updateSocialCircle(SocialCircle socialCircle)
    {
        if (socialCircle.getId() == null)
        {
            return false;
        }

        //先查询
        List<SocialCircle> SocialCircleList = findSocialCircleList(socialCircle.getId(),null);

        if (SocialCircleList == null || SocialCircleList.size() <= 0)
        {
            return false;
        }

        SocialCircle oldCommmodity = SocialCircleList.get(0);

        //用原来的值填充,方便更新
        XLUtil.fillObjectWithNull(oldCommmodity,socialCircle);

        //构造更新sql
        String sql = "UPDATE `social_circle`\n" +
                "SET `number`=?,`name`=?,`principal`=?,`mobilePhone`=?,`QQ`=?,`wx`=? WHERE `id`= ?;";

        //执行更新
        boolean execute = JDBCConnection.execute(sql, socialCircle.getNumber(), socialCircle.getName(),socialCircle.getPrincipal(),
                socialCircle.getMobilePhone(),socialCircle.getWx(),socialCircle.getQQ(),String.valueOf(socialCircle.getId()));

        return execute;
    }

    //创建
    public static boolean createSocialCircle(SocialCircle socialCircle)
    {
        //构造更新sql
        String sql = "INSERT INTO `social_circle` ( `number`, `name`, `principal`, `mobilePhone`, `QQ`, `wx` ) " +
                "VALUES ( ?,?,?,?,?,?);";

        //执行插入
        boolean execute = JDBCConnection.execute(sql, socialCircle.getNumber(),socialCircle.getName(),socialCircle.getPrincipal(),
                socialCircle.getMobilePhone(),socialCircle.getQQ(),socialCircle.getWx());

        return execute;
    }

    //删除
    public static boolean deleteSocialCircle(Long id)
    {
        //构造更新sql
        String sql = "DELETE FROM social_circle WHERE id = ?";

        //执行删除
        boolean execute = JDBCConnection.execute(sql, String.valueOf(id));

        return execute;
    }

}
