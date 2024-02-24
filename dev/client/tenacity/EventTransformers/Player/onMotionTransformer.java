package dev.client.tenacity.EventTransformers.Player;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class onMotionTransformer {
	
	public static byte[] TransformClient(byte[] originalBytes)
    {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9, classWriter)
        {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor original_mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
                if (name.equals("V") && descriptor.equals("(J)V"))
                {
                    return new MethodVisitor(Opcodes.ASM9, original_mv) {
                        @Override
                        public void visitCode()
                        {
                        	mv.visitTypeInsn(Opcodes.NEW, "dev/event/impl/player/MotionEvent");
                            mv.visitInsn(Opcodes.DUP);
                        	mv.visitFieldInsn(Opcodes.GETSTATIC, "dev/event/Event$Type", "PRE", "Ldev/event/Event$Type;");
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitFieldInsn(Opcodes.GETFIELD, "com/craftrise/client/fa", "bE", "D");
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/craftrise/client/fa", "a", "()Lcom/craftrise/ah;", false);
                            mv.visitFieldInsn(Opcodes.GETFIELD, "com/craftrise/ah", "a", "D");
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitFieldInsn(Opcodes.GETFIELD, "com/craftrise/client/fa", "bH", "D");
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitFieldInsn(Opcodes.GETFIELD, "com/craftrise/client/fa", "bL", "F");
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitFieldInsn(Opcodes.GETFIELD, "com/craftrise/client/fa", "N", "F");
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitFieldInsn(Opcodes.GETFIELD, "com/craftrise/client/fa", "s", "Lcr/launcher/eb;");
                            mv.visitLdcInsn(new Long(522424L));
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "cr/launcher/eb", "a", "(J)Z", false);
                            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "dev/event/impl/player/MotionEvent", "<init>", "(Ldev/event/Event$Type;DDDFFZ)V", false);
                            mv.visitVarInsn(Opcodes.ASTORE, 8);
                            mv.visitVarInsn(Opcodes.ALOAD, 8);
                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "dev/client/Client", "dispatchEvent", "(Ldev/event/Event;)V", false);
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
