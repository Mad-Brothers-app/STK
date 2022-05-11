package com.example.busseatreserve;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class calcualtionTest {
    private salaryCalculation calculateSalary;
    @BeforeEach
    public void setup(){
        calculateSalary=new salaryCalculation();
    }

    @Test
    public void testSalary(){
        int result=0;
       result=testSalary(50000,4);
       assertEquals(48000,result);
    }
    @Test
    public void countLeave(){
        int result=test(7);
        assertEquals(7,result);

    }













    public int test(int count){
        int result=count;
        return count;
    }
    public int testSalary(int salary,int count){
        int result=salary-(count*500);
        return result;
    }

}
