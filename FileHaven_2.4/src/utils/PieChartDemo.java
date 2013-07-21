package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartDemo {
    public static void main(String[] args) {
        //TODO: Add code to generate PDFs with charts
    }
 
    public static JFreeChart generatePieChart() {
        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("timetable1", 2);
        dataSet.setValue("timetable2", 1);
        
 
        JFreeChart chart = ChartFactory.createPieChart(
                "File Statistics", dataSet, true, true, false);
 
        return chart;
    }
 
//    public static JFreeChart generateBarChart() {
//        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
//        dataSet.setValue(791, "Population", "1750 AD");
//        dataSet.setValue(978, "Population", "1800 AD");
//        dataSet.setValue(1262, "Population", "1850 AD");
//        dataSet.setValue(1650, "Population", "1900 AD");
//        dataSet.setValue(2519, "Population", "1950 AD");
//        dataSet.setValue(6070, "Population", "2000 AD");
// 
//        JFreeChart chart = ChartFactory.createBarChart(
//                "World Population growth", "Year", "Population in millions",
//                dataSet, PlotOrientation.VERTICAL, false, true, false);
// 
//        return chart;
//    }
}
