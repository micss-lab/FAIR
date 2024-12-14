import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSemantics.Message;
import jason.asSemantics.Option;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger;
import jason.bb.BeliefBase;
import jason.infra.centralised.CentralisedAgArch;
import jason.runtime.RuntimeServices;


public class SortAgentArch extends AgArch {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4870008488788266203L;
	
	private static boolean isFuzzyEvent = false;

	//private AgArch successor = null;
	
	 private AgArch successor = null;
	 
	 private String holder = "";
	
	@Override
    public void reasoningCycleStarting() throws NullPointerException  { //reasoningCycleStarting reasoningCycleFinished
		
		
		
		LogicalFormula context = null;
		List<Option> RPs = null;
		List<Option> APs = null;
		try {
			
			Trigger tg = getTS().getC().getSelectedEvent().getTrigger();
		     if (tg.toString().contains("fuzzy")) {
		        	
		        	isFuzzyEvent = true;
		        	
		        	
		       }
			
		
			
			
			
			
		    
			RPs = getTS().getC().getRelevantPlans(); //getSelectedOption().getPlan().getContext();
			APs = getTS().getC().getApplicablePlans();
			
			
		} catch (Exception e) {
			// ignore if there is not any context or cycle does not hold one.
		}
		
		if(RPs!=null) {
			
			if  (isFuzzyEvent) {
				
				
				
				
				
				System.out.println(" BEGINS ***********************************************************\n");
				System.out.println(RPs);
				System.out.println(" ***********************************************************\n");
		//		System.out.println("APS "+APs + " ** " + getTS().getAg().getPL().getAllRelevant(te));
				System.out.println(" ***********************************************************\n");
				System.out.println(getTS().getC().getSelectedOption());
				System.out.println(" ENDS ***********************************************************\n");
				
				
				//APs.clear();
				//APs.addAll(RPs);
				
				isFuzzyEvent = false;
			}
			
			
			
		
			
			
			
			
			
			
			
		//	holder = String.valueOf(context);
		//	System.out.println("Holder is "+ holder);
		} // YUKARIDAKI 99999 sorgusunu act'de yapmaya calis.
		
		
        if (successor != null)
            successor.reasoningCycleStarting();
    }
	
	
	
	
	@Override
	  public void act(ActionExec action) {
		
		
		
			/*
			
			Term fuzzyAnnot = Literal.parseLiteral("fuzzy");  
			
		
				
			if (action.getActionTerm().getAnnots() !=null && String.valueOf(action.getActionTerm().getAnnots()).contains("fuzzy") ) { // alternative action.getActionTerm().toString().contains("fuzzy")
				System.out.println(action.toString());
				
				//final Term memDeg = Literal.parse("22"); // trial parse
				
			//	action.getActionTerm().addTerm(memDeg); // trial addition
				
			//	System.out.println("Action Intention as Term"+action.getIntention().);
				
				System.out.println(" Arch | Context Functors as ordered "+extractContextFunctors(holder));
				
				
				
				  BeliefBase beliefs = getTS().getAg().getBB();
				  
				
					
				
				for (Literal belief : beliefs)
				{
                    if (belief.getFunctor().contains("isitRed") && belief.hasAnnot(fuzzyAnnot))
                    {
                    	
                        List<Term> terms = belief.getTerms();
                        for (Term term : terms) 
                        { // get the values from here.
                        	System.out.println(" Arch | ALL TERMS "+term.toString()+" with value of "+ SortAgentEx.linguisticTermsRed.get(term.toString())+ belief.getAnnots().toString());
                        	action.getActionTerm().addTerm(Literal.parse(SortAgentEx.linguisticTermsRed.get(term.toString()).toString()));
                        }
                    }
                 }

				
				
				System.out.println(" Arch | LAST TERM "+ action.getActionTerm().toString() + " with the ContextHolder is " +holder);
			}
			*/
			super.act(action);
		
			
		

    }
	
	public List<String> extractContextFunctors (String text) {
		
		//  text = "dropped(false) | emergencyLocked(true)";

	        // Define a regular expression pattern to capture function names and their arguments
	        Pattern pattern = Pattern.compile("(\\w+)\\((\\w+)\\)");

	        // Create a Matcher object and find matches in the text
	        Matcher matcher = pattern.matcher(text);

	        // List to store function names and arguments
	        List<String[]> functions = new ArrayList<>();

	        // Find all matches and store them in the list
	        while (matcher.find()) {
	            String functionName = matcher.group(1);
	            String argument = matcher.group(2);
	            functions.add(new String[]{functionName, argument});
	        }

	        // Display the order
	        List<String> order = new ArrayList<>();
	        for (String[] function : functions) {
	            order.add(function[0]);
	        }

	     //   System.out.println("Function order: " + order);
	        
	        return order;
	    }
		
	


}
