package com.hospital.app.service.impl;

import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.model.SimpleModel;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.repository.Constants;
import com.hospital.app.repository.QueryRedactor;
import com.hospital.app.repository.elements.SimpleRepository;
import com.hospital.app.service.Service;
import com.hospital.app.service.ValidatorUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleService implements Service<SimpleModel> {

    private SimpleRepository simpleRepository;
    private String classNameParam;

    public SimpleService() {
    }

    public static SimpleService getSimpleService(String className) {
        SimpleService simpleService = new SimpleService();
        simpleService.setClassNameParam(className);
        return simpleService;
    }

     public void setClassNameParam(String className){
         classNameParam = className;
         simpleRepository = SimpleRepository.getRepository(className);
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
        simpleRepository.setClassNameParam(classNameParam);
        return simpleRepository.getSize(qr);
    }

    @Override
    public List<SimpleModel> getAll() throws DBException, SQLException {
        simpleRepository.setClassNameParam(classNameParam);
        return simpleRepository.getAll();
    }

    public int getSize() throws DBException {
        simpleRepository.setClassNameParam(classNameParam);
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
