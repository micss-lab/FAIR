// Agent sample_agent in project networkScaler

/* Initial beliefs and rules */

/* Initial goals */



// ;startTimePeriod;next(slot);checkStatus;
//[FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY FUZZY]

!start.

/* Plans */

+!start: true <- .print("asd"); !startt.

+!startt : (currentWorkLoad(CWL) & CWL==0) |(arrivedTurn(AVV) & AVV==0)<-getWorkLoadBool;checkWorkLoad; ?arrivedWorkLoad(AWL); .print("Arrived Work Load= ", AWL); .wait(1);!arrangeResourceScale[fuzzy].
+!startt : currentWorkLoad(CWL) & CWL\==0  <-checkWorkLoad; ?currentWorkLoad(CCWL); .print("Current Work Load= ", CCWL);.wait(1);  !arrangeResourceScale[fuzzy].




+!arrangeResourceScale[fuzzy]: workLoad(low) & responseTime(good) <- .print("RULE 1");   scaleFactorF(-15)[fuzzy]; consumeWorkLoad;!start.
+!arrangeResourceScale[fuzzy]: workLoad(low) & responseTime(ok) <- .print("RULE 2");     scaleFactorF(-10)[fuzzy]; consumeWorkLoad;!start.
+!arrangeResourceScale[fuzzy]: workLoad(low) & responseTime(bad) <- .print("RULE 3");   scaleFactorF(10)[fuzzy]; consumeWorkLoad;!start.



+!arrangeResourceScale[fuzzy]: workLoad(medium) & responseTime(good) <- .print("RULE 4");    scaleFactorF(-10)[fuzzy]; consumeWorkLoad;!start.
+!arrangeResourceScale[fuzzy]: workLoad(medium) & responseTime(ok) <- .print("RULE 5");        scaleFactorF(0)[fuzzy]; consumeWorkLoad;!start.
+!arrangeResourceScale[fuzzy]: workLoad(medium) & responseTime(bad) <- .print("RULE 6");     scaleFactorF(10)[fuzzy]; consumeWorkLoad;!start.

+!arrangeResourceScale[fuzzy]: workLoad(high) & responseTime(good) <- .print("RULE 7");  scaleFactorF(0)[fuzzy]; consumeWorkLoad;!start.
+!arrangeResourceScale[fuzzy]: workLoad(high) & responseTime(ok) <- .print("RULE 8");     scaleFactorF(10)[fuzzy]; consumeWorkLoad;!start.
+!arrangeResourceScale[fuzzy]: workLoad(high) & responseTime(bad) <- .print("RULE 9");   scaleFactorF(15)[fuzzy]; consumeWorkLoad;!start.




/*
 * 

* 
 */







+!start : true <- .print("hello world.").
