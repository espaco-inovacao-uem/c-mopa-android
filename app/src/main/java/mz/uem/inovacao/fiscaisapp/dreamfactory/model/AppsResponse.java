package mz.uem.inovacao.fiscaisapp.dreamfactory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AppsResponse {
  /* Array of system application records. */
  @JsonProperty("record")
  private List<AppResponse> record = new ArrayList<AppResponse>();
  /* Array of metadata returned for GET requests. */
  @JsonProperty("meta")
  private Metadata meta = null;
  public List<AppResponse> getRecord() {
    return record;
  }
  public void setRecord(List<AppResponse> record) {
    this.record = record;
  }

  public Metadata getMeta() {
    return meta;
  }
  public void setMeta(Metadata meta) {
    this.meta = meta;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppsResponse {\n");
    sb.append("  record: ").append(record).append("\n");
    sb.append("  meta: ").append(meta).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

