

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.Battery;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;
import ev3dev.sensors.ev3.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;


import java.util.Random; 


//Jason Imports Start
import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;
import jason.environment.grid.Location;
import java.awt.Font;
import java.awt.Graphics;
import java.lang.invoke.StringConcatFactory;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Logger;
//Jason Imports End
import java.util.stream.IntStream;

/*
 * 
 *         //To Stop the motor in case of pkill java for example
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Emergency Stop");
                motorLeft.stop();
                motorRight.stop();
            }
        }));
 */

public class PLEnv extends Environment {
	
	
		
	
	public static final Term motorB = Literal.parseLiteral("actuate(motorB)");
	public static final Term initComponents = Literal.parseLiteral("initialize_comps");
	public static final Term senseDrop = Literal.parseLiteral("senseDrop");
	
	public static final Term conveyorStart = Literal.parseLiteral("conveyorStart");
	public static final Term conveyorStop = Literal.parseLiteral("conveyorStop");
	public static final Term conveyorReverse = Literal.parseLiteral("conveyorReverse");
	
	
	
	
	
	
	public static final Term sampleColor = Literal.parseLiteral("sampleColor");
	public static final Term buttonPressed = Literal.parseLiteral("buttonPressed");
	
	public static final Term productState = Literal.parseLiteral("productState");
	public static final Term dropProduct = Literal.parseLiteral("dropProduct");
	
	public static final Term shredStart = Literal.parseLiteral("shredStart");
	public static final Term shredStop = Literal.parseLiteral("shredStop");
	
	public static final Term checkEmergency = Literal.parseLiteral("checkEmergency"); //motor1.stop();
	
	
	
	//push Agent
	
	public static final Term pushMotor = Literal.parseLiteral("pushMotor");
	
	//sort Agent
	
	public static final Term goRedPosition = Literal.parseLiteral("goRedPosition");
	
	
	public static final Term goBluePosition = Literal.parseLiteral("goBluePosition");
	public static final Term goGreenPosition = Literal.parseLiteral("goGreenPosition");
	public static final Term goYellowPosition = Literal.parseLiteral("goYellowPosition");
	
	
	
	// GoRedPosition()
	
	
		static EV3LargeRegulatedMotor motor2 = null;
	
	
	
//	public static final Literal senseDrop = Literal.parseLiteral("senseDrop");
	static int count=0;
	static Logger logger = Logger.getLogger(PLEnv.class.getName());
	static Random rand = null;
	static int n= 0;
	
	//EV3LargeRegulatedMotor motorBact;
	
	 public PLEnv() {
	//        this.motorBact = null;
		 		 
	    }
	
	 @Override
	    public void init(String[] args) {
		// final EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
    // motorBact  = new EV3LargeRegulatedMotor(MotorPort.B);
		 System.out.println("Sensor modes and Motors are initializing"); 
		 rand = new Random();
		 
		 clearAllPercepts();
		 
		 motor2  = new EV3LargeRegulatedMotor(MotorPort.D); 

		 
	 }
	 
	   
	  

	@Override
    public boolean executeAction(String ag, Structure action) {
		
		logger.info(ag+" doing: "+ action);
        try {
            if (action.equals(motorB)) {
            	
            	
            } 
           
            else if(action.equals(initComponents)) {
            	
            	initComponents_func();
            }
            
                     
            else if(action.equals(conveyorStart)) {
            	
            	
            }
            	
            
            else if(action.equals(conveyorStop)) {
            	
            	
            }
            
            else if(action.equals(conveyorReverse)) {
            	
            
            }
            
            
          
            
                     
		
            
		    else if(action.equals(dropProduct)) {
		    	
		    
		    } 
		    
		    else if(action.equals(shredStart)) {
		    	
			
			      
		    }
            
		    else if(action.equals(shredStop)) {
		    	
			
		           
		    }            
            
		    else if(action.equals(checkEmergency)) {
		    	
			 
				      
			}
            
		    else if(action.equals(goRedPosition)) {
		    	
		    	GoRedPosition();
					      
				}
            
		    else if(action.equals(goGreenPosition)) {
		    	
		    	GoGreenPosition();
					      
				}
            
		    else if(action.equals(goBluePosition)) {
		    	
		    	GoBluePosition();
					      
				}
            
		    else if(action.equals(goYellowPosition)) {
		    	
		    	GoYellowPosition();
					      
				}
            
            
            //push agent
            
		    else if(action.equals(pushMotor)) {
		    	
		    	
					      
				}
			    
            
         
           
            
            	
            
       /*     else if(action.equals(senseDrop)) {
            	
            	senseDrop_func();
            }
    */        
            
            else {
                return false;
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            Thread.sleep(1);
        } catch (Exception e) {}
        informAgsEnvironmentChanged();
        return true;
		
	}	
	

	



	

	/* private void senseDrop_func() {

		 
		
		
	}*/

	

	
	
	
	

	
	
	
	
	/* CONVEYOR 2 STARTS*/ 
	

  
    
    
	public  void GoGreenPosition() {
     System.out.println("GREEN ACTIVE");
    }
    
    
    
    public  void GoBluePosition() {
    	  System.out.println("BLUE ACTIVE");
    }   
    
    
    public  void GoYellowPosition(){
     	  System.out.println("YELLOW ACTIVE");
    }
    public  void GoRedPosition() {
		
		
		motor2.setSpeed(800);
        Delay.msDelay(1);
        motor2.backward();
        Delay.msDelay(3000);
        motor2.stop();
        motor2.getPosition();
		
		
		
     	  System.out.println("RED ACTIVE");
    }
  
	
	
	

	private void initComponents_func() {
		System.out.println("Drop Motor is resetting...");
		
		/* reset drop motor*/
		//dropmotor.setSpeed(100);
        //Delay.msDelay(1);
        //dropmotor.rotateTo(0); 
		
	}
	
	


}
