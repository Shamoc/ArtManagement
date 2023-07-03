package DB_Implementation;

import Model.Artwork;
import Model.Expositon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpositionDaoImplementation {
    private static ExpositionDaoImplementation expoInstance;
    static Connection con = null;
    static {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/artcodb",
                    "root", "wjcXWo69^1ez2S#hMShCh$WeLxIJXz");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(Expositon expo)
            throws SQLException
    {
        String query
                = "insert into expo (name, " + "description, " + "status, " + "start_date, " + "end_date ) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, expo.getExpoName());
        ps.setString(2, expo.getExpoDescription());
        ps.setString(3, expo.getExpoStatus());
        ps.setDate(4, expo.getStartDate());
        ps.setDate(5, expo.getEndDate());

        int n = ps.executeUpdate();
        return n;
    }
    public void delete(int id)
            throws SQLException
    {
        String query
                = "delete from expo where id =?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    public Expositon getExposition(int id)
            throws SQLException
    {

        String query
                = "select * from expo where id= ?";
        PreparedStatement ps
                = con.prepareStatement(query);

        ps.setInt(1, id);
        Expositon expo = new Expositon();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            expo.setExpo_id(rs.getInt("id"));
            expo.setExpoName(rs.getString("name"));
            expo.setExpoDescription(rs.getString("description"));
            expo.setExpoStatus(rs.getString("status"));
            expo.setStartDate(rs.getDate("start_date"));
            expo.setEndDate(rs.getDate("end_date"));
        }

        if (check == true) {
            return expo;
        }
        else
            return null;
    }
    public boolean getIsOnExpo(int id)
            throws SQLException {
        String query = "select arts.id, arts.expo_id from arts where arts.expo_id != \"null\" && arts.id = ?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
        }
        if (check == true) {
            return true;
        }
        else
            return false;
    }
    public List<Expositon> getExposition()
            throws SQLException
    {
        String query = "select * from expo";
        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Expositon> ls = new ArrayList();

        while (rs.next()) {
            Expositon expo = new Expositon();
            expo.setExpo_id(rs.getInt("id"));
            expo.setExpoName(rs.getString("name"));
            expo.setExpoDescription(rs.getString("description"));
            expo.setExpoStatus(rs.getString("status"));
            expo.setStartDate(rs.getDate("start_date"));
            expo.setEndDate(rs.getDate("end_date"));
            ls.add(expo);
        }
        return ls;
    }
    public void update(Expositon expo)
            throws SQLException
    {

        String query
                = "update expo set name=?, "
                + " description= ?, status=?, start_date=?, end_date=? where id = ?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, expo.getExpoName());
        ps.setString(2, expo.getExpoDescription());
        ps.setString(3, expo.getExpoStatus());
        ps.setDate(4, expo.getStartDate());
        ps.setDate(5, expo.getEndDate());
        ps.setInt(6, expo.getExpo_id());
        ps.executeUpdate();
    }
    public List<Artwork> getArtOnExpo(int id)
            throws SQLException {
        String query = "select arts.id, arts.name from arts inner join expo on expo.id = expo_id where expo_id =?;";
        PreparedStatement ps
                = con.prepareStatement(query);

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        List<Artwork> ls = new ArrayList();

        while (rs.next()) {
            Artwork art = new Artwork();
            art.setArt_id(rs.getInt("id"));
            art.setName(rs.getString("name"));
            ls.add(art);
        }
        return ls;
    }
    public static ExpositionDaoImplementation getInstance() {
        if (expoInstance == null) {
            expoInstance = new ExpositionDaoImplementation();
        }
        return expoInstance;
    }
}
