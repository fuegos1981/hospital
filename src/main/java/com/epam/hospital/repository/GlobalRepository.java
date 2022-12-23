package com.epam.hospital.repository;


import java.sql.*;
import java.util.Date;
import java.util.List;

public abstract class GlobalRepository<T> {


    public T read(String query, Object... filters) throws DBException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            addFilters(stmt, filters);

            try (ResultSet rs = stmt.executeQuery()) {
                return readByResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Trouble with method read by object ", e);
        }

    }

    public int readSize(String query, Object... filters) throws DBException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
             addFilters(stmt, filters);
             try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }else
                    throw new DBException("Trouble with method readSize by object ");

             }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Trouble with method readSize by object ", e);
        }

    }

    protected abstract T readByResultSet(ResultSet rs) throws SQLException,DBException;

    public List<T> findAll(String query, Object... filters) throws DBException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            addFilters(stmt, filters);
            try (ResultSet rs = stmt.executeQuery()) {
                return findByResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Trouble with method findAll object "+e.getMessage(), e);
        }
    }

    protected abstract List<T> findByResultSet(ResultSet rs) throws SQLException,DBException;

    public int insert(String query,Object... filters) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = ConnectionPool.getConnection();
            stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            addFilters(stmt, filters);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Trouble with method insert! ", e);
        } finally {
            close(stmt);
            close(con);
        }
    }

    public boolean update(String query, Object... filters) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            stmt = con.prepareStatement(query);
            addFilters(stmt, filters);
            boolean res = stmt.executeUpdate() != 0;
            con.commit();
            return res;
        } catch (SQLException e) {
            rollback(con);
            throw new DBException("Trouble with method update! ", e);
        } finally {
            close(stmt);
            close(con);
        }
    }

    protected boolean delete(String query, Object... filters) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            boolean res = true;
            stmt = con.prepareStatement(query);
            addFilters(stmt, filters);
            if (stmt.executeUpdate() == 0) res = false;
            con.commit();
            return res;
        } catch (SQLException e) {
            rollback(con);
            throw new DBException("Trouble with method delete! ", e);
        } finally {
            close(stmt);
            close(con);
        }
    }

    private void addFilters(PreparedStatement stmt, Object[] filters) throws SQLException {
        int step = 1;
        for (Object obj : filters) {
            if (obj instanceof String) {
                stmt.setString(step, (String) obj);
            }
            else if (obj instanceof Date) {
                Date date = (Date)obj;
                java.sql.Date sqlPackageDate = new java.sql.Date(date.getTime());
                stmt.setDate(step, sqlPackageDate);
            } else {
                stmt.setInt(step, (Integer) obj);
            }
            step++;
        }
    }

    private void close(AutoCloseable el) throws DBException {
        if (el != null) {
            try {
                el.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new DBException("not close el! ", e);
            }
        }
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
