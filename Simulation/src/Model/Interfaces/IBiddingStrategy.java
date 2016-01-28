
package Model.Interfaces;

import Model.Core.Distributor;
import Model.Core.Moment;
import Model.Core.Weather;
import java.io.*;
import java.util.*;
import seas3.core.*;

public interface IBiddingStrategy 
{
    // Actualiza la función de compraventa del prosumidor dependiendo del entorno y sus componentes
    public void setBid( Moment moment, Distributor distributor,  Weather weather,
                             IBattery battery, ArrayList<IGenerator> generators, ArrayList<IAppliance> appliances);
    // Actualiza la función de compraventa del prosumidor fijando sus parámetros
    public void setBid( Moment moment, double minX, double maxX, Distributor distributor, ArrayList<IAppliance> appliances);
    // Linealiza la función de compraventa para que el problema sea resoluble por el CEAPSolver
    public PiecewiseLinearValuation toPLV();
    // Procesa los intercambios que se han realizado en la última tanda
    public void processResults( HashMap<Double,  TraderType> trades );
    // Escribe las órdenes de gnuplot para crear la gráfica de la función de compraventa
    public void writePlotData( String plotFile, PrintWriter writer );
    // Se diferencia entre dos tipos de intercambios: Los efectuados con la distribuidora y con los prosumidores
    public enum TraderType{ House, Distributor }
}
