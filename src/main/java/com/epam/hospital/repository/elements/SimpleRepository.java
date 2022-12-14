package com.epam.hospital.repository.elements;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Person;
import com.epam.hospital.model.SimpleModel;
import com.epam.hospital.model.Category;
import com.epam.hospital.repository.ConnectionPool;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.GlobalRepository;

import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleRepository extends GlobalRepository<SimpleModel> {
    private static SimpleRepository  simpleRepository;
    private static String classNameParam;
    private static String[] queries;
    private SimpleRepository() {
        //classNameParam =((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();

    }

    public static SimpleRepository getRepository(String className){
        if (simpleRepository==null)  {
            simpleRepository = new SimpleRepository();
            simpleRepository.classNameParam = className;
            simpleRepository.queries = Constants.getQueries(className);
            connectionPool = ConnectionPool.getInstance();
        }
        return simpleRepository;
    }

    public SimpleModel readByID(int id) throws DBException, SQLException {
        return (SimpleModel) simpleRepository.read(queries[0],id);
    }
    public List<SimpleModel> getAll() throws DBException, SQLException {
        return simpleRepository.findAll(queries[1]);
    }

    public boolean create(SimpleModel simpleModel) throws DBException {
        int idT = simpleRepository.insert(queries[2], simpleModel.toString());
        return idT >=0;
    }
    public boolean delete(SimpleModel simpleModel) throws DBException {
        return simpleRepository.delete(queries[3], simpleModel.toString());
    }
    @Override
    protected SimpleModel readByResultSet(ResultSet rs) throws SQLException {
        while(rs.next()){
            SimpleModel simpleModel = getSimpleInstance();
            simpleModel.setId(rs.getInt(1));
            return simpleModel;
        }
        return null;

    }

    public SimpleModel getSimpleInstance(){
        try {
            Class<?> clazz = Class.forName("com.epam.hospital.model."+classNameParam);
            SimpleModel  simpleModel= (SimpleModel) clazz.getConstructor().newInstance();
            return simpleModel;
        }
        catch( Exception e ) {
            return null;
        }

    }

    @Override
    protected List<SimpleModel> findByResultSet(ResultSet rs) throws SQLException {

        List<SimpleModel> list = new ArrayList<>();
        while(rs.next()){
            SimpleModel simpleModel = getSimpleInstance();
            simpleModel.setId(rs.getInt(1));
            simpleModel.setName(rs.getString(2));
            list.add(simpleModel);
        }
        return list;
    }


}
