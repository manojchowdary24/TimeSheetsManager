package com.api.Timesheets.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDTO {

  private String from;
  private String recepient;
  private String subject;
  private String content;


}
