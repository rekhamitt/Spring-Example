package com.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude = {"id", "owner"})
public class Script implements Serializable {

  private static final long serialVersionUID = -4271794105905772564L;

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;
  private String content;
  private String description;
  private String code;

  @JsonIgnore
  @ManyToOne
  private AuthUser owner;

}
