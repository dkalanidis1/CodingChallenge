package com.damianos.codingchallenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;


@Entity
public class MalformedLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private String linenum;  

    public MalformedLine() {}

    public MalformedLine(String linenum  ) {
        this.linenum = linenum; 
    }
 

    public String getLine() {
		return linenum; 
	}

    public void setLine(String linenum) {
      this.linenum = linenum;
    }
  

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      MalformedLine malformedLine = (MalformedLine) o;
      return  Objects.equals(linenum, malformedLine.linenum) ;
    }

    @Override
    public int hashCode() {
      return Objects.hash(  linenum );
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MalformedLine{"); 
        sb.append(", linenum='").append(linenum).append('\''); 
        sb.append('}');
        return sb.toString();
    }

}