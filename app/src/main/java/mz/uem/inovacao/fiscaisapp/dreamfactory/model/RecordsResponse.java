package mz.uem.inovacao.fiscaisapp.dreamfactory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecordsResponse {
	//  /* Array of system user records. */
	//  @JsonProperty("record")
	//  private List<Object> record = new ArrayList<Object>();
	//  /* Array of metadata returned for GET requests. */
	//  @JsonProperty("meta")
	//  private Metadata meta = null;
	//  public List<Object> getRecord() {
	//    return record;
	//  }
	//  public void setRecord(List<Object> record) {
	//    this.record = record;
	//  }

	@JsonProperty("resource")
	  private List<Object> record;

	  @JsonProperty("meta")
	  private Metadata meta = null;
	  public List<Object> getRecord() {
	    return record;
	  }

	public void setRecord(List<Object> record) {
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
		sb.append("class RecordsResponse {\n");
		sb.append("  record: ").append(record).append("\n");
		sb.append("  meta: ").append(meta).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}

