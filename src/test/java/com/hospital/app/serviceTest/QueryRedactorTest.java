package com.hospital.app.serviceTest;
import com.hospital.app.repository.Fields;
import com.hospital.app.repository.QueryRedactor;
import com.hospital.app.repository.SortRule;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class QueryRedactorTest {

    @Test
    void testGetSelectionString() {
        Map<String,Object> selection = new HashMap<>();
        selection.put(Fields.PATIENT_ID,100);
        selection.put(Fields.DOCTOR_ID,50);
        QueryRedactor qr = QueryRedactor.getRedactor(selection);
        assertEquals(" where doctor_id = ? and patient_id = ?", qr.getQuery(""));
    }

    @Test
    void testGetFullQueryString() {
        Map<String,Object> selection = new HashMap<>();
        selection.put(Fields.PATIENT_ID,100);
        selection.put(Fields.DOCTOR_ID,50);
        QueryRedactor qr = QueryRedactor.getRedactor(selection, SortRule.NAME_DESC,new int[]{2,5});
        assertEquals(" where doctor_id = ? and patient_id = ? order by last_Name desc,first_Name desc limit 2,5", qr.getQuery(""));
    }

    @Test
    void testGetSelectionValuesNull() {
        QueryRedactor qr = QueryRedactor.getRedactor(null, SortRule.NAME_DESC,new int[]{2,5});
        assertNull(qr.getSelectionValues());
    }
}
