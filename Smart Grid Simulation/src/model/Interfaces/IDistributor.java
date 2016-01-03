
package Model.Interfaces;

import Model.Core.*;
import seas3.core.*;

public interface IDistributor 
{
    public void setStartingMoment(Moment moment);

    public double getRate(Moment moment);

    public Participant toParticipant(int id);
}
