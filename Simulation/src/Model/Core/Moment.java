
package Model.Core;

import com.google.gson.annotations.Expose;

public class Moment implements Comparable<Moment>
{
    @Expose
    private int hour;
    @Expose
    private int minute;
    
    public Moment( int hour, int minute )
    {
        this.hour = hour;
        this.minute = minute;
    }
    
    public void advance( int minutes)
    {
        this.hour += minutes / 60;
        this.minute += minutes % 60;
        if( this.minute >= 60 )
        {
            this.hour += 1;
            this.minute -= 60;
        }
        if( this.hour >= 24 )
        {
            this.hour = 0;
        }
        if(this.minute < 0)
        {
            this.minute += 60;
            this.hour--;
        }
    }
    
    @Override
    public String toString()
    {
        return String.format("%02d.%02d", hour, minute);
    }

    @Override
    public int compareTo(Moment other) 
    {
        if( this.hour < other.hour ) return -1;
        if( this.hour > other.hour ) return +1;
        else
        {
            if( this.minute < other.minute ) return -1;
            if( this.minute > other.minute ) return +1;
            return 0;
        }
    }

    public int minutesSince(Moment startingTime) 
    {
        int h = hour - startingTime.hour;
        int m = minute - startingTime.minute;
        if ( m < 0 )
        {
            h -= 1;
            m += 60;
        }
        if( h < 0 )
        {
            h += 24;
        }
        
        return h * 60 + m;
    }

    int hoursSince(Moment since) 
    {
        int hours = hour - since.hour;
        if( hours < 0 ) hours += 24;
        
        return hours;
    }
    
    public int getHour()
    {
        return hour;
    }

    boolean isBetween(Moment since, Moment until) 
    {
        return hour >= since.hour && hour <= until.hour && minute >= since.minute && minute< until.minute;
    }

    public int getMinute() 
    {
        return minute;
    }
}
