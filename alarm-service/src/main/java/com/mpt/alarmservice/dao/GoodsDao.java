package com.mpt.alarmservice.dao;

import com.mpt.alarmservice.config.JdbcUtil;
import com.mpt.alarmservice.domain.Goods;
import com.mpt.alarmservice.dto.AlarmDto;
import com.mpt.alarmservice.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

@Repository
public class GoodsDao {
    private final JdbcUtil jdbcUtil;
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public GoodsDao(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public Goods getGoodsById(int id) {
        Goods goods = null;

        try {
            conn = jdbcUtil.getConnection();
            String sql = " select * from goods where id=?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            rs = psmt.executeQuery();
            if (rs.next()) {
                goods = new Goods(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("del_price"),
                        rs.getInt("price"),
                        rs.getDouble("rating"),
                        rs.getInt("rating_count"),
                        rs.getString("img"),
                        rs.getString("url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close(rs, psmt, conn);
        }
        return goods;
    }

    public ResponseEntity<String> postAlarm(UserResponse userResponse, HashMap<String, String> param) {
        try {
            conn = jdbcUtil.getConnection();
            String sql = "insert into alarm(email, platform, goods_id, target_price) values(?, ?, ?, ?) on duplicate key update target_price = ?";

            psmt = conn.prepareStatement(sql);
            psmt.setString(1, userResponse.getEmail());
            psmt.setString(2, userResponse.getPlatform());
            psmt.setInt(3, Integer.parseInt(param.get("goods_id")));
            psmt.setInt(4, Integer.parseInt(param.get("target_price")));
            psmt.setInt(5, Integer.parseInt(param.get("target_price")));
            psmt.executeUpdate();
            jdbcUtil.close(rs, psmt, conn);
            return ResponseEntity.status(HttpStatus.OK).body("register success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } finally {
            jdbcUtil.close(rs, psmt, conn);
        }
    }

    public AlarmDto existAlarm(UserResponse userResponse, int goodsId) {
        AlarmDto alarmDto = null;
        try {
            conn = jdbcUtil.getConnection();
            String sql = "select email, goods_id, target_price from alarm where goods_id = ? and email = ? and platform = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, goodsId);
            psmt.setString(2, userResponse.getEmail());
            psmt.setString(3, userResponse.getPlatform());

            rs = psmt.executeQuery();
            while (rs.next()) {
                alarmDto = new AlarmDto(
                        rs.getString("email"),
                        rs.getString("goods_id"),
                        rs.getInt("target_price"));
            }
            jdbcUtil.close(rs, psmt, conn);
            return alarmDto;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close(rs, psmt, conn);
        }
        return alarmDto;
    }
}

