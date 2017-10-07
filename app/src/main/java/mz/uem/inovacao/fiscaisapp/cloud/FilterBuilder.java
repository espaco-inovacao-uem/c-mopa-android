package mz.uem.inovacao.fiscaisapp.cloud;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by MauroBanze on 7/20/17.
 * Cria uma sql where clause para ser passada por parametro ao campo "filter" do Dreamfactory.
 * Vai ser concatenada num url portanto deve conter caracteres compactiveis. Fluent interface
 */

public class FilterBuilder {

    private String sqlWhereClause;

    public FilterBuilder() {
        sqlWhereClause = "";
    }

    public FilterBuilder(String key, String value) {
        sqlWhereClause = "";
        equalTo(key, value);

    }

    //******** Condition creators ***************

    public FilterBuilder equalTo(String key, String value) {

        //sqlWhereClause += "(" + key + "%3D" + value + ")";
        appendCondition(key, value, "%3D");// key = value
        return this;
    }

    public FilterBuilder bigger(String key, String value) {

        appendCondition(key, value, "%3E"); // key >= value
        return this;

    }

    public FilterBuilder biggerOrEquals(String key, String value) {

        appendCondition(key, value, "%3E%3D"); // key >= value
        return this;

    }

    public FilterBuilder smaller(String key, String value) {

        appendCondition(key, value, "%3C"); // key <= value
        return this;
    }

    public FilterBuilder smallerOrEquals(String key, String value) {

        appendCondition(key, value, "%3C%3D"); // key <= value
        return this;
    }

    public FilterBuilder equalToOr(String key, ArrayList<String> values) {

        for (int i = 0; i < values.size(); i++) {

            equalTo(key, values.get(i));

            if (i != values.size() - 1) {// if not reached last element of array

                or();// add OR operator
            }

        }

        return this;
    }

    private void appendCondition(String key, String value, String operator) {

        sqlWhereClause += "(" + key + operator + value + ")";
    }

    //********  Condition relationships  *******

    public FilterBuilder and() {

        sqlWhereClause += "%20AND%20";
        return this;
    }

    public FilterBuilder or() {

        sqlWhereClause += "%20OR%20";
        return this;
    }

    //********* Create sublevels of conditions using ()*********

    public FilterBuilder open() {

        sqlWhereClause += "(";// (
        return this;
    }


    public FilterBuilder close() {

        sqlWhereClause += ")";// (
        return this;
    }


    public String build() {

        Log.v("Filter Builder", sqlWhereClause);
        return sqlWhereClause;
    }
}
