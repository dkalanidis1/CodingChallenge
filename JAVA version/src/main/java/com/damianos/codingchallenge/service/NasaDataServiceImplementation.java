package com.damianos.codingchallenge.service;

import java.util.List;

import com.damianos.codingchallenge.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damianos.codingchallenge.dao.NasaDataDao;
import com.damianos.codingchallenge.model.NasaData;

@Service("nasadataService")
public class NasaDataServiceImplementation implements NasaDataServiceInterface {

    NasaDataDao NasaDataDao;

    @Autowired
    public void setNasaDataDao(NasaDataDao nasadataDao) {
        this.NasaDataDao = nasadataDao;
    }

    @Override
    public NasaData findById(int id) {
        return NasaDataDao.findById(id);
    }

    @Override
    public List<NasaData> findAll() {
        return NasaDataDao.findAll();
    }

    @Override
    public void add(NasaData nasadata) { NasaDataDao.add(nasadata); }


    @Override
    public List<HostsRank>  get_top_10_requested_hosts() { return NasaDataDao.get_top_10_requested_hosts(); }




    @Override
    public float get_successful_requests_perc() { return NasaDataDao.get_successful_requests_perc(); }

    @Override
    public float get_unsuccessful_requests_perc() {return NasaDataDao.get_unsuccessful_requests_perc(); }

    @Override
    public List<PagesRank>  get_top_10_unsuccessful_requests() { return NasaDataDao.get_top_10_unsuccessful_requests(); }

    @Override
    public List<PagesRank>  get_top_10_requested_pages() { return NasaDataDao.get_top_10_requested_pages(); }

    @Override
    public List<HostsTopPagesRank>  get_top_5_pages_of_top_10_hosts() { return NasaDataDao.get_top_5_pages_of_top_10_hosts(); }

    @Override
    public List<MalformedLine>  get_malformed_lines() { return NasaDataDao.get_malformed_lines(); }

    @Override
    public void delete(int id) {
        NasaDataDao.delete(id);
    }

}

