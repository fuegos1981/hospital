package com.hospital.app.controller.commands;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ActionCommand;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.ControllerUtils;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.model.SimpleModel;
import com.hospital.app.repository.QueryRedactor;
import com.hospital.app.repository.SortRule;
import com.hospital.app.service.Service;
import com.hospital.app.service.impl.SimpleService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * The class implements working with catalogs Category and Diagnosis
 *Please see the {@link Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class SimpleCommand implements ActionCommand {
    private final static SimpleService categoryService= SimpleService.getSimpleService("Category");
    private final static SimpleService diagnosisService= SimpleService.getSimpleService("Diagnosis");
    private final static String CURRENT_PAGE_CATEGORY = "current_page_category";
    private final static String CURRENT_PAGE_DIAGNOSIS = "current_page_diagnosis";
    private final static String COUNT_PAGE_CATEGORY = "count_page_category";
    private final static String COUNT_PAGE_DIAGNOSIS = "count_page_diagnosis";

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException, ParseException {
        fillCategories(request);
        fillDiagnosis(request);
        return ControllerConstants.PAGE_SIMPLE;
    }

    private void fillCategories(HttpServletRequest request) throws DBException, SQLException {

        int[] limit = ControllerUtils.setMasForPagination(request, categoryService.getSize(),CURRENT_PAGE_CATEGORY,COUNT_PAGE_CATEGORY);
        List<SimpleModel> categories =categoryService.getAll(QueryRedactor.getRedactor(SortRule.NAME_SIMPLE_ASC,limit));
        request.setAttribute(ControllerConstants.CATEGORIES,categories);

    }

    private void fillDiagnosis(HttpServletRequest request) throws DBException, SQLException {
        int[] limit = ControllerUtils.setMasForPagination(request, diagnosisService.getSize(),CURRENT_PAGE_DIAGNOSIS,COUNT_PAGE_DIAGNOSIS);
        List<SimpleModel> diagnosises =diagnosisService.getAll(QueryRedactor.getRedactor(SortRule.NAME_SIMPLE_ASC,limit));
        request.setAttribute(ControllerConstants.DIAGNOSISES,diagnosises);
    }
}
