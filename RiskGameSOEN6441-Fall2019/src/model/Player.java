package model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.MapView;

/**
 * 
 * This is Player class, in which we define our attributes of a Player.
 * 
 * @author f_yazdan
 * @author s_shehna
 * @author AdinAshby
 */
public class Player implements Subject {

	/**
	 * PlayerName is name of the player corresponding to the ID.
	 */
	private String playerName;

	/**
	 * List of all countryIDs that the player owns.
	 */
	private int[] countryIDs;

	private double percentageControlled = 0.00;

	private ArrayList<String> continentsControlled = new ArrayList<String>();
	private int totalNumberOfArmies = 0;
	private ArrayList<Card> cards;
	private int playerCountForCard;
	private int numberOfArmiesEachPlayerGets;
	private boolean allowToGetCard;

	private int counterForPhases;

	private int temporaryArmies;

	private ArrayList<Observer> observersForPhases = new ArrayList<Observer>();
	private ArrayList<Observer> observersForWorldDomination = new ArrayList<Observer>();

	private MapGeo mapGeo;

	private Strategy strategy;

	/**
	 * private scanner
	 */
	private Scanner scanner = new Scanner(System.in);
	/**
	 * private input
	 */
	private String input;
	/**
	 * private regex
	 */
	private String regex;
	/**
	 * private pattern
	 */
	private Pattern pattern;
	/**
	 * private matcher
	 */
	private Matcher matcher;
	/**
	 * private reinforceRequestingMessage
	 */
	private String reinforceRequestingMessage = "Now, it's time to reinforce!\n";
	/**
	 * private fortifyRequestingMessage
	 */
	private String fortifyRequestingMessage = "Let's fortify! or type in \"fortify none\" if you don't want to.\n";

	private String attackRequestingMessage = "Let's Attack\n";

	/**
	 * This is Player constructor method for initializing PlayerName and countryId
	 * 
	 * @param playerID   A unique ID for each player.
	 * @param playerName Corresponding player name.
	 * @param countryID  List of all countries (IDs) that the player owns.
	 */
	public Player(String playerName, int[] countryID, MapGeo mapGeo) {
		this.playerName = playerName;
		this.countryIDs = countryID;
		this.mapGeo = mapGeo;
		cards = new ArrayList<Card>();
		playerCountForCard = 0;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public void addOneToCardCounter() {
		playerCountForCard++;
	}

	/**
	 * This is Getter method for PlayerName.
	 * 
	 * @return playerName Returns playerName as a String.
	 */
	public String getPlayerName() {
		return playerName;
	}

	public int getplayerCountForCard() {

		return playerCountForCard;

	}

	public String getCardNames() {
		String cardsList = "Cards: -";
		for (Card c : cards) {
			cardsList = c.getCardType();
		}
		return cardsList;

	}

	public ArrayList<Card> getCards() {

		return cards;

	}

	public void setCards(ArrayList<Card> cardList) {

		for (int i = 0; i < cardList.size(); i++) {

			cardList.remove(i);

		}

		for (Card cardItem : cardList) {

			this.cards.add(cardItem);

		}

	}

	public void addCard(Card cardToAdd) {

		this.cards.add(cardToAdd);

	}

	public boolean isMoreThanFive() {

		if (cards.size() >= 5)

			return true;

		else

			return false;

	}

	public boolean getAllowingCardStatus() {

		return this.allowToGetCard;

	}

	public void setAllowingStatus(boolean status) {

		this.allowToGetCard = status;

	}

	/**
	 * This is Getter Method for CountryIDs.
	 * 
	 * @return getCountryID Returns CountryIDs as an array of integers.
	 */
	public int[] getCountryIDs() {
		return countryIDs;
	}

	/**
	 * This is Setter Method for CountryIDs.
	 * 
	 * @param countriesIDs Sets a list of countriesIDs to the corresponding field.
	 */
	public void setCountryIDs(int[] countriesIDs) {
		this.countryIDs = countriesIDs;
	}

	public double getPercentageControlled() {
		return percentageControlled;
	}

	public ArrayList<String> getContinentsControlled() {
		return continentsControlled;
	}

	public void setContinentsControlled(ArrayList<String> continentsControlled) {
		this.continentsControlled = continentsControlled;
	}

	public int getTotalNumberOfArmies() {
		return totalNumberOfArmies;
	}

	public boolean isAttackValid(MapGeo mapGeo, int attackerNumDice, Country attackerCountry,
			Country attackingCountry, boolean enablePrint) {
		boolean isValid = true;

		if (attackerCountry == null || attackingCountry == null) {
			System.out.println("Enter Attacker and Attacking Country");
			isValid = false;
		} else {

			if (attackerNumDice > attackingCountry.getArmies()) {
				if (enablePrint)
					System.out.println("attacking dice (" + attackerNumDice
							+ ") should not be more than the number of armies contained in the attacking country");
				isValid = false;
			} else if (attackerNumDice > 3) {
				if (enablePrint)
					System.out.println("attacking dice (" + attackerNumDice + ") should not be more than 3");
				isValid = false;
			} else if (!mapGeo.isAdjacentCountry(attackerCountry.getCountryId(), attackingCountry.getCountryId())) {
				if (enablePrint)
					System.out.println("Countries are not adjacent");
				isValid = false;

				/// Country should be owned and attacking country for opponent
			}
		}
		return isValid;
	}

	public boolean isAttackPossible(MapGeo mapGeo) {
		boolean isAttackPossible = false;
		for (int countyId : countryIDs) {
			// Country country=mapGeo.getCountryById(countyId);
			// System.out.println("Checking " + countyId);
			ArrayList<Integer> CountryAdjList = mapGeo.getCountryAdjacency().getVertexAdjacency(countyId);
			// System.out.println(CountryAdjList); //Arrays.toString(

			for (int adjCountry : CountryAdjList) {
				if (!contains(countryIDs, adjCountry)) {
					for (int i = 1; i < 4; i++) {
						if (isAttackValid(mapGeo, i, mapGeo.getCountryById(countyId),
								mapGeo.getCountryById(adjCountry), false)) {
							isAttackPossible = true;
						}
					}
				}
			}

		}

		return isAttackPossible;
	}

	public static boolean contains(final int[] array, final int v) {

		boolean result = false;

		for (int i : array) {
			if (i == v) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * This method check whether is defend possible
	 * 
	 * @return isDefendPossible
	 */

	public boolean isDefendPossible(MapGeo mapGeo, int defendCountry, int numDice) {

		boolean isDefendPossible = true;
		if (!contains(this.getCountryIDs(), defendCountry)) {
			isDefendPossible = false;
		}
		if (mapGeo.getCountryById(defendCountry).getArmies() < numDice) {
			isDefendPossible = false;
		}

		return isDefendPossible;
	}

	public int getCounterForPhases() {
		return counterForPhases;
	}

	public void setCounterForPhases(int counterForPhases) {
		this.counterForPhases = counterForPhases;
		notifyObserverForPhases();
	}

	@Override
	public void registerPhaseObserver(Observer addObserver) {
		observersForPhases.add(addObserver);
	}

	@Override
	public void registerWorldDominationObserver(Observer addObserver) {
		observersForWorldDomination.add(addObserver);
	}

	@Override
	public void unregisterPhaseObserver(Observer removeObserver) {
		observersForPhases.remove(removeObserver);
	}

	@Override
	public void unregisterWorldDomination(Observer removeObserver) {
		observersForWorldDomination.remove(removeObserver);
	}

	@Override
	public void notifyObserverForWorldDomination() {
		// System.out.println("Ob No.="+observersForWorldDomination.size());
		for (Observer observer : observersForWorldDomination) {
			observer.update(percentageControlled, totalNumberOfArmies, continentsControlled, mapGeo);
		}
	}

	@Override
	public void notifyObserverForPhases() {
		for (Observer observer : observersForPhases) {
			observer.update(counterForPhases, playerName);
		}
	}

	public ArrayList<Observer> getObserversForPhases() {
		return observersForPhases;
	}

	public ArrayList<Observer> getObserversForWorldDomination() {
		return observersForWorldDomination;
	}

	/*
	 * public void calculateTotalNumberOfArmies() { for(Integer each : countryIDs) {
	 * totalNumberOfArmies +=
	 * MapGeo.getInstance().getCountryById(each).getArmies(); }
	 * notifyObserverForWorldDomination(); }
	 * 
	 * public void calculateContinentControlled() {
	 * MapGeo.getInstance().continentsOwnedByPlayer(playerName);
	 * notifyObserverForWorldDomination(); }
	 * 
	 * public void calculatePercentageControlled() { percentageControlled =
	 * getCountryIDs().length * 100 /
	 * MapGeo.getInstance().getAllCountries().size();
	 * notifyObserverForWorldDomination(); }
	 */

	public void calculateWorldDominationView() {
		totalNumberOfArmies = 0;

		for (Integer each : countryIDs) {
			totalNumberOfArmies += mapGeo.getCountryById(each).getArmies();
		}

		mapGeo.continentsOwnedByPlayer(playerName);
		percentageControlled = getCountryIDs().length * 100 / mapGeo.getAllCountries().size();

		notifyObserverForWorldDomination();
	}

	public int getNumberOfArmiesEachPlayerGets() {
		return numberOfArmiesEachPlayerGets;
	}

	/**
	 * This method is for calculate Number Of Armies Each Player Gets
	 * 
	 * @param playerName
	 */

	public void calculateNumberOfArmiesEachPlayerGets() {
		numberOfArmiesEachPlayerGets = (getCountryIDs().length / 3 > 3) ? getCountryIDs().length / 3 : 3;
	}

	public boolean reinforceIsValid(MapGeo mapGeo, String countryName, int armiesAdded) {
		calculateNumberOfArmiesEachPlayerGets();

		if (armiesAdded < 0 || armiesAdded > numberOfArmiesEachPlayerGets) {
			System.out.println("Armies Added (" + armiesAdded
					+ ") is less than 0 or Armies added are more than player armies " + numberOfArmiesEachPlayerGets);
			return false;
		}

		int[] playerCountries = getCountryIDs();

		for (int countryID : playerCountries) {
			if (mapGeo.getCountryByName(countryName) == mapGeo.getCountryById(countryID)) {
				return true;
			}
		}

		return false;
	}

	public boolean reinforce(MapGeo mapGeo, String countryName, int num, boolean finished) {
		return this.strategy.reinforce(mapGeo, countryName, num, finished);
	}

	public boolean reinforceCommand(MapGeo mapGeo, MapView mapView) {
		calculateNumberOfArmiesEachPlayerGets();

		boolean finished = false;
		boolean isValidCommand = false;

		if(this.strategy instanceof HumanPlayer) {
			String addText = "";
			temporaryArmies = numberOfArmiesEachPlayerGets;

			while (!finished) {// && debug == false

<<<<<<< HEAD
			// showmap
			regex = "showmap";
			setPattern(regex);
			setMatcher(input);
			if (getMatcher().find()) {
				isValidCommand = true;
				mapView.showMap(mapGeo);
			}
=======
				isValidCommand = false;
>>>>>>> branch 'master' of https://github.com/AdinAshby/RiskGameSOEN6441-Fall2019.git

<<<<<<< HEAD
			// reinforce
			regex = "(?<=reinforce)(.*)";
			setPattern(regex);
			setMatcher(input);
			if (matcher.find()) {
				addText = matcher.group(1);

				regex = "(([\\w*\\_\\-]*) (\\d*))+";
				setPattern(regex);
				setMatcher(addText);
				if (matcher.find()) {
					String countryName = matcher.group(2);
					int num;
					try {
						num = Integer.parseInt(matcher.group(3));

						finished = reinforce(mapGeo, countryName, num, finished);

						isValidCommand = true;

					} catch (NumberFormatException e) {
						System.out.println("Enter number of armies");
					}

=======
				System.out.println("Player " + getPlayerName() + ":");
				if (temporaryArmies != 0) {
					System.out.println("You have -" + temporaryArmies + "- armies left for reinforcement.");
>>>>>>> branch 'master' of https://github.com/AdinAshby/RiskGameSOEN6441-Fall2019.git
				}
				// Card card=new Card();
				// player.addCard(card);
				System.out.println("You have following cards: ");
				System.out.println(getCardNames());
				readInput();

				// showmap
				regex = "showmap";
				setPattern(regex);
<<<<<<< HEAD
				setMatcher(addText);
				if (matcher.find()) {

					int num1 = Integer.parseInt(matcher.group(1));
					int num2 = Integer.parseInt(matcher.group(2));
					int num3 = Integer.parseInt(matcher.group(3));

					if (mapGeo.exchangeCardsIsValid(this, num1, num2, num3) == true) {

						int cardarmies = mapGeo.exchangeCards(this, num1, num2, num3);
						finished = true;
					} else {
						System.out.println("exchangecards is not valid");
					}
=======
				setMatcher(input);
				if (getMatcher().find()) {
					isValidCommand = true;
					mapView.showMap(mapBuild);
>>>>>>> branch 'master' of https://github.com/AdinAshby/RiskGameSOEN6441-Fall2019.git
				}

				// reinforce
				regex = "(?<=reinforce)(.*)";
				setPattern(regex);
				setMatcher(input);
				if (matcher.find()) {
					addText = matcher.group(1);

					regex = "(([\\w*\\_\\-]*) (\\d*))+";
					setPattern(regex);
					setMatcher(addText);
					if (matcher.find()) {
						String countryName = matcher.group(2);
						int num;
						try {
							num = Integer.parseInt(matcher.group(3));

							finished = reinforce(mapBuild, countryName, num, finished);

							isValidCommand = true;

						} catch (NumberFormatException e) {
							System.out.println("Enter number of armies");
						}

					}
				} // Match Find Reinforce
				////__________________________________________///////
				//__________________________________________///////
				//__________________________________________///////
				regex = "(?<=exchangecards)(.*)";
				setPattern(regex);
				setMatcher(input);
				if (matcher.find()) {
					addText = matcher.group(1);
					regex = "(\\d+) (\\d+) (\\d+)";
					setPattern(regex);
					setMatcher(addText);
					if (matcher.find()) {

						int num1 = Integer.parseInt(matcher.group(1));
						int num2 = Integer.parseInt(matcher.group(2));
						int num3 = Integer.parseInt(matcher.group(3));

						if (mapBuild.exchangeCardsIsValid(this, num1, num2, num3) == true) {

							int cardarmies = mapBuild.exchangeCards(this, num1, num2, num3);
							finished = true;
						} else {
							System.out.println("exchangecards is not valid");
						}
					}

					isValidCommand = true;
				}
				if (!isValidCommand) {
					System.out.println("Correct command not found");
				}
			} // while reinforce
		} 

		if(this.strategy instanceof AggressivePlayer) {

			int[] playerCountries = getCountryIDs();
			int maxArmies = 0;
			String countryName = "";

			for (int countryID : playerCountries) {
				if(mapBuild.getCountryById(countryID).getArmies() > maxArmies) {
					maxArmies = mapBuild.getCountryById(countryID).getArmies();
					countryName = mapBuild.getCountryById(countryID).getCountryName();
				}
			}

			finished = reinforce(mapBuild, countryName, numberOfArmiesEachPlayerGets, true);
		}

		return isValidCommand;
	}

	public void attackMove(Country attackerCountry, Country attackingCountry, int numAttack) {
		System.out.println("attackmove : " + numAttack);
		attackerCountry.setArmies(
				attackerCountry.getArmies() - numAttack);
		attackingCountry.setArmies(numAttack);
		calculateWorldDominationView();

	}

	public void attackMoveCommand(Country attackerCountry, Country attackingCountry) {
		boolean isValidCommand = false;
		// attackmove num
		System.out.println("Ready for attackmove");
		readInput();
		regex = "(?<=attackmove) (\\d+)";
		setPattern(regex);
		setMatcher(input);

		if (matcher.find()) {
			try {
				int numAttack = Integer.parseInt(matcher.group(1));
				if (numAttack < attackerCountry.getArmies() - 1) {
					System.out.println(numAttack
							+ " can not move because you have only : "
							+ attackerCountry.getArmies()
							+ " armies and one should left there");
				} else {
					isValidCommand = true;
					attackMove(attackerCountry, attackingCountry, numAttack);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("NumDice should be integer");
			}
			isValidCommand = true;

		} else {
			isValidCommand = false;
			System.out.println("Please follow the correct command rules");
		}


	}

	public void attack(Country attackerCountry, Country attackingCountry, int attackerNumDice, int defendNumDice, MapGeo mapGeo) {
		this.strategy.attack(attackerCountry, attackingCountry, attackerNumDice, defendNumDice, mapGeo);
	}

	public boolean attackCommand(MapGeo mapGeo, MapView mapView) {
		boolean finished = false;
		boolean isValidCommand = false;
		String addText = "";
		/*
		 ************************************************
		 ************* Attack **********************
		 ************************************************
		 ************************************************
		 */
		// attack
		// System.out.println(attackRequestingMessage);

		finished = false;
		while (!finished) {// && debug == false
			System.out.println(getPlayerName() + " you may attack or finish your turn");
			isValidCommand = false;
			// Check there is any available attack
			if (isAttackPossible(mapGeo) == false) {
				System.out.println("Attack is not possible.");
				isValidCommand = true;
				finished = true;
			}
			readInput();

			// showmap
			regex = "showmap";
			setPattern(regex);
			setMatcher(input);
			if (getMatcher().find()) {
				isValidCommand = true;
				mapView.showMap(mapGeo);
			}

			regex = "(?<=attack)(.*)";
			setPattern(regex);
			setMatcher(input);
			if (matcher.find()) {
				addText = matcher.group(1);
				regex = "(([\\w*\\_\\-]*) ([\\w*\\_\\-]*) ((\\d+)|(-allout)))|(-noattack)";// )+
				setPattern(regex);
				setMatcher(addText);
				if (matcher.find()) {

					// for (int j = 0; j <= matcher.groupCount(); j++) {
					// System.out.println("------------------------------------");
					// System.out.println("Group " + j + ": ***" + matcher.group(j)+"***");
					//
					// }

					if (matcher.group(7) != null && matcher.group(7).equals("-noattack")) {
						System.out.println("No attack selected");
						isValidCommand = true;
						finished = true;

					} else {
						int attackerNumDice = 0;
						String attackerCountryName = matcher.group(2);
						String attackingCountryName = matcher.group(3);

						if (matcher.group(6) != null && matcher.group(6).equals("-allout")) {
							System.out.println("Allout selected");
							attackerNumDice = 3;
						} else {
							attackerNumDice = Integer.parseInt(matcher.group(4));
						}

						// Country attacker=;
						Country attackerCountry = mapGeo.getCountryByName(attackerCountryName);
						Country attackingCountry = mapGeo.getCountryByName(attackingCountryName);
						// System.out.println("Armies in Attacking " + attackingCountryName + " is "+
						// attackingCountry.getArmies());

						System.out.println("Attack from " + attackerCountryName + " To " + attackingCountryName + " by "
								+ attackerNumDice + " dice");

						if (isAttackValid(mapGeo, attackerNumDice, attackerCountry, attackingCountry, true) == true) {



							/**** Start Defend *******/

							isValidCommand = true;
							// Show name of player of defend country and ask him/her to roll dice by DEFEND

							boolean finishedDefend = false;
							while (!finishedDefend) {
								// defend numdice
								readInput();
								regex = "(?<=defend) (\\d+)";
								setPattern(regex);
								setMatcher(input);

								if (matcher.find()) {
									try {
										int defendNumDice = Integer.parseInt(matcher.group(1));
										attack(attackerCountry, attackingCountry, attackerNumDice, defendNumDice,  mapGeo );





										// if(numDice>attackingCountry.getArmies() || numDice>3) {
										// System.out.println("defending dice should not be more than the number of
										// armies contained in the attacking country or more than 3");
										// isValidCommand = false;
										// }else {
										// isValidCommand = true;
										// System.out.println("Defend by: " + numDice);
										// }
										isValidCommand = true;
									} catch (NumberFormatException e) {
										// TODO Auto-generated catch block
										System.out.println("NumDice should be integer");
									}
									finishedDefend = true;

								} else {
									isValidCommand = false;
									System.out.println("Please enter the defence command");
								}
							} // while(!finishedDefend) {

						}

						/*
						 * String allOut = matcher.group(5); String noAttack = matcher.group(6);
						 */
					}
				} else {
					isValidCommand = false;
				}

			}

			if (!isValidCommand) {
				System.out.println("Correct command not found");
			}

			/// end of the attack phase

		} // End of While(!finished)
		return isValidCommand;
	}

	/**
	 * This method is for checking whether the fortify is valid
	 * 
	 * @param player
	 * @param fromCountry
	 * @param toCountry
	 * @param num
	 * @return false
	 */
	public boolean fortifyIsValid(String fromCountry, String toCountry, int num, MapGeo mapGeo) {
		boolean fromCountryCheck = false;
		boolean toCountryCheck = false;

		if (num < 0 || num > mapGeo.getCountryByName(fromCountry).getArmies())
			return false;

		for (int countryID : getCountryIDs()) {
			if (mapGeo.getCountryByName(fromCountry) == mapGeo.getCountryById(countryID)) {
				fromCountryCheck = true;
			}
		}

		for (int countryID : getCountryIDs()) {
			if (mapGeo.getCountryByName(toCountry) == mapGeo.getCountryById(countryID)) {
				toCountryCheck = true;
			}
		}

		if (fromCountryCheck && toCountryCheck)
			return true;

		return false;
	}

	public void fortify(String fromCountry, String toCountry, int armiesToMove,  MapGeo mapGeo) {
		this.strategy.fortify(fromCountry, toCountry, armiesToMove, mapGeo);
	}

	/**
	 * 
	 * @param mapGeo
	 * @param mapView
	 * @return
	 */

	public boolean fortifyCommand(MapGeo mapGeo, MapView mapView) {
		System.out.println(fortifyRequestingMessage);

		boolean finished = false;
		boolean isValidCommand = false;
		String addText = "";

		while (!finished) {

			isValidCommand = false;

			System.out.println("Player " + getPlayerName() + ":");
			readInput();

			// showmap
			regex = "showmap";
			setPattern(regex);
			setMatcher(input);
			if (getMatcher().find()) {
				isValidCommand = true;
				mapView.showMap(mapGeo);
			}

			// fortify -none
			regex = "fortify -none";

			if (input.equalsIgnoreCase(regex)) {
				isValidCommand = true;
				finished = true;
			}

			// fortify
			regex = "(?<=fortify)(.*)";
			setPattern(regex);
			setMatcher(input);
			if (matcher.find()) {
				addText = matcher.group(1);
				regex = "(([\\w*\\_\\-]*) ([\\w*\\_\\-]*) (\\d+))+";
				setPattern(regex);
				setMatcher(addText);
				if (matcher.find()) {

					String fromCountry = matcher.group(2);
					String toCountry = matcher.group(3);
					int num = Integer.parseInt(matcher.group(4));
					fortify(fromCountry, toCountry, num, mapGeo);
					finished = true;

				} 

				isValidCommand = true;
			}
		}

		return isValidCommand;
	}

	public int getTemporaryArmies() {
		return temporaryArmies;
	}

	public void setTemporaryArmies(int temporaryArmies) {
		this.temporaryArmies = temporaryArmies;
	}

	/**
	 * This method is for reading the input from console
	 */
	public void readInput() {
		this.input = scanner.nextLine();
	}

	/**
	 * This method is for setting the pattern
	 * 
	 * @param regex
	 */
	public void setPattern(String regex) {
		this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	}

	/**
	 * This method is for setting the matcher
	 * 
	 * @param input
	 */

	public void setMatcher(String input) {
		this.matcher = pattern.matcher(input);
	}

	/**
	 * This method is for fetching the matcher
	 * 
	 * @return Matcher
	 */
	public Matcher getMatcher() {
		return this.matcher;
	}

}