package com.wms.DAO;

import com.wms.domain.Customer;
import com.wms.domain.User;
import com.wms.util.DateUtil;
import com.wms.util.JDBCConnection;
import com.wms.util.LocalData;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-06-16 13:59<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: <br>
 */
public class UserDAO {

    /**
     * 登陆
     * @param userName
     * @param password
     * @return
     */
    public static User login(String userName, String password)
    {
        User user = null;
        try {

            // 创建sql语句
            String sql = "SELECT id,username,PASSWORD,type FROM [user] where username = ? and password = ? ORDER BY id";

            // 执行查询
            ResultSet rSet = JDBCConnection.query(sql, userName, password);

            // 遍历结果
            while (rSet.next()) {
                user = new User();
                user.setId(rSet.getLong(1));
                user.setUserName(rSet.getString(2));
                user.setPassword(rSet.getString(3));
                user.setType(rSet.getInt(4));
            }

            if (user != null)
            {
                LocalData.setCurrentUser(user);

                //记录登陆日志
                String loginSql = "INSERT INTO [login_log]([userId], [userName], [type], [loginTime]) " +
                        "VALUES (?,?,?,?);";

                String loginTime = DateUtil.getDateTimeAsString(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");

                boolean execute = JDBCConnection.execute(loginSql, String.valueOf(user.getId()), user.getUserName(), String.valueOf(user.getType()), loginTime);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * 注册
     * @param userName
     * @param password
     * @return
     */
    public static boolean register(String userName, String password)
    {
        //先查询是否存在
        String querySql = "SELECT id,username,PASSWORD,type FROM [user] where username = ?";

        ResultSet query = JDBCConnection.query(querySql, userName);

        boolean isExsit = false;

        try {
            while (query.next())
            {
                isExsit = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (isExsit == true)
        {
            //已存在
            return false;
        }

        //注册的只能是客户,管理员不允许注册
        String sql = "INSERT INTO [user]([username], [password], [type]) VALUES (?,?,1);";

        //执行注册sql
        boolean execute = JDBCConnection.execute(sql, userName, password);

        return execute;

    }

    /**
     * 查询用户列表
     * @return
     */
    public static List<User> findUser(Integer type)
    {
        String sql = "SELECT id,username,PASSWORD,type FROM [user]";

        if (type != null)
        {
            sql += " where type=" + type;
        }

        ResultSet resultSet = JDBCConnection.query(sql);

        List<User> userList = new ArrayList<>();

        try {
            while (resultSet.next())
            {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setUserName(resultSet.getString(2));
                user.setType(resultSet.getInt(4));
                userList.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return userList;
    }

    /**
     * 更新用户
     * @return
     */
    public static boolean updateUser(Long id, String userName)
    {
        String sql = "UPDATE [user] SET [username] = ? WHERE [id] = ?;";

        boolean execute = JDBCConnection.execute(sql, userName, String.valueOf(id));

        return execute;
    }

    /**
     * 删除用户
     * @return
     */
    public static boolean deletedUser(Long id)
    {
        String sql = "DELETE FROM [user] WHERE [id] = ?;";

        boolean execute = JDBCConnection.execute(sql, String.valueOf(id));

        return execute;
    }


}
