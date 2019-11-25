package test;
import java.util.*;
import org.junit.Assert.*;
import org.junit.Before;

import model.*;

import org.junit.Assert;
import org.junit.Test;
/**
 * This testcase class test the calculation of armies given to players
 * @author s_shehna
 *
 */
public class TestArmies {

	ArrayList<String> players = new ArrayList<String>();
	ArrayList<String> strategy = new ArrayList<String>();
	MapGeo mapBuild = MapGeo.getInstance();
	MapConquest mapConquest = new MapConquest();
	
/**
 * This testcase tests the number of armies given to only player in game
 *
 */
	
	
	@Test
	public void testArmiesOwnedByPlayer1() throws Exception {
		mapConquest.readConquest("ameroki");
		players.add("Shehnaz");
		Player[] myPlayers = mapBuild.getPlayers();
		ArrayList<Integer> Country_List_Player1 = myPlayers[0].getCountryIDs();
		Player player = new Player("Shehnaz", Country_List_Player1, mapBuild);
		mapBuild.assigningPlayersToCountries(players, strategy );
		player.calculateNumberOfArmiesEachPlayerGets();
		int armies_owned_by_player1 = player.getNumberOfArmiesEachPlayerGets();
	
		Assert.assertEquals(14, armies_owned_by_player1);	 
}
	/**
	 * This testcase tests the number of armies given to two players in game
	 * 
	 */
//	@Test
//	public void testArmiesOwnedByPlayer2() throws Exception {
//		mapConquest.readConquest("ameroki");
//		players.add("Shehnaz");
//		players.add("Golnoosh");
//		mapBuild.assigningPlayersToCountries(players);
//		mapBuild.calculateNumberOfArmiesEachPlayerGets("Shehnaz");
//		mapBuild.calculateNumberOfArmiesEachPlayerGets("Golnoosh");
//		int armies_owned_by_player1 = mapBuild.getNumberOfArmiesEachPlayerGets();
//		int armies_owned_by_player2 = mapBuild.getNumberOfArmiesEachPlayerGets();
//		
//		Assert.assertEquals(7, armies_owned_by_player1);	
//		Assert.assertEquals(7, armies_owned_by_player2);	
//	}
//	/**
//	 * This testcase tests the calculation for number of armies when there are 3 players
//	 * 
//	 */
//	@Test
//	public void testArmiesOwnedByPlayer3() throws Exception {
//		mapConquest.readConquest("ameroki");
//		players.add("Shehnaz");
//		players.add("Golnoosh");
//		players.add("Aiden");
//		mapBuild.assigningPlayersToCountries(players);
//		mapBuild.calculateNumberOfArmiesEachPlayerGets("Shehnaz");
//		int armies_owned_by_player1 = mapBuild.getNumberOfArmiesEachPlayerGets();
//		mapBuild.calculateNumberOfArmiesEachPlayerGets("Golnoosh");
//		int armies_owned_by_player2 = mapBuild.getNumberOfArmiesEachPlayerGets();
//		mapBuild.calculateNumberOfArmiesEachPlayerGets("Aiden");
//		int armies_owned_by_player3 = mapBuild.getNumberOfArmiesEachPlayerGets();
//		Assert.assertEquals(4, armies_owned_by_player1);	
//		Assert.assertEquals(4, armies_owned_by_player2);	
//		Assert.assertEquals(4, armies_owned_by_player3);	
//	}
//	/**
//	 * This testcase tests the calculation for number of armies when there are 4 players
//	 * 
//	 */
//	@Test
//	public void testArmiesOwnedByPlayer4() throws Exception {
//		mapConquest.readConquest("ameroki");
//		players.add("Shehnaz");
//		players.add("Golnoosh");
//		players.add("Aiden");
//		players.add("Babita");
//		mapBuild.assigningPlayersToCountries(players);
//		mapBuild.calculateNumberOfArmiesEachPlayerGets("Shehnaz");
//		int armies_owned_by_player1 = mapBuild.getNumberOfArmiesEachPlayerGets();
//		mapBuild.calculateNumberOfArmiesEachPlayerGets("Golnoosh");
//		int armies_owned_by_player2 = mapBuild.getNumberOfArmiesEachPlayerGets();
//		mapBuild.calculateNumberOfArmiesEachPlayerGets("Aiden");
//		int armies_owned_by_player3 = mapBuild.getNumberOfArmiesEachPlayerGets();
//		mapBuild.calculateNumberOfArmiesEachPlayerGets("Babita");
//		int armies_owned_by_player4 = mapBuild.getNumberOfArmiesEachPlayerGets();
//		Assert.assertEquals(3, armies_owned_by_player1);	
//		Assert.assertEquals(3, armies_owned_by_player2);	
//		Assert.assertEquals(3, armies_owned_by_player3);
//		Assert.assertEquals(3, armies_owned_by_player4);
//	}
}
