/**
 *                            License
 * THE WORK (AS DEFINED BELOW) IS PROVIDED UNDER THE TERMS OF THIS  
 * CREATIVE COMMONS PUBLIC LICENSE ("CCPL" OR "LICENSE"). 
 * THE WORK IS PROTECTED BY COPYRIGHT AND/OR OTHER APPLICABLE LAW.  
 * ANY USE OF THE WORK OTHER THAN AS AUTHORIZED UNDER THIS LICENSE OR  
 * COPYRIGHT LAW IS PROHIBITED.
 * 
 * BY EXERCISING ANY RIGHTS TO THE WORK PROVIDED HERE, YOU ACCEPT AND  
 * AGREE TO BE BOUND BY THE TERMS OF THIS LICENSE. TO THE EXTENT THIS LICENSE  
 * MAY BE CONSIDERED TO BE A CONTRACT, THE LICENSOR GRANTS YOU THE RIGHTS CONTAINED 
 * HERE IN CONSIDERATION OF YOUR ACCEPTANCE OF SUCH TERMS AND CONDITIONS.
 * 
 */
package l1j.server.server.model;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.utils.Random;
import l1j.server.server.utils.SQLUtil;
import l1j.server.server.utils.collections.Maps;
import l1j.server.server.serverpackets.S_ServerMessage;

// Referenced classes of package l1j.server.server.model:
// L1Teleport, L1PcInstance

public class DungeonRandom {

	private static Logger _log = Logger.getLogger(DungeonRandom.class.getName());

	private static DungeonRandom _instance = null;

	private static Map<String, NewDungeonRandom> _dungeonMap = Maps.newMap();

	public static DungeonRandom getInstance() {
		if (_instance == null) {
			_instance = new DungeonRandom();
		}
		return _instance;
	}

	private DungeonRandom() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();

			pstm = con.prepareStatement("SELECT * FROM dungeon_random");
			rs = pstm.executeQuery();
			while (rs.next()) {
				int srcMapId = rs.getInt("src_mapid");
				int srcX = rs.getInt("src_x");
				int srcY = rs.getInt("src_y");
				String key = new StringBuilder().append(srcMapId).append(srcX).append(srcY).toString();
				int[] newX = new int[5];
				int[] newY = new int[5];
				short[] newMapId = new short[5];
				int expand = rs.getInt("expand");
				int hour_start = rs.getInt("hour_start");
				int hour_end = rs.getInt("hour_end");
				newX[0] = rs.getInt("new_x1");
				newY[0] = rs.getInt("new_y1");
				newMapId[0] = rs.getShort("new_mapid1");
				newX[1] = rs.getInt("new_x2");
				newY[1] = rs.getInt("new_y2");
				newMapId[1] = rs.getShort("new_mapid2");
				newX[2] = rs.getInt("new_x3");
				newY[2] = rs.getInt("new_y3");
				newMapId[2] = rs.getShort("new_mapid3");
				newX[3] = rs.getInt("new_x4");
				newY[3] = rs.getInt("new_y4");
				newMapId[3] = rs.getShort("new_mapid4");
				newX[4] = rs.getInt("new_x5");
				newY[4] = rs.getInt("new_y5");
				newMapId[4] = rs.getShort("new_mapid5");
				int heading = rs.getInt("new_heading");
				if (expand <= 0) {
					NewDungeonRandom newDungeonRandom = new NewDungeonRandom(newX, newY, newMapId, heading, hour_start, hour_end);
					if (_dungeonMap.containsKey(key)) {
						_log.log(Level.WARNING, "同じキーのdungeonデータがあります。key=" + key);
					} else {
						_dungeonMap.put(key, newDungeonRandom);
					}
				} else if(expand <= 2) {
					for (int dx = -expand; dx <= expand; dx++) {
						for (int dy = -expand; dy <= expand; dy++) {
							if (dx == 0 && dy == 0) {
								continue;
							}
							String expandKey = new StringBuilder().append(srcMapId).append(srcX + dx).append(srcY + dy).toString();
							NewDungeonRandom newDungeonRandom = new NewDungeonRandom(newX, newY, newMapId, heading, hour_start, hour_end);
							if (_dungeonMap.containsKey(expandKey)) {
								_log.log(Level.WARNING, "同dungeon key=" + expandKey);
							} else {
								_dungeonMap.put(expandKey, newDungeonRandom);
							}
						}
					}
				} else {
					_log.log(Level.WARNING, "expand設定過高 expand=" + expand);
				}
			}
		}
		catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static class NewDungeonRandom {
		int[] _newX = new int[5];

		int[] _newY = new int[5];

		short[] _newMapId = new short[5];

		int _heading;

		int _hour_start;

		int _hour_end;

		private NewDungeonRandom(int[] newX, int[] newY, short[] newMapId, int heading, int hour_start, int hour_end) {
			for (int i = 0; i < 5; i++) {
				_newX[i] = newX[i];
				_newY[i] = newY[i];
				_newMapId[i] = newMapId[i];
			}
			_heading = heading;
			_hour_start = hour_start;
			_hour_end = hour_end;
		}
	}

	public boolean dg(int locX, int locY, int mapId, L1PcInstance pc) {
		String key = new StringBuilder().append(mapId).append(locX).append(locY).toString();
		if (_dungeonMap.containsKey(key)) {
			int rnd = Random.nextInt(5);
			NewDungeonRandom newDungeonRandom = _dungeonMap.get(key);
			short newMap = newDungeonRandom._newMapId[rnd];
			int newX = newDungeonRandom._newX[rnd];
			int newY = newDungeonRandom._newY[rnd];
			int heading = newDungeonRandom._heading;
			int hour_start = newDungeonRandom._hour_start;
			int hour_end = newDungeonRandom._hour_end;
			if (hour_start >= 0) {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH");
				Calendar realTime = getRealTime();
				int nowHour = Integer.valueOf(sdf.format(realTime.getTime()));
				_log.fine("[NewDungeonRandom] 現在の時間:" + nowHour + " 開始時間:" + hour_start + " 終了時間:" + hour_end);
				if (nowHour < hour_start || (hour_end > 0 && nowHour > hour_end)) {
					pc.sendPackets(new S_ServerMessage(79)); // 沒有任何事情發生。
					return false;
				}
			}
			// 2秒無敵狀態。
			pc.setSkillEffect(ABSOLUTE_BARRIER, 2000);
			pc.stopHpRegeneration();
			pc.stopMpRegeneration();
			pc.stopHpRegenerationByDoll();
			pc.stopMpRegenerationByDoll();
			L1Teleport.teleport(pc, newX, newY, newMap, heading, true);
			return true;
		}
		return false;
	}

	private static Calendar getRealTime() {
		TimeZone _tz = TimeZone.getTimeZone(Config.TIME_ZONE);
		Calendar cal = Calendar.getInstance(_tz);
		return cal;
	}
}
