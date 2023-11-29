package com.rpg;

import com.fight.SkillManager;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    private final String name;
    private int gold;
    private int hp;
    private int maxHP;
    private int mana;
    private int maxMana;
    private int level;
    private int experience;
    private int experienceMax;
    private int speed;
    private int strength;
    private int accuracy;
    private int intelligence;
    private int awakening;
    private int levelUpPoint;
    private int dungeonStage;
    private int dungeonEventNumber;
    private int dungeonBeforeNextStage;
    private int dungeonHighestStage;
    private boolean nextStageAvailable;
    //TODO maps for occurence
    private boolean[] majorOccurence;
    private Runnable labelListener;
    private Runnable levelListener;
    private Inventory inventory;
    private int id;
    private TypePlace currentPosition;
    private SkillManager skills;
    private ArrayList<Integer> skillsRef;

    private Player(int id, String name, int hp, int maxHP, int mana, int maxMana, int  level, int experience, int speed, int strength, int accuracy, int intelligence, int awakening, int  experienceMax, int levelUpPoint, int dungeonStage, int dungeonEventNumber, int dungeonBeforeNextStage, int dungeonHighestStage, boolean nextStageAvailable, ArrayList<Integer> attacksRef, int gold){
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.maxHP = maxHP;
        this.mana = mana;
        this.maxMana = maxMana;
        this.level = level;
        this.experience = experience;
        this.speed = speed;
        this.strength = strength;
        this.accuracy = accuracy;
        this.intelligence = intelligence;
        this.awakening = awakening;
        this.experienceMax = experienceMax;
        this.levelUpPoint = levelUpPoint;
        this.dungeonStage = dungeonStage;
        this.dungeonEventNumber = dungeonEventNumber;
        this.dungeonBeforeNextStage = dungeonBeforeNextStage;
        this.dungeonHighestStage = dungeonHighestStage;
        this.nextStageAvailable = nextStageAvailable;
        this.skillsRef = attacksRef;
        this.gold = gold;
    }

    private void initMajorOccurence() {
        majorOccurence = new boolean[0];
    }

    public void expMaxForLevelUp(){
        if(getExperience() >= getExperienceMax()){
            setExperience(getExperience() - getExperienceMax());
            setLevel(getLevel()+1);
            setLevelUpPoint(getLevelUpPoint()+3);
            this.levelListener.run();

        }
        setExperienceMax((level-1) * 20 + 100);
    }
    public void healHp(){
        setHp(getMaxHP());
        labelListener.run();
    }
    public void healMana(){
        setMana(getMaxMana());
        labelListener.run();
    }

    public void addItem(final short key, final int value){
        this.inventory.addItem(key, value);
    }
    public void removeItem(final short key, final int value){
        if(!this.inventory.contain(key,value))return;
        this.inventory.removeItem(key, value);
    }
    public boolean containItem(final short key, final int value ){
        return this.inventory.contain(key,value);
    }
    public void setSkills(SkillManager skills) {
        this.skills = skills;
    }
    public void setAttackMove(){
       skills.setSkills(skillsRef);
    }
    public void addSkillsMove(int... ref){
        for(int skillRef : ref){
            skillsRef.add(skillRef);
        }
        setAttackMove();
    }
    public void removeSkillsMove(int... ref){
        for(int skillRef: ref){
            for(int i = 0; i < skillsRef.size(); i++){
                if(skillsRef.get(i) == skillRef){
                    skillsRef.remove(i);
                    break;
                }
            }
        }
        setAttackMove();
    }

    //---------- Getter / Setter -----------------------
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public void setLevel(int level) {
        this.level = level;
        labelListener.run();
    }

    public int getLevel() {
        return level;
    }

    public void setHp(int hp) {
        if(hp <= 0){
            hp = 0;
        }
        this.hp = hp;
        labelListener.run();
    }

    public int getHp() {
        return hp;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
        labelListener.run();
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMana(int mana) {
        this.mana = mana;
        labelListener.run();
    }

    public int getMana() {
        return mana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        labelListener.run();
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        labelListener.run();
    }

    public int getSpeed() {
        return speed;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        labelListener.run();
    }

    public int getStrength() {
        return strength;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
        labelListener.run();
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
        labelListener.run();
    }

    public int getIntelligence() {
        return intelligence;
    }


    public void setAwakening(int awakening) {
        this.awakening = awakening;
        labelListener.run();
    }

    public int getAwakening() {
        return awakening;
    }

    public void setExperience(int experience) {
        this.experience = experience;
        labelListener.run();
    }
    public int getExperience() {
        return experience;
    }

    public void setLevelUpPoint(int levelUpPoint) {
        this.levelUpPoint = levelUpPoint;
        labelListener.run();
    }

    public int getLevelUpPoint() {
        return levelUpPoint;
    }

    public void setExperienceMax(int experienceMax) {
        this.experienceMax = experienceMax;
    }

    public int getExperienceMax() {
        return experienceMax;
    }

    public void setDungeonStage(int dungeonStage) {
        this.dungeonStage = dungeonStage;
    }

    public int getDungeonStage() {
        return dungeonStage;
    }

    public void setDungeonBeforeNextStage(int dungeonBeforeNextStage) {
        this.dungeonBeforeNextStage = dungeonBeforeNextStage;
    }

    public int getDungeonBeforeNextStage() {
        return dungeonBeforeNextStage;
    }

    public void setDungeonEventNumber(int dungeonEventNumber) {
        this.dungeonEventNumber = dungeonEventNumber;
    }

    public int getDungeonEventNumber() {
        return dungeonEventNumber;
    }

    public void setNextStageAvailable(boolean nextStageAvailable) {
        this.nextStageAvailable = nextStageAvailable;
    }

    public boolean isNextStageAvailable() {
        return nextStageAvailable;
    }

    public void setDungeonHighestStage(int dungeonHighestStage) {
        this.dungeonHighestStage = dungeonHighestStage;
    }

    public int getDungeonHighestStage() {
        return dungeonHighestStage;
    }
    public void addLabelListener(Runnable runnable) {
        this.labelListener = runnable;
    }
    public void addLevelListener(Runnable runnable) {
        this.levelListener = runnable;
    }

    public int getId() {
        return this.id;
    }

    public void setCurrentPosition(TypePlace currentPosition) {
        this.currentPosition = currentPosition;
    }
    public TypePlace getCurrentPosition() {
        return currentPosition;
    }

    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
        labelListener.run();
    }

    public enum TypePlace{
        DUNGEON(0),
        TOWN(1),
        TAVERN(2),
        CHURCH(3),
        ADVENTURERGUILD(4),
        DUNGEONENTRANCE(5),
        FIGHTSTART(6),
        FIGHT(7),
        FIGHTEVENT(8),
        EVENT(9),
        FOUTAIN(10),
        ALTAR(11),
        LIBRARY(12),
        TRAININGFIELD(13),
        MERCHANT(14),
        ;

        private final int value;

        TypePlace(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public ArrayList<Integer> getSkillsRef() {
        return skillsRef;
    }

    public static class PlayerBuilder{
        private int gold;
        private int id;
        private String name;
        private int hp;
        private int maxHP;
        private int mana;
        private int maxMana;
        private int level;
        private int experience;
        private int experienceMax;
        private int speed;
        private int strength;
        private int accuracy;
        private int intelligence;
        private int awakening;
        private int levelUpPoint;
        private int dungeonStage;
        private int dungeonEventNumber;
        private int dungeonBeforeNextStage;
        private int dungeonHighestStage;
        private boolean nextStageAvailable;
        private ArrayList<Integer> skillsRef;
        public PlayerBuilder(String name){
            this.id = 1;
            this.name = name;
            this.hp = 10;
            this.maxHP = 10;
            this.mana = 10;
            this.maxMana = 10;
            this.level = 1;
            this.experience = 0;
            this.speed = 5;
            this.strength = 5;
            this.accuracy = 5;
            this.intelligence = 5;
            this.awakening = 0;
            this.experienceMax = 100;
            this.levelUpPoint = 0;
            this.dungeonStage = 0;
            this.dungeonEventNumber = 0;
            this.dungeonBeforeNextStage = 60;
            this.dungeonHighestStage = 0;
            this.nextStageAvailable = false;
            this.skillsRef = new ArrayList<>();
            this.skillsRef.add(0);
            this.skillsRef.add(1);
            this.gold = 10;
        }
        public Player build() {
            return new Player(id, name, hp,maxHP, mana, maxMana, level, experience, speed, strength, accuracy, intelligence, awakening, experienceMax, levelUpPoint, dungeonStage, dungeonEventNumber, dungeonBeforeNextStage, dungeonHighestStage, nextStageAvailable, skillsRef, gold);
        }

        public PlayerBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public PlayerBuilder setName(String name) {
            this.name = name;
            return this;
        }
        public PlayerBuilder setLevel(int level) {
            this.level = level;
            return this;
        }
        public PlayerBuilder setHp(int hp) {
            this.hp = hp;
            return this;
        }
        public PlayerBuilder setMaxHP(int maxHP) {
            this.maxHP = maxHP;
            return this;
        }
        public PlayerBuilder setMana(int mana) {
            this.mana = mana;
            return this;
        }
        public PlayerBuilder setMaxMana(int maxMana) {
            this.maxMana = maxMana;
            return this;
        }
        public PlayerBuilder setSpeed(int speed) {
            this.speed = speed;
            return this;
        }
        public PlayerBuilder setStrength(int strength) {
            this.strength = strength;
            return this;
        }
        public PlayerBuilder setAccuracy(int accuracy) {
            this.accuracy = accuracy;
            return this;
        }
        public PlayerBuilder setIntelligence(int intelligence) {
            this.intelligence = intelligence;
            return this;
        }
        public PlayerBuilder setAwakening(int awakening) {
            this.awakening = awakening;
            return this;
        }
        public PlayerBuilder setExperience(int experience) {
            this.experience = experience;
            return this;
        }
        public PlayerBuilder setLevelUpPoint(int levelUpPoint) {
            this.levelUpPoint = levelUpPoint;
            return this;
        }
        public PlayerBuilder setExperienceMax(int experienceMax) {
            this.experienceMax = experienceMax;
            return this;
        }
        public PlayerBuilder setDungeonStage(int dungeonStage) {
            this.dungeonStage = dungeonStage;
            return this;
        }
        public PlayerBuilder setDungeonBeforeNextStage(int dungeonBeforeNextStage) {
            this.dungeonBeforeNextStage = dungeonBeforeNextStage;
            return this;
        }
        public PlayerBuilder setDungeonEventNumber(int dungeonEventNumber) {
            this.dungeonEventNumber = dungeonEventNumber;
            return this;
        }
        public PlayerBuilder setNextStageAvailable(boolean nextStageAvailable) {
            this.nextStageAvailable = nextStageAvailable;
            return this;
        }
        public PlayerBuilder setDungeonHighestStage(int dungeonHighestStage) {
            this.dungeonHighestStage = dungeonHighestStage;
            return this;
        }
        public PlayerBuilder setSkillsRef(String skillsString) {
            ArrayList<Integer> skillsRef = new ArrayList<>();
            String[] arr = skillsString.split(";");
            for(int i=0; i < arr.length; i++){
                skillsRef.add(Integer.parseInt(arr[i]));
            }
            this.skillsRef = skillsRef;
            return this;
        }

        public PlayerBuilder setGold(int gold) {
            this.gold = gold;
            return this;
        }
    }

}
