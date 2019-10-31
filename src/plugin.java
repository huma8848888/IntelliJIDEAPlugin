import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiMethodImpl;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.psi.PsiParameterList;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class plugin extends AnAction {
    private final String METHOD_CLASS_NAME = "com.intellij.psi.impl.source.PsiMethodImpl";
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        //获取到当前的编辑器对象
        Project project = e.getData(PlatformDataKeys.PROJECT);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        PsiFile mFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        PsiElement[] psiElement = mFile.getChildren();
        //获取类，这里先把类的位置定为3，功能实现后再实现类中有子类的情况
        PsiElement[] psiClassElement = psiElement[3].getChildren();

        ArrayList methods = new ArrayList<>();
        for (int i = 0; i < psiClassElement.length ; i++){
            //把方法取出来，取出来的是个psiClassElement
            if (psiClassElement[i].getClass().getName().equalsIgnoreCase(METHOD_CLASS_NAME)){
                methods.add(psiClassElement[i]);
            }
        }

        for (int i = 0; i < methods.size() ; i++){
            PsiParameterList parameterList = ((PsiMethodImpl)methods.get(i)).getParameterList();
            PsiParameter[] psiParameter = parameterList.getParameters();
            System.out.println("Method : " + methods.get(i));
            System.out.println("Body :" + ((PsiMethodImpl)methods.get(i)).getBody().getText());
//            ((PsiMethodImpl)methods.get(i)).add()
//            ((PsiMethodImpl)methods.get(i)).getBody();
//            for (int j = 0; j < psiParameter.length ; j++){
////                System.out.println("parameter:" + psiParameter[j]);
////            }
        }
    }
}
