package com.epam.hospital.repository.elements;
import com.epam.hospital.model.SimpleModel;
import com.epam.hospital.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleRepository extends GlobalRepository<SimpleModel> {
    private static SimpleRepository  simpleRepository;
    private String classNameParam;
    private String[] queries;
    private SimpleRepository() {
    }

    public static SimpleRepository getRepository(String className){

        simpleRepository = new SimpleRepository();
        connectionPool = ConnectionPool.getInstance();
        simpleRepository.setClassNameParam(className);

        return simpleRepository;
    }
    public  void setClassNameParam(String className){
        simpleRepository.classNameParam = className;
        simpleRepository.queries = Constants.getQueries(className);
    }



    public SimpleModel readByID(int id) throws DBException, SQLException {
        return simpleRepository.read(queries[0],id);
    }
    public List<SimpleModel> getAll() throws DBException, SQLException {
        return simpleRepository.findAll(queries[1]);
    }

    public boolean create(SimpleModel simpleModel) throws DBException {
        int id = simpleRepository.insert(queries[2], null,simpleModel.toString());
        return id >=0;
    }
    public boolean delete(SimpleModel simpleModel) throws DBException {
        return simpleRepository.delete(queries[3], null,simpleModel.toString());
    }
    @Override
    protected SimpleModel readByResultSet(ResultSet rs) throws SQLException {
        while(rs.next()){
            return getSimpleModel(rs);
        }
        return null;

    }


    @Override
    protected List<SimpleModel> findByResultSet(ResultSet rs) throws SQLException {

        List<SimpleModel> list = new ArrayList<>();
        while(rs.next()){
            list.add(getSimpleModel(rs));
        }
        return list;
    }

    private SimpleModel getSimpleModel(ResultSet rs) throws SQLException {
        SimpleModel simpleModel = RepositoryUtils.getSimpleInstance(classNameParam);
        simpleModel.setId(rs.getInt(Fields.ID));
        simpleModel.setName(rs.getString(Fields.NAME));
        return simpleModel;
    }


}
