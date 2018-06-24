package br.com.academiadev.reembolsoazul.config.jwt;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenHelper extends AbstractTokenHelper {

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

	public LocalDateTime getCreationDate(String token) {
		LocalDateTime issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = timeProvider.toLocalDateTime(claims.getIssuedAt());
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public LocalDateTime getExpirationDate(String token) {
		LocalDateTime issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = timeProvider.toLocalDateTime(claims.getExpiration());
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
		try {
			Claims claims = this.getAllClaimsFromToken(token);
			claims.setIssuedAt(timeProvider.toDate(timeProvider.getActualDateTime()));
			updatedToken = Jwts.builder().setClaims(claims)
					.setExpiration(timeProvider.toDate(generateExpirationDate(device)))
					.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		} catch (Exception e) {
			updatedToken = null;
		}
		return updatedToken;
	}

	public String generateToken(String username, String userType, String companyName, Device device) {
		if (device == null) {
			return Jwts.builder().setIssuer(APP_NAME).setSubject(username).claim("role", userType)
					.claim("companyName", companyName)
					.setIssuedAt(timeProvider.toDate(timeProvider.getActualDateTime()))
					.setExpiration(timeProvider.toDate(LocalDateTime.now().plusSeconds(500)))
					.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		}

		String audience = generateAudience(device);
		return Jwts.builder().setIssuer(APP_NAME).setSubject(username).setAudience(audience).claim("role", userType)
				.claim("companyName", companyName).setIssuedAt(timeProvider.toDate(timeProvider.getActualDateTime()))
				.setExpiration(timeProvider.toDate(generateExpirationDate(device)))
				.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
	}

	public int getExpiredIn(Device device) {
		return device.isMobile() || device.isTablet() ? MOBILE_EXPIRES_IN : EXPIRES_IN;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		User user = (User) userDetails;
		final String userToken = getUser(token);
		final LocalDateTime dataDeCriacao = getCreationDate(token);
		Boolean foiCriadoAntesDaUltimaTrocaDeSenha = isCreatedBeforeLastPasswordReset(dataDeCriacao,
				user.getLastPasswordChange());
		boolean ehMesmoUsuario = userToken != null && userToken.equals(userDetails.getUsername());
		boolean estaEspirado = getExpirationDate(token).compareTo(timeProvider.getActualDateTime()) <= 0;
		return (ehMesmoUsuario && !foiCriadoAntesDaUltimaTrocaDeSenha && !estaEspirado);
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