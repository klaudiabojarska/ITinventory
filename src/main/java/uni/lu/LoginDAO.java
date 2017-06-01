package uni.lu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uni.lu.DataConnect;

public class LoginDAO {

	public static Login validate(String user, String password) {
		Connection con = null;
		PreparedStatement ps = null;
		Login l = new Login();

		try {
			con = DataConnect.getConnection();
			

			ps = con.prepareStatement("Select users.id, login, password, group_id, "
					+ "canCreateUser, canReadUser, canEditUser, "
					+ "canCreateGroup, canReadGroup, canEditGroup, "
					+ "canCreateDevice, canReadDevice, canEditDevice, "
					+ "canCreateConsumables, canReadConsumables, canEditConsumables, "
					+ "canCreateRepair, canReadRepair, canEditRepair, "
					+ "ownedByUser, groups.name, groups.time_create from users inner join groups "
					+ "on users.group_id = groups.id "
					+ "where login = ? and password = ?");
			
			ps.setString(1, user);
			ps.setString(2, password);

			ResultSet result = ps.executeQuery();
			
			if (result.next()) {
				l.setUser(result.getString("login"));
				Group g = new Group();
				l.setUserId(result.getLong("users.id"));
				g.setId(result.getLong("group_id"));
				g.setName(result.getString("groups.name"));
				g.setTime_create(result.getDate("groups.time_create"));
				g.setCanCreateUser(result.getBoolean("canCreateUser"));
				g.setCanReadUser(result.getBoolean("canReadUser"));
				g.setCanEditUser(result.getBoolean("canEditUser"));

				g.setCanCreateGroup(result.getBoolean("canCreateGroup"));
				g.setCanReadGroup(result.getBoolean("canReadGroup"));
				g.setCanEditGroup(result.getBoolean("canEditGroup"));

				g.setCanCreateDevice(result.getBoolean("canCreateDevice"));
				g.setCanReadDevice(result.getBoolean("canReadDevice"));
				g.setCanEditDevice(result.getBoolean("canEditDevice"));

				g.setCanCreateConsumables(result.getBoolean("canCreateConsumables"));
				g.setCanReadConsumables(result.getBoolean("canReadConsumables"));
				g.setCanEditConsumables(result.getBoolean("canEditConsumables"));

				g.setCanCreateRepair(result.getBoolean("canCreateRepair"));
				g.setCanReadRepair(result.getBoolean("canReadRepair"));
				g.setCanEditRepair(result.getBoolean("canEditRepair"));
				
				g.setOwnedByUser(result.getBoolean("ownedByUser"));
				
				/*Long groupId = result.getLong("group_id");
				if (groupId != null){
									
					GroupBean groupBean = new GroupBean();
					groupBean.getList();*/
					
					l.setGroup(g);
					//l.setGroup(groupBean.getGroup(groupId));
				
				
				return l;
			}
				
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return null;
		} finally {
			DataConnect.close(con);
		}
		return null;
	}
}