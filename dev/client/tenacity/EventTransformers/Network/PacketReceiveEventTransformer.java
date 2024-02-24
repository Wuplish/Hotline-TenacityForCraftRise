package dev.client.tenacity.EventTransformers.Network;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class PacketReceiveEventTransformer {
	
	public static byte[] TransformClient(byte[] originalBytes)
    {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9, classWriter)
        {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor original_mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
                if (name.equals("a") && descriptor.equals("(Lio/netty/channel/ChannelHandlerContext;Lcom/craftrise/lv;J)V"))
                {
                    return new MethodVisitor(Opcodes.ASM9, original_mv) {
                        @Override
                        public void visitCode()
                        {
                        	 mv.visitTypeInsn(Opcodes.NEW, "dev/event/impl/network/PacketReceiveEvent");
                             mv.visitInsn(Opcodes.DUP);
                             mv.visitVarInsn(Opcodes.ALOAD, 2);
                             mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "dev/event/impl/network/PacketReceiveEvent", "<init>", "(Lcom/craftrise/lv;)V", false);
                             mv.visitVarInsn(Opcodes.ASTORE, 9);
                             mv.visitVarInsn(Opcodes.ALOAD, 9);
                             mv.visitMethodInsn(Opcodes.INVOKESTATIC, "dev/client/Client", "dispatchEvent", "(Ldev/event/Event;)V", false);
                             mv.visitVarInsn(Opcodes.ALOAD, 9);
                             mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "dev/event/impl/network/PacketReceiveEvent", "isCancelled", "()Z", false);
                             Label label = new Label();
                             mv.visitJumpInsn(Opcodes.IFEQ, label);
                             mv.visitInsn(Opcodes.RETURN);
                             mv.visitLabel(label);
                        	mv.visitCode();

                        }
                    };
                }
                return original_mv;
            }
        };

        ClassReader classReader = new ClassReader(originalBytes);
        classReader.accept(classVisitor, 0);
        return classWriter.toByteArray();
    }
}
