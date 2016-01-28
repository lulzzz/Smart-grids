
package Model.Interfaces;

import Model.Core.Moment;

public interface IAppliance 
{
    // Devuelve la energía requerida entre los momentos proveídos
    public double getConsum( Moment since, Moment until );
    // Devuelve el estado actual del dispositivo
    public ApplianceState getState();
    // Devuelve el momento en el está programado el inicio del dispositivo
    public Moment getStartingTime();
    // Los dispositivos pueden encontrarse en tres estados
    public enum ApplianceState { Waiting, inExecution, Ended }

    
}
