package DB_Implementation;

import Model.Expositon;
import Model.Institution;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstitutionDaoImplementation {
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

    public int add(Institution inst)
            throws SQLException
    {
        String query
                = "insert into institution(name, " + "address) VALUES (?, ?)";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, inst.getInstName());
        ps.setString(2, inst.getInstAddress());

        int n = ps.executeUpdate();
        return n;
    }

    public void delete(int id)
            throws SQLException
    {
        String query
                = "delete from institution where id =?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public Institution getInst(int id)
            throws SQLException
    {

        String query
                = "select * from institution where id= ?";
        PreparedStatement ps
                = con.prepareStatement(query);

        ps.setInt(1, id);
        Institution inst = new Institution();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            inst.setInst_id(rs.getInt("id"));
            inst.setInstName(rs.getString("name"));
            inst.setInstAddress(rs.getString("address"));
        }

        if (check == true) {
            return inst;
        }
        else
            return null;
    }

    public List<Institution> getInstitution()
            throws SQLException
    {
        String query = "select * from institution";
        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Institution> ls = new ArrayList();

        while (rs.next()) {
            Institution inst = new Institution();
            inst.setInst_id(rs.getInt("id"));
            inst.setInstName(rs.getString("name"));
            inst.setInstAddress(rs.getString("address"));
            ls.add(inst);
        }
        return ls;
    }

    public void update(Institution inst)
            throws SQLException
    {

        String query
                = "update institution set name=?, "
                + " address= ? where id = ?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, inst.getInstName());
        ps.setString(2, inst.getInstAddress());
        ps.setInt(3, inst.getInst_id());
        ps.executeUpdate();
    }
}
