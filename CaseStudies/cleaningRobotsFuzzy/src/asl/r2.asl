// mars robot 2

beginningIntensity(false).

!checkAgents.

+!dirtValues: beginningIntensity(true) <- getDirtIntensityValues.

+!checkAgents:  moveCount(r1,2) &  moveCount(r3,2) <- .print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgents are finished");-+beginningIntensity(true);!dirtValues.

+!checkAgents: true <- .wait(1); !checkAgents.


//+garbage(r2) : true <- burn(garb).




