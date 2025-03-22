package com.hospital.app.repository.elements;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.model.SimpleModel;
import com.hospital.app.repository.Constants;
import com.hospital.app.repository.Fields;
import com.hospital.app.repository.GlobalRepository;
import com.hospital.app.repository.QueryRedactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleRepository extends GlobalRepository<SimpleModel> {
    private static SimpleRepository simpleRepository;
    private String classNameParam;
    private String[] queries;

    private SimpleRepository() {
    }

    public static SimpleRepository getRepository(String className) {

        simpleRepository = new SimpleRepository();
        simpleRepository.setClassNameParam(className);

        return simpleRepository;
    }

    public void setClassNameParam(String className) {
        simpleRepository.classNameParam = className;
        simpleRepository.queries = Constants.getQueries(className);
    }

    public SimpleModel readByID(int id) throws DBException, SQLException {
        return simpleRepository.read(queries[0], id);
    }

    public List<SimpleModel> getAll(QueryRedactor qr) throws DBException {
        return simpleRepository.findAll(qr.getQuery(queries[1]),
                qr.getSelectionValues());
    }

    public int getSize(QueryRedactor qr) throws DBException {
        return simpleRepository.readSize(qr.getQuery(queries[4]),
                qr.getSelectionValues());
    }

    public List<SimpleModel> getAll() throws DBException, SQLException {
        return simpleRepository.findAll(queries[1]);
    }

    public boolean create(SimpleModel simpleModel) throws DBException {
        int id = simpleRepository.insert(queries[2], simpleModel.toString());
        return id >= 0;
    }

    public boolean delete(SimpleModel simpleModel) throws DBException {
        return simpleRepository.delete(queries[3], simpleModel.toString());
    }

    public SimpleModel readByName(String name) throws DBException {
        return simpleRepository.read(queries[5], name);
    }

    public boolean updateSimple(SimpleModel simpleModel) throws DBException {
        Object[] objects = {simpleModel.getName(), simpleModel.getId()};
        return simpleRepository.update(queries[6], objects);
    }

    @Override
    protected SimpleModel readByResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return getSimpleModel(rs);
        }
        return null;

    }

    public int getSize() throws DBException {
        return simpleRepository.readSize(queries[4]);
    }


    @Override
    protected List<SimpleModel> findByResultSet(ResultSet rs) throws SQLException {

        List<SimpleModel> list = new ArrayList<>();
        while (rs.next()) {
            list.add(getSimpleModel(rs));
        }
        return list;
    }

    private SimpleModel getSimpleModel(ResultSet rs) throws SQLException {
        SimpleModel simpleModel = SimpleModel.getSimpleInstance(classNameParam);
        simpleModel.setId(rs.getInt(Fields.ID));
        simpleModel.setName(rs.getString(Fields.NAME));
        return simpleModel;
    }


}
