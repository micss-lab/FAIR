//package savi_ros_java.savi_ros_bdi.agent_core;

import jason.RevisionFailedException;
import jason.asSemantics.*;
import jason.asSyntax.*;
import jason.bb.BeliefBase;

import java.util.*;
import java.util.regex.*;


import jason.asSemantics.Agent;
import jason.asSemantics.Option;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Plan;
import jason.asSyntax.PlanBody;
import jason.asSyntax.PredicateIndicator;
import jason.asSyntax.PlanBody.BodyType;
import jason.asSyntax.Term;
import jason.asSyntax.StringTerm;
import jason.asSyntax.LogicalFormula;
import static jason.asSyntax.ASSyntax.*;
import jason.asSyntax.parser.ParseException;

public class Yedek_SortAgentEx extends Agent {
	
	public static double planMaxMem = -0.1;
	
	public static  ArrayList<Integer> planNumberList = new ArrayList<>();

    /**
	 * 
	 */
	private static final long serialVersionUID = 1123932742485166865L;
	protected static List<String> FuzzyEvents = new ArrayList<>();
	protected static boolean isFuzzyEvent = false;

    public Yedek_SortAgentEx() {
        super();
        this.setFuzzyEvents();
    }

    /**
     * Specify the event priorities for the event selection function.
     * This can be overridden if a new priority setting is needed.
     */
    protected void setFuzzyEvents() {
     //   this.FuzzyEvents = ;
    }

    /**
     * Highest priority event has priority
     * Otherwise, first event in the queue is chosen.
     */
    @Override
    public Event selectEvent(Queue<Event> events) {
        Event selected;
        
      //  System.out.println(" \n EVENT SIZE =  " + events.size() + events.toString());
   //    System.out.println("FUNCTOR = "+events.element().getTrigger().getLiteral().getFunctor()); en bastaki ismini veriyor.
        
        
        // EVENT OLUNCA BELIEF BASE'e BAKIP ESLESTIRME YAPIYOR. O ZAMAN BIR FUZZY EVENT GELINCE isit sonuclari guncellenmelidir.
        
   //     if (events.size() > 1) {
   //         selected = this.getHighestPriorityEvent(events);
  //      } else {
            selected = super.selectEvent(events);
  //      }
        events.remove(selected);
		//System.out.println("Chosen event is: " + selected.toString() + "ends");

        
        Term fuzzyAnnot = Literal.parseLiteral("fuzzy"); 
        System.out.println("Selected Event "+ selected.getTrigger().toString()+ " " + selected.getTrigger().toString().contains("fuzzy"));
        
        
        if (selected.getTrigger().toString().contains("fuzzy")) {
        	
        	isFuzzyEvent = true;
        }
        
     
       /* 
		if (selected.toString().contains("fuzzy"))
		{
				
				    int exclamationIndex = selected.toString().indexOf('!');
			        int bracketIndex = selected.toString().indexOf('[');
			        
			        
			        String purePlanTriggerName = selected.toString().substring(exclamationIndex + 1, bracketIndex);
				
				
				  	//String purePlanTriggerName = selected.getTrigger().toString(); //getType() da bas haneside bulunuyor.
			         System.out.println("Event Trigger Name |" + purePlanTriggerName);
			         
			         purePlanTriggerName = purePlanTriggerName.replaceAll("[^a-zA-Z]+", "");
			         
			         
			         purePlanTriggerName = purePlanTriggerName.replace("fuzzy", "");
			         purePlanTriggerName = purePlanTriggerName.replace("source", "");
			         purePlanTriggerName = purePlanTriggerName.replace("self", "");
			         purePlanTriggerName = purePlanTriggerName.replace("samplecolor", "");  // sadece fuzzy olanlar atilmali otekileri filtrele.
			         
			        
					         if (!FuzzyEvents.contains(purePlanTriggerName))
					         {
					        	 	FuzzyEvents.add(purePlanTriggerName);
					         }
			         
			         System.out.println(" Event Selection | Pure Plan Trigger Name " + purePlanTriggerName + FuzzyEvents);
			         System.out.println ("asdasd");
		         
		 }
		         
		    */    
				
			
			
			
			
			
			
			
			
		//	System.out.println("***trialdecidecolor Event is selected.***");
			/*  BeliefBase beliefs = this.getBB();
			  System.out.println("Beliefs are:");
			 int i = 0;
			  for (Literal belief : beliefs) {
				  
				  System.out.println(i + " " + belief.getFunctor() + "(" +  belief.getTerms()+ ") \n"  );
				
			  }
			  */
		
        return selected;
    }
    
    
    // ben ekledim.
    
    
//    @Override
//    public ActionExec selectAction(Queue<ActionExec> actions) {
//        // make sure the selected Action is removed from actList
//        // (do not return suspended intentions)
//
//        /* // old code, suspended is now considered in hasFA; no need to sync, it is done in TS
//         * synchronized (actList) {
//            Iterator<ActionExec> i = actList.iterator();
//            while (i.hasNext()) {
//                ActionExec a = i.next();
//                if (!a.getIntention().isSuspended()) {
//                    i.remove();
//                    return a;
//                }
//            }
//        }*/
//
//    	
//    	
//    	
//    	
//        if (actions.isEmpty())
//            return null;
//        else {
//        	Iterator<ActionExec> i = actions.iterator();
//            while (i.hasNext()) {
//                ActionExec a = i.next();
//                System.out.println("{{{{{{{{{{{{{{{{{{{{{{Actions of the selected Intention are"+a+" "+actions.size() + " }}}}}}" );
//            }
//            
//            ActionExec Selaction = actions.poll();
//            
//            
//            
//            if (Selaction.getActionTerm().toString().contains("fuzzy")) {
//				 //System.out.println(Selaction.toString());
//				final Term memDeg = Literal.parse("22");
//				
//				Selaction.getActionTerm().addTerm(memDeg);
//				
//			//	System.out.println("TERM TERM TERM TERM TERM TERM TERM"+memDeg.toString()+action.getActionTerm().toString());
//				
//				System.out.println("77777 The Selected One "+Selaction.getActionTerm() + "777777");
//			}
//            
//        	return Selaction;
//             // end of else
//        }
//        	
//        	//actions.poll();
//        	
//            
//    }
    
    
    @Override
    public Intention selectIntention(Queue<Intention> intentions) {
    	
    	Iterator<Intention> ii = intentions.iterator();
    	
    	while (ii.hasNext()) {
    		Intention i = ii.next();
    	//	System.out.println(" \n IIIII INTENTION SIZE =  " + i.size() + " - all intentions are:"+i );
    		
    	}
    	
    	Intention selected;
    	
    	selected = super.selectIntention(intentions);
    	
    /*	if (selected.toString().contains("fuzzy"))
    	System.out.println(" \n F SELECTED INTENTION IS " + selected.toString() + "with fuzzy" );
    	else 
    	System.out.println(" \n SELECTED INTENTION IS " + selected.toString() );
    	
    */
    	return selected;
    }
    
    
    
    
 // action extend et.
    
    
    
    


    /**
     * Select most appropriate option
     * Most specific should be highest priority
     * Default should be lowest priority
     */
    @Override
    public Option selectOption(List<Option> options) {
        Option selected = null;
        
     //   System.out.println(" \n OPTIONS SIZE =  " + options.size() + " - " + options.get(0).getPlan().getTrigger().toString());
        
 
        String purePlanTriggerName="";
        if (options.size() > 1) {
		          purePlanTriggerName = options.get(0).getPlan().getTrigger().toString();
		         
		         
		         if (purePlanTriggerName.indexOf('(') !=-1){
		        	 purePlanTriggerName = options.get(0).getPlan().getTrigger().toString().
		        			 substring(0, purePlanTriggerName.indexOf('(') ).replaceAll("[^a-zA-Z]+", "");
		         }
		         else {
		        	 purePlanTriggerName = options.get(0).getPlan().getTrigger().toString().replaceAll("[^a-zA-Z]+", "");
		        	 
		         }
		         
           
		         purePlanTriggerName = purePlanTriggerName.replace("fuzzy", "");
		         
		         System.out.println("Pure Plan Trigger Name " + purePlanTriggerName);
         
        }
		// OPTION SELECTION STARTS HERE. CAN WE UPDATE THE BELIEFS HERE?         
		         
		         Term fuzzyAnnot = Literal.parseLiteral("fuzzy");    
		    	
// neden -trialdecidecolor fail ile basliyor? ve fail eventleri neden annotate edemiyoruz ki
		         
//		        if (!FuzzyEvents.contains(purePlanTriggerName))  {
//		        	
//			        for (int i = 0; i < options.size(); i++)
//			        {
//			        	System.out.println("hhhhhhhhhhhhhhhas Fuzzy Trigger"+options.get(i).getPlan().getTrigger().toString());
//			        	
//			        	
//			        	if (options.get(i).getPlan().getTrigger().toString().contains("fuzzy"))
//			        	{
//			        		
//			        		FuzzyEvents.add(purePlanTriggerName);
//			        		continue;
//			        		
//			        	}
//						
//					}
//		        
		   
		         
		        System.out.println(" Fuzzy Eventsssss " + FuzzyEvents + "***" + options.get(0).getPlan().getTrigger() + "**"+ ( options.get(0).getPlan().getTrigger().toString().contains("fuzzy")+ " " +(!options.get(0).getPlan().getTrigger().toString().contains("-"))));
		         
        if (options.size() > 0 && isFuzzyEvent ) //&& (!options.get(0).getPlan().getTrigger().toString().contains("-")) 
        {
        	
        	
        //	System.out.println("ZZZZZZZZZZ OPTIONS FIRST CONDITION ZZZZZZZZ");
            try {
				selected = this.selectOptionAsFuzzy(options);
			} catch (RevisionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  // change the name afterwards
        } else {
            selected = super.selectOption(options);
        }

        System.out.println(" Ags | Chosen selectedOption is: " +selected.toString() + "\n selected Context " + selected.getPlan().getContext()
        		+ " &&&&&&& "+selected.getPlan().getTerm(0) + "&&" + selected.getPlan().getTerm(1) + "&&" + selected.getPlan().getTerm(2) + "&&" + selected.getPlan().getTerm(3));
        
       
        
        
     //  System.out.println("Selected Parsed Terms"+trial.getTerms());
       
        /*
        for (int i=0; i <= selected.getPlan().getTerms().size(); i++) {
        	
        	System.out.println("Plan Terms \n" + selected.getPlan().getTerm(i));
        }
        */
        
  //      int planTermSize = selected.getPlan().getTermsSize();
        
   //     String planModifiedFuzzyContent = parsePlanActions(String.valueOf(selected.getPlan().getTerm(planTermSize)));
        
    //    System.out.println(" Ags |" + planModifiedFuzzyContent);
        
    //    System.out.println(" SSSSSSSSSSSSSSelected Plan Body is "+ selected.getPlan().getBody().getBodyTerm());
        
        
    //    selected.getPlan().getBody().setBodyTerm(Literal.parseLiteral(planModifiedFuzzyContent));
        
        
        
        
   //     System.out.println(" [BODY] SSSSSSSSSSSSSSelected Plan Body is "+ selected.getPlan().getBody());
        
   //     System.out.println(" [DEL] SSSSSSSSSSSSSSelected Plan Size is "+selected.getPlan().getTerm(0) + "******" +selected.getPlan().getTerm(1)
   //     		+"******"+selected.getPlan().getTerm(2)+"********"+selected.getPlan().getTerm(3));
        
   //    selected.getPlan().setTerm(3, Literal.parseLiteral(planModifiedFuzzyContent));
        
        
   //     System.out.println(" YYYYYYYYYYYYYY SELECTED LATEST YYYYYYYYYYYYY"+ selected.getPlan());
        
        isFuzzyEvent = false;  // clear the isFuzzyEvent flag
        options.remove(selected);
        selected = getPlanActions(selected);
        System.out.println(" \n \n TTTTTTT This is the plan actions "+selected);
 
        return selected;

    }


//    /**
//     * Return the highest priority event from the events list.
//     */
//    protected Event getHighestPriorityEvent(Queue<Event> events) { // Fuzzy Emergency events maybe?
//
//        BeliefBase beliefs = this.getBB();
//        for (String priority : FuzzyEvents) {
//            for (Event event : events) {
//                String trigger = event.getTrigger().getLiteral().getFunctor();
//                for (Literal belief : beliefs) {
//                    if (belief.getFunctor().contentEquals(priority)) {
//                        List<Term> terms = belief.getTerms();
//                        for (Term term : terms) {
//                            if (term.toString().contentEquals(trigger)) {
//                                System.out.println("Chosen getHighestPriorityEvent: " + event.toString());
//                                return event;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        // If I made it here, use the default
//        //System.out.println("Using the default event");
//        return super.selectEvent(events);
//    }
    

    /**
     * Return the highest priority option from the options list.
     * 		Default is lowest priority
     *		Anything else is better than default option.
     * 		Need a way to choose between non default options
     * @throws RevisionFailedException 
     */
    
    
 // Linguistic Terms creations  
    
 // Create a Map for each linguistic Term.
	public static Map<String, Double> linguisticTermsRed = new HashMap<>();
	public static Map<String, Double> linguisticTermsGreen = new HashMap<>();
	public static Map<String, Double> linguisticTermsBlue = new HashMap<>();
    
    protected Option selectOptionAsFuzzy(List<Option> options) throws RevisionFailedException {
    	
    	
    	////
    	
    	 BeliefBase beliefs = this.getBB();
    	// belief.getFunctor() + belief.getTerms()
    	 
    	 
    	 // input variables.
         List<Double> rgb_values = new LinkedList<Double>(); 

    	
    	// this should also be generated. 
    	 for (Literal belief : beliefs) {
    		 
    		 List<Term> terms = belief.getTerms();
    		 if (belief.getFunctor().contains("rgb")) {
    			  
    			 System.out.println("HERE RGB VALUES "+belief.getFunctor().toString() + " - " + belief.getTerms().toString());
    			 rgb_values = (convertToListOfDoubles(belief.getTerms()));
    		 }
    		 
    	 }
    	////
    	
    	
    	
    	//r1,r2,r3,g1,g2,g3,g4,g5,g6,g7,b1,b2,b3
    	 
    	 double values[] = new double[13];
    	 
    	// System.out.println("DOUBLE VALUES" + rgb_values.get(0));
    	 
    	 
    	 // fuzzy functions.
    	 values=fuzzyColorSensor(rgb_values.get(0),rgb_values.get(1),rgb_values.get(2));
    	 
    	 
    	 
    	 

         // Add linguistic terms and their corresponding values
    	 linguisticTermsRed.put("low", values[0]);
    	 linguisticTermsRed.put("medium", values[1]);
    	 linguisticTermsRed.put("high", values[2]);
    	 
    	 
    	     	 
    	 
    	 linguisticTermsGreen.put("low", values[3]);
    	 linguisticTermsGreen.put("medium", values[4]);
    	 linguisticTermsGreen.put("high", values[5]);    	 
    	 linguisticTermsGreen.put("veryhigh", values[6]);
    	 linguisticTermsGreen.put("ultralow", values[7]);
    	 linguisticTermsGreen.put("ultramedium", values[8]);    	 
    	 linguisticTermsGreen.put("ultramedium", values[9]);
    	 
    	 
    	 linguisticTermsBlue.put("low", values[10]);
    	 linguisticTermsBlue.put("medium", values[11]);
    	 linguisticTermsBlue.put("high", values[12]);
    	 
    	 
    	 
    	 
    	 
    	 
         
         

         // Find the linguistic term with the maximum value
         String maxTermRed = findMaxLinguisticTerm(linguisticTermsRed);
         String maxTermGreen = findMaxLinguisticTerm(linguisticTermsGreen);
         String maxTermBlue = findMaxLinguisticTerm(linguisticTermsBlue);

         // Print the result
         System.out.println("Linguistic terms with maximum values: " + maxTermRed + " " + maxTermGreen + " "+ maxTermBlue);
    	 
    	 
    	
    	
    	
    	Literal isitRed = Literal.parseLiteral("isitRed("+findMaxLinguisticTerm(linguisticTermsRed)+")");
    	Literal isitGreen = Literal.parseLiteral("isitGreen("+findMaxLinguisticTerm(linguisticTermsGreen)+")");
    	Literal isitBlue = Literal.parseLiteral("isitBlue("+findMaxLinguisticTerm(linguisticTermsBlue)+")");
    	
    	Literal result = Literal.parseLiteral("isitResult(0.5)");
    	
    	
    	planMaxMem = 0.877;   // modify the end result of the logical context.
    	
    	/*
    	Term asd1 = Literal.parseLiteral("sourcepercept)");
    	Term asd2 = Literal.parseLiteral("sourcepercept)");
    	Term asd3 = Literal.parseLiteral("sourcepercept)");
    	
    	isitRed.addAnnot(asd1);
    	isitGreen.addAnnot(asd2);
    	isitBlue.addAnnot(asd3);
    	*/
    	
    	
    	
    	
    	Term fuzzyAnnot = Literal.parseLiteral("fuzzy");    	
    	isitRed.addAnnot(fuzzyAnnot);
    	isitGreen.addAnnot(fuzzyAnnot);
    	isitBlue.addAnnot(fuzzyAnnot);
    	
    	
    	this.delBel(Literal.parseLiteral("isitRed(_)"));
    	this.addBel(isitRed);
    	this.delBel(Literal.parseLiteral("isitGreen(_)"));
    	this.addBel(isitGreen);
    	this.delBel(Literal.parseLiteral("isitBlue(_)"));
    	this.addBel(isitBlue);
    	
    	
    /*	Term asd3 = Literal.parseLiteral("source(percept)");
    	result.addAnnot(asd3);
    	this.addBel(result);*/
    	
    //	System.out.println("PERCEPTS OF THE AGENT"+this.getBB().getPercepts().next());
    	
    	
    
    
    	
    	

        Option priorityOption = null;    // The event that has the highest priority so far

        // Iterate through the list, look for the "champion"
        Iterator<Option> optionInstance = options.iterator();
        
    /*    if (options.size() >2) {
        System.out.println(" ** \n ALL OPTIONS \n ** "+options.get(0).toString()+ " \n "  +options.get(1).toString() + " \n" );  // Buraya kadar geliyor.
        }
        else if (options.size() ==1) {
        	System.out.println(" ** \n ALL OPTIONS \n ** "+options.get(0).toString()+  " \n" );  // Buraya kadar geliyor.	
        }
      */  
        
        
        
        // Track first check (for the contingency)
        boolean first = true;

        while (optionInstance.hasNext()) {
        	
        
        	
            Option current = optionInstance.next();
            Plan currentPlan =  current.getPlan();
            LogicalFormula context = currentPlan.getContext();
            
            if (context!=null) {
            System.out.println(" LLLLLLLLLL LOGICAL CONTEXT LLLLLLLLLLL " + context.toString());
            }
            
            // Add contingency option in case we don't find anything useful
            if (first) {
                priorityOption = current;
                first = false;
            }

            // Context is null for default plans.
            // Need a way of comparing options if there is more than one applicable
            // non default plan
            if (context != null) {     // Rule'lari burada karsilastir.
                priorityOption = current;
                break;	// Break out of the loop, we have a non default plan.
            }
        }

        return priorityOption;
    }
    
    /*
     
       protected Option getHighestPriorityOption(List<Option> options) {

        Option priorityOption = null;    // The event that has the highest priority so far

        // Iterate through the list, look for the "champion"
        Iterator<Option> optionInstance = options.iterator();
        System.out.println(" ** \n ALL OPTIONS \n ** "+optionInstance.toString());

        // Track first check (for the contingency)
        boolean first = true;

        while (optionInstance.hasNext()) {
            Option current = optionInstance.next();
            Plan currentPlan =  current.getPlan();
            LogicalFormula context = currentPlan.getContext();
            System.out.println(" LLLLLLLLLL LOGICAL CONTEXT LLLLLLLLLLL " + context.toString());

            // Add contingency option in case we don't find anything useful
            if (first) {
                priorityOption = current;
                first = false;
            }

            // Context is null for default plans.
            // Need a way of comparing options if there is more than one applicable
            // non default plan
            if (context != null) {     // Rule'lari burada karsilastir.
                priorityOption = current;
                break;	// Break out of the loop, we have a non default plan.
            }
        }

        return priorityOption;
    }
     
     * */
    
    
    // This should be generated.
    public static double[] fuzzyColorSensor(double r, double g, double b) {

    	

	    double r1 = 0.0;
	    double r2 = 0.0;
	    double r3 = 0.0;
	    double r4 = 0.0;
	    double r5 = 0.0;


	    double g1 = 0.0;
	    double g2 = 0.0;
	    double g3 = 0.0;
	    double g4 = 0.0;
	    double g5 = 0.0;
	    double g6 = 0.0;
	    double g7 = 0.0;


	    double b1 = 0.0;
	    double b2 = 0.0;
	    double b3 = 0.0;
	    double b4 = 0.0;
	    double b5 = 0.0;

	    int red =  0;
	    int green = 0;
	    int blue = 0;



	    red = (int) r;
	    green =(int) g;
	    blue =(int) b;




	    String s_red = "";
	    String s_green = "";
	    String s_blue = "";


	    String result = "";


	    if (red < 60)    // low red
	    {

	        r1 = (60.0-red)/60.0;
	        r2 = red/60.0;
	        r3 = 0;
	        //    System.out.println("low red" + " " + r1 + " " + r2 + " " + r3);

	        s_red = "low red";

	    } else if (red >= 60 && red < 115) {   // middle red

	        r1 = 0;
	        r2 = (115 - red) / 55.0;
	        r3 = (red-60.0)/55.0;
	        //   System.out.println("middle red" + " " + r1 + " " + r2 + " " + r3);

	        s_red = "middle red";

	    } else {


	        r1=0;
	        r2=0;
	        r3=1;

	        s_red = "full red";
	    }



	    if (blue < 10.5)    // low blue
	    {

	        b1 = (10.5-blue)/10.5;
	        b2 = 0;//(blue/10.5); /// BURASI
	        b3 = 0;
	        //     System.out.println("low blue" + " " + b1 + " " + b2 + " " + b3);

	        s_red = "low blue";

	    } else if (blue >= 10.5 && blue < 19.5) {   // middle red

	        b1 = 0;
	        b2 = (19.5 - blue) / 9.0;
	        b3 = (blue-10.5)/9.0;
	        //      System.out.println("middle blue" + " " + b1 + " " + b2 + " " + b3);

	        s_blue = "middle blue";

	    } else {  // full red


	        b1=0;
	        b2=0;
	        b3=1;

	        s_blue = "full blue";
	    }

///////////////////////////////////////////////////////////////////////////////////////
	    if (green < 20)    // low green
	    {

	        g1 = (20.0-green)/20.0;
	        g2 = green/20.0;
	        g3 = 0;
	        //     System.out.println("low green" + " " + r1 + " " + r2 + " " + r3);

	        s_green = "low green";

	    } else if (green >= 20 && green < 30) {   // middle green

	        g1 = 0;
	        g2 = (30.0 - green) / 10.0;
	        g3 = (green-20.0)/10.0;
	        //    System.out.println("middle green" + " " + r1 + " " + r2 + " " + r3);

	        s_green = "middle green";

	/*    } else {


	        g1=0;
	        g2=0;
	        g3=1;

	        s_green = "full green";

	    }
	*/
	        
	    } else if ( green >=30 && green<50) {


            g1=0;
            g2=0;
            g3=(50.0-green)/20.0;
            g4=(green-30.0)/20.0;
            g5=0;


            s_green = "high green";

        }   // RED PURPLE LIMIT
	    
     else if ( green >=50 && green<80) {


        g1=0;
        g2=0;
        g3=0;
        
        g4=(80.0-green)/30.0;
        g5=(green-50.0)/30.0;


        s_green = "very high green";

    }
	    
     else if ( green >=80 && green<110) {


            g1=0;
            g2=0;
            g3=0;
            g4=0;
            g5=(110.0-green)/30.0;
            g6=(green-80.0)/30.0;
            


            s_green = "ultra green";

      }
	    
     else if ( green >=110 && green<145) {


            g1=0;
            g2=0;
            g3=0;
            g4=0;
            g5=0;
            g6=(145-green)/35.0;
            g7=(green-110.0)/35.0;


            s_green = "ultra high green";

        }
	    
	    double ret_val [] = {r1,r2,r3,g1,g2,g3,g4,g5,g6,g7,b1,b2,b3};
	    
		return ret_val;

	}
    
    private static List<Double> convertToListOfDoubles(List<Term> termList) {
        List<Double> doubleList = new ArrayList<>();

        for (Term term : termList) {
        	//System.out.println("To be Converted"+ term.toString());
            doubleList.add(Double.parseDouble(term.toString()));
        }

        return doubleList;
    }
    
    private static String findMaxLinguisticTerm(Map<String, Double> linguisticTerms) {
        double maxValue = Double.MIN_VALUE;
        String maxTerm = null;

        for (Map.Entry<String, Double> entry : linguisticTerms.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxTerm = entry.getKey();
            }
        }

    //    System.out.println("MAX Value"+maxValue);
        
        return maxTerm;
    }
     
    /* Getting actions from the plan body */
    public Option getPlanActions(Option option){

        Plan plan = option.getPlan();
        
        String removeString = String.valueOf(plan.getTerm(0));

        // Use regular expression to remove non-digit characters
        String result = removeString.replaceAll("[^0-9]", "");
        
        
        int planNum = Integer.parseUnsignedInt(result);
        
        System.out.println("Plan Number " + Integer.parseUnsignedInt(result));
     //   planNumber.add
        
    	Term fuzzyAnnot = Literal.parseLiteral("fuzzy");  

    //    Set<Action> plan_actions_list = new HashSet<Action>();

        PlanBody plan_body = plan.getBody();
        
        
        
       
        
      
        
        
        
     //   System.out.println("Plan Body "+plan_body);

        while(plan_body != null){

            if(plan_body.getBodyType() == BodyType.action){

                Literal body_term = (Literal)plan_body.getBodyTerm();
                
          //      System.out.println("Body Term "+body_term);

                if(body_term.hasAnnot(fuzzyAnnot)){
                	
               // 	option.getPlan().delTerm(3); // this requires an optimal solution where the plan has only one fuzzy action .
                	
                	 if (!planNumberList.contains(planNum)) {
                     	
                     	
                     	
                     	System.out.println("### BEFORE BODY TERM "+body_term);
                     	body_term.addTerm(Literal.parse(String.valueOf(planMaxMem)));
                     	System.out.println("### AFTER BODY TERM "+body_term);
                     }
                	 else 
                	 {
                		 
                		 body_term.setTerm(body_term.getTerms().size()-1, Literal.parse(String.valueOf(planMaxMem)));
                	 }
                	
                	

                	
                	
                	System.out.println("\n Fuzzy Annot Captured and Modified "+body_term + "* "+ body_term.getTerms().size() + "*" + option.getPlan() + planNumberList);
                	
                  /*  Term action_resource = body_term.getAnnot("artifact_name").getTerm(0);

                    if(option.getUnifier().get(action_resource.toString()) != null){
                        action_resource = option.getUnifier().get(action_resource.toString());
                    }
                    Term action_name = body_term.getTerm(0);

                 
                    */
                }
                

            }
            

            plan_body = plan_body.getBodyNext();
          //  System.out.println("Plan Body GET NEXT" +plan_body);

        }
        
        planNumberList.add(planNum);
       
        return option;
    }
    
    
/*
 private static String parsePlanActions (String inputString) { //String inputString
	 
	   inputString = ";resetCounter; .print(\"RESET HAS PASSED\"); fuzzyActionA[fuzzy]; fuzzyActionB(57)[fuzzy]; .wait(10); ?colorRed(high,Color); ?colorGreen(high,Color2); ?colorBlue(medium,Color3); .print(Color,\" \",Color2,\" \",Color3); !toPushF(3,Color); -+colordecided(true); .wait(20); !samplecolor.";

       // Define a regular expression pattern to capture terms between ";"
       Pattern pattern = Pattern.compile(";([^;]*?)(?=(;|$))");

       // Create a Matcher object and find matches in the input string
       Matcher matcher = pattern.matcher(inputString);

       // List to store all terms and fuzzy terms
       List<String> allTerms = new ArrayList<>();
      

       // Find all matches and store them in the lists
       while (matcher.find()) {
           String term = matcher.group(1).trim();
          

           if (term.contains("[fuzzy]")) {
               
               
              Term modifyFuzzyAction = Literal.parseLiteral(term);
              
              ((Structure) modifyFuzzyAction).addTerm(Literal.parse(String.valueOf(planMaxMem)));
              
              term = String.valueOf(modifyFuzzyAction);
              
        //       System.out.println("Modified Terms"+term);
           }
           allTerms.add(term);
       }

       // Display the results
    //   System.out.println("All terms between \";\": " + allTerms);
      
       // Combine all parsed content with ";" between terms
       String combinedContent = String.join(";", allTerms);
   //    System.out.println("Combined content: " + combinedContent);


       return combinedContent;
 }
	 
 	*/
}
	
