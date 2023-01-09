package com.epam.hospital.repository;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used to form the final query to the database.
 *
 * @author Sinkevych Olena
 *
 */
public class QueryRedactor {
    private  String query;
    Map<String, Object> selection;
    SortRule sortRule;
    private  int[] limit;

    /**
     * <p>This method is used to receive object QueryRedactor
     * </p>
     * @param query  is query to database.
     * @param selection is restriction map for the list of records in the database.
     * @param sortRule is a query part for sorting records.
     * @param limit is an array that contains the start number of the record in the database and the number of records to filter.
     *
     */
    public  static QueryRedactor getRedactor(String query,Map<String, Object> selection,SortRule sortRule, int[] limit) {
        QueryRedactor qr = new QueryRedactor();
        qr.query = query;
        qr.selection= selection;
        qr.sortRule = sortRule;
        qr.limit = limit;
        return  qr;
    }

    /**
     * <p>This method is used to receive object QueryRedactor
     * </p>
     * @param query  is query to database.
     * @param selection is restriction map for the list of records in the database.
     *
     */
    public  static QueryRedactor getRedactor(String query,Map<String, Object> selection) {
        QueryRedactor qr = new QueryRedactor();
        qr.query = query;
        qr.selection= selection;
        return  qr;
    }

    /**
     * <p>This method return final query to the database.
     * </p>
     *
     */
    public  String getQuery() {
        return  query
                +getSelectionString(selection)
                +(sortRule==null?"":sortRule.getQuery())
                +(limit==null?"":" limit " +limit[0]+","+limit[1]);
    }

    private static String getSelectionString(Map<String, Object> selection){
        if (selection == null||selection.size()==0)
            return "";
        return  " where "+selection.keySet().stream()
                .map(i -> i+" = ?")
                .collect(Collectors.joining(" and "));
    }

}
