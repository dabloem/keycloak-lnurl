<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>
    <#if section = "header">
        ${msg("loginAccountTitle")}
    <#elseif section = "form">
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <#if realm.password>
                    <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
                        <div class="${properties.kcFormGroupClass!}">
                            <label for="message" class="${properties.kcLabelClass!}">${msg('message')}</label>
                            <div class="pf-c-clipboard-copy">
                                <div class="pf-c-clipboard-copy__group">
                                    <input
                                            class="pf-c-form-control"
                                            readonly
                                            type="text"
                                            value="${login.message}"
                                            id="message"
                                            aria-label="Copyable input"
                                    />
                                    <button
                                            class="pf-c-button pf-m-control"
                                            type="button"
                                            aria-label="Copy to clipboard"
                                            id="basic-readonly-copy-button"
                                            aria-labelledby="basic-readonly-copy-button basic-readonly-text-input">
                                        <i class="fas fa-copy" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </div>
                        </div>

                        <script>
                            document.getElementById("basic-readonly-copy-button").onclick = function (){
                                /* Get the text field */
                                var copyText = '${login.message}';
                                // var copyText = document.getElementById("message");

                                /* Select the text field */
                                // copyText.select();
                                // copyText.setSelectionRange(0, 99999); /* For mobile devices */

                                /* Copy the text inside the text field */
                                navigator.clipboard.writeText(copyText);
                                alert("Copied message: " + copyText);
                            };
                        </script>
                        <div class="${properties.kcFormGroupClass!}">
                            <label for="node" class="${properties.kcLabelClass!}">${msg('node')}</label>

                                <input tabindex="1" id="node" class="${properties.kcInputClass!}" name="node" type="text" autofocus
                                       aria-invalid="<#if messagesPerField.existsError('node','signature')>true</#if>"
                                />

                                <#if messagesPerField.existsError('node','signature')>
                                    <span id="input-error" class="${properties.kcInputErrorMessageClass!}" aria-live="polite">
                                        ${kcSanitize(messagesPerField.getFirstError('node','signature'))?no_esc}
                                    </span>
                                </#if>

                        </div>

                        <div class="${properties.kcFormGroupClass!}">
                            <label for="signature" class="${properties.kcLabelClass!}">${msg("signature")}</label>

                            <input tabindex="2" id="signature" class="${properties.kcInputClass!}" name="signature" autocomplete="off"
                                   aria-invalid="<#if messagesPerField.existsError('node','signature')>true</#if>"
                            />
                        </div>

                        <div class="${properties.kcFormGroupClass!} ${properties.kcFormSettingClass!}">
                            <div id="kc-form-options">
                                <#if realm.rememberMe && !usernameEditDisabled??>
                                    <div class="checkbox">
                                        <label>
                                            <#if login.rememberMe??>
                                                <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox" checked> ${msg("rememberMe")}
                                            <#else>
                                                <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox"> ${msg("rememberMe")}
                                            </#if>
                                        </label>
                                    </div>
                                </#if>
                            </div>
                        </div>

                        <div id="kc-form-buttons" class="${properties.kcFormGroupClass!}">
                            <input type="hidden" id="id-hidden-input" name="credentialId" <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>
                            <input tabindex="4" class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" name="login" id="kc-login" type="submit" value="${msg("doLogIn")}"/>
                        </div>
                    </form>
                </#if>
            </div>

            <#if realm.password && social.providers??>
                <div id="kc-social-providers" class="${properties.kcFormSocialAccountSectionClass!}">
                    <hr/>
                    <h4>${msg("identity-provider-login-label")}</h4>

                    <ul class="${properties.kcFormSocialAccountListClass!} <#if social.providers?size gt 3>${properties.kcFormSocialAccountListGridClass!}</#if>">
                        <#list social.providers as p>
                            <a id="social-${p.alias}" class="${properties.kcFormSocialAccountListButtonClass!} <#if social.providers?size gt 3>${properties.kcFormSocialAccountGridItem!}</#if>"
                               type="button" href="${p.loginUrl}">
                                <#if p.iconClasses?has_content>
                                    <i class="${properties.kcCommonLogoIdP!} ${p.iconClasses!}" aria-hidden="true"></i>
                                    <span class="${properties.kcFormSocialAccountNameClass!} kc-social-icon-text">${p.displayName!}</span>
                                <#else>
                                    <span class="${properties.kcFormSocialAccountNameClass!}">${p.displayName!}</span>
                                </#if>
                            </a>
                        </#list>
                    </ul>
                </div>
            </#if>

        </div>
    <#elseif section = "info" >
        <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
            <div id="kc-registration-container">
                <div id="kc-registration">
                    <span>${msg("noAccount")}
                        <a tabindex="6" href="${url.registrationUrl}">${msg("doRegister")}</a>
                    </span>
                </div>
            </div>
        </#if>
    </#if>

</@layout.registrationLayout>