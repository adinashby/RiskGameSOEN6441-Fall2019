package model;

public class RandomPlayer extends Player implements Strategy {

	public RandomPlayer(String playerName, int[] countryID, MapGeo mapBuild) {
		super(playerName, countryID, mapBuild);
		
	}
	
	@Override
	public void attack(Country attackerCountry, Country attackingCountry, int attackerNumDice, int defendNumDice, MapGeo mapBuild){
		
	}

	@Override
	public boolean reinforce(MapGeo mapBuild, String countryName, int num, boolean finished) {
		int oldArmies = mapBuild.getCountryByName(countryName).getArmies();
		mapBuild.getCountryByName(countryName)
		.setArmies(oldArmies + mapBuild.playerContinentValuesOwnership(this.getPlayerName()) + num);
		// mapBuild.reinforce(getPlayerName(), countryName, num);
		calculateWorldDominationView();
		
		return true;
	}

	@Override
	public void fortify(String fromCountry, String toCountry, int armiesToMove, MapGeo mapBuild) {
		
	}

}
