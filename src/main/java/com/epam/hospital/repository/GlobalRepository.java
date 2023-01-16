package com.epam.hospital.repository;

import com.epam.hospital.exceptions.DBException;
import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 * abstract class contains methods for working with the database.
 *
 * @author Sinkevych Olena
 *
 */
public abstract class GlobalRepository<T> {

    public T read(String query, Object... filters) throws DBException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            addFilters(stmt, filters);
            try (ResultSet rs = stmt.executeQuery()) {
                return readByResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Trouble with method read by object " +e.getMessage());
        }

    }

    public int readSize(String query, Object... filters) throws DBException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
             addFilters(stmt, filters);
             ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                else return 0;
             }
         catch (SQLException e) {
            throw new DBException("Trouble with method readSize by object " +e.getMessage());
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
            throw new DBException("Trouble with method insert! " +e.getMessage());
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

    /**
     * <p>This method forms the final query to the database
     * </p>
     * @param stmt is an object used for executing a static SQL statement and returning the results it produces.
     * @param filters  is an array of objects to replace wildcard characters in the query.
     *
     */
    private void addFilters(PreparedStatement stmt, Object[] filters) throws SQLException {
        if (filters!= null) {
            int step = 1;
            for (Object obj : filters) {
                if (obj instanceof String) {
                    stmt.setString(step, (String) obj);
                } else if (obj instanceof Date) {
                    Date date = (Date) obj;
                    java.sql.Timestamp sqlPackageDate = new Timestamp(date.getTime());
                    stmt.setTimestamp(step, sqlPackageDate);
                } else {
                    stmt.setInt(step, (Integer) obj);
                }
                step++;
            }
        }
    }

    private void close(AutoCloseable el) throws DBException {
        if (el != null) {
            try {
                el.close();
            } catch (Exception e) {
                throw new DBException("not close! ", e);
            }
        }
    }

    private void rollback(Connection con) throws DBException {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new DBException("rollback! ", ex);
            }
        }
    }

}
