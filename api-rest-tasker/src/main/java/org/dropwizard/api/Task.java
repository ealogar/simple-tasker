package org.dropwizard.api;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
public class Task {
  public static final String DATE_FORMAT_PATTERN="dd-MM-yyyy hh:mm";

  private Long id;

  @NotBlank(message = "Description may not be empty")
  private String description;

  // We just use this jackson annotation and leave jackson use reflection to
  // serialize/deserialize (not complicated names)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_PATTERN)
  @NotNull
  private Date dueDate;

  private boolean completed;

  // Jackson deserialize
  public Task() {}


  public Task(Long id, String description, Date dueDate) {
    this.id = id;
    this.description = description;
    this.dueDate = dueDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }


  public boolean isCompleted() {
    return completed;
  }


  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  

}
