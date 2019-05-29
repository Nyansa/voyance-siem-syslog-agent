package com.nyansa.siem.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class AgentDB {
  private static final Logger logger = LogManager.getLogger(AgentDB.class);

  private static final String DB_NAME = "voyance-agent";
  private Connection conn;

  public AgentDB() throws SQLException {
    try {
      conn = DriverManager.getConnection("jdbc:h2:file:./" + DB_NAME);
      conn.setAutoCommit(true);
      createTables();
    } catch (SQLException e) {
      logger.error("Caught SQL exception: {}", ExceptionUtils.getStackTrace(e));
      throw e;
    }
  }

  public void close() {
    try {
      conn.close();
    } catch (SQLException e) {
      logger.error("Caught SQL exception: {}", ExceptionUtils.getStackTrace(e));
    }
  }

  private void createTables() throws SQLException {
    Statement stmt = conn.createStatement();
    stmt.execute("CREATE TABLE IF NOT EXISTS last_read_ts (fetch_id VARCHAR(100) PRIMARY KEY, ts TIMESTAMP)");
    stmt.close();
  }

  public Timestamp getLastReadTs(final String fetchId) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement("SELECT ts FROM last_read_ts WHERE fetch_id = ?");
      stmt.setString(1, fetchId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getTimestamp(1);
      }
      return null;
    } catch (SQLException e) {
      logger.error("Caught SQL exception: {}", ExceptionUtils.getStackTrace(e));
      return null;
    } finally {
      closeStatement(stmt);
    }
  }

  public void setLastReadTs(final String fetchId, final Timestamp ts) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement("MERGE INTO last_read_ts VALUES (?, ?)");
      stmt.setString(1, fetchId);
      stmt.setTimestamp(2, ts);
      stmt.executeUpdate();
    } catch (SQLException e) {
      logger.error("Caught SQL exception: {}", ExceptionUtils.getStackTrace(e));
    } finally {
      closeStatement(stmt);
    }
  }

  private void closeStatement(Statement stmt) {
    if (stmt != null) {
      try {
        stmt.close();
      } catch (SQLException e) {
        logger.error("Caught SQL exception: {}", ExceptionUtils.getStackTrace(e));
      }
    }
  }
}
