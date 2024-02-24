package dev.client.tenacity.EventTransformers.Game;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TickEventTransformer {
	
	public static byte[] TransformClient(byte[] originalBytes)
    {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9, classWriter)
        {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor original_mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
                if (name.equals("z") && descriptor.equals("(J)V"))
                {
                    return new MethodVisitor(Opcodes.ASM9, original_mv) {
                        @Override
                        public void visitCode()
                        {
                        	mv.visitTypeInsn(Opcodes.NEW, "dev/event/impl/game/TickEvent");
                            mv.visitInsn(Opcodes.DUP);
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitFieldInsn(Opcodes.GETFIELD, "com/craftrise/client/S", "bl", "I");
                            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "dev/event/impl/game/TickEvent", "<init>", "(I)V", false);
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
