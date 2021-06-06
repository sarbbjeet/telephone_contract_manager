package java_classes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class ReadWriteTextFile{

public ReadWriteTextFile(){
    

}


public boolean isfileAvailable(String path){

    File file = new File(path);
    if(file.exists()){
        return true;
    }
    else{
        return false; 
    }
}

public boolean writeDataToFile(String fileName, String allcontracts){
    boolean writeSuccessfully= false;
    try (FileWriter f = new FileWriter(new File(fileName))
    // BufferedWriter b = new BufferedWriter(f); 
     //PrintWriter p = new PrintWriter(b);) 
    )
    {
    //System.out.println(allcontracts);
     f.write(allcontracts);    
    //p.print(allcontracts);
    writeSuccessfully= true;

    }catch(Exception e){
     writeSuccessfully= false; 
    }
    return writeSuccessfully; 
}

/* pass data parameter in the form of string */ 
public boolean appendDataToFile(String fileName, String data){
    boolean appendSuccessfully= false; 
    try (FileWriter f = new FileWriter(fileName, true);
       BufferedWriter b = new BufferedWriter(f); 
       PrintWriter p = new PrintWriter(b);) 
    { 
       if(!data.equals(""))
        p.println(data);
       else{
           System.out.println("data length is null");
           appendSuccessfully=false;
       }
      } catch (IOException i) { i.printStackTrace(); appendSuccessfully=false; }
       
      return appendSuccessfully; 
  }



/* pass data parameter in the form of string array */ 
public boolean appendDataToFile(String fileName, String[] data){
  boolean appendSuccessfully= false; 
  String fString="";
  try (FileWriter f = new FileWriter(fileName, true);
     BufferedWriter b = new BufferedWriter(f); 
     PrintWriter p = new PrintWriter(b);) 
  { 
     if(data.length>0){ 
      for(int i=0 ;i<data.length;i++)
        fString += String.format("%8s", data[i]);
      p.println(fString);
     }
     else{
         System.out.println("data length is null");
         appendSuccessfully=false;
     }
    } catch (IOException i) { i.printStackTrace(); appendSuccessfully=false; }
     
    return appendSuccessfully; 
}


public void readFile(String file, ReadCallback callback, byte monthTag){ 
       try{
           FileReader fileReader = new FileReader(file);
           
           int data= fileReader.read();
           String stringOutput="";
           while(data !=-1){
            char c = (char)data;
            stringOutput +=c; 
            data= fileReader.read();
           }
           fileReader.close();
           callback.readFileCallback(stringOutput, monthTag);
        
      }
      catch(Exception e){
       
      } 
    }


    
}
