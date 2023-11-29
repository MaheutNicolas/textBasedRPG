package com.rpg;

import com.fight.Skills;

import java.util.Random;
import java.util.function.Consumer;

public class Enemy {
    private String name;
    private int level;
    private int hp;
    private int mana;
    private int speed;
    private int strength;
    private int accuracy;
    private int intelligence;
    private Skills[] skillsArray;
    private EnemyType type;

    private String[] enemyText;

    private int attackMove;
    final Random random = new Random();

    private int enemyExperience;
    private Consumer<Boolean> listener;
    private short itemDrop;
    public Enemy(){
        rollAttackAction();
    }
    public void rollAttackAction() {
        attackMove = random.nextInt(3);
    }

    public void setEnemy(int mobRef) {
        switch (mobRef) {
            case 0:
                this.name = "Slime";
                this.level = 1;
                this.hp = 10;
                this.mana = 10;
                this.speed = 5;
                this.strength = 4;
                this.accuracy = 1;
                this.intelligence = 7;
                this.enemyText = new String[]{"Un slime apparaît en bondissant",
                        "le noyaux est détruit et le slime meurt",
                        "",
                        "Son noyaux tremble légèrement",
                        ""};
                this.skillsArray = new Skills[]{
                        new Skills("Jet d'acide","Le slime lance un jet d'acide", new int[][]{{1,2,4}},2),
                        new Skills("Charge","Le slime charge et vous percute", new int[][]{{1,2,2}},0)};
                this.enemyExperience = 20;
                this.itemDrop = 1;
                this.type = EnemyType.AQUA;

                break;
            case 1:
                this.name = "Loup";
                this.level = 1;
                this.hp = 8;
                this.mana = 0;
                this.speed = 5;
                this.strength = 5;
                this.accuracy = 1;
                this.intelligence = 5;
                this. enemyText = new String[]{"Un loup apparâit en rugissant",
                        "le loup est mort et disparaît en fumée",
                        "le loups gratte le sol",
                        "",
                        ""};
                this.skillsArray = new Skills[]{
                        new Skills("Coups de griffe","Le Loup donne un coups de griffes", new int[][]{{1,2,4}},0),
                        new Skills("Charge","Le loup charge et vous mord", new int[][]{{1,2,2}},0)};
                this.enemyExperience = 15;
                this.itemDrop = 2;
                type = EnemyType.CANIDAE;
                break;
            default:
                break;
        }
        this.listener.accept(false);
    }

    //---------- Getter / Setter ----------------

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public int getMana() {
        return mana;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getAttackMove() {
        return attackMove;
    }

    public Skills[] getAttackArray() {
        return skillsArray;
    }

    public int getEnemyExperience() {
        return enemyExperience;
    }

    public String[] getEnemyText() {
        return enemyText;
    }

    public EnemyType getType() {
        return type;
    }

    public void setHp(int hp) {
        this.hp = hp;
        this.listener.accept(this.hp <= 0);
    }
    public void setMana(int mana) {
        this.mana = mana;
        this.listener.accept(this.hp <= 0);
    }
    public short getItemDrop() {
        return itemDrop;
    }
    public void addListener(Consumer<Boolean> booleanConsumer) {
        this.listener = booleanConsumer;
    }
    public enum EnemyType{
        AQUA(1),
        CANIDAE(2),
        UNDEAD (3),
        ;
        private final int value;
        EnemyType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
