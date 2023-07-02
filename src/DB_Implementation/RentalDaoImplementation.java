package DB_Implementation;

import Controller.RentalController;
import Model.Institution;
import Model.Rental;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDaoImplementation {
    private static RentalDaoImplementation rentInstance;
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

    public int add(Rental rent)
            throws SQLException
    {
        String query
                = "insert into rent (price, " + "status, " + "pending_rent, " + "inst_id, " + "art_id ) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, rent.getRentalPrice());
        ps.setString(2, rent.getRentalStatus());
        ps.setInt(3, rent.getPendingRental());
        ps.setInt(4, rent.getInst_id());
        ps.setInt(5, rent.getArt_id());

        int n = ps.executeUpdate();
        return n;
    }

    public void delete(int id)
            throws SQLException
    {
        String query
                = "delete from rent where id =?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public Rental getRent(int id)
            throws SQLException
    {

        String query
                = "select * from rent where id= ?";
        PreparedStatement ps
                = con.prepareStatement(query);

        ps.setInt(1, id);
        Rental rent = new Rental();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            rent.setRent_id(rs.getInt("id"));
            rent.setRentalPrice(rs.getInt("price"));
            rent.setRentalStatus(rs.getString("status"));
            rent.setPendingRental(rs.getInt("pending_rent"));
            rent.setInst_id(rs.getInt("inst_id"));
            rent.setInst_id(rs.getInt("art_id"));
        }

        if (check == true) {
            return rent;
        }
        else
            return null;
    }

    public List<Rental> getRentsByInstitution(int id) throws SQLException {

        String query = "select rent.price, rent.status, rent.pending_rent, rent.art_id from rent where rent.inst_id = ?;";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        List<Rental> ls = new ArrayList();

        while (rs.next()) {
            Rental rent = new Rental();
            rent.setRentalPrice(rs.getInt("price"));
            rent.setRentalStatus(rs.getString("status"));
            rent.setPendingRental(rs.getInt("pending_rent"));
            rent.setArt_id(rs.getInt("art_id"));
            ls.add(rent);
        }

        return ls;
    }

    public boolean getIsOnRentArt(int id) throws SQLException {
        String query = "select rent.status, rent.art_id from rent where rent.status != \"paid\" && rent.art_id = ?;";
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

    public List<Rental> getRental()
            throws SQLException
    {
        String query = "select * from rent";
        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Rental> ls = new ArrayList();

        while (rs.next()) {
            Rental rent = new Rental();
            rent.setRent_id(rs.getInt("id"));
            rent.setRentalPrice(rs.getInt("price"));
            rent.setRentalStatus(rs.getString("status"));
            rent.setPendingRental(rs.getInt("pending_rent"));
            rent.setInst_id(rs.getInt("inst_id"));
            rent.setArt_id(rs.getInt("art_id"));
            ls.add(rent);
        }
        return ls;
    }

    public void update(Rental rent)
            throws SQLException
    {

        String query
                = "update rent set price=?, "
                + " status= ?, pending_rent= ?, inst_id=? where id = ?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, rent.getRentalPrice());
        ps.setString(2, rent.getRentalStatus());
        ps.setInt(3, rent.getPendingRental());
        ps.setInt(4, rent.getInst_id());
        ps.setInt(4, rent.getArt_id());
        ps.setInt(5, rent.getRent_id());

        ps.executeUpdate();
    }

    public static RentalDaoImplementation getInstance() {
        if (rentInstance == null) {
            rentInstance = new RentalDaoImplementation();
        }
        return rentInstance;
    }
}