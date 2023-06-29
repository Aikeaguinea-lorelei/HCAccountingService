package com.hardcore.accounting.manager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class addManagerTest {
    private AddManager addManager = new AddManager();
    @Test
    public void testAddMethod(){
        // Arrange(预先准备好测试的工具数据,调用main里面被测试的代码,并new一个函数出来(6行))
        int number=100;

        // Act(执行我的测试)
        int result = addManager.add(number);

        // Assert(断言执行的结果和预期结果相同)
        assertEquals(101,result);
    }
}
