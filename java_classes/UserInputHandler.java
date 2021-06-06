package java_classes;
import java.util.Scanner;

public class UserInputHandler extends NewContract{
    public UserInputHandler(){
     super();

    }

    private void optionToGoBack(){
        System.out.println("something wrong.. ");
        System.out.println("press 1: try again" );
        System.out.println("press 2: go back to main menu" );
        if (new Scanner(System.in).nextLine().equals("2"))
         new selectOptions().optionSelectedByUser();  //main menu
       
    }


    public void contractHolderName(){
        boolean loop= false;
        while(!loop){
            try{
                System.out.print("Enter name of contract  holder (firstname lastname): ");
                if(this.setContractHolderName(new Scanner(System.in).nextLine()))
                  loop=true;
                else{
                    loop=false;
                    this.optionToGoBack();
                }
             } catch(Exception e){
                 System.out.println("wrong user input");

             }
    }
}


public void contractRefNum(){
    boolean loop= false;
  
    while(!loop){
        try{
            System.out.print("Enter reference number (e.g. PT123N): ");
            if(this.setContractRefNum(new Scanner(System.in).nextLine()))
              loop=true;
            else{
              loop = false;
              this.optionToGoBack();

            }
         } catch(Exception e){
             System.out.println("wrong user input");

         }
}
}


public void selectPackage(){
    boolean loop= false;
  
    while(!loop){
        try{
            System.out.print("Enter package Id (e.g. 1 2 or 3): ");
            if(this.setPackage(new Scanner(System.in).nextByte()))
              loop=true;
            else{
                loop=false;
                optionToGoBack();

            }
         } catch(Exception e){
             System.out.println("wrong user input");

         }
}
}


public void selectDataPlan(){
    boolean loop= false;
  
    while(!loop){
        try{
            System.out.print("Enter Data bundle (e.g. 1 2 3 or 4 ): ");
            if(this.setDataBundle(new Scanner(System.in).nextByte()))
              loop=true;
            else{
                loop=false;
                optionToGoBack();

            }
         } catch(Exception e){
             System.out.println("wrong user input");

         }
}
}

public void selectInternationalCall(){
    boolean loop= false;
  
    while(!loop){
        try{
            System.out.print("Enter International Plan (e.g. Y or N): ");
            if(this.setInternationalCall(new Scanner(System.in).nextLine()))
              loop=true;
            else{
                loop=false;
                optionToGoBack();

            }
         } catch(Exception e){
             System.out.println("wrong user input");

         }
}
}

public void selectContractPeriod(){
    boolean loop= false;
  
    while(!loop){
        try{
            System.out.print("Enter contract length (e.g. 1 12 18 or 24 months ): ");
            if(this.setContractLength(new Scanner(System.in).nextByte()))
              loop=true;
            else{
                loop=false;
                optionToGoBack();

            }
         } catch(Exception e){
             System.out.println("wrong user input");

         }
}
}

public String[] newContractArr(){
String[] arr = new String[10];
arr[0]= this.setContractStartDate();

if(this.getpackage()!=0)
 arr[1]= StaticVariables.packageArry[this.getpackage()-1]; 
else  
arr[1] = null;

if(this.getDataBundle()!=0)
 arr[2]= StaticVariables.dataBuddleArry[this.getDataBundle()-1]; 
else  
arr[2] = null;

arr[3]= String.format("%s", this.getContractLength());  
arr[4] = this.getInternationalCall();
arr[5]= this.getContractRefNum();
arr[6]= this.getCName();
arr[7] = String.format("%s", this.getContractDiscount() + "%");

if(this.getTypeOfBussiness()!=' ')
 switch(this.getTypeOfBussiness()){
     case 'N':
      arr[8]=StaticVariables.accountType[0]; 
      break;
     case 'B':
     arr[8]=StaticVariables.accountType[0]; 
     break;
     case 'C':
     arr[8]=StaticVariables.accountType[2]; 
     break;
     default:
     break;     
      
 }
  
else  
 arr[8] = null;
//arr[8] = String.format("%s", this.getTypeOfBussiness());
arr[9] = String.format("%.2f", this.getDiscountedPrice());
return arr; 
}


public String contractTextForm(){
 String contract="";
 contract = String.format("%8s %6s %6s %8s %8s %6s %8s %8s %8s %.2f",this.getContractStartDate(), this.getpackage(), this.getDataBundle(),
 this.getContractLength(), this.getInternationalCall(), this.getContractRefNum(), this.getCName().split(" ")[0], this.getCName().split(" ")[1], 
 this.getTypeOfBussiness(), this.getDiscountedPrice());
 
 return contract; 
} 
}
