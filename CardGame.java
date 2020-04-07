

/**
 * @author CaseyJayne
 * uses a deck of cards with or without jokers.  Also set number of players
 * and dealer options through constructors.  
 */
public class CardGame extends CardDeck{
   private int players;
   private int dealer;
   
   /**
    * @param playerCount number of players for a game without a dealer
    * position, normal deck, no dealer
    * 
    */
   public CardGame(int playerCount) {
      this.players= playerCount;
      setDealer(false);
   }
   /**
    * @param playerCount number of players
    * @param dealer true if dealer (default: false)
    */
   public CardGame(int playerCount, boolean dealer) {
      this.players= playerCount;
      setDealer(dealer);
      setJokers(0);
   }
   /**
    * @param playerCount
    * @param dealer true if dealer exists (default: false)
    * @param jokers number of jokers to use
    */
   public CardGame(int playerCount, boolean dealer, int jokers) {
      this.players= playerCount;
      setDealer(dealer);
      setJokers(jokers);
   }
   /**
    * @param playerCount number of players
    * @param jokers number of jokers to use
    */
   public CardGame(int playerCount, int jokers) {
      this.players= playerCount;
      setDealer(false);
      setJokers(jokers);
   }
   /**
    * default: one player and normal deck
    */
   public CardGame() {
      this.players = 1; //assume if playing game, at least one person
      setDealer(false);
      setJokers(0);
   }
   private void setJokers(int jokeCount) {
      for (;jokeCount>0;jokeCount--) {
         //add a card to Deck stack for number of Jokers Set
         getDeck().add("Joker");
      }
   }
   private void setDealer(boolean dealer) {
      if (dealer = true) {
         this.dealer = 1;
      }
      else {
         this.dealer = 0;
      }
   }
   /**
    * @return number of dealers (1 or 0)
    */
   public int getDealer() {
      return this.dealer;
   }
   /**
    * @return number of Players
    */
   public int getPlayers() {
      return this.players;
   }
   
   
}
