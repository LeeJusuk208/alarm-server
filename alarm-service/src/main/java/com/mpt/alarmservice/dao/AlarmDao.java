package com.mpt.alarmservice.dao;

import com.mpt.alarmservice.config.JdbcUtil;
import com.mpt.alarmservice.domain.Alarm;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Repository
public class AlarmDao {
    private final JdbcUtil jdbcUtil;
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public AlarmDao(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public ArrayList<Alarm> getAlarmList(){
        Alarm alarm;
        ArrayList<Alarm> list = new ArrayList<>();
        try { conn = jdbcUtil.getConnection();
            String sql = "select * from alarm";
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
            }
            rs = stmt.executeQuery();

            while(rs.next()) {
                alarm = new Alarm(rs.getString("email"),
                        rs.getInt("goods_id"),
                        rs.getInt("target_price"));

                list.add(alarm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close(rs, stmt, conn);
        }
        return list;
    }
}
