package com.damianos.codingchallenge.model;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;


@SpringBootTest
public class HostsRankTests {

    @Autowired
    private HostsRank HostsRank1 = new HostsRank();

    @Test
    public void testGetHost() {
        HostsRank1.setHost("121.121.121.121");
        String result = HostsRank1.getHost();
        Assert.assertEquals("121.121.121.121", result);
    }

    @Test
    public void testGetHits() {
        HostsRank1.setHits("22");
        String result = HostsRank1.getHits();
        Assert.assertEquals("22", result);
    }


}