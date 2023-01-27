package com.epam.hospital.service.impl;

import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.model.SimpleModel;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.QueryRedactor;
import com.epam.hospital.repository.elements.SimpleRepository;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.ValidatorUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleService implements Service<SimpleModel> {

    private SimpleRepository simpleRepository;
    private String classNameParam;

    private SimpleService() {
    }

    public static SimpleService getSimpleService(String className) {

        SimpleService simpleService = new SimpleService();
        simpleService.classNameParam = className;
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
        if (id == null)
            return null;
        simpleRepository.setClassNameParam(classNameParam);
        return simpleRepository.readByID(id);
    }

    @Override
    public boolean update(SimpleModel simpleModel) throws DBException, ValidateException {
        checkSimple(simpleModel);
        return simpleRepository.updateSimple(simpleModel);
    }

    @Override
    public void delete(SimpleModel simpleModel) throws DBException, SQLException {
        Map<String,Object> selection = new HashMap<>();
        if (classNameParam.equals(Constants.CATEGORY)){
            selection.put("category.name", simpleModel.getName());
            if (DoctorService.getDoctorService().getAll(QueryRedactor.getRedactor(selection)).size()>0){
                throw new DBException("Doctor use this category!");
            }
        }
        if (classNameParam.equals(Constants.DIAGNOSIS)){
            selection.put("diagnosis_id", simpleModel.getId());
            if (AppointmentService.getAppointmentService().getAll(QueryRedactor.getRedactor(selection)).size()>0){
                throw new DBException("Appointment use this diagnosis!");
            }
        }
        simpleRepository.delete(simpleModel);
    }

    @Override
    public List<SimpleModel> getAll(QueryRedactor qr) throws DBException, SQLException {
        simpleRepository.setClassNameParam(classNameParam);
        return simpleRepository.getAll(qr);
    }

    public int getSize(QueryRedactor qr) throws DBException {
        return simpleRepository.getSize(qr);
    }

    @Override
    public List<SimpleModel> getAll() throws DBException, SQLException {
        simpleRepository.setClassNameParam(classNameParam);
        return simpleRepository.getAll();
    }

    public int getSize() throws DBException {
        return simpleRepository.getSize();
    }

    private void checkSimple(SimpleModel simpleModel) throws ValidateException, DBException {
        ValidatorUtils.nameValidate(ControllerConstants.NAME, simpleModel.getName());
        SimpleModel sm = simpleRepository.readByName(simpleModel.getName());
        if (sm!= null) {
            if ((simpleModel.getId() == 0||simpleModel.getId() != sm.getId())) {
                throw new ValidateException("duplicate_name");
            }
        }

    }
}
