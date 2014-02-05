package org.smartcampus.simulation.smartcampus.replay;

import org.smartcampus.simulation.stdlib.replay.ExcelFormator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by foerster
 * on 05/02/14.
 */
public class SuezExcelFormator extends ExcelFormator{

    private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private final int numberOfMilliSecondsInADay = 86400000 ;

    public SuezExcelFormator(){
        super(2,new String[]{"A","B"},2);
    }

    @Override
    protected long transform(String[] columns) {
        long timestamp = 0 ;
        try {
            timestamp = sdf.parse(columns[0]).getTime();
            int hoursToMilli = Integer.valueOf(columns[1])*1000;
            timestamp += hoursToMilli;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timestamp;

    }
}
