
package View;

import com.beust.jcommander.*;

public class CommandLineArguments 
{
    @Parameter(names = {"-i","-inputJson"}, description = "json with all the input")
    private String inputJson;

    public String getInputJson(){ return inputJson; }
    
    public CommandLineArguments( String[] args )
    {
        new JCommander(this, args);
    }
}