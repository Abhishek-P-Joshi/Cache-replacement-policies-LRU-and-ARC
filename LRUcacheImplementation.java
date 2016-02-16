import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;



public class LRUcacheImplementation {

	public static void main(String[] args) throws IOException {


		/***************** Initializing variables ********************************/

		long cachehit =0;
		long cachemiss =0;
		int cache_size=0;
		long Reqnum=0;
		String trace_name;
		int i=0;

		try{


			/********************* Accepting Inputs for Cache size and Trace file **********************/

			System.out.println("Enter the Cache Size : ");
			Scanner Scan=new Scanner(System.in);
			cache_size=Scan.nextInt();

			System.out.println("Enter the full path of the trace file to give as an input : ");
			trace_name=Scan.next();	





			/******************** Creating a linked list. This is used as a data Structure to implement cache **************/

			LinkedList<Integer> LRUcache = new LinkedList<Integer>();


			/********** Starting Timer *********************/

			long start_timer=System.currentTimeMillis();




			/*
			 * 
			 ************** Start Reading the inputs from the Trace file *********************
			 * 
			 */

			BufferedReader readfile = new BufferedReader(new FileReader(trace_name));

			while (true) {
				String line = readfile.readLine();
				if (line == null) {
					break;
				}




				/********* Extracting the Start Address and Number of blocks from the trace file **********/

				int j=line.indexOf(' ');
				String p1=line.substring(0,j);
				int P1=Integer.parseInt(p1);
				int k=line.indexOf(' ',j+1);
				String p2=line.substring(j+1,k);
				int P2=Integer.parseInt(p2);
				int P3=P1+P2;




				/********************* Checking for Cache hits and Cache misses ************************/

				for(P1=P1;P1<(P3);P1++){
					Reqnum++;
					if(LRUcache.contains(P1)){
						cachehit++;
						int index = LRUcache.indexOf(P1);
						LRUcache.remove(index);
						LRUcache.addFirst(P1);
					}			    	
					else{
						cachemiss++;
						if(LRUcache.size()>=cache_size){
							LRUcache.removeLast();
							LRUcache.addFirst(P1);
						}
						else {
							LRUcache.addFirst(P1);
						}
					}
				}
				i++;
			}

			readfile.close();			// Close file


			/************* End Timer ******************************/

			long end_timer=System.currentTimeMillis();

			long Time_taken = end_timer-start_timer;	// in milliseconds

			Time_taken=Time_taken/1000;		// in seconds



			/******************************* Final Calculation for hit ratio *******************************/


			cachehit=cachehit*100;
			float f=cachehit;
			float g=Reqnum;
			float hitratio=f/g;
			
			double final_hitratio = Math.round(hitratio*100.0)/100.0;






			/********************* Displaying the Results ******************************/


			System.out.println("Cache size : "+LRUcache.size());
			System.out.println("Number of cachehits : "+ cachehit/100);
			System.out.println("Number of cachemisses : "+cachemiss);
			System.out.println("Total number of requests : "+Reqnum);
			System.out.println("Hitratio : "+hitratio+"%");
			System.out.println("Hit ratio after rounding off : "+final_hitratio);
			System.out.println("Time taken : "+Time_taken+ " Seconds");


		}catch(IOException e){
			System.out.println("File Not found.. Please Check the Path and Restart the Program..  =>> " + e.getMessage());
		}

	}
}


