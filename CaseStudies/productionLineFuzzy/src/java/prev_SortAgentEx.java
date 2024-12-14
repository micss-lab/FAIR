//package savi_ros_java.savi_ros_bdi.agent_core;

import jason.JasonException;
import jason.RevisionFailedException;
import jason.asSemantics.*;
import jason.asSyntax.*;
import jason.bb.BeliefBase;


import java.io.StringReader;
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
import jason.asSyntax.parser.Token;
import jason.asSyntax.parser.TokenMgrError;
import jason.asSyntax.parser.as2j;
import jason.asSyntax.parser.as2jConstants;
import jason.asSyntax.*;



import jason.asSyntax.ArithExpr.ArithmeticOp;
import jason.asSyntax.LogExpr.LogicalOp;
import jason.asSyntax.RelExpr.RelationalOp;

public class prev_SortAgentEx extends Agent {
	
	public static double planMaxMem = -0.1;
	
	public static  ArrayList<Integer> planNumberList = new ArrayList<>();

    /**
	 * 
	 */
	private static final long serialVersionUID = 1123932742485166865L;
	protected static List<String> FuzzyEvents = new ArrayList<>();
	protected static boolean isFuzzyEvent = false;
	protected static Trigger EventTrigger;

    public prev_SortAgentEx() {
        super();
        this.setFuzzyMaps();
    }

    /**
     * Specify the fuzzyEvents for the fuzzy option selection function.
    **/
    // Container to hold multiple HashMaps
    Map<String, FuzzyObject[]> fuzzyBeliefsToMemberships = new HashMap<>();
    protected void setFuzzyMaps() {
    	
     //   this.FuzzyEvents = ;
    //	System.out.println("Fuzzy Maps are set");
    	
    	
    	
   // 	String AgentName, String termName, String FunctionType, ArrayList<Double> points
    	
    	//////////////////// THE KNOWLEDGE BASE VARIABLES //////////////////////////////////
    	
    	
    	fuzzyBeliefsToMemberships.put("red",  new FuzzyObject[] 
    	{ new FuzzyObject("sortAgent", "low" ,"trian", new ArrayList<>(List.of(0.0, 0.0, 60.0))),
    	  new FuzzyObject("sortAgent", "medium" ,"trian", new ArrayList<>(List.of(0.0, 60.0, 115.0))),
    	  new FuzzyObject("sortAgent", "high" ,"trape", new ArrayList<>(List.of(60.0, 115.0, 255.0, 255.0)))
    	});
    	
    	
    	
    	fuzzyBeliefsToMemberships.put("green",  new FuzzyObject[] 
    	    	{ new FuzzyObject("sortAgent", "low" ,"trian", new ArrayList<>(List.of(0.0, 0.0, 20.0))),
    	    	  new FuzzyObject("sortAgent", "medium" ,"trian", new ArrayList<>(List.of(0.0, 20.0, 30.0))),
    	    	  new FuzzyObject("sortAgent", "high" ,"trian", new ArrayList<>(List.of(20.0, 30.0, 50.0))),
    	    	  new FuzzyObject("sortAgent", "veryhigh" ,"trian", new ArrayList<>(List.of(30.0, 50.0, 80.0))),
    	    	  new FuzzyObject("sortAgent", "ultralow" ,"trian", new ArrayList<>(List.of(50.0, 80.0, 110.0))),
    	    	  new FuzzyObject("sortAgent", "ultramedium" ,"trian", new ArrayList<>(List.of(80.0, 110.0, 145.0))),
    	    	  new FuzzyObject("sortAgent", "ultrahigh" ,"trape", new ArrayList<>(List.of(110.0, 145.0, 255.0, 255.0)))
    	    	});
    	
    	
    	
    	
    	
    	fuzzyBeliefsToMemberships.put("blue",  new FuzzyObject[] 
    	    	{ new FuzzyObject("sortAgent", "low" ,"trian", new ArrayList<>(List.of(0.0, 0.0, 10.5))),
    	    	  new FuzzyObject("sortAgent", "medium" ,"trian", new ArrayList<>(List.of(5.25, 10.5, 19.5))),
    	    	  new FuzzyObject("sortAgent", "high" ,"trape", new ArrayList<>(List.of(10.5, 19.5, 255.0, 255.0)))
    	    	});
    	
    	
    	

    /*    new FuzzyObject("trian", new ArrayList<>(List.of(0.0, 60.0, 115.0)))))
    			
    			);   
     
    	fuzzyBeliefsToMemberships.put("red", new HashMap<String, FuzzyObject>(Map.of("full",  new FuzzyObject("trape", new ArrayList<>(List.of(60.0, 115.0, 255.0, 255.0)))) ));  
     
     */
     
     
     
     
     
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
        
        EventTrigger = selected.getTrigger();
		
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
        
      //  System.out.println(" \n OPTIONS SIZE =  " + options.size() + " - " + options);
        
 // BURADAKI OPTIONS A BAK.
        
    
        
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
		   
		         
		//        System.out.println(" Fuzzy Eventsssss " + FuzzyEvents + "***" + options.get(0).getPlan().getTrigger() + "**"+ ( options.get(0).getPlan().getTrigger().toString().contains("fuzzy")+ " " +(!options.get(0).getPlan().getTrigger().toString().contains("-"))));
		         
		         
		        
        if (options.size() > 0 && isFuzzyEvent ) //&& (!options.get(0).getPlan().getTrigger().toString().contains("-")) 
        {
        	
        	
        //	System.out.println("ZZZZZZZZZZ OPTIONS FIRST CONDITION ZZZZZZZZ");
            try {
				selected = this.selectOptionAsFuzzy(options);
				
		//	   System.out.println("ssssssssssssssssssssssSelect Option As Fuzzy");
				
			} catch (RevisionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  // change the name afterwards
        } else {
            selected = super.selectOption(options);
        }
        
        try {
			//System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPParsed Result is ");
			
	/*		for (int i = 0; i< parseStructure(String.valueOf("f&(a|b)|c&(d|e)")).getTerms().size() ; i++) 
			{
				
				//Term a = parseStructure(String.valueOf("f&(a|b)|c&(d|e)")).getTerms();
				
				
			}*/
			
			
			
				
			
		      
		       
		 
        	// as2j parser = new as2j(new StringReader(("(f(low)&(a(low)|b(low))|c(low)&(d(low)|e(low)))"))); //f&(a|b)|c&(d|e)
        	 
      //  	 String anyContext = "not f(low)"; 
        	
        	
       
        	
        	
        	
        		
        		
        		
        		
        	
        	
        //	 Structure st  = Structure.parse(anyContext);
        	 
        	 	
		//       System.out.println(" SSSSSSSSSSSSSSSSSS"+  st + " ******* " + st.getFunctor() + " ******* " + st.getTerms() );
		    	   
    /// ************** Bunu logical parser tree ye gore yap. **********************
		            
		  
			
			
			
		//	if (parseStructure(String.valueOf(selected.getPlan().getContext())).getTermsSize()>1)
		//		System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPParsed Result is "+ parseStructure(String.valueOf(selected.getPlan().getContext())).getTermsArray()[1] );
			
        	
		} catch ( TokenMgrError e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
      

        System.out.println(" Ags | Chosen selectedOption is: " +selected.toString() + "\n selected Context " + selected.getPlan().getContext()
        		+ " &&&&&&& "+selected.getPlan().getTerm(0) + "&&" + selected.getPlan().getTerm(1) + "&&" + selected.getPlan().getTerm(2) + "&&" + selected.getPlan().getTerm(3));
        
       
        
        
        
        
        
    
        isFuzzyEvent = false;  // clear the isFuzzyEvent flag
        options.remove(selected);
        selected = getPlanActions(selected);
        System.out.println(" \n \n TTTTTTT This is the plan actions "+selected);
 
        return selected;

    }
    
    
    
    public StructureNode addStructureNodeRecursive (Structure anyContext)
    {
    	
  //  	System.out.println("Context "+ anyContext);
		
    //	String st = String.valueOf(anyContext);
    	
    	StructureNode a = null;
    	
  
    	
    	if (String.valueOf(anyContext.getFunctor()).matches("[a-zA-Z]+"))
    	{
    		
    		
    		a = new StructureNode(anyContext, String.valueOf(anyContext));
    		// System.out.println(String.valueOf(anyContext.getFunctor()));
    		return a;
    	}
    	
    	else
    	{
    		
    		System.out.println("KKKK "+anyContext.getFunctor() + " " ); //+ anyContext.getTermsSize()
    		
    		a = new StructureNode(anyContext, String.valueOf(anyContext.getFunctor()));
    		
    		if (anyContext.getTermsSize() >1 ) // left and right hand sides.
    		{ 
    			
    			
    			
    			
    			
    				System.out.println(" LEFT HAND BEGIN " + !String.valueOf(anyContext.getTerm(0)).matches("[0-9]+") + " * " + !String.valueOf(anyContext.getTerm(1)).matches("[0-9]+"));
    			
    				
    				if (!(String.valueOf(anyContext.getTerm(0)).matches("^[-+]?\\d*\\.?\\d+$")))
    				{
    					
    					System.out.println("Err p 1 ");
    					a.left  = addStructureNodeRecursive(Structure.parse(String.valueOf(anyContext.getTerm(0))));
    				}
    				
    				if (!(String.valueOf(anyContext.getTerm(1)).matches("^[-+]?\\d*\\.?\\d+$")))
    				{
    					System.out.println("Err p 2 "+anyContext.getTerm(1));
    					a.right = addStructureNodeRecursive(Structure.parse(String.valueOf(anyContext.getTerm(1))));
    				
    				}
    				
    				System.out.println(" LEFT HAND VALUE "+Structure.parse(String.valueOf(anyContext.getTerm(0))));

	    			if ((String.valueOf(anyContext.getTerm(0)).matches("^[-+]?\\d*\\.?\\d+$")))
	    				{
	    				
	    				System.out.println("Err p 3 ");
	    					a.left = new StructureNode();    				
	    					a.left.beliefValue = Double.parseDouble(String.valueOf(anyContext.getTerm(0)));
	    				
	    					System.out.println(" LEFT HAND VALUE SECOND CONDITION " + a.left);
	    				
	    				}
	    				
	    			if ((String.valueOf(anyContext.getTerm(1)).matches("^[-+]?\\d*\\.?\\d+$")))
	    				{
	    				
	    				System.out.println("Err p 4 ");
	    					a.right = new StructureNode();    				
	    					a.right.beliefValue = Double.parseDouble(String.valueOf(anyContext.getTerm(1)));
	    				
	    					System.out.println(" RIGHT HAND VALUE SECOND CONDITION " + a.right);
	    				
	    				}
	    				
	    				return a;
    				
    				
    			  
    		}else // this is for fuzzy [not]  a term can have a not before hand such as (not speed(low))
    		{ 
    			a.left = addStructureNodeRecursive(Structure.parse(String.valueOf(anyContext.getTerm(0))));
    		}
    		
    		
    	}
    	
    	
    		
    	
    	return a;
    	
     } 
    
      public StructureNode findLeafNodesRecursive (StructureNode anyContext)
      {
	    	
	    //	StructureNode a = anyContext;
    	  
    	  if (anyContext!=null)
    	  {
	    	
	    	if (anyContext.left==null && anyContext.right==null)
	    
	    	{
	    		
	    		if (anyContext.beliefValue == Double.MIN_VALUE) {
	    		
	    		anyContext.beliefValue = findBeliefValue(anyContext.value);
	    		
	    		}
	    		
	    		
	    		
	    		
	    		
	    		 System.out.println(" VALUE LEAFF "+anyContext.value + "*** "+ anyContext.beliefValue);
	    		
	    	}
	    	
	    	
	    	findLeafNodesRecursive(anyContext.left);
    		findLeafNodesRecursive(anyContext.right);
    	
    			// BeliefBase'den degerleri buldurup, hesaplatmayi yaptir.
    
    	  }
    	  
		return anyContext;
    	  
      }
      
      
      public double findBeliefValue (String beliefString)
      {
    	  
      
    	    double beliefValue = 0.0;
    	    	
    	    Literal beliefParse = Literal.parseLiteral(beliefString); 
    	    
    	   
    	    
    	    BeliefBase beliefs = this.getBB();
    	    
    	    Term fuzzyAnnot = Literal.parseLiteral("fuzzy"); 
            
        	// this should also be generated. 
        	 for (Literal belief : beliefs)
        	 {
        		 
      		 
        		 if (String.valueOf(belief).contains(String.valueOf(beliefParse)))
        		 {
        			// System.out.println("BBELIEF MATCHES ==> "+ belief.getAnnot("mu").getTerm(0) + "   " + beliefParse);
        			 
        			 beliefValue = Double.valueOf(String.valueOf(belief.getAnnot("mu").getTerm(0))); // get the membership degree here.
        	
        		 }
        		 
        		 
        		 
        		 
        	 } // end of for loop
        	
        		 
        	//	 if (belief.hasAnnot(fuzzyAnnot)) {
        			 
        			 //	 CapturedFuzzyBelief = belief.getFunctor().replace("isit", "");
        			
        			 
        			 
        	//		 Pattern pattern = Pattern.compile("file:src/asl/(\\w+)\\.asl");

        		        // Use a Matcher to find the match in the input string
        		//        Matcher matcher = pattern.matcher(this.aslSource.toString());

        		        // Check if there is a match and extract the Agent's name
        		//        String AgentName="";
        	//	        if (matcher.matches()) {
        		//            AgentName = matcher.group(1);
        		            
        		//        } else {
        		//            System.out.println("No match found.");
        		//        }
        		        
        		        
        		    //    System.out.println(" Captured Fuzzy Belief "+ CapturedFuzzyBelief+" "+AgentName);
        			 
        		   //     getFuzzyBeliefMembershipValue(AgentName, CapturedFuzzyBelief);
        		        
        		        
        		        
        			 
        	//	 }
    	
        	 	return beliefValue;
    	  
      } 
      
      public double traverseStructureTree (StructureNode anyContext) {
    	  
    	 // if(anyContext==null) return anyContext;
    	  
    	  System.out.println("AnyContext Value "+ anyContext.value + " ***** " +anyContext.beliefValue + "****" + anyContext.value.hashCode());
	    
    	  /*// Empty tree
	  	    if (anyContext == null) {
					return 0.0;
				}
  	 */
    	
    	    // Leaf node i.e, an integer
    	    if (anyContext.left == null && anyContext.right == null) {
    	    	
    	    	System.out.println("Left and right are null 1, returning");
    	    	
    	        return anyContext.beliefValue;
    	        
    	    }
    	 
    	    double leftEval=Double.MIN_VALUE;
    	    double rightEval=Double.MIN_VALUE;
    	    
    	    try {
    		    // Evaluate left subtree
    	    	leftEval = traverseStructureTree(anyContext.left);
        	    
           	 
        	    // Evaluate right subtree
    	    	rightEval = traverseStructureTree(anyContext.right);
    	    	
    	    	System.out.println("LEFT AND RIGHT "+ leftEval + "**" + rightEval);
    	    }
      		catch (NullPointerException e) {
	    	
			System.out.println(" NULL pointer exception"+ leftEval + " " + rightEval);

    	    }
	   
	    
	 
	
	 
    	    
    	    
    	    // Check which operator to apply
    	    if (anyContext.value.hashCode()==34628) { // | OR = max
    	    	
    	    	System.out.println("2");
    	    	return Math.max(leftEval, rightEval)  ;  //leftEval + rightEval
    	    	
    	    }
    	    
    	    if (anyContext.value.hashCode()==32706) { //  > operator
    	    	
    	    	System.out.println("33333333333333333333333333333333333333333333333333333333333333333"+leftEval+"*"+rightEval);
    	    	if (leftEval>rightEval)
    	    	return 1.0;
    	    	else
    	    	return 0.0;  //leftEval + rightEval
    	    	
    	    }
    	    
    	    if (anyContext.value.hashCode()==32644) { //  < operator
    	    	
    	    	System.out.println("444444444444444444"+leftEval+"*"+rightEval);
    	    	if (leftEval<rightEval)
    	    	return 1.0;
    	    	else
    	    	return 0.0;  //leftEval + rightEval
    	    	
    	    }
    	    
    	    if (anyContext.value.hashCode()==32675) { //  = operator
    	    	
    	    	System.out.println("2323"+leftEval+"*"+rightEval);
    	    	if (leftEval==rightEval)
    	    	return 1.0;
    	    	else
    	    	return 0.0;  //leftEval + rightEval
    	    	
    	    }  
    	    
    	    
    	    
    	    if (anyContext.value.hashCode()==1014817) { //  <=  1014817
    	    	
    	    	System.out.println("buyuk esittir"+leftEval+"*"+rightEval);
    	    	if (leftEval>=rightEval)
    	    	return 1.0;
    	    	else
    	    	return 0.0;  
    	    	
    	    } 
    	    
    	    if (anyContext.value.hashCode()==1012895) { //  <=  1012895
    	    	
    	    	System.out.println("kucuk esittir"+leftEval+"*"+rightEval);
    	    	if (leftEval<=rightEval)
    	    	return 1.0;
    	    	else
    	    	return 0.0;  
    	    	
    	    } 
    	        
    	 
    	    if (String.valueOf(anyContext.value).equals("-")) {
    	    	
    	    	System.out.println("4");
    	    	return leftEval - rightEval;
    	    	
    	    }
    	        
    	    
    	 
    	    if (anyContext.value.hashCode()==31962) { // * AND = min
    	    	
    	    	 
    	    	System.out.println("MATCHES &");
    	    	 return Math.min(leftEval, rightEval);  //leftEval * rightEval
    	    }
    	      
    	    if (String.valueOf(anyContext.value).equals("/")) {
    	    	
    	    	return leftEval / rightEval;
    	    }
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    if (anyContext.value.hashCode()==3387309) { // this is for [not]
    	    	
    	    	
    	    	System.out.println(" \n \n NOT NOT NOT NOT NOT NOT NOT [NOT] MATCHES "+(1.0-leftEval));
    	    	return 1.0-leftEval;
    	   
    	    }
      		
    	  
    	  //System.out.println(" !!!Tree Traverse!!! ");
    	  
    	 /* traverseStructureTree(anyContext.left);
    	  System.out.println(anyContext.value);;
    	  traverseStructureTree(anyContext.right);
    	  */
    	  
    	  
    	  
		//return anyContext;
    	  
    	    return -2.2;
          	    
          	   
       	 
    	  
      }
      
      
      
    
    



    

    /** MODIFY THIS LATER
     * Return the highest priority option from the options list.
     * 		Default is lowest priority
     *		Anything else is better than default option.
     * 		Need a way to choose between non default options
     * @throws RevisionFailedException 
     */
        
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
 // Linguistic Terms creations  
    
 // Create a Map for each linguistic Term.
//	public static Map<String, Double> linguisticTermsRed = new HashMap<>();
//	public static Map<String, Double> linguisticTermsGreen = new HashMap<>();
//	public static Map<String, Double> linguisticTermsBlue = new HashMap<>();
 //
	
	
	
	

    
    
	
	
    protected Option selectOptionAsFuzzy(List<Option> options) throws RevisionFailedException {
    	
    	System.out.println(" 1) OP OP OP OP OPTIONS\n"+ options + "***\n");
    	
    	try {
			options = getTS().relevantPlans(this.EventTrigger);
		} catch (JasonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(" 2) OP OP OP OP OPTIONS\n"+ options + "***\n");
    	
    	
    	
    
    
    	
    	////
    	 String  CapturedFuzzyBelief ="";
    	 BeliefBase beliefs = this.getBB();
    	// belief.getFunctor() + belief.getTerms()
    	 
    	 
    	 
    	 // input variables.
         List<Double> rgb_values = new LinkedList<Double>(); 

         Term fuzzyAnnot = Literal.parseLiteral("fuzzy"); 
         
    	// this should also be generated. 
    	/* for (Literal belief : beliefs) {
    	
    		 
    		 if (belief.hasAnnot(fuzzyAnnot)) {
    			 
    			 CapturedFuzzyBelief = belief.getFunctor().replace("isit", "");
    			
    			 
    			 
    			 Pattern pattern = Pattern.compile("file:src/asl/(\\w+)\\.asl");

    		        // Use a Matcher to find the match in the input string
    		        Matcher matcher = pattern.matcher(this.aslSource.toString());

    		        // Check if there is a match and extract the Agent's name
    		        String AgentName="";
    		        if (matcher.matches()) {
    		            AgentName = matcher.group(1);
    		            
    		        } else {
    		            System.out.println("No match found.");
    		        }
    		        
    		        
    		    //    System.out.println(" Captured Fuzzy Belief "+ CapturedFuzzyBelief+" "+AgentName);
    			 
    		      //  getFuzzyBeliefMembershipValue(AgentName, CapturedFuzzyBelief);
    		        
    		        
    		        
    			 
    		 }
    		 
    		 
    		 
    		 //System.out.println("Belief Annots"+ belief.hasAnnot(fuzzyAnnot));
    		 
    		// List<Term> terms = belief.getTerms();
    		 if (belief.getFunctor().contains("rgb"))
    		 {
    			  
    			// System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHERE RGB VALUES "+belief.getFunctor().toString() + " - " + belief.getTerms().toString());
    			 
    			 
    			 rgb_values = (convertTermsListOfDoubles(belief.getTerms()));
    		 }
    		 
    	 }
    	////
    	
    	
    	
    	//r1,r2,r3,g1,g2,g3,g4,g5,g6,g7,b1,b2,b3
    	 
    	 double values[] = new double[13];
    	 

    	 
    	 values=fuzzyColorSensor(rgb_values.get(0),rgb_values.get(1),rgb_values.get(2));  // go the  fuzzy membership functions.
    	 
 
    	Literal result = Literal.parseLiteral("isitResult(0.5)");
    	
    	
    	*/
    	
    	//************************************
    	 /*	Structure sta  = Structure.parse("f(low)>=f(low)");
 		
 		
 		
			System.out.println(sta + "******" + sta.getTerms() + " **** " + sta.getFunctor());
		
		
				StructureNode root = 	addStructureNodeRecursive(st);

		
			root = findLeafNodesRecursive(root);
		
			System.out.println("************************************************ END RESULT "+traverseStructureTree(root));
			System.out.println("RRRRRRRRRRRRRRRR "+ root.left.left.beliefValue +"**" + root.value+ "****" +root.right.value);
		*/
    	//**************************************
    	
    	
    	
    	
    	
    	
    	
    	
    
    
    	
    	
    
    
    	
    	

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
        int z=0;
        int maxIndex=0;
        double maxDegree=0.0;
        double planTriggeringDegree = 0.0;
        
        for (int i = 0; i < options.size(); i++) {
			
        	System.out.println("OOOOOOOOOOOPTIONS ALL SIZE="+options.size()+" OPS "+options.get(i).toString());
        	
        	
        	Option current = options.get(i);
            Plan currentPlan =  current.getPlan();
            LogicalFormula context = currentPlan.getContext();
            
            if (context!=null) {
	            	z++;
	            System.out.println(" LLLLLLLLLL LOGICAL CONTEXT LLLLLLLLLLL " +  z + " " + context.toString());
	            
	            
	        	Structure st  = Structure.parse(String.valueOf(context).replaceAll("\\|\\s*true", ""));
	     		
	     		
	     		
				System.out.println(st + "******" + st.getTerms() + " **** " + st.getFunctor() + " " + st.getFunctor().hashCode());
			
			
				StructureNode root = 	addStructureNodeRecursive(st);
	
			
				root = findLeafNodesRecursive(root);
				
				planTriggeringDegree = traverseStructureTree(root);
				
				if (maxDegree>planTriggeringDegree)
				{
					
					maxIndex = i;
				}
				
			
				System.out.println("************************************************ END RESULT "+ i + " " +planTriggeringDegree);
			//	System.out.println("RRRRRRRRRRRRRRRR "+ root.left.left.beliefValue +"**" + root.value+ "****" +root.right.value);
	            
	            
	            
            }
		}
        
        
        
    	planMaxMem = planTriggeringDegree;   // modify the end result of the logical context.
    	
    	priorityOption = options.get(maxIndex);
        
        
        /*
        while (optionInstance.hasNext()) {
        	
        	
        	
            Option current = optionInstance.next();
            Plan currentPlan =  current.getPlan();
            LogicalFormula context = currentPlan.getContext();
            
            if (context!=null) {
            	z++;
      //      System.out.println(" LLLLLLLLLL LOGICAL CONTEXT LLLLLLLLLLL " +  z + " " + context.toString());
            
            
        	Structure st  = Structure.parse(String.valueOf(context));
     		
     		
     		
		//	System.out.println(st + "******" + st.getTerms() + " **** " + st.getFunctor());
		
		
			StructureNode root = 	addStructureNodeRecursive(st);

		
			root = findLeafNodesRecursive(root);
		
		//	System.out.println("************************************************ END RESULT "+traverseStructureTree(root));
		//	System.out.println("RRRRRRRRRRRRRRRR "+ root.left.left.beliefValue +"**" + root.value+ "****" +root.right.value);
            
            
            
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
          //      break;	// Break out of the loop, we have a non default plan.
            }
        } // END OF WHILE
        
        */
    	
    	System.out.println("RETURN PRIORITY OPTION "+priorityOption);

        return priorityOption;
    }
    
       
    
    private void getFuzzyBeliefMembershipValue(String agentName, String capturedFuzzyBelief ) throws RevisionFailedException
    {
		
    	
    	 BeliefBase beliefs = this.getBB();
    	 Double Value =0.0;

    	 for (Literal belief : beliefs)
    	 {

    	//	 System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF "+belief.getFunctor() + " " + capturedFuzzyBelief  );
    		if ( belief.getFunctor().equalsIgnoreCase(capturedFuzzyBelief))
    		{
    			    			
    			Value = Double.valueOf(String.valueOf(belief.getTerm(0))); 
    			
    		}
    	 }
    	 
    	 capturedFuzzyBelief= capturedFuzzyBelief.toLowerCase();
    	//	 System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFound Object is "+ Value);
    		 
    		 for (int i = 0; i < fuzzyBeliefsToMemberships.get(capturedFuzzyBelief).length ; i++  )
    		 {
    			// System.out.println(" $$$$$$$$$$$$$$$$$$$$$ AAAAGENT NAME EQUALLLLLS S "+ fuzzyBeliefsToMemberships.get("red")[i].AgentName + "" + (agentName));
    			 
    			 	
    			 		if (fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].AgentName.equals(agentName)) 
    			 		{
    		//	 			System.out.println(" AAAAGENT NAME EQUALLLLLS S ");
    			 			
    			 			
    			 	      
	    			 	       if (fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].FunctionType.equalsIgnoreCase("trian")) {
	    			 	        	
	    			 	        	fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].result = SortAgentEx.triangular(			 	        			
	    			 	        	fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].points.get(0), fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].points.get(1), 
	    			 	        	fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].points.get(2), Value);
	    			 	            
	    			 	        } 
	    			 	        
	    			 	        else if (fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].FunctionType.equalsIgnoreCase("gauss"))
	    			 	        {
	    			 	        	fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].result = SortAgentEx.gauss(
	    			 	        	fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].points.get(0), 
	    			 	            fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].points.get(1), Value); //double mean, double stdev, double val
	    			 	            
	    			 	        }
	    			 	        
	    			 	        else if (fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].FunctionType.equalsIgnoreCase("trape")) {
	    			 	        	
	    			 	        	fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].result = SortAgentEx.trape(
	    			 	        	fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].points.get(0), fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].points.get(1),
	    			 	            fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].points.get(2), 
	    			 	            fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].points.get(3), Value); //double mean, double stdev, double val
	    			 	          
	    			 	        } 
    			 	        
    			 	    }
    			 		
    			 		//	System.out.println(" RRRRRRRRRRESULTT "+fuzzyBeliefsToMemberships.get(capturedFuzzyBelief)[i].result);
    			 			
    			} // end of the for loop.
    		 
    		 
    		 		String MaxFuzzyLinguisticTerm = findMaxFuzzyLinguisticTerm(fuzzyBeliefsToMemberships.get(capturedFuzzyBelief));
    		 
    		 		System.out.println(" \n "+MaxFuzzyLinguisticTerm);
    		 
    		 
    		 	// make the red to isitRed as the initial is capitalized.
    		 	capturedFuzzyBelief = capturedFuzzyBelief.substring(0, 1).toUpperCase() + capturedFuzzyBelief.substring(1);
    		 	Literal fuzzyBelief = Literal.parseLiteral("isit"+capturedFuzzyBelief+"("+MaxFuzzyLinguisticTerm+")+");
    		 	Term fuzzyAnnot = Literal.parseLiteral("fuzzy"); 
    		 	fuzzyBelief.addAnnot(fuzzyAnnot);
    	    	this.delBel(Literal.parseLiteral("isit"+capturedFuzzyBelief+"(_)+"));    	    	
    	    	this.addBel(fuzzyBelief);
    	    	
    		 
    		 
    		 } // end of the getFuzzyBeliefMembershipValue
    		 
   
    	
    	
		
	

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
    
 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    private static List<Double> convertTermsListOfDoubles(List<Term> termList) {
        List<Double> doubleList = new ArrayList<>();

        for (Term term : termList) {
        	//System.out.println("To be Converted"+ term.toString());
            doubleList.add(Double.parseDouble(term.toString()));
        }

        return doubleList;
    }
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////       
    private static String findMaxLinguisticTerm(Map<String, Double> linguisticTerms) {
        double maxValue = Double.MIN_VALUE;
        String maxTerm = null;

        for (Map.Entry<String, Double> entry : linguisticTerms.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxTerm = entry.getKey();
            }
        }

     //   System.out.println(" MAX Value "+maxValue);
        
        return maxTerm;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////       
private static String findMaxFuzzyLinguisticTerm(FuzzyObject[] linguisticTerms) {
double maxValue = Double.MIN_VALUE;
String maxTerm = null;

for (FuzzyObject entry : linguisticTerms)
{
	if (entry.result > maxValue)
	    {
			maxValue = entry.result;
			maxTerm = entry.termName;
		}
}

//   System.out.println(" MAX Value "+maxValue);

return maxTerm;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
    
    
    
    
     
    /* Getting actions from the plan body */
    public Option getPlanActions(Option option){

        Plan plan = option.getPlan();
        
        String removeString = String.valueOf(plan.getTerm(0));

        // Use regular expression to remove non-digit characters
        String result = removeString.replaceAll("[^0-9]", "");
        
        
        int planNum = Integer.parseUnsignedInt(result);
        
        System.out.println("Plan Number " + Integer.parseUnsignedInt(result));
     //   planNumber.add
        
    	

    //    Set<Action> plan_actions_list = new HashSet<Action>();

       
        
        
        
       
        
      
        
        
        
     //   System.out.println("Plan Body "+plan_body);
        Term fuzzyAnnot = Literal.parseLiteral("fuzzy");  
    	PlanBody plan_body = plan.getBody();

        while(plan_body != null){

            if(plan_body.getBodyType() == BodyType.action){

                Literal body_term = (Literal)plan_body.getBodyTerm();
                
          //      System.out.println("Body Term "+body_term);

                if(body_term.hasAnnot(fuzzyAnnot)){
                	
               // 	option.getPlan().delTerm(3); // this requires an optimal solution where the plan has only one fuzzy action .
                	
                	 if (!planNumberList.contains(planNum))
                	 { // Plan Number List (planNumberList) is required not to add the term over and over again in each selected cycle.
                     	
                     	
                     	
                     //	System.out.println("### BEFORE BODY TERM "+body_term);
                     	body_term.addTerm(Literal.parse(String.valueOf(planMaxMem)));
                     //	System.out.println("### AFTER BODY TERM "+body_term);
                     }
                	 else 
                	 {
                		 
                		 body_term.setTerm(body_term.getTerms().size()-1, Literal.parse(String.valueOf(planMaxMem)));
                	 }
                	
                	

                	
                	
                //	System.out.println("\n Fuzzy Annot Captured and Modified "+body_term + "* "+ body_term.getTerms().size() + "*" + option.getPlan() + planNumberList);
                	
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
    
    public static double triangular (double s, double m, double e, double val)
    {

       // System.out.println("Membership parameters are"+ s+" "+m+" "+e+" "+val);

        double start = s;
        double mid= m;
        double end = e;

      //  int[] val  = new int[] {6,37,77,80,106};

        double result=0.0;


            if (val >= start && val <= mid){

                result = (val-start) / (mid-start);

            }

            if (val>=mid && val<=end){

                result = (end-val) / (end-mid);
            }

           return  result;


    }

    public static double gauss (double mean, double stdev, double val) {

        double result=0.0;

        result = (Math.exp(-1*(Math.pow((val-mean),2))/(2*Math.pow(stdev,2))));

        return result;
    }

    public static  double trape (double a, double b, double c, double d, double val)
    {

        double result = 0.0;

        if  (val>=a && val <=b)
        {
            result =  (val - a) / (b-a);
        }
        else if ((val>=b && val <=c)) {

            result = 1.0;
        }
        else if ((val>=c && val <=d)) {

            result =  (d - val) / (d-c);
        }
        else if (val>=d) {
            result = 0.0;
        }

        return result;
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
    
class StructureNode{
	
	 String value="";
	 Double beliefValue= Double.MIN_VALUE;
	 
	 StructureNode left;
	 StructureNode right;
	 
	 Structure st;
	 
	 StructureNode (Structure st, String value)
	 {
		 this.st = st;
		 this.value = value;		 
		
		 
		// System.out.println(this.st+ " *** "+this.value);
	 }
	 
	 public StructureNode() {
	
	}
	
	
}    
    
    
class FuzzyObject {
	
		private String inputVarName;  // such as red
        private String termName; // such as low middle full
        private String FunctionType; // trian trape gauss etc
        private ArrayList<Double> points; // double type params for these function types such as 110.0, 145.0, 255.0, 255.0
        private String AgentName;
        private Double result = 0.0;
        

        public FuzzyObject(String AgentName, String termName, String FunctionType, ArrayList<Double> points)
        
        {        	
        	this.AgentName = AgentName;
        	this.termName = termName;
            this.FunctionType = FunctionType;
            this.points = points;
           
        }


    }
    
}
	
