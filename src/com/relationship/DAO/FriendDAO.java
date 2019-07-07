package com.relationship.DAO;

import com.relationship.domain.Friend;
import com.relationship.domain.SocialCircle;
import com.relationship.util.JDBCConnection;
import com.relationship.util.XLUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-16 13:59<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: <br>
 */
public class FriendDAO {

    /**
     * 查询联系人列表
     * @return
     */
    public static List<Friend> findFriendList(Long Id)
    {
        String sql = "SELECT `id`, `name`, `sex`, `mobilePhone`, `QQ`, `wx` , socialCircleId, socialCircleName FROM friend";

        if (Id != null)
        {
            sql += " where id=" + Id;
        }

        ResultSet resultSet = JDBCConnection.query(sql);

        List<Friend> friendList = new ArrayList<>();

        try {
            while (resultSet.next())
            {
                Friend friend = new Friend();
                friend.setId(resultSet.getLong(1));
                friend.setName(resultSet.getString(2));
                friend.setSex(resultSet.getString(3));
                friend.setMobilePhone(resultSet.getString(4));
                friend.setQQ(resultSet.getString(5));
                friend.setWx(resultSet.getString(6));
                friend.setSocialCircleId(resultSet.getLong(7));
                friend.setSocialCircleName(resultSet.getString(8));

                friendList.add(friend);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return friendList;
    }

    /**
     * 更新联系人
     * @return
     */
    public static boolean updateFriend(Friend friend)
    {
        if (friend.getId() == null)
        {
            return false;
        }

        //先查询
        List<Friend> friendList = findFriendList(friend.getId());

        if (friendList == null || friendList.size() <= 0)
        {
            return false;
        }

        Friend oldFind = friendList.get(0);

        //用原来的值填充,方便更新
        XLUtil.fillObjectWithNull(oldFind,friend);

        String sql = "UPDATE `friend` " +
                "SET `name`=?,`sex`=?,`mobilePhone`=?,`QQ`=?,`wx`=?, socialCircleId=?, socialCircleName=? WHERE `id`=?;";

        boolean execute = JDBCConnection.execute(sql, friend.getName(), friend.getSex(), friend.getMobilePhone(),
                friend.getQQ(),friend.getWx(), String.valueOf(friend.getSocialCircleId()), friend.getSocialCircleName(), String.valueOf(friend.getId()));

        return execute;
    }

    /**
     * 删除联系人
     * @return
     */
    public static boolean deleteFriend(Long id)
    {
        String sql = "DELETE FROM friend WHERE id = ?;";

        boolean execute = JDBCConnection.execute(sql, String.valueOf(id));

        return execute;
    }

    //创建
    public static boolean createFriend(Friend friend)
    {
        //构造更新sql
        String sql = "INSERT INTO `friend`( `name`, `sex`, `mobilePhone`, `QQ`, `wx`, socialCircleId, socialCircleName)" +
                "VALUES (?,?,?,?,?,?,?);";

        //执行插入
        boolean execute = JDBCConnection.execute(sql, friend.getName(), friend.getSex(), friend.getMobilePhone(),
                friend.getQQ(), friend.getWx(), String.valueOf(friend.getSocialCircleId()), friend.getSocialCircleName());

        return execute;
    }

}
