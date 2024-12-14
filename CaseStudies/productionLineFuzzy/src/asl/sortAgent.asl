// Agent sortAgent in project productionLine

/* Initial beliefs and rules */


isit(Is) :- reverseBack(Is,S1) & not(reverseBack(_,S2) & S2>S1).

//fred(low)[fuzzy,degree(0.4)].





//fRed(none,107)[fuzzy].

isitRed(none)[fuzzy].
isitGreen(none)[fuzzy].
isitBlue(none)[fuzzy].

//isitRed(Is) :- colorRed(Is,S1) & not(colorRed(_,S2) & S2>S1).

//isitGreen(Is) :- colorGreen(Is,S1) & not(colorGreen(_,S2) & S2>S1).

//isitBlue(Is) :- colorBlue(Is,S1) & not(colorBlue(_,S2) & S2>S1).



dropped(false).
shredEndSent(false).
count_time(0).
reverse_triggered(false).

colorDecided(false).

f(low)[mu(0.333)]. // 0.8 // 0.2 dene // and min *      - or max + 
a(low)[mu(0.9)].
b(low)[mu(0.8)].
c(low)[mu(0.6)].
d(low)[mu(0.4)].
e(low)[mu(0.5)].



verror(low)[mu(0.35)].
verror(med)[mu(0.50)].
verror(high)[mu(0.15)].

derror(low)[mu(0.25)].
derror(med)[mu(0.20)].
derror(high)[mu(0.60)].


vmotor(low)[mu(0.55)].
vmotor(med)[mu(0.30)].
vmotor(high)[mu(0.15)].




red(low)[mu(0.1)].
red(middle)[mu(0.1)].
red(full)[mu(0.1)].
green(low)[mu(0.55)].
green(middle)[mu(0.1)].
green(high)[mu(0.1)].
green(veryhigh)[mu(0.1)].
green(ultralow)[mu(0.1)].
green(ultramedium)[mu(0.1)].
green(ultrahigh)[mu(0.1)].
blue(low)[mu(0.65)].
blue(middle)[mu(0.1)].
blue(full)[mu(0.1)].

 /* Plans */ 

+!trialdecidecolor[fuzzy]: (red(low) > 0.2 | green(low) & blue(low)) | true <- actionB[fuzzy]; actionC.
+!trialdecidecolor[fuzzy]: (red(middle) & green(middle) & blue(middle)) | true <- actionD; actionE[fuzzy].
+!trialdecidecolor[fuzzy]: (red(high) & green(high) & blue(high)) | true <- actionF[fuzzy].
// -!trialdecidecolor[fuzzy]: <- .print("trialdecidecolor Fail Plan");

//colorValue(0).

/* Initial goals */

//!status.

/* Plans */



+!status : dropped(false) <-   !status.
+!status : dropped(true) <-   .print("MESSAGE SORT - dropped"); !samplecolor.		

+!contsamplecolor: true<- -+colorDecided(false); !samplecolor.


																																//; 
												
+!samplecolor: colorDecided(CDS)  & CDS==false    <- ?rgb(RR,GG,BB); .print(" ###### START Collecting #####",RR,GG,BB); sampleColor; reverseMovements; .wait(20);  !attrialdecidecolor[fuzzy].//  ?rgb(RR,GG,BB); .print(RR,GG,BB);  .print("---------Sampled Color Value =-------------", "R ", RR, "G ", GG, "B ",BB);  //  ?count_time(M); .print(M); New_time=M+1; -+count_time(New_time);;    // 

//+!deneme: (f(low)&(a(low)|b(low))|c(low)&(d(low)|e(low)))  <- .print("OKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK").

-!samplecolor <- .print("Sample Fail Plan"); .wait(1000); !samplecolor. // .drop_all_intentions; 


// trial fuzzies

// high   high   medium
// high   low    medium

@fuzzy1                     // 0.4   LOW med high        0.2               0.3                 0.3
+!trialdecidecolor[fuzzy]: isitRed(high) & isitGreen(high) & isitBlue(medium)  <-   .print("$$$$$ PURPLE 2 -HIGH- $$$$$");resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy];  .wait(10); ?colorRed(high,Color); ?colorGreen(high,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20);   !samplecolor. //?? Karistirma noktasi Kirmizi
@fuzzy2
+!trialdecidecolor[fuzzy]: isitRed(high) & isitGreen(low) & isitBlue(medium)    <-   .print(" $$$$$ RED 1 -HIGH- $$$$$"); resetCounter; .wait(10);?colorRed(high,Color);  ?colorGreen(low,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toBuild;-+colordecided(true); .wait(20); !samplecolor.
@fuzzy3
-!trialdecidecolor <- .print("Trial Decide Color Fail Plan"); !samplecolor. // .drop_all_intentions; 



@ffuzzy1                     // 0.4   LOW med high        0.2               0.3                 0.3
+!ttrialdecidecolor[fuzzy]: (f(low)&(a(low)|b(low))|c(low)&(d(low)|e(low)))  <-   .print("$$$$$ PURPLE 2 -HIGH- $$$$$");resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy];  .wait(10); ?colorRed(high,Color); ?colorGreen(high,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20);   !samplecolor. //?? Karistirma noktasi Kirmizi
@ffuzzy2
+!ttrialdecidecolor[fuzzy]:(not f(low)&(a(low)|b(low))|c(low)&((d(low)>0.2)&e(low)))    <-   .print(" $$$$$ RED 1 -HIGH- $$$$$"); resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy]; .wait(10);?colorRed(high,Color);  ?colorGreen(low,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toBuild;-+colordecided(true); .wait(20); !samplecolor.
@ffuzzy3
-!ttrialdecidecolor <- .print("TT Trial Decide Color Fail Plan"); !samplecolor. // .drop_all_intentions; 



//+!attrialdecidecolor[fuzzy]: verror(low) & derror(med) & vmotor(high) <- .print("plan1");resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy];  .wait(10); -+colordecided(true); .wait(20);   !samplecolor.



//+!attrialdecidecolor[fuzzy]: verror(low) & (derror(med) | vmotor(high)) <- .print("plan2");resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy];  .wait(10); -+colordecided(true); .wait(20);   !samplecolor.


//+!attrialdecidecolor[fuzzy]: verror(low) & (derror(med) | not vmotor(high)) <- .print("plan3");resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy];  .wait(10); -+colordecided(true); .wait(20);   !samplecolor.

//+!attrialdecidecolor[fuzzy]: verror(low) | (derror(med) | not vmotor(high)) <- .print("plan4");resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy];  .wait(10); -+colordecided(true); .wait(20);   !samplecolor.




//+!attrialdecidecolor[fuzzy]: verror(med) & (derror(high)>0.6 & vmotor(high)) <- .print("plan 5555");resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy];  .wait(10); -+colordecided(true); .wait(20);   !samplecolor.

//+!attrialdecidecolor[fuzzy]: verror(med) | (derror(high)>0.6 & vmotor(high)) <- .print("plan 666");resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy];  .wait(10); -+colordecided(true); .wait(20);   !samplecolor.


//   [ONEMLI NOTTTT] (| true sonuna eklemek gerekebilir.)
+!attrialdecidecolor[fuzzy]: (verror(med) &  not ( derror(high)>0.6 &  vmotor(high))) <- .print("plan 7777");resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy];  .wait(10); -+colordecided(true); .wait(20);  !samplecolor.

+!attrialdecidecolor[fuzzy]: verror(med) | not (derror(high)>0.6 & vmotor(high)) <- .print("plan 8888");resetCounter; .print("RESET HAS PASSED"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy];  .wait(10); -+colordecided(true); .wait(20);   !samplecolor.



// -!trialdecidecolor[fuzzy]: <- .print("trialdecidecolor Fail Plan");
// -!trialdecidecolor[fuzzy]: <- .print("trialdecidecolor Fail Plan");





+!ztrialdecidecolor[fuzzy]: (f(low)<=f(low)) | true <- actionB[fuzzy]; actionC.











//Fuzzified Plan decidecolorF

// Replace this "!colorBucket(2) !toPushF(3,Color);" !toBuild for PushForce Experiment
+!decidecolorF: isitRed(high) & isitGreen(low) & isitBlue(medium)    <-   .print(" $$$$$ RED 1 -HIGH- $$$$$"); resetCounter; .wait(10);?colorRed(high,Color);  ?colorGreen(low,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toBuild;-+colordecided(true); .wait(20); !samplecolor.  

+!decidecolorF: isitRed(high) & isitGreen(medium) & isitBlue(medium)    <-   .print(" $$$$$ RED 2 -HIGH- $$$$$");resetCounter; .wait(10);?colorRed(high,Color);  ?colorGreen(medium,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toBuild;-+colordecided(true); .wait(20);  !samplecolor. 

+!decidecolorF: isitRed(high) & isitGreen(high) & isitBlue(low)    <-   .print("$$$$$ RED 3 -HIGH- $$$$$");resetCounter;.wait(10); ?colorRed(high,Color); ?colorGreen(high,Color2); ?colorBlue(low,Color3); .print(Color," ",Color2," ",Color3); !toBuild; -+colordecided(true); .wait(20);   !samplecolor. 

+!decidecolorF: isitRed(medium) & isitGreen(high) & isitBlue(low)    <-   .print(" $$$$$ RED 7 -MEDIUM- $$$$$");resetCounter;.wait(10);  ?colorRed(medium,Color);  ?colorGreen(high,Color2); ?colorBlue(low,Color3); .print(Color," ",Color2," ",Color3); !toBuild; -+colordecided(true); .wait(20); !samplecolor. 

+!decidecolorF: isitRed(medium) & isitGreen(medium) & isitBlue(low)    <-   .print(" $$$$$ RED 4 -MEDIUM- $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color);   ?colorGreen(medium,Color2); ?colorBlue(low,Color3); .print(Color," ",Color2," ",Color3); !toBuild; -+colordecided(true); .wait(20);  !samplecolor. 

////////
+!decidecolorF: isitRed(medium) & isitGreen(veryhigh) & isitBlue(low)    <-   .print(" $$$$$ RED 9 -MEDIUM- $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color);   ?colorGreen(medium,Color2); ?colorBlue(low,Color3); .print(Color," ",Color2," ",Color3); !toBuild; -+colordecided(true); .wait(20);  !samplecolor.
+!decidecolorF: isitRed(high) & isitGreen(veryhigh) & isitBlue(low)    <-   .print(" $$$$$ RED 10 -HIGH- $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color);   ?colorGreen(medium,Color2); ?colorBlue(low,Color3); .print(Color," ",Color2," ",Color3); !toBuild; -+colordecided(true); .wait(20);  !samplecolor.

//mor

+!decidecolorF: isitRed(medium) & isitGreen(medium) & isitBlue(medium)    <-   .print(" $$$$$ PURPLE 4 -MEDIUM- $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color); ?colorGreen(medium,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20);   !samplecolor. // Karistirma noktasi

+!decidecolorF: isitRed(medium) & isitGreen(high) & isitBlue(medium)    <-   .print("$$$$$ PURPLE 5 -MEDIUM- $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color); ?colorGreen(high,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20);   !samplecolor. 

+!decidecolorF: isitRed(high) & isitGreen(high) & isitBlue(medium)    <-   .print("$$$$$ PURPLE 2 -HIGH- $$$$$");resetCounter;.wait(10); ?colorRed(high,Color); ?colorGreen(high,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20);   !samplecolor. //?? Karistirma noktasi Kirmizi

+!decidecolorF: isitRed(medium) & isitGreen(medium) & isitBlue(high)    <-   .print("$$$$$ PURPLE 7 -MEDIUM- $$$$$ ");resetCounter;.wait(10); ?colorRed(medium,Color); ?colorGreen(medium,Color2); ?colorBlue(high,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20);  !samplecolor. 

+!decidecolorF: isitRed(high) & isitGreen(medium) & isitBlue(low)    <-   .print("$$$$$ PURPLE 3 -HIGH- $$$$$");resetCounter;.wait(10); ?colorRed(high,Color); ?colorGreen(medium,Color2); ?colorBlue(low,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20);   !samplecolor.

+!decidecolorF: isitRed(medium) & isitGreen(veryhigh) & isitBlue(medium)    <-   .print("$$$$$ PURPLE 13 -MEDIUM- $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color); ?colorGreen(high,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20);   !samplecolor.
+!decidecolorF: isitRed(medium) & isitGreen(veryhigh) & isitBlue(high)    <-   .print("$$$$$ PURPLE 14 -MEDIUM- $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color); ?colorGreen(high,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20);   !samplecolor.
+!decidecolorF: isitRed(high) & isitGreen(veryhigh) & isitBlue(high)    <-   .print("$$$$$ PURPLE 15 -MEDIUM- $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color); ?colorGreen(high,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20);   !samplecolor.




// FOR Light Green 


+!decidecolorF: isitRed(medium) & isitGreen(ultramedium) & isitBlue(medium)    <-   .print("####################### LIGHT GREEN 1 $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color);  ?colorGreen(ultramedium,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toBuild; -+colordecided(true); .wait(20);   !samplecolor.
+!decidecolorF: isitRed(medium) & isitGreen(ultralow) & isitBlue(medium)    <-   .print("####################### LIGHT GREEN 2 $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color); ?colorGreen(ultralow,Color2); ?colorBlue(medium,Color3);.print(Color," ",Color2," ",Color3);  !toBuild; -+colordecided(true); .print(Color," ",Color2," ",Color3); .wait(20);   !samplecolor.
//+!decidecolorF: isitRed(medium) & isitGreen(ultramedium) & isitBlue(medium)    <-   .print("####################### LIGHT GREEN 3 $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color); ?colorGreen(ultralow,Color2); ?colorBlue(medium,Color3);.print(Color," ",Color2," ",Color3);  !toBuild; -+colordecided(true); .print(Color," ",Color2," ",Color3); .wait(20);   !samplecolor.
+!decidecolorF: isitRed(medium) & isitGreen(ultrahigh) & isitBlue(medium)    <-   .print("####################### LIGHT GREEN 4 $$$$$");resetCounter;.wait(10); ?colorRed(medium,Color); ?colorGreen(ultralow,Color2); ?colorBlue(medium,Color3);.print(Color," ",Color2," ",Color3);  !toBuild; -+colordecided(true); .print(Color," ",Color2," ",Color3); .wait(20);   !samplecolor.

// Middle Green

+!decidecolorF: isitRed(low) & isitGreen(ultralow) & isitBlue(medium)    <-   .print("ZZZZZZZZZZZZ MIDDLE GREEN 1 $$$$$");resetCounter;.wait(10); ?colorRed(low,Color); ?colorGreen(ultralow,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toBuild;-+colordecided(true); .wait(20);   !samplecolor.
+!decidecolorF: isitRed(low) & isitGreen(ultramedium) & isitBlue(low)    <-   .print("ZZZZZZZZZZZZ MIDDLE GREEN 2 $$$$$");resetCounter;.wait(10); ?colorRed(low,Color); ?colorGreen(ultramedium,Color2); ?colorBlue(low,Color3); .print(Color," ",Color2," ",Color3); !toBuild;-+colordecided(true); .wait(20);   !samplecolor.
+!decidecolorF: isitRed(low) & isitGreen(ultralow) & isitBlue(low)    <-   .print("ZZZZZZZZZZZZ  MIDDLE GREEN 3 $$$$$");resetCounter;.wait(10); ?colorRed(low,Color); ?colorGreen(ultralow,Color2); ?colorBlue(low,Color3); .print(Color," ",Color2," ",Color3); !toBuild;-+colordecided(true); .wait(20);   !samplecolor.
+!decidecolorF: isitRed(low) & isitGreen(ultramedium) & isitBlue(medium)    <-   .print("ZZZZZZZZZZZZ  MIDDLE GREEN 4 $$$$$");resetCounter;.wait(10); ?colorRed(low,Color); ?colorGreen(ultramedium,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toBuild;-+colordecided(true); .wait(20);   !samplecolor.

// Dark Green


+!decidecolorF: isitRed(low) & isitGreen(high) & isitBlue(low)    <-   .print("KKKKKKKKKK DARK GREEN 2 $$$$$");resetCounter;.wait(10);?colorRed(low,Color); ?colorGreen(high,Color2); ?colorBlue(low,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color2);-+colordecided(true); .wait(20);   !samplecolor.
+!decidecolorF: isitRed(low) & isitGreen(high) & isitBlue(medium)    <-   .print("KKKKKKKKKK DARK GREEN 3 $$$$$");resetCounter;.wait(10); ?colorRed(low,Color);  ?colorGreen(high,Color2); ?colorBlue(medium,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color2);-+colordecided(true); .wait(20);   !samplecolor.
+!decidecolorF: isitRed(low) & isitGreen(veryhigh) & isitBlue(low)    <-   .print("KKKKKKKKKK DARK GREEN 3 $$$$$");resetCounter;.wait(10);?colorRed(low,Color);  ?colorGreen(veryhigh,Color2); ?colorBlue(low,Color3); .print(Color," ",Color2," ",Color3); !toPushF(3,Color2);-+colordecided(true);  .wait(20);  !samplecolor.

-!decidecolorF: true <-   .drop_all_intentions; .print("deciceFail");  !samplecolor.// .print(" !!!!!!!!!!!!!!!!!! No new Color!!!!!!!!!!!!!!!!!!! ");



+!decideApplyReverse(Threshold) : Threshold> 5 <- .print("Reverse");  !decideRev.
+!decideApplyReverse(Threshold) : Threshold <= 5 <- .print("Not Reverse"); !samplecolor. 


//Fuzzified Plan decideRev
+!decideRev: isit(low)   & reverseBack(low,D1) & D1>0   <-   ?reverseBack(low,D1); .send(shredAgent,achieve,emergencyLocked); .send(shredAgent,achieve,shredend); .print("rev low");  reverseMovementsAction(275,1-D1);  .send(shredAgent,achieve,emergencyUnlocked);  !samplecolor.

+!decideRev: isit(medium)  & reverseBack(medium,D1) & D1>0 <-    ?reverseBack(medium,D1); .send(shredAgent,achieve,emergencyLocked); .send(shredAgent,achieve,shredend); .print("rev medium");   reverseMovementsAction(325,D1); .send(shredAgent,achieve,emergencyUnlocked);   !samplecolor.

+!decideRev: isit(high) & reverseBack(high,D1) & D1>0   <-   ?reverseBack(high,D1); .send(shredAgent,achieve,emergencyLocked);  .send(shredAgent,achieve,shredend); .print("rev  fast");     reverseMovementsAction(350,D1);  .send(shredAgent,achieve,emergencyUnlocked); !samplecolor.

-!decideRev: true <- .print(" #### Noise Value or Unrecognised ######",DD); !samplecolor.



+!colorbucket(F): F==5 <- .print("Sending to the Build Agent");!toBuild;+toBuild(true).

+!colorbucket(F): F\==5 & F\==0 <- .print("Sending to the Bucket and PushAgent",F);!toPush(F);+toPush(true).

+!colorbucket(F): F < 2 <- !samplecolor.


+dropped(M)[source(dropAgent)] : true
   <- .print("Message from ",dropAgent,": ",M);
      -msg(M);-+dropped(true);!status.
      
      
      
+buildFree(M)[source(buildAgent)] : true
   <- .print("Message from ",buildAgent,": ",M);
      -msg(M);.abolish(buildFree(_)); -+buildFree(M).
      

      
      
+!shredend: true <- .send(shredAgent,tell,shredEnd(true)).
+!shredstart: true <- .send(shredAgent,tell,shredEnd(false)).

				
					
+!toPush(Ph):  Ph==2 <-  goBluePosition;    .print("  A message should be sent to Push Agent."); -+colorDecided(false); .send(dropAgent,achieve,loop);  .send(pushAgent,achieve,pushForce(PV)); !samplecolor. //!samplecolor.      //  !shredstart; .send([pushAgent],achieve,push). // go to samplecolor.
+!toPushF(Ph,PV):  Ph==3 <-  goGreenPosition;   .print(" A message should be sent to Push Agent."); -+colorDecided(false); .send(dropAgent,achieve,loop);  .send(pushAgent,achieve,pushForce(PV)); !samplecolor .  //!samplecolor. // .send(pushAgent,achieve,push); .send(dropAgent,achieve,loop);
+!toPush(Ph):  Ph==4 <-  goYellowPosition;   .print("  A message should be sent to Push Agent."); -+colorDecided(false); .send(dropAgent,achieve,loop); .send(pushAgent,achieve,push); !samplecolor . //.send(pushAgent,achieve,push); !shredstart. //!samplecolor.
+!toPush(Ph):  Ph\==2 |Ph\==3 |Ph\==4 <-     .print("A message should be sent to Push Agent."); -+colorDecided(false); !samplecolor.

+!toBuild: buildFree(true) <-  goRedPosition;  .send(buildAgent,achieve,build); .print("A message should be sent to Build Agent."); -+colorDecided(false); !samplecolor.    //.send([buildAgent],achieve,build). // go to samplecolor.
+!toBuild: buildFree(false) <-  goYellowPosition;  .send(pushAgent,achieve,push); .print("Build is not free RED is discarded"); -+colorDecided(false); !samplecolor. 
						         

