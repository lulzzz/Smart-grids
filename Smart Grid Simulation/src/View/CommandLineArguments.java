
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

    public String getGraphFile() {
        return graphFile;
    }

    public int getFrames() {
        return frames;
    }

    public String getOutputFolder() {
        return outputFolder;
    }
    
    public CommandLineArguments( String[] args )
    {
        new JCommander(this, args);
    }
}