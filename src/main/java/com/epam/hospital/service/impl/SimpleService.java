package com.epam.hospital.service.impl;

import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.model.SimpleModel;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.SimpleRepository;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.ServiceUtils;

import java.sql.SQLException;
import java.util.List;

public class SimpleService implements Service<SimpleModel> {
    //private SimpleService simpleService;
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
        ServiceUtils.nameValidate(ControllerConstants.NAME, simpleModel.getName());
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
        ServiceUtils.nameValidate(ControllerConstants.NAME, simpleModel.getName());
        return simpleRepository.create(simpleModel);
    }

    @Override
    public void delete(SimpleModel simpleModel) throws DBException {
        simpleRepository.delete(simpleModel);
    }

    @Override
    public List<SimpleModel> getAll(int[] limit, String sortRule) throws DBException, SQLException {
        simpleRepository.setClassNameParam(classNameParam);
        return simpleRepository.getAll();
    }
    public int getSize() throws DBException {
        return simpleRepository.getSize();
    }
}
