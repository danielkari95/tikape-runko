/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.RaakaAine;

public class RaakaAineDao implements Dao<RaakaAine, Integer> {

    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT RaakaAine.nimi FROM RaakaAine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        RaakaAine raakaAine = new RaakaAine(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return raakaAine;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT RaakaAine.nimi FROM RaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<RaakaAine> annokset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            annokset.add(new RaakaAine(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return annokset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }
    
    public List<RaakaAine> findRaakaAineetForSmoothie(Integer smoothieId) throws SQLException {
        String query = "SELECT * FROM AnnosRaakaAine, Annos WHERE AnnosRaakaAine.annos_id =  ";
        List<RaakaAine> raakaAineet = new ArrayList<>();

    try (Connection conn = database.getConnection()) {
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, smoothieId);
        ResultSet result = stmt.executeQuery();

        while (result.next()) {
            raakaAineet.add(new RaakaAine(result.getInt("id"), result.getString("nimi")));
        }
    }

    return raakaAineet;
}

}