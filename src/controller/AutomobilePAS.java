/**
 * @Class: AutomobilePAS
 * @Description: Main class for the Capstone Project
 * @author CalvinLingat
 * @Created:8/15/2022 
 */
package controller;

import java.sql.Connection;
import java.util.Scanner;

import model.*;
import view.Menus;

public class AutomobilePAS {

	public static void main(String[] args) {
		DatabaseConnection dbCon = new DatabaseConnection();
		Menus menu = new Menus();
		Scanner sc = new Scanner(System.in);
		AutomobilePASService pasService = new AutomobilePASService();
		Connection con = dbCon.connectServer();
		Validation validate = new Validation();

		String input;
		boolean isTrue = true;

		/*
		 * Readies the database if no db is created upon startup. normally this is not
		 * needed since a database is established
		 */
		dbCon.createDatabase(con);

		// create db tables when not existing.
		dbCon.createTables(con);

		System.out.println("Welcome.");
		while (isTrue) {
			menu.displayMainMenu();
			do {
				menu.getInput();
				input = sc.next();
			} while (validate.isInvalidIntInput(input)); // validates input

			// handles what will happen based on the user's input.
			switch (input) {
			case "1":
				pasService.processFirstMainMenuOption(con);
				break;
			case "2":
				pasService.processSecondMainMenuOption(con);
				break;
			case "3":
				pasService.processThirdMainMenuOption(con);
				break;
			case "4":
				pasService.processFourthMainMenuOption(con);
				break;
			case "5":
				pasService.processFifthMainMenuOption(con);
				break;
			case "6":
				pasService.processSixthMainMenuOption(con);
				break;
			case "7":
				pasService.processSeventhMainMenuOption(con);
				break;
			case "8":
				System.out.println("Goodbye");
				isTrue = false;
				break;
			default:
				break;
			}
		}
		sc.close();

	}
}
