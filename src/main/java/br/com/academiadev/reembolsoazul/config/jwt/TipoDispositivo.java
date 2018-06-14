package br.com.academiadev.reembolsoazul.config.jwt;

public enum TipoDispositivo {

	AUDIENCE_UNKNOWN("unknown"), //
	AUDIENCE_WEB("web"), //
	AUDIENCE_MOBILE("mobile"), //
	AUDIENCE_TABLET("tablet");

	private String id;

	private TipoDispositivo(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
