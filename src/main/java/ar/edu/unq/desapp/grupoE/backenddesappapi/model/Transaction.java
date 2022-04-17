package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class Transaction {

    private LocalDateTime date;

    public Transaction(LocalDateTime date){
         this.date = date;
    }

    public Boolean isWithin30Minutes(LocalDateTime completeDate) {
        return Math.abs(date.getMinute() - completeDate.getMinute()) <= 30
                && date.getHour() <= completeDate.getHour()
                && date.getDayOfMonth() == completeDate.getDayOfMonth();
    }
}
