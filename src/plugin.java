import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiMethodImpl;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.psi.PsiParameterList;
import com.intellij.util.IncorrectOperationException;
import org.apache.http.util.TextUtils;
import org.eclipse.jdt.internal.compiler.apt.dispatch.IProcessorProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class plugin extends AnAction {
    private final String METHOD_CLASS_NAME = "com.intellij.psi.impl.source.PsiMethodImpl";
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
//        PsiClass targetPsiClass = (PsiClass) psiElement[psiElement.length - 1];
//        System.out.println("psiElement[psiElement.length - 1] : " + psiElement[psiElement.length - 1]);
        for (int i = 0; i < psiElement.length ; i++){
            if (psiElement[i].getClass().getName().equalsIgnoreCase(METHOD_CLASS_NAME)){
                //查询到类的Impl则停止
                currentEditClass = (PsiClassImpl) psiElement[i];
                break;
            }
        }




//        System.out.println("psiElement :" + psiElement);

//        ArrayList methods = new ArrayList<>();
//        for (int i = 0; i < psiClassElement.length ; i++){
//            //把方法取出来，取出来的是个psiClassElement
//            if (psiClassElement[i].getClass().getName().equalsIgnoreCase(METHOD_CLASS_NAME)){
//                methods.add(psiClassElement[i]);
//            }
//        }
//
//        for (int i = 0; i < methods.size() ; i++){
//            PsiParameterList parameterList = ((PsiMethodImpl)methods.get(i)).getParameterList();
//            PsiParameter[] psiParameter = parameterList.getParameters();
//            System.out.println("Method : " + methods.get(i));
//            System.out.println("Body :" + ((PsiMethodImpl)methods.get(i)).getBody().getText());
//        }
    }

    private void processor(PsiFile mFile, PsiClassImpl mClass){
        run(mFile);
    }

    private void run(PsiFile mFile){
        try {
            WriteCommandAction.runWriteCommandAction(mFile.getProject(), () -> {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(mFile.getProject());
                String resClassPath = createRespFile(mFile,elementFactory); //生成响应文件

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createRespFile(PsiFile mFile, PsiElementFactory elementFactory) {
            PsiClass respClass;
            elementFactory.
            PsiFile file = entityDirectory.findFile(className + ".java");
            if (file != null) {
                respClass = ((PsiJavaFile) file).getClasses()[0];
            } else {
                respClass = JavaDirectoryService.getInstance().createClass(entityDirectory, className);
                respClass.getExtendsList().add(getReferenceClass(elementFactory, BaseModelStr)); //确定继承关系
                respClass.getModifierList().add(elementFactory.createKeyword("public"));//修改权限
            }
            if (respClass == null) {
                return null;
            }
            // 根据输入的Json创建类成员
            JsonToFieldUtil.run(respClass, ConfigCenter.RspBody);
            return respClass.getQualifiedName().trim();

    }
}
