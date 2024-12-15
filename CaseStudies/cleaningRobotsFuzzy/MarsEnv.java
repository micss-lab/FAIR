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



public class MarsEnv extends Environment {

    public static final int GSize = 14; // grid size
    public static final int GARB  = 16; // garbage code in grid model

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
    

    public static float[] values = new float[194]; // Fixed size array
    public static int  currentIndex = -1;
    
    
    // ### CONFIG ### // 
    
    public static double batteryStartD = 0.0;
    public static double vacuumStartD = 0.0;
    
    public static int batteryStart = 0;
    public static int vacuumStart = 0;
    
    
    public static int mapNumber = 0;
    
    // ### CONFIG ### // 
    

    // Specify the path to your data file
    public static String MapDataPath = ""; // Change this to your file path


    
    
    public static boolean initialMapFlag=true;
    
    public static int counter=0;
    
    
    public static final Literal checkStatus = Literal.parseLiteral("checkStatus");
    
    public static final Literal checkStatusBool = Literal.parseLiteral("checkStatusBool");
    
    public static boolean saveFlag=true;
    public static boolean areObstaclesDrawn = false;
    

    
    
    
	 // Create a battery model with an initial capacity of 100 and sensitivity factor k
	    BatteryModel batteryR1 ; // 15
	    BatteryModel batteryR3 ; // 15
	    
	    
	    VacuumBag vacuumBagR1 ;
	    VacuumBag vacuumBagR3 ;
       
        
	    
	    
	    
	    
	 // Simulate performing actions with different intensities
      //  double[] actionValues = {0.5, 1.0, 2.0, 0.2, 3.0}; // Example action values
    double timeInterval = 1.0; // Time interval for each action step
    
    
    public static ArrayList<GarbageHolder> garbageHolder = new ArrayList<GarbageHolder>();
    public static ArrayList<GarbageHolder> visitedGarbages = new ArrayList<GarbageHolder>();
    
    public static ArrayList<GarbageHolder> initialMap = new ArrayList<GarbageHolder>();
    
    
    ArrayList<Map.Entry<String, Long>> agentTimeList = new ArrayList<>();
    
  
    static Logger logger = Logger.getLogger(MarsEnv.class.getName());
    
    static String experimentLogic="";

    private MarsModel model;
    private MarsView  view;

    @Override
    public void init(String[] args) {
    	
    	 mapNumber = Integer.parseInt(args[0]); // 8
    	 
    	 MapDataPath = "C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\MapDist"+mapNumber+".txt";
    	
         batteryStartD = Double.parseDouble(args[1]); // 10.0;
         vacuumStartD = Double.parseDouble(args[2]); //2000.0;
         experimentLogic= String.valueOf(args[3]);
         
         
         System.out.println("################ SIMULATION INFO #########################");
         System.out.println(mapNumber+ " "+ batteryStartD+ " "+  vacuumStartD);
         
         
         batteryStart = (int)batteryStartD; 
         vacuumStart = (int) vacuumStartD;
    	
    	//   hasGUI = args[2].equals("yes");
       //    sleep  = 
       //    initWorld();
         
         
         // Create a battery model with an initial capacity of 100 and sensitivity factor k
 	     batteryR1 = new BatteryModel(batteryStartD, 0.02); // 15
 	     batteryR3 = new BatteryModel(batteryStartD, 0.02); // 15
 	    
 	    
 	     vacuumBagR1 = new VacuumBag(vacuumStartD, "R1");
 	     vacuumBagR3 = new VacuumBag(vacuumStartD, "R3");
 	     
 	     

    	//////////////
 	     
 	     
 	     ////////////////
    	
    	loadValuesFromFile(MapDataPath);
    	
        model = new MarsModel();
        view  = new MarsView(model);
        model.setView(view);
        updatePercepts();
        
        
        try {
			System.setOut(new PrintStream(new FileOutputStream("C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\newExperiments\\MapDist"+mapNumber+"\\"
					+experimentLogic+"\\"+batteryStart+"_"+vacuumStart+"_Map"+mapNumber+"\\"+"Output_"+experimentLogic+".txt")));
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
        
 /*       
    removePerceptsByUnif(Literal.parseLiteral("batteryPower(_)")); 
    Literal bel_batteryPower = Literal.parseLiteral("batteryPower(-1)");
  	addPercept(bel_batteryPower); 
  	 
  	 
  	removePerceptsByUnif(Literal.parseLiteral("vacuumBag(_)")); 
    Literal bel_vacuumBag = Literal.parseLiteral("vacuumBag(-1)");
 	addPercept(bel_vacuumBag);
 	 
 	removePerceptsByUnif(Literal.parseLiteral("vacuumBag(_)")); 
    Literal bel_dirtIntensity = Literal.parseLiteral("dirtIntensity(-1)");
 	addPercept(bel_dirtIntensity);
 	
 	*/

 	
 	
 ///////////////////////////////////////////////////////////////////////////////////
 	
 	
        
        Literal literal_vacuumBagStatusR1 = Literal.parseLiteral("vacuumBagFull(r1,empty)");
     	addPercept("r1",literal_vacuumBagStatusR1);
     	
     	Literal literal_vacuumBagStatusR3 = Literal.parseLiteral("vacuumBagFull(r3,empty)");
     	addPercept("r3",literal_vacuumBagStatusR3);
        
     	
     	
     	
    	
        Literal literal_batteryChargeStatusR1 = Literal.parseLiteral("batteryCharge(r1,full)");
        addPercept("r1",literal_batteryChargeStatusR1);
        
        Literal literal_batteryChargeStatusR3 = Literal.parseLiteral("batteryCharge(r3,full)");
        addPercept("r3",literal_batteryChargeStatusR3);
        		
        

       
        
    }

    // Method to get the next float value
    public Float getNextValue() {
        if (currentIndex < values.length) {
        	currentIndex= currentIndex+1;
            return values[currentIndex];
        } else {
            return null; // No more values to retrieve
        }
    }
    
    
    private void loadValuesFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0; // Local index for the array
            while ((line = br.readLine()) != null && index < values.length) {
            	
                values[index] = (float) Double.parseDouble(line);    
                System.out.println(values[index]+ " " + index );
                index = index+1;
                
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        }
    }


	@Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);
        
        try {
            if (action.equals(ns)) {
                model.nextSlot();
            } else if (action.equals(nsRev)) {
                model.nextSlotRev();
            }
			else if (action.getFunctor().equals("move_towards")) {
                int x = (int)((NumberTerm)action.getTerm(0)).solve();
                int y = (int)((NumberTerm)action.getTerm(1)).solve();
                model.moveTowards(x,y);
            }
			else if (action.getFunctor().equals("move_towardsRev")) {
                int x = (int)((NumberTerm)action.getTerm(0)).solve();
                int y = (int)((NumberTerm)action.getTerm(1)).solve();
                model.moveTowardsRev(x,y);
            }
			else if (action.equals(pg)) {
                model.pickGarb();
            } else if (action.equals(dg)) {
                model.dropGarb();
			}else if (action.equals(pgRev)) {
                model.pickGarbRev();
            } else if (action.equals(dgRev)) {
                model.dropGarbRev();
            } else if (action.equals(bg)) {
             //   model.burnGarb(ag);    
            }else if (action.getFunctor().equals("burnGarb")) {
                double vacuumPowerLimit = (double)((NumberTerm)action.getTerm(0)).solve();
                double vacuumPowerConstant = (double)((NumberTerm)action.getTerm(1)).solve();

                model.burnGarb(ag,vacuumPowerLimit,vacuumPowerConstant);
            }
            
             else if (action.equals(getDirtIntensityValues)) {
	        	 model.getDirtIntensityValues();
	        	 
	        	 String toFile="";
	        	 
	        	 saveFlag=false;
	        	 getPositions(visitedGarbages);
	        	 
	        	 
	        	 // Construct the file path with dynamic values
              //   String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\"+"ExecutionTime"+"_" 
              //           + batteryStart + "_" + vacuumStart + "_" + mapNumber + "_OutBoolean.txt";


                 String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\newExperiments\\MapDist"+mapNumber+"\\"
                		 +experimentLogic+"\\"+batteryStart+"_"+vacuumStart+"_Map"+mapNumber+"\\"+"ExecutionTime_"+experimentLogic+".txt";
                 
                 
                 
	        	 for (Entry<String, Long> agent : agentTimeList)
	        	 {
	                 System.out.println("Agent Name: " + agent.getKey() + ", Execution Time: " + agent.getValue() + " ms");
	                 
	                 
	                 toFile+= "Agent Name: " + agent.getKey() + ", Execution Time: " + agent.getValue() + " ms \n";	
	                

	                 
	             }
	        	 
	        	
	                 // Write content to file
	                 try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathTotal))) {
	                     writer.write(toFile);
	                     System.out.println("File created successfully at " + filePathTotal);
	                 } catch (IOException e) {
	                     System.err.println("Error writing to file: " + e.getMessage());
	                 }
	        	 
	        	 System.out.println("###############################################################################################################");
	        	 printTheMap("Initial-Map ",initialMap);
	        	 System.out.println("---------------------------------------------------------------------------------------------------------------");
	        	 printTheMap("End-Map ",garbageHolder);
	        	 
	        	 
	        	 System.exit(0);

	        	 
             }
            
            
            
            else if (action.equals(checkStatus)) {
                model.checkStatus(ag);
            }
            
            else if (action.equals(checkStatusBool)) {
                model.checkStatusBool(ag);
            }
            
            
            else if (action.equals(startTimePeriod)) {
                    model.startTimePeriod(ag);
            } else {
            	
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        updatePercepts();

        try {
            Thread.sleep(200);
           
        } catch (Exception e) {}
        informAgsEnvironmentChanged();
        return true;
    }

    public void printTheMap(String text, ArrayList<GarbageHolder> list) {
      int thirdCounter=0;
      String toFile="";
      int SeventyMoreCounter=0;
        for (int i = 0; i < list.size(); i++) {
            GarbageHolder item = list.get(i);
          
            if (item.intensity<35.0f) {
            	thirdCounter++;
            }
            
            if (item.intensity>70.0f) {
            	SeventyMoreCounter++;
            }
            
            
            System.out.println(text+ " X: " + item.getX()+" Y: "+item.getY()+" Intensity: " + item.getIntensity() + " Below 35: "+thirdCounter+ " Above 70: "+SeventyMoreCounter);
            
            toFile+= " X: " + item.getX()+" Y: "+item.getY()+" Intensity: " + item.getIntensity() + " \n ";
        }
        
        
		
		 // Construct the file path with dynamic values
    //   String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\"+text+"_" 
   //            + batteryStart + "_" + vacuumStart + "_" + mapNumber + "_OutBoolean.txt";

       String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\newExperiments\\MapDist"+mapNumber+"\\"
      		 +experimentLogic+"\\"+batteryStart+"_"+vacuumStart+"_Map"+mapNumber+"\\"+text+""+experimentLogic+".txt";

       // Write content to file
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathTotal))) {
           writer.write(toFile);
           System.out.println("File created successfully at " + filePathTotal);
       } catch (IOException e) {
           System.err.println("Error writing to file: " + e.getMessage());
       }
       
    }

	public float getTotalIntensity(ArrayList<GarbageHolder> list) {
        float totalIntensity = 0.0f;
        for (int i = 0; i < list.size(); i++) {
            GarbageHolder item = list.get(i);
            totalIntensity += item.intensity;
        }
        return totalIntensity;
    }
    
	/** creates the agents perception based on the MarsModel */
    void updatePercepts() {
        clearPercepts();

        Location r1Loc = model.getAgPos(0);
        Location r2Loc = model.getAgPos(1);
		Location r3Loc = model.getAgPos(2);

        Literal pos1 = Literal.parseLiteral("pos(r1," + r1Loc.x + "," + r1Loc.y + ")");
        Literal pos2 = Literal.parseLiteral("pos(r2," + r2Loc.x + "," + r2Loc.y + ")");
		Literal pos3 = Literal.parseLiteral("pos(r3," + r3Loc.x + "," + r3Loc.y + ")");

        addPercept(pos1);
        addPercept(pos2);
		addPercept(pos3);

        if (model.hasObject(GARB, r1Loc)) {
            addPercept(g1);
        }
        if (model.hasObject(GARB, r2Loc)) {
            addPercept(g2);
        }
		if (model.hasObject(GARB, r3Loc)) {
				addPercept(g3);
        }
		
		view.update();
    }

    class MarsModel extends GridWorldModel {

        public static final int MErr = 2; // max error in pick garb
        int nerr; // number of tries of pick garb
        boolean r1HasGarb = false; // whether r1 is carrying garbage or not
		boolean r3HasGarb = false; // whether r3 is carrying garbage or not

        Random random = new Random(System.currentTimeMillis());

        private MarsModel() {
            super(GSize, GSize, 3);

            // initial location of agents
            try {
                setAgPos(0, 0, 0);

                Location r2Loc = new Location((GSize/2), GSize/2);
		
                setAgPos(1, r2Loc);


				Location r3Loc = new Location((GSize-1), (GSize-1));
				setAgPos(2, r3Loc);


            } catch (Exception e) {
                e.printStackTrace();
            }

            // initial location of garbage // bunlar random olacak ileride.
          
            
           // add(GARB, GSize-2, GSize-1);  // -2 -2 
            
            for (int x = -1; x >= -14; x--) { // -1 -14
                for (int y = -1; y >= -14; y--) { //-1 -14
                	if ( !((GSize + x == 13 && GSize + y == 13) || (GSize + x == 0 && GSize + y == 0)) ) { 
                		
                		add(GARB, GSize + x, GSize + y); 
                		
                	
                    
                	}
                }
            }
        }
        

        
        
  
        public void getDirtIntensityValues() 
        {
        	// C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\"+batteryStart+"_"+vacuumStart+"_"+mapNumber+"_OutFuzzy.txt
        	
        	
		System.out.println("Total Initial Dirt: "+intensityDirtTotalBegin+ " Total Dirt After Cleaning "+ intensityDirtTotalEnd );
		
		 // Construct the file path with dynamic values
    //    String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\Totals_" 
    //            + batteryStart + "_" + vacuumStart + "_" + mapNumber + "_OutBoolean.txt";
        
        
        
        String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\newExperiments\\MapDist"+mapNumber+"\\"
         		 +experimentLogic+"\\"+batteryStart+"_"+vacuumStart+"_Map"+mapNumber+"\\Totals_"+experimentLogic+".txt";
        
        
        
        

        // The content to be written to the file
        String content = "Total Initial Dirt: "+intensityDirtTotalBegin+ " Total Dirt After Cleaning "+ intensityDirtTotalEnd ;

        // Write content to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathTotal))) {
            writer.write(content);
            System.out.println("File created successfully at " + filePathTotal);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
  
        
        }
			
		

		public void startTimePeriod(String ag) {
		
		       	if (ag.equals("r1"))
		       	{
		        		
		        	instantR1 =  Instant.now();	
		       	}
		        		
		       	else if (ag.equals("r3"))
		       	{
		       		instantR3 =  Instant.now();	
		        }
		        	
		}

		
		public void checkStatusBool(String ag)
        {
			// Assuming batteryCapacity, vacuumCapacity, and intensityValue are known input values
			
			
			// ag = ag.substring(1);
            
            int agNumber = Integer.parseInt(ag.substring(1)); 
            double batteryCapacity =0.0;
            double vacuuumCapacity =0.0;
            
            
         

            if (agNumber-1==0)
            {
           	 batteryCapacity = batteryR1.getCapacity(); 
           	 vacuuumCapacity = vacuumBagR1.remainingCAP;
            }
            else if (agNumber-1==2)
            {
           	 batteryCapacity = batteryR3.getCapacity(); 
            	 vacuuumCapacity = vacuumBagR3.remainingCAP;
            }
            
            

            
            removePerceptsByUnif(ag,Literal.parseLiteral("batteryPower(_)"));
            
            
			// Battery Power - Boolean Logic
			if (batteryCapacity <= 5.0) 
			{ // true if batteryCapacity is less than or equal to 5.0
			
			Literal literal_batteryMin = Literal.parseLiteral("batteryPower(min)");
			addPercept(ag, literal_batteryMin);
			}
			
			if (batteryCapacity > 5.0 && batteryCapacity <= 10.0) 
			{ // true if batteryCapacity is between 7.5 and 10.0
			
			Literal literal_batteryMid = Literal.parseLiteral("batteryPower(mid)");
			addPercept(ag, literal_batteryMid);
			}
			

			if (batteryCapacity > 10.0 )
			{ // true if batteryCapacity is greater than 10.0
			
			Literal literal_batteryMax = Literal.parseLiteral("batteryPower(max)");
			addPercept(ag, literal_batteryMax);
			}
			
			
			
			 removePerceptsByUnif(ag,Literal.parseLiteral("vacuumBag(_)"));
			
			// Vacuum Bag - Boolean Logic
			boolean vacuumBagMin = (vacuuumCapacity <= 500.0); // true if vacuumCapacity is 0
			
			Literal literal_vacuumBagMin = Literal.parseLiteral("vacuumBag(min)");
			addPercept(ag, literal_vacuumBagMin);

			boolean vacuumBagMid = (vacuuumCapacity > 500.0 && vacuuumCapacity <= 750.0); // true if vacuumCapacity is between 500 and 1000
			
			Literal literal_vacuumBagMid = Literal.parseLiteral("vacuumBag(mid)");
			addPercept(ag, literal_vacuumBagMid);

			boolean vacuumBagMax = (vacuuumCapacity > 750.0); // true if vacuumCapacity is greater than 1000
		
			Literal literal_vacuumBagMax = Literal.parseLiteral("vacuumBag(max)");
			addPercept(ag, literal_vacuumBagMax);
			
			
			
			
		  	Location loc = getAgPos(agNumber-1);
            float intensityValue = ((MarsView) view).returnIntensity(loc.x,loc.y,garbageHolder);
            
      //      System.out.println("Check Status: "+intensityValue);
            
            removePerceptsByUnif(ag,Literal.parseLiteral("dirtIntensity(_)"));
            
            
			// Dirt Intensity - Boolean Logic
			boolean intensityMin = (intensityValue > 30.0 && intensityValue <= 50.0); // true if intensityValue is less than or equal to 40.0
			
			Literal literal_intensityMin = Literal.parseLiteral("dirtIntensity(min)");
			addPercept(ag, literal_intensityMin);

			boolean intensityMid = (intensityValue > 50.0 && intensityValue <= 70.0); // true if intensityValue is between 50 and 60
	
			Literal literal_intensityMid = Literal.parseLiteral("dirtIntensity(mid)");
			addPercept(ag, literal_intensityMid);

			boolean intensityMax = (intensityValue > 70.0); // true if intensityValue is greater than 70
		
			Literal literal_intensityMax = Literal.parseLiteral("dirtIntensity(max)");
			addPercept(ag, literal_intensityMax);
			
			
			
        
        }
		
		
		
		public void checkStatus(String ag)
        {
        	
        	// ag = ag.substring(1);
             
             int agNumber = Integer.parseInt(ag.substring(1)); 
             double batteryCapacity =0.0;
             double vacuuumCapacity =0.0;
             
             
          

             if (agNumber-1==0)
             {
            	 batteryCapacity = batteryR1.getCapacity(); 
            	 vacuuumCapacity = vacuumBagR1.remainingCAP;
             }
             else if (agNumber-1==2)
             {
            	 batteryCapacity = batteryR3.getCapacity(); 
             	 vacuuumCapacity = vacuumBagR3.remainingCAP;
             }
             
             
            
         	
             
          
        	
         	double batteryMin = triangular(0.0,5.0,8.5,batteryCapacity);       	
        	removePerceptsByUnif(ag,Literal.parseLiteral("batteryPower(min)[mu(_)]"));
        	Literal literal_batteryMin = Literal.parseLiteral("batteryPower(min)[mu("+batteryMin+")]");
        	addPercept(ag,literal_batteryMin);
        	
        	
        	double batteryMid = triangular(7.5,10.0,12.5,batteryCapacity);
        	removePerceptsByUnif(ag,Literal.parseLiteral("batteryPower(mid)[mu(_)]"));
        	Literal literal_batteryMid = Literal.parseLiteral("batteryPower(mid)[mu("+batteryMid+")]");
        	addPercept(ag,literal_batteryMid);


        	double batteryMax = triangular(10.0,15.0,15.0,batteryCapacity);
        	removePerceptsByUnif(ag,Literal.parseLiteral("batteryPower(max)[mu(_)]"));
        	Literal literal_batteryMax = Literal.parseLiteral("batteryPower(max)[mu("+batteryMax+")]");
        	addPercept(ag,literal_batteryMax);
			
        	/////////////////////////////////////////////////////////////////////////////////////////////// vacuumBag
        	
        	
        	
        	
        	
        	double vacuumBagMin = triangular(0.0,0.0,1000.0,vacuuumCapacity);       	
        	removePerceptsByUnif(ag,Literal.parseLiteral("vacuumBag(min)[mu(_)]"));
        	Literal literal_vacuumBagMin = Literal.parseLiteral("vacuumBag(min)[mu("+vacuumBagMin+")]");
        	addPercept(ag,literal_vacuumBagMin);
        	
        	
        	double vacuumBagMid = triangular(500.0,1000.0,1500.0,vacuuumCapacity);
        	removePerceptsByUnif(ag,Literal.parseLiteral("vacuumBag(mid)[mu(_)]"));
        	Literal literal_vacuumBagMid = Literal.parseLiteral("vacuumBag(mid)[mu("+vacuumBagMid+")]");
        	addPercept(ag,literal_vacuumBagMid);


        	double vacuumBagMax = triangular(1000.0,2000.0,2000.0,vacuuumCapacity);
        	removePerceptsByUnif(ag,Literal.parseLiteral("vacuumBag(max)[mu(_)]"));
        	Literal literal_vacuumBagMax = Literal.parseLiteral("vacuumBag(max)[mu("+vacuumBagMax+")]");
        	addPercept(ag,literal_vacuumBagMax);
			
        	//////////////////////////////////////////////////////////////////////////////////////////// dirtIntensity
        	
        	
        	Location loc = getAgPos(agNumber-1);
            float intensityValue = ((MarsView) view).returnIntensity(loc.x,loc.y,garbageHolder);
            
      //      System.out.println("Check Status: "+intensityValue);
            
            
            
        	double intensityValueMin = triangular(30.0,40.0,50.0,intensityValue);       	
        	removePerceptsByUnif(ag,Literal.parseLiteral("dirtIntensity(min)[mu(_)]"));
        	Literal literal_intensityValue = Literal.parseLiteral("dirtIntensity(min)[mu("+intensityValueMin+")]");
        	addPercept(ag,literal_intensityValue);
        	
        	
        	double intensityValueMid = triangular(50.0,60.0,70.0,intensityValue);
        	removePerceptsByUnif(ag,Literal.parseLiteral("dirtIntensity(mid)[mu(_)]"));
        	Literal literal_intensityValueMid = Literal.parseLiteral("dirtIntensity(mid)[mu("+intensityValueMid+")]");
        	addPercept(ag,literal_intensityValueMid);


        	double intensityValueMax = triangular(70.0,100.0,100.0,intensityValue);
        	removePerceptsByUnif(ag,Literal.parseLiteral("dirtIntensity(max)[mu(_)]"));
        	Literal literal_intensityValueMax = Literal.parseLiteral("dirtIntensity(max)[mu("+intensityValueMax+")]");
        	addPercept(ag,literal_intensityValueMax);
        	
        	
        	
        	
        	   System.out.println("Status is being checked. "+ ag + " *** " + agNumber + " ** " +batteryCapacity + " **" + vacuuumCapacity+" ** "+ intensityValue);
			
		}

		void nextSlot() throws Exception {
            Location r1 = getAgPos(0);
            r1.x++;
            if (r1.x == getWidth()) {
                r1.x = 0;
                r1.y++;
            }
            // finished searching the whole grid
            if (r1.y == getHeight()) {
                return;
            }
            setAgPos(0, r1);
            //setAgPos(1, getAgPos(1)); // just to draw it in the view

// ##########################################################################3
  	  
        }
		
		void nextSlotRev() throws Exception {
            Location r3 = getAgPos(2);
			System.out.println(r3.x+" "+r3.y + "" +getWidth());
			r3.x--;
			
		
			
            if (r3.x == -1) {
                r3.x = getWidth()-1;
                r3.y--;
            }
            // finished searching the whole grid
            if (r3.y == getHeight()) {
                return;
            }
            setAgPos(2, r3);
            //setAgPos(1, getAgPos(1)); // just to draw it in the view
        }


        void moveTowards(int x, int y) throws Exception {
            Location r1 = getAgPos(0);
            if (r1.x < x)
                r1.x++;
            else if (r1.x > x)
                r1.x--;
            if (r1.y < y)
                r1.y++;
            else if (r1.y > y)
                r1.y--;
            setAgPos(0, r1);
         //   setAgPos(1, getAgPos(1)); // just to draw it in the view

 	    
        }
		
		 void moveTowardsRev(int x, int y) throws Exception {
            Location r3 = getAgPos(2);
            if (r3.x < x)
                r3.x++;
            else if (r3.x > x)
                r3.x--;
            if (r3.y < y)
                r3.y++;
            else if (r3.y > y)
                r3.y--;
            setAgPos(2, r3);
         //   setAgPos(1, getAgPos(1)); // just to draw it in the view

 	    
        }

        void pickGarb() {
            // r1 location has garbage
            if (model.hasObject(GARB, getAgPos(0))) {
                // sometimes the "picking" action doesn't work
                // but never more than MErr times
                if (random.nextBoolean() || nerr == MErr) {
                    remove(GARB, getAgPos(0));
                    nerr = 0;
                    r1HasGarb = true;
                } else {
                    nerr++;
                }
            }
        }
        
        void dropGarb() {
            if (r1HasGarb) {
                r1HasGarb = false;
                add(GARB, getAgPos(0));
            }
        }
		
		
		void pickGarbRev() {
            // r1 location has garbage
            if (model.hasObject(GARB, getAgPos(2))) {
                // sometimes the "picking" action doesn't work
                // but never more than MErr times
                if (random.nextBoolean() || nerr == MErr) {
                    remove(GARB, getAgPos(2));
                    nerr = 0;
                    r3HasGarb = true;
                } else {
                    nerr++;
                }
            }
        }
        void dropGarbRev() {
            if (r3HasGarb) {
                r3HasGarb = false;
                add(GARB, getAgPos(2));
            }
        }
        
        
        
		
        void burnGarb(String ag, double vacuumPowerLimit, double vacuumPowerConstant) {
            // r2 location has garbage
        	
        	String agOrj= ag;
            ag = ag.substring(1);
            
            int agNumber = Integer.parseInt(ag);

        	
        	
            if (model.hasObject(GARB, getAgPos(agNumber-1))) {
            	
            	Location loc = getAgPos(agNumber-1);
            	
            	

            	float ground_intensityValue = ((MarsView) view).returnIntensity(loc.x,loc.y,garbageHolder);
            	float garbageintensityValue = ground_intensityValue;
            	
            	
           // 	System.out.println("Vacuum PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPower Limit: "+ vacuumPowerLimit  +" *** Constant= "+ vacuumPowerConstant);
            	
            	
            	ground_intensityValue-=(vacuumPowerLimit*vacuumPowerConstant);
            	
            	
            	
            	
            	
            	if (ground_intensityValue<0.0) {
            		
            		ground_intensityValue=0.0f;
            	}
            	else {
            		
            		garbageintensityValue = garbageintensityValue-ground_intensityValue;
            	}
            	
            	((MarsView) view).updateIntensityInList(loc.x,loc.y,garbageHolder,(float)(ground_intensityValue)); // burada yeniden set edecek
            	
            	
                
	          
            	
            	
            	
            
            	
            	 // Loop through the actions and update the battery capacity
                /*  for (int i = 0; i < actionValues.length; i++) {
                      battery.performAction(actionValues[i], timeInterval);
                      System.out.printf("After action %d with intensity %.2f, battery capacity: %.2f%%\n", 
                                        i + 1, actionValues[i], battery.getCapacity());
                                        
                                        */
                  
            	
            	 if (agNumber-1==0)
            	 {
			            	boolean batteryResult = batteryR1.performAction((vacuumPowerLimit*vacuumPowerConstant)/100.0, timeInterval);          		 
			            	boolean addResult     = vacuumBagR1.addItems(garbageintensityValue);
			            		 
			            	if (!batteryResult)
			                 {
			    	            removePerceptsByUnif(agOrj,Literal.parseLiteral("batteryCharge(r1,_)"));
			    	            Literal literal_batteryChargeStatus = Literal.parseLiteral("batteryCharge("+agOrj+",depleted)");
			    	            addPercept("r1",literal_batteryChargeStatus);
			                 		
			                 }
		            		 
		            		 
		            		if (!addResult)
		             		{
			            		removePerceptsByUnif(agOrj,Literal.parseLiteral("vacuumBagFull(r1,_)"));
			                 	Literal literal_vacuumBagStatus = Literal.parseLiteral("vacuumBagFull("+agOrj+",full)");
			                 	addPercept("r1",literal_vacuumBagStatus);
		             		}
		         }
            	 
            	 
            	 else if (agNumber-1==2)
            	 {
	            		 	 boolean batteryResult = batteryR3.performAction((vacuumPowerLimit*vacuumPowerConstant)/100.0, timeInterval);
		            		 boolean addResult =  vacuumBagR3.addItems(garbageintensityValue);
            		 
            		 
		            		if (!addResult)
		            		{
			            		removePerceptsByUnif(agOrj,Literal.parseLiteral("vacuumBagFull(r3,_)"));
			                  	Literal literal_vacuumBagStatus = Literal.parseLiteral("vacuumBagFull(r3,full)");
			                  	addPercept("r3",literal_vacuumBagStatus);
		            		}
		            		
		            		if (!batteryResult)
			                 {
			    	            removePerceptsByUnif(agOrj,Literal.parseLiteral("batteryCharge("+agOrj+",_)"));
			    	            Literal literal_batteryChargeStatus = Literal.parseLiteral("batteryCharge("+agOrj+",depleted)");
			    	            addPercept("r3",literal_batteryChargeStatus);
			                 		
			                 }
            		
            		
            	 }
            	 
            	 
            	 
                	if (agOrj.equals("r1"))
    		       	{
    		        		
    		        	instantR1END =  Instant.now();	
    		        	
    		            Duration elapsedR1 = Duration.between(instantR1, instantR1END);
    		            
    		            
    		            System.out.println("Elapsed Time: " + elapsedR1.toMillis() + " milliseconds");
    		            
    		            
    		            agentTimeList.add(new AbstractMap.SimpleEntry<>("r1", elapsedR1.toMillis()));


    		       	}
    		        		
    		       	else if (agOrj.equals("r3"))
    		       	{
    		       		
    		       	instantR3END =  Instant.now();	
    		       		
    		       	 Duration elapsedR3 = Duration.between(instantR3, instantR3END);
    		       	 
    		       	 
    		         System.out.println("Elapsed Time: " + elapsedR3.toMillis() + " milliseconds");
    		         
    		         
    		         agentTimeList.add(new AbstractMap.SimpleEntry<>("r3", elapsedR3.toMillis()));
    		        }
            	 
            	 
            	
         //        System.out.println("Battery R1: "+batteryR1.getCapacity());
          //       System.out.println("VacuumBaGGGGGGGGGGGGGGGGGGGGGGG R1: "+vacuumBagR1.remainingCAP);
             //    System.out.println("Battery R3: "+batteryR3.getCapacity());
            	
                 
                // Graphics g = view.getCanvas().getGraphics();
                 
                 
                 
                 
              //   ((MarsView) view).drawMyObstacle(g, loc.x, loc.y,currentGarbage.intensity);
                 
                 
                //	 Font defaultFont = new Font("Arial", Font.BOLD, 18);
                // 	 view.drawString(g, loc.x, loc.y, defaultFont, String.valueOf((int)currentGarbage.intensity));
                 	
                 //	 view.update();
               //  	 view.repaint();
            	
            	//System.out.println("Intensity Value= " + (agNumber-1)+ " " +intensityValue);
            	
             //   remove(GARB, getAgPos(agNumber-1));
                	
                	
                	
           if (saveFlag)
           {
        	   
        	   double batteryState=0.0;
        	   double garbageBagState=0.0;
        	   
        	   
        	   if (agOrj.equals("r1")) {
        		    batteryState = batteryR1.getCapacity();   
        		    garbageBagState = vacuumBagR1.remainingCAP;
        		   
        	   }
        	   
        	   if (agOrj.equals("r3")) {
        		    batteryState = batteryR3.getCapacity();   
        		    garbageBagState = vacuumBagR3.remainingCAP;
        		   
        	   }
        	   
        	   
      	        
        	   savePositions(loc.x, loc.y, garbageintensityValue, visitedGarbages, new AgentInfo(agOrj,batteryState,garbageBagState,ground_intensityValue));
      	   
           }
                	
                	
                
            } /////////////////////////////////////////////////////////////////////////////////////////////////
            
            
            
        }

	
    }


    public void savePositions(int x, int y, float intensity, ArrayList<GarbageHolder> list, AgentInfo agInfo)
    {
    	
    		
    	
    	
	    	if (!isInList(x,y,list)) {	    		
	    		GarbageHolder gh = new GarbageHolder(x, y, intensity);
	    		gh.addAgentInfo(agInfo);
	    		
	    		list.add(gh);
	    		gh.increaseVisitedCounter();
	    	}
	    	else
	    	{
	    		updateCounterInList(x,y,list,agInfo);
	    	}
	    	
	    	
	    	
	    	
	    	        	            
        }
    
    
    
    public void updateCounterInList(int x, int y, ArrayList<GarbageHolder> list, AgentInfo agInfo) {
        for (GarbageHolder item : list) {
            if (item.getX() == x && item.getY() == y) {
               item.increaseVisitedCounter();  // Found a match, so the object is in the list
               item.agInfo.add(agInfo);
            }
        }
       
    }

	private boolean isInList(int x, int y, ArrayList<GarbageHolder> list) {
	
    	
		for (int i = 0; i < list.size(); i++) {
		    GarbageHolder item = list.get(i);
		    if (item.getX() == x && item.getY() == y) {
		        return true;  // Match found
		    }
		}
        return false; // Found a match, so the object is in the list
		
	}

	public void getPositions( ArrayList<GarbageHolder> list)
    {   String toFile="";
		String abc = "";
		for (int i = 0; i < list.size(); i++) {
		    GarbageHolder item = list.get(i);
		     abc += "X: " + item.x + " Y: " + item.y + " Intensity: " + item.intensity + " NumOfVisits: " + item.visitedCounter + " " ;
		     toFile+=abc;
		    for (int k = 0; k < item.agInfo.size(); k++) {
		    	abc += item.agInfo.get(k).toString()+" ";
		    	toFile+=abc+" \n ";
		    }
		    
		    System.out.println(abc);
		    abc = ""; 
		    
		    
		} 
		
		
		 // Construct the file path with dynamic values
        //String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\Travels_" 
      //          + batteryStart + "_" + vacuumStart + "_" + mapNumber + "_OutBoolean.txt";
        
        
        
        String filePathTotal = "C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\newExperiments\\MapDist"+mapNumber+"\\"
        		 +experimentLogic+"\\"+batteryStart+"_"+vacuumStart+"_Map"+mapNumber+"\\Travels_"+experimentLogic+".txt";

       

        // Write content to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathTotal))) {
            writer.write(toFile);
            System.out.println("File created successfully at " + filePathTotal);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }



    class MarsView extends GridWorldView {

        public Graphics g;




		public MarsView(MarsModel model) {
            super(model, "Mars World", 600);
            defaultFont = new Font("Arial", Font.BOLD, 18); // change default font
            setVisible(true);
            repaint();
           
            
        }

        /** draw application objects */
        @Override
        public void draw(Graphics g, int x, int y, int object) {
        	this.g = g;
        	 repaint();
            switch (object) {
            case MarsEnv.GARB:
                drawGarb(g, x, y);
                break;
            }
        }

        @Override
        public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        	this.g = g;
            String label = "R"+(id+1);
            c = Color.blue;
            if (id == 0) {
                c = Color.yellow;
                if (((MarsModel)model).r1HasGarb) {
                    label += " - G";
                    c = Color.orange;
                }
            }
            super.drawAgent(g, x, y, c, -1);
            if (id == 0) {
                g.setColor(Color.black);
            } else {
                g.setColor(Color.white);
            }
            super.drawString(g, x, y, defaultFont, label);
            repaint();
        }
        
        
        public int mapValue(int value, int inputMin, int inputMax, int outputMin, int outputMax) {
            return (value - inputMin) * (outputMax - outputMin) / (inputMax - inputMin) + outputMin;
        }
        
        
       
        public void drawMyObstacle(Graphics g, int x, int y, float intensityValue) {
        	
        	
        	
        	
        	
        	
        	this.g = g;
        	// more int value means more dirty
        	  int mappedValue = mapValue((int)intensityValue, 0, 100, 0, 255);
        	  
        	  mappedValue = 255-mappedValue; 
        	
        	  Color c = new Color ((int)mappedValue,(int)mappedValue,(int)mappedValue);
        
      
        	
           	
        	
        	
        	
            g.setColor(c);
            g.fillRect(x * cellSizeW + 1, y * cellSizeH+1, cellSizeW-1, cellSizeH-1);
            g.setColor(Color.pink);
            g.drawRect(x * cellSizeW + 2, y * cellSizeH+2, cellSizeW-4, cellSizeH-4);
            
           
        }


        public void drawGarb(Graphics g, int x, int y) {
        	
        	
    //    BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Burak\\eclipse-workspace_earlier\\cleaningRobotsFuzzy\\src\\java\\experiments\\MapDist.txt"));
        	     
        	
	            if (!isInList(x,y,garbageHolder)) {
	            //	Random random = new Random();    
	           //     float intensity = random.nextFloat() * 100;
	            	
	            
	                
	            	Float intensity=0.0f;
	                
	            	  if (( intensity = getNextValue()) != null)
	            	  {
	  	                
	  	            	 //float intensity = getNextValue();
	  	           
	  	              //  counter++;
	  	                garbageHolder.add(new GarbageHolder(x, y,  intensity)); //47.0
	  	                initialMap.add (new GarbageHolder(x, y,  intensity));
	  	           
	  	                
	  	                intensityDirtTotalBegin=getTotalIntensity(garbageHolder);
	  	                
	  	            //    System.out.println(counter+"X AND Y= " + x + " --- " + y); 194 tane tile var.
	            	  }
	                
	                
	                
	                   
	                    // Simulate processing each value (you can add your logic here)
	                }
	                
	                
	          
        
	
	            float intensityValue = returnIntensity(x,y,garbageHolder);
	        	
	              
	        	this.drawMyObstacle(g, x, y,intensityValue);
	        	
	        	intensityDirtTotalEnd=getTotalIntensity(garbageHolder);
	        	
	        	g.setColor(Color.white);
	            
	            
	            
	            
	     
	            
	            
	            if (intensityValue!=-1.0f) {
	            	drawString(g, x, y, defaultFont, String.valueOf((int)intensityValue));
	            
	            }
	            
	   
            
     
            
            
            
        }
        
        
   
        
        
        public boolean isInList(int x, int y, ArrayList<GarbageHolder> list) {
        	for (int i = 0; i < list.size(); i++) {
        	    GarbageHolder item = list.get(i);
        	    if (item.getX() == x && item.getY() == y) {
        	        return true;  // Match found
        	    }
        	}
            return false; // Found a match, so the object is in the list
        }
        
        public void updateIntensityInList(int x, int y, ArrayList<GarbageHolder> list, float intensity) {
        	
        	for (int i = 0; i < list.size(); i++) {
        	    GarbageHolder item = list.get(i);
        	    if (item.getX() == x && item.getY() == y) {
        	        item.intensity = intensity;  // Found a match, update intensity
        	    }
        	}
           
        }
        
     
        
        public float getTotalIntensity(ArrayList<GarbageHolder> list) {
            float totalIntensity = 0.0f;
            for (int i = 0; i < list.size(); i++) {
                GarbageHolder item = list.get(i);
                totalIntensity += item.intensity;
            }
            return totalIntensity;
        }
        
        
        
         
        public float returnIntensity(int x, int y, ArrayList<GarbageHolder> list) {
            for (GarbageHolder item : list) {
                if (item.getX() == x && item.getY() == y) {
                    return item.intensity;  // No match found, so the object is not in the list
                }
            }
            return -1.0f; // Found a match, so the object is in the list
        }

    }
    
    public class AgentInfo
    {
    	
    	String agentName = "";
    	double agentPastGarbageBagCap = 0.0;
    	double agentPastVacuumBagCap = 0.0;
    	double pastRemainedIntensity =0.0;
    	
    	
    	public AgentInfo (String agName, double agPastGarb, double agPastVacuum, double remainedIntensity) {
    		
    		
    		this.agentName = agName;
    		this.agentPastGarbageBagCap = agPastGarb;
    		this.agentPastVacuumBagCap = agPastVacuum;
    		this.pastRemainedIntensity = remainedIntensity;
    		
    	}
    	
    	@Override
    	public String toString() {
    	    return  agentName+ " " + agentPastGarbageBagCap + " " + agentPastVacuumBagCap + " "+ pastRemainedIntensity;
    	}
    }
    
    public class GarbageHolder
    {
    	public int itemCounter=0;
        private int x;
        private int y;
        private float intensity;
        private String agName;
        private int visitedCounter=0;
        ArrayList<AgentInfo> agInfo = new ArrayList<AgentInfo>();
        
       
        // Constructor
        public GarbageHolder(int x, int y, float intensity) {
            this.x = x;
            this.y = y;
            this.intensity = intensity;
            itemCounter++;
           
        }
       
        public void addAgentInfo (AgentInfo agInfo) {
        	
        	this.agInfo.add(agInfo);
        }
        
        // Getter and Setter methods for x
        public int getItemCounter() {
            return itemCounter;
        }
        

        // Getter and Setter methods for x
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        // Getter and Setter methods for y
        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        // Getter and Setter methods for intensity
        public float getIntensity() {
            return intensity;
        }

        public void setIntensity(float intensity) {
            this.intensity = intensity;
        }

        // Method to display the object information
        @Override
        public String toString() {
            return "GarbageObject [x=" + x + ", y=" + y + ", intensity=" + intensity + "]";
        }

		public String getAgName() {
			return agName;
		}

		public void setAgName(String agName) {
			this.agName = agName;
		}



		public int getVisitedCounter() {
			
			return visitedCounter;
		}



		public void increaseVisitedCounter() {
			this.visitedCounter++;
			//this.visitedCounter = visitedCounter;
		}
        
        
        
        
        
    }
    
    
    public class BatteryModel {
        private double capacity; // Battery capacity in the range of 0-100
        private double k;        // Sensitivity factor for action effect

        public BatteryModel(double initialCapacity, double k) {
            this.capacity = initialCapacity;
            this.k = k;
        }

        // Simulate the battery decrease based on an action value
        public boolean performAction(double actionValue, double timeInterval) {
            // Calculate the battery decrease based on the exponential decay model
            double decreaseFactor = Math.exp(-k * actionValue * timeInterval);
            capacity *= decreaseFactor;
            // Ensure the battery capacity does not go below 0
            if (capacity < 0) {
                capacity = 0;
                return false;
            }
            
            return true;
        }

        public double getCapacity() {
            return capacity;
        }


    }
    
    
    public class VacuumBag {
        private double capacity;        // Maximum capacity of the vacuum bag
        private String agentName;    // Name of the agent using the vacuum bag
        private double remainingCAP;     // Remaining load in the vacuum bag

        // Constructor
        public VacuumBag(double d, String agentName) {
            this.capacity = d;
            this.agentName = agentName;
            this.remainingCAP = this.capacity; // Initialize remaining load to capacity
        }

        // Method to add items to the vacuum bag
        public boolean addItems(double amount) {
            if (remainingCAP - amount >= 0) {
            	remainingCAP -= amount;
                System.out.println("Vacuumed " + amount + " items. Remaining load: " + remainingCAP + ".");
                return true;
            } else {
            	return false;
            }
        }

        // Method to get details of the vacuum bag
        public String getDetails() {
            return "Agent Name: " + agentName + ", Capacity: " + capacity + ", Current Load: " + remainingCAP;
        }

        // Method to check if the vacuum bag is full
        public boolean isFull() {
            return remainingCAP >= 0;
        }

    
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

}
