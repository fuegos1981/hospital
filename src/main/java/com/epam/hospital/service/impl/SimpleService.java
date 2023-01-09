package com.epam.hospital.service.impl;

import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.model.SimpleModel;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.SortRule;
import com.epam.hospital.repository.elements.SimpleRepository;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.ValidatorUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SimpleService implements Service<SimpleModel> {

    private SimpleRepository simpleRepository;
    private String classNameParam;
    private SimpleService() {
    }
    public static SimpleService getSimpleService(String className){

        SimpleService simpleService = new SimpleService();
        simpleService.classNameParam =className;
        simpleService.simpleRepository = SimpleRepository.getRepository(className);

        return simpleService;
    }

    @Override
    public boolean create(SimpleModel simpleModel) throws DBException, ValidateException {
        checkSimple(simpleModel);
        simpleRepository.setClassNameParam(classNameParam);
        return simpleRepository.create(simpleModel);
    }

    @Override
    public SimpleModel readById(Integer id) throws DBException, SQLException, ValidateException {
        if (id==null)
            return null;
        simpleRepository.setClassNameParam(classNameParam);
        return simpleRepository.readByID(id);
    }

    @Override
    public boolean update(SimpleModel simpleModel) throws DBException, ValidateException {
        checkSimple(simpleModel);
        return simpleRepository.create(simpleModel);
    }

    @Override
    public void delete(SimpleModel simpleModel) throws DBException {
        simpleRepository.delete(simpleModel);
    }

    @Override
    public List<SimpleModel> getAll(Map<String, Object> selection, SortRule sortRule, int[] limit) throws DBException, SQLException {
        simpleRepository.setClassNameParam(classNameParam);
        return simpleRepository.getAll();
    }
    public int getSize(Map<String, Object> selection) throws DBException {
        return simpleRepository.getSize();
    }

    private void checkSimple(SimpleModel simpleModel) throws ValidateException, DBException {
        ValidatorUtils.nameValidate(ControllerConstants.NAME, simpleModel.getName());
        if (simpleRepository.readByName(simpleModel.getName())!=null){
            throw new ValidateException("duplicate_name");
        }

    }
}
