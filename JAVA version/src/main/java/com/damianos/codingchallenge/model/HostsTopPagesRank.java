package com.damianos.codingchallenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;


@Entity
public class HostsTopPagesRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private String page;
    private String host;
    private String hits; 

    public HostsTopPagesRank() {}

    public HostsTopPagesRank(String page, String host,  String hits ) {
        this.page = page;
        this.host = host;
        this.hits = hits; 
    }
 

    public String getPage() {
		return page; 
	}

    public void setPage(String page) {
      this.page = page;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }
  

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      HostsTopPagesRank hostsTopPagesRank = (HostsTopPagesRank) o;
      return  Objects.equals(page, hostsTopPagesRank.page) &&
              Objects.equals(host, hostsTopPagesRank.host) &&
              Objects.equals(hits, hostsTopPagesRank.hits)  ;
    }

    @Override
    public int hashCode() {
      return Objects.hash(  page, host, hits );
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HostsTopPagesRank{"); 
        sb.append(", page='").append(page).append('\'');
        sb.append(", host='").append(host).append('\'');
        sb.append(", hits='").append(hits).append('\'');
        sb.append('}');
        return sb.toString();
    }

}