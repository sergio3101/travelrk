package ru.flystar.travelrk.tools;

import org.junit.Assert;
import org.junit.Test;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 10.04.2018.
 */
public class StringToolTest {
    @Test
    public void genPath() throws Exception {
        Assert.assertEquals(8,StringTool.genPath().length());
    }

    @Test
    public void genRandomLowerStr() throws Exception {
        String testResult = StringTool.genRandomLowerStr(10);
        Assert.assertEquals(10,testResult.length());
        String testResult1 = StringTool.genRandomLowerStr(0);
        Assert.assertEquals(0,testResult1.length());
        Assert.assertTrue(testResult1.isEmpty());
    }

    @Test
    public void redeableSize() throws Exception {
        Assert.assertEquals("0 B",StringTool.redeableSize(0));
        Assert.assertEquals("1 B",StringTool.redeableSize(1));
        Assert.assertEquals("100 B",StringTool.redeableSize(100));
        Assert.assertEquals("1 KB",StringTool.redeableSize(1024));
        Assert.assertEquals("1 MB",StringTool.redeableSize(1024000));
        Assert.assertEquals("1 GB",StringTool.redeableSize(1024000000));
    }
}