package mz.uem.inovacao.fiscaisapp.dreamfactory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class RecordResponse {
    /* Array of field name-value pairs. */
    @JsonProperty("_field_")
    private List<String> _field_ = new ArrayList<String>();

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int code;

    @JsonProperty("complete")
    private boolean complete;

    public boolean isComplete() {
        return complete;
    }

    public List<String> get_field_() {
        return _field_;
    }
    public void set_field_(List<String> _field_) {
        this._field_ = _field_;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setComplete(boolean complete){
        this.complete = complete;
    }

    public String getName(){
        return name;
    }



    /*public boolean iscomplete(){
        return complete;
    }*/

    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class RecordResponse {\n");
        sb.append("  _field_: ").append(_field_).append("\n");
        sb.append("  name: ").append(name).append("\n");
        sb.append("  id: ").append(code).append("\n");
        sb.append("  complete: ").append(complete).append("\n");
//        sb.append("  userName: ").append(getUsername()).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}

