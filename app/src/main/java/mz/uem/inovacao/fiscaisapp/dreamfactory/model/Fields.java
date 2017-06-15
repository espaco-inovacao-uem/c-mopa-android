package mz.uem.inovacao.fiscaisapp.dreamfactory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Fields {
  /* An array of field definitions. */
  @JsonProperty("field")
  private List<FieldSchema> field = new ArrayList<FieldSchema>();
  public List<FieldSchema> getField() {
    return field;
  }
  public void setField(List<FieldSchema> field) {
    this.field = field;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Fields {\n");
    sb.append("  field: ").append(field).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

