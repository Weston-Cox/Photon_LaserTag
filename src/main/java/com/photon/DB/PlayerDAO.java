package com.photon.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.photon.Helpers.Player;

public class PlayerDAO {
    private Connection connection;

    public PlayerDAO(PostgreSQL postgreSQL) {
        try {
            this.connection = postgreSQL.getConnection();
            System.out.println(this.connection);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Player findPlayerByID(int id) {
        Player player = null;
        String query = "SELECT * FROM players WHERE id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setInt(1, id); // Safely set the id parameter
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                player = new Player(rs.getString("codename"), rs.getInt("id"), "u", -1);
                System.out.println(player.getCodename()); //DEBUGGING
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return player;
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players";
        try (Statement stmt = this.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Player player = new Player(rs.getString("codename"), rs.getInt("id"), "u", -1);
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }


    public boolean updatePlayerInfo(int id, String codename) {
        String query = "UPDATE players SET codename = ? WHERE id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setString(1, codename);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean createPlayer(int id, String codename) {
        String query = "INSERT INTO players (id, codename) VALUES (?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, codename);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}