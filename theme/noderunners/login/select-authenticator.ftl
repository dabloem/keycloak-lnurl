<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=false; section>
    <#if section = "header" || section = "show-username">
        <script type="text/javascript">
            function fillAndSubmit(authExecId) {
                document.getElementById('authexec-hidden-input').value = authExecId;
                document.getElementById('kc-select-credential-form').submit();
            }
        </script>
        <#if section = "header">
            ${msg("loginChooseAuthenticator")}
        </#if>
    <#elseif section = "form">

        <div class="login">
            <h1 class="h3">Select authentication method</h1>

            <div class="box">
                <div id="infoMessage"></div>
                <form id="kc-select-credential-form" action="${url.loginAction}" method="post" class="voucherForm">
                    <p></p>
                    <div class="row p-3">
                        <div class="col-md-6" style="padding: 15px">
                            <div class="${properties.kcSelectAuthListClass!}">
                                <#list auth.authenticationSelections as authenticationSelection>
                                    <div class="${properties.kcSelectAuthListItemClass!}" onclick="fillAndSubmit('${authenticationSelection.authExecId}')">

                                        <div class="${properties.kcSelectAuthListItemIconClass!}">
                                            <i class="${properties['${authenticationSelection.iconCssClass}']!authenticationSelection.iconCssClass} fa-2x"></i>
                                        </div>
                                        <div class="${properties.kcSelectAuthListItemBodyClass!}">
                                            <div class="${properties.kcSelectAuthListItemHeadingClass!}">
                                                ${msg('${authenticationSelection.displayName}')}
                                            </div>
                                            <div class="${properties.kcSelectAuthListItemDescriptionClass!}">
                                                ${msg('${authenticationSelection.helpText}')}
                                            </div>
                                        </div>
                                        <div class="${properties.kcSelectAuthListItemFillClass!}"></div>
                                        <#if false>
                                        <div class="${properties.kcSelectAuthListItemArrowClass!}">
                                            <i class="${properties.kcSelectAuthListItemArrowIconClass!}"></i>
                                        </div>
                                        </#if>
                                    </div>
                                </#list>
                                <input type="hidden" id="authexec-hidden-input" name="authenticationExecution" />
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </#if>
</@layout.registrationLayout>