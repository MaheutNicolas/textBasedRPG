package com.rpg;

import com.item.Item;
import com.item.ItemTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private final HashMap<Short, ItemTemplate> itemTemplateMap = new HashMap<>();
    public boolean save(final Player player) {

        StringBuilder attackRef = new StringBuilder();
        for(int i = 1; i < player.getSkillsRef().size()-1; i++){
            attackRef.append(player.getSkillsRef().get(i))
                    .append(";");
        }
        if(player.getSkillsRef().size() > 1) {
            attackRef.append(player.getSkillsRef().get(player.getSkillsRef().size()-1));
        }
        else{
            attackRef.append(";");
        }
        final String request = "INSERT INTO players VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (final Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
             final PreparedStatement ps = connection.prepareStatement(request);
             final Statement statement = connection.createStatement()) {
            //Save players
            connection.prepareStatement("DELETE FROM players WHERE id = "+ player.getId()).executeUpdate();
            ps.setInt(1, player.getId());
            ps.setString(2, player.getName());
            ps.setInt(3, player.getLevel());
            ps.setInt(4, player.getLevelUpPoint());
            ps.setInt(5, player.getExperience());
            ps.setInt(6, player.getHp());
            ps.setInt(7, player.getMaxHP());
            ps.setInt(8, player.getMana());
            ps.setInt(9, player.getMaxMana());
            ps.setInt(10, player.getSpeed());
            ps.setInt(11, player.getStrength());
            ps.setInt(12, player.getAccuracy());
            ps.setInt(13, player.getIntelligence());
            ps.setInt(14, player.getAwakening());
            ps.setInt(15, player.getDungeonStage());
            ps.setInt(16, player.getDungeonEventNumber());
            ps.setInt(17, player.getDungeonBeforeNextStage());
            ps.setInt(18, player.getDungeonHighestStage());
            ps.setBoolean(19, player.isNextStageAvailable());
            ps.setString(20, attackRef.toString());
            ps.setInt(21, player.getGold());
            ps.execute();

            //Save Items
            connection.prepareStatement("DELETE FROM items WHERE id_player = "+ player.getId()).executeUpdate();
            for (final Map.Entry<Short, Item> entry : player.getInventory().getItems().entrySet()) {
                StringBuilder sb = new StringBuilder("INSERT INTO items (id_template, id_player, quantity) VALUES (")
                        .append(entry.getKey() + ",")
                        .append(player.getId()+",")
                        .append(entry.getValue().getQuantity() + ")");

                statement.addBatch(sb.toString());
            }
            statement.executeBatch();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
    public Player load(Inventory inventory) {
        //load Player
        final Player.PlayerBuilder pb = new Player.PlayerBuilder("loading");
        try(final Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
            final ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM players WHERE id = 1");
            final ResultSet rsInv = connection.createStatement().executeQuery("SELECT * FROM items WHERE id_player = 1")) {

            while (rs.next()) {
                pb.setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setHp(rs.getInt("hp"))
                        .setMana(rs.getInt("mana"))
                        .setMaxHP(rs.getInt("max_hp"))
                        .setMaxMana(rs.getInt("max_mana"))
                        .setLevel(rs.getInt("level"))
                        .setExperience(rs.getInt("experience"))
                        .setLevelUpPoint(rs.getInt("level_point"))
                        .setSpeed(rs.getInt("speed"))
                        .setStrength(rs.getInt("strength"))
                        .setAccuracy(rs.getInt("accuracy"))
                        .setIntelligence(rs.getInt("intelligence"))
                        .setDungeonStage(rs.getInt("stage"))
                        .setDungeonEventNumber(rs.getInt("event_number"))
                        .setDungeonBeforeNextStage(rs.getInt("before_next_stage"))
                        .setDungeonHighestStage(rs.getInt("highest_stage"))
                        .setNextStageAvailable(rs.getBoolean("next_stage_available"))
                        .setSkillsRef( rs.getString("attack_ref"))
                        .setGold(rs.getInt("gold"));
            }
            final Player player = pb.build();

            //load inventory
            player.setInventory(inventory);
            while (rsInv.next()){
                player.addItem(rsInv.getShort("id_template"), rsInv.getInt("quantity"));
            }
            return player;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<Short, ItemTemplate> loadItemsTemplate() {

        try(final Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
            final Statement statement = connection.createStatement();)
        {
            // Code
            final ResultSet rs = statement.executeQuery("SELECT * FROM items_templates");
            while(rs.next()) {

                short id = rs.getShort("id");
                int price = rs.getInt("price");
                String name = rs.getString("name");
                String[] actions = rs.getString("actions").trim().split(";");
                String[] args = rs.getString("args").split(";");
                String[] cdts = rs.getString("cdts").split(";");
                this.itemTemplateMap.put(id, new ItemTemplate(name, id, actions, args, cdts, price));
            }
            return itemTemplateMap;
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return  null;
    }

    public HashMap<Short, ItemTemplate> getItemTemplateMap() {
        return itemTemplateMap;
    }

    public ArrayList<Quest> loadQuest() {
        try(final Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
            final Statement statement = connection.createStatement())
        {
            ArrayList<Quest> questList = new ArrayList<>();
            final ResultSet rs = statement.executeQuery("SELECT * FROM quest");
            while(rs.next()) {
                int id = rs.getShort("id");
                short itemID = (short) rs.getInt("id_item");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int gold = rs.getInt("gold");
                questList.add( new Quest( id, name, itemID, quantity, gold));
            }
            return questList;
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return  new ArrayList<>();
    }

    public void saveQuest(ArrayList<Quest> questList) {

        try (final Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
             final PreparedStatement statement = connection.prepareStatement("INSERT INTO quest (id, id_item, name, quantity, gold) VALUES (?,?,?,?,?)")){
            connection.prepareStatement("DELETE FROM quest ").executeUpdate();
            for (Quest quest : questList) {
                statement.setInt(1,quest.getId());
                statement.setInt(2,quest.getItemId());
                statement.setString(3,quest.getName());
                statement.setInt(4,quest.getQuantity());
                statement.setInt(5,quest.getGold());
                statement.execute();
            }
            statement.executeBatch();
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}




