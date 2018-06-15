package br.com.academiadev.reembolsoazul.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;

@Component
public class DeviceProvider {

	public Device getDispositivo(HttpServletRequest request) {
		return DeviceUtils.getCurrentDevice(request);
	}
}
