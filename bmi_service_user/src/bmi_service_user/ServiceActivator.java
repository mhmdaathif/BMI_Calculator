package bmi_service_user;

import java.math.RoundingMode;

import java.text.DecimalFormat;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import bmi_service_producer.ServiceBmi;



public class ServiceActivator implements BundleActivator {

	Scanner inputs = new Scanner(System.in);
	
	// variable for continue checking
	String checkAgain = "";
	
	// reference
	ServiceReference serviceReference;
	
	public void start(BundleContext context) throws Exception {
		System.out.println("User activated!\n");
		
		do {
			serviceReference = context.getServiceReference(ServiceBmi.class.getName());
		
			double weight = 0;
			double height = 0;
			double bmi = 0;
		
			System.out.print("Enter your weight (kg) : ");
			weight = inputs.nextDouble();
			System.out.print("Enter your height (cm) : ");
			height = inputs.nextDouble();
		
			ServiceBmi servicebmi = (ServiceBmi)context.getService(serviceReference);
			bmi = servicebmi.bmi(weight, height);
			
			// set decimal places
			DecimalFormat format = new DecimalFormat("#.###");
			format.setRoundingMode(RoundingMode.CEILING);
			
			System.out.println("Your BMI value is      : "+format.format(bmi));
			
		
			// Status
			if(bmi <= 18) {
				System.out.println("\nStatus = Unhealthy !!!");
				System.out.println("You are in an UNDERWEIGHT condition!");
			}
			if(bmi > 18 && bmi < 25) {
				System.out.println("\nStatus = Healthy ");
				System.out.println("Good! You are a HEALTHY person");
			}
			if(bmi>=25) {
				System.out.println("\nStatus = Unhealthy !!!");
				if(bmi<=29) {
					System.out.println("You are in an OVERWEIGHT condition");
				}
				if(bmi > 29 && bmi <= 39) {
					System.out.println("You are in OBESE condition ! ");
				}
				if(bmi > 39) {
					System.out.println("You are in EXTREMELY OBESE condition !! ");
				}
			}
		
			
			// ask user to continue
			System.out.print("\nDo you want to check again (y/n) ? ");
			checkAgain = inputs.next();
			System.out.println();
			
		}while(checkAgain.equals("y"));
		
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("User deactivated!");
		context.ungetService(serviceReference);
	}
	

}