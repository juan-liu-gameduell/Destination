package com.liujuan.destination.utl;

import com.liujuan.destination.vo.City;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Administrator on 2016/9/13.
 */
public class ReaderAndWriterCityUtilTest {

    @Test
    public void mergeFavoriteCities_isCorrect() throws Exception {
        ArrayList<City> old = new ArrayList<>();
        old.add(new City("a", "1"));
        old.add(new City("b", "2"));
        old.add(new City("c", "3"));
        old.add(new City("d", "4"));

        Set<String> newids = new HashSet<>();
        newids.add("3");
        newids.add("4");
        newids.add("5");
        newids.add("6");
        newids.add("7");
        ReaderAndWriterCityUtil.mergeFavoriteCities(old, newids);
        assertEquals(old.size(), 2);
        assertEquals(newids.size(), 3);
    }
}
