
package Model.TemporalDistributions;

import Model.Core.Moment;

public interface ITemporalDistribution 
{
    // Devuelve el valor en un momento dado
    public Double getValue(Moment moment);
    // Devuelve la media de los valores que coge la variable entre dos momentos
    public double getMeanBetween(Moment since, Moment until);
    // Devuelve la agregación de todos los valores que coge la variable entre dos momentos
    public double getAddedValue(Moment since, Moment until);
    // Devuelve el avance que lleva la variable en el momento dado respecto a su dominio de vida
    public double getProgress(Moment moment);
}
