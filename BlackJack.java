import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import java.lang.Integer;

/**
 * @author CaseyJayne
 *
 */
public class BlackJack extends CardGame{
   private int playerMoney;
   private int betMoney;
   String winner= null;
   private ArrayList<String> playerHand = new ArrayList<>();
   private ArrayList<String> dealerHand = new ArrayList<>();
   private int playerHandValue;
   private int dealerHandValue;
   /**
    * @param money the amount of money player wants to play with
    */
   public BlackJack(int money) {
      super(1, true);
      if (money<1) {
         handleMoneyPositive();
      }
      else {
         setPlayerMoney(money);
      }
      play();
   }
   /**
    * throw custom MoneyIsPositive error and print the message
    */
   private void handleMoneyPositive() {
      try {
         throw new MoneyIsPositive();
         }
         catch(MoneyIsPositive mip) {
            mip.getMessage();
         }
   }
   /**
    * @param dollars pass the betMoney as value to add to player total if the 
    * player wins a round 
    */
   private void addPlayerMoney(int dollars) {
      this.playerMoney += dollars;
   }

   /**
    * @param dollars set the bet for 
    */
   private void setBetMoney(int dollars) {
      if (dollars<1) {
         handleMoneyPositive();
         //lowest buy-in value
         this.betMoney = 1;
      }
      else if(dollars>this.playerMoney) {
         System.out.println("Not enough money for that bet.  All in!");
         this.betMoney = this.playerMoney;
      }
      else {
         this.betMoney = dollars;
      }
   }
   
   /**
    * @param dollars money to add to current bet, only during play
    */
   private void addBetMoney(int dollars) {
      if (dollars<1) {
         handleMoneyPositive();
         //don't change the current bet
      }
      else if ((dollars+this.betMoney)>this.playerMoney) {
            //not enough money to add
            System.out.println("You don't have enough to add that! Adding"
                  + " the whole pot!");
            this.betMoney = this.playerMoney;
      }
      else {  
         this.betMoney += dollars;
      }
   }
   /**
    * @return the betMoney
    */
   public int getBetMoney() {
      return this.betMoney;
   }

   /*
    * @param person: the person to declare as winner
    * @return declare winner and the values of each hand
    */
   private String declareWinner(String person) {
      return person + " wins! \nDealer had: " + this.dealerHandValue + "\nYou "
            + "had: " + this.playerHandValue;
   }
   /*
    * @param handValue pass the value of person who's turn it is to check if 
    * over 21 (and end the round if so).  If not over 21, do nothing.
    */
   private void checkBust(int handValue) {
      if (handValue>21){
         System.out.println("Bust!");
         calculateWinner();
      }
   }
   /*
    * check who wins the round via a bust or greater score.  Also check for 
    * tie.  Add or subtract money from player where applicable
    */
   private void calculateWinner() {
      if ((this.dealerHandValue>21) || ((this.playerHandValue > 
            this.dealerHandValue) && (this.playerHandValue<22))) {
         //If dealer busts or player>dealer without busting
         System.out.println(declareWinner("You"));
         addPlayerMoney(this.betMoney);
      }
      else if ((this.playerHandValue>21) || ((this.playerHandValue < 
               this.dealerHandValue) && (this.dealerHandValue<22))) {
         //if player busts or dealer > player without busting
         System.out.println(declareWinner("Dealer"));
         subtractMoney(this.betMoney);
      }
      else if (this.playerHandValue == this.dealerHandValue) {
         //tied score
         System.out.println("Push! Let's play again.");
      }
   }
   /*
    * @param dollars pass the betMoney as parameter to subtract from player if 
    * the player loses
    */
   private void subtractMoney(int dollars) {
      this.playerMoney -= dollars;
   }
   /*
    * print dealer's hand value.  Automate hit and stay based on the dealer 
    * hand value.  Reprint the hand each time the dealer hits
    */
   private void dealersTurn() {
      System.out.println("Dealer's hand is: " + handToString(this.dealerHand));
      while (getDealerHandValue()<17) {
         //return an additional card on the hand
         ArrayList<String> tempHand = hit(this.dealerHand);
         //make the new hand dealerHand
         this.dealerHand = tempHand;
         System.out.println("Dealer's hand is: " 
               + handToString(this.dealerHand));
         //reset Value calculation
         this.dealerHandValue = 0;
         //calculate new Value
         setDealerHandValue();
         //check if dealer busted
         //if bust, winner will be output
         checkBust(this.dealerHandValue);
      }
      //after over 17, if not busted check for winner
      if (this.dealerHandValue<22) {
         calculateWinner();
      }
   }
   /*
    * @param hand input the dealerhand or playerhand to add a card during gameplay
    *   in BlackJack this is called a "Hit"
    */
   private ArrayList<String> hit(ArrayList<String> hand) {
      String card = drawCard();
      hand.add(card);
      return hand;
   }
   /*
    * @param hand input the playerHand or dealerHand and return all contents as
    * a string.  
    */
   public String handToString(ArrayList<String> hand) {
      String showThis = "";
      for (int i=0; i<hand.size(); i++) {
         showThis += hand.get(i)+ "   ";
      }
      return showThis;
   }
   /*
    * @param hand input the playerHand or dealerHand and return the numeric 
    * value of the score as an integer
    */
   private int calcHandValue(ArrayList<String> hand) {
      int value = 0;
      for (int i=0; i<hand.size(); i++) {
         //take away the suit (unnecessary)
         int space = hand.get(i).indexOf(" ");
         String singleVal = hand.get(i).substring(0, space);
         switch(singleVal) {
            case "10":
            case "K":
            case "Q":
            case "J":
               value += 10;
               break;
            case "A":
               //default set to 11, change to 1 if bust
               value += 11;
               break;
            default: 
               //must be 1-9
               //convert the only unit to character -> then integer
               value += Character.getNumericValue(singleVal.charAt(0));
               //value += Integer.parseInt(singleVal, 0, 1)(singleVal.subSequence(0, 0));
               break;
            }
         //check if broke with an Ace
         if (value > 21 && singleVal =="A") {
            //change value of A to 1
            value -=10;
         }
      }
      return value;
   }
   /*
    * @param hand remove all cards from the input ArrayList
    */
   private void clearHand(ArrayList<String> hand) {
      while(hand.size()>0) {
         hand.remove(0);
      }
   }
   /**
    * @return the playerMoney left to bet with
    */
   public int getPlayerMoney() {
      return this.playerMoney;
   }

   /**
    * @param playerMoney the amount of money the player will use to bet
    */
   private void setPlayerMoney(int playerMoney) {
      this.playerMoney = playerMoney;
   }
   
   /**
    * @return the playerHand get the player hand as ArrayList
    */
   public ArrayList<String> getPlayerHand() {
      return this.playerHand;
   }

   /**
    * @param playerHand the playerHand to set from starting deal by drawing 2 
    * cards from the deck
    */
   private void setPlayerHand() {
      //pull top cards from deck
      this.playerHand.add(drawCard());
      this.playerHand.add(drawCard());
      //remove any null values
      this.playerHand.removeAll(Collections.singleton(null));
      setPlayerHandValue();
   }
   /**
    * @return the dealerHand
    */
   public ArrayList<String> getDealerHand() {
      return this.dealerHand;
   }
   /**
    * deal 2 cards to the dealer to begin play
    */
   private void setDealerHand() {
      this.dealerHand.add(drawCard());
      this.dealerHand.add(drawCard());
      setDealerHandValue();
   }

   /**
    * @return the playerHandValue as an integer
    */
   public int getPlayerHandValue() {
      return this.playerHandValue;
   }

   /**
    * calculate and set the playerHandValue
    */
   private void setPlayerHandValue() {
      this.playerHandValue = calcHandValue(this.playerHand);
   }

   /**
    * @return the dealerHandValue
    */
   public int getDealerHandValue() {
      return this.dealerHandValue;
   }

   /**
    * set the dealerHandValue as integer
    */
   private void setDealerHandValue() {
      this.dealerHandValue = calcHandValue(this.dealerHand);
   }
   private String glimpseDealearHand() {
      return this.dealerHand.get(0);
   }
   private void playersTurn() {
      Scanner in = new Scanner(System.in);
      String input; 
      System.out.println("The buy-in to play is $1.  Please enter 1 or more "
            + "your starting bet: ");
      input = in.nextLine().strip();
      int inputVal = Integer.parseInt(input);
      setBetMoney(inputVal);
      System.out.println("Your hand is: " + handToString(getPlayerHand()));
      System.out.println("Dealer's top card is: " + glimpseDealearHand());
      if (this.playerHandValue==21) {
         
      }
      System.out.println("How much would you like to add to your bet? \n(Enter "
            + "0 or more as an integer value: ");
      input = in.nextLine().strip();
      inputVal = Integer.parseInt(input);
      addBetMoney(inputVal);
      System.out.println("Your hand is worth: " + getPlayerHandValue());
      System.out.println("Would you like another card? Enter 'y' for yes(hit) or "
            + "'n' for no (stay): ");
      input = in.nextLine().strip().toLowerCase();
      while (!input.equals("n") && !input.equals("y")) {
         System.out.println("Please try again. \n press 'y' to "
               + "hit (add a card) or 'n' to stay: ");
         input = in.nextLine().strip().toLowerCase();
      }
      
      checkHit: {
         if (input.equals("y")) {
            do {
               ArrayList<String> tempHand = hit(this.playerHand);
               //make the new hand playerHand
               this.playerHand = tempHand;
               System.out.println("Your hand is: " 
                     + handToString(this.playerHand));
               //reset Value calculation
               this.playerHandValue = 0;
               //calculate new Value
               setPlayerHandValue();
               //Check if player busted
               if (this.playerHandValue>21) {
                //end the round
                  checkBust(this.playerHandValue);
                  break checkHit;
               }
               else {
                  System.out.println("Hit again? Enter 'y'(hit) for yes or 'n' "
                        + "for no (stay): ");
                  input = in.nextLine().strip().toLowerCase();
               }
            } while(input.equals("y")); //break loop after bust or upon stay
         }
         //dealers turn if no bust or choose to stay at start
         dealersTurn();
      }
      //in.close();
   }
   
   public void play() {
      System.out.println("Let's play blackjack!");
      //setup input scanner
      Scanner in = new Scanner(System.in);
      //setup do-while controller
      boolean game = true;
      do {
         shuffle();
         setPlayerHand();
         setDealerHand();
         //start play until round ends!
         playersTurn();
         if (this.playerMoney==0) {
            //no money to play- end the game
            System.out.println("Sorry! No money left-- better luck next time.");
            //exit the do-while loop
            game = false;
         }
         else {
            //offer another game
            System.out.println("You have: " + this.playerMoney + " dollars "
                  + "left.\n Play again? (Press 'q' to quit, any other key to "
                  + "continue: ");
            String input = in.nextLine().strip().toLowerCase();
            if (input.equals("q")) {
              //exit the do-while loop
              game = false;
            }
            else {
               //clear values to continue playing
               clearHand(this.dealerHand);
               clearHand(this.playerHand);
               setPlayerHandValue();
               setDealerHandValue();
            }
         }
      } while(game);
      in.close();
      System.out.println("Good Game!");
   }
}
