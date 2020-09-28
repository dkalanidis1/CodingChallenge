package com.damianos.codingchallenge.service;

import java.util.List;

import com.damianos.codingchallenge.model.*;

public interface NasaDataServiceInterface {

    NasaData findById(int id);

    List<NasaData> findAll();

    void add(NasaData nasaData);

    List<HostsRank> get_top_10_requested_hosts();

    float get_successful_requests_perc();

    float get_unsuccessful_requests_perc();

    List<PagesRank> get_top_10_unsuccessful_requests();

    List<PagesRank> get_top_10_requested_pages();

    List<HostsTopPagesRank>  get_top_5_pages_of_top_10_hosts();

    List<MalformedLine>  get_malformed_lines();

    void delete(int id);

}



