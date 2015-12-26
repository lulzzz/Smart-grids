
package Model;

public class Moment implements Comparable<Moment>
{
    private int hour;
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
    }
    
    public String toString()
    {
        return String.format("%02d:%02d", hour, minute);
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

    public double minutesSince(Moment startingTime) 
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
        
        return h * 24 + m;
    }
}
