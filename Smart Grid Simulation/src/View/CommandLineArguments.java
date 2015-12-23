
package View;

import com.beust.jcommander.*;

public class CommandLineArguments 
{
    @Parameter(names = {"-i","-graphFile"}, description = "txt file with all the nodes")
    private String graphFile;

    @Parameter(names = {"-f","-frames"}, description = "number of frames to be run")
    private int frames;

    @Parameter(names = {"-o","-outputFolder"}, description = "Output folder")
    private String outputFolder;
    
    @Parameter(names = {"-h","-hour"}, description = "Simulation's starting hour")
    private int startingHour;
    
    @Parameter(names = {"-m","-minute"}, description = "Simulation's starting minute")
    private int startingMinute;

    @Parameter(names = {"-t","-timeStep"}, description = "Simulation timeStep")
    private int timeStep;

    public String getGraphFile() {
        return graphFile;
    }

    public int getFrames() {
        return frames;
    }

    public String getOutputFolder() {
        return outputFolder;
    }
    public int getStartingHour() {
        return startingHour;
    }

    public int getStartingMinute() {
        return startingMinute;
    }

    public int getTimeStep() {
        return timeStep;
    }
    
    public CommandLineArguments( String[] args )
    {
        new JCommander(this, args);
    }
}