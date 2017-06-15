package mz.uem.inovacao.fiscaisapp.utils;

/**
 * Created by SaNogueira on 19/03/2015.
 */
public class QueryBuilder {

    private String params ;

    public QueryBuilder(){
        this.params = "";
    }

    public QueryBuilder(String key, String value){

        this.params = key+"%20%3D%20"+value+"" ;
    }

    public void addParam(String key, String value){

        this.params = this.params + key+"%20%3D%20"+value+"";
    }

    public String getParams() {
        return params;
    }
}
