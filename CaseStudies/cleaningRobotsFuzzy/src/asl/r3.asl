// mars robot 1

/* Initial beliefs */

//batteryPower(-1).
//vacuumBag(-1). 
//dirtIntensity(-1).


at(P) :- pos(P,X,Y) & pos(r3,X,Y).

/* Initial goal */

!check(slots).  // disabled for now.

/* Plans */

//+!check(slots) : true // not garbage(r3)
//   <- next(slotRev);
//      !check(slots).
//+!check(slots).


+!check(slots) : not garbage(r3) 
   <- next(slotRev);
      !check(slots).





+!check(slots) : garbage(r3) & ((not pos(r3,8,7)) | continue(r3,true)[source(r1)])  & vacuumBagFull(r3,K) & K==empty &  batteryCharge(r3,BR3) & BR3==full
   <-.print("Slot R3-1"); startTimePeriod;next(slotRev);checkStatus;!arrangeVacuumPower[fuzzy];!check(slots).				
   
   
   
      
   
+!check(slots) : (  garbage(r3) & pos(r3,8,7) & ((vacuumBagFull(r3,MK) & MK==full) | ( batteryCharge(r3, BR3) & BR3==depleted))  ) 
 <-  .print("Slot R3-4"); .send(r3,tell,continue(r1,true)); .send(r2,tell,moveCount(r3,2)).  //;  R3 can NOT move further, yet arrives right on the 8,7 and has finished its area.   
   
   
+!check(slots) : (garbage(r3) & pos(r3,8,7) & ((vacuumBagFull(r3,KK) & KK==empty) | ( batteryCharge(r3, BR3) & BR3==full))       )
 <-  .print("Slot R3-2"); .send(r1,tell,continue(r1,true)); .send(r2,tell,moveCount(r3,1)).    //   R3 can move further, yet arrives it has finished its area.
  
  
   
 +!check(slots) : (garbage(r3)  & (vacuumBagFull(r3,KI) & KI==full) | ( batteryCharge(r3, BR3) & BR3==depleted))
 <-  .print("Slot R3-3"); .send(r1,tell,continue(r1,true)); .send(r2,tell,moveCount(r3,2)).    //  R3 cannot move any further.

   

   
   
   

+!check(slots)<-.print("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRruns R3").


+continue(r3,true)[source(r1)] : not .desire(check(slots))            
   <- .print("  MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMessage from R1");!check(slots).  


//@lg[atomic]
//+garbage(r3) : not .desire(carry_to(r2))
 //  <-  burn(garb);!check(slots).
   
   
   
+!arrangeVacuumPower[fuzzy]: batteryPower(min) & ( vacuumBag(min)) & dirtIntensity(min) <- .print("RULE 1"); burnGarb(50)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(min) & ( vacuumBag(min)) & dirtIntensity(mid) <- .print("RULE 2"); burnGarb(70)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(min) & ( vacuumBag(min)) & dirtIntensity(max) <- .print("RULE 3"); burnGarb(90)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(min) & vacuumBag(mid) & dirtIntensity(min) <- .print("RULE 4"); burnGarb(50)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(min) & vacuumBag(mid) & dirtIntensity(mid) <- .print("RULE 5"); burnGarb(70)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(min) & vacuumBag(mid) & dirtIntensity(max) <- .print("RULE 6"); burnGarb(90)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(min) & vacuumBag(max) & dirtIntensity(min) <- .print("RULE 7"); burnGarb(50)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(min) & vacuumBag(max) & dirtIntensity(mid) <- .print("RULE 8"); burnGarb(70)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(min) & vacuumBag(max) & dirtIntensity(max) <- .print("RULE 9"); burnGarb(90)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(mid) & ( vacuumBag(min)) & dirtIntensity(min) <- .print("RULE 10"); burnGarb(50)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(mid) & (vacuumBag(min)) & dirtIntensity(mid) <- .print("RULE 11"); burnGarb(70)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(mid) & ( vacuumBag(min)) & dirtIntensity(max) <- .print("RULE 12"); burnGarb(90)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(mid) & vacuumBag(mid) & dirtIntensity(min) <- .print("RULE 13"); burnGarb(50)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(mid) & vacuumBag(mid) & dirtIntensity(mid) <- .print("RULE 14"); burnGarb(70)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(mid) & vacuumBag(mid) & dirtIntensity(max) <- .print("RULE 15"); burnGarb(90)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(mid) & vacuumBag(max) & dirtIntensity(min) <- .print("RULE 16"); burnGarb(50)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(mid) & vacuumBag(max) & dirtIntensity(mid) <- .print("RULE 17"); burnGarb(70)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(mid) & vacuumBag(max) & dirtIntensity(max) <- .print("RULE 18"); burnGarb(90)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(max) & ( vacuumBag(min)) & dirtIntensity(min) <- .print("RULE 19"); burnGarb(50)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(max) & ( vacuumBag(min)) & dirtIntensity(mid) <- .print("RULE 20"); burnGarb(70)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(max) & ( vacuumBag(min)) & dirtIntensity(max) <- .print("RULE 21"); burnGarb(90)[fuzzy].
+!arrangeVacuumPower[fuzzy]: batteryPower(max) & vacuumBag(mid) & dirtIntensity(min) <- .print("RULE 22"); burnGarb(50)[fuzzy].


+!arrangeVacuumPower[fuzzy]: batteryPower(max) & vacuumBag(mid) & dirtIntensity(mid) <- .print("RULE 23"); burnGarb(100)[fuzzy].


+!arrangeVacuumPower[fuzzy]: batteryPower(max) & vacuumBag(mid) & dirtIntensity(max) <- .print("RULE 24"); burnGarb(100)[fuzzy].


+!arrangeVacuumPower[fuzzy]: batteryPower(max) & vacuumBag(max) & dirtIntensity(min) <- .print("RULE 25"); burnGarb(70)[fuzzy].

+!arrangeVacuumPower[fuzzy]: batteryPower(max) & vacuumBag(max) & dirtIntensity(mid) <- .print("RULE 26"); burnGarb(100)[fuzzy].


+!arrangeVacuumPower[fuzzy]: batteryPower(max) & vacuumBag(max) & dirtIntensity(max) <- .print("RULE 27"); burnGarb(100)[fuzzy].   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
 
+!carry_to(R)
   <- // remember where to go back
      ?pos(r3,X,Y);
      -+pos(last,X,Y);

      // carry garbage to r2
      !take(garb,R);

      // goes back and continue to check
      !at(last);
      !check(slots).
	  
+!take(S,L) : true
   <- !ensure_pick(S);
      !at(L);
      dropRev(S).

+!ensure_pick(S) : garbage(r3)
   <- pickRev(garb);
      !ensure_pick(S).
+!ensure_pick(_).

+!at(L) : at(L).
+!at(L) <- ?pos(L,X,Y);
           move_towardsRev(X,Y);
           !at(L).



