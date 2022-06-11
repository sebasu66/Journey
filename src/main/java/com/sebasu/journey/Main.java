package com.sebasu.journey;

import static com.sebasu.journey.gameManager.CommandInterpreter.interpretExpression;
import static com.sebasu.journey.gameManager.GAdmin.*;
/**
 *
static import feature allows to access the static members of a class without the class qualification.
 **/

import com.sebasu.journey.components.Player;
import com.sebasu.journey.gameManager.Game;
import com.sebasu.journey.gameManager.GameStepBuilder;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        String id =getInstance().
                setupNewGame()
                .howManyPlayers(GameStepBuilder.playerCountEnum.TWO)
                .whichMapSize(GameStepBuilder.mapSizeEnum.SMALL)
                .setReadyAndReturnID();
        System.out.println(id);


        try {
            getInstance().getGame(id).addPlayer(new Player("Sebas"));
        } catch (Game.GameRequirementException e) {
            e.printStackTrace();
        }

        System.out.println(getInstance().getGame(id).getPlayers().get(0).getStats());
        System.out.println("welcome player, would ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while(!command.equals("exit")) {
            System.out.println(interpretExpression(command));
            command = scanner.nextLine();
        }


    }


}
