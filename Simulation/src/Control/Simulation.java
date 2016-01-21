package Control;

import Model.Core.*;
import View.*;
import com.google.gson.*;
import java.io.*;
import seas3.core.*;
import seas3.radpro.*;

public class Simulation 
{
    private Market market;
    private Moment from, to, startingMoment;
    private int minutesPerFrame;
    private int frames;
    private String outputFolder;
    private String marketMesh;

    public Simulation( Market market, int hour, int minute, int frames, int minutesPerFrame, String outputfolder, String marketMesh )
    {
        this.market = market;
        
        this.from = new Moment(hour, minute);
        this.to = new Moment(hour, minute);
        this.startingMoment = new Moment(hour, minute);
        to.advance(minutesPerFrame);
        
        this.minutesPerFrame = minutesPerFrame;
        this.frames = frames;
        
        this.outputFolder = outputfolder;
        this.marketMesh = marketMesh;
    }   

    public JsonObject run() throws IOException
    {
        // The output json
        JsonObject json = new JsonObject();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        json.addProperty("marketMesh", marketMesh);
        JsonArray frameArray = new JsonArray();
        
        // Save starting state
        market.setStartingMoment(from);
        market.savePlots( outputFolder + "/plot images" );
        frameArray.add(new JsonParser().parse(gson.toJson(market) ).getAsJsonObject());
        
        for( int step = 0; step < frames; step++ )
        {
            // Print the moment
            System.out.println(String.format("Trading at %s", from.toString(), to.toString()));

            Assignment assignment = trade();

            // Update market state considering the trading results
            market.advance( from, to, assignment );
            // Create gnuplot scripts for the bidding systems
            // pasar toda la ruta
            market.savePlots(outputFolder+"/plot images");
            // ejecutar plotBid
            // Save market state
            frameArray.add(new JsonParser().parse(gson.toJson(market) ).getAsJsonObject());
            
            // Refresh bids
            market.refreshBids();

            // Advance timeframe
            from.advance(minutesPerFrame);
            to.advance(minutesPerFrame);
        }
        
        json.add("frames", frameArray);
        
        // Plot bids using gnuplotScripts given in json
        FileIO.plotBids(json);
        // Save simulation json
        FileIO.saveJson(json, outputFolder + "/simulation.json");
        
        
        return json;
    }
    
    private Assignment trade()
    {
         // Solve the problem
            Problem problem = market.toProblem();
            
            Solver radPro = new RadProSolver();
            Options results = radPro.solve(problem, new Options());
            
            // Save the assignment
            Assignment assignment = (Assignment) results.get(Solver.solution);
            
            return assignment;
    }
}
