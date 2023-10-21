package ProjetJee.ProjetJee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JdbcUserDetailsService implements UserDetailsService {

    @Autowired
    private DataSource dataSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Connection connection;
		try {
		connection = dataSource.getConnection();
        PreparedStatement statement;
		statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new User(
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getBoolean("enabled"),
                true, true, true,
                AuthorityUtils.createAuthorityList("USER")
            );
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    
    } catch (Exception e) {
		e.printStackTrace();
	}
		return null;
}}
