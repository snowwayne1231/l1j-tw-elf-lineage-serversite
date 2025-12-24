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
package l1j.server.server.serverpackets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class ServerBasePacket {
	private static Logger _log = Logger.getLogger(ServerBasePacket.class.getName());

	private static final String CLIENT_LANGUAGE_CODE = Config.CLIENT_LANGUAGE_CODE;

	private static final Set<String> NO_LOG_TYPES = Collections.unmodifiableSet(
        new HashSet<String>(Arrays.asList(
            "[S] S_NpcChatPacket",
            "[S] S_ChangeHeading",
            "[S] S_CharTitle",
            "[S] S_Light",
            "[S] S_HPMeter",
            "[S] S_MPUpdate",
            "[S] S_HPUpdate"
        ))
    );

	ByteArrayOutputStream _bao = new ByteArrayOutputStream();

	protected ServerBasePacket() {
		// String type = getType();
		// if (!NO_LOG_TYPES.contains(type)) {
        //     _log.info("type=" + type + ", len=" + _bao.size());
        // }
	}

	protected void writeD(int value) {
		_bao.write(value & 0xff);
		_bao.write(value >> 8 & 0xff);
		_bao.write(value >> 16 & 0xff);
		_bao.write(value >> 24 & 0xff);
	}

	protected void writeH(int value) {
		_bao.write(value & 0xff);
		_bao.write(value >> 8 & 0xff);
	}

	protected void writeC(int value) {
		_bao.write(value & 0xff);
	}

	protected void writeP(int value) {
		_bao.write(value);
	}

	protected void writeL(long value) {
		_bao.write((int) (value & 0xff));
	}

	protected void writeF(double org) {
		long value = Double.doubleToRawLongBits(org);
		_bao.write((int) (value & 0xff));
		_bao.write((int) (value >> 8 & 0xff));
		_bao.write((int) (value >> 16 & 0xff));
		_bao.write((int) (value >> 24 & 0xff));
		_bao.write((int) (value >> 32 & 0xff));
		_bao.write((int) (value >> 40 & 0xff));
		_bao.write((int) (value >> 48 & 0xff));
		_bao.write((int) (value >> 56 & 0xff));
	}

	protected void writeS(String text) {
		try {
			if (text != null) {
				_bao.write(text.getBytes(CLIENT_LANGUAGE_CODE));
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}

		_bao.write(0);
	}

	protected void writeByte(byte[] text) {
		try {
			if (text != null) {
				_bao.write(text);
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	public int getLength() {
		return _bao.size() + 2;
	}

	public byte[] getBytes() {
		int padding = _bao.size() % 4;

		if (padding != 0) {
			for (int i = padding; i < 4; i++) {
				writeC(0x00);
			}
		}

		return _bao.toByteArray();
	}

	public abstract byte[] getContent() throws IOException;

	/**
	 * サーバーパケットの種類を表す文字列を返す。("[S] S_WhoAmount" 等)
	 */
	public String getType() {
		return "[S] " + this.getClass().getSimpleName();
	}
}
