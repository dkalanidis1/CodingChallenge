package com.damianos.codingchallenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;


@Entity
public class PagesRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private String page;
    private String hits; 

    public PagesRank() {}

    public PagesRank(String page, String hits ) {
        this.page = page;
        this.hits = hits; 
    }
 

    public String getPage() {
		return page; 
	}

    public void setPage(String page) {
      this.page = page;
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
      PagesRank pagesRank = (PagesRank) o;
      return  Objects.equals(page, pagesRank.page) &&
              Objects.equals(hits, pagesRank.hits)  ;
    }

    @Override
    public int hashCode() {
      return Objects.hash(  page, hits );
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PagesRank{"); 
        sb.append(", page='").append(page).append('\'');
        sb.append(", hits='").append(hits).append('\'');
        sb.append('}');
        return sb.toString();
    }

}