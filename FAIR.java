//package savi_ros_java.savi_ros_bdi.agent_core;

import jason.JasonException;
import jason.RevisionFailedException;
import jason.asSemantics.*;
import jason.asSyntax.*;
import jason.bb.BeliefBase;
import jason.bb.StructureWrapperForLiteral;

import java.io.StringReader;
import java.util.*;
import java.util.regex.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import jason.asSemantics.Agent;
import jason.asSemantics.Option;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Plan;
import jason.asSyntax.PlanBody;
import jason.asSyntax.PredicateIndicator;
import jason.asSyntax.PlanBody.BodyType;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger.TEOperator;
import jason.asSyntax.Trigger.TEType;
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

public class FAIR extends Agent {
	
	public static double planMaxMem = -0.1;
	
	public static  ArrayList<Integer> planNumberList = new ArrayList<>();

    /**
	 * 
	 */
	private static final long serialVersionUID = 1123932742485166865L;
	protected static List<String> FuzzyEvents = new ArrayList<>();
	protected static boolean isFuzzyEvent = false;
	protected static Trigger EventTrigger;

    public FAIR() {
        super();
        this.setFuzzyMaps();
    }

    /**
     * Specify the fuzzyEvents for the fuzzy option selection function.
    **/
    // Container to hold multiple HashMaps
    Map<String, FuzzyObject[]> fuzzyBeliefsToMemberships = new HashMap<>();
    protected void setFuzzyMaps() {
    	
     
    }
    
    
    
    /** Belief Update Function: adds/removes percepts into belief base.
    *
    *  @return the number of changes (add + dels)
    */
    @Override
   public int buf(Collection<Literal> percepts) {
       /*
       // complexity 3n

       HashSet percepts = clone from the list of current environment percepts // 1n

       for b in BBPercept (the set of perceptions already in BB) // 1n
           if b not in percepts // constant time test
               remove b in BBPercept // constant time
               remove b in percept

       for p still in percepts // 1n
           add p in BBPercepts
       */

       if (percepts == null) {
           return 0;
       }

    
       int adds = 0;
       int dels = 0;
  

       // to copy percepts allows the use of contains below
       Set<StructureWrapperForLiteral> perW = new HashSet<>();
       Iterator<Literal> iper = percepts.iterator();
       while (iper.hasNext()) {
           Literal l = iper.next();
           if (l != null)
           {
        	   StructureWrapperForLiteral ll= new StructureWrapperForLiteral(l);
               perW.add(ll);
     
           }
       }


       // deleting percepts in the BB that are not perceived anymore
       Iterator<Literal> perceptsInBB = getBB().getPercepts();
       while (perceptsInBB.hasNext()) {
           Literal l = perceptsInBB.next();
       
           
           Literal anmu = l.getAnnot("mu");
        
           boolean hasMu = false;
           if(anmu!=null)
        	   hasMu = true;
           
           
         
           boolean hasPerc = perW.contains(new StructureWrapperForLiteral(l));
    
          // if (l.subjectToBUF() && ! perW.remove(new StructureWrapperForLiteral(l))) { // l is not perceived anymore
           
           if (l.subjectToBUF() && ((! hasPerc) || (hasPerc && hasMu) )) {
        	   
               dels++;
               perceptsInBB.remove(); // remove l as perception from BB

               // new version (it is sure that l is in BB, only clone l when the event is relevant)
               Trigger te = new Trigger(TEOperator.del, TEType.belief, l);
               if (ts.getC().hasListener() || pl.hasCandidatePlan(te)) {
                   l = ASSyntax.createLiteral(l.getFunctor(), l.getTermsArray());
                   l.addAnnot(BeliefBase.TPercept);
                   te.setLiteral(l);
                   ts.getC().addEvent(new Event(te, Intention.EmptyInt));
               }
           }
           else
           {
        	   perW.remove(new StructureWrapperForLiteral(l));
           }
        	   
       }

       /*
           ////// previous version, without perW hashset
           // could not use percepts.contains(l), since equalsAsTerm must be
           // used (to ignore annotations)
           boolean wasPerceived = false;
           Iterator<Literal> ip = percepts.iterator();
           while (ip.hasNext()) {
               Literal t = ip.next();

               // if perception t is already in BB
               if (l.equalsAsStructure(t) && l.negated() == t.negated()) {
                   wasPerceived = true;
                   // remove in percepts, since it already is in BB
                   // [can not be always removed, since annots in this percepts should be added in BB
                   //  Jason team for AC, for example, use annots in perceptions]
                   if (!l.hasAnnot())
                       ip.remove();
                   break;
               }
           }
           if (!wasPerceived) {
               dels++;
               // new version (it is sure that l is in BB, only clone l when the event is relevant)
               perceptsInBB.remove(); // remove l as perception from BB

               Trigger te = new Trigger(TEOperator.del, TEType.belief, l);
               if (ts.getC().hasListener() || pl.hasCandidatePlan(te)) {
                   l = l.copy();
                   l.clearAnnots();
                   l.addAnnot(BeliefBase.TPercept);
                   te.setLiteral(l);
                   ts.getC().addEvent(new Event(te, Intention.EmptyInt));
               }
       */
       /*
       // even older version
       // can not delete l, but l[source(percept)]
       l = (Literal)l.clone();
       l.clearAnnots();
       l.addAnnot(BeliefBase.TPercept);
       if (bb.remove(l)) {
           ts.updateEvents(new Event(new Trigger(TEOperator.del, TEType.belief, l), Intention.EmptyInt));
       }
       */
       //}
       //}

       for (StructureWrapperForLiteral lw: perW) {
           try {
               Literal lp = lw.getLiteral().copy().forceFullLiteralImpl();
               lp.addAnnot(BeliefBase.TPercept);
               if (getBB().add(lp)) {
                   adds++;
                   ts.updateEvents(new Event(new Trigger(TEOperator.add, TEType.belief, lp), Intention.EmptyInt));
               }
           } catch (Exception e) {
               logger.log(Level.SEVERE, "Error adding percetion " + lw.getLiteral(), e);
           }
       }

       //if (qCache != null)
       //    qCache.reset();
       //if (qProfiling != null)
       //    qProfiling.newUpdateCycle(getTS().getUserAgArch().getCycleNumber(), adds+dels, System.nanoTime()-startTime);
       return adds + dels;
   }


   /*
   public QueryCacheSimple getQueryCache() {
       return qCache;
   }
   public QueryProfiling getQueryProfiling() {
       return qProfiling;
   }
   */
    
    
    
    

    /**
     * Highest priority event has priority
     * Otherwise, first event in the queue is chosen.
     */
    @Override
    public Event selectEvent(Queue<Event> events) {
        Event selected;
        
        
     
        
   
            selected = super.selectEvent(events);

        events.remove(selected);
	
        
        Term fuzzyAnnot = Literal.parseLiteral("fuzzy"); 
    
        
        if (selected.getTrigger().toString().contains("fuzzy")) {
        	
        	isFuzzyEvent = true;
        	
        	
        }
        
     
    
			
			
			
			
			
			
			

		
        
        EventTrigger = selected.getTrigger();
		
        return selected;
    }
    
    

    
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

    
    @Override
    public Intention selectIntention(Queue<Intention> intentions) {
    	
    	Iterator<Intention> ii = intentions.iterator();
    	
    	while (ii.hasNext()) {
    		Intention i = ii.next();
   
    		
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
		         
         
        }
	
		         Term fuzzyAnnot = Literal.parseLiteral("fuzzy");    
		      
		         
		        
        if (options.size() > 0 && isFuzzyEvent ) //&& (!options.get(0).getPlan().getTrigger().toString().contains("-")) 
        {
        	
        	
  
            try {
				selected = this.selectOptionAsFuzzy(options);
				
	
				
			} catch (RevisionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  // change the name afterwards
        } else {
            selected = super.selectOption(options);
        }
             
        isFuzzyEvent = false;  // clear the isFuzzyEvent flag
        options.remove(selected);
        selected = getPlanActions(selected);
   
        return selected;

    }
    
    
    
    public StructureNode addStructureNodeRecursive (Structure anyContext)
    {
    	
 
    	
    	StructureNode a = null;
    	
  
    	
    	if (String.valueOf(anyContext.getFunctor()).matches("[a-zA-Z]+"))
    	{
    		
    		
    		a = new StructureNode(anyContext, String.valueOf(anyContext));
    	
    		return a;
    	}
    	
    	else
    	{
    		
    
    		
    		a = new StructureNode(anyContext, String.valueOf(anyContext.getFunctor()));
    		
    		if (anyContext.getTermsSize() >1 ) // left and right hand sides.
    		{ 
    			
    			
    			
    			
    			
    		
    			
    				
    				if (!(String.valueOf(anyContext.getTerm(0)).matches("^[-+]?\\d*\\.?\\d+$")))
    				{
    					
    	
    					a.left  = addStructureNodeRecursive(Structure.parse(String.valueOf(anyContext.getTerm(0))));
    				}
    				
    				if (!(String.valueOf(anyContext.getTerm(1)).matches("^[-+]?\\d*\\.?\\d+$")))
    				{
    		
    					a.right = addStructureNodeRecursive(Structure.parse(String.valueOf(anyContext.getTerm(1))));
    				
    				}
    				
    		

	    			if ((String.valueOf(anyContext.getTerm(0)).matches("^[-+]?\\d*\\.?\\d+$")))
	    				{
	    				
	    	
	    					a.left = new StructureNode();    				
	    					a.left.beliefValue = Double.parseDouble(String.valueOf(anyContext.getTerm(0)));
	    				
	    
	    				
	    				}
	    				
	    			if ((String.valueOf(anyContext.getTerm(1)).matches("^[-+]?\\d*\\.?\\d+$")))
	    				{
	    				
	    	
	    					a.right = new StructureNode();    				
	    					a.right.beliefValue = Double.parseDouble(String.valueOf(anyContext.getTerm(1)));
	    				
	    	
	    				
	    				}
	    				
	    				return a;
    				
    				
    			  
    		}else
    		{ 
    			a.left = addStructureNodeRecursive(Structure.parse(String.valueOf(anyContext.getTerm(0))));
    		}
    		
    		
    	}
    	
    	
    		
    	
    	return a;
    	
     } 
    
      public StructureNode findLeafNodesRecursive (StructureNode anyContext)
      {
	  
    	  
    	  if (anyContext!=null)
    	  {
	    	
	    	if (anyContext.left==null && anyContext.right==null)
	    
	    	{
	    		
	    		if (anyContext.beliefValue == Double.MIN_VALUE) {
	    		
	    		anyContext.beliefValue = findBeliefValue(anyContext.value);
	    		
	    		}
	    		
	    		
	    		
	    		
	    		
	  
	    		
	    	}
	    	
	    	
	    	findLeafNodesRecursive(anyContext.left);
    		findLeafNodesRecursive(anyContext.right);
    	
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
        		
        			 
        			 beliefValue = Double.valueOf(String.valueOf(belief.getAnnot("mu").getTerm(0))); // get the membership degree here.
        	
        		 }
        		 
        		 
        		 
        		 
        	 } // end of for loop
        	
        		 
        	
    	
        	 	return beliefValue;
    	  
      } 
      
      public double traverseStructureTree (StructureNode anyContext) {
    	  

    	
    	    // Leaf node i.e, an integer
    	    if (anyContext.left == null && anyContext.right == null) {
    	    	
    	   // 	System.out.println("Left and right are null 1, returning");
    	    	
    	        return anyContext.beliefValue;
    	        
    	    }
    	 
    	    double leftEval=Double.MIN_VALUE;
    	    double rightEval=Double.MIN_VALUE;
    	    
    	    try {
    		    // Evaluate left subtree
    	    	leftEval = traverseStructureTree(anyContext.left);
        	    
           	 
        	    // Evaluate right subtree
    	    	rightEval = traverseStructureTree(anyContext.right);
    	    	
    	  //  	System.out.println("LEFT AND RIGHT "+ leftEval + "**" + rightEval);
    	    }
      		catch (NullPointerException e) {
	    	
			// System.out.println(" NULL pointer exception"+ leftEval + " " + rightEval);

    	    }
	   
	    
	 
	
	 
    	    
    	    
    	    // Check which operator to apply
    	    if (anyContext.value.hashCode()==34628) { // | OR = max
    	    	
    	 
    	    	return Math.max(leftEval, rightEval)  ;  //leftEval + rightEval
    	    	
    	    }
    	    
    	    if (anyContext.value.hashCode()==32706) { //  > operator
    	    	
    	   // 	System.out.println(" "+leftEval+"*"+rightEval);
    	    	if (leftEval>rightEval)
    	    	return 1.0;
    	    	else
    	    	return 0.0;  //leftEval + rightEval
    	    	
    	    }
    	    
    	    if (anyContext.value.hashCode()==32644) { //  < operator
    	    	
    	   // 	System.out.println(""+leftEval+"*"+rightEval);
    	    	if (leftEval<rightEval)
    	    	return 1.0;
    	    	else
    	    	return 0.0;  //leftEval + rightEval
    	    	
    	    }
    	    
    	    if (anyContext.value.hashCode()==32675) { //  = operator
    	    	
    	  //  	System.out.println("2323"+leftEval+"*"+rightEval);
    	    	if (leftEval==rightEval)
    	    	return 1.0;
    	    	else
    	    	return 0.0;  //leftEval + rightEval
    	    	
    	    }  
    	    
    	    
    	    
    	    if (anyContext.value.hashCode()==1014817) { //  <=  1014817
    	    	
    	
    	    	if (leftEval>=rightEval)
    	    	return 1.0;
    	    	else
    	    	return 0.0;  
    	    	
    	    } 
    	    
    	    if (anyContext.value.hashCode()==1012895) { //  <=  1012895
    	    	
    	  
    	    	if (leftEval<=rightEval)
    	    	return 1.0;
    	    	else
    	    	return 0.0;  
    	    	
    	    } 
    	        
    	 
    	    if (String.valueOf(anyContext.value).equals("-")) {
    	    	
    	
    	    	return leftEval - rightEval;
    	    	
    	    }
    	        
    	    
    	 
    	    if (anyContext.value.hashCode()==31962) { // * AND = min
    	    	
    	    	 
    	  //  	System.out.println("MATCHES &");
    	    	 return Math.min(leftEval, rightEval);  //leftEval * rightEval
    	    }
    	      
    	    if (String.valueOf(anyContext.value).equals("/")) {
    	    	
    	    	return leftEval / rightEval;
    	    }
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    if (anyContext.value.hashCode()==3387309) { // this is for [not]
    	    	
    	    	
    	
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
        
	
    protected Option selectOptionAsFuzzy(List<Option> options) throws RevisionFailedException {
    	
  
    	
    	try {
			options = getTS().relevantPlans(this.EventTrigger);
		} catch (JasonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
    	
    	
    	
    
    
    	
    	////
    	 String  CapturedFuzzyBelief ="";
    	 BeliefBase beliefs = this.getBB();
    	
    	 
    	 
    	 
    	 // input variables.
         List<Double> rgb_values = new LinkedList<Double>(); 

         Term fuzzyAnnot = Literal.parseLiteral("fuzzy"); 
     

        Option priorityOption = null;    // The event that has the highest priority so far

        // Iterate through the list, look for the "champion"
        Iterator<Option> optionInstance = options.iterator();
        
 
        
        
        
        // Track first check (for the contingency)
        boolean first = true;
        int z=0;
        int maxIndex=0;
        double maxDegree=0.0;
        double planTriggeringDegree = 0.0;
        
        for (int i = 0; i < options.size(); i++) {
			
  
        	
        	
        	Option current = options.get(i);
            Plan currentPlan =  current.getPlan();
            LogicalFormula context = currentPlan.getContext();
            
            if (context!=null) {
	            	z++;
	  //          System.out.println(" LLLLLLLLLL LOGICAL CONTEXT LLLLLLLLLLL " +  z + " " + context.toString());
	            
	            
	        	Structure st  = Structure.parse(String.valueOf(context).replaceAll("\\|\\s*true", ""));
	     		
	     		
	     		
		//		System.out.println(st + "******" + st.getTerms() + " **** " + st.getFunctor() + " " + st.getFunctor().hashCode());
			
			
				StructureNode root = 	addStructureNodeRecursive(st);
	
			
				root = findLeafNodesRecursive(root);
				
				planTriggeringDegree = traverseStructureTree(root);
				
		//		System.out.println(" PLAN TRIGGERING DEGREE "+planTriggeringDegree);
				
				if (planTriggeringDegree>maxDegree)
				{
					
					maxIndex = i;
					maxDegree = planTriggeringDegree;
				}
				
			
		//		System.out.println("** END RESULT "+ i + " triggeringDegree= " +planTriggeringDegree + " maxIndex= " + maxIndex);
			//	System.out.println("-- "+ root.left.left.beliefValue +"**" + root.value+ "****" +root.right.value);
	            
	            
	            
            }
		}
        
        
        
    	planMaxMem = maxDegree;   // modify the end result of the logical context.  	
    	priorityOption = options.get(maxIndex);
        return priorityOption;
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
     //   System.out.println("Plan Body "+plan_body);
        Term fuzzyAnnot = Literal.parseLiteral("fuzzy");  
    	PlanBody plan_body = plan.getBody();

        while(plan_body != null){

            if(plan_body.getBodyType() == BodyType.action){

                Literal body_term = (Literal)plan_body.getBodyTerm();
                        
                if(body_term.hasAnnot(fuzzyAnnot)){
                	                          	
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
	
