package com.photon.DB;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.photon.Helpers.Player;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

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

    public void createPlayer(Player player) {
        // Create a new player in the database
    }

    public Player findPlayerByID(int id) {
        Player player = null;
        String query = "SELECT * FROM players WHERE id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setInt(1, id); // Safely set the id parameter
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                player = new Player(rs.getString("codename"), rs.getInt("id"), "u");
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
                Player player = new Player(rs.getString("codename"), rs.getInt("id"), "u");
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }
}
