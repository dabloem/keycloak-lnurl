<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>
<#if section = "header">
${msg("loginAccountTitle")}
<#elseif section = "form">

<div class="login">
    <h1 class="h3">Enter username</h1>
</div>

    <form id="kc-update-profile-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">


        <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
            <#if isAppInitiatedAction??>
                <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("doSubmit")}" />
                <button class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonLargeClass!}" type="submit" name="cancel-aia" value="true" formnovalidate/>${msg("doCancel")}</button>
            <#else>
                <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("doSubmit")}" />
            </#if>
        </div>
    </form>
</#if>
</@layout.registrationLayout>