package utils;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import model.FileReport;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartDemo {
//    public static void main(String[] args) {
//        //TODO: Add code to generate PDFs with charts
//    }
	
	public void checking(ArrayList<FileReport> f1)
	{
//		if()
	}
 
    public static JFreeChart generatePieChart(ArrayList<FileReport> f1) {
        DefaultPieDataset dataSet = new DefaultPieDataset();
//        for(int i=0;i<f1.size();i++)
//        {	
//        	dataSet.setValue(f1.get(i).getFileName(), 2);
//        }
        dataSet.setValue("timetable1", 2);
        dataSet.setValue("timetable2", 1);
        
 
        JFreeChart chart = ChartFactory.createPieChart(
                "File Statistics", dataSet, true, true, false);
 
        return chart;
    }
 
    public static JFreeChart generateBarChart(ArrayList<FileReport> reports) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
         
        Hashtable<String,Integer> dict = new Hashtable<String, Integer>();
        for (int j=0; j<reports.size(); j++)
        {        	
        	String name = reports.get(j).getUserName();
        	if (dict.containsKey(name) == false) {
        	Integer count =0;
        	for(int i=0;i<reports.size();i++)
        	{        		
        		if (name.equals(reports.get(i).getUserName()))
        		{
        			count++;
        		}
        	}
        	dict.put(name, count);
        	}
        }
        
        //System.out.println(dict.toString());
        Enumeration<String> s = dict.keys();
        Iterator<Integer> i = dict.values().iterator();
        while(s.hasMoreElements()) {
//        	System.out.println("Name: " + s.nextElement());
//        	System.out.println("Value: " + i.next());
        	  dataSet.setValue(i.next(),"User" ,s.nextElement());
        }
        

    
      
      
//        dataSet.setValue(791, "Population", "1750 AD");
//        dataSet.setValue(978, "Population", "1800 AD");
//        dataSet.setValue(1262, "Population", "1850 AD");
//        dataSet.setValue(1650, "Population", "1900 AD");
//        dataSet.setValue(2519, "Population", "1950 AD");
//        dataSet.setValue(6070, "Population", "2000 AD");
 
        JFreeChart chart = ChartFactory.createBarChart(
                "No. of times the file was downloaded", "User", "Downloads",
                dataSet, PlotOrientation.VERTICAL, false, true, false);
 
        return chart;
    }
}

