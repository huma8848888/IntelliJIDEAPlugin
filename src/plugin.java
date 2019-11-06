import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.tree.PsiPlainTextImpl;
import com.intellij.psi.impl.source.tree.java.PsiNewExpressionImpl;
import com.intellij.psi.util.PsiUtilBase;

import java.util.ArrayList;

public class plugin extends AnAction {
    private final String PSI_CLASS_NAME = "com.intellij.psi.impl.source.PsiClassImpl";
    private final String PSI_METHOD_NAME = "com.intellij.psi.impl.source.PsiMethodImpl";

    private PsiClassImpl currentEditClass;
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        //获取到当前的编辑器对象
        Project project = e.getData(PlatformDataKeys.PROJECT);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        //获取到当前正在编辑的文件
        PsiFile mFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        //将文件拆分为psiElement
        PsiElement[] psiElement = mFile.getChildren();
        //获取类并将类拆分为psiElement，这里先把类的位置定为最后一处，功能实现后再实现类中有子类的情况
        PsiElement[] psiClassElement = psiElement[psiElement.length - 1].getChildren();
        //获取PsiClass
        for (int i = 0; i < psiElement.length ; i++){
            if (psiElement[i].getClass().getName().equalsIgnoreCase(PSI_CLASS_NAME)){
                //查询到类的Impl则停止
                currentEditClass = (PsiClassImpl) psiElement[i];
                break;
            }
        }

        ArrayList arrayList = new ArrayList<>();
        for (int j = 0; j < currentEditClass.getChildren().length; j++){
            if (currentEditClass.getChildren()[j].getClass().getName().equalsIgnoreCase(PSI_METHOD_NAME)){
            }
        }







    }

//    private void processor(PsiFile mFile, PsiClassImpl mClass){
//        run(mFile);
//    }

//    private void run(PsiFile mFile){
//        try {
//            WriteCommandAction.runWriteCommandAction(mFile.getProject(), () -> {
//                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(mFile.getProject());
//                String resClassPath = createRespFile(mFile,elementFactory); //生成响应文件
//
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private String createRespFile(PsiFile mFile, PsiElementFactory elementFactory) {
//            PsiClass respClass;
//            elementFactory.
//            PsiFile file = entityDirectory.findFile(className + ".java");
//            if (file != null) {
//                respClass = ((PsiJavaFile) file).getClasses()[0];
//            } else {
//                respClass = JavaDirectoryService.getInstance().createClass(entityDirectory, className);
//                respClass.getExtendsList().add(getReferenceClass(elementFactory, BaseModelStr)); //确定继承关系
//                respClass.getModifierList().add(elementFactory.createKeyword("public"));//修改权限
//            }
//            if (respClass == null) {
//                return null;
//            }
//            // 根据输入的Json创建类成员
//            JsonToFieldUtil.run(respClass, ConfigCenter.RspBody);
//            return respClass.getQualifiedName().trim();
//
//    }
}
