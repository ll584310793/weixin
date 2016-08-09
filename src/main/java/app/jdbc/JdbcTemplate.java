package app.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate {

	public long insertAndGetId(final String sql, final Object... args) {
		long id = 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		update(new PreparedStatementCreator() {
			public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection conn)
					throws java.sql.SQLException {
				java.sql.PreparedStatement ps = conn.prepareStatement(sql);
				for (int i = 1, l = args.length; i <= l; i++) {
					ps.setObject(i, args[i - 1]);
				}
				return ps;
			}
		}, keyHolder);
		id = keyHolder.getKey().longValue();
		return id;
	}

	public Map<String, Object> queryForMap(String sql, Object... args) {
		List<Map<String, Object>> list = super.queryForList(sql, args);
		return list != null && !list.isEmpty() ? list.get(0) : null;
	}

}
