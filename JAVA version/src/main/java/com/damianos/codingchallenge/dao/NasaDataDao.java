package com.damianos.codingchallenge.dao;

import java.util.List;

import com.damianos.codingchallenge.model.*;

public interface NasaDataDao {

    NasaData findById(int id);

    List<NasaData> findAll();

    List<HostsRank> get_top_10_requested_hosts();

    float get_successful_requests_perc();

    float get_unsuccessful_requests_perc();

    List<PagesRank> get_top_10_unsuccessful_requests();

    List<PagesRank> get_top_10_requested_pages();

    List<HostsTopPagesRank> get_top_5_pages_of_top_10_hosts();

    List<MalformedLine> get_malformed_lines();


    void add(NasaData nasaData);

    void save(NasaData nasaData);

    void update(NasaData nasaData);

    void delete(int id);

}