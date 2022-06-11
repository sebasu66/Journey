package com.sebasu.journey.gameManager;

import com.sebasu.journey.components.rules.GameRules;
import com.sebasu.journey.components.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.function.*;

public class CommandInterpreter {

    //command - Message workflow

    //definition

    //[Message]
    // TitleML
    // GameStatus:Relevant game info, turn results. Report format  Food:10/+1, Gold:10, Population:10, Happiness:10, Hunger:10
    // * Text: ML label - 118n
    // * Userid: who
    // actor:Adviser, General, bishop, Spy
    // CommandExpression[] list of responses for user to chose from, at least "ok"

    //Message example:
    //"We are ready to start Sire, what shall we do first?"
    //Expressions: "Explore", "Gather -X","Gather Z", "Build [Name] [building function]-[cost]", "Train"

    //[CommandExpression] - Link between user input and actions, Interpreted, validated, and executed
    // Text: ML label - 118n
    //userID
    //Parameters json
    //CommandCode

    // [Command] - One or more actions taken by a player during his turn, Only a list of available commands are possible, defined by
    // by the game depending on rules and context, a command is defined by combining parts, similar to natural language
    // verb [noun] [target]
    //  *userID
    //  toUserID - optional for commands that affect other players
    // expressionML ["help","ayuda"] - how the command is translated to the user, understand multiple languages
    // action()[] - each action is 1 update to the game state . pure functions,
    // type: build, train, gather, explore, move, attack, trade, ...


    //[Action]
    //* SourcePlayer
    //TargetPlayer
    //* call to a GameFunction
    //type[1P,P2P,NONE]

    //Game IA Analyses the game state and game rules to create a list of possible commands per player
    //Commands of the same type are grouped together into a Message
    //Message is added to the MessageQueue
    //User receives a message with a list of allowed commandsExpressions
    //user selects a commandExpression as response
    //CommandExpression
    private static interface VoidFunction {
        void call();
    }


    enum commandBLocks {
        SHOW_ME(new String[]{"show", "show me", "mostrame"}, CommandInterpreter::print, 10),
        MY(new String[]{"my", "mi", "mis"}, CommandInterpreter::getCurrentPlayer, 3),
        GAME(new String[]{"game", "juego"}, CommandInterpreter::getGame, 3),
        HELP(new String[]{"help", "ayuda"}, CommandInterpreter::help, -1),
        EXIT(new String[]{"exit", "salir"}, CommandInterpreter::exit, 4),
        INVENTORY(new String[]{"inventory", "inventario"}, CommandInterpreter::getInventory, 7),
        STATS(new String[]{"stats", "estadisticas"}, CommandInterpreter::getStats, 7);

        private String[] keywords;
        private VoidFunction action;
        private int priority;

        commandBLocks(String[] keywords, VoidFunction action, int priority) {
            this.keywords = keywords;
            this.action = action;
            this.priority = priority;
        }


    }

    private static Object firstSubject = null;
    private static StringBuilder answerText = new StringBuilder();


    private static void getStats() {
        if (firstSubject == null) {
            GAdmin.getInstance().getGame(GameRules.testConstants.get("gameID")).getPlayers().get(0).getStatsNarration();
        }
        if (firstSubject instanceof Player) {
            Player player = (Player) firstSubject;
            answerText.append(player.getStatsNarration());
        }
        if (firstSubject instanceof Game) {
            Game game = (Game) firstSubject;
            answerText.append(game.getStatsNarration());
        }
        //System.out.println("getStats called");
    }

    private static void getInventory() {
        System.out.println("getInventory called");

    }

    private static void help() {
        System.out.println("help called");
    }

    private static void getGame() {
        firstSubject = GAdmin.getInstance().getGame("gameId");
        System.out.println("getGame called");
    }

    private static void getCurrentPlayer() {
        firstSubject = GAdmin.getInstance().getGame("gameId").getPlayers().get(0);
        System.out.println("getCurrentPlayer called");
    }

    private static void print() {
        System.out.println("print called");
    }

    private static void exit() {
        System.out.println("exit called");
    }

    public static String interpretExpression(String expression) {


        //1. Check for common use commands that are always allowed within the game
        final String[] termList = Helper.getTermListFromCommand(expression);
        Stack<VoidFunction> actionStack = Helper.findMatchingActionsInOrder(termList);

        answerText = new StringBuilder();
        while (actionStack.stream().iterator().hasNext()) {
            actionStack.pop().call();
        }

        return answerText.toString().isEmpty() ? "I'm not sure what you mean" : answerText.toString();
    }


    static class Helper {
        static String[] getTermListFromCommand(String command) {
            if (command == null) {
                return new String[]{};
            }
            String[] terms = command.split(" ");
            //terms to lower case
            return Arrays.stream(terms).map(String::toLowerCase).map(String::trim).toArray(String[]::new);
        }


        public static Stack findMatchingActionsInOrder(String[] termList) {
            Stack<VoidFunction> actionStack = new Stack<>();

            Predicate<String> termIsCommand = (commandKeyword) -> {
                //return true if the iterated term is contained in the list of keywords
                //of the iterated commandBlock
                if (Arrays.stream(termList).anyMatch(commandKeyword::equalsIgnoreCase)) {
                    return true;
                }
                return false;
            };

            Arrays.stream(commandBLocks.values())
                    .sorted(Comparator.comparingInt(block -> 10 - block.priority))
                    .forEach(block -> {
                        if (Arrays.stream(block.keywords).anyMatch(termIsCommand)) {
                            actionStack.push(block.action);
                        }
                    });
            return actionStack;
        }
    }
}