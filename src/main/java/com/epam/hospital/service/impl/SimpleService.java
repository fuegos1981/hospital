package com.epam.hospital.service.impl;

import com.epam.hospital.model.SimpleModel;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.SimpleRepository;
import com.epam.hospital.service.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class SimpleService implements Service<SimpleModel> {
    private static SimpleService simpleService;
    private SimpleRepository simpleRepository;
    private SimpleService() {
        this.simpleRepository = simpleRepository.getRepository();
    }
    public static SimpleService getSimpleService(){
        return Objects.requireNonNullElseGet(simpleService, SimpleService::new);
    }
    @Override
    public boolean create(SimpleModel simpleModel) throws DBException {
        return simpleRepository.create(simpleModel);
    }

    @Override
    public SimpleModel readById(int id) throws DBException, SQLException {
        return simpleRepository.readByID(id);
    }

    @Override
    public boolean update(SimpleModel simpleModel) throws DBException {
        return simpleRepository.create(simpleModel);
    }

    @Override
    public void delete(SimpleModel simpleModel) throws DBException {
        simpleRepository.delete(simpleModel);
    }

    @Override
    public List<SimpleModel> getAll(String sortRule) throws DBException, SQLException {
        return simpleRepository.getAll();
    }
}
