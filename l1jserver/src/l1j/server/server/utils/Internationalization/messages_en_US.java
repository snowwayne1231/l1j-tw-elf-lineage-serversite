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
package l1j.server.server.utils.Internationalization;

import java.util.ListResourceBundle;

/**
 * @category 英美-英語<br>
 *           國際化的英文是Internationalization 因為單字中總共有18個字母，簡稱I18N，
 *           目的是讓應用程式可以應地區不同而顯示不同的訊息。
 */

public class messages_en_US extends ListResourceBundle {
	static final Object[][] contents = {
		// { "l1j.server.memoryUse", "Used: " },
		// { "l1j.server.memory", "MB of memory" },
		// { "l1j.server.server.model.onGroundItem", "items on the ground" },
		// { "l1j.server.server.model.seconds","will be delete after 10 seconds" },
		// { "l1j.server.server.model.deleted", "was deleted" }, 
		// { "l1j.server.server.GameServer.ver","Version: Lineage 3.80C  Dev. By L1J-TW For All User" },
		// { "l1j.server.server.GameServer.settingslist","●●●●〈Server Config List〉●●●●"},
		// { "l1j.server.server.GameServer.exp","「exp」"},
		// { "l1j.server.server.GameServer.x","【times】"},
		// { "l1j.server.server.GameServer.level","【LV】"},
		// { "l1j.server.server.GameServer.justice","「justice」"},
		// { "l1j.server.server.GameServer.karma","「karma」"},
		// { "l1j.server.server.GameServer.dropitems","「dropitems」"},
		// { "l1j.server.server.GameServer.dropadena","「dropadena」"},
		// { "l1j.server.server.GameServer.enchantweapon","「enchantweapon」"},
		// { "l1j.server.server.GameServer.enchantarmor","「enchantarmor」"},
		// { "l1j.server.server.GameServer.chatlevel","「chatLevel」"},
		// { "l1j.server.server.GameServer.nonpvp1","「Non-PvP」: Not Work (PvP)"},
		// { "l1j.server.server.GameServer.nonpvp2","「Non-PvP」: Work (Non-PvP)"},
		// { "l1j.server.server.GameServer.maxplayer","Max connection limit "},
		// { "l1j.server.server.GameServer.player"," players"},
		// { "l1j.server.server.GameServer.waitingforuser","Waiting for user's connection..."},
		// { "l1j.server.server.GameServer.from","from "},
		// { "l1j.server.server.GameServer.attempt"," attempt to connect."},
		// { "l1j.server.server.GameServer.setporton","Server is successfully set on port "},
		// { "l1j.server.server.GameServer.initialfinished","Initialize finished.."}};
		{ "l1j.server.memoryUse", "使用了: " },
		{ "l1j.server.memory", "MB 的記憶體" },
		{ "l1j.server.server.model.onGroundItem", "地上的物品" },
		{ "l1j.server.server.model.seconds", "10秒後將被清除" },
		{ "l1j.server.server.model.deleted", "已經被清除了" },
		{ "l1j.server.server.GameServer.ver","版本: Lineage 3.80C(3.63c Client) 開發 By L1J-TW 修改 By Snow" },
		{ "l1j.server.server.GameServer.settingslist","●●●●〈伺服器設置清單〉●●●●"},
		{ "l1j.server.server.GameServer.exp","「經驗值」"},
		{ "l1j.server.server.GameServer.x","【倍】"},
		{ "l1j.server.server.GameServer.level","【級】"},
		{ "l1j.server.server.GameServer.justice","「正義值」"},
		{ "l1j.server.server.GameServer.karma","「友好度」"},
		{ "l1j.server.server.GameServer.dropitems","「物品掉落」"},
		{ "l1j.server.server.GameServer.dropadena","「金幣掉落」"},
		{ "l1j.server.server.GameServer.enchantweapon","「衝武」"},
		{ "l1j.server.server.GameServer.enchantarmor","「衝防」"},
		{ "l1j.server.server.GameServer.chatlevel","「廣播頻道可用等級」"},
		{ "l1j.server.server.GameServer.nonpvp1","「Non-PvP設定」: 【無效 (PvP可能)】"},
		{ "l1j.server.server.GameServer.nonpvp2","「Non-PvP設定」: 【有效 (PvP不可)】"},
		{ "l1j.server.server.GameServer.maxplayer","連線人數上限為 "},
		{ "l1j.server.server.GameServer.player"," 人 "},
		{ "l1j.server.server.GameServer.waitingforuser","等待客戶端連接中..."},
		{ "l1j.server.server.GameServer.from","從 "},
		{ "l1j.server.server.GameServer.attempt"," 試圖連線"},
		{ "l1j.server.server.GameServer.setporton","伺服器成功建立在 port "},
		{ "l1j.server.server.GameServer.initialfinished","初始化完畢"}};

	@Override
	protected Object[][] getContents() {
		return contents;
	}

}
