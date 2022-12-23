package com.epam.hospital.repository.elements;

import com.epam.hospital.model.*;
import com.epam.hospital.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository extends GlobalRepository<Doctor> {
    private static DoctorRepository  doctorRepository;

    private DoctorRepository() {
    }

    public static DoctorRepository getRepository(){
        if (doctorRepository==null)  {
            doctorRepository = new DoctorRepository();
        }
        return doctorRepository;
    }

    public Doctor readByID(int id) throws DBException, SQLException {
        return doctorRepository.read(Constants.GET_DOCTOR_BY_ID,id);
    }
    public Doctor readByLogin(String login) throws DBException {
        return doctorRepository.read(Constants.GET_DOCTOR_BY_LOGIN,login);
    }
    public int getSize() throws DBException {
        return doctorRepository.readSize(Constants.GET_SIZE_DOCTOR);
    }

    public List<Doctor> getAllDoctors(int[] limit,String sortRule) throws DBException {

        return doctorRepository.findAll(Constants.GET_ALL_DOCTORS+getSortRule(sortRule)+(limit==null?"":" limit " +limit[0]+","+limit[1]));
    }

    private String getSortRule(String sortRule) {
        if (sortRule ==null||sortRule.equals("name asc"))
            return " order by last_Name,first_Name";
        else if (sortRule.equals("name desc"))
            return " order by last_Name desc,first_Name desc";
        else if (sortRule.equals("category asc"))
            return " order by category_name,last_Name,first_Name";
        else if (sortRule.equals("category desc"))
            return " order by category_name desc,last_Name,first_Name";
        else if (sortRule.equals("count patients asc"))
            return " order by count_patients,last_Name,first_Name";
        else if (sortRule.equals("count patients desc"))
            return " order by count_patients desc, last_Name, first_Name";
        return "";
    }

    public boolean create(Doctor doctor) throws DBException {

        Object[] objects = {doctor.getLastName(), doctor.getFirstName(), doctor.getCategory().getId(),
                doctor.getLogin(), doctor.getPassword(), Role.getID(doctor.getRole())};
            int idDoctor = doctorRepository.insert(Constants.ADD_DOCTOR,objects);
            return idDoctor >=0;
    }
    public boolean delete(Doctor doctor) throws DBException {
        return doctorRepository.delete(Constants.DELETE_DOCTOR,doctor.getId());
    }

    @Override
    protected Doctor readByResultSet(ResultSet rs) throws SQLException {
        while(rs.next()){
            return getDoctor(rs);
        }
        return null;
    }

    @Override
    protected List<Doctor> findByResultSet(ResultSet rs) throws SQLException {
        List<Doctor> list = new ArrayList<>();
        while(rs.next()){
            list.add(getDoctor(rs));
        }
        return list;
    }

    private Doctor getDoctor(ResultSet rs) throws SQLException {

        Doctor doctor = Doctor.createDoctor(rs.getString(Fields.LAST_NAME),
                rs.getString(Fields.FIRST_NAME),
                Category.createInstance(rs.getString(Fields.CATEGORY_NAME)));
        doctor.setPassword(rs.getString(Fields.PASSWORD));
        doctor.setLogin(rs.getString(Fields.LOGIN));
        doctor.setRole(Role.valueOf(rs.getString(Fields.ROLE_NAME).toUpperCase()));
        doctor.setId(rs.getInt(Fields.ID));
        return doctor;
    }


}
