
package model;

public class Times
{
    private int hour;
    private int minute;
    private int frameMinutes;
    
    public Times( int hour, int minute, int frameMinutes )
    {
        this.hour = hour;
        this.minute = minute;
        this.frameMinutes = frameMinutes;
    }
    
    public void nextFrame()
    {
        this.hour += frameMinutes / 60;
        this.minute += frameMinutes % 60;
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
        return String.format("%2d:%2d", hour, minute);
    }
}
