package com.api.Timesheets.ExceptionHandlers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class GlobalException extends RuntimeException {

  @JsonSerialize(using = DateTimeSerializerBase.class)
  private String timestamp;

  private Integer status;
  private String error;
  private String exception;
  private String message;
  private String path;
  private Details details;

  public GlobalException(String message, Throwable cause) {
    super(message, cause);
  }

  public GlobalException(HttpStatus httpStatus, String message, Object... args) {
    this(httpStatus.value(), String.format(message, args));
  }

  public GlobalException(int httpCode, String message) {
    this.timestamp = new DateTime(DateTimeZone.UTC).toString();
    this.status = httpCode;
    this.error = HttpStatus.valueOf(httpCode).getReasonPhrase();
    this.exception = this.toString();
    this.message = message;
  }

  public GlobalException(int httpCode, String message, String objectType, Integer objectId) {
    this.timestamp = new DateTime(DateTimeZone.UTC).toString();
    this.status = httpCode;
    this.error = HttpStatus.valueOf(httpCode).getReasonPhrase();
    this.exception = this.toString();
    this.message = message;
    this.details = new Details(objectType, objectId);
  }

  public HttpStatus getHttpCode() {

    return HttpStatus.valueOf(this.status);
  }

  @Override
  public String getMessage() {
    return message;
  }

  public String getJSONMessage() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public class Details {

    private Integer objectId;
    private String objectType;

    public Details(String objectType, Integer objectId) {
      this.objectId = objectId;
      this.objectType = objectType;
    }
  }
}
