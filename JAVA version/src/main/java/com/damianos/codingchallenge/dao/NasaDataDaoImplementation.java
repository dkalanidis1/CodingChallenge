package com.damianos.codingchallenge.dao;

import com.damianos.codingchallenge.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class NasaDataDaoImplementation implements NasaDataDao {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public NasaData findById(int id) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        String sql = "SELECT * FROM log_data WHERE id=:id";
        NasaData result = null;
        try {
            result = namedParameterJdbcTemplate
                    .queryForObject(sql, params, new NasaDataMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing, return null
        }
        return result;
    }

    @Override
    public List<NasaData> findAll() {
        String sql = "SELECT * FROM log_data";
        List<NasaData> result = namedParameterJdbcTemplate.query(sql, new NasaDataMapper());
        return result;
    }

    @Override
    public List<HostsRank> get_top_10_requested_hosts() {
        String sql = "	SELECT host, COUNT(host) as hits FROM log_data WHERE malformed_line = 1 GROUP by host ORDER BY COUNT(host) DESC LIMIT 10;	";
        List<HostsRank> result = namedParameterJdbcTemplate.query(sql, new HostsRankMapper());
        return result;
    }


    @Override
    public float get_successful_requests_perc() {
        String sql = "";
        int percent = 0;
        int successful  = 0;
        int total = 0;
        Map<String, Object> params = new HashMap<String, Object>();
        NasaData result = null;
        sql = "SELECT COUNT(http_st_code) as counter FROM log_data WHERE http_st_code >= 200 and http_st_code <400 and malformed_line = 1;";
        successful  = namedParameterJdbcTemplate.queryForObject(sql, params, new NasaDataMapperCount());
        sql = "SELECT COUNT(http_st_code) as counter FROM log_data WHERE  malformed_line = 1;";
        total  = namedParameterJdbcTemplate.queryForObject(sql, params, new NasaDataMapperCount());
        return ( (float) successful / total);
    }



    @Override
    public float get_unsuccessful_requests_perc() {
        String sql = "";
        int percent = 0;
        int unsuccessful  = 0;
        int total = 0;
        Map<String, Object> params = new HashMap<String, Object>();
        NasaData result = null;
        sql = "SELECT COUNT(http_st_code) as counter FROM `log_data` WHERE http_st_code < 200 or http_st_code >=400 and malformed_line = 1;";
        unsuccessful  = namedParameterJdbcTemplate.queryForObject(sql, params, new NasaDataMapperCount());
        sql = "SELECT COUNT(http_st_code) as counter FROM log_data WHERE  malformed_line = 1;";
        total  = namedParameterJdbcTemplate.queryForObject(sql, params, new NasaDataMapperCount());
        return ( (float) unsuccessful / total);
    }

    @Override
    public List<PagesRank> get_top_10_unsuccessful_requests() {
        String sql = "	SELECT page, COUNT(page) as hits FROM log_data WHERE  http_st_code < 200 or http_st_code >=400  and malformed_line = 1 GROUP by page ORDER BY COUNT(page) DESC LIMIT 10;	";
        List<PagesRank> result = namedParameterJdbcTemplate.query(sql, new PagesRankMapper());
        return result;
    }


    @Override
    public List<PagesRank> get_top_10_requested_pages() {
        String sql = "	SELECT page, COUNT(page) as hits FROM log_data WHERE malformed_line = 1 GROUP by page ORDER BY COUNT(page) DESC LIMIT 10;	";
        List<PagesRank> result = namedParameterJdbcTemplate.query(sql, new PagesRankMapper());
        return result;
    }


    @Override
    public List<HostsTopPagesRank>  get_top_5_pages_of_top_10_hosts() {
        String sql = "	SELECT host, COUNT(host) as hits FROM log_data WHERE malformed_line = 1 GROUP by host ORDER BY COUNT(host) DESC LIMIT 10;	";
        List<HostsRank> result = namedParameterJdbcTemplate.query(sql, new HostsRankMapper());
        List<HostsTopPagesRank> result2 = new  ArrayList<HostsTopPagesRank>();
        List<HostsTopPagesRank> fulllist = new  ArrayList<HostsTopPagesRank>();
        String current_host = "";

        Iterator it=result.iterator();
        while(it.hasNext())
        {
            HostsRank e=(HostsRank)it.next();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("host", e.getHost());
            current_host = e.getHost();

            sql = "SELECT page,host ,COUNT(page) as hits FROM log_data WHERE malformed_line = 1 and host =:host GROUP by page,host ORDER BY COUNT(page) DESC LIMIT 5;";
            result2  = namedParameterJdbcTemplate.query(sql, params, new HostsTopPagesRankMapper());


            Iterator it2=result2.iterator();
            while(it2.hasNext())
            {

                HostsTopPagesRank z=(HostsTopPagesRank)it2.next();
                fulllist.add(z);
               System.out.println(" " + z.getPage() + " " + z.getHost() + " "+z.getHits() + " ");
            }
        }
        return fulllist;
    }



    public List<MalformedLine> get_malformed_lines() {
        String sql = "	SELECT malformed_line_number FROM log_data WHERE malformed_line = 0   ORDER BY malformed_line_number ASC;	";
        List<MalformedLine> result = namedParameterJdbcTemplate.query(sql, new MalformedLineMapper());
        return result;
    }



    @Override
    public void add(NasaData nasaData) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO log_data (host, page,http_st_code,malformed_line) "
          + "VALUES ( NULL, :host, :page, :http_st_code, :malformed_line)";
        namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(nasaData), keyHolder);
        nasaData.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void save(NasaData nasaData) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO log_data (host, page,http_st_code,malformed_line) "
                + "VALUES ( NULL, :host, :page, :http_st_code, :malformed_line)";
        namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(nasaData), keyHolder);
        nasaData.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(NasaData nasaData) {
        String sql = "UPDATE USER SET  first_name=:firstName, email=:email, "
                + "city=:city, nbateamid=:nbateamid  WHERE id=:id";
        namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(nasaData));
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM USER WHERE id= :id";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    private SqlParameterSource getSqlParameterByModel(NasaData nasaData) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", nasaData.getId());
        paramSource.addValue("firstName", nasaData.getHost());
        paramSource.addValue("email", nasaData.getPage());
        paramSource.addValue("city", nasaData.getHttp_st_code());
        paramSource.addValue("nbateamid", nasaData.getMalformed_line());
        return paramSource;
    }

    private static final class NasaDataMapper implements RowMapper<NasaData> {
        public NasaData mapRow(ResultSet rs, int rowNum) throws SQLException {
            NasaData nasaData = new NasaData();
            nasaData.setId(rs.getInt("id"));
            nasaData.setHost(rs.getString("host"));
            nasaData.setPage(rs.getString("page"));
            nasaData.setHttp_st_code(rs.getInt("http_st_code"));
            nasaData.setmMalformed_line(rs.getInt("malformed_line"));
            return nasaData;
        }
    }

    public static final class  NasaDataMapperCount implements RowMapper<Integer> {

        public Integer mapRow(ResultSet rs, int rownumber) throws SQLException
        {
            int e;
            e = rs.getInt("counter");
            return e;
        }
    }

    public static final class  HostsRankMapper implements RowMapper<HostsRank> {

        public HostsRank mapRow(ResultSet rs, int rownumber) throws SQLException
        {
            HostsRank hostsRank = new HostsRank();
            hostsRank.setHost(rs.getString("host"));
            hostsRank.setHits(rs.getString("hits"));
            return hostsRank;
        }
    }


    public static final class  PagesRankMapper implements RowMapper<PagesRank> {

        public PagesRank mapRow(ResultSet rs, int rownumber) throws SQLException
        {
            PagesRank pagesRank = new PagesRank();
            pagesRank.setPage(rs.getString("page"));
            pagesRank.setHits(rs.getString("hits"));
            return pagesRank;
        }
    }

    public static final class  HostsTopPagesRankMapper implements RowMapper<HostsTopPagesRank> {

        public HostsTopPagesRank mapRow(ResultSet rs, int rownumber) throws SQLException
        {
            HostsTopPagesRank hostsTopPagesRank = new HostsTopPagesRank();
            hostsTopPagesRank.setPage(rs.getString("page"));
            hostsTopPagesRank.setHost(rs.getString("host"));
            hostsTopPagesRank.setHits(rs.getString("hits"));
            return hostsTopPagesRank;
        }
    }



    public static final class MalformedLineMapper implements RowMapper<MalformedLine> {

        public MalformedLine mapRow(ResultSet rs, int rownumber) throws SQLException
        {
            MalformedLine malformedLine = new MalformedLine();
            malformedLine.setLine(rs.getString("malformed_line_number"));
            return malformedLine;
        }
    }


    private static List<String> convertDelimitedStringToList(String delimitedString) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isEmpty(delimitedString)) {
            result = Arrays.asList(StringUtils.delimitedListToStringArray(delimitedString, ","));
        }
        return result;
    }

    private String convertListToDelimitedString(List<String> list) {
        String result = "";
        if (list != null) {
            result = StringUtils.arrayToCommaDelimitedString(list.toArray());
        }
        return result;
    }
}