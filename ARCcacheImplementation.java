import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class ARCcacheImplementation {

	/******************** Creating a linked list. This is used as a data Structure to implement cache **************/

	public static LinkedList<Integer> T1 = new LinkedList<Integer>();
	public static LinkedList<Integer> T2 = new LinkedList<Integer>();
	public static LinkedList<Integer> B1 = new LinkedList<Integer>();
	public static LinkedList<Integer> B2 = new LinkedList<Integer>();

	public static void main(String[] args) throws IOException {

		long cachehit =0;
		long cachemiss =0;
		int cache_size=0;
		long Reqnum=0;
		String trace_name;	
		int p=0;


		try{

			/********************* Accepting Inputs for Cache size and Trace file **********************/

			System.out.println("Enter the Cache Size : ");
			Scanner Scan=new Scanner(System.in);
			cache_size=Scan.nextInt();



			System.out.println("Enter the full path of the trace file to give as an input : ");
			trace_name=Scan.next();			


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

					/*************** Case 1 ***********************/

					if(T1.contains(P1)){
						cachehit++;
						int index = T1.indexOf(P1);
						T1.remove(index);
						T2.addFirst(P1);					
					}
					else if(T2.contains(P1)){
						cachehit++;
						int index = T2.indexOf(P1);
						T2.remove(index);
						T2.addFirst(P1);					
					}

					/********************* Case 2 ***********************/

					else if(B1.contains(P1)){
						cachemiss++;
						int d1;
						if(B1.size()>=B2.size()){
							d1=1;
						}
						else{
							d1=(B2.size()/B1.size());
						}

						if(p+d1<cache_size){
							p=p+d1;
						}
						else{
							p=cache_size;
						}

						Replace(P1,p);

						int index = B1.indexOf(P1);
						B1.remove(index);
						T2.addFirst(P1);						


					}

					/*********************** Case 3 ***********************/

					else if(B2.contains(P1)){
						cachemiss++;
						int d2;
						if(B2.size()>=B1.size()){
							d2=1;
						}
						else{
							d2=(B1.size()/B2.size());
						}

						if(p-d2>0){
							p=p-d2;
						}
						else{
							p=0;
						}

						Replace(P1,p);

						int index = B2.indexOf(P1);
						B2.remove(index);
						T2.addFirst(P1);						

					}

					/***************** Case 4 ******************/

					else{
						cachemiss++;

						/********************* Case A *******************/

						if(T1.size()+B1.size()==cache_size){
							if(T1.size()<cache_size){
								B1.removeLast();
								Replace(P1,p);
							}

							else{
								T1.removeLast();
							}
						}

						/********************* Case B ********************/	

						else if((T1.size()+B1.size())<cache_size){

							int Totalcache= (T1.size()+T2.size()+B1.size()+B2.size());

							if(Totalcache>=cache_size){

								if((T1.size()+T2.size()+B1.size()+B2.size())==(2*cache_size)){
									B2.removeLast();
								}
								Replace(P1,p);
							}
						}
						T1.addFirst(P1);
					}
				}
			}

			readfile.close();			// Close file




			/******************************* Final Calculation for hit ratio *******************************/


			cachehit=cachehit*100;
			float f=cachehit;
			float g=Reqnum;
			float hitratio=f/g;
			
			double final_hitratio = Math.round(hitratio*100.0)/100.0;


			/************* End Timer ******************************/

			long end_timer=System.currentTimeMillis();

			long Time_taken = end_timer-start_timer;	// in milliseconds

			Time_taken=Time_taken/1000;		// in seconds
			//long Time_taken1=Time_taken/60;		//in mins





			/********************* Displaying the Results ******************************/


			System.out.println("Cache size : "+(T1.size()+T2.size()));
			System.out.println("Number of cachehits : "+ cachehit/100);
			System.out.println("Number of cachemisses : "+cachemiss);
			System.out.println("Total number of requests : "+Reqnum);
			System.out.println("Hitratio : "+hitratio+"%");
			System.out.println("Hit ratio after rounding off : "+final_hitratio);
			System.out.println("Time taken : "+Time_taken+ " Seconds");

		}catch(IOException e){
			System.out.println("File Not found.. Please Check the Path and Restart the Program..  =>> " + e.getMessage());
		}


		/********************** Replace Function ******************************************/

	}
	static void Replace(int P1,int p){

		if((T1.size()!=0)&&((T1.size()>p)||(B2.contains(P1)&&(T1.size()==p)))){
			int last=T1.getLast();
			T1.removeLast();
			B1.addFirst(last);
		}
		else {
			int last=T2.getLast();
			T2.removeLast();
			B2.addFirst(last);
		}
	}

}
