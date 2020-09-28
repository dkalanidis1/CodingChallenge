package com.damianos.codingchallenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;


@Entity
public class HostsRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private String host;
    private String hits; 

    public HostsRank() {}

    public HostsRank(String host, String hits ) {
        this.host = host;
        this.hits = hits; 
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
      HostsRank hostsRank = (HostsRank) o;
      return  Objects.equals(host, hostsRank.host) &&
              Objects.equals(hits, hostsRank.hits)  ;
    }

    @Override
    public int hashCode() {
      return Objects.hash(  host, hits );
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HostsRank{"); 
        sb.append(", host='").append(host).append('\'');
        sb.append(", hits='").append(hits).append('\'');
        sb.append('}');
        return sb.toString();
    }

}