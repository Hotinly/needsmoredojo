package com.chrisfolger.needsmoredojo.intellij.inspections;

import com.chrisfolger.needsmoredojo.core.settings.DojoSettings;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class AddExceptionQuickFix implements LocalQuickFix
{
    private PsiElement define;
    private PsiElement parameter;
    private String absolutePath;

    public AddExceptionQuickFix(PsiElement define, PsiElement parameter, String absolutePath) {
        this.absolutePath = absolutePath;
        this.define = define;
        this.parameter = parameter;
    }

    @NotNull
    @Override
    public String getName() {
        if(absolutePath != null)
        {
            return "Flag " + absolutePath + " as a mismatched imports exception";
        }
        else
        {
            return "Flag " + define.getText() + " as a mismatched imports exception";
        }
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Needs More Dojo";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor)
    {
        if(absolutePath != null)
        {
            ServiceManager.getService(project, DojoSettings.class).getAmdImportNamingExceptionsList().add(absolutePath + "(" +  parameter.getText());
        }
        else
        {
            ServiceManager.getService(project, DojoSettings.class).getAmdImportNamingExceptionsList().add(define.getText().replaceAll("\"|'", "") + "(" +  parameter.getText());
        }
    }
}