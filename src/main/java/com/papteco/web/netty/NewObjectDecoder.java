package com.papteco.web.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * A decoder which deserializes the received {@link ByteBuf}s into Java objects.
 * <p>
 * Please note that the serialized form this decoder expects is not compatible
 * with the standard {@link ObjectOutputStream}. Please use
 * {@link ObjectEncoder} or {@link ObjectEncoderOutputStream} to ensure the
 * interoperability with this decoder.
 */
public class NewObjectDecoder extends LengthFieldBasedFrameDecoder {

	private final ClassResolver classResolver;

	/**
	 * Creates a new decoder whose maximum object size is {@code 1048576} bytes.
	 * If the size of the received object is greater than {@code 1048576} bytes,
	 * a {@link StreamCorruptedException} will be raised.
	 * 
	 * @param classResolver
	 *            the {@link ClassResolver} to use for this decoder
	 */
	public NewObjectDecoder(ClassResolver classResolver) {
		this(50048576, classResolver);
	}

	/**
	 * Creates a new decoder with the specified maximum object size.
	 * 
	 * @param maxObjectSize
	 *            the maximum byte length of the serialized object. if the
	 *            length of the received object is greater than this value,
	 *            {@link StreamCorruptedException} will be raised.
	 * @param classResolver
	 *            the {@link ClassResolver} which will load the class of the
	 *            serialized object
	 */
	public NewObjectDecoder(int maxObjectSize, ClassResolver classResolver) {
		super(maxObjectSize, 0, 4, 0, 4);
		this.classResolver = classResolver;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if (frame == null) {
			return null;
		}

		return new CompactObjectInputStream(new ByteBufInputStream(frame),
				classResolver).readObject();
	}

	@Override
	protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer,
			int index, int length) {
		return buffer.slice(index, length);
	}
}
