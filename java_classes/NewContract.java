package java_classes;
import java.time.LocalDate;
import java.util.regex.Pattern;

import javax.lang.model.util.ElementScanner14;

public class NewContract{
private String cName="";
private String RefNum="";
private String contractStartDate="";
private byte packageId=0; 
private byte dataBundleId=0;
private byte contractLength=0; 
private char typeOfBusiness=' ';
private byte discount=0;
private String internationalPlan="";
private float monthlyDiscountedPrice=0;

 //constructor   
public NewContract(){

}

public boolean setDefaultParametersValue(String contract){
try{
String[] col = contract.split("\\s+");   //get column array by spliting double spaces
this.setContractStartDate(col[0]);   // set date
this.setPackage((byte)Integer.parseInt(col[1]));
this.setDataBundle((byte)Integer.parseInt(col[2]));
this.setContractLength((byte)Integer.parseInt(col[3]));
this.setInternationalCall(col[4]);
this.setContractRefNum(col[5]);
this.setContractHolderName(String.format("%s %s", col[6], col[7]));

return true; 
}
catch(Exception e){
//error to set default values // 
return false;
}
}

public boolean setContractRefNum(String refNum){
   String RegE ="[a-zA-Z]{2}[0-9]{3}[BCN]{1}";  //regular expression( ref num- BT123C)
   if(Pattern.matches(RegE, refNum)){
        //System.out.println("matched....");
        this.RefNum = refNum;
        this.getTypeOfBussiness(); 
        return true;
    }
    else{
     this.RefNum = ""; 
     return false;
    }
}

public String getContractRefNum(){
return this.RefNum;
}




public boolean setContractHolderName(String name){
    if(name.length()<25 && name !=""){
        this.cName =name; 
        return true;
    }
    else{ 
        this.cName="";  
        return false;
    }
}

public String getCName(){
return this.cName;
}

public boolean setPackage(byte tPackageId){
    if(!(tPackageId>=1 && tPackageId<=3)){ 
      this.packageId = 0;  
      return false;
    }
    else{
    this.packageId= tPackageId;  
    return true;
    }
}
public byte getpackage(){
    return this.packageId; 
}


public boolean setDataBundle(byte tDataBundleId){
    if(!(tDataBundleId>=1 && tDataBundleId<=4))
    {
       this.dataBundleId=0;
        return false;   
    } 
    else{
    this.dataBundleId= tDataBundleId;  
    return true;
    }
}
public byte getDataBundle(){
    return this.dataBundleId; 
}


public boolean setInternationalCall(String tInc){
    if(!(tInc.equals("N")|| tInc.equals("Y"))){
        this.internationalPlan="";
        return false;  
    } 
    else{
    this.internationalPlan= tInc;  
    return true;
    }
}
public String getInternationalCall(){
    return this.internationalPlan; 
}



public boolean setContractLength(byte tPeriod){
    if(!(tPeriod==1 || tPeriod==12 || tPeriod==18 || tPeriod ==24)){
        this.contractLength=0;
        return false;
    } 
    else{
    this.contractLength= tPeriod;  
    return true;
    }
}
public byte getContractLength(){
    return this.contractLength;  
}

public String setContractStartDate(){
LocalDate date = LocalDate.now();
String tdate = String.format("%s-%s-%s" ,date.getDayOfMonth(),StaticVariables.monthsNameArr[date.getMonthValue()-1], date.getYear()); 

this.contractStartDate= tdate;
return this.contractStartDate;  
}

public boolean setContractStartDate(String tDate){
    if(!(tDate.split("-").length==3)){
        this.contractStartDate="";
        return false;  
    }
    else{
        this.contractStartDate = tDate;
        return true;
    }
}

public String getContractStartDate(){
    return this.contractStartDate; 
}



public char getTypeOfBussiness(){
  if(this.getContractRefNum() !=""){
   this.typeOfBusiness=this.getContractRefNum().charAt(5); 
  }
  else{
   this.typeOfBusiness=' '; //empty
  }

    return this.typeOfBusiness;  
}

public byte getContractDiscount(){
   byte tDiscount=0; 
   if(this.packageId !=0 && this.dataBundleId !=0 && this.typeOfBusiness !=' ' && this.contractLength !=0){
     if(this.typeOfBusiness=='B' && this.contractLength>=12)
        tDiscount = (byte) (tDiscount + 10);   //10% discount
     else if(this.typeOfBusiness=='C')
       tDiscount = (byte) (tDiscount + 30);  //30% discount
    else if (this.typeOfBusiness=='N' && this.contractLength==24)
      tDiscount = (byte) (tDiscount + 10);  //10% discount
    else if (this.typeOfBusiness=='N' && this.contractLength>=12 && this.contractLength<=18)
      tDiscount = (byte) (tDiscount + 5);  //5% discount
    else 
      tDiscount =0 ;
   } 
   this.discount =tDiscount;
   return tDiscount; 
}


public float getDiscountedPrice(){
    float tPrice=0;
    if(this.packageId !=0 && this.dataBundleId !=0){
      tPrice = StaticVariables.priceTable[packageId-1][dataBundleId-1]; 
      tPrice =tPrice- ((tPrice *this.getContractDiscount())/100);  
    }

    if(this.internationalPlan.equals("Y")){
       //15% price increase
       tPrice = tPrice + ((tPrice * 15)/100);   
    }

    this.monthlyDiscountedPrice = tPrice/100; //pounds 
    return this.monthlyDiscountedPrice;  
}

}


