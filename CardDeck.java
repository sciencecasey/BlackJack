import java.util.ArrayList;
import java.util.Collections;

/**
 * @author CaseyJayne
 *creates a deck of 52 cards.  Has shuffle and getDeck public methods
 */
public class CardDeck{
   
   /**
    * organizes an array of unshuffled cards in a deck of 52
    */
   private final ArrayList<String> DECK = new ArrayList<>();
   private String TOD;
   
   CardDeck(){
      CardVals cards = new CardVals();
         for (String k: cards.values) {
            for(String j: cards.suits) {
               //System.out.println(k+j);
               this.DECK.add(k + " " + j);
         }
      }
      setTOD();
   }
   /**
    * @return get the deck of 52 cards
    */
   public ArrayList<String> getDeck(){
      return this.DECK;
   }
   /*
    * pull a card from deck and return it as String.  Results in reducing size
    * of deck and changing TOD
    */
   public String drawCard() {
      //resset the TOD 
      String myCard = this.TOD;
      //remove card from the deck
      this.DECK.remove(this.DECK.size()-1);
      setTOD();
      return myCard;
   }
   /*
    * set TOD to the String located in the last element
    */
   private void setTOD() {
    //set TOD
      this.TOD = this.DECK.get((this.DECK.size()-1));
   }
   /**
    *uses Collections.shuffle to shuffle deck 
    */
   public void shuffle() {
      Collections.shuffle(this.DECK);
   }
   /**
    * keep cardvalues in a private nested class to be instantiated only when
    * the cardDeck class is instantiated
    * @author CaseyJayne
    *
    */
   private class CardVals{
      private String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", 
            "10", "J", "Q", "K"};
      private String[] suits = {"hearts", "clubs", "spades", "diamonds"};
   }
   
   
}