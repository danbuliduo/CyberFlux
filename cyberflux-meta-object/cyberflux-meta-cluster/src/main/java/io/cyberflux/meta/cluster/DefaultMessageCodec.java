package io.cyberflux.meta.cluster;

import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.scalecube.cluster.transport.api.Message;
import io.scalecube.cluster.transport.api.MessageCodec;

public class DefaultMessageCodec implements MessageCodec {
	private final ObjectMapper mapper;

	public DefaultMessageCodec() {
		this(DefaultMessageMapper.INSTANCE);
	}

	public DefaultMessageCodec(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public Message deserialize(InputStream stream) throws Exception {
		return mapper.readValue(stream, Message.class);
	}

	@Override
	public void serialize(Message message, OutputStream stream) throws Exception {
		mapper.writeValue(stream, message);
	}

}
