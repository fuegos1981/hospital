package com.epam.hospital.repository;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used to form the final query to the database.
 *
 * @author Sinkevych Olena
 */
public class QueryRedactor {
    private Map<String, Object> selection;
    private SortRule sortRule;
    private int[] limit;

    /**
     * <p>This method is used to receive object QueryRedactor
     * </p>
     *
     * @param selection is restriction map for the list of records in the database.
     * @param sortRule  is a query part for sorting records.
     * @param limit     is an array that contains the start number of the record in the database and the number of records to filter.
     */
    public static QueryRedactor getRedactor(Map<String, Object> selection, SortRule sortRule, int[] limit) {
        QueryRedactor qr = new QueryRedactor();
        qr.selection = selection;
        qr.sortRule = sortRule;
        qr.limit = limit;
        return qr;
    }

    /**
     * <p>This method is used to receive object QueryRedactor
     * </p>
     *
     * @param sortRule is a query part for sorting records.
     * @param limit    is an array that contains the start number of the record in the database and the number of records to filter.
     */
    public static QueryRedactor getRedactor(SortRule sortRule, int[] limit) {
        QueryRedactor qr = new QueryRedactor();
        qr.sortRule = sortRule;
        qr.limit = limit;
        return qr;
    }

    /**
     * <p>This method is used to receive object QueryRedactor
     * </p>
     *
     * @param sortRule is a query part for sorting records.
     */
    public static QueryRedactor getRedactor(SortRule sortRule) {
        QueryRedactor qr = new QueryRedactor();
        qr.sortRule = sortRule;
        return qr;
    }

    /**
     * <p>This method is used to receive object QueryRedactor
     * </p>
     *
     * @param selection is restriction map for the list of records in the database.
     */
    public static QueryRedactor getRedactor(Map<String, Object> selection) {
        QueryRedactor qr = new QueryRedactor();
        qr.selection = selection;
        return qr;
    }

    /**
     * <p>This method return final query to the database.
     * </p>
     *
     * @param query is query to database.
     */
    public String getQuery(String query) {
        return query
                + getSelectionString(selection)
                + (sortRule == null ? "" : sortRule.getQuery())
                + (limit == null ? "" : " limit " + limit[0] + "," + limit[1]);
    }

    /**
     * <p>This method return part of query based on selection.
     * </p>
     *
     * @param selection is restriction map for the list of records in the database.
     */
    private static String getSelectionString(Map<String, Object> selection) {
        if (selection == null || selection.size() == 0)
            return "";
        return " where " + selection.keySet().stream()
                .map(i -> i + " = ?")
                .collect(Collectors.joining(" and "));
    }

    /**
     * <p>This method return array of values from selection.
     * </p>
     */
    public Object[] getSelectionValues() {
        return selection == null ? null : selection.values().toArray();
    }
}
