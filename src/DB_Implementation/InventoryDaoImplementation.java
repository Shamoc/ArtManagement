package DB_Implementation;

import Model.Institution;
import Model.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDaoImplementation {
    private static InventoryDaoImplementation invInstance;
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

    public int add(Inventory inv)
            throws SQLException
    {
        String query
                = "insert into inventory (name, " + "address) VALUES (?, ?)";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, inv.getInventoryName());
        ps.setString(2, inv.getInventoryAddress());

        int n = ps.executeUpdate();
        return n;
    }

    public void delete(int id)
            throws SQLException
    {
        String query
                = "delete from inventory where id =?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public Inventory getInv(int id)
            throws SQLException
    {

        String query
                = "select * from inventory where id= ?";
        PreparedStatement ps
                = con.prepareStatement(query);

        ps.setInt(1, id);
        Inventory inv = new Inventory();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            inv.setInv_id(rs.getInt("id"));
            inv.setInventoryName(rs.getString("name"));
            inv.setInventoryAddress(rs.getString("address"));
        }

        if (check == true) {
            return inv;
        }
        else
            return null;
    }

    public List<Inventory> getInventory()
            throws SQLException
    {
        String query = "select * from inventory";
        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Inventory> ls = new ArrayList();

        while (rs.next()) {
            Inventory inv = new Inventory();
            inv.setInv_id(rs.getInt("id"));
            inv.setInventoryName(rs.getString("name"));
            inv.setInventoryAddress(rs.getString("address"));
            ls.add(inv);
        }
        return ls;
    }

    public void update(Inventory inv)
            throws SQLException
    {

        String query
                = "update inventory set name=?, "
                + " address= ? where id = ?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, inv.getInventoryName());
        ps.setString(2, inv.getInventoryAddress());
        ps.setInt(3, inv.getInv_id());
        ps.executeUpdate();
    }

    public static InventoryDaoImplementation getInstance() {
        if (invInstance == null) {
            invInstance = new InventoryDaoImplementation();
        }
        return invInstance;
    }
}