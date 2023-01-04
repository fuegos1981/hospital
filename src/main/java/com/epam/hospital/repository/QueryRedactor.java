package com.epam.hospital.repository;

import java.util.Map;
import java.util.stream.Collectors;

public class QueryRedactor {
    private  String query;
    Map<String, Integer> selection;
    SortRule sortRule;
    private  int[] limit;

    public  static QueryRedactor getRedactor(String query,Map<String, Integer> selection,SortRule sortRule, int[] limit) {
        QueryRedactor qr = new QueryRedactor();
        qr.query = query;
        qr.selection= selection;
        qr.sortRule = sortRule;
        qr.limit = limit;
        return  qr;
    }
    public  static QueryRedactor getRedactor(String query,Map<String, Integer> selection) {
        QueryRedactor qr = new QueryRedactor();
        qr.query = query;
        qr.selection= selection;
        return  qr;
    }

    public  static QueryRedactor getRedactor(String query,SortRule sortRule, int[] limit) {
        QueryRedactor qr = new QueryRedactor();
        qr.query = query;
        qr.sortRule = sortRule;
        qr.limit = limit;
        return  qr;
    }

    public  String getQuery() {
        return  query
                +getSelectionString(selection)
                +(sortRule==null?"":sortRule.getQuery())
                +(limit==null?"":" limit " +limit[0]+","+limit[1]);
    }

    private static String getSelectionString(Map<String, Integer> selection){
        if (selection == null||selection.size()==0)
            return "";
        return  " where "+selection.keySet().stream()
                .map(i -> i+" = ?")
                .collect(Collectors.joining(" and "));
    }

}
