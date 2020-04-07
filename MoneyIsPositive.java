
public class MoneyIsPositive extends Exception {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   public String getMessage(){
      System.out.println("Invalid input: Money should be a positive value.");
      return null;
   }
}
