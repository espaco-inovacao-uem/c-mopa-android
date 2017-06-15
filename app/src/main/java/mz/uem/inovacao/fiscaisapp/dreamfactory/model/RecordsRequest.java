package mz.uem.inovacao.fiscaisapp.dreamfactory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class RecordsRequest {
  /* Array of records. */
  @JsonProperty("record")
  private List<CloudObject> record = new ArrayList<CloudObject>();
  public List<CloudObject> getRecord() {
    return record;
  }
  public void setRecord(List<CloudObject> record) {
    this.record = record;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecordsRequest {\n");
    sb.append("  record: ").append(record).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

