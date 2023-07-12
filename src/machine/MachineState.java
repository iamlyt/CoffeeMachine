package machine;

public class MachineState {
    enum State {
        // main
        ACTION_MENU,
        BUY,
        FILL_WATER,
        FILL_MILK,
        FILL_COFFEE_BEANS,
        FILL_CUPS,
        EXIT
    }

    // enum classes for State and Coffee
    private State state;
    private Coffee coffee;

    private int water;
    private int milk;
    private int coffeeBeans;
    private int disposableCups;
    private int money;

    public MachineState(int water, int milk, int coffee, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffee;
        this.disposableCups = cups;
        this.money = money;

        // show action menu FIRST
        actionMenu();
    }

    // boolean method for executing program --> check if machine is on or off
    public boolean isOn() {
        return state != State.EXIT;
    }

    // shows action menu
    public void actionMenu() {
        state = State.ACTION_MENU;
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    // this is where you'll handle the input's STATE that comes in
    public void handleInput(String input) {
        switch (state) {
            case ACTION_MENU -> action(input);  // action menu shows FIRST
            case BUY -> {
                buy(input);
                actionMenu();
            }
            case FILL_WATER -> {
                this.water += Integer.parseInt(input);
                System.out.println("Write how many ml of milk you want to add:");
                state = State.FILL_MILK;
            }
            case FILL_MILK -> {
                this.milk += Integer.parseInt(input);
                System.out.println("Write how many grams of coffee beans you " +
                        "want to add:");
                state = State.FILL_COFFEE_BEANS;
            }
            case FILL_COFFEE_BEANS -> {
                this.coffeeBeans += Integer.parseInt(input);
                System.out.println("Write how many disposable cups you want " +
                        "to add:");
                state = State.FILL_CUPS;
            }
            case FILL_CUPS -> {
                this.disposableCups += Integer.parseInt(input);
                System.out.println();
                actionMenu();
            }

        }
    }

    // this is where you'll invoke each action depending on input
    // sets the state
    public void action(String input) {
        switch (input) {
            case "buy" -> {
                System.out.println("\nWhat do you want to buy? 1 - espresso, " +
                        "2 - latte, 3 - cappuccino, back - to main menu:");
                state = State.BUY;      //sets state to buy -> execute buy method
            }
            case "remaining" -> {
                remaining();
                actionMenu();
            }
            case "take" -> {
                take();
                actionMenu();
            }
            case "fill" -> {
                System.out.println("\nWrite how many ml of water you want to " +
                        "add:");
                state = State.FILL_WATER;   //sets state to fill water
            }
            case "exit" -> state = State.EXIT;         //sets state to "exit"
            default -> {
                System.out.println("Unknown action. Please try again.\n");
                actionMenu();
            }
        }
    }

    // check which coffee customer wants and set coffee to that particular one
    public void buy(String input) {
        switch (input) {
            case "1" -> coffee = Coffee.ESPRESSO;
            case "2" -> coffee = Coffee.LATTE;
            case "3" -> coffee = Coffee.CAPPUCCINO;
        }
        // check if input is back, if it is, set state to action menu
        if (input.equals("back")) {
            state = State.ACTION_MENU;
        } else {
            enoughResources(coffee);
        }
    }

    // method to take all money
    public void take() {
        System.out.printf("I gave you $%d\n\n", this.money);
        this.money = 0;
    }

    // method for when user inputs "remaining"
    public void remaining() {
        System.out.printf("""
              
              The coffee machine has:
              %d ml of water
              %d ml of milk
              %d g of coffee beans
              %d disposable cups
              $%d of money

              """,this.water,this.milk,this.coffeeBeans,
                this.disposableCups,this.money);
    }

    // checks if there's enough resources to make the coffee
    public void enoughResources(Coffee coffee) {
        if (water < coffee.getWater()) {
            System.out.println("Sorry, not enough water!\n");
        } else if (milk < coffee.getMilk()) {
            System.out.println("Sorry, not enough milk!\n");
        } else if (coffeeBeans < coffee.getCoffeeBeans()) {
            System.out.println("Sorry, not enough coffee beans!\n");
        } else if (disposableCups < 1) {
            System.out.println("Sorry, not enough disposable cups!\n");
        } else {
            this.water -= coffee.getWater();
            this.milk -= coffee.getMilk();
            this.coffeeBeans -= coffee.getCoffeeBeans();
            disposableCups--;
            this.money += coffee.getMoney();
            System.out.println("I have enough resources, making you a " +
                    "coffee!\n");
        }
    }


}

