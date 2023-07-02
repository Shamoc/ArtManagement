package DB_Implementation;


import Model.Artwork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtworkDaoImplementation {
    private static ArtworkDaoImplementation artInstance;
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

    public int add(Artwork artwork)
            throws SQLException
    {
        String query
                = "insert into arts(name, " + "description, " + "author, " + "acquisition_year, " + "art_style, " + "inv_id, " + "expo_id ) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, artwork.getName());
        ps.setString(2, artwork.getDescription());
        ps.setString(3, artwork.getAuthor());
        ps.setInt(4, artwork.getAcquisitionYear());
        ps.setString(5, artwork.getArtStyle());
        ps.setInt(6, artwork.getInv_id());
        if (artwork.getExpo_id() != null) {
            ps.setInt(7, artwork.getExpo_id());
        } else {
            ps.setNull(7, Types.INTEGER);
        }
        int n = ps.executeUpdate();
        return n;
    }
    public void delete(int id)
            throws SQLException
    {
        String query
                = "delete from arts where id =?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    public Artwork getArtwork(int id)
            throws SQLException
    {

        String query
                = "select * from arts where id= ?";
        PreparedStatement ps
                = con.prepareStatement(query);

        ps.setInt(1, id);
        Artwork art = new Artwork();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            art.setArt_id(rs.getInt("id"));
            art.setName(rs.getString("name"));
            art.setDescription(rs.getString("description"));
            art.setAuthor(rs.getString("author"));
            art.setAcquisitionYear(rs.getInt("acquisition_year"));
            art.setArtStyle(rs.getString("art_style"));
            art.setInv_id(rs.getInt("inv_id"));
            art.setExpo_id(rs.getInt("expo_id"));
        }

        if (check == true) {
            return art;
        }
        else
            return null;
    }
    public List<Artwork> getArtwork()
            throws SQLException
    {
        String query = "select * from arts";
        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Artwork> ls = new ArrayList();

        while (rs.next()) {
            Artwork art = new Artwork();
            art.setArt_id(rs.getInt("id"));
            art.setName(rs.getString("name"));
            art.setDescription(rs.getString("description"));
            art.setAuthor(rs.getString("author"));
            art.setAcquisitionYear(rs.getInt("acquisition_year"));
            art.setArtStyle(rs.getString("art_style"));
            art.setInv_id(rs.getInt("inv_id"));
            art.setExpo_id(rs.getInt("expo_id"));
            ls.add(art);
        }
        return ls;
    }
    public List<Artwork> getArtForExpo()
            throws SQLException {
        String query = "select * from arts where inv_id != \"null\";";
        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Artwork> ls = new ArrayList();



        while (rs.next()) {
            Artwork art = new Artwork();
            art.setArt_id(rs.getInt("id"));
            art.setName(rs.getString("name"));
            art.setDescription(rs.getString("description"));
            art.setAuthor(rs.getString("author"));
            art.setAcquisitionYear(rs.getInt("acquisition_year"));
            art.setArtStyle(rs.getString("art_style"));
            art.setInv_id(rs.getInt("inv_id"));
            art.setExpo_id(rs.getInt("expo_id"));
            ls.add(art);
        }
        return ls;
    }
    public void updateArtToExpo(int artID, int expoID)
            throws SQLException {
        String query
                = "update arts set inv_id = null, expo_id =? where id =?;";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, expoID);
        ps.setInt(2, artID);
        ps.executeUpdate();
    }
    public void updateArtToInvFromExpo(int expoID)
            throws  SQLException {
        String query
                = "update arts set inv_id = 1, expo_id = null where expo_id =?;";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, expoID);
        ps.executeUpdate();
    }
    public void update(Artwork art)
            throws SQLException
    {

        String query
                = "update arts set name=?, "
                + " description= ?, author=?, acquisition_year=?, art_style=?, inv_id=?, expo_id=?, rent_id=? where id = ?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, art.getName());
        ps.setString(2, art.getDescription());
        ps.setString(3, art.getAuthor());
        ps.setInt(4, art.getAcquisitionYear());
        ps.setString(5, art.getArtStyle());
        ps.setInt(6, art.getInv_id());
        ps.setInt(7, art.getExpo_id());
        ps.setInt(9, art.getArt_id());
        ps.executeUpdate();
    }

    public static ArtworkDaoImplementation getInstance() {
        if (artInstance == null) {
            artInstance = new ArtworkDaoImplementation();
        }
        return artInstance;
    }
}
