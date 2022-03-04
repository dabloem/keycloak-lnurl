<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>
    <#if section = "header">
        ${msg("loginAccountTitle")}
    <#elseif section = "form">

        <div class="login">
            <h1 class="h3">Login</h1>

            <div class="box">
                <div id="infoMessage"></div>
                <#if realm.password>
                    <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post" class="voucherForm">
                        <p></p>
                        <div class="row p-3">
                            <div class="col-md-8" style="padding: 15px">

                                <fieldset>
                                    <input type="text" name="username" value="" id="username" placeholder="Email" class="form-control valid" aria-invalid="false" value="${(login.username!'')}" style="background: whitesmoke;">
                                    <input type="password" name="password" value="" id="password" placeholder="Password" class="form-control valid" aria-invalid="false" style="background: whitesmoke">
                                </fieldset>


                                <#if messagesPerField.existsError('username','password')>
                                    <span id="input-error" class="${properties.kcInputErrorMessageClass!}" aria-live="polite">
                                    ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
                                    </span>
                                </#if>

                                <#if realm.rememberMe && !usernameEditDisabled??>
                                <p>
                                    <#if login.rememberMe??>
                                        <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox" checked> ${msg("rememberMe")}
                                    <#else>
                                        <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox"> ${msg("rememberMe")}
                                    </#if>
                                <p>
                                </#if>

                                <p>
                                    <input type="hidden" id="id-hidden-input" name="credentialId" <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>

                                    <input id="kc-login" type="submit" name="submit" value="${msg("doLogIn")}" class="btn btn-primary">
                                    <#if realm.resetPasswordAllowed>
                                    <a href="${url.loginResetCredentialsUrl}" class="right">${msg("doForgotPassword")}</a>
                                    </#if>
                                </p>
                            </div>
                        </div>
                    </form>
                </#if>

            </div>
        </div>
   </#if>
</@layout.registrationLayout>