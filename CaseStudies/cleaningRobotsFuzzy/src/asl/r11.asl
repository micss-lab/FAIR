// mars robot 1

/* Initial beliefs */

//batteryPower(-1).
//vacuumBag(-1). 
//dirtIntensity(-1).




at(P) :- pos(P,X,Y) & pos(r1,X,Y).

/* Initial goal */

!check(slots).

/* Plans */

+!check(slots) : not garbage(r1)
   <- next(slot);
      !check(slots).


+!check(slots) : garbage(r1) & (not pos(r1,6,7) | continue(r1,true)[source(r3)]) & vacuumBagFull(r1,M) & M==empty & batteryCharge(r1, BR1) & BR1==full
   <- .print("Slot R1-1");startTimePeriod;next(slot);checkStatus;!arrangeVacuumPower[fuzzy];!check(slots).


+!check(slots) : (  garbage(r1) & pos(r1,6,7) & ((vacuumBagFull(r1,M) & M==empty) | ( batteryCharge(r1, BR1) & BR1==full))  ) 
 <-  .print("Slot R1-2"); .send(r3,tell,continue(r3,true)); .send(r2,tell,moveCount(r1,1)).  //;  R1 can move further, yet arrives it has finished its area.



+!check(slots) : (		garbage(r1) & (vacuumBagFull(r1,M) & M==full) | ( batteryCharge(r1, BR1) & BR1==depleted)		) 
 <-  .print("Slot R1-3");.send(r3,tell,continue(r3,true)); .send(r2,tell,moveCount(r1,2)).  //;    R1 cannot move any further.






// .print("Slot R3-2"); ?moveCount(r1,MCR1); MCR1+1; -+moveCount(r3,MCR1); .send(r1,tell,continue(r1,true)); .send(r2,tell,moveCount(r3,_)).    

//+!check(slots)<-.print("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRruns R1").


+continue(r1,true)[source(r3)] : not .desire(check(slots))      
   <- .print(" MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM Message from R3");!check(slots).  
   
   
   
+!arrangeVacuumPower: batteryPower(min) & ( vacuumBag(min)) & dirtIntensity(min) <- .print("RULE 1"); burnGarb(50,1).
+!arrangeVacuumPower: batteryPower(min) & ( vacuumBag(min)) & dirtIntensity(mid) <- .print("RULE 2"); burnGarb(70,1).
+!arrangeVacuumPower: batteryPower(min) & ( vacuumBag(min)) & dirtIntensity(max) <- .print("RULE 3"); burnGarb(90).
+!arrangeVacuumPower: batteryPower(min) & vacuumBag(mid) & dirtIntensity(min) <- .print("RULE 4"); burnGarb(50,1).
+!arrangeVacuumPower: batteryPower(min) & vacuumBag(mid) & dirtIntensity(mid) <- .print("RULE 5"); burnGarb(70,1).
+!arrangeVacuumPower: batteryPower(min) & vacuumBag(mid) & dirtIntensity(max) <- .print("RULE 6"); burnGarb(90).
+!arrangeVacuumPower: batteryPower(min) & vacuumBag(max) & dirtIntensity(min) <- .print("RULE 7"); burnGarb(50,1).
+!arrangeVacuumPower: batteryPower(min) & vacuumBag(max) & dirtIntensity(mid) <- .print("RULE 8"); burnGarb(70,1).
+!arrangeVacuumPower: batteryPower(min) & vacuumBag(max) & dirtIntensity(max) <- .print("RULE 9"); burnGarb(90).
+!arrangeVacuumPower: batteryPower(mid) & ( vacuumBag(min)) & dirtIntensity(min) <- .print("RULE 10"); burnGarb(50,1).
+!arrangeVacuumPower: batteryPower(mid) & (vacuumBag(min)) & dirtIntensity(mid) <- .print("RULE 11"); burnGarb(70,1).
+!arrangeVacuumPower: batteryPower(mid) & ( vacuumBag(min)) & dirtIntensity(max) <- .print("RULE 12"); burnGarb(90).
+!arrangeVacuumPower: batteryPower(mid) & vacuumBag(mid) & dirtIntensity(min) <- .print("RULE 13"); burnGarb(50,1).
+!arrangeVacuumPower: batteryPower(mid) & vacuumBag(mid) & dirtIntensity(mid) <- .print("RULE 14"); burnGarb(70,1).
+!arrangeVacuumPower: batteryPower(mid) & vacuumBag(mid) & dirtIntensity(max) <- .print("RULE 15"); burnGarb(90).
+!arrangeVacuumPower: batteryPower(mid) & vacuumBag(max) & dirtIntensity(min) <- .print("RULE 16"); burnGarb(50,1).
+!arrangeVacuumPower: batteryPower(mid) & vacuumBag(max) & dirtIntensity(mid) <- .print("RULE 17"); burnGarb(70,1).
+!arrangeVacuumPower: batteryPower(mid) & vacuumBag(max) & dirtIntensity(max) <- .print("RULE 18"); burnGarb(90).
+!arrangeVacuumPower: batteryPower(max) & ( vacuumBag(min)) & dirtIntensity(min) <- .print("RULE 19"); burnGarb(50,1).
+!arrangeVacuumPower: batteryPower(max) & ( vacuumBag(min)) & dirtIntensity(mid) <- .print("RULE 20"); burnGarb(70,1).
+!arrangeVacuumPower: batteryPower(max) & ( vacuumBag(min)) & dirtIntensity(max) <- .print("RULE 21"); burnGarb(90).
+!arrangeVacuumPower: batteryPower(max) & vacuumBag(mid) & dirtIntensity(min) <- .print("RULE 22"); burnGarb(50,1).


+!arrangeVacuumPower: batteryPower(max) & vacuumBag(mid) & dirtIntensity(mid) <- .print("RULE 23"); burnGarb(100,1).


+!arrangeVacuumPower: batteryPower(max) & vacuumBag(mid) & dirtIntensity(max) <- .print("RULE 24"); burnGarb(100,1).


+!arrangeVacuumPower: batteryPower(max) & vacuumBag(max) & dirtIntensity(min) <- .print("RULE 25"); burnGarb(70,1).

+!arrangeVacuumPower: batteryPower(max) & vacuumBag(max) & dirtIntensity(mid) <- .print("RULE 26"); burnGarb(100,1).


+!arrangeVacuumPower: batteryPower(max) & vacuumBag(max) & dirtIntensity(max) <- .print("RULE 27"); burnGarb(100,1).   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
 

//@lg[atomic]
//+garbage(r1) : not .desire(carry_to(r2))
//   <- !check(slots).


+!carry_to(R)
   <- // remember where to go back
      ?pos(r1,X,Y);
      -+pos(last,X,Y);

      // carry garbage to r2
      !take(garb,R);

      // goes back and continue to check
      !at(last);
      !check(slots).

+!take(S,L) : true
   <- !ensure_pick(S);
      !at(L);
      drop(S).

+!ensure_pick(S) : garbage(r1)
   <- pick(garb);
      !ensure_pick(S).
+!ensure_pick(_).

+!at(L) : at(L).
+!at(L) <- ?pos(L,X,Y);
           move_towards(X,Y);
           !at(L).
