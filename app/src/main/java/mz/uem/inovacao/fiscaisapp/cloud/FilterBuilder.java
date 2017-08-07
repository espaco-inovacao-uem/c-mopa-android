package mz.uem.inovacao.fiscaisapp.cloud;

import android.util.Log;

/**
 * Created by MauroBanze on 7/20/17.
 * Cria uma sql where clause.
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

        sqlWhereClause += "(" + key + "%3D" + value + ")";
        return this;
    }

    //********  Condition relationships  *******

    public FilterBuilder and() {

        sqlWhereClause += "%20AND%20";
        return this;
    }

    public String getFilterString() {

        Log.v("Filter Builder", sqlWhereClause);
        return sqlWhereClause;
    }
}
