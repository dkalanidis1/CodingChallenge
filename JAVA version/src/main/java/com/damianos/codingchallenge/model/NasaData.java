package com.damianos.codingchallenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;


@Entity
public class NasaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String host;
    private String page;
    private int http_st_code;
    private int malformed_line;

    public NasaData() {}

    public NasaData(String host, String page,  int  http_st_code, int malformed_line) {
        this.host = host;
        this.page = page;
        this.http_st_code = http_st_code;
        this.malformed_line = malformed_line;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getHost() { return host; }

    public void setHost(String host) {
      this.host = host;
    }

    public String getPage() {
      return page;
    }

    public void setPage( String page) {
      this.page = page;
    }

    public int getHttp_st_code() {
      return http_st_code;
    }

    public void setHttp_st_code(int http_st_code) {
      this.http_st_code = http_st_code;
    }

    public int getMalformed_line() {
         return malformed_line;
     }

    public void setmMalformed_line(int city) {
         this.malformed_line = malformed_line;
     }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      NasaData nasadata = (NasaData) o;
      return Objects.equals(id, nasadata.id) &&
              Objects.equals(host, nasadata.host) &&
              Objects.equals(page, nasadata.page) &&
              Objects.equals(http_st_code, nasadata.http_st_code) &&
              Objects.equals(malformed_line, nasadata.malformed_line);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id, host, page, http_st_code, malformed_line);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NasaData{");
        sb.append("id=").append(id);
        sb.append(", host='").append(host).append('\'');
        sb.append(", page='").append(page).append('\'');
        sb.append(", http_st_code='").append(http_st_code).append('\'');
        sb.append(", malformed_line='").append(malformed_line).append('\'');
        sb.append('}');
        return sb.toString();
    }

}