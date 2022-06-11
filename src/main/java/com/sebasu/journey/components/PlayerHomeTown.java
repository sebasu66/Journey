package com.sebasu.journey.components;

/*
public class PlayerHomeTown implements TurnListener {
    ValueRange happiness = new ValueRange(0, 10,5); //sadness causes desertion, happines = new population, boost productivity
    ValueRange population = new ValueRange(0, 20,20);
    ValueRange hunger = new ValueRange(0, 10,5);
    Player owner;
    private ResourceManager resourceManager;

    public PlayerHomeTown(Player owner) {
        this.owner = owner;
        this.resourceManager = new ResourceManager();
    }

    public void onTurnEnd() {
        population.increase();
    }



class ResourceManager implements TurnListener {

    List<Good> goods = new ArrayList<>();
    List<GoodProductionModifier> modifierList = new ArrayList<>();

    @Override
    public void onTurnEnd() {

    }

    public ResourceManager() {
        int EMPTY_GOOD = 0;
        ValueRange EMPTY_GOOD_PRODUCTION = new ValueRange(0, 0, 0);
        goods.add(new Good(GameComponents.ProductionGoods.FOOD,50, new ValueRange(0,10,4)));
        goods.add(new Good(GameComponents.ProductionGoods.LOGS,EMPTY_GOOD, EMPTY_GOOD_PRODUCTION));
        goods.add(new Good(GameComponents.ProductionGoods.IRONBARS,EMPTY_GOOD, EMPTY_GOOD_PRODUCTION));
        goods.add(new Good(GameComponents.ProductionGoods.COBLESTONE,EMPTY_GOOD, EMPTY_GOOD_PRODUCTION));
        goods.add(new Good(GameComponents.ProductionGoods.HOUSING,EMPTY_GOOD, EMPTY_GOOD_PRODUCTION));
        goods.add(new Good(GameComponents.ProductionGoods.KNOWLEDGE,EMPTY_GOOD, EMPTY_GOOD_PRODUCTION));


    }

     class GoodProductionModifier implements TurnListener {
        Good good;
        int modifier;
        int duration;
        int currentDuration;
        String name;
        boolean dispose = false;
        @Override
        public void onTurnEnd() {
            this.good.getProductionRange().modifyCurrent(modifier);
            currentDuration++;
            if (currentDuration >= duration) {
                dispose = true;
            }
        }

        public GoodProductionModifier(Good good, int modifier, int duration) {
            this.good = good;
            this.modifier = modifier;
            this.duration = duration;
        }

    }


     class Good {
        private GameComponents.ProductionGoods good;
        private int amount;
        private ValueRange productionRange;

        public Good(GameComponents.ProductionGoods good, int amount, ValueRange productionRange) {
            this.good = good;
            this.amount = amount;
            this.productionRange = productionRange;
        }

        public GameComponents.ProductionGoods getGood() {
            return good;
        }

        public int getAmount() {
            return amount;
        }

        public ValueRange getProductionRange() {
            return productionRange;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public void setProductionRange(ValueRange productionRange) {
            this.productionRange = productionRange;
        }
    }
    }
}*/