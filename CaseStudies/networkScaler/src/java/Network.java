

import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;
import jason.environment.grid.Location;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Logger;










public class Network  extends Environment {
	
	
	ArrayList<Map.Entry<String, Long>> agentTimeList = new ArrayList<>();
	
	public static final Term   nsRev = Literal.parseLiteral("next(slotRev)");   
    public static final Term    ns = Literal.parseLiteral("next(slot)");    
    public static final Term    pg = Literal.parseLiteral("pick(garb)");
    public static final Term    dg = Literal.parseLiteral("drop(garb)");
	
	public static final Term    pgRev = Literal.parseLiteral("pickRev(garb)");
    public static final Term    dgRev = Literal.parseLiteral("dropRev(garb)");
	
    public static final Term    bg = Literal.parseLiteral("burn(garb)");
    public static final Literal g1 = Literal.parseLiteral("garbage(r1)");
    public static final Literal g2 = Literal.parseLiteral("garbage(r2)");
    public static final Literal g3 = Literal.parseLiteral("garbage(r3)");
    
    
    public static final Literal getDirtIntensityValues = Literal.parseLiteral("getDirtIntensityValues");
    
    
    public static Instant instantR1;
    public static Instant instantR3;
    
    public static Instant instantR1END;
    public static Instant instantR3END;
    
    public static float intensityDirtTotalBegin=0.0f;
    public static float intensityDirtTotalEnd=0.0f;
    
    public static final Literal startTimePeriod = Literal.parseLiteral("startTimePeriod");
    
    
    
    
    
    
  
    
    
    public static boolean initialMapFlag=true;
    
    public static int counter=0;
    
    public static int  currentIndex = -1;
    
    public static double responseTime=0.0;
    
    
    public static final Literal checkWorkLoad = Literal.parseLiteral("checkWorkLoad");
    
    public static final Literal checkWorkLoadBool = Literal.parseLiteral("checkWorkLoadBool"); 
    
    public static final Literal consumeWorkLoad = Literal.parseLiteral("consumeWorkLoad"); 
    
    public static final Literal getWorkLoadBool = Literal.parseLiteral("getWorkLoadBool"); 
    
    
    
    public static String turn;
    public static String turnScope;
    		
    public static String textName;
    
    public static String MapDataPath;
    
    
    public static final Literal scaleFactor = Literal.parseLiteral("scaleFactor"); 
    
    public static boolean saveFlag=true;
    public static boolean areObstaclesDrawn = false;
    
    public static int workLoad=0;
    public static int arrivalTurns=0;
    public static int sumWorkload=0;
    public static int workloadTurnCounter=0;
	
	
    NetworkScaler netsc = new NetworkScaler();
	
	
	
    public static int[] values = new int[500]; // Fixed size array
    public static int[] arrivals = new int[500];
    
    public static ArrayList<Long> executionTimes = new ArrayList<Long>();
    		
    public static int totalMach=0;
    
    public static Instant start;
	
	
	static Logger logger = Logger.getLogger(Network.class.getName());
	
    @Override
    public void init(String[] args) {
    	
    	
    	
    	turn = String.valueOf(args[0]); // 10.0;
    	turnScope = String.valueOf(args[1]); //2000.0;
    	textName= turnScope+String.valueOf(args[2]);
    	
    	  
     //   turn="1";
     //   turnScope="250-350";    		
    //    textName= turnScope+"_boolean_";
        
        
        
        
        
        
        // Specify the path to your data file
        MapDataPath = "C:\\Users\\Burak\\eclipse-workspace_earlier\\networkScaler\\src\\java\\inputs\\"+turnScope+"\\Workload_"+turnScope+"_Iteration"+turn+".txt"; // Change this to your file path


  
    	
    	
    	

    	
    	
    	 loadValuesFromFile(MapDataPath);
    	 
    	 // Calculate the sum of values[0]
         
         for (int i = 0; i < values.length; i++) {
             sumWorkload += values[i];
         }

    	 
    		removePerceptsByUnif("agent1",Literal.parseLiteral("currentWorkLoad(_)"));
        	Literal literal_currentWorkLoad = Literal.parseLiteral("currentWorkLoad(0)");
    		addPercept("agent1",literal_currentWorkLoad);
    		
    		removePerceptsByUnif("agent1",Literal.parseLiteral("currentWorkLoad(_)"));
        	Literal literal_currentWorkLoadFuzzy = Literal.parseLiteral("currentWorkLoad(0)");
    		addPercept("agentFuzzy",literal_currentWorkLoadFuzzy);
    		
    		
    		Literal literal_workLoadLow = Literal.parseLiteral("workLoad(low)[mu(0)]");
        	addPercept("agentFuzzy",literal_workLoadLow);
        	
        	
    		Literal literal_arrivedTurn = Literal.parseLiteral("arrivedTurn(0)");
        	addPercept("agent1",literal_arrivedTurn);      	        
        	addPercept("agentFuzzy",literal_arrivedTurn);
        	
        	
        	
          	Literal literal_responseTimeMin = Literal.parseLiteral("responseTime(bad)[mu(0)]");
        	addPercept("agentFuzzy",literal_responseTimeMin);
    	
    	//loadValuesFromFile(MapDataPath);
    
        //updatePercepts();
        
        
   /*     try {
			System.setOut(new PrintStream(new FileOutputStream("C:\\Users\\Burak\\eclipse-workspace_earlier\\networkScaler\\src\\java\\Workload1_OutBoolean.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
       

 	
 	
        
     //   Literal literal_vacuumBagStatusR1 = Literal.parseLiteral("vacuumBagFull(r1,empty)");
    //	addPercept("r1",literal_vacuumBagStatusR1);
     	
    	//     	Literal literal_vacuumBagStatusR3 = Literal.parseLiteral("vacuumBagFull(r3,empty)");
    	// addPercept("r3",literal_vacuumBagStatusR3);
        
     	
     	
     	
    	
    	//  Literal literal_batteryChargeStatusR1 = Literal.parseLiteral("batteryCharge(r1,full)");
    	//  addPercept("r1",literal_batteryChargeStatusR1);
        
    	//  Literal literal_batteryChargeStatusR3 = Literal.parseLiteral("batteryCharge(r3,full)");
    	//  addPercept("r3",literal_batteryChargeStatusR3);
        		
        

       
        
    }
	
    
    @Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);
        
        try {
            if (action.equals(ns)) {
          
            } else if (action.equals(nsRev)) {
              
            }
			else if (action.getFunctor().equals("move_towards")) {
                int x = (int)((NumberTerm)action.getTerm(0)).solve();
                int y = (int)((NumberTerm)action.getTerm(1)).solve();
           
            }
		
			else if (action.equals(pg)) {
           
            } else if (action.equals(dg)) {
            
			}else if (action.equals(getWorkLoadBool)) {
				
				
				System.out.println(" Total Cost "+netsc.getTotalCost());
				
				getWorkLoadBool(ag);
              
            } else if (action.equals(consumeWorkLoad)) {
         
            	consumeWorkLoad(ag);
            	
            } else if (action.getFunctor().equals("scaleFactor")) {
            	int scaleFactorParam = (int)((NumberTerm)action.getTerm(0)).solve();
            //    double vacuumPowerConstant = (double)((NumberTerm)action.getTerm(1)).solve();

                scaleFactor(scaleFactorParam);
                
                
            }
            
             else if (action.equals(getDirtIntensityValues)) {
	        
	        	 
	        	 System.out.println("###############################################################################################################");
	       // 	 printTheMap("Initial-Map ",initialMap);
	        	 System.out.println("---------------------------------------------------------------------------------------------------------------");
	     //   	 printTheMap("End-Map ",garbageHolder);
	        	 
             }
            
            
            
            else if (action.equals(checkWorkLoad)) {
             
            	checkWorkLoad(ag);
            }
            
            else if (action.equals(checkWorkLoadBool)) {
            	checkWorkLoadBool(ag);
            }
            
            
            else if (action.getFunctor().equals("scaleFactorF")) {
                int scaleFactor = (int)((NumberTerm)action.getTerm(0)).solve();
                double degree = (double)((NumberTerm)action.getTerm(1)).solve();
                
                
                
                System.out.println("SCALE FACTOR: "+scaleFactor +" Degree: " +degree );
        		netsc.setNumberOfMachines((int)Math.round(scaleFactor*degree));
           
            }
            
            
            else if (action.equals(startTimePeriod)) {
                  
            } else {
            	
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

     //   updatePercepts();

        try {
            Thread.sleep(1);
           
        } catch (Exception e) {}
        informAgsEnvironmentChanged();
        return true;
    }
	
	private void scaleFactor(int scaleFactorParam) {
		System.out.println("SCALE FACTOR "+scaleFactorParam);
		netsc.setNumberOfMachines(scaleFactorParam);
	}


	public void startTimePeriod(String ag) {
		
    
        	instantR1 =  Instant.now();	
      
     
        	
}
	
	
    private void loadValuesFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0; // Local index for the array
            while ((line = br.readLine()) != null && index < values.length)
            {
            	
            	String[] val = line.split(", ");
                
                // Parse values and add to the respective lists
            
                	values[index] = (Integer.parseInt(val[0]));  // 2 ye bolunce daha iyi calisti
                	arrivals[index] = Integer.parseInt(val[1]);  
                	
                	
                    System.out.println( "VAL: "+values[index] + " Arrival Turn:" + arrivals[index] + " ");
                    index = index+1;
                
            	
                
                
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        }
    }
	
	
    
    
    
    
    
    
    // Method to get the next float value
    public Integer[] getNextValue() {
    	
    	 Integer[] currentWorkloadAndArrival;
    	 
        if (currentIndex < values.length) {
        	currentIndex= currentIndex+1;
        	
            currentWorkloadAndArrival = new Integer[] { values[currentIndex], arrivals[currentIndex] };

        	
            return currentWorkloadAndArrival;
        } else {
        
        	
        	
            
            String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\networkScaler\\src\\java\\Sonuclar\\"+turnScope+"\\Result-" 
                                   + textName + "-" + turn + ".txt";
            
            String fileExecutionTimePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\networkScaler\\src\\java\\Sonuclar\\"+turnScope+"\\ExecutionTime-" 
                    + textName + "-" + turn + ".txt";

            // Write the sum to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathTotal))) {
                writer.write(totalMach+" "+workloadTurnCounter);
                System.out.println("File created successfully at " + filePathTotal);
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
            
            // Write the sum to the file
            try (BufferedWriter writer2 = new BufferedWriter(new FileWriter(fileExecutionTimePathTotal,true))) {
            	
            	for (int i=0; i< executionTimes.size(); i++)
            	{
            	
            		writer2.write(String.valueOf(executionTimes.get(i)+" \n "));
            	}
            	
              //  writer.write(totalMach+" "+workloadTurnCounter);
                System.out.println("File created successfully at " + filePathTotal);
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        	
            
            
        
        	System.exit(0);
        	
        	
            return null; // No more values to retrieve
        }
    }
    
    
    public void getWorkLoadBool (String ag) {
    	Integer arrivedWorkload;
    	Integer[] arrivedWorkloadAndTurn;
    	
    	arrivedWorkloadAndTurn = getNextValue();
    	
    	arrivedWorkload =  arrivedWorkloadAndTurn[0];
    	arrivalTurns =   arrivedWorkloadAndTurn[1];
    	
    	workLoad +=  arrivedWorkload;
    
    	
    	
    	System.out.println("************* Arrived Workload: "+arrivedWorkload+ " Current Workload: "+workLoad + " Arrival Turns: "+arrivalTurns);
    	
    	
    	
    	removePerceptsByUnif(ag,Literal.parseLiteral("arrivedWorkLoad(_)"));
    	
    	Literal literal_arrivedWorkLoad = Literal.parseLiteral("arrivedWorkLoad("+workLoad+")");
		addPercept(ag, literal_arrivedWorkLoad);
    	
		removePerceptsByUnif(ag,Literal.parseLiteral("arrivedTurn(_)"));
    	
    	Literal literal_arrivedTurn = Literal.parseLiteral("arrivedTurn("+arrivalTurns+")");
		addPercept(ag, literal_arrivedTurn);
    	
    	
    }
	
	public void checkWorkLoadBool(String ag)
    { 
		
	
		
		
	 	start =  Instant.now();	
   		
      //  Duration elapsedR3 = Duration.between(start, end);
		
		
		
		
	
		responseTime=netsc.calculateResponseTime(workLoad);
		
		
		
        removePerceptsByUnif(ag,Literal.parseLiteral("workLoad(_)"));
        
        
			
			if (workLoad <= 100.0) 
			{ 
			
			Literal literal_workLoadLow = Literal.parseLiteral("workLoad(low)");
			addPercept(ag, literal_workLoadLow);
			}
			
			if (workLoad > 100.0 && workLoad <= 400.0) 
			{ 
			
			Literal literal_workLoadMedium = Literal.parseLiteral("workLoad(medium)");
			addPercept(ag, literal_workLoadMedium);
			}
			

			if (workLoad > 400.0 )
			{ 
			
			Literal literal_workLoadHigh = Literal.parseLiteral("workLoad(high)");
			addPercept(ag, literal_workLoadHigh);
			}
			
			
		
			
			
			removePerceptsByUnif(ag,Literal.parseLiteral("responseTime(_)"));
			
			// Vacuum Bag - Boolean Logic
			if  (responseTime <= 5.0) { // true if vacuumCapacity is 0
			
			Literal literal_responseTimeLow = Literal.parseLiteral("responseTime(good)");
			addPercept(ag, literal_responseTimeLow);
			
			}

			if (responseTime > 5.0 && responseTime <= 10.0) { // true if vacuumCapacity is between 500 and 1000
			
			Literal literal_responseTimeMedium = Literal.parseLiteral("responseTime(ok)");
			addPercept(ag, literal_responseTimeMedium);
			}
			if (responseTime > 10.0) { // true if vacuumCapacity is greater than 1000
		
			Literal literal_responseTimeMax = Literal.parseLiteral("responseTime(bad)");
			addPercept(ag, literal_responseTimeMax);
			}
		
			
		  

			
			
		
    }
	
	
	
	public void checkWorkLoad(String ag)
    {
		
		start =  Instant.now();	
    	
    	// ag = ag.substring(1);
         
       //  int agNumber = Integer.parseInt(ag.substring(1)); 
      //   double batteryCapacity =0.0;
      //   double vacuuumCapacity =0.0;
         
         
     	responseTime=netsc.calculateResponseTime(workLoad);
         
         
      
    	
     	double workLoadLow = trape(0.0,0.0,75.0,125.0,workLoad);       	
    	removePerceptsByUnif(ag,Literal.parseLiteral("workLoad(low)[mu(_)]"));
    	Literal literal_workLoadLow = Literal.parseLiteral("workLoad(low)[mu("+workLoadLow+")]");
    	addPercept(ag,literal_workLoadLow);
    	
    	
    	double workLoadMid = trape(50.0,150.0,300.0,400.0,workLoad);
    	removePerceptsByUnif(ag,Literal.parseLiteral("workLoad(medium)[mu(_)]"));
    	Literal literal_workLoadMid = Literal.parseLiteral("workLoad(medium)[mu("+workLoadMid+")]");
    	addPercept(ag,literal_workLoadMid);


    	double workLoadMax = trape(250.0,400.0,5000.0,5000.0,workLoad);
    	removePerceptsByUnif(ag,Literal.parseLiteral("workLoad(high)[mu(_)]"));
    	Literal literal_workLoadMax = Literal.parseLiteral("workLoad(high)[mu("+workLoadMax+")]");
    	addPercept(ag,literal_workLoadMax);
		
    	/////////////////////////////////////////////////////////////////////////////////////////////// vacuumBag
    	
    	

    	
    	
    	double responseTimeMin = trape(0.0,0.0,5.0,7.5,responseTime);       	
    	removePerceptsByUnif(ag,Literal.parseLiteral("responseTime(good)[mu(_)]"));
    	Literal literal_responseTimeMin = Literal.parseLiteral("responseTime(good)[mu("+responseTimeMin+")]");
    	addPercept(ag,literal_responseTimeMin);
    	
    	
    	double responseTimeMid = trape(5.0,7.5,7.5,10.0,responseTime);
    	removePerceptsByUnif(ag,Literal.parseLiteral("responseTime(ok)[mu(_)]"));
    	Literal literal_responseTimeMid = Literal.parseLiteral("responseTime(ok)[mu("+responseTimeMid+")]");
    	addPercept(ag,literal_responseTimeMid);


    	double responseTimeMax = trape(7.5,10.0,100.0,100.0,responseTime);
    	removePerceptsByUnif(ag,Literal.parseLiteral("responseTime(bad)[mu(_)]"));
    	Literal literal_responseTimeMax = Literal.parseLiteral("responseTime(bad)[mu("+responseTimeMax+")]");
    	addPercept(ag,literal_responseTimeMax);
		
    	//////////////////////////////////////////////////////////////////////////////////////////// dirtIntensity
    	
    	
    	
    	
    //	   System.out.println("Status is being checked. "+ ag + " *** " + agNumber + " ** " +batteryCapacity + " **" + vacuuumCapacity+" ** " );
    	
    	
    	
    	
    	
    	
		
	}
	
     		
	
	
	void nextWorkload() throws Exception
	{
	
	}
	
	void consumeWorkLoad (String ag) {
		
		String toFile="";
		
		netsc.consumeWorkLoad();
		
		removePerceptsByUnif(ag,Literal.parseLiteral("currentWorkLoad(_)"));
    	Literal literal_currentWorkLoad = Literal.parseLiteral("currentWorkLoad("+workLoad+")");
		addPercept(ag, literal_currentWorkLoad);
		
		
		int remainingTurns = netsc.arrivalTurnsBackCounter();
		
		
		
		removePerceptsByUnif(ag,Literal.parseLiteral("arrivedTurn(_)"));
    	Literal literal_arrivedTurn = Literal.parseLiteral("arrivedTurn("+remainingTurns+")");
		addPercept(ag, literal_arrivedTurn);
		
		workloadTurnCounter+=1;
		
		
	
		
		
		System.out.println("Number-of-Machnies: " +netsc.numberOfMachines+" "+ " Work Load= "+workLoad+ " --- ArrivalTurn= "+arrivalTurns + " Remaining Turns= "+ remainingTurns +" Response Time " + responseTime);
		
		//System.out.println(totalMach+=netsc.numberOfMachines);
		
		
	//	"Work Load= "+workLoad+ " --- ArrivalTurn= "+arrivalTurns +" Response Time " + responseTime
		totalMach+=netsc.numberOfMachines;
        toFile= netsc.numberOfMachines+" " +workLoad+ " "+arrivalTurns + " " +  remainingTurns+ " "  + responseTime+" \n  " ;
     //   toFile+=" "+(totalMach+=netsc.numberOfMachines);
     //   toFile+=" \n ";
        
        
		 // Construct the file path with dynamic values
    //   String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\"+text+"_" 
   //            + batteryStart + "_" + vacuumStart + "_" + mapNumber + "_OutBoolean.txt";

       String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\networkScaler\\src\\java\\Sonuclar\\"+turnScope+"\\"+textName+"_"+turn+".txt";

       // Write content to file
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathTotal, true))) {         
           writer.write(toFile);
           System.out.println("File created successfully at " + filePathTotal);
       } catch (IOException e) {
           System.err.println("Error writing to file: " + e.getMessage());
       }
		
		
		
       Instant  end =  Instant.now();	
  		
      Duration elapsed = Duration.between(start, end);
		
		
      executionTimes.add(elapsed.toMillis());
		
		
	 	//instantR3END =  Instant.now();	
   		
     // 	 Duration elapsedR3 = Duration.between(instantR3, instantR3END);
      	 
      	 
    //    System.out.println("Elapsed Time: " + elapsedR3.toMillis() + " milliseconds");
        
        
    //    agentTimeList.add(new AbstractMap.SimpleEntry<>("r3", elapsedR3.toMillis()));

	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class NetworkScaler {
	
	 private int numberOfMachines;
	    private final int initialMachines = 100;
	    private final double machineCost = 1.0;
	    private final double minResponseTime = 1.0; // Best response time (1 ms)
	    private final double maxResponseTime = 4.0; // Worst response time (4 ms)

	    public NetworkScaler() {
	        this.numberOfMachines = initialMachines;
	    }

	    public void scaleUp() {
	  //      numberOfMachines++;
	    }

	    public void scaleDown() {
	   //     if (numberOfMachines > 1) {
	   //         numberOfMachines--;
	     //   }
	    }

	    public double calculateResponseTime(double workload) {
	        if (numberOfMachines == 0) { return Double.MAX_VALUE;}
	        
	        
	    
	        
	        return (double) workload / numberOfMachines; // Response time in ms   // netsc
	    }
	    
	    
	    public void consumeWorkLoad() {
	        
	    	
	        System.out.println("Workload ("+workLoad+") - "+numberOfMachines+"");
	        
	         workLoad = workLoad - numberOfMachines; // Consume workload
	         if (workLoad <=0) {
	        	 workLoad = 0;
	         }
	         
	         System.out.println("Workload: "+workLoad+" NumberOfMachines:" +numberOfMachines);
	         
	      
	         
	    }
	    
	    public int arrivalTurnsBackCounter() {
	    	
	    	 if (arrivalTurns>0) {
	    	   arrivalTurns-=1;
	    	 }
			return arrivalTurns;
	    	
	    }
	    
	    public void setNumberOfMachines(int scaleFactor) {
	        numberOfMachines= numberOfMachines + scaleFactor;
	        
	        if (numberOfMachines<=50) {
	        	numberOfMachines = 50;
	        }
	        
	    }
	    
	    
	    public double getTotalCost() {
	        return numberOfMachines * machineCost;
	    }

	    public void adjustScaling(double workload) {
	        // First, check if we can scale down to save costs
	   //     while (calculateResponseTime(workload) < minResponseTime && numberOfMachines > 1) {
	   //         scaleDown();
	        }

	        // Then scale up if the response time exceeds the max allowed
	     //   while (calculateResponseTime(workload) >= maxResponseTime) {
	    //        scaleUp();
	    //    }
	  //  }

/*	    public static void main(String[] args) {
	        NetworkScaler scaler = new NetworkScaler();
	        Random rand = new Random();

	        // Simulate workloads and calculate response times
	        for (int i = 0; i < 10; i++) {
	            int workload = rand.nextInt(431) + 70; // Random workload between 70 and 500
	            scaler.adjustScaling(workload);
	            double responseTime = scaler.calculateResponseTime(workload);
	            double cost = scaler.getTotalCost();
	            System.out.printf("Turn %d: Workload: %d, Number of Machines: %d, Response Time: %.3f ms, Total Cost: %.2f%n", 
	                              i + 1, workload, scaler.numberOfMachines, responseTime, cost);
	        }
	    }
	*/
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	   public static double triangular (double s, double m, double e, double val)
	    {

	       // System.out.println("Membership parameters are"+ s+" "+m+" "+e+" "+val);

	        double start = s;
	        double mid= m;
	        double end = e;

	      //  int[] val  = new int[] {6,37,77,80,106};

	        double result=0.0;

				if (mid==end && end==val)
				{
					return 1.0;
					
				}



	            if (val >= start && val <= mid){

	                result = (val-start) / (mid-start);

	            }

	            if (val>=mid && val<=end){

	                result = (end-val) / (end-mid);
	            }
	            
	          // System.out.println("TRIAN RESULT"+ result);

	           if (result==-0.0)
	        		   result=0.0;
	           
	         
	           return  result;


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
	    
	    ////////////////// enhanced trape below ////////////////////////////////////
	    
	    public static double trapeEnc(double a, double b, double c, double d, double val) {
	        double result = 0.0;

	        if (val >= a && val <= b) {
	            if (b != a) {
	                result = (val - a) / (b - a);
	            } else {
	                result = 0.0; // Prevent division by zero
	            }
	        } else if (val >= b && val <= c) {
	            result = 1.0;
	        } else if (val >= c && val <= d) {
	            if (d != c) {
	                result = (d - val) / (d - c);
	            } else {
	                result = 0.0; // Prevent division by zero
	            }
	        }

	        return result;
	    }

	    

}
