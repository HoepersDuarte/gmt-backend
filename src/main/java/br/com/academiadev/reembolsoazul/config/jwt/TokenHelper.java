package br.com.academiadev.reembolsoazul.config.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenHelper extends AbstractTokenUtils {

	public String getUser(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getCreationDate(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getIssuedAt();
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public Date getExpirationDate(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getExpiration();
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public String getDevice(String token) {
		String audience;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			audience = claims.getAudience();
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	public String updateToken(String token, Device device) {
		String updatedToken;
		Date a = timeProvider.getCurrentDate();
		try {
			Claims claims = this.getAllClaimsFromToken(token);
			claims.setIssuedAt(a);
			updatedToken = Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate(device)).signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		} catch (Exception e) {
			updatedToken = null;
		}
		return updatedToken;
	}

	public String generateToken(String username, Device device) {
		String audience = generateAudience(device);
		return Jwts.builder().setIssuer(APP_NAME).setSubject(username).setHeaderParam("email", "docsbruno@gmail.com").setAudience(audience).setIssuedAt(timeProvider.getCurrentDate()).setExpiration(generateExpirationDate(device)).signWith(SIGNATURE_ALGORITHM, SECRET).compact();
	}

	public int getExpiredIn(Device device) {
		return device.isMobile() || device.isTablet() ? MOBILE_EXPIRES_IN : EXPIRES_IN;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		User user = (User) userDetails;
		final String username = getUser(token);
		final Date creationDate = getCreationDate(token);
		Boolean isCreatedBeforeLastPasswordReset = isCreatedBeforeLastPasswordReset(creationDate, user.getLastPasswordChange());
		boolean isSameUser = username != null && username.equals(userDetails.getUsername());
		boolean isExpired = getCreationDate(token).compareTo(timeProvider.getCurrentDate()) <= 0;
		return (isSameUser && !isCreatedBeforeLastPasswordReset && !isExpired);
	}

	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER);
	}

}