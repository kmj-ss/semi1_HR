package smanageT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LevelUp {
	
	Connection con;
	
	public LevelUp(Connection con) {
		String sql = " select empno, floor((trunc(sysdate) - hiredate) / 365) as levelup "
				+ " from jobst ";
		
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			sql = " update emptable set jlevel = ? where empno = ?";
			while (rs.next()) {
				switch (rs.getInt(2)) {
				case 0: case 1: case 2:
					ps = con.prepareStatement(sql);
					ps.setString(1, "사원");
					ps.setInt(2, rs.getInt(1));
					ps.executeQuery();
					break;
				case 3: case 4:
					ps = con.prepareStatement(sql);
					ps.setString(1, "주임");
					ps.setInt(2, rs.getInt(1));
					ps.executeQuery();
					break;
				case 5: case 6:
					ps = con.prepareStatement(sql);
					ps.setString(1, "대리");
					ps.setInt(2, rs.getInt(1));
					ps.executeQuery();
					break;
				case 7: case 8: case 9: case 10: case 11:
					ps = con.prepareStatement(sql);
					ps.setString(1, "과장");
					ps.setInt(2, rs.getInt(1));
					ps.executeQuery();
					break;
				case 12: case 13: case 14:
					ps = con.prepareStatement(sql);
					ps.setString(1, "차장");
					ps.setInt(2, rs.getInt(1));
					ps.executeQuery();
					break;
				default:
					if (rs.getInt(2) >= 15) {
						if (rs.getInt(1) != 1) {
							ps = con.prepareStatement(sql);
							ps.setString(1, "부장");
							ps.setInt(2, rs.getInt(1));
							ps.executeQuery();
						} else {
							ps = con.prepareStatement(sql);
							ps.setString(1, "사장");
							ps.setInt(2, rs.getInt(1));
							ps.executeQuery();
						}
					}
					break;
				}
			}
			
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
