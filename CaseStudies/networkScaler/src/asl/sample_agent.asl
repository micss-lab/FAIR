// Agent sample_agent in project networkScaler

/* Initial beliefs and rules */

/* Initial goals */



// ;startTimePeriod;next(slot);checkStatus;


!start.

/* Plans */

+!start: true <- .print("asd"); !startt.

+!startt : (currentWorkLoad(CWL) & CWL==0) |(arrivedTurn(AVV) & AVV==0) <-getWorkLoadBool;checkWorkLoadBool; ?arrivedWorkLoad(AWL); .print("Arrived Work Load= ", AWL); .wait(1); !arrangeResourceScale.
+!startt : currentWorkLoad(CWL) & CWL\==0 <-checkWorkLoadBool; ?currentWorkLoad(CCWL); .print("Current Work Load= ", CCWL);.wait(1); !arrangeResourceScale.



+!arrangeResourceScale: workLoad(low) & responseTime(good) <- .print("RULE 1");   scaleFactor(-15); consumeWorkLoad;!start.
+!arrangeResourceScale: workLoad(low) & responseTime(ok) <- .print("RULE 2");     scaleFactor(-10); consumeWorkLoad;!start.
+!arrangeResourceScale: workLoad(low) & responseTime(bad) <- .print("RULE 3");   scaleFactor(10); consumeWorkLoad;!start.



+!arrangeResourceScale: workLoad(medium) & responseTime(good) <- .print("RULE 4");    scaleFactor(-10); consumeWorkLoad;!start.
+!arrangeResourceScale: workLoad(medium) & responseTime(ok) <- .print("RULE 5");        scaleFactor(0); consumeWorkLoad;!start.
+!arrangeResourceScale: workLoad(medium) & responseTime(bad) <- .print("RULE 6");     scaleFactor(10); consumeWorkLoad;!start.

+!arrangeResourceScale: workLoad(high) & responseTime(good) <- .print("RULE 7");  scaleFactor(0); consumeWorkLoad;!start.
+!arrangeResourceScale: workLoad(high) & responseTime(ok) <- .print("RULE 8");     scaleFactor(10); consumeWorkLoad;!start.
+!arrangeResourceScale: workLoad(high) & responseTime(bad) <- .print("RULE 9");   scaleFactor(15); consumeWorkLoad;!start.




/*
 * 
 * +!arrangeResourceScale[fuzzy]: workLoad(low) & responseTime(good) <- .print("RULE 1"); scaleFactor(-2)[fuzzy].
+!arrangeResourceScale[fuzzy]: workLoad(low) & responseTime(ok) <- .print("RULE 2"); scaleFactor(-1)[fuzzy].
+!arrangeResourceScale[fuzzy]: workLoad(low) & responseTime(bad) <- .print("RULE 3"); scaleFactor(1)[fuzzy].



+!arrangeResourceScale[fuzzy]: workLoad(mid) & responseTime(good) <- .print("RULE 4"); scaleFactor(-1)[fuzzy].
+!arrangeResourceScale[fuzzy]: workLoad(mid) & responseTime(ok) <- .print("RULE 5"); scaleFactor(0)[fuzzy].
+!arrangeResourceScale[fuzzy]: workLoad(mid) & responseTime(bad) <- .print("RULE 6"); scaleFactor(1)[fuzzy].

+!arrangeResourceScale[fuzzy]: workLoad(high) & responseTime(good) <- .print("RULE 7"); scaleFactor(0)[fuzzy].
+!arrangeResourceScale[fuzzy]: workLoad(high) & responseTime(ok) <- .print("RULE 8"); scaleFactor(1)[fuzzy].
+!arrangeResourceScale[fuzzy]: workLoad(high) & responseTime(bad) <- .print("RULE 9"); scaleFactor(2)[fuzzy].
* 
 */







+!start : true <- .print("hello world.").
