package br.com.academiadev.reembolsoazul.converter;

public interface Converter<T, D> {
	public D toDTO(T entity);

	public T toEntity(D dto);
}
