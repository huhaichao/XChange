package info.bitrich.xchangestream.okcoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WebSocketMessage {
  private final String op;
  private final List<Object> args;

  public WebSocketMessage(
      @JsonProperty("op") String op, @JsonProperty("args") List<Object> args) {
    this.op = op;
    this.args = args;
  }

  public String getOp() {
    return op;
  }

  public List<Object> getArgs() {
    return args;
  }
}
