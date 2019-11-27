package model;

import java.util.ArrayList;
import java.util.Arrays;

public class HumanPlayer extends Player implements Strategy {

	public HumanPlayer(String playerName, ArrayList<Integer> countriesIDs, MapGeo mapGeo) {
		
		super(playerName, countriesIDs, mapGeo);
	}
	
	@Override
	public void attack(Country attackerCountry, Country attackingCountry, int attackerNumDice, int defendNumDice, int attackAllout){
		Dice attackDice = new Dice(attackerNumDice);
		int[] attackDiceArray = attackDice.getDiceArray();
		//attackDice.showDice();

		Dice defendDice = new Dice(defendNumDice);
		int[] defendDiceArray = defendDice.getDiceArray();
		//defendDice.showDice();

		boolean[] winner = attackDice.isWinner(defendDice);
		

		for (int i = 0; i < winner.length; i++) {
			int diceNo = i + 1;
			if (winner[i]) {
				System.out.println("Attacker win dice " + diceNo );
				attackingCountry.setArmies(attackingCountry.getArmies() - 1);
				calculateWorldDominationView();
				if (attackingCountry.getArmies() == 0) {
					System.out.println("\n*****"+
							attackingCountry.getCountryName() + " is conquered"+"*****\n");
					mapGeo.setContinentNamesOfPlayer(this);
					Card card = new Card();
					addCard(card);
					System.out.println("You have the following cards now :");
					System.out.println(getCardNames());
					attackingCountry.setPlayer(attackerCountry.getPlayerName());
					addCountryIdToPlayer(attackingCountry.getCountryId());
					int NoOfContinentsControlled = getContinentsControlled().size();
					if (NoOfContinentsControlled == mapGeo
							.getNoOfContinentsControlled()) {
						System.out.println(attackerCountry.getPlayerName()
								+ " is winner. Game over!");
						System.exit(0);
					}
					mapGeo.showMap();
					
					attackMoveCommand(attackerCountry, attackingCountry, attackAllout);

				}

			} else {
				System.out.println("Defender win dice " + diceNo );
				attackerCountry.setArmies(attackerCountry.getArmies() - 1);
				calculateWorldDominationView();
				if (attackerCountry.getArmies() == 0) {
					System.out.println(
							attackerCountry.getCountryName() + " is conquered");
					attackerCountry.setPlayer(attackingCountry.getPlayerName());
				}
			}

		}
	}



	@Override
	public boolean reinforce(MapGeo mapGeo, String countryName, int num, boolean finished) {

		if (reinforceIsValid(mapGeo, countryName, num) == true) {
			int oldArmies = mapGeo.getCountryByName(countryName).getArmies();
			mapGeo.getCountryByName(countryName)
			.setArmies(oldArmies + mapGeo.playerContinentValuesOwnership(super.getPlayerName()) + num);
			// mapGeo.reinforce(getPlayerName(), countryName, num);
			calculateWorldDominationView();
			//temporaryArmies -= num;
			super.setTemporaryArmies(super.getTemporaryArmies() - num);

			if (super.getTemporaryArmies() <= 0) {
				finished = true;
			}

		} else {
			System.out.println("Reinforce is not valid");
		}
		return finished;
		
	}

	@Override
	public void fortify(String fromCountry, String toCountry, int armiesToMove, MapGeo mapGeo) {
		if (fortifyIsValid(fromCountry, toCountry, armiesToMove, mapGeo) == true) {
			int oldArmiesFromCountry = mapGeo.getCountryByName(fromCountry).getArmies();
			mapGeo.getCountryByName(fromCountry).setArmies(oldArmiesFromCountry - armiesToMove);

			int oldArmiesToCountry = mapGeo.getCountryByName(toCountry).getArmies();
			mapGeo.getCountryByName(toCountry).setArmies(oldArmiesToCountry + armiesToMove);
			calculateWorldDominationView();

		}
	}

}
