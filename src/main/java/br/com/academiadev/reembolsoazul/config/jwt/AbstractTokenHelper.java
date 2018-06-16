package br.com.academiadev.reembolsoazul.config.jwt;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;

import br.com.academiadev.reembolsoazul.common.TimeProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AbstractTokenHelper {

	@Value("${app.name}")
	protected String APP_NAME;

	@Value("${jwt.secret}")
	public String SECRET;

	@Value("${jwt.expires_in}")
	protected int EXPIRES_IN;

	@Value("${jwt.mobile_expires_in}")
	protected int MOBILE_EXPIRES_IN;

	@Value("${jwt.header}")
	protected String AUTH_HEADER;

	@Autowired
	protected TimeProvider timeProvider;

	protected SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	public AbstractTokenHelper() {
		super();
	}

	protected String generateAudience(Device device) {
		DeviceType audience = DeviceType.AUDIENCE_UNKNOWN;
		if (device.isNormal()) {
			audience = DeviceType.AUDIENCE_WEB;
		} else if (device.isTablet()) {
			audience = DeviceType.AUDIENCE_TABLET;
		} else if (device.isMobile()) {
			audience = DeviceType.AUDIENCE_MOBILE;
		}
		return audience.getId();
	}

	protected Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	protected LocalDateTime generateExpirationDate(Device device) {
		long expiresIn = device.isTablet() || device.isMobile() ? MOBILE_EXPIRES_IN : EXPIRES_IN;
		return LocalDateTime.now().plusSeconds(expiresIn * 1000);
	}

	protected Boolean isCreatedBeforeLastPasswordReset(LocalDateTime created, LocalDateTime lastPasswordReset) {
		return (lastPasswordReset != null && created.isBefore(lastPasswordReset));
	}

}