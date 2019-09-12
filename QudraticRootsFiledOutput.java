/*@Author Mangaliso Moses Mngomezulu
   Aim: To compute  the roots of a quadratic equation, taking the constants(a ,b , c ) from a file and printing 
   the output into  anther file. In here we calculate the imaginary roots also.*/
   
import java.util.*;
import java.io.*;  

public class QudraticRootsFiledOutput{
    
   private static double a = 0, b = 0,c = 0 ;     //the co-efficients of the quadratic equation 
   private static String solution1 = "", solution2 = "";  //these will hold values to be output to the file , the string representation of the solutions
      
   public static void main(String[] args){
     
      Scanner fromFile ,fromKeyboard; // this is the Scanner object to be used to get the constants of the equation from a file 
      fromKeyboard = new Scanner(System.in);
      
      FileReader file = null;//we will read the constants of the equation from a file

      String fileName , fileRecord = "" , constantOfQuadratic = ""; /*fileName will come from the user , fileRecord will be read from the file
                                                                      the constantOfQuadratic can be a , b or c*/
      StringTokenizer token;  // will be used to read the file records
      /*taking input from the file and handling the possibility of wrong input type and IOException*/
      
           try{ //for file name type "QuadraticValues.txt"
               System.out.print("Enter source of constants file name: ");  fileName = fromKeyboard.next().trim();/*take file name and remove unecessary spaces in both ends*/
               if(!fileName.endsWith(".txt"))  /*if the file name entered doesnot end with .txt*/
                  fileName += "txt";/*add the appropiate file extension*/
             
               file = new FileReader(fileName); //open the file
               fromFile = new Scanner(file);//connect the Scanner to the file for reading
            
               while(fromFile.hasNextLine()){  //check if there is still a line to read
                   fileRecord = fromFile.nextLine();  //read the line 
                   token = new StringTokenizer(fileRecord , "="); //the "=" sign is the separator of the file data of intrest
                   constantOfQuadratic =token.nextToken(); //assigning to the letter at the beginning
                   
                   if(constantOfQuadratic.trim().equalsIgnoreCase("a"))  //check if the record begins with a 
                      a = Double.parseDouble(token.nextToken().trim()); // convert to the expected type
                      
                   else if (constantOfQuadratic.trim().equalsIgnoreCase("b"))//check if the record begins with b
                      b = Double.parseDouble(token.nextToken().trim());// convert to the expected type
                      
                   else if(constantOfQuadratic.trim().equalsIgnoreCase("c"))//check if the record begins with c
                      c = Double.parseDouble(token.nextToken().trim());// convert to the expected type
                   else {
                         System.out.println("The file record has an unexpected format , Please fix the structure of the file.");
                         System.out.println("Format expeceted \na = any number\nb = any number\nc = any number.");
                         System.exit(1);
                         }//end else
               }//end while for reading the file
               
           fromFile.close();//avoid resource leaks , Very Crucial
           
           if(a == 0)
              { /*avoid division by 0, on (2*a) part */
               solution1 = solution2 = "\nCannot compute the roots  the \"a\" value is 0";
               
               //write the respaonse into the file
               fileSolutions(solution1 , solution2);
               
               
               System.exit(1);/*terminate the run/application*/
              }
           else if(discriminant(a, b, c) < 0)/*the roots of the equation exists in the complex plane */
                {/*a case when the discriminant is  negative we factor out the square root of -1 from the discriminant
                  when the discriminant is under the square root sign , then we get the squre root of the discriminant sine 
                  it is positive by then. The squre root of -1 is assigned the constant name (i) in the set of complex numbers
                  so it must be included to reserve equivalence.So the root of discriminant will be multiplied by (i) */
                  
 
                  /*b is multiplied by -1 as in the quadratic formula ,it is prefarable to divide by 2*a separately so that
                    the solution looks simplified to the user*/
               
                  //below is for the case when th root of the discriminant is positive 
                  solution1  = String.format("x = %.2f + %.2fi\n" , (-1*b/2*a) , Math.sqrt(-1*discriminant(a, b, c))/2*a);
                  
                  //below is for the case when the root of the discriminant is negative
                  solution2 = String.format("x = %.2f - %.2fi\n" , (-1*b) / 2*a , Math.sqrt(-1*discriminant(a, b, c)) / 2*a);
                  
                  //file the solutions
                  fileSolutions(solution1 , solution2);
                  
                }//end if for when the discriminant is negative
                 
            //give solutions values
            else {
                  solution1 = String.format("x  =  %.2f\n",negativeRoot(a, b, c));
                  solution2 = String.format("x = %.2f\n",positiveRoot(a ,b, c));
                  
                  fileSolutions(solution1 , solution2);
                  }//end  else
   
            }/*end try */
            
            catch(InputMismatchException e){/*reading unexpected input*/
                 System.out.println("The file is not formed properly, format expeceted \na = any number\nb = any number\nc = any number.");
                 System.exit(1);//the app terminates and the user has to fix the structure of the file
                 
           }catch(FileNotFoundException e){//Tell the user the specified file name
                 System.err.println("The specified file doesnot exist.");
           }
            /*end catch*/
   }/*end main*/
   
   
   /*other static methods*/
   public static double discriminant(double a , double b , double c){/*return discriminant(b^2 - 4*a*c)*/
      return Math.pow(b , 2) - 4*a*c;   
   }
   
   public static double negativeRoot(double a , double b , double c){/*using negative root of the discriminant*/
      return (-b - Math.sqrt(discriminant(a,b,c))) / 2*a;
   }
   
   public static double positiveRoot(double a , double b , double c){/*using positive root of the discriminant*/
      return (-b + Math.sqrt(discriminant(a,b,c))) / 2*a;
   }
   
   
   /* the method will take String representations of the two solutioins.
    in the file , the values of a , b, c will be printed then the solutions below them.*/
   public static void fileSolutions(String root1, String root2){
      
      Scanner takeFileName = new Scanner(System.in);
      
      //use a file name for the solutions that is provided by the user
      
      System.out.print("Enter solutions file name: ");
      String solutionsFileName = takeFileName.next().trim();
      
      //check if the file has the appropiate extension(.txt) add the extension if it is not found
      if(!solutionsFileName.endsWith(".txt"))
          solutionsFileName += ".txt";
      
      //link the file to the PrintWriter for writing to the file of the users choice
      File solutionsFile = new File(solutionsFileName);
      PrintWriter printToFile = null ;
      
      //attempt writing the solutions into the file
      try{
      
          printToFile = new PrintWriter(solutionsFile);
          
          
          //the fisrt thing will be to print the quadratic's constants to the file
          printToFile.println("Constants:\n");
          printToFile.printf("a = %.2f\nb = %.2f\nc = %.2f\n", a ,b , c);
          
          //next the corresponding solutions
          printToFile.println("\nSolutions:");
          printToFile.printf("\n%s\n%s" , solution1 , solution2);
            
         }catch(IOException ioe){
         
               System.err.println("Error while writing to the file");
               ioe.printStackTrace();     
         }//end catch
         
     printToFile.close();//avoid resource leaks
   }
}/*end of program*/
