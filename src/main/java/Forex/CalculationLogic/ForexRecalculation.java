package Forex.CalculationLogic;

import Forex.DataContainers.NBURate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ForexDailyRecalculation {

    public List<Date> extractDates(List<NBURate> nbuRates){
        return nbuRates.stream()
                .map(d -> d.getDate())
                .distinct()
                .collect(Collectors.toList());
    }
}
